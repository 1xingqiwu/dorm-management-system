package com.ylb.common.filter;

import com.ylb.common.utils.JwtUtils;
import com.ylb.common.utils.RedisUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. 尝试从 Header 获取 Token
        String token = request.getHeader("Authorization");

        // 2. 如果 Header 没有，尝试从 URL 参数获取 (为了 AI 流式对话)
        if (!StringUtils.hasText(token)) {
            token = request.getParameter("token");
        }

        if (StringUtils.hasText(token)) {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            try {
                // 3. 解析 Token 获取用户名
                if (jwtUtils.validateToken(token)) {
                    Claims claims = jwtUtils.parseToken(token);
                    String username = claims.getSubject();

                    //去 Redis 查当前有效的 Token 是哪个
                    String redisToken = (String) redisUtils.get("login_token:" + username);

                    // 如果 Redis 里没有，或者和手里的 Token 不一致 -> 视为无效（被挤下线）
                    if (redisToken == null || !redisToken.equals(token)) {
                        response.setStatus(401);
                        response.setContentType("application/json;charset=UTF-8");
                        response.getWriter().write("{\"code\":401, \"msg\":\"您的账号已在别处登录，或登录已过期，请重新登录\"}");
                        return; // 拦截，不往下走了
                    }

                    // 4. 校验通过，设置 Security 上下文
                    String role = (String) claims.get("role");
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(username, null,
                                    Collections.singletonList(new SimpleGrantedAuthority(role)));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                // Token 解析失败（格式错误等）
                response.setStatus(401);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"code\":401, \"msg\":\"Token认证失败\"}");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}