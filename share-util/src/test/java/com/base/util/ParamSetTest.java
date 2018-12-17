package com.base.util;

import java.util.List;

import org.junit.Assert;

import org.junit.Test;

/**
 * 参数集合测试类
 */
public class ParamSetTest {

    @Test
    public void test() {
        // addParam
        ParamSet<String, Object> params = new ParamSet<String, Object>();
        params.addParam("A1", "V11");
        params.addParam("A1", "V12");
        params.addParam("A1", "V12"); // duplicated
        params.addParam("A2", "V21");
        params.addParam("A2", 1);
        params.addParam("A2", 1); // duplicated
        params.addParam("A3", null);
        params.addParam("A3", null); // duplicated

        Assert.assertEquals(5, params.size());

        // getParam
        Assert.assertEquals("V11", params.getParam("A1"));

        // getParams
        List<Object> a1Vals = params.getParams("A1");
        Assert.assertEquals(2, a1Vals.size());
        Assert.assertEquals("V11", a1Vals.get(0));
        Assert.assertEquals("V12", a1Vals.get(1));

        // containsParam
        Assert.assertTrue(params.containsParam("A3"));
        Assert.assertNull(params.getParam("A3"));

        // removeParam
        Assert.assertEquals("V21", params.removeParam("A2"));

        // removeParams
        Assert.assertTrue(params.removeParams("A2"));
        Assert.assertFalse(params.containsParam("A2"));

        Assert.assertEquals(3, params.size());
    }
}
