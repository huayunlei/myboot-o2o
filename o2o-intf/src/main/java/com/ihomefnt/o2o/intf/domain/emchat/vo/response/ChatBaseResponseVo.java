package com.ihomefnt.o2o.intf.domain.emchat.vo.response;

import lombok.Data;

import java.util.Arrays;
import java.util.List;
@Data
public class ChatBaseResponseVo {

    private List<String> commonExp; //常用语
    private String productLink; //商品的链接（暂时配成PC站的商品详情）
    
    public ChatBaseResponseVo() {
        commonExp = Arrays.asList(new String[] {"全品家产品该如何付款呢？",
                "全品家的话，哪些部分需要单独收费？","你们的品牌和质量怎么样？多久发货？", "哪些地区可以发货？"});
    }

}
