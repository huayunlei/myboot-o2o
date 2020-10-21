/*
 * Author: ZHAO
 * Date: 2018/10/12
 * Description:StarProductProxy.java
 */
package com.ihomefnt.o2o.service.proxy.art;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ihomefnt.common.api.ResponseVo;
import com.ihomefnt.o2o.intf.proxy.art.StarProductProxy;
import com.ihomefnt.o2o.service.manager.zeus.StrongSercviceCaller;
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
@Service
public class StarProductProxyImpl implements StarProductProxy{
	@Autowired
	private StrongSercviceCaller strongSercviceCaller;

    @Override
    public ArtStarWorkList getArtStarWorksList(Map<String, Object> paramMap) {
        ResponseVo<ArtStarWorkList> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post("cms-web.art-app.getArtStarWorksList", paramMap,  
					new TypeReference<ResponseVo<ArtStarWorkList>>() {
					});
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null) {
            return null;
        }
        if (responseVo.isSuccess()) {
			return responseVo.getData();
		}
        return null;
    }

    @Override
    public ArtStarWork getArtStarWorkDetail(Map<String, Object> paramMap) {
        ResponseVo<ArtStarWork> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post("cms-web.art-app.getArtStarWorkDetail", paramMap,  
					new TypeReference<ResponseVo<ArtStarWork>>() {
					});
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null) {
            return null;
        }
        if (responseVo.isSuccess()) {
			return responseVo.getData();
		}
        return null;
    }

    @Override
    public List<ArtStarGood> getArtStarGoodList(Map<String, Object> paramMap) {
        ResponseVo<List<ArtStarGood>> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post("cms-web.art-app.getArtStarGoodList", paramMap,  
					new TypeReference<ResponseVo<List<ArtStarGood>>>() {
					});
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null) {
            return null;
        }
        if (responseVo.isSuccess()) {
			return responseVo.getData();
		}
        return null;
    }

    @Override
    public ArtStarGood getArtStarGoodDetail(Map<String, Object> paramMap) {
        ResponseVo<ArtStarGood> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post("cms-web.art-app.getArtStarGoodDetail", paramMap,  
					new TypeReference<ResponseVo<ArtStarGood>>() {
					});
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null) {
            return null;
        }
        if (responseVo.isSuccess()) {
			return responseVo.getData();
		}
        return null;
    }

	@Override
	public List<ArtStarWork> getArtStarWorksListById(Map<String, Object> paramMap) {
        ResponseVo<List<ArtStarWork>> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post("cms-web.art-app.getArtStarWorksListById", paramMap,  
					new TypeReference<ResponseVo<List<ArtStarWork>>>() {
					});
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null) {
            return null;
        }
        if (responseVo.isSuccess()) {
			return responseVo.getData();
		}
        return null;
	}

	@Override
	public List<ArtStarWork> getStarWorksListByWorkIds(List<Integer> workIds) {
        ResponseVo<List<ArtStarWork>> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post("cms-web.art-app.getStarWorksListByWorkIds", workIds,  
					new TypeReference<ResponseVo<List<ArtStarWork>>>() {
					});
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null) {
            return null;
        }
        if (responseVo.isSuccess()) {
			return responseVo.getData();
		}
        return null;
	}

	@Override
	public List<AppArtStarSku> getStarSkuListByIds(List<Integer> skuIds) {
        ResponseVo<List<AppArtStarSku>> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post("cms-web.art-app.getStarSkuListByIds", skuIds,  
					new TypeReference<ResponseVo<List<AppArtStarSku>>>() {
					});
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null) {
            return null;
        }
        if (responseVo.isSuccess()) {
			return responseVo.getData();
		}
        return null;
	}

	@Override
	public List<ArtStarWork> getAllStarWorksListByWorkIds(List<Integer> workIds) {
        ResponseVo<List<ArtStarWork>> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post("cms-web.art-app.getAllStarWorksListByWorkIds", workIds,  
					new TypeReference<ResponseVo<List<ArtStarWork>>>() {
					});
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null) {
            return null;
        }
        if (responseVo.isSuccess()) {
			return responseVo.getData();
		}
        return null;
	}

	@Override
	public List<AppArtStarSku> getAllStarSkuListByIds(List<Integer> skuIds) {
        ResponseVo<List<AppArtStarSku>> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post("cms-web.art-app.getAllStarSkuListByIds", skuIds,  
					new TypeReference<ResponseVo<List<AppArtStarSku>>>() {
					});
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null) {
            return null;
        }
        if (responseVo.isSuccess()) {
			return responseVo.getData();
		}
        return null;
	}

	@Override
	public List<ArtCustomRecordResponse> getOrderListByArtId(Integer artId) {
        ResponseVo<List<ArtCustomRecordResponse>> responseVo = null;
        try {
            responseVo = strongSercviceCaller.post("cms-web.art-app.getOrderListByArtId", artId,  
					new TypeReference<ResponseVo<List<ArtCustomRecordResponse>>>() {
					});
        } catch (Exception e) {
            return null;
        }

        if (responseVo == null) {
            return null;
        }
        if (responseVo.isSuccess()) {
			return responseVo.getData();
		}
        return null;
	}
	
}
