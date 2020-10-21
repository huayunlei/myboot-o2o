/*
 * Copyright (C) 2006-2016 AiJia All rights reserved
 * Author: zhang
 * Date: 2017年9月13日
 * Description:ErrorEntity.java 
 */
package com.ihomefnt.o2o.intf.domain.mail.dto;

import com.ihomefnt.common.api.ResponseVo;
import lombok.Data;

/**
 * @author zhang
 */
@Data
public class ErrorEntity {

	private String wcmEmail; // 收件人,注意是配置在wcm.t_dic的key_desc这个字段,如:CREATE_ART_ERROR_MAIL

	private String title;// 邮件标题

	private String zeusMethod;// 执行方法名

	private Object param;// 请求参数

	private ResponseVo<?> responseVo;// 输出结果

	private String errorMsg;// 异常信息
}
