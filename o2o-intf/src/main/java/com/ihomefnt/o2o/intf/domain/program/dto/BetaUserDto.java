package com.ihomefnt.o2o.intf.domain.program.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * beta用户信息
 *
 * @author liyonggang
 * @create 2019-12-17 11:19 上午
 */
@Data
public class BetaUserDto {
    private Integer id;
    private String userName;
    private String password;
    private String userNameZh;
    private String email;
    private String mobile;
    private String position;
    private String remark;
    private String addTime;
    private String updateTime;
    private Integer domainId;
    private Integer userId;
    private Integer delFlag;
    private Boolean active;
    private String roleCode;
    private String roleName;
    private List<String> roleCodeList;
}
