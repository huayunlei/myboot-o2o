package com.ihomefnt.o2o.service.service.inspiration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ihomefnt.o2o.intf.dao.inspiration.InspirationDao;
import com.ihomefnt.o2o.intf.dao.product.CollectionDao;
import com.ihomefnt.o2o.intf.dao.product.ProductTypeDao;
import com.ihomefnt.o2o.intf.dao.user.UserDao;
import com.ihomefnt.o2o.intf.domain.inspiration.dto.Article270;
import com.ihomefnt.o2o.intf.domain.inspiration.dto.ArticleComment270;
import com.ihomefnt.o2o.intf.domain.inspiration.dto.Case;
import com.ihomefnt.o2o.intf.domain.inspiration.dto.KeyValue;
import com.ihomefnt.o2o.intf.domain.inspiration.dto.Picture270;
import com.ihomefnt.o2o.intf.domain.inspiration.dto.PictureAlbum;
import com.ihomefnt.o2o.intf.domain.inspiration.dto.PictureInfo;
import com.ihomefnt.o2o.intf.domain.inspiration.dto.Strategy;
import com.ihomefnt.o2o.intf.domain.inspiration.vo.request.HttpArticleRequest270;
import com.ihomefnt.o2o.intf.domain.inspiration.vo.request.HttpCommentRequest270;
import com.ihomefnt.o2o.intf.domain.inspiration.vo.request.HttpInspirationRequest;
import com.ihomefnt.o2o.intf.domain.inspiration.vo.request.HttpInspirationRequest270;
import com.ihomefnt.o2o.intf.domain.inspiration.vo.request.HttpMoreInspirationRequest;
import com.ihomefnt.o2o.intf.domain.inspiration.vo.request.HttpSearchArticleRequest270;
import com.ihomefnt.o2o.intf.domain.inspiration.vo.response.HttpArticleResponse270;
import com.ihomefnt.o2o.intf.domain.inspiration.vo.response.HttpCaseDetailResponse;
import com.ihomefnt.o2o.intf.domain.inspiration.vo.response.HttpCaseListResponse;
import com.ihomefnt.o2o.intf.domain.inspiration.vo.response.HttpCommentResponse270;
import com.ihomefnt.o2o.intf.domain.inspiration.vo.response.HttpInspirationResponse270;
import com.ihomefnt.o2o.intf.domain.inspiration.vo.response.HttpInspirationResponse290;
import com.ihomefnt.o2o.intf.domain.inspiration.vo.response.HttpPictureDetailResponse;
import com.ihomefnt.o2o.intf.domain.inspiration.vo.response.HttpPictureListResponse;
import com.ihomefnt.o2o.intf.domain.inspiration.vo.response.HttpSearchArticleResponse270;
import com.ihomefnt.o2o.intf.domain.inspiration.vo.response.HttpStrategyDetailResponse;
import com.ihomefnt.o2o.intf.domain.inspiration.vo.response.HttpStrategyListResponse;
import com.ihomefnt.o2o.intf.domain.product.doo.ClassifyNode;
import com.ihomefnt.o2o.intf.domain.product.doo.TCollection;
import com.ihomefnt.o2o.intf.domain.product.doo.TreeNode;
import com.ihomefnt.o2o.intf.domain.product.vo.response.AppButton;
import com.ihomefnt.o2o.intf.domain.user.doo.LogDo;
import com.ihomefnt.o2o.intf.manager.constant.inspiration.InspirationConstant;
import com.ihomefnt.o2o.intf.service.inspiration.InspirationService;
import com.ihomefnt.o2o.intf.service.product.ProductService;
import com.ihomefnt.o2o.intf.service.user.UserService;
import com.ihomefnt.o2o.intf.domain.common.http.HttpBasePageResponse;
import com.ihomefnt.o2o.service.manager.config.ImageConfig;
import com.ihomefnt.o2o.service.manager.config.ImageProperty;
import com.ihomefnt.o2o.intf.domain.shareorder.vo.request.HttpShareOrderRequest;
import com.ihomefnt.o2o.intf.domain.shareorder.dto.ShareOrder;
import com.ihomefnt.o2o.intf.service.shareorder.ShareOrderService;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageSize;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.QiniuImageUtils;
import com.ihomefnt.o2o.intf.manager.util.common.VersionUtil;

@Service
public class InspirationServiceImpl implements InspirationService {

	@Autowired
	InspirationDao inspirationDao;
	
	@Autowired
    ProductTypeDao productTypeDao;
	
	@Autowired
    ImageConfig imageConfig;
	
	@Autowired
    UserDao userDao;
	
	@Autowired
    ProductService productService;
	
	@Autowired
    CollectionDao collectionDao;
	
	@Autowired
    UserService userService;
	
	@Autowired 
	private ShareOrderService shareOrderService;
	
	@Value("${host}")
    public String host;

	@Override
	public List<AppButton> queryPhotoButton() {
		return inspirationDao.queryPhotoButton();
	}

	@Override
	public List<AppButton> queryStrategyButton() {
		return inspirationDao.queryStrategyButton();
	}

	@Override
	public HttpCaseListResponse queryCaseList(HttpMoreInspirationRequest request) {
        HttpCaseListResponse response = new HttpCaseListResponse();            
        
        if (null != request.getIsNavigation() && request.getIsNavigation()) {    
        	String nodeStr = "570,600";
            List<ClassifyNode> classifyNodes = productTypeDao.queryClassifyNodeByIds(nodeStr);
            if (classifyNodes != null && !classifyNodes.isEmpty()) { //分类
                TreeNode rootNode = new TreeNode();
                rootNode.setMenuId(1l);
                createClassifyTree(rootNode, classifyNodes);
                response.setClassifyTreeList(rootNode.getChildren());
            }
        }

        List<Long> filterIds = request.getFilterIdList();
        
        String filterStrIds = "";
        if (null != filterIds && filterIds.size() > 0) {
            for (int i = 0; i < filterIds.size(); i++) {
                if (StringUtils.isNotBlank(filterStrIds)) {
                    filterStrIds = filterStrIds + "," + filterIds.get(i);
                } else {
                    filterStrIds = "" + filterIds.get(i);
                }
            }
        }

        String conditionS = "";
        String orderSort = "";
        if (StringUtils.isNotBlank(filterStrIds)) {
            List<String> c = inspirationDao.queryCondition(filterStrIds);
            for (int i = 0; i < c.size(); i++) {
                String str = c.get(i);
                if (StringUtils.isNotBlank(str)) {
                    if (str.contains("and") || str.contains("AND")) {
                        if (StringUtils.isNotBlank(conditionS)) {
                            conditionS = "  " + conditionS + "  " + str;
                        } else {
                            conditionS = "  " + str;
                        }
                    } else {
                        if (StringUtils.isNotBlank(orderSort)) {
                            orderSort = "  " + orderSort + "," + str;
                        } else {
                            orderSort = "  " + str;
                        }
                    }
                }
            }
        }
        
        int pageSize = request.getPageSize() > 0 ? request.getPageSize() : 10;
        int pageNo = request.getPageNo() > 0 ? request.getPageNo() : 1;

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("size", pageSize);
        paramMap.put("from", (pageNo - 1) * pageSize);
        if (StringUtils.isNotBlank(conditionS)) {
            paramMap.put("conditionS", conditionS);
        }

        if (StringUtils.isNotBlank(orderSort)) {
            paramMap.put("orderSort", orderSort);
        }

        List<Case> caseList = inspirationDao.queryCaseList(paramMap);

        if (null != caseList && caseList.size() > 0) {
            for (Case caseInfo : caseList) {
                List<String> imgList = ImageUtil.removeEmptyStr(caseInfo.getImages());
                List<String> strResponseList = new ArrayList<String>();
                if (null != imgList && imgList.size() > 0) {
                    for (String str : imgList) {
                        if (null != str && !"".equals(str)) {
                            str += appendImageMethod(ImageConfig.SIZE_MEDIUM);
                            strResponseList.add(str);
                        }
                    }
                }
                caseInfo.setImageList(strResponseList);
            }
        }
        response.setCaseList(caseList);
        int totalRecords = inspirationDao.queryCaseCount(paramMap);
        response.setTotalRecords(totalRecords);
        int totalPages = (int) ((totalRecords + pageSize - 1) / pageSize);
        response.setTotalPages(totalPages);
        return response;
	}
	
	@Override
	public HttpCaseDetailResponse queryCaseDetail(HttpInspirationRequest request) {
        HttpCaseDetailResponse response = new HttpCaseDetailResponse();  
        
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("caseId", request.getInspirationId());
        Case caseInfo = inspirationDao.queryCaseDetail(param);
        List<String> imgList = ImageUtil.removeEmptyStr(caseInfo.getImages());
        List<String> strResponseList = new ArrayList<String>();
        if (null != imgList && imgList.size() > 0) {
            for (String str : imgList) {
                if (null != str && !"".equals(str)) {
                    //str += appendImageMethod(ImageConfig.SIZE_MEDIUM);
                    strResponseList.add(str);
                }
            }
        }
        caseInfo.setImageList(strResponseList);
        caseInfo.setCreateTime(caseInfo.getCreateTime().substring(0, 10));
        caseInfo.setDesc("https://m.ihomefnt.com" + "/caseDetail/" + request.getInspirationId());
        response.setCaseInfo(caseInfo);
        
        LogDo tLog = userService.isLoggedIn(request.getAccessToken());
        if (tLog != null && null != tLog.getuId()) {
        	TCollection collection = collectionDao.queryCollection(request.getInspirationId(), tLog.getuId(), 5l);
            if (collection == null || collection.getStatus() == 0) {
            	response.setCollection(0);
            } else {
            	response.setCollection(1);
            }
        } else {
        	response.setCollection(0);
        }
        
        return response;
	}
	
	@Override
	public HttpStrategyListResponse queryStrategyList(HttpMoreInspirationRequest request) {
		HttpStrategyListResponse response = new HttpStrategyListResponse();            
        
        int pageSize = request.getPageSize() > 0 ? request.getPageSize() : 10;
        int pageNo = request.getPageNo() > 0 ? request.getPageNo() : 1;

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("size", pageSize);
        paramMap.put("from", (pageNo - 1) * pageSize);
        
        paramMap.put("nodeId", request.getNodeId());
        
        String filterStrIds = null;
        
        if(null != request.getNodeId()&&request.getNodeId().intValue() != 595){
        	filterStrIds = request.getNodeId().toString();
        }
        String conditionS = "";
        if (StringUtils.isNotBlank(filterStrIds)) {
            List<String> c = inspirationDao.queryCondition(filterStrIds);
            for (int i = 0; i < c.size(); i++) {
                String str = c.get(i);
                if (StringUtils.isNotBlank(str)) {
                    if (str.contains("and") || str.contains("AND")) {
                        if (StringUtils.isNotBlank(conditionS)) {
                            conditionS = "  " + conditionS + "  " + str;
                        } else {
                            conditionS = "  " + str;
                        }
                    }
                }
            }
        }
        if (StringUtils.isNotBlank(conditionS)) {
            paramMap.put("conditionS", conditionS);
        }
        List<Strategy> StrategyList = inspirationDao.queryStrategyList(paramMap);
        List<Strategy> StrategyHomeList = null;
        if(null == request.getNodeId()||request.getNodeId().intValue() == 595){
        	StrategyHomeList = inspirationDao.queryStrategyHomeList(paramMap);
        }

        if (null != StrategyHomeList && StrategyHomeList.size() > 0) {
            for (Strategy strategy : StrategyHomeList) {
                List<String> imgList = ImageUtil.removeEmptyStr(strategy.getImages());
                List<String> strResponseList = new ArrayList<String>();
                if (null != imgList && imgList.size() > 0) {
                    for (String str : imgList) {
                        if (null != str && !"".equals(str)) {
                            str += appendImageMethod(ImageConfig.SIZE_MEDIUM);
                            strResponseList.add(str);
                        }
                    }
                }
                strategy.setImageList(strResponseList);
            }
        }
        
        if (null != StrategyList && StrategyList.size() > 0) {
            for (Strategy strategy : StrategyList) {
                List<String> imgList = ImageUtil.removeEmptyStr(strategy.getImages());
                List<String> strResponseList = new ArrayList<String>();
                if (null != imgList && imgList.size() > 0) {
                    for (String str : imgList) {
                        if (null != str && !"".equals(str)) {
                            str += appendImageMethod(ImageConfig.SIZE_MEDIUM);
                            strResponseList.add(str);
                        }
                    }
                }
                strategy.setImageList(strResponseList);
            }
        }
        response.setStrategyList(StrategyList);
        response.setStrategyHomeList(StrategyHomeList);
        int totalRecords = inspirationDao.queryStrategyCount(paramMap);
        response.setTotalRecords(totalRecords);
        int totalPages = (int) ((totalRecords + pageSize - 1) / pageSize);
        response.setTotalPages(totalPages);
        return response;
	}

	@Override
	public HttpStrategyDetailResponse queryStrategyDetail(
			HttpInspirationRequest request) {
		HttpStrategyDetailResponse response = new HttpStrategyDetailResponse();  
        
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("strategyId", request.getInspirationId());
        
        Strategy strategy = inspirationDao.queryStrategyDetail(param);
        
        List<String> imgList = ImageUtil.removeEmptyStr(strategy.getImages());
        List<String> strResponseList = new ArrayList<String>();
        if (null != imgList && imgList.size() > 0) {
            for (String str : imgList) {
                if (null != str && !"".equals(str)) {
                    //str += appendImageMethod(ImageConfig.SIZE_MEDIUM);
                    strResponseList.add(str);
                }
            }
        }
        strategy.setImageList(strResponseList);
        strategy.setCreateTime(strategy.getCreateTime().substring(0, 10));
        strategy.setDesc("https://m.ihomefnt.com" + "/strategyDetail/" + request.getInspirationId());
        response.setStrategy(strategy);
        
        LogDo tLog = userService.isLoggedIn(request.getAccessToken());
        if (tLog != null && null != tLog.getuId()) {
        	TCollection collection = collectionDao.queryCollection(request.getInspirationId(), tLog.getuId(), 4l);
            if (collection == null || collection.getStatus() == 0) {
            	response.setCollection(0);
            } else {
            	response.setCollection(1);
            }
        } else {
        	response.setCollection(0);
        }
        
        return response;
	}

	@Override
	public HttpPictureDetailResponse queryPictureAlbumView(HttpInspirationRequest request) {
		HttpPictureDetailResponse response = new HttpPictureDetailResponse();  
        
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("albumId", request.getInspirationId());
        
        PictureAlbum pictureAlbum = inspirationDao.queryPictureAlbumView(param);
        response.setCurrentAlbumInfo(pictureAlbum);
        
        return response;
	}
	
	@Override
	public HttpPictureListResponse queryPictureAlbumList(HttpMoreInspirationRequest request, Long userId) {
		HttpPictureListResponse response = new HttpPictureListResponse();            
        
        if (null != request.getIsNavigation() && request.getIsNavigation()) {            
            List<ClassifyNode> classifyNodes = productTypeDao.queryClassifyNodeByIds("530,550");
            if (classifyNodes != null && !classifyNodes.isEmpty()) { //分类
                TreeNode rootNode = new TreeNode();
                rootNode.setMenuId(1l);
                createClassifyTree(rootNode, classifyNodes);
                
                if (null != request.getNodeId()) {
                    List<TreeNode> resList = new ArrayList<TreeNode>();
                    TreeNode node = new TreeNode();
                    iteratorTree(rootNode, request.getNodeId(), node);
                    
                    if (null != node && null != node.getChildren() && node.getChildren().size() == 0) {
                    	TreeNode fatherNode = new TreeNode();
                    	iteratorFatherOfNode(rootNode, request.getNodeId(), fatherNode);
                    	fatherNode.setName(node.getName());
                    	resList.add(fatherNode);
                    }
                    if (null != node && null != node.getChildren()
                            && node.getChildren().size() > 0) {
                        resList.add(node);
                    }
                    resList.add(rootNode.getChildren().get(1));
                    response.setClassifyTreeList(resList);
                } else {
                	response.setClassifyTreeList(rootNode.getChildren());
                }
                //response.setClassifyTreeList(rootNode.getChildren());
            }
        }

        List<Long> filterIds = request.getFilterIdList();
        
        if(null != request.getNodeId()){
        	if(null == filterIds){
        		filterIds = new ArrayList<Long>();
        		filterIds.add(request.getNodeId());
        	}
        }
        
        String filterStrIds = "";
        if (null != filterIds && filterIds.size() > 0) {
            for (int i = 0; i < filterIds.size(); i++) {
                if (StringUtils.isNotBlank(filterStrIds)) {
                    filterStrIds = filterStrIds + "," + filterIds.get(i);
                } else {
                    filterStrIds = "" + filterIds.get(i);
                }
            }
        }

        String conditionS = "";
        if (StringUtils.isNotBlank(filterStrIds)) {
            List<String> c = inspirationDao.queryCondition(filterStrIds);
            for (int i = 0; i < c.size(); i++) {
                String str = c.get(i);
                if (StringUtils.isNotBlank(str)) {
                    if (str.contains("and") || str.contains("AND")) {
                        if (StringUtils.isNotBlank(conditionS)) {
                            conditionS = "  " + conditionS + "  " + str;
                        } else {
                            conditionS = "  " + str;
                        }
                    }
                }
            }
        }
        
        int pageSize = request.getPageSize() > 0 ? request.getPageSize() : 10;
        int pageNo = request.getPageNo() > 0 ? request.getPageNo() : 1;

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("size", pageSize);
        paramMap.put("from", (pageNo - 1) * pageSize);
        if (StringUtils.isNotBlank(conditionS)) {
            paramMap.put("conditionS", conditionS);
        }

        List<PictureAlbum> pictureAlbumList = inspirationDao.queryPictureAlbumList(paramMap);
        
        int bigPicWidt = 0;
        int needPicWidt = 0;
        if (request.getNeedPicWidt() != 0){
        	needPicWidt = request.getNeedPicWidt();
        } else {
        	if(request!=null&&request.getWidth()!=null)
        	needPicWidt = request.getWidth()/2;
        }
        bigPicWidt = needPicWidt * 3;
        if (null != pictureAlbumList && pictureAlbumList.size() > 0) {
            for (PictureAlbum album : pictureAlbumList) {
            	
            	List<PictureInfo> pictureInfoList = inspirationDao.queryPictureByAlbumId(album.getAlbumId());
            	if(null == pictureInfoList || pictureInfoList.size() < 1){
            		continue;
            	}
            	PictureInfo firstPicture = pictureInfoList.get(0);
            	double pictureWidth = firstPicture.getWidth() > 0 ? firstPicture.getWidth() : needPicWidt;
                double pictureHeight = firstPicture.getHeight() > 0 ? firstPicture.getHeight() : needPicWidt;
                double ratio = pictureHeight/pictureWidth;
                int needHeight = (int) (ratio * needPicWidt);
                int bigheight = (int) (ratio * bigPicWidt);
                if(needPicWidt!=0)
                album.setUrl(firstPicture.getImageUrl() + appendImageMethod(needPicWidt,needHeight));
                album.setWidth(needPicWidt);
                album.setHeight(needHeight);
                if(pictureInfoList.size() > 1){
                	album.setImageSetFlag(1);
                }
                
            	for(PictureInfo pictureInfo : pictureInfoList){
            		String imageUrl = pictureInfo.getImageUrl();
            		if(bigPicWidt!=0)
                    pictureInfo.setImageUrl(imageUrl + appendImageMethod(bigPicWidt,bigheight));
                    pictureInfo.setCurPicHeight(bigheight);
                    pictureInfo.setCurPicWidt(bigPicWidt);
                    
                    if (userId != null) {
                    	TCollection collection = collectionDao.queryCollection(pictureInfo.getPictureId(), userId, 6l);
                        if (collection == null || collection.getStatus() == 0) {
                        	pictureInfo.setCollection(0);
                        } else {
                        	pictureInfo.setCollection(1);
                        }
                    } else {
                    	pictureInfo.setCollection(0);
                    }
                    
            	}
            	album.setPictureInfoList(pictureInfoList);
            }
        }
        
        response.setPictureList(pictureAlbumList);
        //List<Long> pictureAlbumIds = inspirationDao.queryPictureAlbumIds(paramMap);
        //response.setPictureAlbumIds(pictureAlbumIds);
        int totalRecords = inspirationDao.queryPictureAlbumCount(paramMap);
        response.setTotalRecords(totalRecords);
        int totalPages = (int) ((totalRecords + pageSize - 1) / pageSize);
        response.setTotalPages(totalPages);
        return response;
	}
	
	@Override
	public int updateViewCount(HttpInspirationRequest request) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", request.getInspirationId());
        int viewCount = 0;
        if(request.getType()==1){
        	viewCount = inspirationDao.updateAlbumViewCount(param);
        }
        if(request.getType()==2){
        	viewCount = inspirationDao.updateStrategyViewCount(param);
        }
        if(request.getType()==3){
        	viewCount = inspirationDao.updateCaseViewCount(param);
        }
        return viewCount;
	}
	
	@Override
	public int updateTranspondCount(HttpInspirationRequest request) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", request.getInspirationId());
        int viewCount = 0;
        if(request.getType()==1){
        	viewCount = inspirationDao.updateAlbumTranspondCount(param);
        }
        if(request.getType()==2){
        	viewCount = inspirationDao.updateStrategyTranspondCount(param);
        }
        if(request.getType()==3){
        	viewCount = inspirationDao.updateCaseTranspondCount(param);
        }
        return viewCount;
	}
	
	/**
     * 构建树
     * 递归
     *
     * @param rootNode
     * @param classifyNodes
     */
    public void createClassifyTree(TreeNode node, List<ClassifyNode> classifyNodes) {
        List<TreeNode> children = new ArrayList<TreeNode>();
        for (int i = 0, l = classifyNodes.size(); i < l; i++) {
            if (node.getMenuId().equals(classifyNodes.get(i).getParentKey())) {
                TreeNode tempNode = new TreeNode(classifyNodes.get(i));
                createClassifyTree(tempNode, classifyNodes);
                children.add(tempNode);
            }
        }
        node.setChildren(children);
    }
    
    /**
     * 求指定叶节点的父节点
     */
    public void iteratorFatherOfNode(TreeNode manyTreeNode, Long menuId, TreeNode node) {
        if (manyTreeNode != null) {
            for (TreeNode index : manyTreeNode.getChildren()) {
            	if (menuId.equals(index.getMenuId())) {
                    node.setMenuId(manyTreeNode.getMenuId());
                    node.setName(manyTreeNode.getName());
                    node.setChildren(manyTreeNode.getChildren());
                    return;
                }
                if (index.getChildren() != null && index.getChildren().size() > 0) {
                	iteratorFatherOfNode(index, menuId, node);
                }
            }
        }
    }
    
    public void iteratorTree(TreeNode manyTreeNode, Long menuId, TreeNode node) {
        if (manyTreeNode != null) {
            for (TreeNode index : manyTreeNode.getChildren()) {
                if (menuId.equals(index.getMenuId())) {
                    node.setMenuId(index.getMenuId());
                    node.setName(index.getName());
                    node.setChildren(index.getChildren());
                }
                if (index.getChildren() != null && index.getChildren().size() > 0) {
                    iteratorTree(index, menuId, node);
                }
            }
        }
    }
    
    public String appendImageMethod(int mode) {
        String methodUrl = "?imageView2/1/w/*/h/*";
        ImageProperty imageProperty = imageConfig.getImageConfigMap().get(mode);
        methodUrl = methodUrl.replaceFirst("\\*", String.valueOf(imageProperty.getWidth()));
        methodUrl = methodUrl.replaceFirst("\\*", String.valueOf(imageProperty.getHeight()));
        return methodUrl;
    }
    
    public String appendImageMethod(int width,int height) {
        String methodUrl = "?imageView2/1/w/*/h/*";
        methodUrl = methodUrl.replaceFirst("\\*", String.valueOf(width));
        methodUrl = methodUrl.replaceFirst("\\*", String.valueOf(height));
        return methodUrl;
    }

	@Override
	public List<KeyValue> getArticleTypeList270() {
		List<KeyValue> list=new ArrayList<KeyValue>();
		KeyValue keyValue=new KeyValue();
		keyValue.setKey(0);
		keyValue.setValue("晒家");
		keyValue.setSerialNum(0);
		list.add(keyValue);
		List<KeyValue> firstlist=inspirationDao.getArticleTypeList270();
		if(firstlist!=null&&!firstlist.isEmpty()){
			list.addAll(firstlist);
		}

		return list;
	}

	@Override
	public HttpInspirationResponse270 getHome270(HttpInspirationRequest270 request) {
		Integer  key =request.getArticleType();// 文章类型：动态传入
		Integer  pageNo =request.getPageNo();// 当前页数
		if(pageNo==null){
			pageNo=1;
		}
		Integer  pageSize=request.getPageSize();// 每页大小
		if(pageSize==null){
			pageSize=8;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("key", key);	//注意0为全部
        params.put("size", pageSize);
        params.put("from", (pageNo - 1) * pageSize); 
        /**
         * 业务决定 先显示2条数据 ，然后显示3条美图，再瀑布显示剩下所有数据
         * 
         * 前面2条显示数据规则是 ：首先是不是置顶，如果是置顶，再看置顶时间有没有过去三天，如果还满足没有过去三天，则这条数据满足要求。
         * 
         * 如果 不存在置顶数据，则根据时间倒序取两条就可以。
         */
		List<Article270> firstList =new ArrayList<Article270>();
    	//先取是置顶并且 满足没有过去三天 时间倒序的2条数据
		Map<String, Object> firstParamMap = new HashMap<String, Object>();
		firstParamMap.put("key", key);
		firstParamMap.put("size", 2);
		firstParamMap.put("from", 0);
    	List<Article270> oneList =inspirationDao.getDataByTopAndRemain(firstParamMap);
    	int size=2;       	
    	if(oneList!=null&&!oneList.isEmpty()){
    		size =size-oneList.size();
    		firstList.addAll(oneList);

    	}
    	//如果 不存在置顶数据，则根据时间倒序取两条就可以。(取出来也可能是置顶，需要排除)
    	if(size>0){
    		List<Article270> twoList =inspirationDao.getDataByTime(firstParamMap);
    		if(twoList!=null&&!twoList.isEmpty()){
    			for(Article270 article270 :twoList){
    				if(!firstList.contains(article270)){
    					firstList.add(article270);
    				}
    			}
    		} 
    		//最后保证取出来数据只有两条
    		if(firstList.size()>2){
    			firstList=firstList.subList(0, 2);
    		}
    	}  		
    	//图片处理
    	//超过 2.10.0版本 需要处理
		if (VersionUtil.mustUpdate("2.10.0", request.getAppVersion())) {
			if(request.getWidth()!=null){
	    		int width = request.getWidth()* ImageSize.WIDTH_PER_SIZE_95
						/ ImageSize.WIDTH_PER_SIZE_100;
		    	if(firstList.size()>0){
		    		for(Article270 image : firstList){
		    			image.setImageUrl(QiniuImageUtils.compressImageAndDiffPic(image.getImageUrl(),width,-1));
		    		}
		    	}
	    	}else{
	    		if(firstList.size()>0){
		    		for(Article270 image : firstList){
		    			image.setImageUrl(QiniuImageUtils.compressImage_40(image.getImageUrl()));
		    		}
		    	}
	    	}
		}
    	
		List<Picture270> middleList =new ArrayList<Picture270>();
        if(pageNo==1){  	
        	//查询三张美图，不是很确定，可能还需要跟产品经理确认
        	middleList=inspirationDao.getPictureList();    
        }        
        if(middleList==null){
        	middleList=new ArrayList<Picture270>();
        }    
        //查询除去前面两篇文章以外的文章
        params.put("excludeList",firstList);
        List<Article270> lastList =inspirationDao.getDataByCondition(params);
        if(lastList==null){
        	lastList = new  ArrayList<Article270>();
        }else{
        	//超过 2.10.0版本 需要处理
    		if (VersionUtil.mustUpdate("2.10.0", request.getAppVersion())) {
	        	for(Article270 image : lastList){
	    			image.setImageUrl(QiniuImageUtils.compressImage_40(image.getImageUrl()));
	    		}
    		}
        }
        HttpInspirationResponse270 response = new HttpInspirationResponse270();      
        response.setFirstList(firstList);     
        response.setMiddleList(middleList);    
        response.setLastList(lastList);
		return response;		
	}

	@Override
	public HttpArticleResponse270 getArticleDetailByPK270(HttpArticleRequest270   request,Long userId) {
		Long articleId =request.getArticleId();
		Article270 article=inspirationDao.getArticleByPK270(articleId);// 文章详情			
	    if(article==null){
	    	return null;
	    }
	    //图片宽度固定，高度自适应
	    if(request.getWidth()!=null){
	    	int width = request.getWidth() * ImageSize.WIDTH_PER_SIZE_95
					/ ImageSize.WIDTH_PER_SIZE_100;
	    	article.setImageUrl(QiniuImageUtils.compressImageAndSamePic(article.getImageUrl(), width, -1));
	    }
		Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("articleId", articleId);
	    if(userId==null){
	    	article.setPraiseStatus(InspirationConstant.STATUS_PRAISE_NO);
	    }else{
	        paramMap.put("userId", userId);
	        paramMap.put("op", InspirationConstant.ACTION_PRAISE);//操作类型：阅读1,2点赞,3收藏,4转发
			int count = inspirationDao.getPraiseArticleCountByArticleIdAndUserId(paramMap);
			if(count>0){
				article.setPraiseStatus(InspirationConstant.STATUS_PRAISE_YES);
			}else{
				article.setPraiseStatus(InspirationConstant.STATUS_PRAISE_NO);
			}
	    }
		Integer  pageNo =request.getPageNo();// 当前页数
		if(pageNo==null){
			pageNo=1;
		}
		Integer  pageSize=request.getPageSize();// 每页大小
		if(pageSize==null){
			pageSize=8;
		}

        paramMap.put("size", pageSize);
        paramMap.put("from", (pageNo - 1) * pageSize);  
		List<ArticleComment270> commentList =inspirationDao.getArticleCommentListByArticleId270(paramMap);// 文章评论集合	
		int commentTotal =inspirationDao.getArticleCommentTotalByArticleId270(articleId);
		List<Article270> recommendArticleList =inspirationDao.getRecommendArticleListBySourceId(articleId);//推荐文章集合  		
		HttpArticleResponse270 httpArticleResponse270 = new HttpArticleResponse270();
		httpArticleResponse270.setArticle(article);
		httpArticleResponse270.setCommentList(commentList);
		httpArticleResponse270.setCommentTotal(commentTotal);
		httpArticleResponse270.setRecommendArticleList(recommendArticleList);
		//最后需要更新阅读数量
		Integer fromType=request.getFromType();
		if(fromType==null||fromType==0){
	        paramMap.put("op", InspirationConstant.ACTION_READ);//操作类型：阅读1,2点赞,3收藏,4转发
			inspirationDao.updateArticleOpByArticleId(paramMap);
		}
		return httpArticleResponse270;
	}
	
	

	@Override
	public int praiseArticle(Long articleId, Long userId) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("articleId", articleId);
        paramMap.put("userId", userId);
        paramMap.put("op", InspirationConstant.ACTION_PRAISE);//操作类型：阅读1,2点赞,3收藏,4转发
		int count = inspirationDao.getPraiseArticleCountByArticleIdAndUserId(paramMap);
		if(count>0){
			return InspirationConstant.NUM_PRAISED;
		}
	    inspirationDao.updateArticleOpByArticleId(paramMap);	
	    inspirationDao.insertPraiseLog(paramMap);	//插入日志
		return InspirationConstant.NUM_PRAISE_GOOD;
	}

	@Override
	public boolean addComment(HttpCommentRequest270 request, Long userId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("articleId", request.getArticleId());
		paramMap.put("comment", request.getComment());
		paramMap.put("userId", userId);
		int count = inspirationDao.addComment(paramMap);
		if (count == 1) {
			return true;
		}
		return false;

	}

	@Override
	public HttpSearchArticleResponse270 searhArticleList(HttpSearchArticleRequest270 request) {
		Integer  pageNo =request.getPageNo();// 当前页数
		if(pageNo==null){
			pageNo=1;
		}
		Integer  pageSize=request.getPageSize();// 每页大小
		if(pageSize==null){
			pageSize=8;
		}		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String title=request.getTitle();
		paramMap.put("title", request.getTitle());
        paramMap.put("size", pageSize);
        paramMap.put("from", (pageNo - 1) * pageSize); 		
		List<Article270> articleList = inspirationDao.searhArticleList(paramMap);
		int artcletotal =inspirationDao.searhArticleListTotal(title);		
		HttpSearchArticleResponse270 response = new HttpSearchArticleResponse270();
		response.setArticleList(articleList);
		response.setArtcletotal(artcletotal);
		return response;
	}

	@Override
	public HttpCommentResponse270 getCommentListByArticleId(
			HttpArticleRequest270 request) {
		HttpCommentResponse270 response =new HttpCommentResponse270();
		Long articleId =request.getArticleId();
		Integer  pageNo =request.getPageNo();// 当前页数
		if(pageNo==null){
			pageNo=1;
		}
		Integer  pageSize=request.getPageSize();// 每页大小
		if(pageSize==null){
			pageSize=8;
		}
		Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("articleId", articleId);
        paramMap.put("size", pageSize);
        paramMap.put("from", (pageNo - 1) * pageSize);  
		List<ArticleComment270> commentList =inspirationDao.getArticleCommentListByArticleId270(paramMap);// 文章评论集合	
		int commentTotal =inspirationDao.getArticleCommentTotalByArticleId270(articleId);
		response.setCommentList(commentList);
		response.setCommentTotal(commentTotal);
		return response;
	}

	@Override
	public boolean forwardArticle(Long articleId) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("articleId", articleId);
        paramMap.put("op",InspirationConstant.ACTION_FORWARD);//操作类型：阅读1,2点赞,3收藏,4转发
		int result= inspirationDao.updateArticleOpByArticleId(paramMap);
		if(result==1){
			return true;
		}		
		else{
			return false;
		}
	}

	@Override
	public boolean collectArticle(Long articleId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("articleId", articleId);
		paramMap.put("op", InspirationConstant.ACTION_COLLECT);// 操作类型：阅读1,2点赞,3收藏,4转发
		int result = inspirationDao.updateArticleOpByArticleId(paramMap);
		if (result == 1) {
			return true;
		} else {
			return false;
		}
	}

    @Override
    public HttpCaseDetailResponse queryCaseDetailApi(HttpInspirationRequest request) {
        HttpCaseDetailResponse response = new HttpCaseDetailResponse();  
        
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("caseId", request.getInspirationId());
        Case caseInfo = inspirationDao.queryCaseDetail(param);
        List<String> imgList = ImageUtil.removeEmptyStr(caseInfo.getImages());
        List<String> strResponseList = new ArrayList<String>();
        if (null != imgList && imgList.size() > 0) {
            for (String str : imgList) {
                if (null != str && !"".equals(str)) {
                    //str += appendImageMethod(ImageConfig.SIZE_MEDIUM);
                    strResponseList.add(str);
                }
            }
        }
        caseInfo.setImageList(strResponseList);
        caseInfo.setCreateTime(caseInfo.getCreateTime().substring(0, 10));
        caseInfo.setDesc("https://m.ihomefnt.com" + "/caseDetail/" + request.getInspirationId());
        response.setCaseInfo(caseInfo);
        
        return response;
    }

    @Override
    public HttpStrategyDetailResponse queryStrategyDetailApi(HttpInspirationRequest request) {
        HttpStrategyDetailResponse response = new HttpStrategyDetailResponse();  
        
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("strategyId", request.getInspirationId());
        
        Strategy strategy = inspirationDao.queryStrategyDetail(param);
        
        List<String> imgList = ImageUtil.removeEmptyStr(strategy.getImages());
        List<String> strResponseList = new ArrayList<String>();
        if (null != imgList && imgList.size() > 0) {
            for (String str : imgList) {
                if (null != str && !"".equals(str)) {
                    //str += appendImageMethod(ImageConfig.SIZE_MEDIUM);
                    strResponseList.add(str);
                }
            }
        }
        strategy.setImageList(strResponseList);
        strategy.setCreateTime(strategy.getCreateTime().substring(0, 10));
        strategy.setDesc("https://m.ihomefnt.com" + "/strategyDetail/" + request.getInspirationId());
        response.setStrategy(strategy);
        
        return response;
    }

	@Override
	public List<KeyValue> getArticleTypeList290() {
		List<KeyValue> list=new ArrayList<KeyValue>();
		KeyValue keyValue=new KeyValue();
		keyValue.setKey(0);
		keyValue.setValue("新家大晒");
		keyValue.setSerialNum(0);
		list.add(keyValue);
		List<KeyValue> firstlist=inspirationDao.getArticleTypeList270();
		if(firstlist!=null&&!firstlist.isEmpty()){
			list.addAll(firstlist);
		}
		return list;
	}

	@Override
	public HttpInspirationResponse290 getHome290(HttpInspirationRequest270 request) {
		Integer  key =request.getArticleType();// 文章类型：动态传入
		Integer  pageNo =request.getPageNo();// 当前页数
		if(pageNo==null){
			pageNo=1;
		}
		Integer  pageSize=request.getPageSize();// 每页大小
		if(pageSize==null){
			pageSize=8;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("key", key);	//注意0为全部
        params.put("size", pageSize);
        params.put("from", (pageNo - 1) * pageSize); 
        /**
         * 业务决定 先显示2条数据 ，然后显示3条美图，再瀑布显示剩下所有数据
         * 
         * 前面2条显示数据规则是 ：首先是不是置顶，如果是置顶，再看置顶时间有没有过去三天，如果还满足没有过去三天，则这条数据满足要求。
         * 
         * 如果 不存在置顶数据，则根据时间倒序取两条就可以。
         */
		List<Article270> firstList =new ArrayList<Article270>();
    	//先取是置顶并且 满足没有过去三天 时间倒序的5条数据
		Map<String, Object> firstParamMap = new HashMap<String, Object>();
		firstParamMap.put("key", key);
		firstParamMap.put("size", 5);
		firstParamMap.put("from", 0);
    	List<Article270> oneList =inspirationDao.getDataByTopAndRemain(firstParamMap);
    	int size=5;       	
    	if(oneList!=null&&!oneList.isEmpty()){
    		size =size-oneList.size();
    		firstList.addAll(oneList);

    	}
    	//如果 不存在置顶数据，则根据时间倒序取两条就可以。(取出来也可能是置顶，需要排除)
    	if(size>0){
    		List<Article270> twoList =inspirationDao.getDataByTime(firstParamMap);
    		if(twoList!=null&&!twoList.isEmpty()){
    			for(Article270 article270 :twoList){
    				if(!firstList.contains(article270)){
    					firstList.add(article270);
    				}
    			}
    		} 
    		//最后保证取出来数据只有两条
    		if(firstList.size()>5){
    			firstList=firstList.subList(0, 5);
    		}
    	}  		
		List<Picture270> middleList =new ArrayList<Picture270>();
        if(pageNo==1){  	
        	//查询三张美图，不是很确定，可能还需要跟产品经理确认
        	middleList=inspirationDao.getPictureList();    
        }        
        if(middleList==null){
        	middleList=new ArrayList<Picture270>();
        }    
        
        //取出5条新家大晒文章
        List<ShareOrder> shareOrderList = buildShareOrderList(1,5);
        
        //取出新家大晒列表
        List<ShareOrder> lastList = buildShareOrderList(pageNo, pageSize);
        
        HttpInspirationResponse290 result = new HttpInspirationResponse290();      
        result.setFirstList(firstList);     
        result.setMiddleList(middleList);    
        result.setShareOrderList(shareOrderList);
        result.setLastList(lastList);
		return result;		
	}
	
	/**
	 * 构建新家大晒列表
	 * @param page
	 * @param limit
	 * @return
	 */
	private List<ShareOrder> buildShareOrderList(int page,int limit) {
		List<ShareOrder> result = new ArrayList<ShareOrder>();
		HttpShareOrderRequest shareOrderListRequest = new HttpShareOrderRequest();
		shareOrderListRequest.setPage(page);
		shareOrderListRequest.setLimit(limit);
		HttpBasePageResponse response = shareOrderService.getShareOrderList(shareOrderListRequest);
		if(null != response && null != response.getObj()) {
			result = response.getObj();
		}
		return result;
	}
    
}
