package com.base.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 身份证工具类
 */
public final class IdUtil {
    /**
     * 身份证每一位的计算分量
     */
    private static final int[] WEIGHTS = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

    /**
     * 校验位映射
     */
    private static final char[] CHECK_VALUES = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};

    /**
     * 18位身份证格式 TODO 完善验证规则，前缀
     */
    private static final String ID18_PATTERN = "\\d{17}[0-9xX*]";

    /**
     * 15位身份证格式
     */
    private static final String ID15_PATTERN = "\\d{15}";

    /**
     * 身份证省份编码映射
     */
    private static final Map<String, String> PROV_CODE_2_NAME = new HashMap<String, String>();

    static {
        PROV_CODE_2_NAME.put("11", "北京");
        PROV_CODE_2_NAME.put("12", "天津");
        PROV_CODE_2_NAME.put("13", "河北");
        PROV_CODE_2_NAME.put("14", "山西");
        PROV_CODE_2_NAME.put("15", "内蒙古");
        PROV_CODE_2_NAME.put("21", "辽宁");
        PROV_CODE_2_NAME.put("22", "吉林");
        PROV_CODE_2_NAME.put("23", "黑龙江");
        PROV_CODE_2_NAME.put("31", "上海");
        PROV_CODE_2_NAME.put("32", "江苏");
        PROV_CODE_2_NAME.put("33", "浙江");
        PROV_CODE_2_NAME.put("34", "安徽");
        PROV_CODE_2_NAME.put("35", "福建");
        PROV_CODE_2_NAME.put("36", "江西");
        PROV_CODE_2_NAME.put("37", "山东");
        PROV_CODE_2_NAME.put("41", "河南");
        PROV_CODE_2_NAME.put("42", "湖北");
        PROV_CODE_2_NAME.put("43", "湖南");
        PROV_CODE_2_NAME.put("44", "广东");
        PROV_CODE_2_NAME.put("45", "广西");
        PROV_CODE_2_NAME.put("46", "海南");
        PROV_CODE_2_NAME.put("50", "重庆");
        PROV_CODE_2_NAME.put("51", "四川");
        PROV_CODE_2_NAME.put("52", "贵州");
        PROV_CODE_2_NAME.put("53", "云南");
        PROV_CODE_2_NAME.put("54", "西藏");
        PROV_CODE_2_NAME.put("61", "陕西");
        PROV_CODE_2_NAME.put("62", "甘肃");
        PROV_CODE_2_NAME.put("63", "青海");
        PROV_CODE_2_NAME.put("64", "宁夏");
        PROV_CODE_2_NAME.put("65", "新疆");
        PROV_CODE_2_NAME.put("71", "台湾");
        PROV_CODE_2_NAME.put("81", "香港");
        PROV_CODE_2_NAME.put("82", "澳门");
        PROV_CODE_2_NAME.put("91", "国外");
    }

    /**
     * 将十五位身份证号转换为十八位
     *
     * @param id15
     * @return
     */
    public static String convertId15To18(String id15) {
        if (id15 == null || id15.matches("\\d{15}") == false) {
            throw new IllegalArgumentException("The length of id must be 15, id=" + id15);
        }

        StringBuilder id18 = new StringBuilder();
        id18.append(id15.substring(0, 6));
        id18.append("19");
        id18.append(id15.substring(6, 15));
        id18.append(calcCheckValue(id18.toString()));

        return id18.toString();
    }

    /**
     * 计算校验位(输入前17位)
     *
     * @param id17
     * @return
     */
    public static String calcCheckValue(String id17) {
        if (id17 == null || id17.matches("\\d{17}") == false) {
            throw new IllegalArgumentException("The length of id must be 17, id=" + id17);
        }

        int sum = 0;
        for (int i = 0; i < 17; i++) {
            sum += WEIGHTS[i] * Integer.parseInt(id17.substring(i, i + 1));
        }

        return String.valueOf(CHECK_VALUES[sum % 11]);
    }

    /**
     * 是否是有效合法身份证
     *
     * @param id
     * @return
     */
    public static boolean isValidIdCardNo(String id) {
        if (id == null) {
            return false;
        }

        if (id.matches(ID18_PATTERN)) {
            // 18位身份证 需检查校验位
            String expCv = calcCheckValue(id.substring(0, 17));
            String actCv = id.substring(17, 18).replace('*', 'X'); // *视为X

            return expCv.equalsIgnoreCase(actCv);
        }

        // 15位身份证
        return id.matches(ID15_PATTERN);
    }

    /**
     * 获取省份代码
     *
     * @param id
     * @return
     */
    public static String getProvinceCode(String id) {
        if (id == null || id.length() < 2) {
            return null;
        }
        return id.substring(0, 2);
    }

    /**
     * 获取省份名称
     *
     * @param id
     * @return
     */
    public static String getProvinceName(String id) {
        return PROV_CODE_2_NAME.get(getProvinceCode(id));
    }

    /**
     * 格式化身份证，结尾的x或*替换成X
     *
     * @param id
     * @return
     */
    public static String format(String id) {
        if (id == null) {
            return null;
        }

        if (id.endsWith("*") || id.endsWith("x")) {
            char[] idChars = id.toCharArray();
            idChars[idChars.length - 1] = 'X';

            id = new String(idChars);
        }

        return id;
    }

    private IdUtil() {
    }
}
