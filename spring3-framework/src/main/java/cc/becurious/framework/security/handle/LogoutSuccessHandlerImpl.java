package cc.becurious.framework.security.handle;

import cc.becurious.common.core.domain.AjaxResult;
import cc.becurious.common.core.domain.LoginUser;
import cc.becurious.common.utils.JsonUtils;
import cc.becurious.common.utils.ServletUtils;
import cc.becurious.framework.security.service.TokenService;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Resource
    private TokenService tokenService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if(!Objects.isNull(loginUser)){
            //删除redis用户信息
            tokenService.delLoginUser(loginUser.getToken());
        }
        ServletUtils.renderString(response, JsonUtils.toJSONString(AjaxResult.success("退出成功")));
    }
}
