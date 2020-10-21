package com.ihomefnt.o2o.service.service.art;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ihomefnt.o2o.intf.domain.art.vo.response.ArtWordListResponseVo;
import com.ihomefnt.o2o.intf.service.art.ArtWordService;
import com.ihomefnt.o2o.intf.manager.constant.program.ProductProgramPraise;
import com.ihomefnt.o2o.intf.proxy.program.KeywordWcmProxy;
import com.ihomefnt.o2o.intf.domain.program.dto.KeywordListResponseVo;
import com.ihomefnt.o2o.intf.domain.art.dto.KeywordVo;

@Service
public class ArtWordServiceImpl implements ArtWordService{
	
	@Autowired 
	private KeywordWcmProxy keywordWcmProxy;
	
	@Override
	public ArtWordListResponseVo getArtWordList() {
		KeywordListResponseVo listResponseVo = keywordWcmProxy.getKeywordList(ProductProgramPraise.KEYWORD_ART_LIST);
		if (listResponseVo != null && !CollectionUtils.isEmpty(listResponseVo.getKeywordList())) {
			List<KeywordVo> keywordList = listResponseVo.getKeywordList();
    		List<String> words = new ArrayList<String>();
    		
    		if (!CollectionUtils.isEmpty(words)) {
    			for(KeywordVo artWord : keywordList){
    				words.addAll(artWord.getWords());
    			}
    			return new ArtWordListResponseVo(words);
    		}
		}
		
		return null;
	}

}
