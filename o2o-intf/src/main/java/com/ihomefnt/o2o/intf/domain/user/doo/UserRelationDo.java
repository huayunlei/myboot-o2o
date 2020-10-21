package com.ihomefnt.o2o.intf.domain.user.doo;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class UserRelationDo {
	 /**主鍵 */
    private Long id;
    private String invitemobile;
    private String invitedmobile;
    private Integer status;
    private Timestamp  createTime;
}
