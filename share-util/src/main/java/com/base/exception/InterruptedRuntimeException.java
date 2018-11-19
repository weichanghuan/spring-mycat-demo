package com.base.exception;

/**
 * 中断运行态异常类。
 * 
 *
 */
public class InterruptedRuntimeException extends RuntimeException {
	private static final long serialVersionUID = -6824693291999865639L;

	public InterruptedRuntimeException() {
	}
	
	public InterruptedRuntimeException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
