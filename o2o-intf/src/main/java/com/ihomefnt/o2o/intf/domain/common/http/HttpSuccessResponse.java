package com.ihomefnt.o2o.intf.domain.common.http;

public class HttpSuccessResponse extends HttpBaseResponse{
    private static HttpMessage message = new HttpMessage();

    static {
        message.setMsg(MessageConstant.SUCCESS);
    }

    public static HttpSuccessResponse newInstance(Object data) {
        HttpSuccessResponse instance = new HttpSuccessResponse();
        instance.setObj(data);
        instance.setExt(message);
        instance.setCode(HttpResponseCode.SUCCESS);
        return instance;
    }
}
