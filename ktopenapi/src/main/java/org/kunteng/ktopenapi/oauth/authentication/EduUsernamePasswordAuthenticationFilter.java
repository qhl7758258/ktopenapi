package org.kunteng.ktopenapi.oauth.authentication;


import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录认证
 *
 */
public class EduUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    public EduUsernamePasswordAuthenticationFilter() {
    }

    public EduUsernamePasswordAuthenticationFilter(String defaultFilterProcessesUrl) {
        //super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String account = request.getParameter("username");
        String password = request.getParameter("password");
        String province = request.getParameter("province");
        String loginType = request.getParameter("loginType");
        String ip = request.getRemoteAddr();
        LoginAuthentication loginAuthentication = new LoginAuthentication(account, password, null, account, password, province, loginType);
        loginAuthentication.setIp(ip);
        //this.getAuthenticationManager().authenticate();
        return this.getAuthenticationManager().authenticate(loginAuthentication);
    }

    @Override
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String account = request.getParameter("username");
        if (StringUtils.isBlank(account)) {
            return false;
        }
        boolean flag = super.requiresAuthentication(request, response);
        return flag;
    }
}
