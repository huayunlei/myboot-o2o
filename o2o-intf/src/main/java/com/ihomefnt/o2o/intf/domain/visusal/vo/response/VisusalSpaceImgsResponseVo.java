package com.ihomefnt.o2o.intf.domain.visusal.vo.response;

import com.google.common.collect.Lists;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageConstant;
import com.ihomefnt.o2o.intf.manager.util.common.image.QiniuImageUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisusalSpaceImgsResponseVo {
    private List<String> imgs = Lists.newArrayList();

    public List<String> getImgs() {
        return CollectionUtils.isNotEmpty(imgs) ? imgs.stream().map(img -> QiniuImageUtils.compressImageAndSamePicTwo(img, 750, ImageConstant.SIZE_MIDDLE)).collect(Collectors.toList()) :
                Collections.emptyList();
    }
}
