package org.kunteng.ktopenapi.oauth.token;

import org.kunteng.ktopenapi.core.redis.JedisSentinelTemplate;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

/**
 * 用于生成code
 * Created by hlqian
 */
public class OAuthAuthorizationCodeServices extends RandomAuthorizationCodeServices {
    private static final String PRE_KEY = "CODE_";
    
    private static JedisSentinelTemplate redisTemplate;

	
    public static JedisSentinelTemplate getRedisTemplate() {
		return redisTemplate;
	}

	public static void setRedisTemplate(JedisSentinelTemplate redisTemplate) {
		OAuthAuthorizationCodeServices.redisTemplate = redisTemplate;
	}

	@Override
    protected void store(String code, OAuth2Authentication authentication) {
    	redisTemplate.set2Beanex(PRE_KEY + code, 10 * 60, authentication);
    }

    @Override
    protected OAuth2Authentication remove(String code) {
    	OAuth2Authentication authentication = (OAuth2Authentication) redisTemplate.get2Bean(PRE_KEY + code);
    	redisTemplate.del(PRE_KEY + code);
        return authentication;
    }

}
