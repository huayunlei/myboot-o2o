package com.ihomefnt.o2o.service.service.right;

import com.ihomefnt.cms.utils.DateUtil;
import com.ihomefnt.o2o.intf.domain.programorder.vo.response.FamilyOrderPayResponse;
import com.ihomefnt.o2o.intf.domain.right.dto.GradeVersionDto;
import com.ihomefnt.o2o.intf.domain.right.dto.OrderRightsResultDto;
import com.ihomefnt.o2o.intf.domain.right.vo.request.QueryMyOrderRightItemListRequest;
import com.ihomefnt.o2o.intf.domain.right.vo.response.*;
import com.ihomefnt.o2o.intf.manager.constant.right.RightLevelNewEnum;
import com.ihomefnt.o2o.intf.manager.util.common.bean.JsonUtils;
import com.ihomefnt.o2o.intf.manager.util.common.image.QiniuImageUtils;
import com.ihomefnt.o2o.intf.proxy.right.RightProxy;
import com.ihomefnt.o2o.intf.service.programorder.ProductProgramOrderService;
import com.ihomefnt.o2o.intf.service.right.QueryMyOrderRightService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author jerfan cang
 * @date 2018/10/11 10:46
 */
@Service
public class QueryMyOrderRightServiceImpl implements QueryMyOrderRightService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryMyOrderRightServiceImpl.class);

    @Autowired
    private ProductProgramOrderService orderService;

    @Autowired
    RightProxy rightProxy;
    /**
     * 图片100 尺寸枚举值
     */
    private static final Integer IMAGE_SIZE_ICON = 100;

    /**
     * 10000 枚举值
     */
    private static final Integer NUMBER_TEN_THOUSAND = 10000;

    @Override
    public OrderRightsResultVo queryMyOrderRightItemList(QueryMyOrderRightItemListRequest param) {
        GradeVersionDto gradeVersionDto = rightProxy.queryCurrentVersion(param.getOrderNum());
        param.setVersion(gradeVersionDto == null ? 1 : gradeVersionDto.getVersion());
        OrderRightsResultDto dto = rightProxy.queryMyOrderRightItemList(param);
        if (null == dto) {
            return null;
        }
        OrderRightsResultVo vo = dto2vo(dto, param.getVersion());
        return vo;
    }

    /**
     * dto 转换成 vo
     *
     * @param dto     OrderRightsResultDto
     * @param version
     * @return OrderRightsResultVo
     * @throws RuntimeException 异常
     */
    private OrderRightsResultVo dto2vo(final OrderRightsResultDto dto, Integer version) throws RuntimeException {
        OrderRightsResultVo vo = null;
        try {

            vo = JsonUtils.json2obj(JsonUtils.obj2json(dto), OrderRightsResultVo.class);
            vo.setVersionFlag(version);
            // 权益已使用描述字段赋值
            vo = setRightConsumerDesc(vo);
            // 降等级 后 去除不可选的权益项
            vo = clearMoreItem(vo);
            // 把已经消费的塞给classifyInfoList节点
            vo = hadUsed(vo);
//            // 1、给权益项排序 已经确权的排在前面 2、计算权益项个数 3、url和subtitle处理 4、艾佳贷和现金项
//            vo = assembleRightInfoList(vo);
            // 给权益项排序 已经确权的排在前面
            vo = sortInClassifyInfoList(vo);
            //时间往前推三天
            vo = dateBefore3Day(vo);
            // 计算权益项个数
            vo = countItems(vo);
            // url 和 subtitle 处理
            vo = handle_url_subtitle(vo);
            // 艾佳贷 和 现金项
            vo = handle2Note(vo);
            // 设置服务器时间
            vo.setCurrentTime(System.currentTimeMillis());
            //设置已确认收款金额
            vo.setConfirmReapAmount(dto.getConfirmedAmount() == null ? BigDecimal.ZERO : dto.getConfirmedAmount());

            // 权益使用
            setRightUsed(vo);
            try {
                if (vo.getCouponAmount() == null) {
                    vo.setCouponAmount(new BigDecimal(0));
                }
                if (vo.getOriginalOrderAmount() == null) {
                    vo.setOriginalOrderAmount(new BigDecimal(0));
                }
                vo.setDeductionAmount(vo.getCouponAmount().min(vo.getOriginalOrderAmount()));
            } catch (Exception e) {
                LOGGER.error("right error message {}", e);
                vo.setDeductionAmount(new BigDecimal(0));
            }
        } catch (Exception e) {
            LOGGER.error("QueryMyOrderRightServiceImpl.dto2vo exception , more info : ", e);
            throw new RuntimeException("QueryMyOrderRightServiceImpl.dto2vo exception , more info : " + e);
        }
        return vo;
    }

    /**
     * 对权益分类下的项进行排序
     *
     * @param vo
     * @return
     */
    private OrderRightsResultVo sortInClassifyInfoList(OrderRightsResultVo vo) {
        // 遍历权益分类
        for (OrderClassifyInfoVo classifyInfoVo : vo.getClassifyInfoList()) {
            // 对分类下的权益项进行排序
            classifyInfoVo.setItemDetailList(getSortList(classifyInfoVo.getItemDetailList()));
        }
        return vo;
    }

    /**
     * 计算权益项个数
     *
     * @param vo
     * @return
     */
    private OrderRightsResultVo countItems(OrderRightsResultVo vo) {
        Integer itemCount = 0;
        if (null != vo.getGradeId() && vo.getGradeId() > 0) {
            for (OrderClassifyInfoVo orderClassifyInfoVo : vo.getClassifyInfoList()) {
                if (orderClassifyInfoVo.getClassifyId() == 3 ||
                        orderClassifyInfoVo.getClassifyId() == 4) {
                    itemCount += orderClassifyInfoVo.getRightsConfirmedLimit();
                }
            }
            //现金补贴项目
            itemCount++;
        }
        vo.setItemCount(itemCount);
        return vo;
    }

    /**
     * 切图和拼接subtitle
     *
     * @param vo
     * @return
     */
    private OrderRightsResultVo handle_url_subtitle(OrderRightsResultVo vo) {

        for (OrderClassifyInfoVo orderClassifyInfoVo : vo.getClassifyInfoList()) {
            for (RigthtsItemDetailVo rigthtsItemDetailVo : orderClassifyInfoVo.getItemDetailList()) {
                String url = rigthtsItemDetailVo.getUrl();
                // 切图
                url = QiniuImageUtils.compressImageAndSamePicTwo(url, IMAGE_SIZE_ICON, IMAGE_SIZE_ICON);
                rigthtsItemDetailVo.setUrl(url);
                //设置subtitle
                rigthtsItemDetailVo.setSubtitle(subtitle(rigthtsItemDetailVo, vo.getVersionFlag()));
            }
        }

        return vo;
    }

    /**
     * 艾佳贷和现金权益项节点 单独放到OrderRightsResultVo 下
     *
     * @param vo
     * @return
     */
    private OrderRightsResultVo handle2Note(OrderRightsResultVo vo) {
        OrderClassifyInfoVo orderClassifyInfoVo_base = null;
        for (OrderClassifyInfoVo orderClassifyInfoVo : vo.getClassifyInfoList()) {
            if (null != orderClassifyInfoVo.getClassifyNo() && 1 == orderClassifyInfoVo.getClassifyNo()) {
                orderClassifyInfoVo_base = orderClassifyInfoVo;
                for (RigthtsItemDetailVo rigthtsItemDetailVo : orderClassifyInfoVo.getItemDetailList()) {
                    if (null != rigthtsItemDetailVo.getItemNo()
                            && 8 == rigthtsItemDetailVo.getItemNo()) {
                        rigthtsItemDetailVo.setClassifyId(2);
                        vo.setLoan(rigthtsItemDetailVo);
                    } else if (null != rigthtsItemDetailVo.getItemNo()
                            && 9 == rigthtsItemDetailVo.getItemNo()) {
                        rigthtsItemDetailVo.setClassifyId(2);
                        vo.setCash(rigthtsItemDetailVo);
                    }
                }
                /*orderClassifyInfoVo.getItemDetailList().remove(vo.getLoan());
                orderClassifyInfoVo.getItemDetailList().remove(vo.getCash());*/
            }

        }
        // 把艾佳贷特性 和 现金特权 节点删除
        vo.getClassifyInfoList().remove(orderClassifyInfoVo_base);
        return vo;
    }


    private void setRightUsed(OrderRightsResultVo orderRightsResultVo) {
        List<RightsItemConsumeVo> rightsItemConsumeDtoList = orderRightsResultVo.getRightsItemConsumeDtoList();
        if (CollectionUtils.isEmpty(rightsItemConsumeDtoList)) {
            return;
        }

        for (RightsItemConsumeVo vo : rightsItemConsumeDtoList) {
            //  删除取消的记录
            removeCancelRightsItemConsume(vo);
            // 艾久久
            if (vo.getItemNo().equals(11)) {
                processAiJiuJiuRightsItemConsume(vo);
            }

            // 艾呼吸
            if (vo.getItemNo().equals(14)) {
                processAiHuXiRightsItemConsume(vo,orderRightsResultVo.getVersionFlag());
            }

            // 艾洁士
            if (vo.getItemNo().equals(15)) {
                processAiJieShiRightsItemConsume(vo);
            }

            // 艾先住
            if (vo.getItemId() == 16) {
                processAiXianZhuRightsItemConsume(vo);
            }
        }
    }

    private void processAiXianZhuRightsItemConsume(RightsItemConsumeVo vo) {
        // 艾先住
        AiXianzhuRewardDetailVo detail = vo.getAiXianzhuRewardDetailVo();
        if (null != detail) {
            try {
                if (null != detail.getBeginDate()) {
                    detail.setBeginDateStr(DateUtil.format(detail.getBeginDate(), DateUtil.FORMAT_SHORT_CN));
                }
                if (null != detail.getDeliverTime()) {
                    detail.setDeliverTimeStr(DateUtil.format(detail.getDeliverTime(), DateUtil.FORMAT_SHORT_CN));
                }
            } catch (Exception e) {
                LOGGER.error("right 艾先住 日期处理错误，{}", JsonUtils.obj2json(detail));
            }
        }
    }

    private void processAiJieShiRightsItemConsume(RightsItemConsumeVo vo) {
        // 艾洁士
        List<RightsConsumerRecordVo> consumerRecordVos = vo.getConsumerRecordVos();
        if (CollectionUtils.isEmpty(consumerRecordVos)) {
            return;
        }
        for (RightsConsumerRecordVo record : consumerRecordVos) {
            String dateStr = record.getConsumerTime();
            if (null != dateStr && !"".equals(dateStr)) {
                try {
                    Date beginDate = DateUtil.parse(dateStr, DateUtil.FORMAT_SHORT);
                    record.setConsumerTimeStr(DateUtil.format(beginDate, DateUtil.FORMAT_SHORT_CN));
                } catch (Exception e) {
                    LOGGER.error("right 艾洁士 日期处理错误，{}", JsonUtils.obj2json(record));
                }
            }
        }
    }

    private void processAiHuXiRightsItemConsume(RightsItemConsumeVo vo, Integer versionFlag) {
        // 艾呼吸
        List<RightsConsumerRecordVo> consumerRecordVos = vo.getConsumerRecordVos();
        if (CollectionUtils.isEmpty(consumerRecordVos)) {
            return;
        }
        RightsConsumerRecordVo record = consumerRecordVos.get(0);
        String dateStr = record.getConsumerTime();
        if (null != dateStr && !"".equals(dateStr)) {
            try {
                Date beginDate = DateUtil.parse(dateStr, DateUtil.FORMAT_SHORT);
                record.setConsumerTimeStr(DateUtil.format(beginDate, DateUtil.FORMAT_SHORT_CN));
                String days = vo.getItemRewardQuota();
                if (null != days && !"".equals(days)) {
                    int day = Integer.parseInt(days);
                    Date expireTime = DateUtil.addDay(beginDate, day);
                    record.setExpireTimeStr(DateUtil.format(expireTime, DateUtil.FORMAT_SHORT_CN));
                }
            } catch (Exception e) {
                LOGGER.error("right 艾呼吸 日期处理错误，{}", JsonUtils.obj2json(record));
            }
        }
    }

    private void processAiJiuJiuRightsItemConsume(RightsItemConsumeVo vo) {
        // 艾久久
        AiJiuJiuRewardDetailVo detail = vo.getAiJiuJiuRewardDetailVo();
        if (null != detail) {
            try {
                if (null != detail.getExpiryStartDate()) {
                    detail.setExpiryStartDateStr(DateUtil.format(detail.getExpiryStartDate(), DateUtil.FORMAT_SHORT_CN));
                }
                if (null != detail.getExpiryEndDate()) {
                    detail.setExpiryEndDateStr(DateUtil.format(detail.getExpiryEndDate(), DateUtil.FORMAT_SHORT_CN));
                }
            } catch (Exception e) {
                LOGGER.error("right 艾久久 日期处理错误，{}", JsonUtils.obj2json(detail));
            }
        }
    }

    private void removeCancelRightsItemConsume(RightsItemConsumeVo vo) {
        List<RightsConsumerRecordVo> consumerRecordVos = vo.getConsumerRecordVos();
        if (CollectionUtils.isEmpty(consumerRecordVos)) {
            return;
        }

        int i = 0;
        Iterator<RightsConsumerRecordVo> it = consumerRecordVos.iterator();
        while (it.hasNext()) {
            RightsConsumerRecordVo item = it.next();
            if (null != item && null != item.getStatus() && item.getStatus() == 3) {
                // 艾洁士最新的一条取消记录不删
                if (i != 0 || vo.getItemNo() != 15) {
                    it.remove();
                }
            }
            i++;
        }
    }

    /**
     * 给天降喜福分类下的艾升级项的 rightConsumerDesc 字段赋值
     *
     * @param vo OrderRightsResultVo
     * @return vo OrderRightsResultVo
     */
    private OrderRightsResultVo setRightConsumerDesc(OrderRightsResultVo vo) {

        // 查询权益已使用情况 根据订单id getOrderNum todo
        BigDecimal rightAmount = null;
        try {
            FamilyOrderPayResponse familyOrderPayResponse = orderService.queryPayBaseInfo(vo.getOrderNum());
            rightAmount = familyOrderPayResponse.getRightAmount();
        } catch (Exception e) {
            LOGGER.error("QueryMyOrderRightServiceImpl.setRightConsumerDesc exception , more info is : " + e);
            return vo;
        }
        //天降喜福分类的id  3  no 2
        final Integer classifyId = 3;
        // 艾升级权益项的id 10
        final Integer itemId = 10;
        //权益项已经使用标识
        final Integer consumerStatus = 4;
        for (OrderClassifyInfoVo orderClassifyInfoVo : vo.getClassifyInfoList()) {
            if (orderClassifyInfoVo.getClassifyId().equals(classifyId)||orderClassifyInfoVo.getClassifyNo().equals(4)) {
                for (RigthtsItemDetailVo rigthtsItemDetailVo : orderClassifyInfoVo.getItemDetailList()) {
                    // 权益id匹配上 且已经使用
                    if (rigthtsItemDetailVo.getItemNo().equals(itemId) &&
                            rigthtsItemDetailVo.getConsumeStatus() == consumerStatus
                            && null != rightAmount) {
                        rigthtsItemDetailVo.setRightConsumeDesc("已抵扣" + rightAmount.intValue() + "元");
                        break;
                    } else {
                        continue;
                    }
                }
            } else {
                continue;
            }
        }

        return vo;
    }


    /**
     * 降等之后如果已经消费的权益数大于可选的权益数
     * 则把剩下的权益项move 不返回给前端
     *
     * @param vo
     * @return
     */
    private OrderRightsResultVo clearMoreItem(OrderRightsResultVo vo) {
        // 已经消费的状态为4
        Integer status_had_consume = 4;
        for (OrderClassifyInfoVo orderClassifyInfoVo : vo.getClassifyInfoList()) {
            Integer count = 0;
            List<RigthtsItemDetailVo> notConsume = new ArrayList<>();
            for (RigthtsItemDetailVo rigthtsItemDetailVo : orderClassifyInfoVo.getItemDetailList()) {
                if (rigthtsItemDetailVo.getConsumeStatus().equals(status_had_consume)) {
                    count++;
                } else {
                    notConsume.add(rigthtsItemDetailVo);
                }
            }
            if (count >= orderClassifyInfoVo.getRightsConfirmedLimit() && notConsume.size() > 0) {
                for (RigthtsItemDetailVo item : notConsume) {
                    orderClassifyInfoVo.getItemDetailList().remove(item);
                }
            }
        }
        vo.getClassifyInfoList();
        return vo;
    }

    /**
     * 把已经消费的塞给classifyInfoList节点
     *
     * @param vo
     * @return
     */
    private OrderRightsResultVo hadUsed(OrderRightsResultVo vo) {
        // 普通等级的id是0
        final Integer grade_common = 0;
        final Integer list_size_eq_zero = 0;
        if (null != vo.getGradeId()
                && grade_common == vo.getGradeId()
                && null != vo.getCommonRightsUsedList()
                && vo.getCommonRightsUsedList().size() > list_size_eq_zero) {
            //情义无价
            OrderClassifyInfoVo orderClassifyInfoVo = new OrderClassifyInfoVo();
            orderClassifyInfoVo.setGradeId(vo.getGradeId());
            orderClassifyInfoVo.setGradeName("");
            orderClassifyInfoVo.setClassifyId(4);
            orderClassifyInfoVo.setVersion("20180910");
            orderClassifyInfoVo.setClassifyName("情义无价");
            orderClassifyInfoVo.setRightsConfigLimit(16);
            orderClassifyInfoVo.setRightsConfirmedLimit(0);
            // 降等级后情义无价被消费的权益
            orderClassifyInfoVo.setItemDetailList(vo.getCommonRightsUsedList());
            vo.getClassifyInfoList().add(orderClassifyInfoVo);
        }
        return vo;
    }

    /**
     * 副标题 枚举匹配
     *
     * @param item RightItemDto
     * @return subtitle
     */
    private String subtitle(RigthtsItemDetailVo item, Integer versionFlag) {
        String itemName = item.getItemName();
        String subtitle = null;
        switch (itemName) {
            case "艾升级":
                subtitle = item.getItemRewardQuota() + item.getItemRewardUnitStr();
                break;
            case "艾久久":
                subtitle = item.getItemRewardQuota() + item.getItemRewardUnitStr();
                break;
            case "艾艺术":
                subtitle = "价值" + item.getItemRewardQuota() + item.getItemRewardUnitStr();
                break;
            case "艾无忧":

                subtitle = (Integer.parseInt(item.getItemRewardQuota()) / NUMBER_TEN_THOUSAND) + "万" + item.getItemRewardUnitStr();
                break;
            case "艾呼吸":
                if (versionFlag.equals(1)) {
                    subtitle = item.getItemRewardQuota() + item.getItemRewardUnitStr() + "免租金";
                }
                break;
            case "艾洁士":
                subtitle = "共" + item.getItemRewardQuota() + item.getItemRewardUnitStr();
                break;
            case "艾先住":
                subtitle = item.getItemRewardQuota();
                break;
            case "艾前程":
                subtitle = "可推荐" + item.getItemRewardQuota() + "人";
                break;
            case "艾就业":
                subtitle = "小区大众监理" + item.getItemRewardQuota() + "人";
                break;
            case "艾聚会":
                subtitle = "年度生态大会";
                break;
            case "艾祝福":
                subtitle = "价值" + item.getItemRewardQuota() + item.getItemRewardUnitStr();
                break;
            case "艾乔迁":
                subtitle = item.getItemRewardQuota() + item.getItemRewardUnitStr();
                break;
            case "艾长者":
                subtitle = item.getItemRewardQuota() + item.getItemRewardUnitStr();
                break;
            case "艾小宝":
                subtitle = item.getItemRewardQuota() + item.getItemRewardUnitStr();
                break;
            case "艾焕新":
                subtitle = item.getItemRewardQuota() + item.getItemRewardUnitStr();
                break;
            case "艾维权":
                subtitle = item.getItemRewardQuota();
                break;
            case "艾佳游":
                subtitle = item.getItemRewardQuota() + item.getItemRewardUnitStr();
                break;
            case "艾艺星":
                subtitle = "小星星艺术节";
                item.getItemRewardUnitStr();
                break;
            case "艾扮家":
                subtitle = "参加创意大赛";
                break;
            case "艾订制":
                subtitle = "个性软饰设计";
                break;
            case "艾学习":
                subtitle = item.getItemRewardQuota() + item.getItemRewardUnitStr() + "无敌券";
                break;
            case "艾援助":
                subtitle = item.getItemRewardQuota();
                break;
            case "艾留念":
                subtitle = "全家福留念";
                break;
            default:
                subtitle = "";
        }
        return subtitle;
    }

    /**
     * 时间往前推3天
     *
     * @param vo
     * @return
     */
    private OrderRightsResultVo dateBefore3Day(OrderRightsResultVo vo) {

        //提前3天
        final int date_three = -3;
        List<GradeNodeVo> upgradableList = new ArrayList<>();
        List<GradeNodeVo> upgradableListReturn = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(vo.getNodeDtoList())) {
            for (GradeNodeVo gradeNodeVo : vo.getNodeDtoList()) {
                // 获得当前等级截止时间")
                String getCurrentNodeTimeStr = gradeNodeVo.getGetCurrentNodeTimeStr();
                try {
                    getCurrentNodeTimeStr = plusDay(date_three, getCurrentNodeTimeStr);
                    gradeNodeVo.setGetCurrentNodeTimeStr(getCurrentNodeTimeStr);
                } catch (RuntimeException re) {
                    LOGGER.error("QueryMyOrderRightServiceImpl.dateBefore3Day exception , more info : " + re);
                    continue;
                }
            }
            if(vo.getFundAmount().compareTo(BigDecimal.ZERO)!=0){
                for (GradeNodeVo gradeNodeVo : vo.getNodeDtoList()) {

                        if(vo.getGradeId()==0 && gradeNodeVo.getGradeId()>0 && compareDate(gradeNodeVo.getGetCurrentNodeTimeStr())){//当前为普通权益
                            gradeNodeVo.setGradeLevelIcoUrl(RightLevelNewEnum.getGradeLevelIcoUrl(gradeNodeVo.getGradeId()));
                            upgradableList.add(gradeNodeVo);
                        }
                        if(vo.getGradeId()==5 && gradeNodeVo.getGradeId()!=0 && gradeNodeVo.getGradeId()!=5 && compareDate(gradeNodeVo.getGetCurrentNodeTimeStr())){//当前为青铜权益
                            gradeNodeVo.setGradeLevelIcoUrl(RightLevelNewEnum.getGradeLevelIcoUrl(gradeNodeVo.getGradeId()));
                            upgradableList.add(gradeNodeVo);
                        }
                        if(vo.getGradeId()==1 && (gradeNodeVo.getGradeId() == 2 || gradeNodeVo.getGradeId() == 3) && compareDate(gradeNodeVo.getGetCurrentNodeTimeStr())){//当前为黄金权益
                            gradeNodeVo.setGradeLevelIcoUrl(RightLevelNewEnum.getGradeLevelIcoUrl(gradeNodeVo.getGradeId()));
                            upgradableList.add(gradeNodeVo);
                        }
                        if(vo.getGradeId()==2 && gradeNodeVo.getGradeId() == 3 && compareDate(gradeNodeVo.getGetCurrentNodeTimeStr())){//当前为铂金权益
                            gradeNodeVo.setGradeLevelIcoUrl(RightLevelNewEnum.getGradeLevelIcoUrl(gradeNodeVo.getGradeId()));
                            upgradableList.add(gradeNodeVo);
                        }
                        if(vo.getGradeId()==4 && gradeNodeVo.getGradeId()!=0 && gradeNodeVo.getGradeId()!=5 && compareDate(gradeNodeVo.getGetCurrentNodeTimeStr())){//当前为白银权益
                            gradeNodeVo.setGradeLevelIcoUrl(RightLevelNewEnum.getGradeLevelIcoUrl(gradeNodeVo.getGradeId()));
                            upgradableList.add(gradeNodeVo);
                        }
                    }
                }

        }
        if(CollectionUtils.isNotEmpty(upgradableList)){
            for(GradeNodeVo gradeNodeVo : upgradableList){
                if(gradeNodeVo.getGradeId() == RightLevelNewEnum.LEVEL_FOR.getCode()){
                    upgradableListReturn.add(gradeNodeVo);
                }
            }
            for(GradeNodeVo gradeNodeVo : upgradableList){
                if(gradeNodeVo.getGradeId() == RightLevelNewEnum.LEVEL_THREE.getCode()){
                    upgradableListReturn.add(gradeNodeVo);
                }
            }
            for(GradeNodeVo gradeNodeVo : upgradableList){
                if(gradeNodeVo.getGradeId() == RightLevelNewEnum.LEVEL_TWO.getCode()){
                    upgradableListReturn.add(gradeNodeVo);
                }
            }
            for(GradeNodeVo gradeNodeVo : upgradableList){
                if(gradeNodeVo.getGradeId() == RightLevelNewEnum.LEVEL_FIV.getCode()){
                    upgradableListReturn.add(gradeNodeVo);
                }
            }
            for(GradeNodeVo gradeNodeVo : upgradableList){
                if(gradeNodeVo.getGradeId() == RightLevelNewEnum.LEVEL_SIX.getCode()){
                    upgradableListReturn.add(gradeNodeVo);
                }
            }
        }
        vo.setUpgradableList(upgradableListReturn);
        if(CollectionUtils.isNotEmpty(upgradableListReturn)){
            vo.setUpgradable(true);
        }
        return vo;
    }

    /**
     * 比较日期和当前时间
     * @param date
     * @return
     */
    private boolean compareDate(String date){
        boolean result = false;
        try {
            result=DateUtils.parseDate(date, DateUtil.FORMAT_SHORT).getTime()>=
                    (DateUtils.parseDate( DateFormatUtils.format(new Date(),DateUtil.FORMAT_SHORT), DateUtil.FORMAT_SHORT)).getTime();
        } catch (ParseException e) {
            LOGGER.info("Date Format error!");
        }
        return result;
    }


    /**
     * 时间操作 num 为时间变化的量 整数表示加num 天 负数表示 减少num天
     *
     * @param num  变化的量
     * @param date 需要操作的时间 必须为yyyy-MM-dd
     * @return 变化后的时间
     * @throws ParseException 时间转换异常
     */
    private String plusDay(int num, String date) throws RuntimeException {
        if (null == date) {
            throw new RuntimeException("QueryMyOrderRightServiceImpl.plusDay param is null . ");
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat(DateUtil.FORMAT_SHORT);
            Date currentDate = format.parse(date);
            Calendar ca = Calendar.getInstance();
            ca.setTime(currentDate);
            ca.add(Calendar.DATE, num);// num为增加的天数，可以改变的
            currentDate = ca.getTime();
            return format.format(currentDate);
        } catch (Exception e) {
            throw new RuntimeException("format date exception , more insomnia is :" + e);
        }
    }

    /**
     *
     * @param vo
     * @return
     */
//    private OrderRightsResultVo assembleRightInfoList(OrderRightsResultVo vo) {
//        OrderClassifyInfoVo orderClassifyInfoVo_base = null;
//        Integer itemCount = 0;
//        // 遍历权益分类
//        for (OrderClassifyInfoVo orderClassifyInfoVo : vo.getClassifyInfoList()) {
//            // 对分类下的权益项进行排序
//            orderClassifyInfoVo.setItemDetailList(getSortList(orderClassifyInfoVo.getItemDetailList()));
//
//            // 计算权益项个数
//            if (orderClassifyInfoVo.getClassifyId() == 3 ||
//                    orderClassifyInfoVo.getClassifyId() == 4) {
//                itemCount += orderClassifyInfoVo.getRightsConfirmedLimit();
//            }
//
//            // 切图和拼接subtitle
//            for (RigthtsItemDetailVo rigthtsItemDetailVo : orderClassifyInfoVo.getItemDetailList()) {
//                String url = rigthtsItemDetailVo.getUrl();
//                // 切图
//                url = QiniuImageUtils.compressImageAndSamePicTwo(url, IMAGE_SIZE_ICON, IMAGE_SIZE_ICON);
//                rigthtsItemDetailVo.setUrl(url);
//                //设置subtitle
//                rigthtsItemDetailVo.setSubtitle(subtitle(rigthtsItemDetailVo, vo.getVersionFlag()));
//            }
//
//            // 艾佳贷和现金权益项节点 单独放到OrderRightsResultVo 下
//            if (null != orderClassifyInfoVo.getClassifyId() && 2 == orderClassifyInfoVo.getClassifyId()) {
//                orderClassifyInfoVo_base = orderClassifyInfoVo;
//                for (RigthtsItemDetailVo rigthtsItemDetailVo : orderClassifyInfoVo.getItemDetailList()) {
//                    if (null != rigthtsItemDetailVo.getItemId()
//                            && 8 == rigthtsItemDetailVo.getItemId()) {
//                        rigthtsItemDetailVo.setClassifyId(2);
//                        vo.setLoan(rigthtsItemDetailVo);
//                    } else if (null != rigthtsItemDetailVo.getItemId()
//                            && 9 == rigthtsItemDetailVo.getItemId()) {
//                        rigthtsItemDetailVo.setClassifyId(2);
//                        vo.setCash(rigthtsItemDetailVo);
//                    }
//                }
//            }
//        }
//
//        if (null != vo.getGradeId() && vo.getGradeId() > 0) {
//            //现金补贴项目
//            itemCount++;
//        }
//        vo.setItemCount(itemCount);
//
//        // 把艾佳贷特性 和 现金特权 节点删除
//        vo.getClassifyInfoList().remove(orderClassifyInfoVo_base);
//        return vo;
//    }

    /**
     * 对 RigthtsItemDetailVo 排序
     * 根据 coonsumeStatus
     *
     * @param list
     * @return
     */
    private List<RigthtsItemDetailVo> getSortList(List<RigthtsItemDetailVo> list) {
        Collections.sort(list, new Comparator<RigthtsItemDetailVo>() {
            @Override
            public int compare(RigthtsItemDetailVo o1, RigthtsItemDetailVo o2) {
                if (o1.getConsumeStatus() < o2.getConsumeStatus()) {
                    return 1;
                }
                if (o1.getConsumeStatus() == o2.getConsumeStatus()) {
                    return 0;
                }
                return -1;
            }
        });
        return list;
    }


}
