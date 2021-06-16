package com.example.springsecurity.jwt.security;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Фильтер - это часть цепочки, которую проходит запрос в security.
 * То есть его можно рассматрировать как сбор подписей, когда мы должны сдать документ, мы проходит цепочку отделов
 * где получаем подпись, то есть одобрение, чтобы пойти дальше и дойти до конца, в нашем случае, конец - это контроллер,
 * который будет обрабатывать запрос.
 */
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends GenericFilterBean {
    private static final String REFRESH_URL = "/api/.*/auth/refresh";

    private final JwtTokenProvider provider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            // получаем токен из заголовка Authentication: Bearer token
            String token = provider.resolveToken(((HttpServletRequest) request));
            if (token != null) {
                // проверяем токен на то, что он действительный и не истек еще
                provider.checkTokenOnValid(token);

                // устанавливаем авторизацию в Spring Security.
                SecurityContextHolder.getContext().setAuthentication(provider.getAuthentication(token));
            }
        } catch (ExpiredJwtException e) {
            if (!isRefreshUrl(((HttpServletRequest) request).getRequestURI())) {
                ((HttpServletResponse) response).setStatus(HttpStatus.UNAUTHORIZED.value());
                return;
            }
        } catch (JwtAuthException e) {
            ((HttpServletResponse) response).setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        // если все успешно, то продолжаем цепочку фильтров
        chain.doFilter(request, response);
    }

    private boolean isRefreshUrl(String url) {
        return url.matches(REFRESH_URL);
    }
}
