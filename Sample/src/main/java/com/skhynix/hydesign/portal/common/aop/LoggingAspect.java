package com.skhynix.hydesign.portal.common.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 메서드 호출 시 parameter와 return 값을 logging 함
 * 
 * @author <a href="mailto:support@openit.co.kr">(주)오픈잇 | openit Inc.</a>
 * @version 1.00
 * @created 2014. 12. 24.
 */
@Aspect
public class LoggingAspect {

    /**
     * logger
     */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Default Constructor
     */
    public LoggingAspect() {
        // Default Constructor
    }

    /**
     * 메서드 호출 시 parameter와 return 값을 logging 함
     * 
     * @param point 호출되는 메서드에 대한 정보
     * @return Object 호출되는 메서드의 return 값
     * @exception Throwable 로직 부분 호출 시 예외 발생
     */
    public Object around(ProceedingJoinPoint point) throws Throwable {

        StringBuilder sb = new StringBuilder();
        Object[] args = point.getArgs();

        for (Object arg : args) {
            if (arg != null && arg.getClass().isArray()) {
                sb.append(Arrays.deepToString((Object[])arg)).append(" ");
            } else {
                sb.append(arg).append(" ");
            }
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Param {} : {}", new Object[] {point.getSignature().toShortString(), sb});
        }

        // 로직 부분 호출
        Object result = point.proceed();

        if (logger.isDebugEnabled()) {
            logger.debug("Return {} : {}", new Object[] {point.getSignature().toShortString(), result});
        }

        return result;
    }
}
