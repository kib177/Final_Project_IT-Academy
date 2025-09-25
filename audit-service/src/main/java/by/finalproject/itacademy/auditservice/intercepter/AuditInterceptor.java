package by.finalproject.itacademy.auditservice.intercepter;

import by.finalproject.itacademy.common.config.JwtTokenUtil;
import by.finalproject.itacademy.common.config.JwtUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Component
public class AuditInterceptor implements HandlerInterceptor {

    @Autowired
    private AuditService auditService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private static final ThreadLocal<AuditContext> auditContext = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle");
        if (handler instanceof HandlerMethod) {
            try {
                AuditContext context = new AuditContext();
                context.setStartTime(System.currentTimeMillis());
                context.setMethod(request.getMethod());
                context.setUri(request.getRequestURI());
                context.setQueryString(request.getQueryString());
                context.setUserAgent(request.getHeader("User-Agent"));
                context.setClientIp(getClientIp(request));

                // Извлекаем пользователя из JWT токена
                String token = extractToken(request);
                if (token != null && jwtTokenUtil.validateToken(token)) {
                    try {
                        JwtUser jwtUser = jwtTokenUtil.extractUser(token);
                        context.setUserUuid(jwtUser.userId());
                    } catch (Exception e) {
                        System.out.println("Failed to extract user from token: {}" + e.getMessage());
                    }
                }

                auditContext.set(context);
                System.out.println("Audit context created for: {} {}" + context.getMethod() + context.getUri());

            } catch (Exception e) {
                System.out.print("Error preHandle: {}" + e.getMessage() + e);
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        AuditContext context = auditContext.get();
        if (context != null) {
            try {
                context.setResponseStatus(response.getStatus());
                context.setDuration(System.currentTimeMillis() - context.getStartTime());

                if (ex != null) {
                    context.setError(ex.getMessage());
                }

                // Асинхронно сохраняем аудит чтобы не блокировать основной поток
                auditService.saveAuditAsync(context);


            } catch (Exception e) {
                System.out.print("Error saving audit: {}" + e.getMessage() + e);
            } finally {
                auditContext.remove();
            }
        }
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    private String getClientIp(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}

@Data
class AuditContext {
    private Long startTime;
    private String method;
    private String uri;
    private String queryString;
    private String userAgent;
    private String clientIp;
    private UUID userUuid;
    private Integer responseStatus;
    private Long duration;
    private String error;
}