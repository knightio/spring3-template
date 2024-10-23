package cc.becurious.system.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "用户信息")
public class SysUser {

    @Schema(description = "id",example = "1")
    private Long id;

    @Schema(description = "用户名称", example = "张三")
    private String username;

    @Schema(hidden = true)
    private String password;

    @Schema(description = "登录时间", example = "2000-01-01 00:00:00")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginDate;

}


