package cc.becurious.web.controller;

import cc.becurious.system.domain.SysUser;
import cc.becurious.system.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
    @Operation(
            summary = "获取用户信息",
            description = "根据用户ID获取用户详细信息",
            security = {
                    @SecurityRequirement(name = "Authorization")
            }
    )
    public SysUser getUser(@PathVariable("userId") Long userId) {
        return userService.selectUserById(userId);
    }

}
