package com.base.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 附件信息类
 * 
 *
 */
public class AttachmentInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 分隔符
	 */
	public static final String DELIMITER = "|";

	/**
	 * 附件条目集合
	 */
	private List<AttachmentItem> items;

	/**
	 * 解析字符串得到附件信息对象
	 * 
	 * @param info
	 * @return
	 */
	public static AttachmentInfo parse(String info) {
		if (info == null) {
			return null;
		}

		List<AttachmentItem> items = new ArrayList<AttachmentItem>();
		if (info.length() > 0) {
			for (String itemInfo : info.split("\\" + DELIMITER)) {
				items.add(AttachmentItem.parse(itemInfo));
			}
		}

		AttachmentInfo attachInfo = new AttachmentInfo();
		attachInfo.items = items;

		return attachInfo;
	}

	/**
	 * 格式化成字符串类型
	 * 
	 * @param info
	 * @return
	 */
	public static String format(AttachmentInfo info) {
		if (info == null) {
			return null;
		}

		StringBuilder buff = new StringBuilder();
		if (info.items != null) {
			for (AttachmentItem item : info.items) {
				if (item == null) {
					continue;
				}

				if (buff.length() > 0) {
					buff.append(DELIMITER);
				}

				buff.append(AttachmentItem.format(item));
			}
		}

		return buff.toString();
	}

	/**
	 * @return the items
	 */
	public List<AttachmentItem> getItems() {
		return items;
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(List<AttachmentItem> items) {
		this.items = items;
	}

	/**
	 * @return
	 */
	public AttachmentItem[] getItemsArray() {
		if (items == null) {
			return null;
		}

		return items.toArray(new AttachmentItem[items.size()]);
	}

	/**
	 * 添加条目
	 * 
	 * @param type
	 * @param idUnderType
	 */
	public void addItem(String type, String idUnderType) {
		AttachmentItem item = new AttachmentItem();
		item.setAttachmentType(type);
		item.setIdUnderType(idUnderType);

		addItem(item);
	}

	/**
	 * 添加条目
	 * 
	 * @param item
	 */
	public void addItem(AttachmentItem item) {
		if (items == null) {
			items = new ArrayList<AttachmentItem>();
		}

		items.add(item);
	}

	/**
	 * 根据类型找匹配的第一个条目
	 * 
	 * @param type
	 * @return
	 */
	public AttachmentItem findItemByType(String type) {
		if (type != null && items != null) {
			for (AttachmentItem item : items) {
				if (type.equals(item.getAttachmentType())) {
					return item;
				}
			}
		}

		return null;
	}

	/**
	 * 根据类型找匹配的第一个条目编号
	 * 
	 * @param type
	 * @return
	 */
	public String findItemIdByType(String type) {
		AttachmentItem item = findItemByType(type);
		return (item != null ? item.getIdUnderType() : null);
	}

	/**
	 * 根据类型找所有匹配的条目
	 * 
	 * @param type
	 * @return
	 */
	public List<AttachmentItem> findItemsByType(String type) {
		if (type == null || items == null) {
			return Collections.emptyList();
		}

		List<AttachmentItem> findItems = new ArrayList<AttachmentItem>();
		for (AttachmentItem item : items) {
			if (type.equals(item.getAttachmentType())) {
				findItems.add(item);
			}
		}

		return findItems;
	}
}
