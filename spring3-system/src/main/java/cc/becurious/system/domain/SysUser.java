package cc.becurious.system.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysUser {

    Long id;
    String username;
    String password;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date loginDate;

}


