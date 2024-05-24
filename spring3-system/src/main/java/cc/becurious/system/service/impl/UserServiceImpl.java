package cc.becurious.system.service.impl;

import cc.becurious.system.domain.SysUser;
import cc.becurious.system.mapper.UserMapper;
import cc.becurious.system.service.IUserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private UserMapper userMapper;

    public SysUser selectUserById(Long id) {
        return userMapper.selectUserById(id);
    }

    @Override
    public SysUser selectUserByUserName(String userName) {
        return userMapper.selectUserByUserName(userName);
    }

    @Override
    public Long updateUser(SysUser sysUser) {
        return userMapper.updateUser(sysUser);
    }
}
