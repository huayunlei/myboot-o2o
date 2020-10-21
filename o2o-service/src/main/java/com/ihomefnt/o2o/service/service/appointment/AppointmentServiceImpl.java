package com.ihomefnt.o2o.service.service.appointment;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ihomefnt.o2o.intf.domain.appointment.vo.response.AppointmentTime;
import com.ihomefnt.o2o.intf.service.appointment.AppointmentService;

@Service
public class AppointmentServiceImpl implements AppointmentService{
	
	static Map<Integer, String> weekMap = new HashMap<Integer, String>();
	static {
		weekMap.put(0,"星期日");
		weekMap.put(1,"星期一");
		weekMap.put(2,"星期二");
		weekMap.put(3,"星期三");
		weekMap.put(4,"星期四");
		weekMap.put(5,"星期五");
		weekMap.put(6,"星期六");
	}
	static Map<Integer, Integer> weekDayMap = new HashMap<Integer, Integer>();
	static {
		weekDayMap.put(2,6);
		weekDayMap.put(3,5);
		weekDayMap.put(4,4);
		weekDayMap.put(5,3);
		weekDayMap.put(6,2);
		weekDayMap.put(7,1);
		weekDayMap.put(1,0);
	}
	//下午两点为分界点
	static List<String> timeStr = new ArrayList<String>();
	static {
		timeStr.add("10时-12时");
		timeStr.add("12时-14时");
		timeStr.add("14时-17时");
	}
	
	@Override
	public List<AppointmentTime> getDateTime() {
		List<AppointmentTime> result = new ArrayList<AppointmentTime>();
		Calendar c = Calendar.getInstance();
		
		// ---------------处理今天start------------------------
		int hour = c.get(Calendar.HOUR_OF_DAY);//取几点
		AppointmentTime  todayApp = new AppointmentTime();
		List<String> todayTimeStr = new ArrayList<String>();
		if(0 <= hour && hour < 10){
			todayApp.setWeekStr("今天");
			todayTimeStr.addAll(timeStr);
			todayApp.setTimeSlotList(todayTimeStr);
			result.add(todayApp);
		}
		
		if(10 <= hour && hour < 12){
			todayApp.setWeekStr("今天");
			todayTimeStr.add(timeStr.get(1));
			todayTimeStr.add(timeStr.get(2));
			todayApp.setTimeSlotList(todayTimeStr);
			result.add(todayApp);
		}
		
		if(12 <= hour && hour < 14){
			todayApp.setWeekStr("今天");
			todayTimeStr.add(timeStr.get(2));
			todayApp.setTimeSlotList(todayTimeStr);
			result.add(todayApp);
		}
		// ---------------处理今天end------------------------

		try{
	        Format f = new SimpleDateFormat("yyyy-MM-dd");
	        String flag = "";
	        String compareFlag = f.format(getNextMonday(new Date()));
	        for(int i=1;i<=7;i++){
	        	AppointmentTime appTime = new AppointmentTime();
	        	
	            c.set(Calendar.DAY_OF_MONTH,c.get(Calendar.DAY_OF_MONTH)+1);
	            String temp = f.format(c.getTime());
	        	if(compareFlag.equals(temp)){
	        		flag="下";
	        	}
	            if(i==1){
	            	appTime.setWeekStr("明天");
	            	appTime.setTimeSlotList(timeStr);
	            } else {
	            	int j = c.get(Calendar.DAY_OF_WEEK);
	            	String weekStr = flag+weekMap.get(j-1);
	            	appTime.setWeekStr(weekStr);
	            	appTime.setTimeSlotList(timeStr);
	            }
	            result.add(appTime);
	        }
		} catch(Exception e){
			
		}
		return result;
	}
	
	/**
	 * 以一周一开始的第一天的下周一
	 * @param date
	 * @return
	 * @throws Exception
	 */
    public static Date getNextMonday(Date date)throws Exception{
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week = cal.get(Calendar.DAY_OF_WEEK);
        cal.add(Calendar.DAY_OF_MONTH,weekDayMap.get(week)+1);
        return cal.getTime();
    }
	
    public Date getSunday(Date date)throws Exception{
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week = cal.get(Calendar.DAY_OF_WEEK);
        if(week>1){
            cal.add(Calendar.DAY_OF_MONTH,-(week-1)+7);
        }else{
            cal.add(Calendar.DAY_OF_MONTH,1-week+7);
        }
        return cal.getTime();
    }

    public String getDateFromWeek(String str){
    	String res = "";
    	
    	Map<String,String> dateWeek = new HashMap<String,String>();
		Calendar c = Calendar.getInstance();
		try{
	        Format f = new SimpleDateFormat("yyyy-MM-dd");
	        dateWeek.put("今天",f.format(c.getTime()));
	        
	        String flag = "";
	        String temp = "";
	        String compareFlag = f.format(getNextMonday(new Date()));
	        for(int i=1;i<=7;i++){
	            c.set(Calendar.DAY_OF_MONTH,c.get(Calendar.DAY_OF_MONTH)+1);
	            temp = f.format(c.getTime());
	        	if(compareFlag.equals(temp)){
	        		flag="下";
	        	}
	            if(i==1){
	            	dateWeek.put("明天",temp);
	            } else {
	            	int j = c.get(Calendar.DAY_OF_WEEK);
	            	String weekStr = flag+weekMap.get(j-1);
	            	dateWeek.put(weekStr,temp);
	            }
	        }
		} catch(Exception e){
			
		}
		res = dateWeek.get(str);
    	return res;
    }
    
}
