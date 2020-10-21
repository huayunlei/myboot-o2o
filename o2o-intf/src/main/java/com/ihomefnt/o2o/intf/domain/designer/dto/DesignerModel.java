package com.ihomefnt.o2o.intf.domain.designer.dto;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Created by hvk687 on 8/17/15.
 */
@Data
public class DesignerModel {
    @JsonIgnore
    private Long designerId;

    private String avatar;
    private String name;
    private String brief;
    private String nick;
    private Integer gender;
    private String mobile;//this property should load from t_user table;
    private String telephone;
    private String residency;
    private String company;
    private String designCase;

}