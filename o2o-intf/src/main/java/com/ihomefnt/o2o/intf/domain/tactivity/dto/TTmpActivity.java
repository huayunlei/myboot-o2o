package com.ihomefnt.o2o.intf.domain.tactivity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Data
@EqualsAndHashCode
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TTmpActivity {
    private Long activityId;
    private String name;
    private String mobile;
    private String activateCode;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Integer status;
    
    private String createTimeStr;
    private String updateTimeStr;
    public String getCreateTimeStr() {
        String str = null;
        if(null != createTime){
            DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm");
            str = df.format(createTime);
        }
        return str;
    }
    public String getUpdateTimeStr() {
        String str = null;
        if(null != updateTime){
            DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm");
            str = df.format(updateTime);
        }
        return str;
    }

}
