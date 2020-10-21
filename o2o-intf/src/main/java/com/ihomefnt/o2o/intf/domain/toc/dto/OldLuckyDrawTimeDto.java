package com.ihomefnt.o2o.intf.domain.toc.dto;

import io.swagger.annotations.ApiModel;

import java.util.List;

/**
 * Created by Administrator on 2018/11/21 0021.
 */
@ApiModel("老用户抽奖剩余次数列表")
public class OldLuckyDrawTimeDto {

    private List<RemainTimeDto> remainTimeList;
}
