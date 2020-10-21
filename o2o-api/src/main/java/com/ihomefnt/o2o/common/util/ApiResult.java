package com.ihomefnt.o2o.common.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpMessage;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;

/**
 * 接口通用返回
 *
 * @author jiangjun
 * @version 2.0, 2018-04-02 下午2:47
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ApiResult {

    private String code;

    private MultiValueMap<String, String> headers;

    private HttpBaseResponse baseResponse;

    public ApiResult(){
        if(null == headers){
            headers = new HttpHeaders();
        }

        //headers.set("Access-Control-Allow-Origin", "*");
    }

    public ResponseEntity<HttpBaseResponse> success(Object object, String msg){

        return sendResult(HttpResponseCode.SUCCESS, object, msg);
    }

    public ResponseEntity<HttpBaseResponse> success(Object object){

        return sendResult(HttpResponseCode.SUCCESS, object, "");
    }

    public ResponseEntity<HttpBaseResponse> success(String msg){

        return sendResult(HttpResponseCode.SUCCESS, null, msg);
    }

    public ResponseEntity<HttpBaseResponse> success(){

        return sendResult(HttpResponseCode.SUCCESS, null, "");
    }

    public ResponseEntity<HttpBaseResponse> fail(Object object, String msg){

        return sendResult(HttpResponseCode.FAILED, object, msg);
    }

    public ResponseEntity<HttpBaseResponse> fail(Integer code, String msg){

        return sendResult(code,null, msg);
    }

    public ResponseEntity<HttpBaseResponse> fail(String msg){

        return sendResult(HttpResponseCode.FAILED, null, msg);
    }

    public ResponseEntity<HttpBaseResponse> fail(Object object){

        return sendResult(HttpResponseCode.FAILED, object, "");
    }

    public ResponseEntity<HttpBaseResponse> fail(){

        return sendResult(HttpResponseCode.FAILED, null, "");
    }

    public ResponseEntity<HttpBaseResponse> fail(Long code , String msg){

        return sendResult(code, null, msg);
    }

    public ResponseEntity<HttpBaseResponse> sendResult(long code, Object object, String msg){
        if(null == baseResponse){
            baseResponse = new HttpBaseResponse();
        }

        baseResponse.setCode(code);

        baseResponse.setObj(object);

        HttpMessage message = new HttpMessage();

        message.setMsg(msg);

        baseResponse.setExt(message);

        return new ResponseEntity(baseResponse, headers, HttpStatus.OK);
    }

    public ApiResult addHeader(String header, String val){
        this.headers.set(header, val);

        return this;
    }

    public ApiResult cleanHeaders(String header, String val){
        this.headers.clear();

        return this;
    }

    public String getCode() {
        return code;
    }

    public ApiResult setCode(String code) {
        this.code = code;
        return this;
    }

    public MultiValueMap<String, String> getHeaders() {
        return headers;
    }

    public ApiResult setHeaders(MultiValueMap<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public HttpBaseResponse getBaseResponse() {
        return baseResponse;
    }

    public ApiResult setBaseResponse(HttpBaseResponse baseResponse) {
        this.baseResponse = baseResponse;
        return this;
    }
}
