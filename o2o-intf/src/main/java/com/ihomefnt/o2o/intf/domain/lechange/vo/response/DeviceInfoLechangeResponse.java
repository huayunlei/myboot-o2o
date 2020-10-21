package com.ihomefnt.o2o.intf.domain.lechange.vo.response;

import com.ihomefnt.o2o.intf.domain.lechange.dto.LechangeChannelInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 乐橙设备信息详情
 * @author ZHAO
 */
@Data
public class DeviceInfoLechangeResponse {
	private String deviceId;//设备ID
	
	private Integer status;//当前状态：0-离线，1-在线，3-升级中
	
    private String baseline;//设备基线类型，详见华视微讯设备协议
    
    private String deviceModel;//设备型号
    
    private String deviceCatalog;//设备分类（NVR/DVR/HCVR/IPC/SD/IHG/ARC)
    
    private String brand;//设备品牌信息：lechange-乐橙设备，general-通用设备
    
    private String version;//设备软件版本号
    
    private String name;//设备名称
    
    private String ability;//设备能力项，逗号隔开，详见华视微讯设备协议
    
    private boolean canBeUpgrade;//是否有新版本可以升级
    
    private List<LechangeChannelInfo> channels;//抽取的通道列表

    public DeviceInfoLechangeResponse() {
        this.deviceId = "";
        this.status = 0;
        this.baseline = "";
        this.deviceModel = "";
        this.deviceCatalog = "";
        this.brand = "";
        this.version = "";
        this.name = "";
        this.ability = "";
        this.canBeUpgrade = false;
        this.channels = new ArrayList<LechangeChannelInfo>();
    }

}
