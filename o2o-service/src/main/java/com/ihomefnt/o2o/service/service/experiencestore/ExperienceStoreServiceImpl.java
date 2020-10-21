/**
 * 
 */
package com.ihomefnt.o2o.service.service.experiencestore;

import com.ihomefnt.common.util.JsonUtils;
import com.ihomefnt.common.util.RedisUtil;
import com.ihomefnt.o2o.intf.dao.experiencestore.ExperienceStoreDao;
import com.ihomefnt.o2o.intf.domain.experiencestore.dto.ExpStoreHome;
import com.ihomefnt.o2o.intf.domain.experiencestore.vo.response.*;
import com.ihomefnt.o2o.intf.domain.house.dto.House;
import com.ihomefnt.o2o.intf.domain.product.vo.response.HttpHouseSuitProductResponse;
import com.ihomefnt.o2o.intf.domain.product.vo.response.Suit;
import com.ihomefnt.o2o.intf.domain.user.doo.UserVisitLogDo;
import com.ihomefnt.o2o.intf.manager.util.common.bean.StringUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageUtil;
import com.ihomefnt.o2o.intf.service.experiencestore.ExperienceStoreService;
import com.ihomefnt.o2o.intf.service.product.ProductService;
import com.ihomefnt.o2o.intf.service.user.UserService;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author Administrator
 *
 */
@Service
public class ExperienceStoreServiceImpl implements ExperienceStoreService {
    private static final Logger LOG = LoggerFactory.getLogger(ExperienceStoreServiceImpl.class);

    private static final Integer WIDTH_640 = 640;
    private static final Integer HEIGHT_480 = 480;
    private static final Integer HEIGHT_360 = 360;
    private static final Integer HEIGHT_96 = 96;

    @Autowired
    ExperienceStoreDao experienceStoreDao;
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;
    /* (non-Javadoc)
     * @see com.ihomefnt.o2oold.experiencestore.ExperienceStoreService#getExperStores(java.lang.Double, java.lang.Double)
     */
    @Override
    public HttpExperienceStoresResponse getExperStores(Double latitude, Double longitude,String cityCode) {
        HttpExperienceStoresResponse experienceStoresResponse = new HttpExperienceStoresResponse();
        List<HttpExperienceStoreResponse> experienceStores = new ArrayList<HttpExperienceStoreResponse>();
        if (latitude != null && longitude != null) {
            experienceStores = experienceStoreDao.getExperStores(latitude, longitude,cityCode);
        } else {
            experienceStores = experienceStoreDao.getExperStores(cityCode);
        }
        if (experienceStores != null && !experienceStores.isEmpty()) {
            for (int i = 0, l = experienceStores.size(); i < l; i++) {
                List<String> images = ImageUtil.removeEmptyStr(experienceStores.get(i).getImage());
                for (int j = 0, k = images.size(); j < k; j++) {
                    String imgUrl = images.get(j);
                    if (i == 0) {
                        imgUrl += appendImageMethod(WIDTH_640, HEIGHT_480);
                    } else {
                        imgUrl += appendImageMethod(HEIGHT_96, HEIGHT_96);
                    }
                    images.set(j, imgUrl);
                }
                experienceStores.get(i).setImages(images);
                String distance = experienceStores.get(i).getDistance();
                if (distance != null && !distance.isEmpty()) {
                Double distanced = Double.parseDouble(distance);
                if (distanced < 0) {
                    experienceStores.get(i).setDistance("");
                } else if (distanced < 1000) {
                        int n = (int) Math.ceil(distanced / 100);
                        experienceStores.get(i).setDistance(n * 100 + "米");
                } else {
                    experienceStores.get(i).setDistance((int) Math.rint(distanced / 1000) + "公里");
                }
                }
            }
        }
        experienceStoresResponse.setExperienceStores(experienceStores);
        return experienceStoresResponse;
    }

    /* (non-Javadoc)
     * @see com.ihomefnt.o2oold.experiencestore.ExperienceStoreService#getDSDetail(java.lang.Long)
     */
    @Override
    public HttpExperienceStoreDetailResponse getDSDetail(Long dsId) {
        HttpExperienceStoreDetailResponse experienceStoreDetail = new HttpExperienceStoreDetailResponse();
        experienceStoreDetail = experienceStoreDao.getDSDetail(dsId);
        if(experienceStoreDetail!=null&&experienceStoreDetail.getBuildingId()!=null){
            List<HttpHouseSuitProductResponse> houseSuitList = productService
                    .queryHouseSuitFromBuilding(12, experienceStoreDetail.getBuildingId());
            List<String> images = ImageUtil.removeEmptyStr(experienceStoreDetail.getImage());
            for (int j = 0, k = images.size(); j < k; j++) {
                String imgUrl = images.get(j);
                imgUrl += appendImageMethod(WIDTH_640, HEIGHT_360);
                images.set(j, imgUrl);
            }
            experienceStoreDetail.setImages(images);
            experienceStoreDetail.setHouseSuitList(houseSuitList);
        }
        return experienceStoreDetail;
    }

    /* (non-Javadoc)
     * @see com.ihomefnt.o2oold.experiencestore.ExperienceStoreService#getExperStores()
     */
    @Override
    public HttpExperienceStoresResponse getExperStores(String cityCode) {
        HttpExperienceStoresResponse experienceStoresResponse = new HttpExperienceStoresResponse();
        List<HttpExperienceStoreResponse> experienceStores = new ArrayList<HttpExperienceStoreResponse>();
        experienceStores = experienceStoreDao.getExperStores(cityCode);
        if (experienceStores != null && !experienceStores.isEmpty()) {
            for (int i = 0, l = experienceStores.size(); i < l; i++) {
                List<String> images = ImageUtil.removeEmptyStr(experienceStores.get(i).getImage());
                for (int j = 0, k = images.size(); j < k; j++) {
                    String imgUrl = images.get(j);
                    if (i == 0) {
                        imgUrl += appendImageMethod(WIDTH_640, HEIGHT_480);
                    } else {
                        imgUrl += appendImageMethod(HEIGHT_96, HEIGHT_96);
                    }
                    images.set(j, imgUrl);
                }
                experienceStores.get(i).setImages(images);

            }
        }
        experienceStoresResponse.setExperienceStores(experienceStores);
        return experienceStoresResponse;
    }

    public String appendImageMethod(Integer width, Integer height) {
        String methodUrl = "?imageView2/1/w/*/h/*";

        methodUrl = methodUrl.replaceFirst("\\*", String.valueOf(width));
        methodUrl = methodUrl.replaceFirst("\\*", String.valueOf(height));
        return methodUrl;
    }

	@Override
	public List<HttpExperienceStoreDetailResponse> getDSDetailById(Long esId) {
		return experienceStoreDao.getDSDetailById(esId);
	}

	@Override
	public HttpExperienceStoresResponse getExperStores200(
			HttpExperienceStoreResquest request) {
		
        List<HttpExperienceStoreResponse> experienceStores = null;
        
        Map<String, Object> map = new HashMap<String, Object>();
        if(null != request.getCityCode() && !"".equals(request.getCityCode())){
        	map.put("cityCode", request.getCityCode());
        } else {
        	map.put("cityCode", "210000");
        }
        int pageSize = request.getPageSize() > 0 ? request.getPageSize() : 10;
        int pageNo = request.getPageNo() > 0 ? request.getPageNo() : 1;
        map.put("size", pageSize);
        map.put("from", (pageNo - 1) * pageSize);
        if (request.getLatitude() != null && request.getLongitude() != null) {
        	map.put("latitude", request.getLatitude());
            map.put("longitude", request.getLongitude());
            experienceStores = experienceStoreDao.getExperStoresFromPlace(map);
        } else {
            experienceStores = experienceStoreDao.getExperStores(map);
        }
        List<HttpExperienceStoreResponse> mostSuitList = experienceStoreDao.getMostSuitList(map);
        List<HttpExperienceStoreResponse> mostSaleList = experienceStoreDao.getMostSaleList(map);
        
        if (experienceStores != null) {
            for (int i = 0; i < experienceStores.size(); i++) {
                List<String> images = ImageUtil.removeEmptyStr(experienceStores.get(i).getImage());
                for (int j = 0; j < images.size(); j++) {
                    String imgUrl = images.get(j);
                    imgUrl += appendImageMethod(236, 236);
                    images.set(j, imgUrl);
                }
                experienceStores.get(i).setImages(images);
                map.put("esId", experienceStores.get(i).getEsId());
                experienceStores.get(i).setHouseCount(experienceStoreDao.getHouseCount(map));
                if(experienceStores.get(i).getEsId().equals(mostSaleList.get(0).getEsId())){
                	experienceStores.get(i).setExpLabel("人气最旺");
                	experienceStores.get(i).setSuitSale(mostSuitList.get(0).getSuitSale());
                }
                if(experienceStores.get(i).getEsId().equals(mostSuitList.get(0).getEsId())){
                	experienceStores.get(i).setExpLabel("套装最多");
                	experienceStores.get(i).setSuitCount(mostSuitList.get(0).getSuitCount());
                }
                
                String distance = experienceStores.get(i).getDistance();
                if (distance != null && !distance.isEmpty()) {
                    Double distanced = Double.parseDouble(distance);
                    experienceStores.get(i).setDist(distanced.intValue());
                    if (distanced < 0) {
                        experienceStores.get(i).setDistance("");
                    } else if (distanced < 1000) {
                        int n = (int) Math.ceil(distanced / 100);
                        experienceStores.get(i).setDistance(n * 100 + "米");
                    } else {
                    	DecimalFormat df = new DecimalFormat("#.0");  
                        experienceStores.get(i).setDistance(df.format(distanced / 1000) + "公里");
                    }
                    if (i == 0) {
                    	experienceStores.get(i).setExpLabel("距离最近");
                    }
                }
            }
            
            setExpStoreAttribute200(experienceStores);
            sortExpStore(experienceStores);
        }
        HttpExperienceStoresResponse response = new HttpExperienceStoresResponse();
        int totalRecords = experienceStoreDao.getExperStoresCount(map);
        response.setExperienceStores(experienceStores);
        response.setTotalRecords(totalRecords);
        int totalPages = (int) ((totalRecords + pageSize - 1) / pageSize);
        response.setTotalPages(totalPages);
        return response;
	}
	
	/**
	 * 临时修改排序.
	 * @param experienceStores
	 */
	private void setExpStoreAttribute200(List<HttpExperienceStoreResponse> experienceStores) {
	    for (HttpExperienceStoreResponse httpExperienceStoreResponse : experienceStores) {
	        int houseCountWeight = httpExperienceStoreResponse.getHouseCount() * 100000000;
	        double expWeight = houseCountWeight;
	        Integer distance = httpExperienceStoreResponse.getDist();
	        if (distance != null) {
                expWeight = expWeight - distance; 
	        }
	        httpExperienceStoreResponse.setExpWeight(expWeight);
	    }
	}
	
	
	
	@Override
    public HttpExperienceStoreDetailResponse getDSDetail200(Long dsId) {
        HttpExperienceStoreDetailResponse experienceStoreDetail = experienceStoreDao.getDSDetail200(dsId);
        if(experienceStoreDetail != null){
        	if(null == experienceStoreDetail.getIsOfflineExp()||experienceStoreDetail.getIsOfflineExp() == 0){
        		Double latitude = experienceStoreDetail.getLatitude();
            	Double longitude = experienceStoreDetail.getLongitude();
            	HttpExperienceStoreDetailResponse queryNearestAddress = experienceStoreDao.queryNearestAddress(latitude, longitude);
            	Integer distance = Math.round(Integer.parseInt(queryNearestAddress.getExpDistance())/100f)/10;
            	experienceStoreDetail.setExpDistance(distance.toString()+"公里");
            	experienceStoreDetail.setExpAddress(queryNearestAddress.getExpAddress());
            	experienceStoreDetail.setExpName(queryNearestAddress.getExpName());
            	experienceStoreDetail.setLatitude(queryNearestAddress.getLatitude());
            	experienceStoreDetail.setLongitude(queryNearestAddress.getLongitude());
            	experienceStoreDetail.setHasExpShop(false);
        	}else if(experienceStoreDetail.getIsOfflineExp() == 1){
        		experienceStoreDetail.setHasExpShop(true);
        	}
        	
        	List<ActivityLabel> queryExperienceStoreActivity = experienceStoreDao.queryExperienceStoreActivity(dsId);
        	experienceStoreDetail.setActivityLabelList(queryExperienceStoreActivity);
        	
            List<String> images = ImageUtil.removeEmptyStr(experienceStoreDetail.getImage());
            for (int j = 0; j < images.size(); j++) {
                String imgUrl = images.get(j);
                imgUrl += appendImageMethod(WIDTH_640, HEIGHT_360);
                images.set(j, imgUrl);
            }
            experienceStoreDetail.setImages(images);
            if(images.size()>0){
            	experienceStoreDetail.setHeadImg(images.get(0));
            }
            List<House> houseList = experienceStoreDao.queryHouseByBuildingId(experienceStoreDetail.getBuildingId());
            int modelCount = 0;
            int caseCount = 0;
            int houseTypeCount = 0;
            if(null != houseList){
            	Iterator<House> iterator = houseList.iterator();
            	while(iterator.hasNext()){
            		House house = iterator.next();
            		if(!house.getName().contains("户型")){
        				house.setName(house.getName()+"户型");
        			}
            		List<Suit> suitList = experienceStoreDao.querySuitByHouseId(house.getIdtHouse());
            		if(null != suitList && suitList.size()>0){
            			caseCount += suitList.size();
            			for(Suit suit : suitList){
            				List<String> suitImages = ImageUtil.removeEmptyStr(suit.getImagesUrl());
            	            if(suitImages.size() > 0){
            	            	String suitImg = suitImages.get(0)+appendImageMethod(236, 236);
            	            	suit.setSuitImg(suitImg);
            	            }
            				List<String> suitLabel = new ArrayList<String>();
            	            if(StringUtils.isNotBlank(suit.getUrl3d())){
            	            	suit.setHas3d(true);
            	            	suitLabel.add("3D");
            	            }else{
            	            	suit.setHas3d(false);
            	            }
            	            if(suit.getOffLine()==1){
            	            	suit.setHasModel(true);
            	            	modelCount++;
            	            	suitLabel.add("样板间");
            	            }else{
            	            	suit.setHasModel(false);
            	            }
            	            suit.setSuitLabel(suitLabel);
            			}
            			house.setSuitList(suitList);
            			house.setSuitCount(suitList.size());
            		}else if(null == house.getHouseImg() || house.getHouseImg().equals("")){
            			iterator.remove();
            		}
//            		List<String> imageList = ImageUtil.removeEmptyStr(house.getImages());
//            		if(imageList.size() > 0){
//            			house.setHouseImg(imageList.get(0));
//            		}
            	}
            	houseTypeCount = houseList.size();
            	experienceStoreDetail.setHouseCount(houseTypeCount);
            	experienceStoreDetail.setModelCount(modelCount);
            	experienceStoreDetail.setCaseCount(caseCount);
            	
            	Comparator<House> comparator = new Comparator<House>() {
					@Override
					public int compare(House o1, House o2) {
						int o1Count = 0;
						int o2Count = 0;
						if(!StringUtil.isNullOrEmpty(o1.getHouseImg())&&null!=o1.getSuitList()){
							o1Count += 40;
						}else if(StringUtil.isNullOrEmpty(o1.getHouseImg())&& null!= o1.getSuitList()){
							o1Count += 20;
						}else{
							o1Count += 10;
						}
						
						if(!StringUtil.isNullOrEmpty(o2.getHouseImg())&&null!=o2.getSuitList()){
							o2Count += 40;
						}else if(StringUtil.isNullOrEmpty(o2.getHouseImg())&& null!= o2.getSuitList()){
							o2Count += 20;
						}else{
							o2Count += 10;
						}
						
						if(o1Count>o2Count){
							return -1;
						}else if(o1Count==o2Count){
							return 0;
						}else {
							return 1;
						}
					}
				};
            	Collections.sort(houseList, comparator);
            	experienceStoreDetail.setHouseList(houseList);
            }
            experienceStoreDetail.setHouseList(houseList);
        }
        return experienceStoreDetail;
    }

	
	
    @Override
    public HttpExperienceStoresResponse getExperStores260(HttpExperienceStoreResquest request) {
        HttpExperienceStoresResponse response = new HttpExperienceStoresResponse();
        Map<String, Object> map = new HashMap<String, Object>();
        String cityCode = request.getCityCode();
        if(StringUtils.isNotBlank(cityCode)){
            map.put("cityCode", cityCode);
        } else {
            map.put("cityCode", "210000");
        }
        
        int pageSize = request.getPageSize();
        int pageNo = request.getPageNo();
        /**
         * 临时修改方案，由于ios不能翻页，14数据，只能显示10条丢失4条数据；故约定一页显示20条
         * 不要问我，为啥又是后台修改
         */
//        if(pageSize<20){
//        	pageSize=20;
//        }
        pageSize = pageSize > 0 ? pageSize : 10;
        pageNo = pageNo > 0 ? pageNo : 1;
        map.put("size", pageSize);
        map.put("from", (pageNo - 1) * pageSize);
        
        String searchItem = request.getSearchItem();
        if (StringUtils.isNotBlank(searchItem)) {
            map.put("searchItem", searchItem);
            
            UserVisitLogDo userVisitLog = new UserVisitLogDo(request.getDeviceToken(), request.getMobileNum(), 4, "体验店搜索", searchItem); //记录用户行为日志
            userService.saveUserVisitLog(userVisitLog);
        }
        
        List<HttpExperienceStoreResponse> expStoreSummaryList = null;
        Double latitude = request.getLatitude();
        Double longitude = request.getLongitude();
        if (latitude != null && longitude != null) {
            map.put("latitude", latitude);
            map.put("longitude", longitude);
            expStoreSummaryList = experienceStoreDao.getExperStoresFromPlace260(map);
        } else {
            expStoreSummaryList = experienceStoreDao.getExperStores260(map);
        }
        
        if (null != expStoreSummaryList) {
            setExpStoreAttribute(expStoreSummaryList);
            sortExpStore(expStoreSummaryList);
            
            
            int totalRecords = expStoreSummaryList.size();
            int mod = totalRecords % pageSize;
            int totalPages = mod == 0 ? totalRecords / pageSize : totalRecords / pageSize + 1;
            response.setTotalRecords(totalRecords);
            response.setTotalPages(totalPages);
            
            if (totalRecords > pageSize) {
                List<HttpExperienceStoreResponse> sList = new ArrayList<HttpExperienceStoreResponse>(pageSize);
                int start = 0;
                if (pageNo != 0) {
                    start = (pageNo - 1) * pageSize;
                }
                
                int end = pageSize;
                int diffNum = totalRecords - start;
                if (end > diffNum) {
                    end = diffNum + start;
                } else {
                    end = start + end;
                }
                for (int i=start; i<end; i++) {
                    HttpExperienceStoreResponse experStore = new HttpExperienceStoreResponse();
                    try {
                        PropertyUtils.copyProperties(experStore, expStoreSummaryList.get(i));
                    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        LOG.error("getExperStores260 Exception ", e);
                    } 
                    sList.add(experStore);
                }
               
                expStoreSummaryList = sList;
            }
            
            response.setExperienceStores(expStoreSummaryList);
            
            return response;
        }
        
        return null;
    }
    
    /**
     * 计算设置体验店权重值及体验店其它属性.
     * @param expStoreSummaryList
     */
    private void setExpStoreAttribute(List<HttpExperienceStoreResponse> expStoreSummaryList) {
        List<HouseSummary> houseSummaryList = null;
        for (HttpExperienceStoreResponse expSummary : expStoreSummaryList) {
            setExperStoreActivity(expSummary);
            setExperStoreImage(expSummary);
            
            int real = 0;    //实景套装
            int virtual = 0; //虚拟套装
            double panorama3dUrlVal = 0;    //3d虚拟地址
            boolean offline = false;
            int distance = expSummary.getDist();
            int negativeDistance = Integer.parseInt("-" + distance);    //距离权重 距离取负  相加
            double expWeight = negativeDistance;
            houseSummaryList = expSummary.getHouseSummaryList();
            if (null != houseSummaryList) {
                expSummary.setHouseTypeCount(houseSummaryList.size());  //户型数量
                
                List<SuitSummary> suitSummaryList = null;
                for (HouseSummary houseSummay : houseSummaryList) {
                    suitSummaryList = houseSummay.getSuitSummaryList();
                    if (null != suitSummaryList) {
                        for (SuitSummary suitSummary : suitSummaryList) {
                            int offlineExperience = suitSummary.getOfflineExperience();
                            if (offlineExperience == 1) {
                                offline = true;
                                real ++;
                            } else {
                                virtual ++;
                            }
                            
                            String panorama3dUrl = suitSummary.getPanorama3dUrl();
                            if (StringUtils.isNotBlank(panorama3dUrl)) {
                                panorama3dUrlVal ++;
                            }
                        }
                        
                        panorama3dUrlVal = panorama3dUrlVal * 0.00001;
                        
                        expWeight += real * 0.1;
                        expWeight += virtual * 0.001;
                        expWeight += panorama3dUrlVal;
                    }
                }
            }
            
            expSummary.setHouseCount(real); //样板间数量
            expSummary.setCaseCount(real + virtual);   //方案数量
            
            if (offline) {
                expWeight += 100000000; //如果存在线下体验店  加  100000000
            }
            
            expSummary.setExpWeight(expWeight);
            
            if (real > 0) {
                expSummary.setHasExpShop(true);
            }
            
            setDistance(expSummary, distance);
            
            expSummary.setHouseSummaryList(null);
        }
        
    }
    
    
    /**
     * 设置体验店活动信息.
     * @param expSummary
     */
    private void setExperStoreActivity(HttpExperienceStoreResponse expSummary) {
        Long esId = expSummary.getEsId();
        List<ActivityLabel> activityLabelList = experienceStoreDao.getExperStoresActivity();
        List<ActivityLabel> experStoreActivityList = new ArrayList<ActivityLabel>(2);
        for (ActivityLabel al : activityLabelList) {
            Long experStoreId = al.getExperStoreId();
            if (Long.compare(esId, experStoreId) == 0) {
                al.setH5Url("");    //列表不需要活动URL
                experStoreActivityList.add(al);
            }
        }
        expSummary.setActivityLabelList(experStoreActivityList);
    }
    
    /**
     * 设置体验店头图.
     * @param expSummary
     */
    private void setExperStoreImage(HttpExperienceStoreResponse expSummary) {
        List<String> images = ImageUtil.removeEmptyStr(expSummary.getImage());
        if (null != images) {
            String imgUrl = images.get(0) + appendImageMethod(236, 236);
            expSummary.setExpStoreImage(imgUrl);    //头图
        }
    }
    
    /**
     * 距离转换 加上单位
     * @param expSummary
     * @param distance
     */
    private void setDistance(HttpExperienceStoreResponse expSummary, Integer distance) {
        if (distance < 0) {
            expSummary.setDistance("0");
        } else if (distance < 1000) {
            int n = (int) Math.ceil((double) distance / 100);
            expSummary.setDistance(n * 100 + "米");
        } else {
            DecimalFormat df = new DecimalFormat("#.0");  
            expSummary.setDistance(df.format(distance / 1000) + "公里");
        }
    }
    
    /**
     * 体验店排序
     * @param expStoreSummaryList
     */
    private void sortExpStore(List<HttpExperienceStoreResponse> expStoreSummaryList) {
        Collections.sort(expStoreSummaryList, new Comparator<HttpExperienceStoreResponse>() {  
            public int compare(HttpExperienceStoreResponse ess1, HttpExperienceStoreResponse ess2) {  
                double weight1 = ess1.getExpWeight();
                double weight2 = ess2.getExpWeight();

                int value = BigDecimal.valueOf(weight1).compareTo(BigDecimal.valueOf(weight2));
                if (value < 0) {
                    return 1;  
                } else if (value == 0) {
                    return 0;  
                } else {  
                    return -1;  
                }  
            }  
        });  
    }

    private final static String EXP_STORE_HOME = "expStore:expStoreHome_";
    private final static String EXP_STORE = "expStore:expStore_";

	/**
	 * 获取体验店首页缓存
	 * @param areaId
	 * @return
	 * @author Ivan
	 * @date 2016年5月10日 下午5:34:39
	 */
	@Override
	public ExpStoreHome getHomeCache(Long areaId){
		String obj = RedisUtil.get(this.EXP_STORE_HOME+areaId);
		if(obj!=null){
			return JsonUtils.json2obj(obj, ExpStoreHome.class);
		}
		return null;
	}
    
}
