package com.ihomefnt.o2o.intf.domain.product.doo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 产品分类-使用
 * @author Administrator
 *
 */
@Data
@NoArgsConstructor
public class TreeNode {
	private Long menuId;
	private String name;
	private List<TreeNode> children;

	public TreeNode(ClassifyNode classifyNode) {
		this.menuId = classifyNode.getKey();
		this.name = classifyNode.getName();
	}
}