package com.ihomefnt.o2o.intf.domain.paintscreen.vo.response;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * Created by Administrator on 2018/12/4 0004.
 */
@Data
@ApiModel("首页展示信息")
public class ArtHomeResponse {

    private Integer modelId;
    private String modelTitle;
    private String modelImage;
    private Integer modelType;
    private Integer resourceType;
    private String typeStr;
    private String stateStr;
    private String externalLink;
    private Integer modelSort;
    private Integer modelState;
    private List<JSONObject> resourceDetailList;

}
