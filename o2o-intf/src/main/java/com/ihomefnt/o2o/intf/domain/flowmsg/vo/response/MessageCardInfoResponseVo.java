package com.ihomefnt.o2o.intf.domain.flowmsg.vo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author hua
 * @Date 2019-11-18 15:30
 */
@Data
@Accessors(chain = true)
@ApiModel("信息流卡片信息")
public class MessageCardInfoResponseVo {
    @ApiModelProperty("卡片样式：1单张大图；2三图；3单张小图")
    private int cardType;

    @ApiModelProperty("卡片urls")
    private List<String> imgList;

    private String cardImgs; //卡片图urls，逗号隔开

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("副标题")
    private String subTitle;

    @ApiModelProperty("主文案")
    private String content;

    @ApiModelProperty("副文案")
    private String subContent;

    @ApiModelProperty("RN://跳转地址")
    private String openUrl;

    @ApiModelProperty("是否已度： 0未读，1已读")
    private int hasRead;

    @ApiModelProperty("是否置顶 0不置顶，1置顶")
    private int isPushTop;

    @ApiModelProperty("推送时间")
    @JsonFormat(pattern = "MM月dd日 HH:mm")
    private Date pushTime;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty("消息序号")
    private int messageNum;
}
