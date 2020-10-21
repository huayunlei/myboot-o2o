package com.ihomefnt.o2o.mapi.controller;

import com.ihomefnt.common.util.ConvertUtil;
import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpMessage;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;





/**
 * @ClassName: 基本控制类 (BaseController.java)
 * 
 * @Description: 提供一些在控制层中常用的方法
 * 
 * @Date: 2016年2月17日 上午9:58:48
 * @Author shenchen
 * @Version 1.0
 */

public class MapiBaseController {

	private static final Log logger = LogFactory.getLog(MapiBaseController.class);

	protected static void flushResponse(HttpServletResponse response,
			Object responseContent) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		try (PrintWriter writer = response.getWriter();) {
			writer.write(ConvertUtil.getString(responseContent));
			writer.flush();
		} catch (IOException e) {
			logger.error(e);
		}

	}

	protected static void flushResponseMsg(HttpServletResponse response,
			boolean success, String msg) {

		Map<String, Object> map = new HashMap<>();
		map.put("success", success);
		map.put("msg", msg);
		flushResponse(response, JsonUtils.obj2json(map));
	}

	/**
	 * <p>
	 * 从request中获取参数根据键值对形成Map. <br>
	 * 注意:对于数组参数，只拷贝了第1个元素.<br>
	 * 对于全空格的数据，仍然保留，在保存修改时可能需要.
	 * </p>
	 * 
	 * @param request
	 * @return map
	 */
	protected static Map<String, String> getParameter(HttpServletRequest request) {
		Map<String, String> paramValue = new HashMap<String, String>();
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String key = names.nextElement();
			String value = request.getParameter(key);
			paramValue.put(key, value);
		}
		return paramValue;
	}

	/**
	 * <p>
	 * 从request中获取参数根据键值对形成Map. <br>
	 * 对于全空格的数据，仍然保留，在保存修改时可能需要.
	 * </p>
	 * 
	 * @param request
	 * @return map
	 */
	protected static Map<String, String[]> getParameterValues(
			HttpServletRequest request) {
		Map<String, String[]> paramValues = new HashMap<String, String[]>();
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String key = names.nextElement();
			String value[] = request.getParameterValues(key);
			paramValues.put(key, value);
		}
		return paramValues;
	}

	/**
	 * 获得当前页码
	 * 
	 * @param request
	 * @return int
	 */
	protected static int getPageNo(HttpServletRequest request) {
		int pageNum = ConvertUtil.getInteger(request.getParameter("pageNo"));
		return pageNum != 0 ? pageNum : 1;
	}

	/**
	 * 获得每页大小
	 * 
	 * @param request
	 * @return int
	 */
	protected static int getPageSize(HttpServletRequest request) {
		int numPerPage = ConvertUtil.getInteger(request
				.getParameter("pageSize"));
		return numPerPage != 0 ? numPerPage : 10;
	}

	/**
	 * 获得总数
	 * 
	 * @param request
	 * @return int
	 */
	protected static int getTotalCount(HttpServletRequest request) {
		return ConvertUtil.getInteger(request.getParameter("totalCount"));
	}
	
	/**
     * 
     * 通用返回
     * 
     */
	protected  ResponseEntity<HttpBaseResponse> returnResponse(HttpBaseResponse baseResponse){
		baseResponse.setCode(HttpResponseCode.SUCCESS);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Access-Control-Allow-Origin", "*");
        headers.set("Access-Control-Request-Method", "post");
        return new ResponseEntity<HttpBaseResponse>(baseResponse, headers, HttpStatus.OK);
    }
	
	/**
	 * 返回参数错误(默认返回：参数为空或者参数格式不正确)
	 * @param baseResponse
	 * @return
	 * @author shenchen
	 * @date 2016年3月25日 下午3:45:46
	 */
	protected  ResponseEntity<HttpBaseResponse> returnParameterError(){
		String className=Thread.currentThread().getStackTrace()[2].getClassName();
		String methodName=Thread.currentThread().getStackTrace()[2].getMethodName();
		logger.info(className+"."+methodName+":"+MessageConstant.PARAMS_NOT_EXISTS);
		HttpBaseResponse baseResponse = new HttpBaseResponse();
		baseResponse.setCode(HttpResponseCode.FAILED);
		baseResponse.setObj(null);
		HttpMessage message = new HttpMessage();
		message.setMsg(MessageConstant.PARAMS_NOT_EXISTS);
		baseResponse.setExt(message);
		return returnResponse(baseResponse);
    }
	
	/**
	 * 返回参数错误
	 * @param baseResponse
	 * @return
	 * @author shenchen
	 * @date 2016年3月25日 下午3:45:46
	 */
	protected  ResponseEntity<HttpBaseResponse> returnError(String message){
		String className=Thread.currentThread().getStackTrace()[2].getClassName();
		String methodName=Thread.currentThread().getStackTrace()[2].getMethodName();
		logger.info(className+"."+methodName+":"+message);
		HttpBaseResponse baseResponse = new HttpBaseResponse();
		baseResponse.setCode(HttpResponseCode.FAILED);
		baseResponse.setObj(null);
		HttpMessage httpMessage = new HttpMessage();
		httpMessage.setMsg(message);
		baseResponse.setExt(httpMessage);
		return returnResponse(baseResponse);
    }
	
	/**
	 * 返回参数错误
	 * @param baseResponse
	 * @return
	 * @author shenchen
	 * @date 2016年3月25日 下午3:45:46
	 */
	protected  ResponseEntity<HttpBaseResponse> returnError(Object obj,String message){
		String className=Thread.currentThread().getStackTrace()[2].getClassName();
		String methodName=Thread.currentThread().getStackTrace()[2].getMethodName();
		logger.info(className+"."+methodName+":"+message);
		HttpBaseResponse baseResponse = new HttpBaseResponse();
		baseResponse.setCode(HttpResponseCode.FAILED);
		baseResponse.setObj(obj);
		HttpMessage httpMessage = new HttpMessage();
		httpMessage.setMsg(message);
		baseResponse.setExt(httpMessage);
		return returnResponse(baseResponse);
    }

}
