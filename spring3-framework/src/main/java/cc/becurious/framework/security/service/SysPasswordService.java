package cc.becurious.framework.security.service;

import cc.becurious.common.core.redis.RedisCache;
import cc.becurious.common.exception.ServiceException;
import cc.becurious.common.utils.SecurityUtils;
import cc.becurious.framework.security.context.AuthenticationContextHolder;
import cc.becurious.system.domain.SysUser;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class SysPasswordService {

    @Resource
    private RedisCache redisCache;

    public void validate(SysUser user){
        Authentication context = AuthenticationContextHolder.getContext();
        String username = context.getName();
        String password = context.getCredentials().toString();

        // todo 登录失败次数
        




        if(!matches(user,password)){
            throw new ServiceException("密码错误！");
        }

    }

    public boolean matches(SysUser user, String rawPassword)
    {
        return SecurityUtils.matchesPassword(rawPassword, user.getPassword());
    }

}
