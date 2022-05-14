package com.itwj.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.itwj.reggie.common_class.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    public static final AntPathMatcher PATH_MATCHER=new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        HttpServletResponse response=(HttpServletResponse)servletResponse;

        String requestURI=request.getRequestURI();//获取请求的url
        String []urls=new String[]{//不需要拦截的url
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**"
        };
        System.out.println(requestURI);
        boolean check=check(urls,requestURI);

        if(check)
        {
            filterChain.doFilter(request,response);
            return;
        }
        if(request.getSession().getAttribute("employee")!=null)
        {
            filterChain.doFilter(request,response);
            return;
        }
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }
    public boolean check(String []urls,String requestURI){
        for (String url : urls) {
           boolean match= PATH_MATCHER.match(url,requestURI);
            System.out.println(match);
           if(match)
               return true;
        }
        return  false;
    }

}
