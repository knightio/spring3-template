package cc.becurious.web.controller;

import cc.becurious.system.domain.SysUser;
import cc.becurious.system.service.IUserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys")
public class SysUserController {

    @Resource
    private IUserService userService;


    @GetMapping("/{userId}")
    public SysUser getUser(@PathVariable("userId") Long userId) {

        return userService.selectUserById(userId);
    }

}
