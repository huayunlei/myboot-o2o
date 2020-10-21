package com.ihomefnt.o2o.intf.dao.product;

import java.util.List;
import java.util.Map;

import com.ihomefnt.o2o.intf.domain.product.doo.ClassifyNode;
import com.ihomefnt.o2o.intf.manager.util.common.bean.TreeNode;

/**
 * Created by hvk687 on 15/3/18.
 */
public interface ProductTypeDao {
    public List<TreeNode> getProductNodeList();

    public List<ClassifyNode> queryClassifyNodes();
    
    public ClassifyNode queryClassifyNodeById(Long menuId);
    
    public List<ClassifyNode> queryClassifyNodeByIds(String menuIds);

    public List<ClassifyNode> queryClassifyNodes(Long parentKey);

    public List<Long> queryProductNodeByClassifyName(String name);
    
    public List<String> queryClassifyNameByProductNode(Long nodeId);
    
    public List<String> queryCondition(String menuKey);
    
    public List<ClassifyNode> queryBuildingClassifyNodes(Map<String, Object> param);
    
    public List<ClassifyNode> queryStyleClassifyNodes(Map<String, Object> param);
    
    public List<ClassifyNode> querySizeClassifyNodes(Map<String, Object> param);
}
