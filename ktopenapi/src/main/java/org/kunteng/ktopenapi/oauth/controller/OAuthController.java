package org.kunteng.ktopenapi.oauth.controller;


import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author hlqian
 *
 */
@Controller
@SessionAttributes("authorizationRequest")
public class OAuthController {
    @RequestMapping("/oauth/login")
    public String oauth_login() throws Exception {
        return "/oauth_login";
    }

    @RequestMapping("/oauth/oob")
    public String oauth_oob() throws Exception {
        return "/oauth_oob";
    }
    /**
     * /oauth/authorize请求的异常，后期应处理为异常页面
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/oauth/error")
    @ResponseBody
    public OAuth2Exception handleError(HttpServletRequest request) throws Exception {
        Object error = request.getAttribute("error");
        if (error instanceof OAuth2Exception) {
            return (OAuth2Exception) error;
        }
        return new OAuth2Exception("unknown error");
    }
}
