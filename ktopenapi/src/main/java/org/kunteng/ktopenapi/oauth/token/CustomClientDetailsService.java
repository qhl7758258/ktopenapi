package org.kunteng.ktopenapi.oauth.token;

import org.kunteng.ktopenapi.core.redis.JedisSentinelTemplate;
import org.kunteng.ktopenapi.util.Constants;
import org.kunteng.ktopenapi.util.JsonUtils;
import org.kunteng.ktopenapi.util.SerializeUtil;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import javax.sql.DataSource;

/**
 * 
 * @author hlqian
 *
 */
public class CustomClientDetailsService extends JdbcClientDetailsService {
	private static JedisSentinelTemplate redisTemplate;


	public static JedisSentinelTemplate getRedisTemplate() {
		return redisTemplate;
	}

	public static void setRedisTemplate(JedisSentinelTemplate redisTemplate) {
		CustomClientDetailsService.redisTemplate = redisTemplate;
	}

	public CustomClientDetailsService(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws InvalidClientException {
    	
    	 ClientDetails details = (ClientDetails) redisTemplate.get2Bean(Constants.CACHE_OAUTH_CLIENT_PRE + clientId);
         if (details == null) {
             details = super.loadClientByClientId(clientId);
             redisTemplate.set2Beanex(Constants.CACHE_OAUTH_CLIENT_PRE + clientId, 30 * 60, details);
         }
         return details;
    }

}
