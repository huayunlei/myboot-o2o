package com.ihomefnt.o2o.service.manager.config;

import com.ihomefnt.o2o.intf.manager.util.common.image.ImageSize;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shirely_geng on 15-2-9.
 */
public class ImageConfig extends ImageSize{

    private static final ImageConfig imageConfig = new ImageConfig();

    private static Map<Integer, ImageProperty> imageConfigMap = new HashMap<>();

    public static ImageConfig init() {
        imageConfigMap.put(1, new ImageProperty(1080, 540));
        imageConfigMap.put(2, new ImageProperty(540, 300));
        imageConfigMap.put(3, new ImageProperty(200, 200));
        return imageConfig;
    }

    public Map<Integer, ImageProperty> getImageConfigMap() {
        return imageConfigMap;
    }

}
