package com.ihomefnt.o2o.intf.domain.bundle.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author xiamingyu
 * @date 2018/11/22
 */

@ApiModel(description = "模块实体返回")
@Data
@Accessors(chain = true)
public class Module implements Serializable {

    private static final long serialVersionUID = -3128579291035885565L;

    @ApiModelProperty(value = "模块唯一码")
    private String moduleCode;

    @ApiModelProperty(value = "是否需要更新 0-不需要 1-全量 2-增量 3-回滚")
    private Integer updateFlag;

    @ApiModelProperty(value = "更新提醒方式 0-静默更新 1-弹窗更新")
    private Integer remindMode;

    @ApiModelProperty(value = "最新的bundle版本号")
    private Integer latestVersionCode;

    @ApiModelProperty(value = "全量更新的下载url")
    private String fullUrl;

    @ApiModelProperty(value = "增量更新的下载url")
    private String incrementUrl;

    @ApiModelProperty(value = "bundle文件的md5")
    private String bundleFileMd5;

}
