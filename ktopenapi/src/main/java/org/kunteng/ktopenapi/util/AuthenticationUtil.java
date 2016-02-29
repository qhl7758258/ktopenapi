package org.kunteng.ktopenapi.util;

import org.kunteng.ktopenapi.core.exception.SystemException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;

/**
 * Created by Shawn on 2015/3/12.
 */
public class AuthenticationUtil {
    public static Authentication getOAuth2UserAuthentication() {
        Authentication tempAuthentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(tempAuthentication instanceof OAuth2Authentication)) {
            throw new SystemException(-101l, "非法授权");
        }
        OAuth2Authentication authentication = (OAuth2Authentication) tempAuthentication;
        Authentication userAuthentication = authentication.getUserAuthentication();
        return userAuthentication;
    }
    
    public static OAuth2Request getOAuth2Request(){
    	  Authentication tempAuthentication = SecurityContextHolder.getContext().getAuthentication();
          if (!(tempAuthentication instanceof OAuth2Authentication)) {
              throw new SystemException(-101l, "非法授权");
          }
          OAuth2Authentication authentication = (OAuth2Authentication) tempAuthentication;
          
          return  authentication.getOAuth2Request();
    }
}
