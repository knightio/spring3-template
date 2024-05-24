package cc.becurious.framework.web.service;

import cc.becurious.common.core.domain.LoginUser;
import cc.becurious.system.domain.SysUser;
import cc.becurious.system.service.IUserService;
import jakarta.annotation.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class SysLoginService {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private TokenService tokenService;

    @Resource
    private IUserService userService;

    public String login(String username, String password){
        Authentication authentication = null;
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
        // 调用实现UserDetailsService接口类的loadUserByUsername方法
        authentication = authenticationManager.authenticate(authenticationToken);

        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        recordLoginInfo(loginUser.getUserId());

        return tokenService.createToken(loginUser);

    }

    private void recordLoginInfo(Long userId) {
        SysUser sysUser = SysUser.builder().id(userId).loginDate(new Date()).build();
        userService.updateUser(sysUser);
    }
}
