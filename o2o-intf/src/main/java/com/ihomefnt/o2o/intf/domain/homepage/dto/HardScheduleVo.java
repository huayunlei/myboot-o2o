package com.ihomefnt.o2o.intf.domain.homepage.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.AccessType;

/**
 * 施工进度
 *
 * @author jiangjun
 * @version 2.0, 2018-04-11 下午1:36
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Data
@Accessors(chain = true)
public class HardScheduleVo {

    //预计交房日期
    private String houseDeliverDate;

    //预计进场开工日期
    private String planBeginDate;

    //实际进场开工日期
    private String actualBeginDate;

    //软装进场时间
    private String expectSendDate;

    //完成交付时间
    private String actualEndDate;

    //整体已开工时长
    private Integer totalDays = 0;

    //硬装进度
    private String hard;

    //软装进度
    private String soft;

    //订单类型 0:全品家 1：全品家软 2：全品家硬
    private Integer orderType;

    //工地摄像头信息（呈现最新的工地近况图片。如无摄像头，则最近的艾管家上传的图片）
    private String url;
}
