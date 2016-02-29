package org.kunteng.ktopenapi.oauth.authentication;


import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * 自定义身份认证
 * @author hlqian
 *
 */
public class TokenAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication instanceof TokenAuthentication) {
            TokenAuthentication tokenAuthentication = (TokenAuthentication) authentication;
            return new TokenAuthentication(tokenAuthentication.getPrincipal(), tokenAuthentication.getCredentials()
                    , AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"), tokenAuthentication.getUserId()
                    ,tokenAuthentication.getAccount(), tokenAuthentication.getPassword());
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(TokenAuthentication.class);
    }
}
