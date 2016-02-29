package org.kunteng.ktopenapi.oauth.authentication;


import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
/**
 * 
 * @author hlqian
 *
 */
public class TokenAuthentication extends BaseAuthentication {

    public TokenAuthentication(Object principal, Object credentials, String userId, String account, String password) {
        super(principal, credentials, userId, account, password);
    }

    public TokenAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, String userId, String account, String password) {
        super(principal, credentials, authorities, userId, account, password);
    }
}
