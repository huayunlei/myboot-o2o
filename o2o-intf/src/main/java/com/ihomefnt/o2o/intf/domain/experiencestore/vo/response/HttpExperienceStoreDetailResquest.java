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
public class HttpExperienceStoreDetailResquest extends HttpBaseRequest {

    private Long esId;//体验店ID

}
