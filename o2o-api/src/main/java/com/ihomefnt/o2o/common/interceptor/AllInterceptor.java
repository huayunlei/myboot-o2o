package com.ihomefnt.o2o.common.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class AllInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, HEAD, PUT, DELETE");
		response.setHeader("Access-Control-Allow-Headers",
				"Accept, Origin, X-Requested-With, Content-Type, Last-Modified");
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
        String ua1 = request.getHeader("aijia");//ios
        String ua = request.getHeader("user-agent");//android
        HttpSession session = request.getSession(false);
		if(StringUtils.isNotBlank(ua1)&&ua1.contains("ihome")){
            session.setAttribute("UA", ua1.toLowerCase());
            response.setHeader("aijia", ua1.toLowerCase() + "-ios");
		}else if(StringUtils.isNotBlank(ua) && null != session){
			session.setAttribute("UA", ua.toLowerCase());
		}
		String fromApp=request.getParameter("fromApp");
		if(StringUtils.isNotBlank(fromApp) && null != session){
            session.setAttribute("UA", session.getAttribute("UA") != null ? session.getAttribute("UA").toString() : "" + "ihome");
		}
	}

}
