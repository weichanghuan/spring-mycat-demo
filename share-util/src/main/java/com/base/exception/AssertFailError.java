package com.base.exception;

/**
 * 断言失败异常类
 *
 *
 */
public class AssertFailError extends Error {
	private static final long serialVersionUID = 1L;

	/**
	 * 默认构造函数
	 */
	public AssertFailError() {
	}

	/**
	 * 构造函数
	 * 
	 * @param msg
	 */
	public AssertFailError(String msg) {
		super(msg);
	}
}
