package com.ihomefnt.o2o.service.service.coupon;

import com.ihomefnt.o2o.intf.manager.util.common.bean.JsonUtils;
import com.ihomefnt.o2o.intf.manager.constant.coupon.VoucherConstant;
import com.ihomefnt.o2o.intf.service.coupon.VoucherService;
import com.ihomefnt.o2o.intf.dao.coupon.VoucherDao;
import com.ihomefnt.o2o.intf.domain.coupon.dto.Voucher;
import com.ihomefnt.o2o.intf.domain.coupon.dto.VoucherLog;
import com.ihomefnt.o2o.intf.domain.coupon.dto.VoucherRemark;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



@Service
public class VoucherServiceImpl implements VoucherService {

	@Autowired
	private VoucherDao voucherDao;
	
	@Override
	public List<Voucher> queryVoucherByMobile(String mobile) {
		List<Voucher> list=this.voucherDao.queryVoucherByMobile(mobile);
		for (Voucher voucher : list) {
			//限制订单类型Json转List<String>（给前台用）
			if(StringUtils.isNotBlank(voucher.getOrderTypes())){
				voucher.setOrderTypeList(JsonUtils.json2list(voucher.getOrderTypes(), String.class));
			}
			//限制品类Json转List<String>（给前台用）
			if(StringUtils.isNotBlank(voucher.getProductTypes())){
				voucher.setProductTypeList(JsonUtils.json2list(voucher.getProductTypes(), String.class));
			}
			if(voucher.getCreateTimestamp()!=null){
			
				voucher.setCreateTime(voucher.getCreateTimestamp().getTime());
			}
			String  timeDesc="";
			DateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");   
			//使用开始时间转换（给前台用）
			if(voucher.getStartTimestamp()!=null){
				String startTime = sdf.format(voucher.getStartTimestamp());   
				timeDesc += startTime;				
				voucher.setStartTime(voucher.getStartTimestamp().getTime());
			}
			timeDesc +="~";
			
			//使用结束时间转换（给前台用）
			if(voucher.getEndTimestamp()!=null){
				Timestamp now1=new Timestamp(System.currentTimeMillis());
				Timestamp now2=new Timestamp(System.currentTimeMillis()+VoucherConstant.MONTH_TIME);
				Timestamp endTimestamp =voucher.getEndTimestamp();
				if(endTimestamp.before(now1)){
					//有效期已经超过今天，过期
					voucher.setStatusDisplay(VoucherConstant.TIME_OVER);
				}else if(endTimestamp.after(now1)&&endTimestamp.before(now2)){
					//有效期距离今天不足一个月，快过期
					voucher.setStatusDisplay(VoucherConstant.TIME_QUICK);
				}else{
					//有效期距离今天超过一个月，正常
					voucher.setStatusDisplay(VoucherConstant.TIME_OK);
				}
				String endTime = sdf.format(endTimestamp);  				
				timeDesc += endTime;
				voucher.setEndTime(endTimestamp.getTime());
			}
			if(!timeDesc.equals("~")){
				voucher.setTimeDesc(timeDesc);
			}
			//消费门槛转换（给前台用）
			if(voucher.getMoneyLimit()!=null){
				String ss = voucher.getMoneyLimit().toString().replaceAll("0+?$", "");//去掉后面无用的零
				ss = ss.toString().replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点			
				if(StringUtils.isNotBlank(ss)&&!ss.equals("0")){
					voucher.setMoneyLimitDesc("满"+ss+"元可用");
				}
				
			}
			if (StringUtils.isNotBlank(voucher.getRemark())) {				
				String[] remarks = voucher.getRemark().split("<question>");
				List<VoucherRemark> voucherRemarkList = new ArrayList<VoucherRemark>();
				for(int i=1;i<remarks.length;i++){
					VoucherRemark remark = new VoucherRemark();
					String[] set = remarks[i].split("<answer>");
					if(set.length==2){
						remark.setQuestion(set[0]);
						remark.setAnswer(set[1]);
						voucherRemarkList.add(remark);
					}								
				}
				voucher.setVoucherRemarkList(voucherRemarkList);
				
	        }
			//voucher.setVoucherDesc(VoucherConstant.VOUCHER_DESC);
		}
		return list;
	}

	@Override
	public double queryVoucherAmountByMobile(String mobile) {
		return this.voucherDao.queryVoucherAmountByMobile(mobile);
	}


	@Override
	public List<Voucher> getEnableVoucherList(String mobile,
			double amountPayable) {
		//1.查询个人所有抵用券
		List<Voucher> allList=queryVoucherByMobile(mobile);
		//2.根据订单类型、品类、金额筛选并标记可用的抵用券
		List<Voucher> enableList=new ArrayList<Voucher>();//可用的抵用券集合
		List<Voucher> disableList=new ArrayList<Voucher>();//不可用的抵用券集合
		for (int i = 0; i < allList.size(); i++) {
			int satisfied=0;//满足条件数（金额、使用日期范围2个条件必须全满足才认为是可用的券）
			Voucher cv=allList.get(i);
			
			//2.1判断最低消费
			if(cv.getMoneyLimit()!=null&&cv.getMoneyLimit()>0){//有最低消费限制
				if(amountPayable>=cv.getMoneyLimit()){
					satisfied++;
				}else{
					disableList.add(cv);
					continue;
				}
			}else{//无最低消费限制
				satisfied++;
			}
			
			//2.2判断是否在使用日期范围内
			if(cv.getEndTimestamp()!=null&&cv.getEndTimestamp().before(new Timestamp(System.currentTimeMillis()))){//已过期
				disableList.add(cv);
				continue;
			}
			if(cv.getStartTimestamp()!=null){//有开始使用日期限制
				if(cv.getStartTimestamp().after(new Timestamp(System.currentTimeMillis()))){//使用开始日期还没到
					disableList.add(cv);
					continue;
				}else{
					satisfied++;
				}
			}else{//无开始日期限制
				satisfied++;
			}
			
			//2.5 判断状态是否1:待确认收款2:已生效3:已使用4:已失效
			if(cv.getVoucherStatus()!=null&&cv.getVoucherStatus()==VoucherConstant.STATUS_OK){
				satisfied++;
			}else{
				disableList.add(cv);
				continue;
			}
			if(satisfied==3){//同时满足3个条件
				
				enableList.add(cv);
			}else{
				disableList.add(cv);
			}
			
		}
		//3.抵用券集合合并与排序（按照可用靠前不可用靠后，其次按照面额大的靠前面额小的靠后）
		
		//	3.1可用券按面值排序
		Collections.sort(enableList, new Comparator<Voucher>() {
            public int compare(Voucher arg0, Voucher arg1) {
                return arg1.getParValue().compareTo(arg0.getParValue());
            }
        });
		if(enableList!=null&&!enableList.isEmpty()){
			enableList.get(0).setDefaultSelect(true);//是否默认选中  true:最大金额 
		}
		//	3.2不可用券按面值排序
		Collections.sort(disableList, new Comparator<Voucher>() {
            public int compare(Voucher arg0, Voucher arg1) {
                return arg1.getParValue().compareTo(arg0.getParValue());
            }
        });
		//	3.3合并
		//enableList.addAll(disableList);
		return enableList;
	}
	

	@Override
	public Voucher queryVoucherById(Long pk){
		Voucher voucher= this.voucherDao.queryVoucherById(pk);
		if(voucher==null){
			return null;
		}
		if (StringUtils.isNotBlank(voucher.getRemark())) {				
			String[] remarks = voucher.getRemark().split("<question>");
			List<VoucherRemark> voucherRemarkList = new ArrayList<VoucherRemark>();
			for(int i=1;i<remarks.length;i++){
				VoucherRemark remark = new VoucherRemark();
				String[] set = remarks[i].split("<answer>");
				if(set.length==2){
					remark.setQuestion(set[0]);
					remark.setAnswer(set[1]);
					voucherRemarkList.add(remark);
				}								
			}
			voucher.setVoucherRemarkList(voucherRemarkList);
			
        }
		return voucher;
	}
	
	@Override
	public Voucher queryVoucherByPK(Long pk){
		Voucher voucher= this.voucherDao.queryVoucherByPK(pk);
		if(voucher==null){
			return null;
		}
		if (StringUtils.isNotBlank(voucher.getRemark())) {				
			String[] remarks = voucher.getRemark().split("<question>");
			List<VoucherRemark> voucherRemarkList = new ArrayList<VoucherRemark>();
			for(int i=1;i<remarks.length;i++){
				VoucherRemark remark = new VoucherRemark();
				String[] set = remarks[i].split("<answer>");
				if(set.length==2){
					remark.setQuestion(set[0]);
					remark.setAnswer(set[1]);
					voucherRemarkList.add(remark);
				}								
			}
			voucher.setVoucherRemarkList(voucherRemarkList);
			
        }
		return voucher;
	}
	
	@Override
	public int updateVoucherById(Voucher voucher){
		return this.voucherDao.updateVoucherById(voucher);
	}

	
	@Override
	public int insertVoucherLog(VoucherLog log){
		return this.voucherDao.insertVoucherLog(log);
	}

}
