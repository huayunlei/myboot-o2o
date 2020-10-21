package com.ihomefnt.o2o.service.dao.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ihomefnt.o2o.intf.dao.product.ProductTypeDao;
import com.ihomefnt.o2o.intf.domain.product.doo.ClassifyNode;
import com.ihomefnt.o2o.intf.manager.util.common.bean.TreeNode;

/**
 * Created by hvk687 on 15/3/18.
 */
@Repository
public class ProductTypeDaoImpl implements ProductTypeDao {
    @Autowired
    SqlSessionTemplate sqlSessionTemplate;
    public static final String NAME_SPACE = "com.ihomefnt.o2o.intf.dao.product.ProductTypeDao.";
    @Override
    public List<TreeNode> getProductNodeList() {
        return sqlSessionTemplate.selectList(NAME_SPACE + "query");
    }
    @Override
    public List<ClassifyNode> queryClassifyNodes() {
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryClassifyNodes");
    }
    
    @Override
    public List<ClassifyNode> queryBuildingClassifyNodes(Map<String, Object> param) {
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryBuildingClassifyNodes", param);
    }
    
    @Override
    public List<ClassifyNode> queryStyleClassifyNodes(Map<String, Object> param) {
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryStyleClassifyNodes", param);
    }
    
    @Override
    public List<ClassifyNode> querySizeClassifyNodes(Map<String, Object> param) {
        return sqlSessionTemplate.selectList(NAME_SPACE + "querySizeClassifyNodes", param);
    }
    

    @Override
    public ClassifyNode queryClassifyNodeById(Long menuId) {
        return sqlSessionTemplate.selectOne(NAME_SPACE + "queryClassifyNodeById", menuId);
    }
    
    @Override
    public List<ClassifyNode> queryClassifyNodeByIds(String menuIds) {
    	Map<String,Object> paramMap = new HashMap<String,Object>();
    	paramMap.put("menuIds", menuIds);
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryClassifyNodeByIds", paramMap);
    }

    @Override
    public List<ClassifyNode> queryClassifyNodes(Long parentKey) {
        Map<String,Object> paramMap = new HashMap<String,Object>();
        if(null != parentKey){
            paramMap.put("parentKey", parentKey);
        }
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryClassifyNodesByCondit",paramMap);
    }

    @Override
    public List<Long> queryProductNodeByClassifyName(String name) {
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryProductNodeByClassifyName", name);
    }
    
    @Override
    public List<String> queryClassifyNameByProductNode(Long nodeId) {
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryClassifyNameByProductNode", nodeId);
    }
    
    @Override
    public List<String> queryCondition(String menuKey) {
        Map<String,Object> paramMap = new HashMap<String,Object>();
        if(null != menuKey && !"".equals(menuKey)){
            paramMap.put("menuKey", menuKey);
        }
        return sqlSessionTemplate.selectList(NAME_SPACE + "queryCondition", paramMap);
    }
}
