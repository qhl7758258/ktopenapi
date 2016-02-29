package org.kunteng.ktopenapi.restapi.vo;


/**
 * 接收JSON的对象
 * 
 */
public class ReceiveMsg<T> {
    private Long ret;
    private String msg;
    private T body;

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

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public ReceiveMsg() {
    }

    public ReceiveMsg(Long ret, String msg, T body) {
        this.ret = ret;
        this.msg = msg;
        this.body = body;
    }

    public ReceiveMsg(Long ret, String msg) {
        this.ret = ret;
        this.msg = msg;
    }
    
    public static ReceiveMsg msg(Long ret, String msg) {
        ReceiveMsg rm = new ReceiveMsg();
        rm.setRet(ret);
        rm.setMsg(msg);
        return rm;
    }
    
    public static ReceiveMsg success(String msg) {
        return msg(0l, msg);
    }
    
}
