package com.ihomefnt.o2o.intf.manager.constant.home;

public enum CommonRoomUseEnum {
	
	MASTER_ROOM(2, 2, "主卧"),
	SECOND_ROOM(2, 3, "次卧"),
	CHILDREN_ROOM(2, 4, "儿童房"),
	STUDY_ROOM(2, 5, "书房"),
	OLD_PERSON_ROOM(2, 9, "老人房"),
	
	TATAMI_ROOM(2, 12, "榻榻米房")
	;
	
	/**
	 * 类型:	1厅	2室	3厨	4卫	5阳台 6储藏间	7衣帽间
	 */
	private Integer classifyType;

	/**
	 * 用途号码 id
	 */
	private Integer code;

    /**
     * 用途描述
     */
    private String description;
    
    public static String getDescriptionByCode(Integer code) {
    	CommonRoomUseEnum[] useEnum = values();
    	for (CommonRoomUseEnum use : useEnum) {
    		if (use.getCode().equals(code)) {
    			return use.getDescription();
    		}
    	}
    	return null;
    }

	private CommonRoomUseEnum(Integer classifyType, Integer code, String description) {
		this.classifyType = classifyType;
		this.code = code;
		this.description = description;
	}

	public Integer getClassifyType() {
		return classifyType;
	}

	public void setClassifyType(Integer classifyType) {
		this.classifyType = classifyType;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
    

}
