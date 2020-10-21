package com.ihomefnt.o2o.service.service.dna;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.ihomefnt.common.concurrent.IdentityTaskAction;
import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import com.ihomefnt.o2o.intf.domain.dna.dto.*;
import com.ihomefnt.o2o.intf.domain.dna.vo.request.QuotePriceRequest;
import com.ihomefnt.o2o.intf.domain.dna.vo.response.DecorationProcessResponse;
import com.ihomefnt.o2o.intf.domain.dna.vo.response.QuotePriceInfoResponse;
import com.ihomefnt.o2o.intf.domain.dna.vo.response.QuotePriceResponse;
import com.ihomefnt.o2o.intf.domain.programorder.dto.OrderDetailDto;
import com.ihomefnt.o2o.intf.domain.style.vo.response.StyleCommitRecordResponse;
import com.ihomefnt.o2o.intf.domain.user.dto.UserDto;
import com.ihomefnt.o2o.intf.manager.concurrent.ConcurrentTaskEnum;
import com.ihomefnt.o2o.intf.manager.concurrent.Executor;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.image.AliImageUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageConstant;
import com.ihomefnt.o2o.intf.proxy.designdemand.StyleQuestionAnwserProxy;
import com.ihomefnt.o2o.intf.proxy.dna.DecorationQuotationProxy;
import com.ihomefnt.o2o.intf.proxy.order.OrderProxy;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.service.dna.DecorationQuotationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wanyunxin
 * @create 2019-11-18 11:13
 */
@Slf4j
@Service
public class DecorationQuotationServiceImpl implements DecorationQuotationService {

    @Autowired
    private DecorationQuotationProxy decorationQuotationProxy;


    @Autowired
    OrderProxy orderProxy;

    /**
     * 装修报价页面图片
     */
    @NacosValue(value = "${app.quote.price.image}", autoRefreshed = true)
    private String QUOTE_PRICE_IMAGE;

    @NacosValue(value = "${app.quote.price.bg.image}", autoRefreshed = true)
    private String QUOTE_PRICE_BG_IMAGE;




    /**
     * 装修历程
     */
    @NacosValue(value = "${app.decoration.process.dto}", autoRefreshed = true)
    private String appDecorationProcessDto;



    @NacosValue(value = "${app.decoration.process.detail}", autoRefreshed = true)
    private String appDecorationProcessDetail;


    private static List styleList= Arrays.asList("现代","美式","欧式","中式");



    /**
     * 装修报价信息查询
     * @param request
     * @return
     */
    @Override
    public QuotePriceInfoResponse queryQuotePriceInfo(HttpBaseRequest request) {
        Integer count = decorationQuotationProxy.queryQuotePriceCount();
        return new QuotePriceInfoResponse()
                .setImageUrl(AliImageUtil.imageCompress(QUOTE_PRICE_IMAGE,request.getOsType(),request.getWidth(), ImageConstant.SIZE_SMALL))
                .setBgImageUrl(AliImageUtil.imageCompress(QUOTE_PRICE_BG_IMAGE,request.getOsType(),request.getWidth(), ImageConstant.SIZE_SMALL))
                .setQuotePriceCount(count==null?0:count);
    }

    /**
     * 获取装修报价
     * @param request
     * @return
     */
    @Override
    public QuotePriceResponse queryQuotePrice(QuotePriceRequest request) {
        if(request ==null || request.getArea() ==null || request.getArea().compareTo(BigDecimal.ZERO)==0){
            throw new BusinessException(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        request.setUserId(request.getUserInfo().getId());
        //获取装修报价
        List<QuotePriceResponse.QuotePriceDetailBean> quotePriceDetailList = queryDNAPriceInfo(request);
        if(CollectionUtils.isEmpty(quotePriceDetailList)){
            throw new BusinessException("获取装修报价失败！");
        }
        Map<String, Object> resultMap = concurrentOrderAndQuotePriceRecord(request);
        OrderDetailDto orderDetailDto = (OrderDetailDto) resultMap.get(ConcurrentTaskEnum.QUERY_ORDER_SUMMARY_INFO.name());
        QuotePriceRecordDto quotePriceRecord = new QuotePriceRecordDto();
        if(orderDetailDto!=null){
            quotePriceRecord.setOrderStatus(orderDetailDto.getOrderStatus());
        }
        quotePriceRecord.setMobile(request.getMobileNum());
        quotePriceRecord.setOrderNum(request.getOrderNum());
        quotePriceRecord.setUserId(request.getUserInfo().getId());
        quotePriceRecord.setQuotePriceResult(JsonUtils.obj2json(quotePriceDetailList));
        //装修报价落库
        Integer quotePriceId =decorationQuotationProxy.addQuotePriceRecord(quotePriceRecord);
        QuotePriceResponse quotePriceResponse = new QuotePriceResponse();
        quotePriceResponse.setQuotePriceDetail(quotePriceDetailList);
        quotePriceResponse.setMinDnaExpectPrice(quotePriceDetailList.stream().map(i->i.getDnaExpectPrice()).min(BigDecimal::compareTo).orElse(BigDecimal.ZERO));
        quotePriceResponse.setQuotePriceId(quotePriceId);
        return quotePriceResponse;
    }


    /**
     * 获取dna价格列表
     * @param request
     * @return
     */
    private List<QuotePriceResponse.QuotePriceDetailBean> queryDNAPriceInfo(QuotePriceRequest request){
        //获取装修报价
        List<DnaPriceInfoDto> quotePriceDetailList = decorationQuotationProxy.queryRecommendDnaList(request);

        if(CollectionUtils.isEmpty(quotePriceDetailList)){
            return new ArrayList<>();
        }
        List<QuotePriceResponse.QuotePriceDetailBean> list = new ArrayList<>(4);
        for (DnaPriceInfoDto dnaPriceInfoDto : quotePriceDetailList) {
            QuotePriceResponse.QuotePriceDetailBean quotePriceDetailBean = new QuotePriceResponse.QuotePriceDetailBean();
            if(CollectionUtils.isNotEmpty(dnaPriceInfoDto.getList())){//具体数据不为空
                Collections.sort(dnaPriceInfoDto.getList(), Comparator.comparing(DnaPriceInfoDto.ListBean::getDnaAvgPrice));
                DnaPriceInfoDto.ListBean minListBean = dnaPriceInfoDto.getList().get(0);
                quotePriceDetailBean.setStyleName(dnaPriceInfoDto.getDnaStyleName())
                        .setSeriesName(minListBean.getDnaSeriesName())
                        .setHeadImgUrl(AliImageUtil.imageCompress(minListBean.getHeadImgUrl(),request.getOsType(),request.getWidth(), ImageConstant.SIZE_MIDDLE))
                        .setDnaType(0)
                        .setDnaName(minListBean.getDnaName())
                        .setDnaId(minListBean.getDnaId())
                        .setDnaExpectPrice(minListBean.getDnaAvgPrice().multiply(request.getArea()).setScale(0, BigDecimal.ROUND_DOWN));
                list.add(quotePriceDetailBean);
            }
        }
        if(CollectionUtils.isNotEmpty(list)){
            setListOrder(styleList,list);
        }
        return list;

    }

    public void setListOrder(List<String> colorList,  List<QuotePriceResponse.QuotePriceDetailBean> list) {
        Collections.sort(list, ((o1, o2) -> {
            int io1 = colorList.indexOf(o1.getStyleName());
            int io2 = colorList.indexOf(o2.getStyleName());

            if (io1 != -1) {
                io1 = list.size() - io1;
            }
            if (io2 != -1) {
                io2 = list.size() - io2;
            }

            return io2 - io1;
        }));
    }


    /**
     * 获取装修历程
     * @param request
     * @return
     */
    @Override
    @SuppressWarnings("all")
    public DecorationProcessResponse queryDecorationProcess(QuotePriceRequest request) {
        DecorationProcessResponse decorationProcessResponse =  new DecorationProcessResponse();
        if(request==null || request.getUserInfo()==null){
            throw new BusinessException(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        request.setUserId(request.getUserInfo().getId());


        List<DecorationProcessDto> decorationProcessDtoList = getMainDecorationProcess(request);
        //装修历程通配数据
        List<DecorationProcessDto> appDecorationProcessDetailList = com.ihomefnt.common.util.JsonUtils.json2list(appDecorationProcessDetail, DecorationProcessDto.class);

        List<DecorationProcessResponse.MasterNodeListBean> appDecorationProcessList = com.ihomefnt.common.util.JsonUtils.json2list(appDecorationProcessDto, DecorationProcessResponse.MasterNodeListBean.class);
        if(CollectionUtils.isNotEmpty(decorationProcessDtoList)&& CollectionUtils.isNotEmpty(appDecorationProcessList)){//wcm查询数据不为空
            appDecorationProcessList.forEach(masterNodeListBean -> {
                List<DecorationProcessDto> collect = decorationProcessDtoList.stream().filter(decorationProcessDto -> decorationProcessDto!=null && decorationProcessDto.getMasterNodeId()!=null && decorationProcessDto.getMasterNodeId().equals(masterNodeListBean.getMasterNodeId())).collect(Collectors.toList());
                if(CollectionUtils.isNotEmpty(collect)){
                    List<DecorationProcessResponse.MasterNodeListBean.EventNodeListBean> eventNodeListBeans = JsonUtils.json2list(com.ihomefnt.o2o.intf.manager.util.common.bean.JsonUtils.obj2json(collect),
                            DecorationProcessResponse.MasterNodeListBean.EventNodeListBean.class);
                    if(CollectionUtils.isNotEmpty(appDecorationProcessDetailList)){
                        eventNodeListBeans.forEach(decorationProcessDto -> appDecorationProcessDetailList.forEach(decorationProcessDto1 -> {
                            if(decorationProcessDto.getEventNodeId().equals(decorationProcessDto1.getEventNodeId())){
                                if(decorationProcessDto.getEventContent()==null){
                                    decorationProcessDto.setEventContent(decorationProcessDto1.getEventContent().replace("$",""));
                                }else{
                                    decorationProcessDto.setEventContent(decorationProcessDto1.getEventContent().replace("$",decorationProcessDto.getEventContent()));
                                }
                                decorationProcessDto.setEventNode(decorationProcessDto1.getEventNode());
                            }
                        }));
                    }
                    Collections.sort(eventNodeListBeans, Comparator.comparing(DecorationProcessResponse.MasterNodeListBean.EventNodeListBean::getCreateTime).reversed());
                    masterNodeListBean.setEventNodeList(eventNodeListBeans);
                }
            });
        }
        int fousNode =0;
        for (int i = appDecorationProcessList.size()-1; i >=0 ; i--) {
            if(CollectionUtils.isNotEmpty(appDecorationProcessList.get(i).getEventNodeList())){
                if(fousNode == 0){
                    appDecorationProcessList.get(i).setMasterStatus(2);
                    fousNode++;
                }else{
                    appDecorationProcessList.get(i).setMasterStatus(1);
                }
            }
        }
        decorationProcessResponse.setMasterNodeList(appDecorationProcessList);
        return decorationProcessResponse;
    }

    /**
     * 组装主节点信息
     * @param request
     */
    @SuppressWarnings("all")
    private List<DecorationProcessDto> getMainDecorationProcess(QuotePriceRequest request){
        Map<String, Object> resultMap = concurrentQueryDecorationProcess(request);
        //wcm数据库查询数据
        List<DecorationProcessDto> decorationProcessDtoList =  (List<DecorationProcessDto>) resultMap.get(ConcurrentTaskEnum.QUERY_DECORATION_PROCESS.name());
        if(decorationProcessDtoList==null){
            decorationProcessDtoList =  new ArrayList<>();
        }
        UserDto userDto =  (UserDto) resultMap.get(ConcurrentTaskEnum.QUERY_USER_BY_TOKEN.name());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleDateFormatShort = new SimpleDateFormat("yyyy-MM-dd");
        DecorationProcessDto userProcessDto = new DecorationProcessDto().setMasterNodeId(1).setEventNodeId(1).setCreateTime(simpleDateFormat.format(userDto.getCreateTime()));//初始化用户节点
        decorationProcessDtoList.add(userProcessDto);
        OrderTimeDto orderTimeDto =  (OrderTimeDto) resultMap.get(ConcurrentTaskEnum.QUERY_ORDER_TIME.name());
        if(orderTimeDto!=null){
            if(orderTimeDto.getCreateTime()!=null){//初始化添加房产
                DecorationProcessDto houstInfoProcessDto = null;
                houstInfoProcessDto = new DecorationProcessDto().setMasterNodeId(2).setEventNodeId(2).setCreateTime(orderTimeDto.getCreateTime());
                decorationProcessDtoList.add(houstInfoProcessDto);
            }
            if(orderTimeDto.getPaySureMoneyTime()!=null){//初始化缴纳定金
                DecorationProcessDto houstInfoProcessDto = null;
                houstInfoProcessDto = new DecorationProcessDto().setMasterNodeId(2).setEventNodeId(3).setCreateTime(orderTimeDto.getPaySureMoneyTime());
                decorationProcessDtoList.add(houstInfoProcessDto);
            }
            if(orderTimeDto.getCommitSignTime()!=null){//提交签约时间
                DecorationProcessDto subProcessDto = null;
                subProcessDto = new DecorationProcessDto().setMasterNodeId(2).setEventNodeId(8)
                        .setCreateTime(orderTimeDto.getCommitSignTime())
                        .setEventContent(orderTimeDto.getSolutionName());
                addDecorationProcessDto(decorationProcessDtoList,subProcessDto,8);
            }
            if(orderTimeDto.getPayFullMoneyTime()!=null){//确认签约方案
                DecorationProcessDto subProcessDto = null;
                subProcessDto = new DecorationProcessDto().setMasterNodeId(2).setEventNodeId(10)
                            .setCreateTime(orderTimeDto.getPayFullMoneyTime())
                            .setEventContent(orderTimeDto.getSolutionName());
                addDecorationProcessDto(decorationProcessDtoList,subProcessDto,10);
            }
        }
        DeliveryTimeDto deliveryTimeDto =  (DeliveryTimeDto) resultMap.get(ConcurrentTaskEnum.QUERY_DELIVERY_TIME.name());

        if(deliveryTimeDto!=null){
            if(deliveryTimeDto.getConfirmRequirementDate()!=null){
                DecorationProcessDto deliveryProcessDto = new DecorationProcessDto().setMasterNodeId(3).setEventNodeId(12)
                        .setCreateTime(simpleDateFormat.format(deliveryTimeDto.getConfirmRequirementDate()));
                decorationProcessDtoList.add(deliveryProcessDto);
            }
            if(deliveryTimeDto.getBeginDate()!=null){
                DecorationProcessDto deliveryProcessDto = new DecorationProcessDto().setMasterNodeId(3).setEventNodeId(13)
                        .setCreateTime(simpleDateFormat.format(deliveryTimeDto.getBeginDate()));
                decorationProcessDtoList.add(deliveryProcessDto);
            }
            if(deliveryTimeDto.getGuaranteeDate()!=null){
                DecorationProcessDto deliveryProcessDto = new DecorationProcessDto().setMasterNodeId(4).setEventNodeId(17)
                        .setCreateTime(simpleDateFormatShort.format(deliveryTimeDto.getGuaranteeDate()));
                decorationProcessDtoList.add(deliveryProcessDto);
            }
        }
        List<StyleCommitRecordResponse> styleCommitRecordList =  (List<StyleCommitRecordResponse>) resultMap.get(ConcurrentTaskEnum.QUERY_STYLE_COMMIT_RECORD_LIST.name());
        if(CollectionUtils.isNotEmpty(styleCommitRecordList)){
            DecorationProcessDto styleCommitRecordDto = new DecorationProcessDto().setMasterNodeId(2).setEventNodeId(4)
                    .setCreateTime(simpleDateFormat.format(styleCommitRecordList.get(0).getCreateTime()));
            decorationProcessDtoList.add(styleCommitRecordDto);
        }

        return decorationProcessDtoList;
    }

    /**
     * 节点数据放置
     * @param decorationProcessDtoList
     * @param subProcessDto
     * @param eventNodeId
     */
    private void addDecorationProcessDto(List<DecorationProcessDto> decorationProcessDtoList,DecorationProcessDto subProcessDto,Integer eventNodeId){
        if(CollectionUtils.isEmpty(decorationProcessDtoList)){
            decorationProcessDtoList.add(subProcessDto);
        }else{
            boolean addFlag=true;
            for (DecorationProcessDto decorationProcessDto : decorationProcessDtoList) {
                if(eventNodeId.equals(decorationProcessDto.getEventNodeId()) && getTime(decorationProcessDto.getCreateTime(),subProcessDto.getCreateTime())){
                    addFlag =false;
                }
            }
            if(addFlag){
                decorationProcessDtoList.add(subProcessDto);
            }
        }
    }


    /**
     * 判断时间差是否小于50秒
     * @param oldTime
     * @param newTime
     * @return
     */
    public  boolean getTime(String oldTime,String newTime)  {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long NTime = 0;
        try {
            NTime = df.parse(newTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long OTime = 0;
        try {
            OTime = df.parse(oldTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long diff=(NTime-OTime)/1000;
        return -50<diff&& diff<50;
    }


    @Autowired
    UserProxy userProxy;

    @Autowired
    private StyleQuestionAnwserProxy styleQuestionAnwserProxy;

    /**
     * 主信息数据查询组装
     * @param request
     * @return
     */
    private Map<String, Object> concurrentQueryDecorationProcess(QuotePriceRequest request) {

        List<IdentityTaskAction<Object>> queryTasks = new ArrayList<>(5);

        //获取wcm装修历程信息
        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                try {
                    return  decorationQuotationProxy.queryDecorationProcess(request);
                }catch (Exception e){
                    log.error("queryDecorationProcess error",e);
                }

                return new ArrayList<DecorationProcessDto>();
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_DECORATION_PROCESS.name();
            }
        });

        //获取用户信息
        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return userProxy.getUserByToken(request.getAccessToken());
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_USER_BY_TOKEN.name();
            }
        });

        // 获取订单时间信息
        queryTasks.add(new IdentityTaskAction<Object>() {

            @Override
            public Object doInAction() {
                try{
                    return decorationQuotationProxy.queryOrderTime(request);
                }catch (Exception e){
                    log.error("queryOrderTime error",e);
                }
                return new OrderTimeDto();
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_ORDER_TIME.name();
            }
        });

        // 获取交付时间信息
        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                try{
                    return decorationQuotationProxy.queryDeliveryTime(request);
                }catch (Exception e){
                    log.error("queryDeliveryTime error",e);
                }
                return new DeliveryTimeDto();
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_DELIVERY_TIME.name();
            }
        });

        //设计任务提交记录
        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return styleQuestionAnwserProxy.queryStyleCommitRecordList(request.getOrderNum());
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_STYLE_COMMIT_RECORD_LIST.name();
            }
        });


        return Executor.getServiceConcurrentQueryFactory().executeIdentityTask(queryTasks);
    }



    private Map<String, Object> concurrentOrderAndQuotePriceRecord(QuotePriceRequest request) {

        List<IdentityTaskAction<Object>> queryTasks = new ArrayList<>(3);

        //获取订单信息
        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                return orderProxy.queryOrderSummaryInfo(request.getOrderNum());
            }

            @Override
            public String identity() {
                return ConcurrentTaskEnum.QUERY_ORDER_SUMMARY_INFO.name();
            }
        });

        //增加报价记录个数
        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                decorationQuotationProxy.addQuotePriceCount();
                return 1;
            }

            @Override
            public String identity() {
                return "addQuotePriceCount";
            }
        });

        //预约全品家-发送给小艾
        queryTasks.add(new IdentityTaskAction<Object>() {
            @Override
            public Object doInAction() {
                 decorationQuotationProxy.addOrderFamilyRecord(request);
                 return null;
            }

            @Override
            public String identity() {
                return "addOrderFamilyRecord";
            }
        });

        return Executor.getServiceConcurrentQueryFactory().executeIdentityTask(queryTasks);
    }
}
