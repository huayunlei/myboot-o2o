package com.ihomefnt.o2o.intf.domain.program.customgoods.response;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 属性信息
 *
 * @author liyonggang
 * @create 2019-03-18 16:43
 */
@ApiModel("属性信息")
@Data
@Accessors(chain = true)
public class Attribute<N, V> {

    private N attributeName;

    private V attributeValue;

    private Attribute() {
    }

    public static <N, V> Attribute<N, V> build(N attributeName, V attributeValue) {
        return new Attribute().setAttributeName(attributeName).setAttributeValue(attributeValue);
    }
}
