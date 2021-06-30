package dev.gunlog.aspect;

import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
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
    @Around("within(dev.gunlog..*))")
    public Object logging(ProceedingJoinPoint pjp) throws Throwable {
        String params = "( X )";
        String uri = "( X )";
        String ip = "( X )";
        RequestAttributes requestAttributes = RequestContextHolder
                .getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes()).getRequest();
            Map<String, String[]> paramMap = request.getParameterMap();
            if (!paramMap.isEmpty()) {
                params = " [" + paramMapToString(paramMap) + "]";
            }
            uri = request.getRequestURI();

            ip = Optional.ofNullable(request.getHeader("X-FORWARDED-FOR"))
                    .orElse(request.getRemoteAddr());
        }

        long startAt = System.currentTimeMillis();
        Object result = pjp.proceed();
        long endAt = System.currentTimeMillis();

        LoggingModel.builder()
                .uri(uri)
                .ip(ip)
                .requestClassName(pjp.getSignature().getDeclaringTypeName())
                .requestMethodName(pjp.getSignature().getName())
                .requestParams(params)
                .responseClassName(pjp.getSignature().getDeclaringTypeName())
                .responseMethodName(pjp.getSignature().getName())
                .result(result)
                .processMilliSecond(endAt - startAt)
                .build().printLog();
        return result;
    }
    private String paramMapToString(Map<String, String[]> paramMap) {
        return paramMap.entrySet().stream()
                .map(entry -> String.format("%s -> (%s)",
                        entry.getKey(), Joiner.on(",").join(entry.getValue())))
                .collect(Collectors.joining(", "));
    }
}
