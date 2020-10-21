/**
 * 
 */
package com.ihomefnt.o2o.intf.domain.experiencestore.vo.response;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;


/**
 * @author Administrator
 *
 */
@Data
public class HttpExperienceStoreResquest extends HttpBaseRequest {

    private Double latitude;
    private Double longitude;
    
    private int pageSize;
    private int pageNo;
    private String searchItem;


}
