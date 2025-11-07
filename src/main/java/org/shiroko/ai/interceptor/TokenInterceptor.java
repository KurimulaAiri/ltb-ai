package org.shiroko.ai.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.shiroko.ai.entity.vo.BaseRespVO;
import org.shiroko.ai.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class TokenInterceptor implements HandlerInterceptor {

    private final JwtUtils jwtUtils;

    @Value("${jwt.header}")
    private String tokenHeader;  // 请求头字段名（如Authorization）

    @Value("${jwt.prefix}")
    private String tokenPrefix;  // Token前缀（如Bearer）

    private final ObjectMapper objectMapper;

    @Autowired
    public TokenInterceptor(JwtUtils jwtUtils, ObjectMapper objectMapper) {
        this.jwtUtils = jwtUtils;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;  // 预检请求直接放行
        }

        // 1. 从请求头中获取Token
        String token = request.getHeader(tokenHeader);

        // 2. 验证Token格式（是否包含前缀）
        if (token == null || !token.startsWith(tokenPrefix)) {
            writeVO(response, 401, BaseRespVO.failed(401, "Token 格式错误")); // 401：未授权
            return false;
        }

        // 3. 去除前缀，获取纯Token
        token = token.replace(tokenPrefix, "");

        // 4. 验证Token有效性
        if (!jwtUtils.validateToken(token)) {
            writeVO(response, 401, BaseRespVO.failed(401, "Token 无效或已过期"));
            return false;
        }

        // 5. Token有效，可将用户信息存入ThreadLocal（供后续业务使用）
        String account = jwtUtils.getAccountFromToken(token);
        request.setAttribute("account", account);  // 存入request，方便Controller获取
        return true;  // 放行
    }

    private void writeVO(HttpServletResponse response, int status, BaseRespVO<?> vo) throws Exception {
        response.setStatus(status);
        // 关键：先设置响应编码（必须在getWriter()之前）
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8"); // 显式指定字符编码
        try (PrintWriter writer = response.getWriter()) {
            // 序列化CommonResult为JSON字符串
            String json = objectMapper.writeValueAsString(vo);
            writer.write(json);
            writer.flush();
        }
    }
}