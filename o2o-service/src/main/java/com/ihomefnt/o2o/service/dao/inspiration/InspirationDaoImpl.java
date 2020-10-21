package com.ihomefnt.o2o.service.dao.inspiration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ihomefnt.o2o.intf.dao.inspiration.InspirationDao;
import com.ihomefnt.o2o.intf.domain.inspiration.dto.Article270;
import com.ihomefnt.o2o.intf.domain.inspiration.dto.ArticleComment270;
import com.ihomefnt.o2o.intf.domain.inspiration.dto.Case;
import com.ihomefnt.o2o.intf.domain.inspiration.dto.KeyValue;
import com.ihomefnt.o2o.intf.domain.inspiration.dto.Picture270;
import com.ihomefnt.o2o.intf.domain.inspiration.dto.PictureAlbum;
import com.ihomefnt.o2o.intf.domain.inspiration.dto.PictureInfo;
import com.ihomefnt.o2o.intf.domain.inspiration.dto.Strategy;
import com.ihomefnt.o2o.intf.domain.product.vo.response.AppButton;

@Repository
public class InspirationDaoImpl implements InspirationDao {

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    private static String NAME_SPACE_PRODUCT = "com.ihomefnt.o2oold.intf.product.";
    
    private static String NAME_SPACE = "com.ihomefnt.o2o.intf.dao.inspiration.InspirationDao.";

	@Override
	public List<AppButton> queryPhotoButton() {
		return sqlSessionTemplate.selectList(NAME_SPACE_PRODUCT + "queryPhotoButton");
	}

	@Override
	public List<AppButton> queryStrategyButton() {
		return sqlSessionTemplate.selectList(NAME_SPACE_PRODUCT + "queryStrategyButton");
	}
	
	@Override
	public List<PictureAlbum> queryPictureAlbumList(Map<String, Object> param) {
		return sqlSessionTemplate.selectList(NAME_SPACE + "queryPictureAlbumList",param);
	}
	
	@Override
	public List<Strategy> queryStrategyList(Map<String, Object> param) {
		return sqlSessionTemplate.selectList(NAME_SPACE + "queryStrategyList",param);
	}
	
	@Override
	public List<Strategy> queryStrategyHomeList(Map<String, Object> param) {
		return sqlSessionTemplate.selectList(NAME_SPACE + "queryStrategyHomeList",param);
	}

	@Override
	public List<Case> queryCaseList(Map<String, Object> param) {
		return sqlSessionTemplate.selectList(NAME_SPACE + "queryCaseList",param);
	}

	@Override
	public int queryPictureAlbumCount(Map<String, Object> param) {
		return sqlSessionTemplate.selectOne(NAME_SPACE + "queryPictureAlbumCount",param);
	}

	@Override
	public int queryStrategyCount(Map<String, Object> param) {
		return sqlSessionTemplate.selectOne(NAME_SPACE + "queryStrategyListCount",param);
	}

	@Override
	public int queryCaseCount(Map<String, Object> param) {
		return sqlSessionTemplate.selectOne(NAME_SPACE + "queryCaseListCount",param);
	}

	@Override
	public PictureAlbum queryPictureAlbumView(Map<String, Object> param) {
		return sqlSessionTemplate.selectOne(NAME_SPACE + "queryPictureAlbumView",param);
	}

	@Override
	public Strategy queryStrategyDetail(Map<String, Object> param) {
		return sqlSessionTemplate.selectOne(NAME_SPACE + "queryStrategyDetail",param);
	}

	@Override
	public Case queryCaseDetail(Map<String, Object> param) {
		return sqlSessionTemplate.selectOne(NAME_SPACE + "queryCaseDetail",param);
	}

	@Override
	public List<Long> queryPictureAlbumIds(Map<String, Object> param) {
		return sqlSessionTemplate.selectList(NAME_SPACE + "queryPictureAlbumIds",param);
	}

	@Override
	public int updateAlbumViewCount(Map<String, Object> param) {
		return sqlSessionTemplate.update(NAME_SPACE + "updateAlbumViewCount",param);
	}

	@Override
	public int updateStrategyViewCount(Map<String, Object> param) {
		return sqlSessionTemplate.update(NAME_SPACE + "updateStrategyViewCount",param);
	}

	@Override
	public int updateCaseViewCount(Map<String, Object> param) {
		return sqlSessionTemplate.update(NAME_SPACE + "updateCaseViewCount",param);
	}
	
	@Override
	public int updateAlbumTranspondCount(Map<String, Object> param) {
		return sqlSessionTemplate.update(NAME_SPACE + "updateAlbumTranspondCount",param);
	}

	@Override
	public int updateStrategyTranspondCount(Map<String, Object> param) {
		return sqlSessionTemplate.update(NAME_SPACE + "updateStrategyTranspondCount",param);
	}

	@Override
	public int updateCaseTranspondCount(Map<String, Object> param) {
		return sqlSessionTemplate.update(NAME_SPACE + "updateCaseTranspondCount",param);
	}
	
	@Override
    public List<String> queryCondition(String menuKey) {
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("menuKey", menuKey);
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryCondition", paramMap);
    }

	@Override
	public List<PictureInfo> queryPictureByAlbumId(Long albumId) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("albumId", albumId);
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryPictureByAlbumId", paramMap);
	}

	@Override
	public List<KeyValue> getArticleTypeList270() {
		Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("id", 13);//13表示灵感文章栏目
		return sqlSessionTemplate.selectList(NAME_SPACE + "getArticleTypeList270", paramMap);
	}

	@Override
	public List<Article270> getDataByTopAndRemain(Map<String, Object> params) {		
		return sqlSessionTemplate.selectList(NAME_SPACE + "getDataByTopAndRemain", params);
	}

	@Override
	public List<Article270> getDataByTime(Map<String, Object> params) {
		return sqlSessionTemplate.selectList(NAME_SPACE + "getDataByTime", params);
	}

	@Override
	public List<Article270> getDataByCondition(Map<String, Object> params) {
		return sqlSessionTemplate.selectList(NAME_SPACE + "getDataByCondition",params);
	}

	@Override
	public Article270 getArticleByPK270(Long articleId) {
		return sqlSessionTemplate.selectOne(NAME_SPACE + "getArticleByPK270",articleId);
	}

	@Override
	public List<ArticleComment270> getArticleCommentListByArticleId270(Map<String,Object> paramMap) {
		return sqlSessionTemplate.selectList(NAME_SPACE + "getArticleCommentListByArticleId270",paramMap);
	}

	@Override
	public List<Article270> getRecommendArticleListBySourceId(Long sourceId) {
		return sqlSessionTemplate.selectList(NAME_SPACE + "getRecommendArticleListBySourceId",sourceId);
	}

	@Override
	public int getPraiseArticleCountByArticleIdAndUserId(Map<String,Object> paramMap) {
		return sqlSessionTemplate.selectOne(NAME_SPACE + "getPraiseArticleCountByArticleIdAndUserId",paramMap);
	}

	@Override
	public int updateArticleOpByArticleId(Map<String,Object> paramMap) {
		return sqlSessionTemplate.update(NAME_SPACE + "updateArticleOpByArticleId",paramMap);
	}

	@Override
	public int insertPraiseLog(Map<String,Object> paramMap) {
		return sqlSessionTemplate.insert(NAME_SPACE + "insertPraiseLog",paramMap);
	}

	@Override
	public int addComment(Map<String, Object> paramMap) {
		return  sqlSessionTemplate.insert(NAME_SPACE + "addComment",paramMap);
	}

	@Override
	public List<Article270> searhArticleList(Map<String, Object> paramMap) {
        return sqlSessionTemplate.selectList(NAME_SPACE + "searhArticleList",paramMap);
	}

	@Override
	public int getArticleCommentTotalByArticleId270(Long articleId) {
		return sqlSessionTemplate.selectOne(NAME_SPACE + "getArticleCommentTotalByArticleId270",articleId);
	}

	@Override
	public List<Picture270> getPictureList() {
		return sqlSessionTemplate.selectList(NAME_SPACE + "getPictureList");
	}

	@Override
	public int searhArticleListTotal(String title) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("title", title);
		return sqlSessionTemplate.selectOne(NAME_SPACE + "searhArticleListTotal",paramMap);
	}

}
