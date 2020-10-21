package com.ihomefnt.o2o.service.service.shareorder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Maps;
import com.ihomefnt.common.util.DateUtils;
import com.ihomefnt.o2o.intf.domain.common.http.*;
import com.ihomefnt.o2o.intf.domain.dic.dto.DicDto;
import com.ihomefnt.o2o.intf.domain.dic.dto.DicListDto;
import com.ihomefnt.o2o.intf.domain.program.dto.HouseInfoResponseVo;
import com.ihomefnt.o2o.intf.domain.program.dto.ImageEntity;
import com.ihomefnt.o2o.intf.domain.push.dto.AppPushDto;
import com.ihomefnt.o2o.intf.domain.push.dto.JpushParamDto;
import com.ihomefnt.o2o.intf.domain.shareorder.dto.*;
import com.ihomefnt.o2o.intf.domain.shareorder.vo.request.*;
import com.ihomefnt.o2o.intf.domain.shareorder.vo.response.BuildingtopicViewResponse;
import com.ihomefnt.o2o.intf.domain.shareorder.vo.response.ShareOrderDetailResponse;
import com.ihomefnt.o2o.intf.domain.shareorder.vo.response.UserHouseInfoResponse;
import com.ihomefnt.o2o.intf.domain.user.dto.MemberDto;
import com.ihomefnt.o2o.intf.domain.user.dto.UserDto;
import com.ihomefnt.o2o.intf.manager.constant.RedisKey;
import com.ihomefnt.o2o.intf.manager.constant.common.StaticResourceConstants;
import com.ihomefnt.o2o.intf.manager.constant.home.HomeCardPraise;
import com.ihomefnt.o2o.intf.manager.constant.program.ProductProgramPraise;
import com.ihomefnt.o2o.intf.manager.constant.push.PushConstant;
import com.ihomefnt.o2o.intf.manager.constant.shareorder.BuildingtopicTypeEnum;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.ihomefnt.o2o.intf.manager.util.common.bean.StringUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.AliImageUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageConstant;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageSize;
import com.ihomefnt.o2o.intf.manager.util.common.image.QiniuImageUtils;
import com.ihomefnt.o2o.intf.proxy.ajb.AjbProxy;
import com.ihomefnt.o2o.intf.proxy.dic.DicProxy;
import com.ihomefnt.o2o.intf.proxy.program.ProductProgramProxy;
import com.ihomefnt.o2o.intf.proxy.push.AppPushProxy;
import com.ihomefnt.o2o.intf.proxy.shareorder.ShareOrderProxy;
import com.ihomefnt.o2o.intf.proxy.shareorder.TransferProxy;
import com.ihomefnt.o2o.intf.proxy.user.LogProxy;
import com.ihomefnt.o2o.intf.proxy.user.UserProxy;
import com.ihomefnt.o2o.intf.service.house.HouseService;
import com.ihomefnt.o2o.intf.service.push.PushService;
import com.ihomefnt.o2o.intf.service.shareorder.ShareOrderService;
import com.ihomefnt.o2o.intf.service.user.UserService;
import com.ihomefnt.o2o.service.service.common.CommentAdminService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by onefish on 2016/11/3 0003.
 */
@Service
public class ShareOrderServiceImpl implements ShareOrderService {

    private final static Logger logger = LoggerFactory.getLogger(ShareOrderServiceImpl.class);

    // 每位用户最多能获取的晒单艾积分奖励次数
    private final static int REWARD_LIMIT = 20;

    // 每次晒单获取的艾积分数额
    private final static int REWARD_AMOUNT = 200;

    @Autowired
    private ShareOrderProxy shareOrderDao;

    @Autowired
    private AjbProxy ajbRechargeProxy;

    @Autowired
    private ProductProgramProxy productProgramProxy;

    @Autowired
    private PushService pushService;

    @Autowired
    UserProxy userProxy;

    @Autowired
    private CommentAdminService commentAdminService;

    @Autowired
    DicProxy dicProxy;

    @Value("${host}")
    public String host;

    @Autowired
    UserService userService;

    @Autowired
    LogProxy logProxy;

    @Autowired
    TransferProxy transferProxy;
    @Autowired
    HouseService houseService;

    @Autowired
    private AppPushProxy appPushProxy;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public HttpBasePageResponse<ShareOrder> getShareOrderList(HttpShareOrderRequest shareOrderRequest) {
        if (null == shareOrderRequest) {
            throw new BusinessException(MessageConstant.DATA_TRANSFER_EMPTY);
        }
        if (shareOrderRequest.getQuerySource().equals("lifeHomePage")) {
            String cacheData = stringRedisTemplate.opsForValue().get(RedisKey.LifePlate.LIFE_HOME_PAGE_SHARE_ORDER_CACHE);
            if (StringUtils.isNotBlank(cacheData)) {
                return JSON.parseObject(cacheData, new TypeReference<HttpBasePageResponse<ShareOrder>>() {
                });
            }
        }
        HttpBasePageResponse<ShareOrder> basePageResponse = new HttpBasePageResponse<>();
        basePageResponse.setPageSize(shareOrderRequest.getLimit());
        basePageResponse.setPageNo(shareOrderRequest.getPage());
        HttpUserInfoRequest userDto = shareOrderRequest.getUserInfo();
        Integer userId = null;
        String mobile = null;
        if (userDto != null) {
            userId = userDto.getId();
            mobile = userDto.getMobile();
        }
        /**
         * null 表示所有,0 晒家,1 楼盘运营
         */
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("type", 0);
        /**
         * http://wiki.ihomefnt.com:8002/pages/viewpage.action?pageId=9568311<br/>
         * 1.未登录,只能看见审批通过 <br/>
         * 2.登录后,特需用户什么都可以看 -- 走老流程就可以 <br/>
         * 3.登录后,普通用户只能看 审批通过和 自己发布并且未审批和审批不通过 <br/>
         */
        Integer checkFlag = null;
        // step 1 :判断是不是特需用户
        boolean superuserTag = this.getSuperUserTag(mobile);
        if (StringUtils.isBlank(mobile)) {
            checkFlag = 1;
        } else {
            if (superuserTag) {
                checkFlag = 2;
            } else {
                checkFlag = 3;
                params.put("userId", userId);
            }
        }
        params.put("pageNo", shareOrderRequest.getPage());
        params.put("pageSize", shareOrderRequest.getLimit());
        params.put("checkFlag", checkFlag);
        // 只查询主表
        PageResponse<TransferDto> page = transferProxy.getTransferDtoList(params);
        List<TransferDto> list = page.getList();
        List<String> shareIdList = new ArrayList<String>();
        Map<String, Integer> sortMap = new HashMap<String, Integer>();
        if (CollectionUtils.isNotEmpty(list)) {
            shareIdList = list.stream().map(TransferDto::getFk).collect(Collectors.toList());
        }
        // 遍历分表:晒家
        List<ShareOrder> shareOrderList = new ArrayList<ShareOrder>();
        if (CollectionUtils.isNotEmpty(shareIdList)) {
            shareOrderList = shareOrderDao.getShareOrderListByIds(shareIdList);
            // 图片处理
            buildImageSize(shareOrderList, shareOrderRequest.getWidth());
            // 获取新家大晒所有评论
            HttpShareOrderCommonListRequest commentListRequest = new HttpShareOrderCommonListRequest();
            commentListRequest.setShareOrderIdList(shareIdList);
            /**
             * 晒家对应 的评论也需要增加审核机制<br/>
             * 其实就是特需用户什么都可以看 -- 走老流程就可以 <br/>
             * 普通用户只能看 审批通过和 自己发布的未审批通过 -- 走新流程<br/>
             */
            List<ShareOrderComment> commentList;
            if (superuserTag) {
                commentList = shareOrderDao.getShareOrderCommentList(commentListRequest);
            } else {
                commentList = shareOrderDao.getShareOrderCommentListByCommonUser(commentListRequest, userId);
            }
            List<Integer> userIdList = this.getUserIdList(shareOrderList, commentList);
            List<UserDto> userList = this.getUserListByIds(userIdList);
            Map<Integer, UserDto> userMap = this.getUserMapByList(userList);
            commentList = this.buildCommentList(commentList, userMap);
            // 新家大晒评论加入列表中
            List<ShareOrder> shareOrderListResponse = buildShareOrderList(shareOrderList, commentList);

            List<ShareOrderPraise> shareOrderPraiseList = new ArrayList<>();
            // 用户已登录的情况下，获取用户是否点赞的信息

            if (StringUtils.isNotEmpty(shareOrderRequest.getAccessToken())) {
                if (null != userId && userId > 0) {
                    shareOrderPraiseList = shareOrderDao.getShareOrderPraiseListByUserId(userId);
                    // 官方回复名称
                    basePageResponse.setUserOfficialName(commentAdminService.queryOfficialName());
                }
            }
            // 数据处理
            this.handleShareOrder(shareOrderListResponse, shareOrderPraiseList, userId, userMap);
        }
        if (CollectionUtils.isNotEmpty(shareOrderList)) {
            Set<Long> userIdList = shareOrderList.stream().map(shareOrder -> shareOrder.getUserId()).collect(Collectors.toSet());
            userIdList.removeIf(id -> id == null);
            if (CollectionUtils.isNotEmpty(userIdList)) {
                List<UserDto> userDtos = userProxy.batchQueryUserInfo(userIdList.stream().map(id -> id.intValue()).collect(Collectors.toList()));
                if (CollectionUtils.isNotEmpty(userDtos)) {
                    Map<Long, UserDto> userMapById = userDtos.stream().collect(Collectors.toMap(o -> o.getId().longValue(), o -> o));
                    shareOrderList.forEach(shareOrder -> {
                        UserDto userInfo = userMapById.get(shareOrder.getUserId());
                        if (userInfo != null && userInfo.getMember() != null && StringUtils.isNotBlank(userInfo.getMember().getuImg())) {
                            shareOrder.setUserImgUrl(AliImageUtil.imageCompress(userInfo.getMember().getuImg(), 1, 750, ImageConstant.SIZE_SMALL));
                            shareOrder.setUserNickName(userInfo.getMember().getNickName());
                        } else {
                            shareOrder.setUserImgUrl(StaticResourceConstants.USER_DEFAULT_HEAD_IMAGE);
                        }
                    });
                }
            }
        }
        basePageResponse.setObj(shareOrderList);
        basePageResponse.setTotalCount(page.getTotalCount());
        basePageResponse.setTotalPage(page.getTotalPage());
        basePageResponse.setExt("查询成功");
        basePageResponse.setCode(HttpResponseCode.SUCCESS);
        if (shareOrderRequest.getQuerySource().equals("lifeHomePage") && CollectionUtils.isNotEmpty(shareOrderList)) {
            stringRedisTemplate.opsForValue().set(RedisKey.LifePlate.LIFE_HOME_PAGE_SHARE_ORDER_CACHE, JSON.toJSONString(basePageResponse), 30, TimeUnit.MINUTES);
        }
        return basePageResponse;
    }

    /**
     * step 1 :判断是不是特需用户
     *
     * @param mobile
     * @return
     */
    private boolean getSuperUserTag(String mobile) {
        if (StringUtils.isNotBlank(mobile)) {
            DicListDto dicList = dicProxy.getDicListByKey(HomeCardPraise.SHARE_ORDER_MOBILE);
            if (dicList != null && CollectionUtils.isNotEmpty(dicList.getDicList())) {
                List<DicDto> list = dicList.getDicList();
                for (DicDto dic : list) {
                    String value = dic.getValueDesc();
                    // step 1 :判断是不是特需用户
                    if (StringUtils.isNotBlank(value) && value.equals(mobile)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @param commentList
     * @param userMap
     * @return
     */
    private List<ShareOrderComment> buildCommentList(List<ShareOrderComment> commentList, Map<Integer, UserDto> userMap) {
        if (CollectionUtils.isNotEmpty(commentList)) {
            for (ShareOrderComment shareOrderComment : commentList) {
                String userNickName = shareOrderComment.getUserNickName();
                // 业务:wcm 回复为艾佳生活
                if (StringUtils.isNotBlank(userNickName) && userNickName.equals("艾佳生活")) {
                    shareOrderComment.setUserImg(StaticResourceConstants.AIJIA_IHOME);
                } else {
                    long userId = shareOrderComment.getUserId();
                    Integer userIdToInt = (int) userId;
                    UserDto useVo = userMap.get(userIdToInt);
                    if (useVo != null) {
                        if (useVo.getMember() != null) {
                            if (StringUtils.isNotBlank(useVo.getMember().getNickName())) {
                                shareOrderComment.setUserNickName(useVo.getMember().getNickName());

                            }
                            if (StringUtils.isNotBlank(useVo.getMember().getuImg())) {
                                shareOrderComment.setUserImg(useVo.getMember().getuImg());
                            }

                        }
                    }

                }

                if (shareOrderComment.getCreateTime() > 0) {
                    shareOrderComment.setCreateTimeDesc(getCreateTimeText(shareOrderComment.getCreateTime()));
                }
                Integer replyUserId = shareOrderComment.getReplyUserId();
                UserDto replyVo = userMap.get(replyUserId);
                if (replyVo != null) {
                    if (replyVo.getMember() != null) {
                        if (StringUtils.isNotBlank(replyVo.getMember().getNickName())) {
                            shareOrderComment.setReplyNickName(replyVo.getMember().getNickName());
                        } else {
                            if (StringUtils.isNotBlank(replyVo.getMobile())) {
                                shareOrderComment
                                        .setReplyNickName(StringUtil.getPhoneNumberForShow(replyVo.getMobile()));
                            } else {
                                shareOrderComment.setReplyNickName(HomeCardPraise.ANONYMOUS_USER);
                            }
                        }

                    }
                }

            }
        }
        return commentList;
    }

    private List<Integer> getUserIdList(List<ShareOrder> shareOrderList, List<ShareOrderComment> commentList) {
        List<Integer> userIdList = new ArrayList<Integer>();
        if (CollectionUtils.isNotEmpty(shareOrderList)) {
            for (ShareOrder shareOrder : shareOrderList) {
                long userId = shareOrder.getUserId();
                int userIdToInt = (int) userId;
                if (!userIdList.contains(userIdToInt)) {
                    userIdList.add(userIdToInt);
                }
            }
        }

        if (CollectionUtils.isNotEmpty(commentList)) {
            for (ShareOrderComment shareOrderComment : commentList) {
                long userId = shareOrderComment.getUserId();
                int userIdToInt = (int) userId;
                if (!userIdList.contains(userIdToInt)) {
                    userIdList.add(userIdToInt);
                }
            }
        }

        return userIdList;
    }

    private List<UserDto> getUserListByIds(List<Integer> userIdList) {
        List<UserDto> userList = userProxy.batchQueryUserInfo(userIdList);
        return userList;
    }

    private Map<Integer, UserDto> getUserMapByList(List<UserDto> userList) {
        Map<Integer, UserDto> userMap = new HashMap<Integer, UserDto>();
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(userList)) {
            for (UserDto user : userList) {
                userMap.put(user.getId(), user);
            }
        }
        return userMap;
    }

    /**
     * 晒单列表增加评论
     *
     * @param shareOrderList
     * @param commentList
     * @return
     */
    private List<ShareOrder> buildShareOrderList(List<ShareOrder> shareOrderList, List<ShareOrderComment> commentList) {
        // 官方回复名称
        String officialName = commentAdminService.queryOfficialName();
        for (ShareOrder shareOrder : shareOrderList) {
            String shareOrderId = shareOrder.getShareOrderId();
            // 昵称、手机号处理
            if (!StringUtil.isNullOrEmpty(shareOrder.getPhoneNum())) {
                shareOrder.setPhoneNum(StringUtil.getPhoneNumberForShow(shareOrder.getPhoneNum()));
            } else {
                shareOrder.setPhoneNum(HomeCardPraise.ANONYMOUS_USER);
            }
            if (org.apache.commons.lang3.StringUtils.isNotBlank(shareOrder.getBuildingAddress())) {
                shareOrder.setBuildingAddress(shareOrder.getBuildingAddress().replace(
                        ProductProgramPraise.HOUSE_NAME_BBC, ""));
            }
            List<ShareOrderComment> comments = new ArrayList<ShareOrderComment>();
            for (ShareOrderComment shareOrderComment : commentList) {
                if (shareOrderComment.getShareOrderId().equals(shareOrderId)) {
                    // 判断是否是官方回复账号
                    if (shareOrderComment.getOfficialFlag() != null) {
                        if (shareOrderComment.getOfficialFlag() == HomeCardPraise.COMMENT_REPLY_OFFICIAL_FRONT) {
                            shareOrderComment.setUserNickName(officialName);
                        } else if (shareOrderComment.getOfficialFlag() == HomeCardPraise.COMMENT_REPLY_OFFICIAL_BEHIND) {
                            shareOrderComment.setReplyNickName(officialName);
                        } else if (shareOrderComment.getOfficialFlag() == HomeCardPraise.COMMENT_REPLY_OFFICIAL_ALL) {
                            shareOrderComment.setUserNickName(officialName);
                            shareOrderComment.setReplyNickName(officialName);
                        }
                    }
                    comments.add(shareOrderComment);
                }
            }
            shareOrder.setComments(comments);
        }
        return shareOrderList;
    }

    @Override
    public String addShareOrder(ShareOrderDto shareOrderDto) {
        // 构造晒单信息
        ShareOrder shareOrder = this.buildShareOrder(shareOrderDto);

        // 发布晒单未获取到用户信息则不再发布
        if (shareOrder.getUserId() <= 0L) {
            throw new BusinessException("用户未登录或登录已过期");
        }

        if (shareOrder.getShareOrderStatus() == null) {
            shareOrder.setShareOrderStatus(HomeCardPraise.APPROVE_FAILED);// 默认不通过
        }
        String shareOrderId = shareOrderDao.insertShareOrder(shareOrder);
        if (StringUtils.isEmpty(shareOrderId)) {
            throw new BusinessException("发表失败");
        }

        return shareOrderId;
    }

    /**
     * 为符合条件的用户发放艾积分
     *
     * @param shareOrder
     * @return 该用户已获得艾积分的次数
     */
    public int rechargeAjb(ShareOrder shareOrder) {
        try {
            // 每位用户可以获取20次艾积分
            int count = shareOrderDao.getShareOrderCountByUserId(shareOrder.getUserId());
            logger.info("user {} has post share order {} times", shareOrder.getUserId(), count);
            if (REWARD_LIMIT >= count) {
                // 晒单发布成功后 发放艾积分
                ajbRechargeProxy.ajbActivityRecharge(Integer.parseInt(String.valueOf(shareOrder.getUserId())),
                        REWARD_AMOUNT, "发表晒家奖励艾积分", 3);
            }
            return count;
        }
        // 捕获异常，避免艾积分发放失败导致晒单失败
        catch (Exception e) {
            logger.error("user {} recharge ajb for new homecard share order  failed", shareOrder.getUserId());
        }
        return 0;
    }

    @Override
    public Integer addPraise(HttpShareOrderPraiseRequest shareOrderPraiseRequest) {

        ShareOrderPraise shareOrderPraise = new ShareOrderPraise();
        BeanUtils.copyProperties(shareOrderPraiseRequest, shareOrderPraise);
        HttpUserInfoRequest userDto = shareOrderPraiseRequest.getUserInfo();

        shareOrderPraise.setUserId(userDto.getId());

        int count = shareOrderDao.getPraiseCount(userDto.getId(), shareOrderPraise.getShareOrderId());
        // 用户已点过赞
        if (count > 0) {
            throw new BusinessException(HttpReturnCode.ADD_PRAISE_ALREADY, "已点过赞");
        }
        // 保存点赞信息
        shareOrderDao.insertShareOrderPraise(shareOrderPraise);
        // 点赞数+1，并返回当前大晒的现在点赞数
        return shareOrderDao.inc(shareOrderPraiseRequest);
    }

    @Override
    public Integer addPraiseByType(HttpShareOrderPraiseRequest shareOrderPraiseRequest, int type) {
        HttpUserInfoRequest userDto = shareOrderPraiseRequest.getUserInfo();
        if (null == userDto) {
            throw new BusinessException(MessageConstant.ADMIN_ILLEGAL);
        }

        int sum = shareOrderDao.addPraiseByType(userDto.getId(), shareOrderPraiseRequest.getShareOrderId());
        // 点赞成功，返回总的点赞数，否则返回0
        if (sum == 0) {
            throw new BusinessException(HttpReturnCode.ADD_PRAISE_ALREADY, "已点过赞");
        }

        return sum;

    }

    /**
     * @param shareOrderList
     * @param shareOrderPraiseList
     */
    private void handleShareOrder(List<ShareOrder> shareOrderList, List<ShareOrderPraise> shareOrderPraiseList,
                                  Integer userId, Map<Integer, UserDto> userMap) {
        // list to set 加快遍历速度
        Set<String> praisedShareOrderId = new HashSet<>();
        if (!CollectionUtils.isEmpty(shareOrderPraiseList)) {
            for (ShareOrderPraise shareOrderPraise : shareOrderPraiseList) {
                praisedShareOrderId.add(shareOrderPraise.getShareOrderId());
            }
        }
        for (ShareOrder shareOrder : shareOrderList) {
            // 用户点赞过该记录
            if (praisedShareOrderId.contains(shareOrder.getShareOrderId())) {
                shareOrder.setPraised("1");
            }
            // 目前没有批量获取user的接口 TODO 暂定这样的方式，以后看情况再修改吧
            this.buildShareOrderUserInfoById(shareOrder, userMap);

            shareOrder.setCreateTimeText(getCreateTimeText(shareOrder.getCreateTime()));
            // 若用户登录，可删除自己的晒家
            if (userId != null && userId == shareOrder.getUserId()) {
                shareOrder.setCanDeleteFlag(1);// 可删除
            } else {
                shareOrder.setCanDeleteFlag(0);// 不可删除
            }
        }
    }

    /**
     * 距当前时间1小时以内，显示N分钟前。 [1,24]小时，显示N小时前，往下取整。 [1,7]天，显示N天前，往下取整。 超过7天，显示几月几号。
     * 跨年则带年份。例如2015.1.7
     *
     * @param createTime
     * @return
     */
    private String getCreateTimeText(long createTime) {
        // 统一显示几月几号 2017-12-11 修改规则
        Date currentDate = new Date(createTime);
        int month = DateUtils.getMonth(currentDate) + 1;
        return month + "月" + DateUtils.getDay(currentDate) + "日";

        // long secondsTimeBeforeNow = (System.currentTimeMillis() - createTime)
        // / 1000;
        // if (secondsTimeBeforeNow < 3600) {
        // return secondsTimeBeforeNow / 60 + "分钟前";
        // }
        // // 24*60*60
        // if (secondsTimeBeforeNow < 86400) {
        // return secondsTimeBeforeNow / 3600 + "小时前";
        // }
        // // 7*24*60*60
        // if (secondsTimeBeforeNow < 604800) {
        // return secondsTimeBeforeNow / (86400) + "天前";
        // }
        // Date currentDate = new Date(createTime);
        // // 未跨年
        // if (DateUtils.getYear(new Date()) == DateUtils.getYear(currentDate))
        // {
        // int month = DateUtils.getMonth(currentDate) + 1;
        // return month + "月" + DateUtils.getDay(currentDate) + "日";
        // }
        // String dateToString = DateUtils.dateToString(new Date(createTime),
        // "yyyy.MM.dd");
        // return dateToString;
    }

    private ShareOrder buildShareOrder(ShareOrderDto shareOrderDto) {
        // 构造用户相关信息
        ShareOrder shareOrder = this.buildShareOrderUserInfoByToken(shareOrderDto.getAccessToken());

        // 用户楼盘信息
        shareOrder.setCityName(shareOrderDto.getCityName());
        if (org.apache.commons.lang3.StringUtils.isNotBlank(shareOrderDto.getBuildingAddress())) {
            shareOrder.setBuildingAddress(shareOrderDto.getBuildingAddress().replace(
                    ProductProgramPraise.HOUSE_NAME_BBC, ""));
        } else {
            shareOrder.setBuildingAddress("");
        }
        // 初始化回复数量为0
        shareOrder.setReplyNum(0);
        // 删除标志
        shareOrder.setDeleteFlag(0);
        shareOrderDto.setShareOrderStatus(shareOrder.getShareOrderStatus());
        BeanUtils.copyProperties(shareOrderDto, shareOrder);

        // 初始化晒单点赞数为0
        shareOrder.setPraiseNum(0);
        shareOrder.setCreateTime(System.currentTimeMillis());

        return shareOrder;
    }

    private ShareOrder buildShareOrderUserInfoByToken(String userToken) {
        ShareOrder shareOrder = new ShareOrder();
        try {
            // UserDto user = userFacade.getUserByToken(userToken);
            UserDto user = userProxy.getUserByToken(userToken);
            if (null != user) {
                // shareOrder.setPhoneNum(StringUtil.getPhoneNumberForShow(user.getMobile()));
                shareOrder.setPhoneNum(user.getMobile());
                if (StringUtils.isNotBlank(user.getMobile())) {
                    boolean result = this.getSuperUserTag(user.getMobile());
                    if (result) {
                        shareOrder.setShareOrderStatus(HomeCardPraise.APPROVE_OK);
                    } else {
                        shareOrder.setShareOrderStatus(HomeCardPraise.APPROVE_FAILED);
                    }

                } else {
                    shareOrder.setShareOrderStatus(HomeCardPraise.APPROVE_FAILED);
                }
                shareOrder.setUserId(user.getId());
                MemberDto member = user.getMember();
                if (null != member) {
                    shareOrder.setUserNickName(user.getMember().getNickName());
                    shareOrder.setUserImgUrl(user.getMember().getuImg());
                }
            }
        }
        // 查询用户信息不要抛出异常
        catch (Exception e) {
            logger.error("query user info error,user id is {}", shareOrder.getUserId());
        }
        return shareOrder;
    }

    private void buildShareOrderUserInfoById(ShareOrder shareOrder, Map<Integer, UserDto> userMap) {
        try {
            UserDto user = userMap.get(Integer.parseInt(String.valueOf(shareOrder.getUserId())));
            if (null != user) {
                shareOrder.setPhoneNum(StringUtil.getPhoneNumberForShow(user.getMobile()));
                shareOrder.setShowName(shareOrder.getPhoneNum());
                MemberDto member = user.getMember();
                if (null != member) {
                    shareOrder.setUserNickName(member.getNickName());
                    // 头像处理
                    if (org.apache.commons.lang3.StringUtils.isNotEmpty(member.getuImg())) {
                        shareOrder.setUserImgUrl(QiniuImageUtils.compressImageAndDiffPic(member.getuImg(), 100, 100));
                    } else {
                        shareOrder.setUserImgUrl(member.getuImg());
                    }
                }
            }
        }
        // 查询用户信息不要抛出异常
        catch (Exception e) {
            logger.error("query user info error,user id is {}", shareOrder.getUserId());
        }
    }

    @Override
    public String addShareOrderComment(HttpShareOrderCommentRequest shareOrderCommentRequest) {
        ShareOrderComment comment = buildCommentUserInfo(shareOrderCommentRequest);
        if (comment.getCommentStatus() == null) {
            comment.setCommentStatus(HomeCardPraise.APPROVE_FAILED);// 默认不通过
        }
        String insertShareOrderComment = shareOrderDao.insertShareOrderComment(comment);
        if (StringUtil.isNullOrEmpty(insertShareOrderComment)) {
            throw new BusinessException("添加用户评论失败");
        }

        HttpShareOrderPraiseRequest shareOrderPraiseRequest = new HttpShareOrderPraiseRequest();
        shareOrderPraiseRequest.setShareOrderId(comment.getShareOrderId());
        ShareOrder shareOrder = shareOrderDao.getShareOrderById(shareOrderPraiseRequest);

        // 若是官方回复，则添加晒家回复数量
        if (comment.getOfficialFlag() != null
                && (comment.getOfficialFlag() == HomeCardPraise.COMMENT_REPLY_OFFICIAL_FRONT || comment
                .getOfficialFlag() == HomeCardPraise.COMMENT_REPLY_OFFICIAL_ALL)) {
            Integer replyNum = 1;
            if (shareOrder.getReplyNum() != null) {
                replyNum = replyNum + shareOrder.getReplyNum();
            }
            shareOrder.setReplyNum(replyNum);
            shareOrderDao.updateShareOrderById(shareOrder);
        }

        // 仅有作者可以收到直接回帖的push通知。直接回帖包括用户回帖和官方回帖,但是作者回复自己的不算
        JpushParamDto jpushRequest = new JpushParamDto();
        jpushRequest.setNoticeSubType(PushConstant.NOTICE_SUBTYPE_SHAREORDER);
        if (shareOrder != null && shareOrder.getUserId() > 0) {

            UserDto user = userProxy.getUserById((int) shareOrder.getUserId());// 作者
            String mobile = user.getMobile();
            if (!StringUtils.isEmpty(mobile)) {
                if ((comment.getReplyUserId() == null || comment.getReplyUserId().intValue() == (int) shareOrder
                        .getUserId()) && (int) comment.getUserId() != (int) shareOrder.getUserId()) {
                    jpushRequest.setNewsTitle(HomeCardPraise.NOTICE_NEW_TITLE);
                    String content = HomeCardPraise.NOTICE_CONTENT;
                    content = content.replaceAll("\\[A\\]", comment.getUserNickName());
                    content = content.replaceAll("\\[B\\]", comment.getComment());
                    jpushRequest.setContent(content);
                    Map<String, String> params = Maps.newHashMap();
                    params.put("content", content);
                    params.put("id", comment.getShareOrderId());
                    appPushProxy.appPush(new AppPushDto().setPushKey("shareOrderCommentReply").setMobile(mobile).setParams(params));
                }
            }
        }
        return insertShareOrderComment;
    }

    @Override
    public String addShareOrderCommentForTopic(HttpShareOrderCommentRequest shareOrderCommentRequest,
                                               UserDto user) {
        String commentId = shareOrderDao.addShareOrderCommentForTopic(shareOrderCommentRequest, user);
        if (StringUtils.isBlank(commentId)) {
            throw new BusinessException("添加用户评论失败");
        }
        return commentId;
    }

    // 构建用户评论
    private ShareOrderComment buildCommentUserInfo(HttpShareOrderCommentRequest commentRequest) {
        ShareOrderComment comment = new ShareOrderComment();
        String userMobile = "";// 评论用户手机号
        String replyUserMobile = "";// 回复用户手机号
        try {
            comment.setCreateTime(System.currentTimeMillis());
            comment.setShareOrderId(commentRequest.getShareOrderId());
            comment.setComment(commentRequest.getComment());
            UserDto user = userProxy.getUserByToken(commentRequest.getAccessToken());
            if (null != user) {
                MemberDto member = user.getMember();
                userMobile = user.getMobile();
                comment.setUserId(user.getId());
                if (null != member) {
                    comment.setUserNickName(StringUtil.isNullOrEmpty(member.getNickName()) ? StringUtil
                            .getPhoneNumberForShow(userMobile) : member.getNickName());
                } else {
                    comment.setUserNickName(StringUtil.getPhoneNumberForShow(userMobile));
                }
                if (StringUtils.isNotBlank(user.getMobile())) {
                    boolean result = this.getSuperUserTag(user.getMobile());
                    if (result) {
                        comment.setCommentStatus(HomeCardPraise.APPROVE_OK);
                    } else {
                        comment.setCommentStatus(HomeCardPraise.APPROVE_FAILED);
                    }

                } else {
                    comment.setCommentStatus(HomeCardPraise.APPROVE_FAILED);
                }
            }
            comment.setDeleteFlag(0);
            // 回复用户信息
            if (org.apache.commons.lang3.StringUtils.isNotBlank(commentRequest.getCommentId())) {
                ShareOrderComment replyShareOrderComment = shareOrderDao.queryShareOrderCommentById(commentRequest
                        .getCommentId());
                if (replyShareOrderComment != null) {
                    comment.setReplyNickName(replyShareOrderComment.getUserNickName());
                    comment.setReplyUserId(Integer.parseInt(String.valueOf(replyShareOrderComment.getUserId())));
                    // UserDto replyUser =
                    // userFacade.getUserByToken(commentRequest.getAccessToken());

                    // 20171009
                    // 回复用户信息不能用commentRequest.getAccessToken()查询，这样查询的都是评论用户的信息
                    // UserVo replyUser =
                    // userProxy.getUserByToken(commentRequest.getAccessToken());
                    UserDto replyUser = userProxy.getUserById(Integer.parseInt(String.valueOf(replyShareOrderComment
                            .getUserId())));
                    if (replyUser != null) {
                        replyUserMobile = replyUser.getMobile();
                    }
                }
            }
            // 以手机号判断是否是官方回复账号
            if (StringUtil.isNullOrEmpty(userMobile) && StringUtil.isNullOrEmpty(replyUserMobile)) {
                comment.setOfficialFlag(HomeCardPraise.COMMENT_REPLY_ORTHER);
            } else {
                // 官方回复手机号
                List<String> replyMobileList = commentAdminService.queryReplyMobileList();

                // 判断是否是官方回复
                boolean userFlag = false;
                boolean replyUserFlag = false;
                if (!CollectionUtils.isEmpty(replyMobileList)) {
                    if (org.apache.commons.lang3.StringUtils.isNotBlank(userMobile)) {
                        userFlag = replyMobileList.contains(userMobile);
                    }
                    if (org.apache.commons.lang3.StringUtils.isNotBlank(replyUserMobile)) {
                        replyUserFlag = replyMobileList.contains(replyUserMobile);
                    }
                }
                if (userFlag && replyUserFlag) {
                    comment.setOfficialFlag(HomeCardPraise.COMMENT_REPLY_OFFICIAL_ALL);
                } else if (userFlag && !replyUserFlag) {
                    comment.setOfficialFlag(HomeCardPraise.COMMENT_REPLY_OFFICIAL_FRONT);
                } else if (replyUserFlag && !userFlag) {
                    comment.setOfficialFlag(HomeCardPraise.COMMENT_REPLY_OFFICIAL_BEHIND);
                } else {
                    comment.setOfficialFlag(HomeCardPraise.COMMENT_REPLY_ORTHER);
                }
            }
        } catch (Exception e) {
            logger.error("query user info error,user id is {}", commentRequest.getAccessToken());
        }
        return comment;
    }

    @Override
    public List<ShareOrderComment> getShareOrderCommentList(HttpShareOrderCommonListRequest shareOrderCommonListRequest) {
        if (shareOrderCommonListRequest == null) {
            return null;
        }
        String orderId = shareOrderCommonListRequest.getShareOrderId();
        if (org.apache.commons.lang.StringUtils.isNotBlank(orderId)) {
            List<String> shareOrderIdList = new ArrayList<String>();
            shareOrderIdList.add(orderId);
        }
        HttpUserInfoRequest userDto = shareOrderCommonListRequest.getUserInfo();
        Integer userId = null;
        String mobile = null;
        if (userDto != null) {
            userId = userDto.getId();
            mobile = userDto.getMobile();
        }
        // step 1 :判断是不是特需用户
        boolean superuserTag = this.getSuperUserTag(mobile);
        List<ShareOrderComment> commentList = new ArrayList<ShareOrderComment>();
        // 晒家类型:0 表示老晒家, 1 表示专题
        int type = shareOrderCommonListRequest.getType();
        if (type == 0) {
            if (superuserTag) {
                commentList = shareOrderDao.getShareOrderCommentList(shareOrderCommonListRequest);
            } else {
                commentList = shareOrderDao.getShareOrderCommentListByCommonUser(shareOrderCommonListRequest, userId);
            }
        } else if (type == 1) {
            commentList = shareOrderDao.getShareOrderCommentListForTopic(shareOrderCommonListRequest, userId,
                    superuserTag);
        }
        List<Integer> userIdList = this.getUserIdList(null, commentList);
        List<UserDto> userList = this.getUserListByIds(userIdList);
        Map<Integer, UserDto> userMap = this.getUserMapByList(userList);
        commentList = this.buildCommentList(commentList, userMap);
        return commentList;
    }

    @Override
    public void exportList(HttpShareOrderRequest shareOrderRequest) {
        List<ShareOrder> shareOrderList = shareOrderDao.getShareOrderList(shareOrderRequest);
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        for (ShareOrder shareOrder : shareOrderList) {
            shareOrder.setCreateTimeText(sdf.format(shareOrder.getCreateTime()));
            // UserDto user =
            // userFacade.getUserById(Integer.parseInt(String.valueOf(shareOrder.getUserId())));
            UserDto user = userProxy.getUserById(Integer.parseInt(String.valueOf(shareOrder.getUserId())));
            if (null != user) {
                shareOrder.setPhoneNum(user.getMobile());
                MemberDto member = user.getMember();
                if (null != member) {
                    shareOrder.setUserNickName(member.getNickName());
                }
            }

        }

    }

    private void buildImageSize(List<ShareOrder> shareOrderList, Integer width) {
        if (shareOrderList != null && shareOrderList.size() > 0) {
            Iterator<ShareOrder> iterator = shareOrderList.iterator();
            while (iterator.hasNext()) {
                ShareOrder shareOrder = (ShareOrder) iterator.next();
                if (null != shareOrder.getImgContent()) {
                    List<ImageEntity> imageEntities = new ArrayList<ImageEntity>();
                    // 如果只有一张图则切图宽度为62%，多张图则是31%
                    if (shareOrder.getImgContent().size() == 1) {
                        String headImg = shareOrder.getImgContent().get(0);
                        ImageEntity entity = new ImageEntity();
                        if (!StringUtils.isEmpty(headImg)) {
                            if (width == null) {
                                entity.setSmallImage(headImg);
                                entity.setBigImage(headImg);
                            } else {
                                Integer smallWidth = width * ImageSize.WIDTH_PER_SIZE_62 / ImageSize.WIDTH_PER_SIZE_100;
                                Integer bigWidth = width;
                                String smallImage = QiniuImageUtils.compressImageAndDiffPic(headImg, smallWidth, -1);
                                String bigImage = QiniuImageUtils.compressImageAndSamePicTwo(headImg, bigWidth, -1);
                                entity.setBigImage(bigImage);// 90% 中部截取
                                entity.setSmallImage(smallImage);// 50% 中部截取
                            }
                        }
                        imageEntities.add(entity);
                    } else {
                        for (String imageUrl : shareOrder.getImgContent()) {
                            ImageEntity entity = getImgEntity(imageUrl, width);
                            imageEntities.add(entity);
                        }
                    }
                    shareOrder.setImgObj(imageEntities);
                }
            }
        }
    }

    /**
     * 获取切图:大图 90% 中部截取 小图:50% 中部截取
     *
     * @param headImg
     * @param width
     * @return
     */
    private ImageEntity getImgEntity(String headImg, Integer width) {
        ImageEntity entity = new ImageEntity();
        if (!StringUtils.isEmpty(headImg)) {
            if (width == null) {
                entity.setSmallImage(headImg);
                entity.setBigImage(headImg);
            } else {
                Integer smallWidth = width * ImageSize.WIDTH_PER_SIZE_31 / ImageSize.WIDTH_PER_SIZE_100;
                Integer bigWidth = width;
                String smallImage = QiniuImageUtils.compressImageAndDiffPic(headImg, smallWidth, -1);
                String bigImage = QiniuImageUtils.compressImageAndSamePicTwo(headImg, bigWidth, -1);
                entity.setBigImage(bigImage);// 90% 中部截取
                entity.setSmallImage(smallImage);// 50% 中部截取
            }
        }
        return entity;
    }

    @Override
    public boolean deleteShareOrderById(String shareOrderId) {
        if (org.apache.commons.lang3.StringUtils.isBlank(shareOrderId)) {
            return false;
        } else {
            // 删除晒家
            SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            HttpShareOrderPraiseRequest shareOrderPraiseRequest = new HttpShareOrderPraiseRequest();
            shareOrderPraiseRequest.setShareOrderId(shareOrderId);
            ShareOrder shareOrder = shareOrderDao.getShareOrderById(shareOrderPraiseRequest);
            shareOrder.setDeleteFlag(-1);
            shareOrder.setDeleteTime(dfs.format(new Date()));
            return shareOrderDao.deleteShareOrderById(shareOrder);
        }
    }

    @Override
    public UserHouseInfoResponse queryUserHouseInfo(Integer userId, Integer orderId) {
        if (userId != null) {
            // CustomerDetailInfo customerDetailInfo =
            // productProgramProxy.getUserInfo(mobileNum);
            List<HouseInfoResponseVo> infoResponseVos = houseService.queryUserHouseList(userId);
            if (CollectionUtils.isNotEmpty(infoResponseVos)) {
                HouseInfoResponseVo houseInfoResponseVo = null;
                if (orderId == null) {
                    houseInfoResponseVo = infoResponseVos.get(0);
                } else {
                    List<HouseInfoResponseVo> collect = infoResponseVos.stream().filter(infoResponseVo -> infoResponseVo.getMasterOrderId().equals(orderId)).collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(collect)) {
                        houseInfoResponseVo = collect.get(0);
                    } else {
                        return null;
                    }
                }
                UserHouseInfoResponse houseInfoResponse = new UserHouseInfoResponse();
                houseInfoResponse.setCityName(houseInfoResponseVo.getCityName());
                houseInfoResponse.setBuildingInfo(houseInfoResponseVo.getBuildingInfo());
                String houseProjectName = houseInfoResponseVo.getHouseProjectName();// 楼盘名称
                if (StringUtils.isNotBlank(houseProjectName)) {
                    houseInfoResponse.setBuildingAddress(houseProjectName.replace(ProductProgramPraise.HOUSE_NAME_BBC,
                            ""));
                } else {
                    houseInfoResponse.setBuildingAddress("");
                }
                return houseInfoResponse;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public String brushShareOrderData() {
        int errorShare = 0;
        int successShare = 0;
        // 查询所有晒家
        List<ShareOrder> orders = shareOrderDao.queryAllShareOrderList();
        for (ShareOrder shareOrder : orders) {
            // 手机号
            // UserDto user =
            // userFacade.getUserById(Integer.parseInt(String.valueOf(shareOrder.getUserId())));
            UserDto user = userProxy.getUserById(Integer.parseInt(String.valueOf(shareOrder.getUserId())));
            if (user != null) {
                shareOrder.setPhoneNum(user.getMobile());
            }
            // 城市名称、小区名称、回复数量
            Integer replyNum = shareOrderDao.queryOfficialCountByShareOrderId(shareOrder.getShareOrderId());
            shareOrder.setDeleteFlag(0);
            shareOrder.setDeleteTime("");
            shareOrder.setReplyNum(replyNum);
            shareOrder.setCityName("");
            shareOrder.setBuildingAddress("");
            if (shareOrderDao.updateShareOrderById(shareOrder)) {
                successShare++;
            } else {
                errorShare++;
            }
        }
        return "更新晒家成功" + successShare + "条，失败" + errorShare + "条！";
    }

    @Override
    public String brushShareOrderCommentData() {
        int errorComment = 0;
        int successComment = 0;
        // 查询所有晒家评论
        List<ShareOrderComment> comments = shareOrderDao.queryAllCommentList();
        for (ShareOrderComment shareOrderComment : comments) {
            // 更新晒家相关
            shareOrderComment.setDeleteFlag(0);
            shareOrderComment.setDeleteTime("");
            shareOrderComment.setReplyUserId(0);
            shareOrderComment.setReplyNickName("");
            if ("艾佳生活".equals(shareOrderComment.getUserNickName())) {
                shareOrderComment.setOfficialFlag(1);
            } else {
                shareOrderComment.setOfficialFlag(0);
            }
            if (shareOrderDao.updateCommentById(shareOrderComment)) {
                successComment++;
            } else {
                errorComment++;
            }
        }
        return "更新晒家评论成功" + successComment + "条，失败" + errorComment + "条！";
    }

    @Override
    public ShareOrderDetailResponse queryShareOrderDetailById(ShareOrderDetailRequest request) {
        if (request != null && org.apache.commons.lang3.StringUtils.isNotBlank(request.getShareOrderId())) {
            ShareOrderDetailResponse detailResponse = new ShareOrderDetailResponse();
            String shareOrderId = request.getShareOrderId();
            Integer width = 0;
            if (request.getWidth() != null && request.getWidth() > 0) {
                width = request.getWidth();
            }
            HttpShareOrderPraiseRequest shareOrderPraiseRequest = new HttpShareOrderPraiseRequest();
            shareOrderPraiseRequest.setShareOrderId(shareOrderId);
            ShareOrder shareOrder = shareOrderDao.getShareOrderById(shareOrderPraiseRequest);
            if (shareOrder != null) {
                detailResponse.setShareOrderId(shareOrder.getShareOrderId());
                detailResponse.setUserId(shareOrder.getUserId());
                // 用户手机号、昵称
                detailResponse.setPhoneNum(shareOrder.getPhoneNum());
                detailResponse.setUserNickName(shareOrder.getUserNickName());
                if (org.apache.commons.lang3.StringUtils.isNotBlank(shareOrder.getUserNickName())) {
                    detailResponse.setShowName(shareOrder.getUserNickName());
                } else if (org.apache.commons.lang3.StringUtils.isNotBlank(shareOrder.getPhoneNum())) {
                    detailResponse.setShowName(StringUtil.getPhoneNumberForShow(shareOrder.getPhoneNum()));
                } else {
                    detailResponse.setShowName(HomeCardPraise.ANONYMOUS_USER);
                }
                // 头像处理
                if (org.apache.commons.lang3.StringUtils.isNotBlank(shareOrder.getUserImgUrl())) {
                    detailResponse.setUserImgUrl(QiniuImageUtils.compressImageAndDiffPic(shareOrder.getUserImgUrl(),
                            100, 100));
                } else {
                    detailResponse.setUserImgUrl("");
                }
                detailResponse.setTextContent(shareOrder.getTextContent());
                // 图片处理
                detailResponse.setImgContent(shareOrder.getImgContent());
                if (null != shareOrder.getImgContent()) {
                    List<ImageEntity> imageEntities = new ArrayList<ImageEntity>();
                    // 如果只有一张图则切图宽度为62%，多张图则是31%
                    if (shareOrder.getImgContent().size() == 1) {
                        String headImg = shareOrder.getImgContent().get(0);
                        ImageEntity entity = new ImageEntity();
                        if (!StringUtils.isEmpty(headImg)) {
                            if (width == null) {
                                entity.setSmallImage(headImg);
                                entity.setBigImage(headImg);
                            } else {
                                Integer smallWidth = width * ImageSize.WIDTH_PER_SIZE_62 / ImageSize.WIDTH_PER_SIZE_100;
                                Integer bigWidth = width;
                                String smallImage = QiniuImageUtils.compressImageAndDiffPic(headImg, smallWidth, -1);
                                String bigImage = QiniuImageUtils.compressImageAndSamePicTwo(headImg, bigWidth, -1);
                                entity.setBigImage(bigImage);// 90% 中部截取
                                entity.setSmallImage(smallImage);// 50% 中部截取
                            }
                        }
                        imageEntities.add(entity);
                    } else {
                        for (String imageUrl : shareOrder.getImgContent()) {
                            ImageEntity entity = getImgEntity(imageUrl, width);
                            imageEntities.add(entity);
                        }
                    }
                    detailResponse.setImgObj(imageEntities);
                }
                detailResponse.setCreateTime(shareOrder.getCreateTime());
                detailResponse.setCreateTimeText(getCreateTimeText(shareOrder.getCreateTime()));
                // 用户已登录的情况下，获取用户是否点赞的信息

                Integer userId = null;
                String mobile = null;
                HttpUserInfoRequest userDto = request.getUserInfo();
                if (null != userDto && null != userDto.getId()) {
                    userId = userDto.getId();
                    mobile = userDto.getMobile();
                    List<ShareOrderPraise> shareOrderPraiseList = shareOrderDao
                            .getShareOrderPraiseListByUserId(userId);
                    Set<String> praisedShareOrderId = new HashSet<>();
                    if (!CollectionUtils.isEmpty(shareOrderPraiseList)) {
                        for (ShareOrderPraise shareOrderPraise : shareOrderPraiseList) {
                            praisedShareOrderId.add(shareOrderPraise.getShareOrderId());
                        }
                    }
                    // 用户点赞过该记录
                    if (praisedShareOrderId.contains(shareOrderId)) {
                        detailResponse.setPraised("1");
                    }
                    detailResponse.setPraiseNum(shareOrder.getPraiseNum());
                    // 若用户登录，可删除自己的晒家
                    if (userId != null && userId == shareOrder.getUserId()) {
                        detailResponse.setCanDeleteFlag(1);// 可删除
                    } else {
                        detailResponse.setCanDeleteFlag(0);// 不可删除
                    }
                }
                detailResponse.setStickyPostNum(shareOrder.getStickyPostNum());
                // 获取新家大晒评论
                HttpShareOrderCommonListRequest commentListRequest = new HttpShareOrderCommonListRequest();
                List<String> shareOrderIdList = new ArrayList<String>();
                shareOrderIdList.add(shareOrderId);
                commentListRequest.setShareOrderIdList(shareOrderIdList);
                boolean superuserTag = this.getSuperUserTag(mobile); // step 1
                // :判断是不是特需用户
                List<ShareOrderComment> commentList = new ArrayList<ShareOrderComment>();
                if (superuserTag) {
                    commentList = shareOrderDao.getShareOrderCommentList(commentListRequest);
                } else {
                    commentList = shareOrderDao.getShareOrderCommentListByCommonUser(commentListRequest, userId);
                }

                // 官方回复昵称

                String officialName = commentAdminService.queryOfficialName();
                detailResponse.setUserOfficialName(officialName);
                List<ShareOrderComment> comments = new ArrayList<ShareOrderComment>();
                for (ShareOrderComment shareOrderComment : commentList) {
                    // 判断是否是官方回复账号
                    if (shareOrderComment.getOfficialFlag() != null) {
                        if (shareOrderComment.getOfficialFlag() == HomeCardPraise.COMMENT_REPLY_OFFICIAL_FRONT) {
                            shareOrderComment.setUserNickName(officialName);
                        } else if (shareOrderComment.getOfficialFlag() == HomeCardPraise.COMMENT_REPLY_OFFICIAL_BEHIND) {
                            shareOrderComment.setReplyNickName(officialName);
                        } else if (shareOrderComment.getOfficialFlag() == HomeCardPraise.COMMENT_REPLY_OFFICIAL_ALL) {
                            shareOrderComment.setUserNickName(officialName);
                            shareOrderComment.setReplyNickName(officialName);
                        }
                    }
                    comments.add(shareOrderComment);
                }
                detailResponse.setComments(comments);
                detailResponse.setDeleteFlag(shareOrder.getDeleteFlag());
                if (org.apache.commons.lang3.StringUtils.isNotBlank(shareOrder.getDeleteTime())) {
                    SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date deleteTime;
                    try {
                        deleteTime = dfs.parse(shareOrder.getDeleteTime());
                        detailResponse.setDeleteTime(deleteTime);
                    } catch (ParseException e) {
                        detailResponse.setDeleteTime(null);
                    }
                } else {
                    detailResponse.setDeleteTime(null);
                }
                detailResponse.setCityName(shareOrder.getCityName());
                if (org.apache.commons.lang3.StringUtils.isNotBlank(shareOrder.getBuildingAddress())) {
                    detailResponse.setBuildingAddress(shareOrder.getBuildingAddress().replace(
                            ProductProgramPraise.HOUSE_NAME_BBC, ""));
                } else {
                    detailResponse.setBuildingAddress("");
                }
                detailResponse.setReplyNum(shareOrder.getReplyNum());
            }
            return detailResponse;
        } else {
            return null;
        }
    }

    @Override
    public ShareOrderDetailResponse queryShareOrderDetailByType(String shareOrderId, Integer userId) {
        BuildingtopicViewResponse detail = shareOrderDao.queryBuildingtopicViewResponse(shareOrderId, userId);
        if (detail == null) {
            return null;
        }
        Date pushTime = detail.getPushTime();// 发布时间
        if (pushTime != null) {
            detail.setPushTimeDesc(getCreateTimeText(pushTime.getTime()));
        }
        Date statusTime = detail.getStatusTime();// 发布时间
        if (statusTime != null) {
            detail.setStatusTimeDesc(getCreateTimeText(statusTime.getTime()));
        }
        if (detail.getTopicType() != null) {
            if (detail.getTopicType() == BuildingtopicTypeEnum.TYPE_DRAFT_TOPIC.getCode()) {
                detail.setTopicName(BuildingtopicTypeEnum.TYPE_DRAFT_TOPIC.getMsg());
            }
            if (detail.getTopicType() == BuildingtopicTypeEnum.TYPE_STORY_TOPIC.getCode()) {
                detail.setTopicName(BuildingtopicTypeEnum.TYPE_STORY_TOPIC.getMsg());
            }
        }
        if (StringUtils.isNotBlank(detail.getAuthorImg())) {
            detail.setCreatorImg(detail.getAuthorImg());
        } else {
            detail.setCreatorImg(StaticResourceConstants.AIJIA_IHOME);
        }
        if (StringUtils.isNotBlank(detail.getAuthor())) {
            detail.setCreatorName(detail.getAuthor());
        } else {
            detail.setCreatorName("艾佳生活");
        }

        Integer allowedTag = detail.getAllowedTag();
        if (allowedTag != null && allowedTag == 1) {
            String limitTimeDesc = detail.getLimitTimeDesc();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            try {
                if (StringUtils.isNotBlank(limitTimeDesc)) {
                    Date currentDate = df.parse(limitTimeDesc);
                    if (currentDate != null && currentDate.after(new Date())) {
                        detail.setLastAllowed(1);
                    }
                }

            } catch (ParseException e) {
                detail.setLastAllowed(0);
            }

        }
        if (detail.getLastAllowed() == null) {
            detail.setLastAllowed(0);
        }

        ShareOrderDetailResponse shareOrder = new ShareOrderDetailResponse();
        shareOrder.setType(1);
        shareOrder.setBuildingTopicData(detail);

        if (detail.isPraiseTag()) {
            shareOrder.setPraised("1");// 0：未点赞 1：已点赞 默认未点赞
        } else {
            shareOrder.setPraised("0");
        }
        if (detail.getPraiseCount() != null) {
            shareOrder.setPraiseNum(detail.getPraiseCount());
        } else {
            shareOrder.setPraiseNum(0);
        }
        if (StringUtils.isNotBlank(detail.getId())) {
            shareOrder.setShareOrderId(detail.getId());
        }
        if (StringUtils.isNotBlank(detail.getFirstTitle())) {
            detail.setFirstTitle("【" + detail.getFirstTitle() + "】");
        }
        return shareOrder;
    }

    @Override
    public Integer forward(HttpForwardRequest request) {
        int result = shareOrderDao.forward(request);
        if (result <= 0) {
            throw new BusinessException("转发失败");
        }
        return result;
    }

    @Override
    public String addShareOrderForArticle(ShareOrderDto shareOrderDto) {
        String articleId = shareOrderDao.addShareOrderForArticle(shareOrderDto);
        if (StringUtils.isBlank(articleId)) {
            throw new BusinessException("投稿失败");
        }
        return articleId;
    }

}
