package com.ihomefnt.o2o.service.dao.push;

import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.common.util.StringUtil;
import com.ihomefnt.o2o.intf.dao.push.PushDao;
import com.ihomefnt.o2o.intf.domain.push.dto.*;
import com.ihomefnt.o2o.intf.manager.constant.push.PushConstant;
import com.ihomefnt.o2o.service.manager.util.mongo.MongoDBUtil;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PushDaoImpl implements PushDao {
    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    private static String NAME_SPACE = "com.ihomefnt.o2o.intf.dao.push.PushDao.";

    @Override
    public void updatePushList(JpushUpdateRequestVo request) {
        if (request == null || CollectionUtils.isEmpty(request.getMsgIdList())
                || StringUtils.isBlank(request.getMobileNum()) || StringUtils.isBlank(request.getAppVersion())) {
            return;
        }
        String mobile = request.getMobileNum();
        List<String> list = request.getMsgIdList();
        String tags = request.getAppVersion().replace(".", "_");
        MongoCollection<Document> conn = MongoDBUtil.getCollection(PushConstant.MONGDB_PUSH_USER);
        for (String msgId : list) {
            Document query = new Document("msgId", msgId).append("mobile", mobile).append("tags", tags);
            Document update = new Document("$set", new Document("sendFlag", PushConstant.SEND_YES)
                    .append("createTime", new Date()));
            UpdateOptions up = new UpdateOptions();
            up.upsert(true);

            //不存在就新建,有就更新
            conn.updateMany(query, update, up);
        }

    }

    @Override
    public List<ExtrasDto> queryPushList(HttpBaseRequest request) {
        List<ExtrasDto> extrasDtos = new ArrayList<>();
        if (!StringUtil.isEmpty(request.getMobileNum())) {
            extrasDtos.addAll(getPersonalMessage(request));
        }
        List<ExtrasDto> platformList = getPlatformMessage(request);
        extrasDtos.addAll(platformList);
        return extrasDtos;
    }

    /**
     * 拉取群推消息
     *
     * @param request
     */
    private List<ExtrasDto> getPlatformMessage(HttpBaseRequest request) {
        List<ExtrasDto> extrasList = new ArrayList<ExtrasDto>();
        Criteria queryBuilder = new Criteria();
        queryBuilder.and("tags").is(request.getAppVersion().replace(".", "_"));
        Query query = new Query(queryBuilder);
        // 查询所有群推消息
        List<PushPlatformCenterInfoDto> platformList = MongoDBUtil.queryDocumentList(PushConstant.MONGDB_PUSH_PLATFORM,
                query, PushPlatformCenterInfoDto.class);
        if (CollectionUtils.isNotEmpty(platformList)) {
            List<String> msgIdList = new ArrayList<String>();
            for (PushPlatformCenterInfoDto info : platformList) {
                String msgId = info.getMsgId();
                msgIdList.add(msgId);
            }
            if (!StringUtil.isEmpty(request.getMobileNum())) {
                queryBuilder = new Criteria();
                queryBuilder.and("tags").is(request.getAppVersion().replace(".", "_"));
                queryBuilder.and("mobile").is(request.getMobileNum());
                queryBuilder.and("msgId").in(msgIdList);
                query = new Query(queryBuilder);
                // 个人已经收到的群推
                List<PushUserCenterInfoDto> infoList = MongoDBUtil.queryDocumentList(PushConstant.MONGDB_PUSH_USER, query,
                        PushUserCenterInfoDto.class);
                //算出没有收到消息
                if (CollectionUtils.isEmpty(infoList)) {
                    setMessageList(msgIdList, request, extrasList);
                } else {
                    List<String> msgIds = new ArrayList<String>();
                    List<String> existList = new ArrayList<String>();
                    //已经收到
                    for (PushUserCenterInfoDto info : infoList) {
                        String msgId = info.getMsgId();
                        if (StringUtils.isNotBlank(msgId)) {
                            existList.add(msgId);
                        }
                    }
                    //没有收到
                    for (PushPlatformCenterInfoDto info : platformList) {
                        String msgId = info.getMsgId();
                        if (!existList.contains(msgId)) {
                            msgIds.add(msgId);
                        }

                    }
                    setMessageList(msgIds, request, extrasList);
                }
            } else {
                setMessageList(msgIdList, request, extrasList);
            }
        }
        return extrasList;
    }

    /**
     * 拉取个人信息
     *
     * @param request
     * @return
     */
    private List<ExtrasDto> getPersonalMessage(HttpBaseRequest request) {
        List<ExtrasDto> extrasList = new ArrayList<ExtrasDto>();
        Criteria queryBuilder = new Criteria();
        queryBuilder.and("tags").is(request.getAppVersion().replace(".", "_"));
        queryBuilder.and("mobile").is(request.getMobileNum());
        queryBuilder.and("sendFlag").is(PushConstant.SEND_NO);
        Query query = new Query(queryBuilder);
        List<PushUserCenterInfoDto> list = MongoDBUtil.queryDocumentList(PushConstant.MONGDB_PUSH_USER, query,
                PushUserCenterInfoDto.class);
        if (CollectionUtils.isNotEmpty(list)) {
            List<String> msgIdList = new ArrayList<String>();
            for (PushUserCenterInfoDto info : list) {
                String msgId = info.getMsgId();
                msgIdList.add(msgId);
            }
            setMessageList(msgIdList, request, extrasList);
        }
        return extrasList;
    }

    /**
     * 设置消息
     *
     * @param msgIdList
     * @param request
     * @param extrasList
     */
    private void setMessageList(List<String> msgIdList, HttpBaseRequest request, List<ExtrasDto> extrasList) {
        if (CollectionUtils.isEmpty(msgIdList)) {
            return;
        }
        Criteria queryBuilder = new Criteria();
        queryBuilder.and("sendJPushMessageId").in(msgIdList);
        Query query = new Query(queryBuilder);
        List<PushInfoDto> infolist = MongoDBUtil
                .queryDocumentList(PushConstant.MONGDB_PUSH_PAYLOAD, query, PushInfoDto.class);
        if (CollectionUtils.isNotEmpty(infolist)) {
            for (PushInfoDto pushInfo : infolist) {
                String payload = pushInfo.getPayload();
                if (StringUtils.isNotBlank(payload)) {
                    PayloadDto load = JsonUtils.json2obj(payload, PayloadDto.class);
                    if (load == null) {
                        continue;
                    }
                    // 1:iPhone客户端，2:Android客户端
                    NotificationDto notification = load.getNotification();
                    if (notification == null) {
                        continue;
                    }
                    if (PushConstant.IPHONE == request.getOsType()) {
                        IOSDto ios = notification.getIos();
                        if (ios == null) {
                            continue;
                        }
                        ExtrasDto extras = ios.getExtras();
                        if (extras != null) {
                            extrasList.add(extras);
                        }
                    } else if (PushConstant.ANDRIOD == request.getOsType()) {
                        AndroidDto android = notification.getAndroid();
                        if (android == null) {
                            continue;
                        }
                        ExtrasDto extras = android.getExtras();
                        if (extras != null) {
                            extrasList.add(extras);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void insertPushList(List<PushUserCenterInfoDto> list) {
        MongoDBUtil.insertDocumentList(list, PushConstant.MONGDB_PUSH_USER);
    }

    @Override
    public void insertPushPlatformCenterInfo(PushPlatformCenterInfoDto center) {
        MongoDBUtil.insertDocument(center, PushConstant.MONGDB_PUSH_PLATFORM);
    }

    @Override
    public PushTempletEntityDto getTempletContentByType(Integer type) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("noticeType", type);
        paramMap.put("noticeStatus", PushConstant.NOTICE_STATUS_ENABLE);
        return sqlSessionTemplate.selectOne(NAME_SPACE + "getTempletContentByType", paramMap);
    }

}
