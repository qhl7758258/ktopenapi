package org.kunteng.ktopenapi.module.user.service;

import java.util.HashMap;
import java.util.Map;

import org.kunteng.ktopenapi.restapi.vo.OauthLoginForm;
import org.kunteng.ktopenapi.restapi.vo.ResultMsg;
import org.springframework.stereotype.Service;
@Service
public class OauthUserLoginService {

	/**
	 * oauh登录
	 * @param loginForms
	 * @return
	 */
	public ResultMsg login(OauthLoginForm loginForms) {
		String account = loginForms.getAccount();
		String password = loginForms.getPassword();
		
		ResultMsg msg = new ResultMsg();
		msg.setMsg("成功!");
		msg.setRet(0l);
		Map body = new HashMap();
		Map user = new HashMap();
		user.put("id", "1");
		user.put("account", "qianhailong");
		user.put("password", "qianhailong");
		body.put("user", user);
		msg.setBody(body);
		return msg;

	}

}
