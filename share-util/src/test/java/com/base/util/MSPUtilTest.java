package com.base.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * 移动运营商工具测试类
 */
public class MSPUtilTest {
    @Test
    public void testIsMSPMobileNumber() {
        Assert.assertTrue(MSPUtil.isCMMobileNumber("13400000000"));
        Assert.assertTrue(MSPUtil.isCMMobileNumber("13500000000"));
        Assert.assertTrue(MSPUtil.isCMMobileNumber("13600000000"));
        Assert.assertTrue(MSPUtil.isCMMobileNumber("13700000000"));
        Assert.assertTrue(MSPUtil.isCMMobileNumber("13800000000"));
        Assert.assertTrue(MSPUtil.isCMMobileNumber("13900000000"));
        Assert.assertTrue(MSPUtil.isCMMobileNumber("14700000000"));
        Assert.assertTrue(MSPUtil.isCMMobileNumber("15000000000"));
        Assert.assertTrue(MSPUtil.isCMMobileNumber("15100000000"));
        Assert.assertTrue(MSPUtil.isCMMobileNumber("15200000000"));
        Assert.assertTrue(MSPUtil.isCMMobileNumber("15700000000"));
        Assert.assertTrue(MSPUtil.isCMMobileNumber("15800000000"));
        Assert.assertTrue(MSPUtil.isCMMobileNumber("15900000000"));
        Assert.assertTrue(MSPUtil.isCMMobileNumber("18200000000"));
        Assert.assertTrue(MSPUtil.isCMMobileNumber("18300000000"));
        Assert.assertTrue(MSPUtil.isCMMobileNumber("18400000000"));
        Assert.assertTrue(MSPUtil.isCMMobileNumber("18700000000"));
        Assert.assertTrue(MSPUtil.isCMMobileNumber("18800000000"));
        Assert.assertTrue(MSPUtil.isCMMobileNumber("17800000000"));

        Assert.assertTrue(MSPUtil.isCUMobileNumber("13000000000"));
        Assert.assertTrue(MSPUtil.isCUMobileNumber("13100000000"));
        Assert.assertTrue(MSPUtil.isCUMobileNumber("13200000000"));
        Assert.assertTrue(MSPUtil.isCUMobileNumber("14500000000"));
        Assert.assertTrue(MSPUtil.isCUMobileNumber("15500000000"));
        Assert.assertTrue(MSPUtil.isCUMobileNumber("15600000000"));
        Assert.assertTrue(MSPUtil.isCUMobileNumber("18500000000"));
        Assert.assertTrue(MSPUtil.isCUMobileNumber("18600000000"));
        Assert.assertTrue(MSPUtil.isCUMobileNumber("17600000000"));

        Assert.assertTrue(MSPUtil.isCTMobileNumber("13300000000"));
        Assert.assertTrue(MSPUtil.isCTMobileNumber("15300000000"));
        Assert.assertTrue(MSPUtil.isCTMobileNumber("18000000000"));
        Assert.assertTrue(MSPUtil.isCTMobileNumber("18100000000"));
        Assert.assertTrue(MSPUtil.isCTMobileNumber("18900000000"));
        Assert.assertTrue(MSPUtil.isCTMobileNumber("17700000000"));

        Assert.assertFalse(MSPUtil.isCMMobileNumber("1340000"));
        Assert.assertFalse(MSPUtil.isCMMobileNumber("13300000000"));
        Assert.assertFalse(MSPUtil.isCMMobileNumber(null));

        Assert.assertFalse(MSPUtil.isCUMobileNumber("1300000"));
        Assert.assertFalse(MSPUtil.isCUMobileNumber("13400000000"));
        Assert.assertFalse(MSPUtil.isCUMobileNumber(null));

        Assert.assertFalse(MSPUtil.isCTMobileNumber("1330000"));
        Assert.assertFalse(MSPUtil.isCTMobileNumber("13000000000"));
        Assert.assertFalse(MSPUtil.isCTMobileNumber(null));
    }

    @Test
    public void testFindOrRemoveMSPMobileNumbers() {
        List<String> mobiles = new ArrayList<String>();
        mobiles.add("18000000000"); // CT
        mobiles.add("18100000000"); // CT
        mobiles.add("18200000000"); // CM
        mobiles.add("18300000000"); // CM
        mobiles.add("18400000000"); // CM
        mobiles.add("18500000000"); // CU
        mobiles.add("18600000000"); // CU
        mobiles.add("18700000000"); // CM
        mobiles.add("18800000000"); // CM
        mobiles.add("18900000000"); // CT
        mobiles.add("00000000000"); // NONE

        // FIND
        List<String> cmMobiles = MSPUtil.findCMMobileNumbers(mobiles);
        Assert.assertEquals(5, cmMobiles.size());
        Assert.assertEquals("18200000000", cmMobiles.get(0));
        Assert.assertEquals("18800000000", cmMobiles.get(4));
        Assert.assertEquals(11, mobiles.size());

        List<String> cuMobiles = MSPUtil.findCUMobileNumbers(mobiles);
        Assert.assertEquals(2, cuMobiles.size());
        Assert.assertEquals("18500000000", cuMobiles.get(0));
        Assert.assertEquals("18600000000", cuMobiles.get(1));
        Assert.assertEquals(11, mobiles.size());

        List<String> ctMobiles = MSPUtil.findCTMobileNumbers(mobiles);
        Assert.assertEquals(3, ctMobiles.size());
        Assert.assertEquals("18000000000", ctMobiles.get(0));
        Assert.assertEquals("18900000000", ctMobiles.get(2));
        Assert.assertEquals(11, mobiles.size());

        // REMOVE
        cmMobiles = MSPUtil.removeCMMobileNumbers(mobiles);
        Assert.assertEquals(5, cmMobiles.size());
        Assert.assertEquals("18200000000", cmMobiles.get(0));
        Assert.assertEquals("18800000000", cmMobiles.get(4));
        Assert.assertEquals(6, mobiles.size());

        cuMobiles = MSPUtil.removeCUMobileNumbers(mobiles);
        Assert.assertEquals(2, cuMobiles.size());
        Assert.assertEquals("18500000000", cuMobiles.get(0));
        Assert.assertEquals("18600000000", cuMobiles.get(1));
        Assert.assertEquals(4, mobiles.size());

        ctMobiles = MSPUtil.removeCTMobileNumbers(mobiles);
        Assert.assertEquals(3, ctMobiles.size());
        Assert.assertEquals("18000000000", ctMobiles.get(0));
        Assert.assertEquals("18900000000", ctMobiles.get(2));
        Assert.assertEquals(1, mobiles.size());
    }
}
