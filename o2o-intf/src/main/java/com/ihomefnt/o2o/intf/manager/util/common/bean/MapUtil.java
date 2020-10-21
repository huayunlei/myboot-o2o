package com.ihomefnt.o2o.intf.manager.util.common.bean;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author liyonggang
 * @create 2018-12-11 15:33
 */
public class MapUtil {

    public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        Map<K, V> hashMap = Maps.newHashMap();
        hashMap.put(k1,v1);
        hashMap.put(k2,v2);
        hashMap.put(k3,v3);
        hashMap.put(k4,v4);
        hashMap.put(k5,v5);
        return hashMap;
    }
    public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        Map<K, V> hashMap = Maps.newHashMap();
        hashMap.put(k1,v1);
        hashMap.put(k2,v2);
        hashMap.put(k3,v3);
        hashMap.put(k4,v4);
        return hashMap;
    }
    public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3) {
        Map<K, V> hashMap = Maps.newHashMap();
        hashMap.put(k1,v1);
        hashMap.put(k2,v2);
        hashMap.put(k3,v3);
        return hashMap;
    }
    public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2) {
        Map<K, V> hashMap = Maps.newHashMap();
        hashMap.put(k1,v1);
        hashMap.put(k2,v2);
        return hashMap;
    }

    public static <K, V> Map<K, V> of(K k1, V v1) {
        Map<K, V> hashMap = Maps.newHashMap();
        hashMap.put(k1,v1);
        return hashMap;
    }
}
