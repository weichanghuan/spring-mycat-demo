package com.base.util;

import java.io.Serializable;

/**
 * 参数类
 * 
 *
 */
public class Param<N, V> implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 参数名
	 */
	private N name;

	/**
	 * 参数值
	 */
	private V value;

	/**
	 * 构造函数
	 */
	public Param() {
	}

	/**
	 * 构造函数
	 * 
	 * @param name
	 * @param value
	 */
	public Param(N name, V value) {
		this.name = name;
		this.value = value;
	}

	/**
	 * @return the name
	 */
	public N getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(N name) {
		this.name = name;
	}

	/**
	 * @return the value
	 */
	public V getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(V value) {
		this.value = value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;

		} else if (obj instanceof Param == false) {
			return false;
		}

		Param<?, ?> other = (Param<?, ?>) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (name.equals(other.name) == false) {
			return false;
		}

		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (value.equals(other.value) == false) {
			return false;
		}

		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("{name=").append(name).append(", value=").append(value).append("}");
		return str.toString();
	}
}
