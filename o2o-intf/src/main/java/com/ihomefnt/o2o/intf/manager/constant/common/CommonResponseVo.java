package com.ihomefnt.o2o.intf.manager.constant.common;


import com.ihomefnt.o2o.intf.domain.common.http.HttpMessage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @description:
 * @author: xiamingyu
 * @create: 2018-08-17 17:30
 */
@ApiModel(description = "响应内容")
public class CommonResponseVo<T> {

    /**
     * 响应代码
     */
    @ApiModelProperty(value = "结果码")
    private Long code = 1L;

    /**
     * 返回消息
     */
    @ApiModelProperty(value = "响应内容")
    private String msg;

    /**
     * 响应数据
     */
    @ApiModelProperty(value = "响应数据")
    private T obj;

    /**
     * 是否成功
     */
    @ApiModelProperty(value = "请求结果")
    private boolean success;

    @ApiModelProperty("APP附加信息")
    private Object ext;

    public CommonResponseVo() {
    }

    public CommonResponseVo(Long code, String msg) {
        this.code = code;
        this.msg = msg;
        HttpMessage httpMessage = new HttpMessage();
        httpMessage.setMsg(msg);
        this.setExt(httpMessage);
    }

    private CommonResponseVo(ResponseCodeEnum responseEnum) {
        this.code = responseEnum.getCode();
        this.msg = responseEnum.getMsg();
        this.success = responseEnum.isSuccess();
        HttpMessage httpMessage = new HttpMessage();
        httpMessage.setMsg(responseEnum.getMsg());
        this.setExt(httpMessage);
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getObj() {
        return obj;
    }

    public CommonResponseVo<T> setObj(T obj) {
        this.obj = obj;
        return this;
    }

    public Object getExt() {
        return ext;
    }

    public void setExt(Object ext) {
        this.ext = ext;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static CommonResponseVo buildSuccessResponse(Long code, String msg) {
        CommonResponseVo commonResponseVo = new CommonResponseVo(code,msg);
        commonResponseVo.setSuccess(true);
        HttpMessage httpMessage = new HttpMessage();
        httpMessage.setMsg(msg);
        commonResponseVo.setExt(httpMessage);
        return commonResponseVo;
    }

    public static CommonResponseVo buildFailedResponse(Long code, String msg) {
        CommonResponseVo commonResponseVo = new CommonResponseVo(code,msg);
        commonResponseVo.setSuccess(false);
        HttpMessage httpMessage = new HttpMessage();
        httpMessage.setMsg(msg);
        commonResponseVo.setExt(httpMessage);
        return commonResponseVo;
    }

    public static CommonResponseVo buildResponse(ResponseCodeEnum responseEnum) {
        CommonResponseVo commonResponseVo = new CommonResponseVo(responseEnum);
        return commonResponseVo;
    }

    public static CommonResponseVo buildResponse(CommonResponseVo commonResponseVo) {
        HttpMessage httpMessage = new HttpMessage();
        httpMessage.setMsg(commonResponseVo.getMsg());
        commonResponseVo.setExt(httpMessage);
        return commonResponseVo;
    }
}