package org.kunteng.ktopenapi.oauth.authentication;

import org.kunteng.ktopenapi.oauth.exception.LoginAuthenticationException;
import org.kunteng.ktopenapi.restapi.vo.ResultMsg;
import org.kunteng.ktopenapi.restapi.vo.UserVo;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * 
 * @author hlqian
 *
 */
public class LoginAuthenticationProvider implements AuthenticationProvider {
	private OauthLoginService oauthLoginService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication instanceof LoginAuthentication) {
            LoginAuthentication loginAuthentication = (LoginAuthentication) authentication;
           /* ResultMsg resultMsg = oauthLoginService.login(loginAuthentication);*/
            ResultMsg resultMsg = oauthLoginService.login(loginAuthentication);
            if (resultMsg.getRet() != 0l) {
                throw new LoginAuthenticationException(resultMsg.getMsg());
            }
            UserVo userVo = (UserVo) resultMsg.getBody().get("user");
            return new LoginAuthentication(userVo.getAccount(), loginAuthentication.getCredentials()
                    , AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER")
                    , userVo.getId(), userVo.getAccount(), userVo.getPassword()
                    , loginAuthentication.getProvince()
                    , loginAuthentication.getLoginType());
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(LoginAuthentication.class);
    }

	public OauthLoginService getOauthLoginService() {
		return oauthLoginService;
	}

	public void setOauthLoginService(OauthLoginService oauthLoginService) {
		this.oauthLoginService = oauthLoginService;
	}

}
