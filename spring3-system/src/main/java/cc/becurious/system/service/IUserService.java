package cc.becurious.system.service;

import cc.becurious.system.domain.SysUser;

public interface IUserService {

    public SysUser selectUserById(Long id);


    public SysUser selectUserByUserName(String userName);


    public Long updateUser(SysUser sysUser);
}
