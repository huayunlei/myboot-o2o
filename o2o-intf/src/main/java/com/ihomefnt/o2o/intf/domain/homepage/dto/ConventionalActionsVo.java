package com.ihomefnt.o2o.intf.domain.homepage.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.AccessType;

/**
 * Created by jiangjun on 2018/4/10.
 * 首页常规动作区按钮
 */
@Data
@Accessors(chain = true)
public class ConventionalActionsVo {

    private String icon;

    private String title;

    private String src;

    private boolean needLogin = false;
}
