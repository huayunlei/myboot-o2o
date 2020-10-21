package com.ihomefnt.o2o.intf.domain.pay.vo.request;

import com.alibaba.fastjson.JSONObject;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.math.BigDecimal;

import static com.ihomefnt.o2o.intf.manager.constant.pay.PayConstants.MODULE_CODE_FGW;

/**
 * 统一支付请求bean
 * @author jerfan cang
 * @date 2018/9/25 15:16
 */
@ApiModel("统一支付请求参数")
@Data
public class PayforRequestVo extends HttpBaseRequest {

    /**
     * 订单编号
     */
    //@NotBlank(message = "订单编号不能为空")
    private String orderNum;

    /**
     * 订单id 前端传这个
     */
    private String orderId;

    /**
     * 订单类型 拼团传16（新星星专属）画屏订单17 画作订单18
     */
    private Integer orderType;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 实际支付金额 选定的金额
     */
    @NotBlank(message = "支付金额不能为空")
    private BigDecimal actualPayMent;

    /**
     * 订单总价
     */
    private BigDecimal totalPayMent;

    /**
     * 订单来源PC("PC网站", 1), APP("APP", 2), H5("H5", 3)
     */
    private Integer source;

    /**
     * 渠道来源WX("WX", 1), ALIPAY("ALIPAY", 2), LIANLIAN("LIANLIAN", 3 ) 微信拼团 （4）
     */
    //@NotBlank(message = "支付来源")
    private Integer channelSource;

    /**
     * 用户IP
     */
    private String ip;

    /**
     * 商品展示地址
     * <p>
     * 需以http://开头的完整路径
     * 例如：http://www.ihomefnt.com/spacedetail/727
     */
    private String showUrl;

    /**
     * 支付完成跳转页面
     */
    private String returnUrl;

    /**
     * 支付完成后异步通知页面路径
     */
    private String notifyUrl;

    /**-----商品订单号------**/

    private String outTradeNo;

    /**
     * 订单描述
     */
    private String orderInfo;


    /**--------以下连连-------**/

    /**
     * 账户名
     */
    @NotBlank(message = "账户名不能为空")
    private String acctName;

    /**
     * 银行卡号
     */
    @NotBlank(message = "银行卡号不能为空")
    private String cardNo;

    /**
     * 身份证号
     */
    @NotBlank(message = "身份证号不能为空")
    private String idNo;

    /**
     * 风控字段
     */
    private String riskItem;

    /**
     * 虚拟商品销售：101001,实物商品销售：109001
     */
    private String busiPartner;

    /**
     * 订单时间
     */
    private String orderDt;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 加签方式
     */
    private String signType;

    /**
     * 商户编号是商户在连连钱包支付平台上开设的商户号码，为18位数字，如：201304121000001004
     */
    private String oidPartner;

    /**
     * 加签字符串
     */
    private String sign;
    
    private String openId;

    /**
     * 操作系统 1android 2ios
     */
    private Integer platform;

    public JSONObject parseJson() throws RuntimeException{
        JSONObject json = new JSONObject();
        //bean.put("orderId",orderId);
        json.put("orderNum",orderNum);
        json.put("orderType",orderType);
        json.put("userId",userId);
        json.put("actualPayMent",actualPayMent);
        json.put("totalPayMent",totalPayMent);

        json.put("source",source);
        json.put("channelSource",channelSource);
        json.put("ip",ip);
        json.put("showUrl",showUrl);
        json.put("returnUrl",returnUrl);

        json.put("notifyUrl",notifyUrl);
        json.put("outTradeNo",outTradeNo);
        json.put("orderInfo",orderInfo);
        json.put("acctName",acctName);
        json.put("cardNo",cardNo);

        json.put("idNo",idNo);
        json.put("riskItem",riskItem);
        json.put("busiPartner",busiPartner);
        json.put("orderDt",orderDt);
        json.put("goodsName",goodsName);

        json.put("signType",signType);

        json.put("oidPartner",oidPartner);
        json.put("sign",sign);
        json.put("openId",openId);
        // 业务编码
        json.put("moduleCode",MODULE_CODE_FGW);
        json.put("platform",platform);

        return json;
    }


}
