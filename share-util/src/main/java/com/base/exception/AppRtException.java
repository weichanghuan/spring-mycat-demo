package com.base.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * 应用运行态异常类，采用异常代码和上下文数据描述方式。
 * 
 *
 */
public class AppRtException extends RuntimeException {
	private static final long serialVersionUID = -4729161738021046262L;

	/**
	 * 异常代码
	 */
	private String code;

	/**
	 * 本地化异常信息
	 */
	private String localizedMessage;

	/**
	 * 异常信息是否不可修改
	 */
	private boolean immutable = false;

	/**
	 * 是否已输出异常信息
	 */
	private boolean logged = false;

	/**
	 * 异常数据上下文对象数组
	 */
	private Map<String, String> context = new HashMap<String, String>();

	public AppRtException(String code) {
		super("AppRtException[" + code + "].");
		this.code = code;
	}

	public AppRtException(String code, Throwable t) {
		super("AppRtException[" + code + "].", t);
		this.code = code;
	}

	public AppRtException(String code, String message) {
		super("AppRtException[" + code + "]: " + message);
		this.code = code;
		this.localizedMessage = message;
	}

	public AppRtException(String code, String message, Throwable t) {
		super("AppRtException[" + code + "]: " + message, t);
		this.code = code;
		this.localizedMessage = message;
	}

	public AppRtException(String code, String message, Map<String, String> args) {
		super("AppRtException[" + code + "]: " + message);
		this.code = code;
		this.localizedMessage = message;
		context.putAll(args);
	}

	public AppRtException(String code, String message, Map<String, String> args, Throwable t) {
		super("AppRtException[" + code + "]: " + message, t);
		this.code = code;
		this.localizedMessage = message;
		context.putAll(args);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Map<String, String> getContext() {
		return context;
	}

	public void setContext(Map<String, String> args) {
		this.context = args;
	}

	public void setLocalizedMessage(String localizedMessage) {
		this.localizedMessage = localizedMessage;
	}

	@Override
	public String getLocalizedMessage() {
		return localizedMessage;
	}

	public boolean isImmutable() {
		return immutable;
	}

	public void setImmutable(boolean immutable) {
		this.immutable = immutable;
	}

	public boolean isLogged() {
		return logged;
	}

	public void setLogged(boolean logged) {
		this.logged = logged;
	}
}
