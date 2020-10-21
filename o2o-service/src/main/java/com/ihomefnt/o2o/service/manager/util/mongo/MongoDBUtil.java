/*
 * Copyright (C) 2006-2012 Aijia All rights reserved
 * Author: Ivan Shen
 * Date: 2016年11月1日
 * Description:MongoDBUtil.java
 */
package com.ihomefnt.o2o.service.manager.util.mongo;

import com.alibaba.ttl.threadpool.TtlExecutors;
import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.common.util.ServiceLocator;
import com.ihomefnt.common.util.StringUtil;
import com.ihomefnt.common.util.mongodb.log.ErrorLog;
import com.ihomefnt.common.util.mongodb.log.ParameterLog;
import com.ihomefnt.o2o.intf.manager.exception.BusinessException;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * MongoDB工具类
 *
 * @author Ivan Shen
 */
public class MongoDBUtil {

    private static MongoTemplate mongoTemplate = (MongoTemplate) ServiceLocator.init().getService(MongoTemplate.class);

    private static final ExecutorService exec = TtlExecutors.getTtlExecutorService(Executors.newFixedThreadPool(100));

    private static Logger log = LoggerFactory.getLogger(MongoDBUtil.class);

    /**
     * 创建集合
     *
     * @param collectionName
     */
    public static MongoCollection<Document> createCollection(String collectionName) {
        return mongoTemplate.createCollection(collectionName);
    }

    /**
     * 获取集合
     *
     * @param collectionName
     * @return
     */
    public static MongoCollection<Document> getCollection(String collectionName) {
        return mongoTemplate.getCollection(collectionName);
    }

    /**
     * 插入单个对象
     *
     * @param object
     * @param collectionName
     */
    public static void insertDocument(Object object, String collectionName) {
        mongoTemplate.insert(object, collectionName);
    }

    /**
     * 同时插入多个文档
     *
     * @param batchToSave
     * @param collectionName
     */
    public static void insertDocumentList(Collection<? extends Object> batchToSave, String collectionName) {
        mongoTemplate.insert(batchToSave, collectionName);
    }

    /**
     * 查询文档列表
     *
     * @param collectionName
     */
    public static <T> List<T> queryDocumentList(String collectionName, Query query, Class<T> c) {
        return mongoTemplate.find(query, c, collectionName);
    }

    /**
     * 查询文档总数
     *
     * @param collectionName
     */
    public static Integer queryDocumentCount(String collectionName, Query query) {
        // 目前认为数据量在20亿以内(int类型方便分页使用)
        Long count = mongoTemplate.count(query, collectionName);
        return count.intValue();
    }

    /**
     * 查询记录对象,用于唯一主键查询一条记录
     *
     * @param collectionName
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    public static <T> T queryDocument(String collectionName, String objectIdStr, Class<T> c) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(objectIdStr));
        return mongoTemplate.findOne(query, c, collectionName);
    }

    private static <T> DBObject bean2DBObject(T bean) {
        if (bean == null) {
            return null;
        }
        Map<String, Object> map = JsonUtils.obj2map(bean);
        if (map.containsKey("id")) {
            map.put("_id", map.get("id"));
            map.remove("id");
        }
        DBObject obj = new BasicDBObject();
        obj.putAll(map);
        return obj;
    }

    /**
     * 只更新传入的非空字段
     *
     * @param collectionName
     * @param updateObject
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static long updateDocumentAsSet(String collectionName, String id, Object updateObject) {

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        DBObject dbObjectUpdate = bean2DBObject(updateObject);
        if (null == dbObjectUpdate) {
            throw new BusinessException("DBObject bean2DBObject is null ");
        }
        Update update = new Update();
        for (String key : dbObjectUpdate.keySet()) {
            update.set(key, dbObjectUpdate.get(key));
        }
        UpdateResult result = mongoTemplate.updateMulti(query, update, updateObject.getClass(), collectionName);
        return result.getModifiedCount();
    }

    /**
     * 以传入对象覆盖原有对象
     *
     * @param collectionName
     * @param updateObject
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static long updateDocumentAsOverride(String collectionName, String id, Object updateObject) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        DBObject dbObjectUpdate = bean2DBObject(updateObject);
        if (null == dbObjectUpdate) {
            throw new BusinessException("DBObject bean2DBObject is null ");
        }
        Update update = new Update();
        for (String key : dbObjectUpdate.keySet()) {
            update.set(key, dbObjectUpdate.get(key));
        }
        UpdateResult result = mongoTemplate.updateMulti(query, update, updateObject.getClass(), collectionName);
        return result.getModifiedCount();
    }

    /**
     * @param collectionName
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static long deleteDocument(String collectionName, String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        DeleteResult result = mongoTemplate.remove(query, collectionName);
        return result.getDeletedCount();
    }

    /**
     * 增加（减少）单条记录的某个字段的数值
     *
     * @param collectionName
     * @param id             mongoId
     * @param fieldName      需要修改的字段名
     * @param value          增加的值（为负数为减少）
     * @return
     */
    public static <T> T inc(String collectionName, String id, String fieldName, Number value, Class<T> clazz) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Update update = new Update();
        update.inc(fieldName, value);

        return mongoTemplate.findAndModify(query, update, clazz, collectionName);
    }

    /**
     * 添加入参日志
     *
     * @param param
     * @param collectionName
     * @return 日志ID
     */
    public static String addParamLog(Object param, final String collectionName) {
        if (param != null) {
            final ParameterLog parameterLog = new ParameterLog();
            parameterLog.setParam(param);
            parameterLog.setCreateTime(new Date());

            Callable<String> call = new Callable<String>() {
                public String call() throws Exception {
                    insertDocument(parameterLog, collectionName);
                    return parameterLog.getId();
                }
            };

            try {
                Future<String> future = exec.submit(call);
                return future.get(1000, TimeUnit.MILLISECONDS); // 任务处理超时时间设为1000ms
            } catch (TimeoutException ex) {
                log.error(ex.getMessage(), ex);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }

            return parameterLog.getId();
        }
        return null;
    }

    /**
     * 添加错误日志
     *
     * @param errorMsg
     * @param t
     * @param fkId
     * @param collectionName
     * @return
     */
    public static void addErrorLog(String errorMsg, Throwable t, String fkId, final String collectionName) {
        if (StringUtil.isNotBlank(errorMsg)) {
            final ErrorLog errorLog = new ErrorLog();
            errorLog.setErrorMsg(errorMsg);
            errorLog.setT(t);
            errorLog.setFkId(fkId);
            errorLog.setCreateTime(new Date());

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    insertDocument(errorLog, collectionName);
                }
            };

            try {
                exec.execute(runnable);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 添加错误日志
     *
     * @param errorMsg
     * @param fkId
     * @param collectionName
     * @return
     */
    public static void addErrorLog(String errorMsg, String fkId, String collectionName) {
        addErrorLog(errorMsg, null, fkId, collectionName);
    }

    /**
     * 添加错误日志
     *
     * @param errorMsg
     * @param collectionName
     * @return
     */
    public static void addErrorLog(String errorMsg, String collectionName) {
        addErrorLog(errorMsg, null, null, collectionName);
    }

}
