package org.kunteng.ktopenapi.oauth.token;


import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;

/**
 * codeService抽象类（可重新指定生成长度）
 * Created by Shawn on 2014/8/8.
 */
public abstract class RandomAuthorizationCodeServices implements AuthorizationCodeServices {
    private RandomValueStringGenerator generator = new RandomValueStringGenerator(16);

    protected abstract void store(String code, OAuth2Authentication authentication);

    protected abstract OAuth2Authentication remove(String code);

    public String createAuthorizationCode(OAuth2Authentication authentication) {
        String code = generator.generate();
        store(code, authentication);
        return code;
    }

    public OAuth2Authentication consumeAuthorizationCode(String code)
            throws InvalidGrantException {
        OAuth2Authentication auth = this.remove(code);
        if (auth == null) {
            throw new InvalidGrantException("Invalid authorization code: " + code);
        }
        return auth;
    }

    public void setGenerator(RandomValueStringGenerator generator) {
        this.generator = generator;
    }
}
