package com.base.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 移动运营商工具类
 */
public final class MSPUtil {
    /**
     * 号码正则表达式 - 中国移动
     */
    private static final String MOBILE_PATTERN_CM = "(13[4-9]|14[78]|15[0-2,7-9]|18[2-4,7-8]|178|198)\\d{8}";

    /**
     * 号码正则表达式 - 中国联通
     */
    private static final String MOBILE_PATTERN_CU = "(13[0-2]|14[56]|15[56]|18[56]|176|166)\\d{8}";

    /**
     * 号码正则表达式 - 中国电信
     */
    private static final String MOBILE_PATTERN_CT = "(133|153|18[019]|177|199)\\d{8}";

    /**
     * 判断是否是中国移动的手机号码
     *
     * @param mobileNumber
     * @return
     */
    public static boolean isCMMobileNumber(String mobileNumber) {
        return (mobileNumber != null && mobileNumber.matches(MOBILE_PATTERN_CM));
    }

    /**
     * 判断是否是中国联通的手机号码
     *
     * @param mobileNumber
     * @return
     */
    public static boolean isCUMobileNumber(String mobileNumber) {
        return (mobileNumber != null && mobileNumber.matches(MOBILE_PATTERN_CU));
    }

    /**
     * 判断是否是中国电信的手机号码
     *
     * @param mobileNumber
     * @return
     */
    public static boolean isCTMobileNumber(String mobileNumber) {
        return (mobileNumber != null && mobileNumber.matches(MOBILE_PATTERN_CT));
    }

    /**
     * 从指定手机号中查找中国移动的手机号码
     *
     * @param mobiles
     * @return
     */
    public static List<String> findCMMobileNumbers(List<String> mobiles) {
        return findMSPMobileNumbers(mobiles, MOBILE_PATTERN_CM, false);
    }

    /**
     * 从指定手机号中查找中国移动的手机号码并从源集合中移除
     *
     * @param mobiles
     * @return
     */
    public static List<String> removeCMMobileNumbers(List<String> mobiles) {
        return findMSPMobileNumbers(mobiles, MOBILE_PATTERN_CM, true);
    }

    /**
     * 从指定手机号中查找中国联通的手机号码
     *
     * @param mobiles
     * @return
     */
    public static List<String> findCUMobileNumbers(List<String> mobiles) {
        return findMSPMobileNumbers(mobiles, MOBILE_PATTERN_CU, false);
    }

    /**
     * 从指定手机号中查找中国联通的手机号码并从源集合中移除
     *
     * @param mobiles
     * @return
     */
    public static List<String> removeCUMobileNumbers(List<String> mobiles) {
        return findMSPMobileNumbers(mobiles, MOBILE_PATTERN_CU, true);
    }

    /**
     * 从指定手机号中查找中国电信的手机号码
     *
     * @param mobiles
     * @return
     */
    public static List<String> findCTMobileNumbers(List<String> mobiles) {
        return findMSPMobileNumbers(mobiles, MOBILE_PATTERN_CT, false);
    }

    /**
     * 从指定手机号中查找中国电信的手机号码并从源集合中移除
     *
     * @param mobiles
     * @return
     */
    public static List<String> removeCTMobileNumbers(List<String> mobiles) {
        return findMSPMobileNumbers(mobiles, MOBILE_PATTERN_CT, true);
    }

    /**
     * 从指定手机号中查找指定移动服务提供商的手机号码
     *
     * @param mobiles
     * @param mobilePattern
     * @param removeIfFind
     * @return
     */
    private static List<String> findMSPMobileNumbers(List<String> mobiles, String mobilePattern, boolean removeIfFind) {
        if (CollectionUtil.isEmpty(mobiles) || mobilePattern == null) {
            return Collections.emptyList();
        }

        List<String> matched = new ArrayList<String>();
        Pattern p = Pattern.compile(mobilePattern);
        for (Iterator<String> i = mobiles.iterator(); i.hasNext(); ) {
            String mobile = i.next();
            if (p.matcher(mobile).matches()) {
                if (removeIfFind) {
                    i.remove();
                }

                matched.add(mobile);
            }
        }

        return matched;
    }

    private MSPUtil() {
    }
}
