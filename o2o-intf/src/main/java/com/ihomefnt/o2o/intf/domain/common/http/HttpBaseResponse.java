package com.ihomefnt.o2o.intf.domain.common.http;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * http返回基础bean
 *
 * @param <T>
 */
@ApiModel(description = "基础Vo")
@Data
@Accessors(chain = true)
public class HttpBaseResponse<T> implements Serializable {
	private static final long serialVersionUID = -4440398715216340165L;

	@ApiModelProperty("code")
    private Long code; //ret code, 0x00: business success; others: error code;

    @ApiModelProperty("ext")
    private Object ext;//Java bean(must conform Java bean standard), extra data; will be converted to bean string when return to client

    @ApiModelProperty("obj")
    private T obj;//Java bean, extra data

    /**
     * 是否成功
     */
    @ApiModelProperty(value = "请求结果")
    private boolean success;

    public static <T> HttpBaseResponse<T> success() {
        return success(null,MessageConstant.SUCCESS);
    }

    public static <T> HttpBaseResponse<T> success(T obj) {
        return success(obj, MessageConstant.SUCCESS);
    }

    public static <T> HttpBaseResponse<T> success(String message) {
        return success(null, message);
    }

    public static <T> HttpBaseResponse<T> success(T obj, String msg) {
        return buildResponse(HttpResponseCode.SUCCESS, obj, msg);
    }

    public static <T> HttpBaseResponse<T> fail(String message) {
        return fail(HttpResponseCode.FAILED, message);
    }

    public static <T> HttpBaseResponse<T> fail(Long code, String message) {
        return buildResponse(code, null, message);
    }

    public static <T> HttpBaseResponse<T> buildResponse(Long code, T obj, String msg) {
        return new HttpBaseResponse<T>().setCode(code).setExt(new HttpMessage(msg)).setObj(obj).setSuccess(HttpResponseCode.SUCCESS.equals(code));
    }

}
