package com.ihomefnt.o2o.intf.domain.agent.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liguolin
 * @create 2018-11-14 9:56
 **/
@ApiModel("查询银行卡信息")
public class BankCnapParamsRequestVo {


    @Data
    @ApiModel("查询总行名称参数")
    public static class BankNameParam {

        @ApiModelProperty("总行姓名前缀")
        private String keyword;
    }


    @Data
    @ApiModel("查询省份名称参数")
    public static class ProvinceNameParam {

        @ApiModelProperty("省份名称前缀")
        private String keyword;
    }


    @Data
    @ApiModel("查询市级名称参数")
    public static class CityNameParam {

        @ApiModelProperty("市级名称前缀")
        private String keyword;

        @ApiModelProperty("省级code")
        private String provinceCode;
    }

    @Data
    @ApiModel("查询市级中银行分行信息参数")
    public static class CityBankNameParam {

        @ApiModelProperty("分行名称前缀")
        private String keyword;

        @ApiModelProperty("省级code")
        private String cityCode;

        @ApiModelProperty("总行code")
        private String bankCode;

    }



}
