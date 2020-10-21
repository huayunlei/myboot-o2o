package com.ihomefnt.o2o.intf.manager.constant.home;

public enum NodeEnum {
	ABOUT_US(0, "了解我们"),
	PAY_DEPOSIT(1, "交定金"),
	SELECT_STYLE(2, "选风格"),
	SELECT_DESIGN(3, "预选设计"),
	PAY_ALL(4, "交装修款"),
	ADJUST_DESIGN(5, "调整设计"),
	CONFIRM_SOLUTION(6, "确认方案"),
	CONSTRUCTION(7, "施工"),
	CHECK(8, "验收"),
	MAINTENANCE(9, "维保");

	private Integer code;
	
	private String name;

	public static String getName(Integer code){
		for (NodeEnum node : NodeEnum.values()) {
			if(code.equals(node.getCode())){
				return node.getName();
			}
		}
		return "";
	}
	
	private NodeEnum(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
