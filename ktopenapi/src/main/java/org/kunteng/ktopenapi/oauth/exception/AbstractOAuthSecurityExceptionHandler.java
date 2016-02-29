package org.kunteng.ktopenapi.oauth.exception;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultOAuth2ExceptionRenderer;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.OAuth2ExceptionRenderer;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 重定义异常处理抽象类
 * Created by hlqian
 */
public abstract class AbstractOAuthSecurityExceptionHandler {
    protected final Log logger = LogFactory.getLog(getClass());

    private WebResponseExceptionTranslator exceptionTranslator = new DefaultWebResponseExceptionTranslator();

    private OAuth2ExceptionRenderer exceptionRenderer = new DefaultOAuth2ExceptionRenderer();

    private HandlerExceptionResolver handlerExceptionResolver = new DefaultHandlerExceptionResolver();

    public void setExceptionTranslator(WebResponseExceptionTranslator exceptionTranslator) {
        this.exceptionTranslator = exceptionTranslator;
    }

    public void setExceptionRenderer(OAuth2ExceptionRenderer exceptionRenderer) {
        this.exceptionRenderer = exceptionRenderer;
    }

    protected final void doHandle(HttpServletRequest request, HttpServletResponse response, Exception authException)
            throws IOException, ServletException {
        try {
            ResponseEntity<OAuth2Exception> result = exceptionTranslator.translate(authException);
            HttpEntity<?> responseEntity = enhanceResponse(result, authException);
            exceptionRenderer.handleHttpEntityResponse(responseEntity, new ServletWebRequest(request, response));
            response.flushBuffer();
        }
        catch (ServletException e) {
            if (handlerExceptionResolver.resolveException(request, response, this, e) == null) {
                throw e;
            }
        }
        catch (IOException e) {
            throw e;
        }
        catch (RuntimeException e) {
            throw e;
        }
        catch (Exception e) {
            // Wrap other Exceptions. These are not expected to happen
            throw new RuntimeException(e);
        }
    }

    /**
     * 必须重写此方法来处理为需要的对象
     * @param result
     * @param authException
     * @return
     */
    protected abstract ResponseEntity<?> enhanceResponse(ResponseEntity<OAuth2Exception> result,
                                                              Exception authException);
}
