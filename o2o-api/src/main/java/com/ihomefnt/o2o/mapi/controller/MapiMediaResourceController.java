package com.ihomefnt.o2o.mapi.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ihomefnt.o2o.intf.domain.bundle.vo.request.QiniuTokenRequestVo;
import com.ihomefnt.o2o.intf.domain.bundle.vo.response.QiniuTokenResponseVo;
import com.ihomefnt.o2o.intf.service.product.ProductService;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseResponse;
import com.ihomefnt.o2o.intf.domain.common.http.HttpResponseCode;
import com.ihomefnt.o2o.intf.manager.util.common.bean.StringUtil;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.config.Config;
import com.qiniu.api.rs.PutPolicy;
import io.swagger.annotations.Api;

/**
 * Created by shirely_geng on 15-1-20.
 */
@Api(value="M站版本老API",description="M站版本老接口",tags = "【M-API】")
@Controller
@RequestMapping(value = "/mapi/media")
public class MapiMediaResourceController {
			
    @Autowired
    ProductService productService;

    static Map<String, String> bucketToUrlMap = new HashMap<String, String>();
    static final String FILTER = "http://m.ihomefnt.com";

    static {
        bucketToUrlMap.put("aijia-product-test", "res2.ihomefnt.com");
        bucketToUrlMap.put("aijia-product", "res.ihomefnt.com");
    }

    @RequestMapping(value = "/qiniutoken", method = RequestMethod.GET)
    public ResponseEntity<HttpBaseResponse> generateToken(QiniuTokenRequestVo tokenRequest) {

        // http base response init method
        HttpBaseResponse baseResponse = new HttpBaseResponse();
        baseResponse.setExt(null);
        baseResponse.setObj(null);
        baseResponse.setCode(HttpResponseCode.FAILED);
        // generate key
        Config.ACCESS_KEY = "0cWE2Ci38evF_wbXbHSAUt-5vXMZgqN3idgyvvMy";
        Config.SECRET_KEY = "3kBcjCfTbqEVKWZttKLae_RM0zEbYc3-Q-STnXkw";
        String uptoken = "";
        String key = "";

        if (tokenRequest != null && !StringUtil.isNullOrEmpty(tokenRequest.getBucketName())) {
            try {
                String downloadUrl = bucketToUrlMap.get(tokenRequest.getBucketName());

                Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
                PutPolicy putPolicy = new PutPolicy(tokenRequest.getBucketName());
                uptoken = putPolicy.token(mac);

                baseResponse.setCode(HttpResponseCode.SUCCESS);
                QiniuTokenResponseVo response = new QiniuTokenResponseVo();

                // generate random folder
                // folder is m*n
                int m = (int) (Math.random() * 10000 + 1);
                int n = (int) (Math.random() * 10000 + 1);
                int j = (int) (Math.random() * 100000 + 1);
                key = String.valueOf(m) + "/" + String.valueOf(n) + "/" + String.valueOf(j) + ".jpg";

                // generate
                response.setToken(uptoken);
                response.setKey(key);
                response.setDownloadUrl(downloadUrl);

                baseResponse.setObj(response);
            } catch (Exception ex) {
            }
        }
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Access-Control-Allow-Origin", FILTER);
        headers.set("Access-Control-Allow-Credentials", "true");
        return new ResponseEntity<HttpBaseResponse>(baseResponse, headers, HttpStatus.OK);
    }
}
