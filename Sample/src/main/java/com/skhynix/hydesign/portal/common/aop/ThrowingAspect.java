package com.skhynix.hydesign.portal.common.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 메서드 호출시 예외가 발생한 경우 logging 함
 * 
 * @author <a href="mailto:support@openit.co.kr">(주)오픈잇 | openit Inc.</a>
 * @version 1.00
 * @created 2014. 12. 24.
 */
@Aspect
public class ThrowingAspect {

    /**
     * logger
     */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Default Constructor
     */
    public ThrowingAspect() {
        // Default Constructor
    }

    /**
     * 메서드 호출시 예외가 발생한 경우 logging 함
     * 
     * @param joinPoint 호출되는 메서드에 대한 정보
     * @param ex 발생한 exception
     * @throws RuntimeException 으로 처리
     */
    public void afterThrowing(JoinPoint joinPoint, Throwable ex) throws RuntimeException {

        StringBuilder sb = new StringBuilder();
    	Object[] args = joinPoint.getArgs();
    	
    	for (Object arg : args) {
            if (arg != null && arg.getClass().isArray()) {
                sb.append(Arrays.deepToString((Object[])arg)).append(" ");
            } else {
                sb.append(arg).append(" ");
            }
        }
    	
        if (logger.isErrorEnabled()) {
            logger.error("Param {} : {}", new Object[] {joinPoint.getSignature().toShortString(), sb});        	
            logger.error(ex.getMessage(), ex);
        }

        throw new RuntimeException(ex);
    }
}
