package com.ihomefnt.o2o.intf.domain.designer.dto;

import lombok.Data;
import net.sf.json.JSONArray;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Created by hvk687 on 10/22/15.
 */
@Data
public class DesignerSuit {
    private String suitName;
    private Long suitId;
    private String suitPic;

    @JsonIgnore
    private String rawImages;
    public String getSuitPic() {
        try {
            JSONArray jsonArray = JSONArray.fromObject(rawImages);
            suitPic = jsonArray.getString(0);
        } catch (Exception e) {
        }
        return suitPic;
    }
}
