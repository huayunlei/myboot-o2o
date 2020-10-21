package com.ihomefnt.o2o.intf.domain.common.http;

public class HttpFailedResponse extends HttpBaseResponse{

    public static HttpFailedResponse newInstance(String message, Long code) {
        HttpFailedResponse instance =  new HttpFailedResponse();

        HttpMessage httpMessage = new HttpMessage();
        httpMessage.setMsg(message);

        instance.setCode(code);
        instance.setExt(httpMessage);

        return instance;
    }
}
