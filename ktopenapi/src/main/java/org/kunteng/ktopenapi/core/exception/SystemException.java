package org.kunteng.ktopenapi.core.exception;

/**
 * 系统自定义的异常
 * @author hlqian
 *
 */
public class SystemException extends RuntimeException {
    private Long code;
    public SystemException(Long code, String message) {
        super(message);
        this.code = code;
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }
}
