package org.kunteng.ktopenapi.oauth.authentication;


import org.kunteng.ktopenapi.module.user.dao.UserRepository;
import org.kunteng.ktopenapi.restapi.vo.UserVo;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 判断用户身份Service
 * 
 */
public class DefaultUserDetailsService implements UserDetailsService{
    
	 private UserRepository userRepository;
	 

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       
    	//xxxxxx
    	UserVo user = new UserVo();
    	user.setAccount("qianhailong");
    	user.setPassword("qianhailong");
        if (user == null) {
            throw new UsernameNotFoundException(username + " is not found");
        }
        //数据库修改为存储密文
        return new org.springframework.security.core.userdetails.User(user.getAccount()
                , user.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
    }

	public UserRepository getUserRepository() {
		return userRepository;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
}
