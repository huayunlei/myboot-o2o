package com.ihomefnt.o2o.intf.manager.util.common.bean;

/**
 * 树节点
 *
 */
public class TreeNode
{
    public static final String ATTR_NAME = "name";
    public static final String ATTR_ID = "id";

    /** 节点名称*/
    private String nodeName;
    /** 父节点Key*/
    private Integer parentKey;
    /**节点Key*/
    private Integer key;//唯一标识

    public TreeNode(){
        
    }
    public TreeNode(String nodeName)
    {
        this.nodeName = nodeName;
    }

    public TreeNode(String nodeName, Integer parentKey)
    {
        this.nodeName = nodeName;
        this.parentKey = parentKey;
    }

    public TreeNode(String nodeName, Integer parentKey,Integer key)
    {
        this.nodeName = nodeName;
        this.parentKey = parentKey;
        this.key = key;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public Integer getParentKey() {
        return parentKey;
    }

    public void setParentKey(Integer parentKey) {
        this.parentKey = parentKey;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }
}
