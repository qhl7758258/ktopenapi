package org.kunteng.ktopenapi.oauth.authentication;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 基础认证对象
 * Created by hlqian
 */
public class BaseAuthentication extends UsernamePasswordAuthenticationToken {
    private String userId;
    private String account;
    private String password;
    public BaseAuthentication(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public BaseAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public BaseAuthentication(Object principal, Object credentials, String userId, String account, String password) {
        super(principal, credentials);
        this.userId = userId;
        this.account = account;
        this.password = password;
    }

    public BaseAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, String userId, String account, String password) {
        super(principal, credentials, authorities);
        this.userId = userId;
        this.account = account;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
