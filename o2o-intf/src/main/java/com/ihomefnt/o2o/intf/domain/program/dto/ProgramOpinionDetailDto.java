package com.ihomefnt.o2o.intf.domain.program.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.ihomefnt.o2o.intf.domain.right.vo.request.ProgramOpinionRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.collections.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * 方案意见详情信息
 *
 * @author liyonggang
 * @create 2019-08-09 11:44
 */
@Data
@ApiModel("方案意见详情信息")
@Accessors(chain = true)
public class ProgramOpinionDetailDto extends ProgramOpinionRequest {

    @ApiModelProperty("空间数据集合")
    private List<ReviseOpinionList> reviseOpinionList = Lists.newArrayList();

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm")
    private Date addTime;

    private List<ReviseOpinionList> reviseOpinionDescription;

    public List<ReviseOpinionList> getReviseOpinionDescription() {
        return CollectionUtils.isNotEmpty(reviseOpinionDescription) ? reviseOpinionDescription : reviseOpinionList;
    }

    public List<ReviseOpinionList> getReviseOpinionList() {
        return CollectionUtils.isNotEmpty(reviseOpinionList) ? reviseOpinionList : reviseOpinionDescription;
    }

    @Data
    public static class ReviseOpinionList {

        @ApiModelProperty("空间id")
        private Integer roomId;

        @ApiModelProperty("空间名称")
        private String roomUsageName;

        @ApiModelProperty("意见说明")
        private String remarks = "";

        @ApiModelProperty("软装标签")
        private List<TagInfo> softTagList = Lists.newArrayList();

        @ApiModelProperty("硬装标签")
        private List<TagInfo> hardTagList = Lists.newArrayList();

        @ApiModelProperty("已选标签")
        private List<TagInfo> selectedTagList = Lists.newArrayList();
    }
}
