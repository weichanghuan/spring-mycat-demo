package com.base.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 参数列表类
 * 
 *
 */
public class ParamList<N, V> extends ArrayList<Param<N, V>> {
	private static final long serialVersionUID = 1L;

	/**
	 * 添加参数
	 * 
	 * @param name
	 * @param value
	 */
	public void addParam(N name, V value) {
		add(new Param<N, V>(name, value));
	}

	/**
	 * 根据参数名获取第一个同名参数值
	 * 
	 * @param name
	 * @return
	 */
	public V getParam(N name) {
		for (Iterator<Param<N, V>> i = iterator(); i.hasNext();) {
			Param<N, V> param = i.next();
			if (ObjectUtil.isEqual(param.getName(), name)) {
				return param.getValue();
			}
		}

		return null;
	}

	/**
	 * 根据参数名获取所有同名参数值集合
	 */
	public List<V> getParams(N name) {
		List<V> values = new ArrayList<V>();
		for (Iterator<Param<N, V>> i = iterator(); i.hasNext();) {
			Param<N, V> param = i.next();
			if (ObjectUtil.isEqual(param.getName(), name)) {
				values.add(param.getValue());
			}
		}

		return values;
	}

	/**
	 * 移除指定参数名的第一个同名参数
	 * 
	 * @param name
	 * @return
	 */
	public V removeParam(N name) {
		for (Iterator<Param<N, V>> i = iterator(); i.hasNext();) {
			Param<N, V> ele = i.next();
			if (ObjectUtil.isEqual(name, ele.getName())) {
				i.remove();
				return ele.getValue();
			}
		}

		return null;
	}

	/**
	 * 移除指定参数名的所有参数
	 * 
	 * @param name
	 * @return
	 */
	public boolean removeParams(N name) {
		boolean removed = false;
		for (Iterator<Param<N, V>> i = iterator(); i.hasNext();) {
			Param<N, V> ele = i.next();
			if (ObjectUtil.isEqual(name, ele.getName())) {
				i.remove();
				removed = true;
			}
		}

		return removed;
	}

	/**
	 * 是否包含指定名称的参数
	 * 
	 * @param name
	 * @return
	 */
	public boolean containsParam(N name) {
		for (Iterator<Param<N, V>> i = iterator(); i.hasNext();) {
			Param<N, V> ele = i.next();
			if (ObjectUtil.isEqual(name, ele.getName())) {
				return true;
			}
		}

		return false;
	}
}
