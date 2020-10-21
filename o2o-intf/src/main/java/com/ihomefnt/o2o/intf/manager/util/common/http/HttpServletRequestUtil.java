package com.ihomefnt.o2o.intf.manager.util.common.http;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

public class HttpServletRequestUtil {

    /**
     * 获取HttpServletRequest参数体
     *
     * @param request
     * @return
     * @throws java.io.IOException
     */
    public static String getAllParam(HttpServletRequest httpServletRequest) {
        try {
            String method = httpServletRequest.getMethod();
            if (method.equals("GET") || method.equals("DELETE")) {
                return httpServletRequest.getQueryString();
            }
            StringBuilder buffer = new StringBuilder();
            String line;
            BufferedReader reader = httpServletRequest.getReader();
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    
}
