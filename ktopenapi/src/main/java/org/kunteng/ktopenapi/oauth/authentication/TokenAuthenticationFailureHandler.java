package org.kunteng.ktopenapi.oauth.authentication;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 
 * @author hlqian
 *
 */
public class TokenAuthenticationFailureHandler extends OAuth2AuthenticationEntryPoint implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        //super.onAuthenticationFailure(request, response, exception);
        /*response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter out = response.getWriter();
        long ret = -HttpServletResponse.SC_UNAUTHORIZED;
        out.print(JsonUtils.Bean2Json(ResultMsg.msg(ret, exception.getMessage())));*/
        doHandle(request, response, exception);
    }
}
