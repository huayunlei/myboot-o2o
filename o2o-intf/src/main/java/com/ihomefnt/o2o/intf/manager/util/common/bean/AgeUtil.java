/*
 * Author: ZHAO
 * Date: 2018/10/19
 * Description:AgeUtil.java
 */
package com.ihomefnt.o2o.intf.manager.util.common.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 出生年月获取年龄
 *
 * @author ZHAO
 */
public class AgeUtil {
    //由出生日期获得年龄  
    public static int getAge(String strDate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDay = null;
        try {
            birthDay = sdf.parse(strDate);
        } catch (ParseException e) {
            /*e.printStackTrace();*/
            return 0;
        }
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) {
            /*throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");*/
            return 0;
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            }else{
                age--;
            }
        }
        return age;
    }
}
