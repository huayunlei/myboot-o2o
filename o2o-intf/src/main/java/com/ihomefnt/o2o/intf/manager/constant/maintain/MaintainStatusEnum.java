package com.ihomefnt.o2o.intf.manager.constant.maintain;

public enum MaintainStatusEnum {
	MAINTAIN_STATUS_ONALLOCATE(0,0,1,"待受理","客服受理","您的极速报修已申请成功，等待客服受理"),
	MAINTAIN_STATUS_ONRESERVE(10,0,2,"已受理","维修预约","艾佳已受理您的极速报修，将尽快与您沟通确认维修时间"),
	MAINTAIN_STATUS_RESERVED(11,0,2,"已受理","维修预约","艾佳已受理您的极速报修，将尽快与您沟通确认维修时间"),
	MAINTAIN_STATUS_ONVISIT(20,0,3,"处理中","上门维修","艾佳工作人员将在预约的时间上门维修"),
	MAINTAIN_STATUS_VISITED(21,0,3,"处理中","上门维修","艾佳工作人员将在预约的时间上门维修"),
	MAINTAIN_STATUS_PROCESSED(3,0,3,"处理中","上门维修","艾佳工作人员将在预约的时间上门维修"),
	MAINTAIN_STATUS_UNEVALUATED(4,0,4,"已完成","","极速报修服务已完成"),
	MAINTAIN_STATUS_FINISHED(4,1,5,"已完成","","极速报修服务已完成，感谢您的评价"),
	MAINTAIN_STATUS_CANCEL(5,0,6,"已取消","","极速报修服务已取消");
	
	private Integer code;//客服中心返回状态code  0待分配、10待预约、11已预约、20待上门、21已上门、3已处理、4已完成、5已取消,
	
	private Integer commentStatus;//0未点评，1已点评
	
	private Integer status;//当前状态
	
	private String statusDesc;//当前状态说明
	
	private String nextStatusDesc;//下一状态说明
	
	private String praiseDesc;//文案说明

	MaintainStatusEnum(Integer code, Integer commentStatus, Integer status, String statusDesc,
			String nextStatusDesc, String praiseDesc) {
		this.code = code;
		this.commentStatus = commentStatus;
		this.status = status;
		this.statusDesc = statusDesc;
		this.nextStatusDesc = nextStatusDesc;
		this.praiseDesc = praiseDesc;
	}
	
	public static MaintainStatusEnum getMiantainStatusEnum(Integer code, Integer commentStatus) {
		for (MaintainStatusEnum maintainStatusEnum : MaintainStatusEnum.values()) {
			if(maintainStatusEnum.getCode().equals(code) && maintainStatusEnum.getCommentStatus().equals(commentStatus)){
				return maintainStatusEnum;
			}
		}
		return null;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Integer getCommentStatus() {
		return commentStatus;
	}

	public void setCommentStatus(Integer commentStatus) {
		this.commentStatus = commentStatus;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public String getNextStatusDesc() {
		return nextStatusDesc;
	}

	public void setNextStatusDesc(String nextStatusDesc) {
		this.nextStatusDesc = nextStatusDesc;
	}

	public String getPraiseDesc() {
		return praiseDesc;
	}

	public void setPraiseDesc(String praiseDesc) {
		this.praiseDesc = praiseDesc;
	}
	
}
