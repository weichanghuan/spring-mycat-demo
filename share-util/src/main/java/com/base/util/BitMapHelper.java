package com.base.util;

import com.base.exception.AppRtException;

public class BitMapHelper {

	/**
	 * 功能可用
	 */
	private static final char FUN_ENABLE_FLAG = '1';
	
	/**
	 * 功能不可用
	 */
	private static final char FUN_DISABLE_FLAG = '0';
	
	
	/**
	 * 判断位图对应位置功能是否可用
	 * @param config
	 * @param index
	 * @return
	 */
	public static boolean isFunEnable(String config, int index){
		if (config == null) {
			return false;
		}
		if (config.length() <= index) {
			return false;
		}
		char funFlag = StringUtil.chatAt(config, index, FUN_DISABLE_FLAG);
		
		return funFlag == FUN_ENABLE_FLAG;
	}
	
	/**
	 * 设定位图对应位置功能是否可用
	 * @param config
	 * @param index
	 * @param funFlag
	 * @return
	 */
	public static String setFunFlag(String config, int index, char funFlag){
		if (config == null) {
			throw new AppRtException("config cannot be null");
		}
		
		if (config.length() <= index) {
			throw new AppRtException("config.length() must be bigger than the value of index");
		}
		
		char[] temp = config.toCharArray();
		temp[index] = funFlag;
		
		return new String(temp);
	}
	
	
	private BitMapHelper(){}
}
