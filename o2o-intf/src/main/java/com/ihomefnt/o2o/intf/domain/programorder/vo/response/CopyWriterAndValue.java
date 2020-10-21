package com.ihomefnt.o2o.intf.domain.programorder.vo.response;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用于同时返回文案和值
 *
 * @author liyonggang
 * @create 2019-02-18 16:02
 */
@ApiModel("文案和值")
@Data
@Accessors(chain = true)
public class CopyWriterAndValue<C, V> {
    private C copyWriter;
    private V value;

    public CopyWriterAndValue(C copyWriter, V value) {
        this.copyWriter = copyWriter;
        this.value = value;
    }

    public static <C, V> CopyWriterAndValue<C, V> build(C copyWriter, V value) {
        return new CopyWriterAndValue<>(copyWriter, value);
    }

}
