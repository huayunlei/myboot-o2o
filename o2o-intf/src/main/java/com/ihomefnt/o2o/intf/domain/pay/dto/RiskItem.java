/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年11月17日
 * Description:RiskItem.java 
 */
package com.ihomefnt.o2o.intf.domain.pay.dto;

import lombok.Data;

/**
 * @author zhang
 */
@Data
public class RiskItem {

	private String frms_ware_category;// 4010

	private String user_info_mercht_userno;// userId

	private String user_info_dt_register;// YYYYMMDDH24MISS

	private String user_info_bind_phone;// moblie

	private String delivery_addr_province;

	private String delivery_addr_city;

	private String delivery_phone;

	private String logistics_mode;

	private String delivery_cycle;

	private String goods_count;

	private String user_info_identify_state;// 是否实名认证:1:是, 0：无认证

	// 实名认证方式:是实名认证时，必填 1：银行卡认证,2：现场认证,3：身份证远程认证,4：其它认证
	private String user_info_identify_type;

	private String user_info_full_name;// 用户姓名
	private String user_info_id_no;// 用户身份证号

}
