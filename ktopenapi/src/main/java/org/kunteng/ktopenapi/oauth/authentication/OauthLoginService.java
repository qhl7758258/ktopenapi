package org.kunteng.ktopenapi.oauth.authentication;

import org.kunteng.ktopenapi.module.user.service.OauthUserLoginService;
import org.kunteng.ktopenapi.oauth.exception.LoginAuthenticationException;
import org.kunteng.ktopenapi.restapi.vo.OauthLoginForm;
import org.kunteng.ktopenapi.restapi.vo.ResultMsg;

/**
 * 
 * @author hlqian
 *
 */
public class OauthLoginService {
	private OauthUserLoginService oauthUserLoginService;

	public ResultMsg login(LoginAuthentication loginAuthentication) {
		String account = (String) loginAuthentication.getPrincipal();
		String password = (String) loginAuthentication.getCredentials();
		OauthLoginForm loginForm = new OauthLoginForm();
		loginForm.setAccount(account);
		loginForm.setPassword(password);
		return oauthUserLoginService.login(loginForm);
	}

	public OauthUserLoginService getOauthUserLoginService() {
		return oauthUserLoginService;
	}

	public void setOauthUserLoginService(
			OauthUserLoginService oauthUserLoginService) {
		this.oauthUserLoginService = oauthUserLoginService;
	}

}
