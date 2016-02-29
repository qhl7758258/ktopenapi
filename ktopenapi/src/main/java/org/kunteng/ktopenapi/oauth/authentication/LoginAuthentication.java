package org.kunteng.ktopenapi.oauth.authentication;


import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 登录认证授权对象
 * 
 */
public class LoginAuthentication extends BaseAuthentication {
    private String province;
    private String loginType;
    private String ip;

    public LoginAuthentication(Object principal, Object credentials, String userId, String account, String password, String province, String loginType) {
        super(principal, credentials, userId, account, password);
        this.province = province;
        this.loginType = loginType;
    }

    public LoginAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, String userId, String account, String password, String province, String loginType) {
        super(principal, credentials, authorities, userId, account, password);
        this.province = province;
        this.loginType = loginType;
    }

    public String getProvince() {
        return province;
    }

    public String getLoginType() {
        return loginType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
