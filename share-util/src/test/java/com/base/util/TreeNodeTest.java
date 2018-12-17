package com.base.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * 树节点实现测试类
 */
public class TreeNodeTest {

    @Test
    public void test() {
        TreeNode<String> node0 = new TreeNode<String>("0");
        TreeNode<String> node00 = new TreeNode<String>("00");
        TreeNode<String> node000 = new TreeNode<String>("000");
        TreeNode<String> node001 = new TreeNode<String>("001");
        TreeNode<String> node0000 = new TreeNode<String>("0000");
        TreeNode<String> node01 = new TreeNode<String>("01");
        TreeNode<String> node010 = new TreeNode<String>("010");

        // level 1
        node0.addChild(node00, true);
        node0.addChild(node01, true);

        // level 2
        node00.addChild(node000, true);
        node00.addChild(node001, true);
        node01.addChild(node010, true);

        // level 3
        node000.addChild(node0000, false); // not as parent

        Assert.assertEquals("0", node0.getData());
        Assert.assertTrue(node0.atRootLevel());
        Assert.assertFalse(node0.atLeafLevel());
        Assert.assertEquals(0, node0.level());
        Assert.assertNull(node0.getParent());
        Assert.assertEquals(2, node0.getChildren().size());
        Assert.assertEquals("00", node0.getChildren().get(0).getData());
        Assert.assertEquals("01", node0.getChildren().get(1).getData());

        Assert.assertEquals("00", node00.getData());
        Assert.assertFalse(node00.atRootLevel());
        Assert.assertFalse(node00.atLeafLevel());
        Assert.assertEquals(1, node00.level());
        Assert.assertEquals(node0, node00.getParent());
        Assert.assertEquals(2, node00.getChildren().size());
        Assert.assertEquals("000", node00.getChildren().get(0).getData());
        Assert.assertEquals("001", node00.getChildren().get(1).getData());

        Assert.assertEquals("01", node01.getData());
        Assert.assertFalse(node01.atRootLevel());
        Assert.assertFalse(node01.atLeafLevel());
        Assert.assertEquals(node0, node01.getParent());
        Assert.assertEquals(1, node01.level());
        Assert.assertEquals(1, node01.getChildren().size());
        Assert.assertEquals("010", node01.getChildren().get(0).getData());

        Assert.assertEquals("000", node000.getData());
        Assert.assertFalse(node000.atRootLevel());
        Assert.assertFalse(node000.atLeafLevel());
        Assert.assertEquals(node00, node000.getParent());
        Assert.assertEquals(2, node000.level());
        Assert.assertEquals(1, node000.getChildren().size());
        Assert.assertEquals("0000", node000.getChildren().get(0).getData());

        Assert.assertEquals("001", node001.getData());
        Assert.assertFalse(node001.atRootLevel());
        Assert.assertTrue(node001.atLeafLevel());
        Assert.assertEquals(node00, node001.getParent());
        Assert.assertEquals(2, node001.level());
        Assert.assertNull(node001.getChildren());

        Assert.assertEquals("010", node010.getData());
        Assert.assertFalse(node010.atRootLevel());
        Assert.assertTrue(node010.atLeafLevel());
        Assert.assertEquals(node01, node010.getParent());
        Assert.assertEquals(2, node010.level());
        Assert.assertNull(node010.getChildren());

        Assert.assertEquals("0000", node0000.getData());
        Assert.assertTrue(node0000.atRootLevel()); // not parent
        Assert.assertTrue(node0000.atLeafLevel());
        Assert.assertNull(node0000.getParent());
        Assert.assertEquals(0, node0000.level());
        Assert.assertNull(node0000.getChildren());

        node000.removeParent();
        Assert.assertNull(node000.getParent());

        node000.removeChildren();
        Assert.assertNull(node000.getChildren());

        node000.addChildren(ArrayUtil.asList("0001", "0002", "0003"), true);
        Assert.assertEquals(3, node000.getChildren().size());
        Assert.assertEquals("0001", node000.getChildren().get(0).getData());
        Assert.assertEquals(node000, node000.getChildren().get(0).getParent());
        Assert.assertEquals("0002", node000.getChildren().get(1).getData());
        Assert.assertEquals(node000, node000.getChildren().get(1).getParent());
        Assert.assertEquals("0003", node000.getChildren().get(2).getData());
        Assert.assertEquals(node000, node000.getChildren().get(2).getParent());
    }
}
