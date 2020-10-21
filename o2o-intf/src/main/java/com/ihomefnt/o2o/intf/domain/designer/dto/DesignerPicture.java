package com.ihomefnt.o2o.intf.domain.designer.dto;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Created by hvk687 on 10/22/15.
 */
@Data
public class DesignerPicture {
    private Long picId;
    private String url;

    @JsonIgnore
    private Integer width;
}
