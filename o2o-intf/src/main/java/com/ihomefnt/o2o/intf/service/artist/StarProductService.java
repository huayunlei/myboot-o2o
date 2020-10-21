/*
 * Author: ZHAO
 * Date: 2018/10/11
 * Description:StarProductService.java
 */
package com.ihomefnt.o2o.intf.service.artist;

import com.ihomefnt.o2o.intf.domain.artist.vo.request.StarArtRequest;
import com.ihomefnt.o2o.intf.domain.artist.vo.request.StarUserLoginRequest;
import com.ihomefnt.o2o.intf.domain.artist.vo.response.ArtCustomRecordResponse;
import com.ihomefnt.o2o.intf.domain.artist.vo.response.ArtStarGood;
import com.ihomefnt.o2o.intf.domain.artist.vo.response.ArtStarWork;
import com.ihomefnt.o2o.intf.domain.artist.vo.response.ArtStarWorkList;

import java.util.List;

/**
 * 小星星商品作品
 *
 * @author ZHAO
 */
public interface StarProductService {
    ArtStarWorkList getArtStarWorksList(StarUserLoginRequest request);

    ArtStarWork getArtStarWorkDetail(StarArtRequest request);

    List<ArtStarGood> getArtStarGoodList(StarArtRequest request);

    ArtStarGood getArtStarGoodDetail(StarArtRequest request);
    
    List<ArtCustomRecordResponse> getCustomRecordList(StarArtRequest request);
}
