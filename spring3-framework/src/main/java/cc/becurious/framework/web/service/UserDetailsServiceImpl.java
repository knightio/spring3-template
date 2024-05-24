package cc.becurious.framework.web.service;

import cc.becurious.common.core.domain.LoginUser;
import cc.becurious.system.service.IUserService;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//        User user = userService.selectUserByUserName(username);
        return new LoginUser(1L,"test",new BCryptPasswordEncoder().encode("123"));
    }
}
