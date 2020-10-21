package com.ihomefnt.o2o.intf.manager.util.common.bean;

public class IntegerUtil {

    public static boolean isEmpty(Integer num) {
        if (num == null || num <= 0) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(Integer num) {
        if (num == null || num <= 0) {
            return false;
        }
        return true;
    }

    public static boolean isNullOrZero(Object obj) {
        if (obj == null) {
            return true;
        } else if (obj instanceof Integer && obj.equals(0)) {
            return true;
        }
        return false;
    }

    public static boolean equals(Object obj, Integer num) {
        return obj instanceof Integer && obj.equals(num);
    }

}
