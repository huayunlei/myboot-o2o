package com.ihomefnt.o2o.service.service.designer;

import com.ihomefnt.o2o.service.manager.config.ImageConfig;
import com.ihomefnt.o2o.intf.service.designer.DesignerService;
import com.ihomefnt.o2o.intf.dao.designer.DesignerDao;
import com.ihomefnt.o2o.intf.domain.designer.dto.DesignerModel;
import com.ihomefnt.o2o.intf.domain.designer.dto.DesignerPic;
import com.ihomefnt.o2o.intf.domain.designer.vo.request.DesignerPicBrowseRequest;
import com.ihomefnt.o2o.intf.domain.designer.vo.request.DesignerPicRequest;
import com.ihomefnt.o2o.intf.domain.designer.dto.DesignerPicture;
import com.ihomefnt.o2o.intf.domain.designer.dto.DesignerSuit;

import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DesignerServiceImpl implements DesignerService {

    private static final String WIDTH_320 = "320";
    private static final String WIDTH_360 = "360";
    private static final String WIDTH_640 = "640";
    private static final String WIDTH_1080 = "1080";
    @Autowired
    DesignerDao designerDao;
    @Autowired
    ImageConfig imageConfig;

    @Override
    public List<DesignerPic> queryDesignerPic(Map<String, Object> paramMap, Integer screenWidth) {
        List<DesignerPic> designerPics = designerDao.queryDesignerPic(paramMap);
        if (null != designerPics && designerPics.size() > 0) {
            for (int i = 0; i < designerPics.size(); i++) {
                String url = designerPics.get(i).getUrl();
                Integer width = designerPics.get(i).getWidth();
                if (screenWidth != null && screenWidth > 640) {
                    if (width == null || width > 360) {
                        url += appendImageToZoomOut(WIDTH_360);
                    } else {
                        url += appendImageToZoomIn(WIDTH_360);
                    }
                } else {
                    if (width == null || width > 320) {
                        url += appendImageToZoomOut(WIDTH_320);
                    } else {
                        url += appendImageToZoomIn(WIDTH_320);
                    }
                }
                designerPics.get(i).setUrl(url);
            }
        }
        return designerPics;
    }

    public String appendImageMethod(String width) {
        String methodUrl = "?imageView2/2/w/*";
        methodUrl = methodUrl.replaceFirst("\\*", width);
        return methodUrl;
    }

    /**
     * 缩
     *
     * @param width
     * @return
     */
    public String appendImageToZoomOut(String width) {
        String methodUrl = "?imageView2/1/w/*";

        methodUrl = methodUrl.replaceFirst("\\*", width);
        return methodUrl;
    }

    /**
     * 放
     *
     * @param width
     * @return
     */
    public String appendImageToZoomIn(String width) {
        String methodUrl = "?imageMogr2/thumbnail/*x*!";

        methodUrl = methodUrl.replaceAll("\\*", width);
        return methodUrl;
    }

    @Override
    public List<DesignerPic> queryDesignerPic(DesignerPicBrowseRequest designerPicRes, Integer screenWidth) {
        List<DesignerPic> designerPics = new ArrayList<DesignerPic>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("designerId", designerPicRes.getDesignerId());
        map.put("id", designerPicRes.getIdtDesignerPic());
        if (designerPicRes.getShiftLeft() == true) {
            designerPics = designerDao.queryDesignerPicShiftLeft(map);
        } else {
            designerPics = designerDao.queryDesignerPicShiftRight(map);
        }
        for (int i = 0, l = designerPics.size(); i < l; i++) {//cut
            String url = designerPics.get(i).getUrl();
            if (screenWidth <= 640) {
                url += appendImageMethod(WIDTH_640);
            } else {
                url += appendImageMethod(WIDTH_1080);
            }
            designerPics.get(i).setUrl(url);
            ;
        }
        return designerPics;
    }

    @Override
    public List<DesignerPic> queryDesignerPicsById(DesignerPicRequest designerPicRequest, Integer screenWidth) {
        List<DesignerPic> designerPics = new ArrayList<DesignerPic>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("designerId", designerPicRequest.getDesignerId());
        map.put("id", designerPicRequest.getIdtDesignerPic());
        List<DesignerPic> pics = designerDao.queryDesignerPicShiftRight(map);
        if (pics != null && pics.size() > 0) {
            pics.get(0).setMark(1);
            designerPics.add(pics.get(0));
        }
        DesignerPic pic = designerDao.queryDesignerPicById(designerPicRequest.getIdtDesignerPic());
        pic.setMark(0);
        designerPics.add(pic);
        pics = designerDao.queryDesignerPicShiftLeft(map);
        if (pics != null && pics.size() > 0) {
            pics.get(0).setMark(-1);
            designerPics.add(pics.get(0));
        }
//		designerPics.add(designerDao.queryDesignerPicById(designerPicRequest.getIdtDesignerPic()));
        if (null != designerPics && designerPics.size() > 0) {
            for (int i = 0; i < designerPics.size(); i++) {
                String url = designerPics.get(i).getUrl();
                if (screenWidth <= 640) {
                    url += appendImageMethod(WIDTH_640);
                } else {
                    url += appendImageMethod(WIDTH_1080);
                }

                designerPics.get(i).setUrl(url);
                ;
            }
        }
        return designerPics;
    }

    @Override
    public DesignerModel loadDesignerInfo(String mobile) {
        return designerDao.loadDesignerInfo(mobile);
    }

    @Override
    public Integer loadDesignerSuitCount(Long designerId) {
        return designerDao.loadDesignerSuitCount(designerId);
    }

    @Override
    public Integer loadDesignerPicCount(Long designerId) {
        return designerDao.loadDesignerPicCount(designerId);
    }

    @Override
    public String loadDesignerSuitImg(Long designerId) {
        String latestImage = "";
        String jsonImage = designerDao.loadDesignerSuitImg(designerId);
        try {
            JSONArray array = JSONArray.fromObject(jsonImage);
            latestImage = array.getString(0);
        } catch (Exception e) {
        }
        return latestImage;
    }

    @Override
    public List<DesignerSuit> loadDesignerSuit(Long designerId, Integer pageNo, Integer pageSize) {
        Integer total = designerDao.loadDesignerSuitCount(designerId);
        if (total == 0) {
            return null;
        }
        Integer pageCount = (total / pageSize) + (total % pageSize == 0 ? 0 : 1);
        if (pageNo > pageCount) {
            pageNo = pageCount;
        }
        return designerDao.loadDesignerSuitList(designerId, (pageNo - 1) * pageSize, pageSize);
    }

    @Override
    public List<DesignerPicture> loadDesignerPicList(Long designerId, Integer pageNo, Integer pageSize) {
        Integer total = designerDao.loadDesignerPicCount(designerId);
        if (total == 0) {
            return null;
        }
        Integer pageCount = (total / pageSize) + (total % pageSize == 0 ? 0 : 1);
        if (pageNo > pageCount) {
            pageNo = pageCount;
        }
        return designerDao.loadDesignerPicList(designerId, (pageNo - 1) * pageSize, pageSize);
    }

    @Override
    public void resizeDesignerPicture(List<DesignerPicture> pictureList, Integer screenWidth) {
        if (pictureList == null || pictureList.isEmpty()
                || screenWidth == null || screenWidth.compareTo(0) <= 0) {
            return;
        }

        for (DesignerPicture picture : pictureList) {
            String url = picture.getUrl();
            Integer width = picture.getWidth();
            if (screenWidth != null && screenWidth > 640) {
                if (width == null || width > 360) {
                    url += appendImageToZoomOut(WIDTH_360);
                } else {
                    url += appendImageToZoomIn(WIDTH_360);
                }
            } else {
                if (width == null || width > 320) {
                    url += appendImageToZoomOut(WIDTH_320);
                } else {
                    url += appendImageToZoomIn(WIDTH_320);
                }
            }
            picture.setUrl(url);
        }
    }
}
