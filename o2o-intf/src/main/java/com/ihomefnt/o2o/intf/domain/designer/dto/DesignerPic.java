package com.ihomefnt.o2o.intf.domain.designer.dto;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class DesignerPic {

	private Long id;
	private Long ownerId;
	private String url;
	private Timestamp createtime;
	private Integer mark;
	private Integer width;
	private Integer height;
    private Long picId;
}
