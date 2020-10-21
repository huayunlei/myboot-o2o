package com.ihomefnt.o2o.intf.manager.util.common.date;

import java.util.Calendar;
import java.util.Date;

/**
 * 时间范围
 * @author ZHAO
 */
public class DateRangeUtils {
	/** 
     * 获取date的月份的时间范围 
     * @param date 
     * @return 
     */  
    public static DateRange getMonthRange(Date date) {  
        Calendar startCalendar = Calendar.getInstance();  
        startCalendar.setTime(date);  
        startCalendar.set(Calendar.DAY_OF_MONTH, 1);  
        setMaxTime(startCalendar);  
          
        Calendar endCalendar = Calendar.getInstance();  
        endCalendar.setTime(date);  
        endCalendar.set(Calendar.DAY_OF_MONTH, endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));  
        setMaxTime(endCalendar);  
          
        return new DateRange(startCalendar.getTime(), endCalendar.getTime());  
    }  
    /** 
     * 获取当前季度的时间范围 
     * @return current quarter 
     */  
    public static DateRange getThisQuarter() {  
        Calendar startCalendar = Calendar.getInstance();  
        startCalendar.set(Calendar.MONTH, ((int) startCalendar.get(Calendar.MONTH) / 3) * 3);  
        startCalendar.set(Calendar.DAY_OF_MONTH, 1);  
        setMinTime(startCalendar);  
          
        Calendar endCalendar = Calendar.getInstance();  
        endCalendar.set(Calendar.MONTH, ((int) startCalendar.get(Calendar.MONTH) / 3) * 3 + 2);  
        endCalendar.set(Calendar.DAY_OF_MONTH, endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));  
        setMaxTime(endCalendar);  
          
        return new DateRange(startCalendar.getTime(), endCalendar.getTime());  
    }  
      
    /** 
     * 获取昨天的时间范围 
     * @return 
     */  
    public static DateRange getYesterdayRange() {  
         Calendar startCalendar = Calendar.getInstance();  
         startCalendar.add(Calendar.DAY_OF_MONTH, -1);  
         setMinTime(startCalendar);  
           
         Calendar endCalendar = Calendar.getInstance();  
         endCalendar.add(Calendar.DAY_OF_MONTH, -1);  
         setMaxTime(endCalendar);  
           
         return new DateRange(startCalendar.getTime(), endCalendar.getTime());  
    }  
      
    /** 
     * 获取当前月份的时间范围 
     * @return 
     */  
    public static DateRange getThisMonth(){  
        Calendar startCalendar = Calendar.getInstance();  
        startCalendar.set(Calendar.DAY_OF_MONTH, 1);  
        setMinTime(startCalendar);  
          
        Calendar endCalendar = Calendar.getInstance();  
        endCalendar.set(Calendar.DAY_OF_MONTH, endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));  
        setMaxTime(endCalendar);  
          
        return new DateRange(startCalendar.getTime(), endCalendar.getTime());  
    }  
      
    /** 
     * 获取上个月的时间范围 
     * @return 
     */  
    public static DateRange getLastMonth(){  
        Calendar startCalendar = Calendar.getInstance();  
        startCalendar.add(Calendar.MONTH, -1);  
        startCalendar.set(Calendar.DAY_OF_MONTH, 1);  
        setMinTime(startCalendar);  
          
        Calendar endCalendar = Calendar.getInstance();  
        endCalendar.add(Calendar.MONTH, -1);  
        endCalendar.set(Calendar.DAY_OF_MONTH, endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));  
        setMaxTime(endCalendar);  
          
        return new DateRange(startCalendar.getTime(), endCalendar.getTime());  
    }  
      
    /** 
     * 获取上个季度的时间范围 
     * @return 
     */  
    public static DateRange getLastQuarter() {  
        Calendar startCalendar = Calendar.getInstance();  
        startCalendar.set(Calendar.MONTH, ((int) startCalendar.get(Calendar.MONTH) / 3 - 1) * 3);  
        startCalendar.set(Calendar.DAY_OF_MONTH, 1);  
        setMinTime(startCalendar);  
          
        Calendar endCalendar = Calendar.getInstance();  
        endCalendar.set(Calendar.MONTH, ((int) endCalendar.get(Calendar.MONTH) / 3 - 1) * 3 + 2);  
        endCalendar.set(Calendar.DAY_OF_MONTH, endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));  
        setMaxTime(endCalendar);  
          
        return new DateRange(startCalendar.getTime(), endCalendar.getTime());  
    }  
      
    private static void setMinTime(Calendar calendar){  
        calendar.set(Calendar.HOUR_OF_DAY, 0);  
        calendar.set(Calendar.MINUTE, 0);  
        calendar.set(Calendar.SECOND, 0);  
        calendar.set(Calendar.MILLISECOND, 0);  
    }  
      
    private static void setMaxTime(Calendar calendar){  
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));  
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));  
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));  
        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));  
    } 
}
