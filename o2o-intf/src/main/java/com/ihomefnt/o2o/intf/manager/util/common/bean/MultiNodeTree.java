package com.ihomefnt.o2o.intf.manager.util.common.bean;

import java.util.List;

/**
 * 多叉树
 */
public class MultiNodeTree
{
    /** 树根*/
    private MTreeNode root;

    /**
     * 构造函数
     */
    public MultiNodeTree()
    {
        root = new MTreeNode(new TreeNode("产品类型"));
    }

    /**
     * 生成一颗多叉树，根节点为root
     *
     * @param treeNodes 生成多叉树的节点集合
     * @return ManyNodeTree
     */
    public MultiNodeTree createTree(List<TreeNode> treeNodes)
    {
        if(treeNodes == null || treeNodes.size() <= 0)
            return null;

        MultiNodeTree manyNodeTree =  new MultiNodeTree();

        //将所有节点添加到多叉树中
        for(TreeNode treeNode : treeNodes)
        {
            if(treeNode.getParentKey() == 1) //key 为 1 代表根节点
            {
                //向根添加一个节点
                manyNodeTree.getRoot().getChildList().add(new MTreeNode(treeNode));
            }
            else
            {
                addChild(manyNodeTree.getRoot(), treeNode);
            }
        }

        return manyNodeTree;
    }

    /**
     * 向指定多叉树节点添加子节点
     *
     * @param manyTreeNode 多叉树节点
     * @param child 节点
     */
    public void addChild(MTreeNode manyTreeNode, TreeNode child)
    {
        for(MTreeNode item : manyTreeNode.getChildList())
        {
            if(null != item.getData() && null != item.getData().getKey() && item.getData().getKey().equals(child.getParentKey()))
            {
                //找到对应的父亲
                item.getChildList().add(new MTreeNode(child));
                break;
            }
            else
            {
                if(item.getChildList() != null && item.getChildList().size() > 0)
                {
                    addChild(item, child);
                }
            }
        }
    }

    /**
     * 遍历多叉树
     *
     * @param manyTreeNode 多叉树节点
     * @return
     */
    public String iteratorTree(MTreeNode manyTreeNode)
    {
        StringBuilder buffer = new StringBuilder();
        buffer.append("\n");

        if(manyTreeNode != null)
        {
            for (MTreeNode index : manyTreeNode.getChildList())
            {
                buffer.append(index.getData().getNodeName()+ ",");

                if (index.getChildList() != null && index.getChildList().size() > 0 )
                {
                    buffer.append(iteratorTree(index));
                }
            }
        }

        buffer.append("\n");

        return buffer.toString();
    }

    public MTreeNode getRoot() {
        return root;
    }

    public void setRoot(MTreeNode root) {
        this.root = root;
    }

}
