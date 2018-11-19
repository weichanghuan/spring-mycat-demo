package com.base.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * 附件条目测试类
 * 
 *
 */
public class AttachmentItemTest {

	@Test
	public void testParse() {
		AttachmentItem item = AttachmentItem.parse("0.1,12345");
		Assert.assertEquals("0.1", item.getAttachmentType());
		Assert.assertEquals("12345", item.getIdUnderType());

		item = AttachmentItem.parse("0.1");
		Assert.assertEquals("0.1", item.getAttachmentType());
		Assert.assertNull(item.getIdUnderType());
		
		item = AttachmentItem.parse(",12345");
		Assert.assertNull(item.getAttachmentType());
		Assert.assertEquals("12345", item.getIdUnderType());
		
		item = AttachmentItem.parse(",");
		Assert.assertNull(item.getAttachmentType());
		Assert.assertNull(item.getIdUnderType());
		
		Assert.assertNotNull(AttachmentItem.parse(""));
		Assert.assertNull(AttachmentItem.parse(null));
	}

	@Test
	public void testFormat() {
		AttachmentItem item = new AttachmentItem();
		Assert.assertEquals(",", AttachmentItem.format(item));

		item.setAttachmentType("1.0");
		Assert.assertEquals("1.0,", AttachmentItem.format(item));

		item.setIdUnderType("12345");
		Assert.assertEquals("1.0,12345", AttachmentItem.format(item));

		Assert.assertNull(AttachmentItem.format(null));
	}
}
