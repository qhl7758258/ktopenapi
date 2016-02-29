package org.kunteng.ktopenapi.restapi.vo;

/**
 * oauth登录表单
 * 
 */
public class OauthLoginForm {
    private String account;
    private String password;
    

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
