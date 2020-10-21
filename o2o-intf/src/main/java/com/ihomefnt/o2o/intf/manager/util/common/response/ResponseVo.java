package com.ihomefnt.o2o.intf.manager.util.common.response;

import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.domain.common.http.MessageConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;
import lombok.experimental.Accessors;

@ApiModel(description = "基础Vo")
@Data
@Accessors(chain = true)
public class ResponseVo<T> {

	@ApiModelProperty(value = "数据")
	private T data;

	/**
	 * 返回消息
	 */
	@ApiModelProperty(value = "响应信息")
	private String msg;

	/**
	 * 代码
	 */
	@ApiModelProperty(value = "响应码")
	private Long code;

	/**
	 * 是否成功
	 */
	@ApiModelProperty(value = "成功标识")
	private boolean success;

	
	public static <T> ResponseVo<T> success() {
        return success(null,MessageConstant.SUCCESS);
    }

    public static <T> ResponseVo<T> success(T data) {
        return success(data, MessageConstant.SUCCESS);
    }

    public static <T> ResponseVo<T> success(String message) {
        return success(null, message);
    }

    public static <T> ResponseVo<T> success(Long code, T data, String msg) {
        return buildResponse(code, data, msg, true);
    }

    public static <T> ResponseVo<T> success(T data, String msg) {
        return buildResponse(HttpResponseCode.SUCCESS_RESPONSE_VO, data, msg, true);
    }

    public static <T> ResponseVo<T> fail(String message) {
        return fail(HttpResponseCode.FAILED, message);
    }

    public static <T> ResponseVo<T> fail(Long code, String message) {
        return buildResponse(code, null, message, false);
    }

    public static <T> ResponseVo<T> buildResponse(Long code, T data, String msg, boolean success) {
        return new ResponseVo<T>().setCode(code).setMsg(msg).setData(data).setSuccess(success);
    }

}
