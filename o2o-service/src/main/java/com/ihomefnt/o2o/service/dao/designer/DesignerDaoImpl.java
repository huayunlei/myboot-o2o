package com.ihomefnt.o2o.service.dao.designer;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ihomefnt.o2o.intf.dao.designer.DesignerDao;
import com.ihomefnt.o2o.intf.domain.designer.dto.DesignerModel;
import com.ihomefnt.o2o.intf.domain.designer.dto.DesignerPic;
import com.ihomefnt.o2o.intf.domain.designer.dto.DesignerPicture;
import com.ihomefnt.o2o.intf.domain.designer.dto.DesignerSuit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DesignerDaoImpl implements DesignerDao {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    private static final String NAME_SPACE = "com.ihomefnt.o2o.intf.dao.designer.DesignerDao.";

    @Override
    public List<DesignerPic> queryDesignerPic(Map<String, Object> paramMap) {
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryDesignerPic", paramMap);
    }

    @Override
    public DesignerPic queryDesignerPicById(Long id) {
        return sqlSessionTemplate.selectOne(NAME_SPACE + "queryDesignerPicById", id);
    }

    @Override
    public List<DesignerPic> queryDesignerPicShiftLeft(Map<String, Object> map) {
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryDesignerPicShiftLeft", map);
    }

    @Override
    public List<DesignerPic> queryDesignerPicShiftRight(Map<String, Object> map) {
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryDesignerPicShiftRight", map);
    }

    @Override
    public DesignerModel loadDesignerInfo(String mobile) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("mobile", mobile);
        return sqlSessionTemplate.selectOne(NAME_SPACE + "queryDesignerInfo", paramsMap);
    }

    @Override
    public Integer loadDesignerSuitCount(Long designerId) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("fid_designer", designerId);
        return sqlSessionTemplate.selectOne(NAME_SPACE + "loadDesignerSuitCount", paramsMap);
    }

    @Override
    public Integer loadDesignerPicCount(Long designerId) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("fid_designer", designerId);
        return sqlSessionTemplate.selectOne(NAME_SPACE + "loadDesignerPicCount", paramsMap);
    }

    @Override
    public String loadDesignerSuitImg(Long designerId) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("fid_designer", designerId);
        return sqlSessionTemplate.selectOne(NAME_SPACE + "loadDesignerSuitImg", paramsMap);
    }

    @Override
    public List<DesignerPicture> loadDesignerPicList(Long designerId, Integer from, Integer pageSize) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("designerId", designerId);
        paramsMap.put("from", from);
        paramsMap.put("size", pageSize);

        return sqlSessionTemplate.selectList(NAME_SPACE + "loadDesignerPicList", paramsMap);
    }

    @Override
    public List<DesignerSuit> loadDesignerSuitList(Long designerId, Integer from, Integer pageSize) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("designerId", designerId);
        paramsMap.put("from", from);
        paramsMap.put("size", pageSize);

        return sqlSessionTemplate.selectList(NAME_SPACE + "loadDesignerSuitList", paramsMap);
    }

    @Override
    public DesignerModel loadDesignerInfoById(Long designerId) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("designerId", designerId);
        return sqlSessionTemplate.selectOne(NAME_SPACE + "loadDesignerInfoById", paramsMap);
    }
}
