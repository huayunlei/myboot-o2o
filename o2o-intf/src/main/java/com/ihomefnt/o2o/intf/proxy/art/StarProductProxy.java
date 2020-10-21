/*
 * Author: ZHAO
 * Date: 2018/10/12
 * Description:IStarProductProxy.java
 */
package com.ihomefnt.o2o.intf.proxy.art;

import java.util.List;
import java.util.Map;

import com.ihomefnt.o2o.intf.domain.artist.vo.response.AppArtStarSku;
import com.ihomefnt.o2o.intf.domain.artist.vo.response.ArtCustomRecordResponse;
import com.ihomefnt.o2o.intf.domain.artist.vo.response.ArtStarGood;
import com.ihomefnt.o2o.intf.domain.artist.vo.response.ArtStarWork;
import com.ihomefnt.o2o.intf.domain.artist.vo.response.ArtStarWorkList;

/**
 * 小星星商品作品
 *
 * @author ZHAO
 */
public interface StarProductProxy {
    ArtStarWorkList getArtStarWorksList(Map<String, Object> paramMap);

    ArtStarWork getArtStarWorkDetail(Map<String, Object> paramMap);

    List<ArtStarGood> getArtStarGoodList(Map<String, Object> paramMap);

    ArtStarGood getArtStarGoodDetail(Map<String, Object> paramMap);
    
    List<ArtStarWork> getArtStarWorksListById(Map<String, Object> paramMap);

    /**
     * 根据作品ID查询作品信息
     * @param workIds
     * @return
     */
    List<ArtStarWork> getStarWorksListByWorkIds(List<Integer> workIds);
    
    /**
     * 根据商品SKUID查询SKU和商品信息
     * @param skuIds
     * @return
     */
    List<AppArtStarSku> getStarSkuListByIds(List<Integer> skuIds);
    
    List<ArtCustomRecordResponse> getOrderListByArtId(Integer artId);

    /**
     * 根据作品ID查询作品信息[含已删除的]
     * @param workIds
     * @return
     */
    List<ArtStarWork> getAllStarWorksListByWorkIds(List<Integer> workIds);
    
    /**
     * 根据商品SKUID查询SKU和商品信息[含已删除的]
     * @param skuIds
     * @return
     */
    List<AppArtStarSku> getAllStarSkuListByIds(List<Integer> skuIds);
    
}
