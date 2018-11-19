package com.base.util;

import java.io.Serializable;

/**
 * 附件条目类
 * 
 *
 */
public class AttachmentItem implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 分隔符
	 */
	public static final String DELIMITER = ",";

	/**
	 * 类型
	 */
	private String attachmentType;

	/**
	 * 编号
	 */
	private String idUnderType;

	/**
	 * 格式化成字符串格式
	 * 
	 * @param item
	 * @return
	 */
	public static String format(AttachmentItem item) {
		if (item == null) {
			return null;
		}

		StringBuilder buff = new StringBuilder();
		if (item.attachmentType != null) {
			buff.append(item.attachmentType);
		}
		buff.append(DELIMITER);
		if (item.idUnderType != null) {
			buff.append(item.idUnderType);
		}

		return buff.toString();
	}

	/**
	 * 解析字符串，得到条目对象
	 */
	public static AttachmentItem parse(String itemInfo) {
		if (itemInfo == null) {
			return null;
		}

		String[] arr = itemInfo.split(DELIMITER);
		AttachmentItem item = new AttachmentItem();
		if (arr.length > 1 && arr[1].length() > 0) {
			item.setIdUnderType(arr[1]);
		}

		if (arr.length > 0 && arr[0].length() > 0) {
			item.setAttachmentType(arr[0]);
		}

		return item;
	}

	/**
	 * @return the attachmentType
	 */
	public String getAttachmentType() {
		return attachmentType;
	}

	/**
	 * @param attachmentType
	 *            the attachmentType to set
	 */
	public void setAttachmentType(String attachmentType) {
		this.attachmentType = attachmentType;
	}

	/**
	 * @return the idUnderType
	 */
	public String getIdUnderType() {
		return idUnderType;
	}

	/**
	 * @param idUnderType
	 *            the idUnderType to set
	 */
	public void setIdUnderType(String idUnderType) {
		this.idUnderType = idUnderType;
	}
}
