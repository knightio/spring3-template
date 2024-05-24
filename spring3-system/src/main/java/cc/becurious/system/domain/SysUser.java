package cc.becurious.system.domain;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class SysUser {

    Long id;
    String username;
    String password;
    Date loginDate;

}


