package org.kunteng.ktopenapi.oauth.exception;


import org.kunteng.ktopenapi.oauth.vo.OAuthResultMap;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * 
 * @author hlqian
 *
 */
@Deprecated
public class OAuthAuthenticationEntryPoint extends AbstractOAuthSecurityExceptionHandler implements
        AuthenticationEntryPoint {
    private String typeName = OAuth2AccessToken.BEARER_TYPE;

    private String realmName = "oauth";

    public void setRealmName(String realmName) {
        this.realmName = realmName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        doHandle(request, response, authException);
    }

    @Override
    protected ResponseEntity<OAuthResultMap> enhanceResponse(ResponseEntity<OAuth2Exception> response, Exception exception) {
        HttpHeaders headers = response.getHeaders();
        String existing = null;
        if (headers.containsKey("WWW-Authenticate")) {
            existing = extractTypePrefix(headers.getFirst("WWW-Authenticate"));
        }
        StringBuilder builder = new StringBuilder();
        builder.append(typeName+" ");
        builder.append("realm=\"" + realmName + "\"");
        if (existing!=null) {
            builder.append(", "+existing);
        }
        HttpHeaders update = new HttpHeaders();
        update.putAll(response.getHeaders());
        update.set("WWW-Authenticate", builder.toString());
        OAuth2Exception entity = response.getBody();
        OAuthResultMap result = OAuthResultMap.msg(-401l, entity.getOAuth2ErrorCode(), entity.getMessage());
        return new ResponseEntity<OAuthResultMap>(result, update, response.getStatusCode());
    }

    private String extractTypePrefix(String header) {
        String existing = header;
        String[] tokens = existing.split(" +");
        if (tokens.length > 1 && !tokens[0].endsWith(",")) {
            existing = StringUtils.arrayToDelimitedString(tokens, " ").substring(existing.indexOf(" ") + 1);
        }
        return existing;
    }
}
