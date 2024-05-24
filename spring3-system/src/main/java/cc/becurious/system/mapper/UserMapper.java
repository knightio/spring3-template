package cc.becurious.system.mapper;

import cc.becurious.system.domain.SysUser;

public interface UserMapper {

    public SysUser selectUserById(Long id);

    SysUser selectUserByUserName(String userName);

    Long updateUser(SysUser sysUser);
}
