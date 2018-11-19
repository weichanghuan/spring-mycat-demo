package com.base.util;

import org.junit.Assert;

import org.junit.Test;

/**
 * 附件信息测试类
 * 
 *
 */
public class AttachmentInfoTest {

	@Test
	public void testFindByType() {
		AttachmentInfo info = AttachmentInfo.parse("0.1,12345|0.2,|,54321|,|0.1,33333");
		Assert.assertEquals("12345", info.findItemByType("0.1").getIdUnderType());
		Assert.assertEquals(2, info.findItemsByType("0.1").size());
		Assert.assertEquals("33333", info.findItemsByType("0.1").get(1).getIdUnderType());
		Assert.assertNull(info.findItemByType("0.2").getIdUnderType());
		Assert.assertNull(info.findItemByType("0.3"));
		Assert.assertNull(info.findItemByType(null));
		
		Assert.assertEquals("12345", info.findItemIdByType("0.1"));
		Assert.assertNull(info.findItemIdByType("0.2"));
		Assert.assertNull(info.findItemIdByType("0.3"));
		Assert.assertNull(info.findItemIdByType(null));
	}

	@Test
	public void testGetItemsArray() {
		AttachmentInfo info = AttachmentInfo.parse("0.1,12345|0.2,|,54321|,|0.1,33333");
		Assert.assertEquals(5, info.getItemsArray().length);
		Assert.assertEquals("12345", info.getItemsArray()[0].getIdUnderType());
		Assert.assertEquals("0.2", info.getItemsArray()[1].getAttachmentType());
		Assert.assertEquals("54321", info.getItemsArray()[2].getIdUnderType());
		Assert.assertNull(info.getItemsArray()[3].getAttachmentType());
		Assert.assertEquals("33333", info.getItemsArray()[4].getIdUnderType());
	}

	@Test
	public void testParse() {
		AttachmentInfo info = AttachmentInfo.parse("0.1,12345|0.2,|,54321|,|");
		Assert.assertEquals(4, info.getItems().size());
		Assert.assertEquals("0.1", info.getItems().get(0).getAttachmentType());
		Assert.assertEquals("12345", info.getItems().get(0).getIdUnderType());
		Assert.assertEquals("0.2", info.getItems().get(1).getAttachmentType());
		Assert.assertNull(info.getItems().get(1).getIdUnderType());
		Assert.assertNull(info.getItems().get(2).getAttachmentType());
		Assert.assertEquals("54321", info.getItems().get(2).getIdUnderType());
		Assert.assertNull(info.getItems().get(3).getAttachmentType());
		Assert.assertNull(info.getItems().get(3).getIdUnderType());

		info = AttachmentInfo.parse("0.1,12345");
		Assert.assertEquals(1, info.getItems().size());
		Assert.assertEquals("0.1", info.getItems().get(0).getAttachmentType());
		Assert.assertEquals("12345", info.getItems().get(0).getIdUnderType());

		info = AttachmentInfo.parse("0.1");
		Assert.assertEquals(1, info.getItems().size());
		Assert.assertEquals("0.1", info.getItems().get(0).getAttachmentType());
		Assert.assertNull(info.getItems().get(0).getIdUnderType());

		info = AttachmentInfo.parse("");
		Assert.assertEquals(0, info.getItems().size());

		Assert.assertNull(AttachmentInfo.parse(null));
	}

	@Test
	public void testFormat() {
		AttachmentInfo info = new AttachmentInfo();
		Assert.assertEquals("", AttachmentInfo.format(info));

		info.addItem("0.2", null);
		Assert.assertEquals("0.2,", AttachmentInfo.format(info));

		info.addItem("0.1", "12345");
		Assert.assertEquals("0.2,|0.1,12345", AttachmentInfo.format(info));

		info.addItem(null, "54321");
		info.addItem(null, null);
		Assert.assertEquals("0.2,|0.1,12345|,54321|,", AttachmentInfo.format(info));

		Assert.assertNull(AttachmentInfo.format(null));
	}
}
