package com.ihomefnt.o2o.intf.domain.homebuild.vo.response;

import com.ihomefnt.o2o.intf.domain.homepage.dto.BasePropertyResponseVo;
import lombok.Data;

import java.util.List;

@Data
public class BasePropertyListResponseVo {

    private List<BasePropertyResponseVo> styleList;
}
