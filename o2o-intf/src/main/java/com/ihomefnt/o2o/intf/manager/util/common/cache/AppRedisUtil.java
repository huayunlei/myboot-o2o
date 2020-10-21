/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年2月9日
 * Description:AppRedisUtil.java
 */
package com.ihomefnt.o2o.intf.manager.util.common.cache;

import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.o2o.intf.manager.util.common.bean.StringUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.AliImageUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.Picture;
import com.ihomefnt.o2o.intf.manager.util.common.image.QiniuImageUtils;
import com.ihomefnt.zeus.finder.ServiceCaller;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

import com.ihomefnt.common.util.RedisUtil;

import java.util.*;

/**
 * @author zhang
 */
public class AppRedisUtil {

    private static final Logger LOG = LoggerFactory.getLogger(AppRedisUtil.class);

    private static final String IMAGE_SIZE_REDIS_KEY = "pictureSize";

    private static final String APP_INTERFACE_REDIS_KEY = "app_interface";

    /**
     * 原子递增(指定值)
     *
     * @param key
     * @return
     */
    public static long incrBy(String key, long value) {
        long result = -1;
        Jedis jedis = null;
        try {
            jedis = RedisUtil.getResource();
            result = jedis.incrBy(key, value);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        } finally {
            RedisUtil.returnResource(jedis);
        }
        return result;
    }

    public static String get(String key) {
        return RedisUtil.get(key);
    }

    public static long del(String key) {
        return RedisUtil.del(key);
    }

    public static Picture getRedisImageSize(String url, ServiceCaller serviceCaller) {
        Picture picture = null;
        if (RedisUtil.mapObjectExists(IMAGE_SIZE_REDIS_KEY, url)) {
            try (Jedis jedis = RedisUtil.getResource()) {
                String json = jedis.hget(IMAGE_SIZE_REDIS_KEY, url);
                picture = JsonUtils.json2obj(json, Picture.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {//如果redis没有，则从七牛取宽高，然后存入redis //新增阿里云链接宽高逻辑
            picture = AliImageUtil.getImageParam(url, serviceCaller);
            if (picture != null) {
                setRedisImageSize(url, picture.getWidth(), picture.getHeight());
            } else {//异常情况处理（图片非七牛图片）
                picture = new Picture();
                picture.setUrl(url);
                picture.setWidth(750);
                picture.setHeight(410);
                picture.setAspectRatio(picture.getRatio());
            }
        }
        return picture;
    }

    public static void setRedisImageSize(String url, Integer width, Integer height) {
        if (StringUtil.isNullOrEmpty(url) || width == null || height == null) {
            return;
        }
        try (Jedis jedis = RedisUtil.getResource()) {
            Picture picture = new Picture(url, width, height);
            picture.setWidth(width);
            picture.setHeight(height);
            String jsonStr = JsonUtils.obj2json(picture);
            Map<String, String> map = new HashMap<>();
            map.put(url, jsonStr);
            jedis.hmset(IMAGE_SIZE_REDIS_KEY, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Picture> getRedisImageSizeList(List<String> urls) {
        List<Picture> imageSizeList = new ArrayList<>();
        try (Jedis jedis = RedisUtil.getResource()) {
            List<String> json = jedis.hmget(IMAGE_SIZE_REDIS_KEY, urls.toArray(new String[urls.size()]));
            if (CollectionUtils.isNotEmpty(json)) {
                for (int i = 0; i < json.size(); i++) {
                    Picture picture = null;
                    String url = urls.get(i);
                    if (json.get(i) != null) {
                        picture = JsonUtils.json2obj(json.get(i), Picture.class);
                        picture.setUrl(url);
                        picture.setAspectRatio(picture.getRatio());
                    } else {
                        Map<String, Object> imageSize = QiniuImageUtils.getImageSize(url);
                        Integer imgWidth = (Integer) imageSize.get("width");
                        Integer imgHeight = (Integer) imageSize.get("height");
                        if (imageSize != null && imgWidth != null && imgHeight != null) {
                            picture = new Picture(url, imgWidth, imgHeight);
                            setRedisImageSize(url, imgWidth, imgHeight);
                        }
                    }
                    imageSizeList.add(picture);
                }
            }
            return imageSizeList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, Picture> getRedisImageSizeMap(List<String> urls, ServiceCaller serviceCaller) {
        Map<String, Picture> pictureMap = new HashMap<>();
        try (Jedis jedis = RedisUtil.getResource()) {
            List<String> json = jedis.hmget(IMAGE_SIZE_REDIS_KEY, urls.toArray(new String[urls.size()]));
            if (CollectionUtils.isNotEmpty(json)) {
                for (int i = 0; i < json.size(); i++) {
                    Picture picture = null;
                    String url = urls.get(i);
                    if (json.get(i) != null) {
                        picture = JsonUtils.json2obj(json.get(i), Picture.class);
                        picture.setUrl(url);
                        picture.setAspectRatio(picture.getRatio());
                    } else {
                        picture = AliImageUtil.getImageParam(url, serviceCaller);
                        if (picture != null) {
                            setRedisImageSize(url, picture.getWidth(), picture.getHeight());
                        } else {
                            picture = new Picture();
                            picture.setUrl(url);
                            picture.setWidth(750);
                            picture.setHeight(420);
                            picture.setAspectRatio(picture.getRatio());
                        }
                    }
                    pictureMap.put(url, picture);
                }
            }
            return pictureMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setInterfaceRedis(String interfaceKey, String key, ResponseVo responseVo) {
        if (StringUtil.isNullOrEmpty(key) || responseVo == null) {
            return;
        }
        RedisUtil.set(APP_INTERFACE_REDIS_KEY + ":" + interfaceKey + ":" + key, JsonUtils.obj2json(responseVo), 3600);
    }

    public static ResponseVo getInterfaceRedis(String interfaceKey, String key) {
        String json = RedisUtil.get(APP_INTERFACE_REDIS_KEY + ":" + interfaceKey + ":" + key);
        if (StringUtil.isNullOrEmpty(json)) {
            return null;
        } else {
            ResponseVo responseVo = JsonUtils.json2obj(json, ResponseVo.class);
            return responseVo;
        }
    }

    public static void setDataRedis(String key, String content) {
        if (StringUtil.isNullOrEmpty(key) || StringUtil.isNullOrEmpty(content)) {
            return;
        }
        RedisUtil.set( key, content, 3600);
    }

    public static String getDataRedis(String key) {
        String json = RedisUtil.get(key);
        if (StringUtil.isNullOrEmpty(json)) {
            return null;
        }
        return json;
    }

    public static Picture getRedisImageSizeNew(String url, ServiceCaller serviceCaller) {
        Picture picture = null;
        if (RedisUtil.mapObjectExists(IMAGE_SIZE_REDIS_KEY, url)) {
            try (Jedis jedis = RedisUtil.getResource()) {
                String json = jedis.hget(IMAGE_SIZE_REDIS_KEY, url);
                picture = JsonUtils.json2obj(json, Picture.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {// 如果redis没有，则从七牛取宽高，然后存入redis
            picture = QiniuImageUtils.getImageSizeNew(url, serviceCaller);
            if (picture != null) {
                setRedisImageSize(url, picture.getWidth(), picture.getHeight());
            }
        }
        return picture;
    }

    public static void set(String key, String value, int seconds) {

        if (StringUtil.isNullOrEmpty(key) || StringUtil.isNullOrEmpty(value)) {
            return;
        }

        try (Jedis jedis = RedisUtil.getResource()) {
            jedis.setex(key, seconds, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setPersist(String key, String value) {

        if (StringUtil.isNullOrEmpty(key) || StringUtil.isNullOrEmpty(value)) {
            return;
        }

        try (Jedis jedis = RedisUtil.getResource()) {
            jedis.set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成缓存key（拼冒号）
     *
     * @param namespace
     * @param keys
     * @return
     */
    public static String generateCacheKey(String namespace, Object... keys) {
        StringBuilder out = new StringBuilder();
        out.append(namespace);
        if (keys != null && keys.length > 0) {
            out.append(":");
            for (int i = 0; i < keys.length; i++) {
                out.append(keys[i]);
                if (i != keys.length - 1) {
                    out.append(":");
                }
            }
        }
        return out.toString();
    }

    /**
     * 生成不拼冒号的缓存key
     *
     * @param namespace
     * @param keys
     * @return
     */
    public static String generateNoColonCacheKey(String namespace, Object... keys) {
        StringBuilder out = new StringBuilder();
        out.append(namespace);
        if (keys != null && keys.length > 0) {
            out.append(":");
            for (int i = 0; i < keys.length; i++) {
                out.append(keys[i]);
            }
        }
        return out.toString();
    }

    public static Set<String> keys(String key) {
        Jedis resource = RedisUtil.getResource();
        return resource.keys(key);
    }

}
