package com.base.util;

/**
 * 非空异常类。
 * 
 *
 */
public class NotNullException extends IllegalArgumentException {
	private static final long serialVersionUID = 6508656564254704707L;

	public NotNullException(String name) {
		super(name + " is null.");
	}
}
