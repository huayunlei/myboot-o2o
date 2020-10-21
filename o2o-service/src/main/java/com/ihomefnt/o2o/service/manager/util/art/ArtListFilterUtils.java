package com.ihomefnt.o2o.service.manager.util.art;

import com.ihomefnt.o2o.intf.domain.art.dto.Artwork;
import com.ihomefnt.o2o.service.service.collage.FilterProductConfig;
import com.ihomefnt.o2o.intf.manager.util.common.bean.JsonUtils;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jerfan cang
 * @date 2018/10/24 13:11
 */
public class ArtListFilterUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArtListFilterUtils.class);
    @Resource(name="filterProductConfig")
    public static FilterProductConfig filterProductConfig;

    /**
     * by jerfan cang 2018-10-20
     * 拼团活动 原价99的大布丁保温杯 新配一个 59元的 上架艺术品
     * 在艺术品列表中需要过滤掉 拼团的商品
     * 过滤到活动商品
     * @param list List<Artwork>
     * @return resultList List<Artwork>
     */
    public static  List<Artwork> filterActivityProduct( List<Artwork> list){
        if(CollectionUtils.isEmpty(list)){
            return  null;
        }
        List<Artwork> resultList = new ArrayList<>();
        for(Artwork art : list){
            if(art.getArtWorkId()!=3369L){
                resultList.add(art);
            }else{
                LOGGER.info("had successfully filter 3369 .");
            }
        }
        LOGGER.info("result sku list  is :"+ JsonUtils.obj2json(resultList));
        return resultList;
        /*List<Artwork> resultList =  new ArrayList<>();
        resultList.addAll(list);
        // 要过滤的商品
        Set<Integer> idList=  new HashSet<>();
        //filterProductConfig.getFilterProduct();
        List<Integer> skuIds =  new ArrayList<>();
        skuIds.add(3369);
        try{
            filterProductConfig.addFilterProduct(skuIds);
        }catch (Exception e){
        }
        if(null != idList && idList.size() >0){
            for(Integer artWorkId : idList){
                for(Artwork art : list ){
                    if(Long.parseLong(artWorkId+"") ==art.getArtWorkId()){
                        resultList.remove(art);
                    }else{
                        continue;
                    }
                }
            }
        } else {
            return resultList;
        }
        return resultList;*/

    }

}
