package com.ihomefnt.o2o.intf.manager.constant.personalneed;

/**
 * @author huayunlei
 *	空间标识枚举信息
 */
@Deprecated
public enum CommonRoomMarkEnum {
	
	MASTER_ROOM("ROOM", 3, "主卧", "房间1"),
    SECOND_ROOM("ROOM", 4, "次卧", "房间2"),
    THREE_ROOM("ROOM", 5, "第三房", "房间3"),
    FOUR_ROOM("ROOM", 6, "第四房", "房间4"),
    FIVE_ROOM("ROOM", 7, "第五房", "房间5"),
    SIX_ROOM("ROOM", 15, "第六房", "房间6"),
    SEVEN_ROOM("ROOM", 16, "第七房", "房间7"),

    
	LIVING("LIVING", 1, "客厅", "客厅1"),
	RESTAURANT("LIVING", 2, "餐厅", "餐厅"),
	THREE_LIVING("LIVING", 13, "第三厅", "客厅2"),
	FOUR_LIVING("LIVING", 14, "第四厅", "客厅3"),

    KITCHEN("KITCHEN", 10, "厨房", "厨房1"),
    SECOND_KITCHEN("KITCHEN", 17, "第二厨房", "厨房2"),
    
    BATHROOM("BATHROOM", 8, "主卫", "主卫"),
    SECOND_BATHROOM("BATHROOM", 9, "客卫", "客卫1"),
    THREE_BATHROOM("BATHROOM", 18, "第三卫", "客卫2"),
    FOUR_BATHROOM("BATHROOM", 19, "第四卫", "客卫3"),

    BALCONY("BALCONY", 11, "主阳台", "阳台1"),
    SECOND_BALCONY("BALCONY", 12, "第二阳台", "阳台2"),
    THREE_BALCONY("BALCONY", 20, "第三阳台", "阳台3"),
    FOUR_BALCONY("BALCONY", 21, "第四阳台", "阳台4"),

    STORAGE_ROOM("STORAGE", 22, "储藏间", "储藏间1"),
    SECOND_STORAGE_ROOM("STORAGE", 23, "第二储藏间", "储藏间2"),

    CLOAK_ROOM("CLOAK", 24, "衣帽间", "衣帽间1"),
    SECOND_CLOAK_ROOM("CLOAK", 25, "第二衣帽间", "衣帽间2")
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
    	CommonRoomMarkEnum[] markEnum = values();
    	for (CommonRoomMarkEnum mark : markEnum) {
    		if (mark.getCode().equals(code)) {
    			return mark.getRoomMarkType();
    		}
    	}
    	return null;
    }
    
    public static String getOsDesc(Integer code) {
    	CommonRoomMarkEnum[] markEnum = values();
    	for (CommonRoomMarkEnum mark : markEnum) {
    		if (mark.getCode().equals(code)) {
    			return mark.getOsDesc();
    		}
    	}
    	return null;
    }

	private CommonRoomMarkEnum(String roomMarkType, Integer code, String description, String osDesc) {
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
