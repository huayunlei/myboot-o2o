package com.ihomefnt.o2o.intf.domain.art.dto;

import com.ihomefnt.o2o.intf.domain.art.vo.request.HttpOrderProductRequest;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class CreateArtworkOrder {

	/**
     * 所属项目
     */
    @NotNull(message="所属项目不能为空")
    private Integer projectId;
    /**
     *顾客姓名
     */
    private String customerName;

    /**
     *顾客电话
     */
    @NotBlank(message="客户电话不能为空")
    private String customerTel;
    /**
     *收货人姓名
     */
    @NotBlank(message="收货人姓名不能为空")
    private String receiverName;

    /**
     *收货人电话
     */
    @NotBlank(message="收货人电话不能为空")
    private String receiverTel;
    /**
     *  区县id
     */
    @NotNull(message="客户区域id不能为空")
    private Integer areaId;
    /**
     *  房屋小区
     */
    @NotBlank(message="客户地址houseAddress不能为空")
    private String houseAddress;
    /**
     *  楼栋号
     */
    @NotBlank(message="客户楼号buildNo不能为空")
    private String buildingNo;
    /**
     *  房屋号
     */
    @NotBlank(message="客户房间号houseNo不能为空")
    private String houseNo;
    /**
     * 软装合同编号
     */
    private String softOrderNum;
    /**
     * 下单时间
     */
    private Date orderTime;
    /**
     * 下单时间
     */
    private String orderTimeStr;

    /**
     *期望收货时间
     */
    private Date expectedReceiptTime;
    /**
     *期望收货时间
     */
    @NotBlank(message="客户期望收货时间不能为空")
    private String expectedReceiptTimeStr;

    /**
     * 订单商品信息
     */
    private List<HttpOrderProductRequest> orderProudctList;

    /**
     * 软装合同金额
     */
    @NotNull(message="项目合同不能为空")
    private BigDecimal softContractgMoney;

    /**
     * 订单备注
     */
    private String orderRemark;

    //抵扣金额

    /**
     * 抵用券id
     */
    private Integer voucherId;

    /**
     * 现金券金额
     */
    private BigDecimal cashVoucherMoney;
    
    /**
     * 艾积分金额
     */
    private BigDecimal ajbMoney;

    /**
     * 诚意金id
     * @return
     */
    private Integer earnestId;

    /**
     * 定金id
     * @return
     */

    private Integer depositId;
    
    //订单来源(1:iphone 2:android 3:m站)
    private Integer source;
}
