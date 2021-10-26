package dev.gunlog.common;

import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@Aspect
public class LoggingAspect {
    @Around("within(dev.gunlog.api.*.*Controller)")
    public Object logging(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();

        long startAt = System.currentTimeMillis();
        Object result = pjp.proceed();
        long endAt = System.currentTimeMillis();

        String clientIp = Optional.ofNullable(request.getHeader("X-FORWARDED-FOR")).orElse(request.getRemoteAddr());
        log.info("REQUEST {} > {} {} {} ({}ms)",
                clientIp,
                request.getMethod(),
                request.getRequestURI(),
                getRequestParams(),
                endAt-startAt);
        return result;
    }
    private String getRequestParams() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        Map<String, String[]> paramMap = request.getParameterMap();
        return paramMap.isEmpty() ? "" : " [" + paramMapToString(paramMap) + "]";
    }
    private String paramMapToString(Map<String, String[]> paramMap) {
        return paramMap.entrySet().stream()
                .map(entry -> String.format("%s -> (%s)",
                        entry.getKey(), Joiner.on(",").join(entry.getValue())))
                .collect(Collectors.joining(", "));
    }
}
