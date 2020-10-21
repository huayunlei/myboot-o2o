package com.ihomefnt.o2o.intf.manager.constant.personalneed;

public enum DnaRoomClassifyTypeEnum {
	
	LIVING(1, "厅"),
	ROOM(2, "室"),
	KITCHEN(3, "厨"),
	BATHROOM(4, "卫"),
	BALCONY(5, "阳台"),
	STORAGE(6, "储藏间"),
	CLOAK(7, "衣帽间")
	;
	
	
	private Integer code;

    private String description;

    private DnaRoomClassifyTypeEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
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

    public static String getDescription(Integer code) {
        DnaRoomClassifyTypeEnum[] values = values();
        for (DnaRoomClassifyTypeEnum value : values) {
            if (value.code.equals(code)) {
                return value.getDescription();
            }
        }
        return "";
    }

    public static Integer getCode(String description) {
        DnaRoomClassifyTypeEnum[] values = values();
        for (DnaRoomClassifyTypeEnum value : values) {
            if (value.description.equals(description)) {
                return value.getCode();
            }
        }
        return null;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
