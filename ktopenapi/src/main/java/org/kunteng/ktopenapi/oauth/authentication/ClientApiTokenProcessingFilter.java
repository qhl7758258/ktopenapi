package org.kunteng.ktopenapi.oauth.authentication;

import org.apache.commons.lang3.StringUtils;
import org.kunteng.ktopenapi.core.redis.JedisSentinelTemplate;
import org.kunteng.ktopenapi.module.user.service.OauthUserLoginService;
import org.kunteng.ktopenapi.oauth.token.OAuthAuthorizationCodeServices;
import org.kunteng.ktopenapi.restapi.vo.OauthLoginForm;
import org.kunteng.ktopenapi.restapi.vo.ResultMsg;
import org.kunteng.ktopenapi.restapi.vo.UserVo;
import org.kunteng.ktopenapi.util.JsonUtils;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.google.gson.Gson;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * 自定义token处理Filter
 * Created by hlqian
 */
public class ClientApiTokenProcessingFilter extends AbstractAuthenticationProcessingFilter {
    private static final String KEY = "token";
    
    private OauthUserLoginService oauthUserLoginService;
    
    private static JedisSentinelTemplate redisTemplate;


    public static JedisSentinelTemplate getRedisTemplate() {
		return redisTemplate;
	}

	public static void setRedisTemplate(JedisSentinelTemplate redisTemplate) {
		ClientApiTokenProcessingFilter.redisTemplate = redisTemplate;
	}

	protected ClientApiTokenProcessingFilter() {
        super("/oauth/authorize");
    }

    protected ClientApiTokenProcessingFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
    /*	String token = httpServletRequest.getParameter(KEY);
        if (StringUtils.isBlank(token)) {
            //return new NullAuthentication();
            throw new AuthenticationCredentialsNotFoundException("未授权");
        }
        String userStr = (String) redisTemplate.get(token);
        if (null == userStr) {
            //return new NullAuthentication();
            throw new AuthenticationCredentialsNotFoundException("授权token不正确");
        }
        
        UserVo userVo = JsonUtils.Json2Bean(userStr, UserVo.class);
        
        TokenAuthentication authRequest = new TokenAuthentication(userVo.getAccount(),
                userVo.getPassword(), userVo.getId(), userVo.getAccount(), userVo.getPassword());
        return this.getAuthenticationManager().authenticate(authRequest);*/
        
    	OauthLoginForm loginForms = new OauthLoginForm();
    	loginForms.setAccount("account");
    	loginForms.setPassword("11111");
    	ResultMsg msg = oauthUserLoginService.login(loginForms);
    	
    	String  userStr =String.valueOf(msg.getBody().get("user"));
    	Gson gson = new Gson();
    	UserVo userVo = gson.fromJson(userStr, UserVo.class);
        TokenAuthentication authRequest = new TokenAuthentication(userVo.getAccount(),
                userVo.getPassword(), userVo.getId(), userVo.getAccount(), userVo.getPassword());
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        //super.successfulAuthentication(request, response, chain, authResult);
        SecurityContextHolder.clearContext();
        if (authResult instanceof TokenAuthentication) {
            SecurityContextHolder.getContext().setAuthentication(authResult);
        } else if (authResult instanceof NullAuthentication) {

        }
        chain.doFilter(request, response);
    }

    @Override
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getParameter(KEY);
        if (StringUtils.isBlank(token)) {
            return false;
        }
        boolean flag = super.requiresAuthentication(request, response);
        return flag;
    }

	public OauthUserLoginService getOauthUserLoginService() {
		return oauthUserLoginService;
	}

	public void setOauthUserLoginService(OauthUserLoginService oauthUserLoginService) {
		this.oauthUserLoginService = oauthUserLoginService;
	}
}
