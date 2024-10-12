package cc.becurious.framework.security.handle;

import cc.becurious.common.core.domain.AjaxResult;
import cc.becurious.common.utils.JsonUtils;
import cc.becurious.common.utils.ServletUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.cbor.Jackson2CborDecoder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.text.MessageFormat;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {

    @Serial
    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        String msg = MessageFormat.format("请求访问：{0}，认证失败，无法访问系统资源", request.getRequestURI());

        ServletUtils.renderString(response, JsonUtils.toJSONString(AjaxResult.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg)));
    }
}
