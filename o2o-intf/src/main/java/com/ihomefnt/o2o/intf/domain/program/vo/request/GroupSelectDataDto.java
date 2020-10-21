package com.ihomefnt.o2o.intf.domain.program.vo.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @author liyonggang
 * @create 2019-08-01 18:50
 */
@Data
public class GroupSelectDataDto {

    private String cabinetType;

    private List<GroupIdRelationDto> groupIdRelationList;

    private List<SelectPropertyDto> SelectPropertyList;


    @Data
    @ApiModel("组合替换关系")
    public static class GroupIdRelationDto {

        private Integer groupId;

        private Integer defaultGroupId;
    }

    @Data
    public static class SelectPropertyDto {

        private Integer categoryId;

        private Integer componentId;

        private Integer propertyId;

        private Integer propertyValueCode;
    }

}
