package com.ihomefnt.o2o.intf.domain.user.doo;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
@Data
public class UserRelationInfoDo {
	    private Long id;
	    private String invitemobile;
	    private String invitedmobile;
	    private Integer status;//0.废弃 1.绑定 2. 推荐 3.被取代 4.其他暂定
	    private Long uId;
	    private String name;
	    private String password;
	    private String mobile;
	    private String uImg;
	    private Timestamp regTime;
	    private Timestamp createTime;
	    private Integer myInvitedUsers;
	    private Integer successInvitedUsers;
	    private List<UserRelationInfoDo> invitedUsers;
}
