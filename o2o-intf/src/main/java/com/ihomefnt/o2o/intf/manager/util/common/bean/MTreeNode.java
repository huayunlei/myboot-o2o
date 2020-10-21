package com.ihomefnt.o2o.intf.manager.util.common.bean;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * 多叉树节点
 */
public class MTreeNode
{
    /** 树节点*/
    private TreeNode data;
    /** 子树集合*/
    private List<MTreeNode> childList;

    /**
     * 构造函数
     *
     * @param data 树节点
     */
    public MTreeNode(TreeNode data)
    {
        this.data = data;
        this.childList = new ArrayList<MTreeNode>();
    }

    /**
     * 构造函数
     *
     * @param data 树节点
     * @param childList 子树集合
     */
    public MTreeNode(TreeNode data, List<MTreeNode> childList)
    {
        this.data = data;
        this.childList = childList;
    }

    public TreeNode getData() {
        return data;
    }

    public void setData(TreeNode data) {
        this.data = data;
    }

    public List<MTreeNode> getChildList() {
        return childList;
    }

    public void setChildList(List<MTreeNode> childList) {
        this.childList = childList;
    }

    @JsonIgnore
    public boolean isLeaf() {
        return childList == null || childList.isEmpty();
    }
}
