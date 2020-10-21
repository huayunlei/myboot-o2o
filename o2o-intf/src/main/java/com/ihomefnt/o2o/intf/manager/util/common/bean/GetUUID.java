package com.ihomefnt.o2o.intf.manager.util.common.bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by piweiwen on 2015年2月7日
 */
public class GetUUID {

    /**
     * 
     * 功能描述: 生成全局唯一ID<br>
     * ID包括：yyMMddhhmmssSSS+17位16进制随机数
     *
     * @return
     */
    public static String createID() {
        DateFormat format = new SimpleDateFormat("yyMMddhhmmssSSS");
        StringBuffer sb = new StringBuffer(32);
        sb.append(format.format(new Date()));
        sb.append(String.valueOf(UUID.randomUUID()).replace("-", "").substring(3, 20));
        return sb.toString();
    }
}
