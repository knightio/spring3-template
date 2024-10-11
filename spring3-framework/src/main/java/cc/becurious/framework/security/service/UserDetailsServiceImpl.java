package cc.becurious.framework.security.service;

import cc.becurious.common.core.domain.LoginUser;
import cc.becurious.common.exception.ServiceException;
import cc.becurious.system.domain.SysUser;
import cc.becurious.system.service.IUserService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Resource
    IUserService userService;

    @Resource
    SysPasswordService sysPasswordService;

    @Override
    public UserDetails loadUserByUsername(String username) {

        SysUser sysUser = userService.selectUserByUserName(username);
        if (Objects.isNull(sysUser))
        {
            log.info("登录用户：{} 不存在.", username);
            throw new ServiceException("登录用户不存在！");
        }

        sysPasswordService.validate(sysUser);

        return createLoginUser(sysUser);

//        User user = userService.selectUserByUserName(username);
//        return new LoginUser(1L,"consen",new BCryptPasswordEncoder().encode("123456"));
    }

    public UserDetails createLoginUser(SysUser user)
    {
        return new LoginUser(user.getId(), user.getUsername(),user.getPassword());
    }
}
