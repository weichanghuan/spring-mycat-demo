package com.base.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 树节点简单实现类 (需要进行序列化操作时，建议不要设置parent属性避免产生递归问题)
 * 
 *
 */
public class TreeNode<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 数据
	 */
	private T data;

	/**
	 * 父节点
	 */
	private TreeNode<T> parent;

	/**
	 * 子节点
	 */
	private List<TreeNode<T>> children;

	/**
	 * 构造函数
	 */
	public TreeNode() {
	}

	/**
	 * 构造函数
	 * 
	 * @param data
	 */
	public TreeNode(T data) {
		this.data = data;
	}

	/**
	 * 构造函数
	 * 
	 * @param data
	 * @param parent
	 */
	public TreeNode(T data, TreeNode<T> parent) {
		this.data = data;
		this.parent = parent;
	}

	/**
	 * 添加子节点数据
	 * 
	 * @param data
	 * @param setParent
	 */
	public void addChild(T data, boolean setParent) {
		if (children == null) {
			children = new ArrayList<TreeNode<T>>();
		}

		children.add(new TreeNode<T>(data, setParent ? this : null));
	}

	/**
	 * 添加子节点
	 * 
	 * @param data
	 * @param setParent
	 */
	public void addChild(TreeNode<T> child, boolean setParent) {
		if (children == null) {
			children = new ArrayList<TreeNode<T>>();
		}

		if (setParent && child != null) {
			child.setParent(this);
		}

		children.add(child);
	}

	/**
	 * 添加子节点集合
	 * 
	 * @param data
	 * @param setParent
	 */
	public void addChildren(List<T> children, boolean setParent) {
		if (children == null || children.isEmpty()) {
			return;
		}

		for (T child : children) {
			addChild(child, setParent);
		}
	}

	/**
	 * @return the children
	 */
	public List<TreeNode<T>> getChildren() {
		return children;
	}

	/**
	 * @param children
	 *            the children to set
	 */
	public void setChildren(List<TreeNode<T>> children) {
		this.children = children;
	}

	/**
	 * @return the data
	 */
	public T getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(T data) {
		this.data = data;
	}

	/**
	 * @return the parent
	 */
	public TreeNode<T> getParent() {
		return parent;
	}

	/**
	 * @param parent
	 *            the parent to set
	 */
	public void setParent(TreeNode<T> parent) {
		this.parent = parent;
	}

	/**
	 * 是否是在叶子节点(不用isXXX避免序列化多余属性)
	 * 
	 * @return
	 */
	public boolean atLeafLevel() {
		return (children == null || children.isEmpty());
	}

	/**
	 * 是否是在根节点(不用isXXX避免序列化多余属性)
	 * 
	 * @return
	 */
	public boolean atRootLevel() {
		return (parent == null);
	}

	/**
	 * 删除父节点
	 */
	public void removeParent() {
		parent = null;
	}

	/**
	 * 删除子节点
	 */
	public void removeChildren() {
		children = null;
	}

	/**
	 * 节点层级
	 * 
	 * @return
	 */
	public int level() {
		return (parent != null ? parent.level() + 1 : 0);
	}
}
