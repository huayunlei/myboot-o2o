package com.ihomefnt.o2o.intf.manager.constant.personalneed;

/**
 * @author huayunlei
 *	空间标识枚举信息
 */
public enum CommonRoomUsageEnum {

	MASTER_ROOM("ROOM", 2, "主卧", "房间1"),
    SECOND_ROOM("ROOM", 3, "次卧", "房间2"),
	CHILDREN_ROOM("ROOM",4, "儿童房", "房间3"),
	STUDY_ROOM("ROOM",5, "书房", "房间4"),
	ELDERLY_ROOM("ROOM",9, "老人房", "房间5"),
	TATAMI_ROOM("ROOM",12, "榻榻米房", "房间6"),
	SITTING_ROOM("ROOM",15, "起居室", "房间7"),
	ROOM_MULTIPLE_FUNC("ROOM",24, "多功能房", "房间8"),

	LIVING("LIVING", 1, "客厅", "客厅1"),
	RESTAURANT("LIVING", 6, "餐厅", "餐厅"),

    KITCHEN("KITCHEN", 8, "厨房", "厨房1"),

    BATHROOM("BATHROOM", 18, "主卫", "主卫"),
    SECOND_BATHROOM("BATHROOM", 19, "客卫", "客卫1"),

    BALCONY("BALCONY", 10, "生活阳台", "阳台1"),
    SECOND_BALCONY("BALCONY", 20, "休闲阳台", "阳台2"),

    STORAGE_ROOM("STORAGE", 22, "储藏间", "储藏间1"),

    CLOAK_ROOM("CLOAK", 11, "衣帽间", "衣帽间1"),
    ;




	/**
	 * 类型
	 */
	private String roomMarkType;

	/**
	 * 标识号码 id
	 */
	private Integer code;

    /**
     * 标识描述
     */
    private String description;

    /**
     * 对应前端描述
     */
    private String osDesc;

    public static String getRoomMarkType(Integer code) {
    	CommonRoomUsageEnum[] markEnum = values();
    	for (CommonRoomUsageEnum mark : markEnum) {
    		if (mark.getCode().equals(code)) {
    			return mark.getRoomMarkType();
    		}
    	}
    	return null;
    }

    public static String getOsDesc(Integer code) {
    	CommonRoomUsageEnum[] markEnum = values();
    	for (CommonRoomUsageEnum mark : markEnum) {
    		if (mark.getCode().equals(code)) {
    			return mark.getOsDesc();
    		}
    	}
    	return null;
    }

	private CommonRoomUsageEnum(String roomMarkType, Integer code, String description, String osDesc) {
		this.roomMarkType = roomMarkType;
		this.code = code;
		this.description = description;
		this.osDesc = osDesc;
		
	}

	public String getRoomMarkType() {
		return roomMarkType;
	}
	
	public void setRoomMarkType(String roomMarkType) {
		this.roomMarkType = roomMarkType;
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

	public String getOsDesc() {
		return osDesc;
	}

	public void setOsDesc(String osDesc) {
		this.osDesc = osDesc;
	}
    
}
