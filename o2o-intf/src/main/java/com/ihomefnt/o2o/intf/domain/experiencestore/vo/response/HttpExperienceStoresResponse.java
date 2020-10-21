/**
 * 
 */
package com.ihomefnt.o2o.intf.domain.experiencestore.vo.response;

import lombok.Data;

import java.util.List;

/**
 * @author Administrator
 *
 */
@Data
public class HttpExperienceStoresResponse {

    private List<HttpExperienceStoreResponse> experienceStores;//体验店
    
    private int totalRecords;
    private int totalPages;
}
