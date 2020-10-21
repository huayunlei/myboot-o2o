package com.ihomefnt.o2o.common.filter;

import com.ihomefnt.o2o.common.aop.BodyReaderHttpServletRequestWrapper;
import com.ihomefnt.o2o.common.util.HttpHelper;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 过滤器
 *
 *
 *
 * @author jiangjun
 * @version 2.0, 2018-04-11 下午2:47
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ControllerFilter implements Filter{


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) arg0;

        HttpServletResponse response = (HttpServletResponse) arg1;

        response.setCharacterEncoding("UTF-8");

        response.setContentType("application/bean; charset=utf-8");

        ServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(req);

        String body = HttpHelper.getBodyString(requestWrapper);

        if(StringUtils.isBlank(body)){
            response.sendRedirect("/error/emptyBody");
            return;
        }

        arg2.doFilter(requestWrapper, response);
    }

    @Override
    public void destroy() {

    }
}
