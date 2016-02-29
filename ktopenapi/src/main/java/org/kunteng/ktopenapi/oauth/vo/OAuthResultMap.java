package org.kunteng.ktopenapi.oauth.vo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author hlqian
 *
 */
@Deprecated
public class OAuthResultMap implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(OAuthResultMap.class);
    private static final long serialVersionUID = 2526963913101582647L;
    private Long ret;
    private String msg;
    private String desc;
    private Map body = new HashMap();

    public static OAuthResultMap msg(Long ret, String msg, String desc) {
        OAuthResultMap rm = new OAuthResultMap();
        rm.setRet(ret);
        rm.setMsg(msg);
        rm.setDesc(desc);
        return rm;
    }

    public static OAuthResultMap msg(Long ret, String msg, String desc, Object... params) {
        OAuthResultMap rm = msg(ret, msg, desc);
        Map body = rm.getBody();
        if (params.length % 2 == 0) {
            for (int i = 0; i < params.length; i += 2) {
                Object param = params[i];
                Object paramValue = params[i + 1];
                body.put(param, paramValue);
            }
        } else {
            log.error("参数必须为偶数个，符合Key/Value");
        }
        return rm;
    }

    public static OAuthResultMap msg(Long ret, String msg, String desc, Map body) {
        OAuthResultMap rm = msg(ret, msg, desc);
        rm.setBody(body);
        return rm;
    }

    public Long getRet() {
        return ret;
    }

    public void setRet(Long ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Map getBody() {
        return body;
    }

    public void setBody(Map body) {
        this.body = body;
    }
}
