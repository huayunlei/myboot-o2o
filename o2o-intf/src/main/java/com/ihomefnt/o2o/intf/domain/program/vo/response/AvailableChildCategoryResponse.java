package com.ihomefnt.o2o.intf.domain.program.vo.response;

import lombok.Data;

import java.util.List;

/**
 * @author liyonggang
 * @create 2019-11-05 11:19
 */
@Data
public class AvailableChildCategoryResponse {


    private List<ChildCategoryList> relationCategoryList;

    @Data
    public static class ChildCategoryList {
        private Integer categoryId;
        private String categoryName;
    }
}
