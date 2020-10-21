package com.ihomefnt.o2o.intf.domain.right.dto;

import com.ihomefnt.o2o.intf.domain.right.dto.RigthtsItemBaseInfo;
import lombok.Data;

import java.util.List;

@Data
public class GradeRightsResultDto {

    private Integer gradeId;//等级,
    private String gradeName;//等级名称
    private Integer classifyId;//权益分类
    private Integer classifyNo;//权益编号
    private Integer version; //版本号,
    private String classifyName;//分类名称,
    private Integer rightsConfigLimit; //配置额度,
    private Integer rightsConfirmedLimit; //可选额度,
    private Integer sort; //排序,
    private List<RigthtsItemBaseInfo> itemList; //权益项列表
}
