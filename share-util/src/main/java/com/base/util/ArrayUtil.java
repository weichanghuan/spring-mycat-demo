package com.base.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 数组工具类
 */
public final class ArrayUtil {
    /**
     * 是否是数组类型，为null或其他类型返回false
     *
     * @param obj
     * @return
     */
    public static boolean isArray(Object obj) {
        if (obj == null) {
            return false;
        }

        return obj.getClass().isArray();
    }

    /**
     * 获得数组对象包含的元素数量
     *
     * @param array 数组
     * @return 数组大小，如果array为null，则返回0
     */
    public static int length(Object[] array) {
        return (array == null ? 0 : array.length);
    }

    /**
     * 获得数组对象包含的元素数量
     *
     * @param array 数组
     * @return 数组大小，如果array为null，则返回0
     */
    public static int length(int[] array) {
        return (array == null ? 0 : array.length);
    }

    /**
     * 获得所有数组对象包含的元素总数量
     *
     * @param arrays 数组集
     * @return 数组集大小，如果arrays为null，则返回0
     */
    public static int length(Object[]... arrays) {
        int len = 0;
        if (arrays != null) {
            for (Object[] array : arrays) {
                if (array != null) {
                    len += array.length;
                }
            }
        }

        return len;
    }

    /**
     * 获得所有数组对象包含的元素总数量
     *
     * @param arrays 数组集
     * @return 数组集大小，如果arrays为null，则返回0
     */
    public static int length(int[]... arrays) {
        int len = 0;
        if (arrays != null) {
            for (int[] array : arrays) {
                if (array != null) {
                    len += array.length;
                }
            }
        }

        return len;
    }

    /**
     * 判断数组对象是否为null或空
     *
     * @param array 数组
     * @return 是否为null或空
     */
    public static boolean isEmpty(Object[] array) {
        return (array == null || array.length == 0);
    }

    /**
     * 判断数组对象是否为null或空
     *
     * @param array 数组
     * @return 是否为null或空
     */
    public static boolean isEmpty(int[] array) {
        return (array == null || array.length == 0);
    }

    /**
     * 判断数组对象是否不为null或空
     *
     * @param array 数组
     * @return 是否不为null或空
     */
    public static boolean isNotEmpty(Object[] array) {
        return (array != null && array.length > 0);
    }

    /**
     * 判断数组对象是否不为null或空
     *
     * @param array 数组
     * @return 是否不为null或空
     */
    public static boolean isNotEmpty(int[] array) {
        return (array != null && array.length > 0);
    }

    /**
     * 判断数组是否包含指定对象
     *
     * @param array  数组
     * @param target 对象
     * @return 是否包含
     */
    public static boolean contains(Object[] array, Object target) {
        if (isNotEmpty(array)) {
            for (Object obj : array) {
                if ((target == null && obj == null) || (target != null && target.equals(obj))) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 判断数组是否包含指定整数
     *
     * @param array  数组
     * @param target 目标整数
     * @return 是否包含
     */
    public static boolean contains(int[] array, int target) {
        if (isNotEmpty(array)) {
            for (int obj : array) {
                if (target == obj) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 判断数组是否包含指定对象集中任何一个对象
     *
     * @param array   数组
     * @param targets 对象集
     * @return 是否包含
     */
    public static boolean containsAny(Object[] array, Object... targets) {
        if (isNotEmpty(array) && isNotEmpty(targets)) {
            for (Object target : targets) {
                if (contains(array, target)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 判断数组是否包含指定对象集中任何一个整数
     *
     * @param array   数组
     * @param targets 整数集
     * @return 是否包含
     */
    public static boolean containsAny(int[] array, int... targets) {
        if (isNotEmpty(array) && isNotEmpty(targets)) {
            for (int target : targets) {
                if (contains(array, target)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 判断数组是否包含指定对象集中所有对象
     *
     * @param array   数组
     * @param targets 对象集
     * @return 是否包含
     */
    public static boolean containsAll(Object[] array, Object... targets) {
        if (isEmpty(array) || isEmpty(targets)) {
            return false;
        }

        for (Object target : targets) {
            if (contains(array, target) == false) {
                return false;
            }
        }

        return true;
    }

    /**
     * 判断数组是否包含指定整数集中所有整数
     *
     * @param array   数组
     * @param targets 整数集
     * @return 是否包含
     */
    public static boolean containsAll(int[] array, int... targets) {
        if (isEmpty(array) || isEmpty(targets)) {
            return false;
        }

        for (int target : targets) {
            if (contains(array, target) == false) {
                return false;
            }
        }

        return true;
    }

    /**
     * 根据指定下标从给定的数组中获得越界
     *
     * @param <T>   元素类型
     * @param array 数组
     * @param index 下标, 负数代表从最后一个元素开始倒数，负无穷大时认为下标越界
     * @return 元素
     */
    public static <T> T getElement(T[] array, int index) {
        return getElement(array, index, null);
    }

    /**
     * 根据指定下标从给定的数组中获得越界
     *
     * @param array 数组
     * @param index 下标, 负数代表从最后一个元素开始倒数，负无穷大时认为下标越界
     * @return 整数
     */
    public static int getElement(int[] array, int index) {
        return getElement(array, index, 0);
    }

    /**
     * 根据指定下标从给定的数组中获得数据，数组为空或下标越界时返回默认元素
     *
     * @param <T>        元素类型
     * @param array      数组
     * @param index      下标, 负数代表从最后一个元素开始倒数，负无穷大时认为下标越界
     * @param defElement 默认元素
     * @return 元素
     */
    public static <T> T getElement(T[] array, int index, T defElement) {
        if (isEmpty(array) || index >= array.length) {
            return defElement;
        }

        if (index < 0) {
            index = array.length + index;
        }

        return (index >= 0 ? array[index] : defElement);
    }

    /**
     * 根据指定下标从给定的数组中获得整数，数组为空或下标越界时返回默认整数
     *
     * @param array      数组
     * @param index      下标, 负数代表从最后一个元素开始倒数，负无穷大时认为下标越界
     * @param defElement 默认整数
     * @return 整数
     */
    public static int getElement(int[] array, int index, int defElement) {
        if (isEmpty(array) || index >= array.length) {
            return defElement;
        }

        if (index < 0) {
            index = array.length + index;
        }

        return (index >= 0 ? array[index] : defElement);
    }

    /**
     * 在根据给定下标从数组中取出最多指定数量的元素
     *
     * @param <T>
     * @param array  数组
     * @param offset 起始下标(偏移), 负数代表从最后一个元素开始倒数，负无穷大时位置为0
     * @param length 最大记录数，正数，当起始下标加length超过数组长度时，length等于数组长度减起始下标
     * @return 取出的元素数组
     */
    public static <T> T[] getElements(T[] array, int offset, int length) {
        if (array == null) {
            return null;

        } else if (array.length == 0 || offset >= array.length || length <= 0) {
            return newArray(array, 0);
        }

        if (offset < 0) {
            offset = Math.max(0, array.length + offset);
        }

        if (offset + length > array.length) {
            length = array.length - offset;
        }

        T[] result = newArray(array, length);
        System.arraycopy(array, offset, (Object[]) result, 0, length);

        return result;
    }

    /**
     * 在根据给定下标从数组中取出最多指定数量的整数
     *
     * @param array  数组
     * @param offset 起始下标(偏移), 负数代表从最后一个元素开始倒数，负无穷大时位置为0
     * @param length 最大记录数，正数，当起始下标加length超过数组长度时，length等于数组长度减起始下标
     * @return 取出的整数数组
     */
    public static int[] getElements(int[] array, int offset, int length) {
        if (array == null) {
            return null;

        } else if (array.length == 0 || offset >= array.length || length <= 0) {
            return new int[0];
        }

        if (offset < 0) {
            offset = Math.max(0, array.length + offset);
        }

        if (offset + length > array.length) {
            length = array.length - offset;
        }

        int[] result = new int[length];
        System.arraycopy(array, offset, result, 0, length);

        return result;
    }

    /**
     * 返回包含目标数组指定范围[start, length)内元素的数组
     *
     * @param <T>
     * @param array 数组
     * @param start 起始下标(包含), 负数代表从最后一个元素开始倒数，负无穷大时位置为0，正无穷大位置为length
     * @return 取出的元素数组
     */
    public static <T> T[] subArray(T[] array, int start) {
        return subArray(array, start, 0);
    }

    /**
     * 返回包含目标数组指定范围[start, length)内整数的数组
     *
     * @param array 数组
     * @param start 起始下标(包含), 负数代表从最后一个元素开始倒数，负无穷大时位置为0，正无穷大位置为length
     * @return 取出的整数数组
     */
    public static int[] subArray(int[] array, int start) {
        return subArray(array, start, 0);
    }

    /**
     * 返回包含目标数组指定范围[start, end)内元素的数组
     *
     * @param <T>
     * @param array 数组
     * @param start 起始下标(包含), 负数代表从最后一个元素开始倒数，负无穷大和零时位置为0，正无穷大时位置为length
     * @param end   结束下标(不包含), 负数代表从最后一个元素开始倒数，负无穷大时位置为0，零和正无穷大位置为length
     * @return 取出的元素数组
     */
    public static <T> T[] subArray(T[] array, int start, int end) {
        if (array == null) {
            return null;
        }

        if (array.length == 0) {
            return newArray(array, 0);
        }

        if (start < 0) {
            start = Math.max(0, array.length + start); // 负无穷大取0
        }

        if (end <= 0) {
            end = array.length + end;
        }

        end = Math.min(end, array.length); // 正无穷大取length

        if (start >= array.length || end > array.length || start >= end) {
            return newArray(array, 0);
        }

        T[] result = newArray(array, end - start);
        System.arraycopy(array, start, result, 0, end - start);

        return result;
    }

    /**
     * 返回包含目标数组指定范围[start, end)内整数的数组
     *
     * @param array 数组
     * @param start 起始下标(包含), 负数代表从最后一个元素开始倒数，负无穷大和零时位置为0，正无穷大时位置为length
     * @param end   结束下标(不包含), 负数代表从最后一个元素开始倒数，负无穷大时位置为0，零和正无穷大位置为length
     * @return 取出的整数数组
     */
    public static int[] subArray(int[] array, int start, int end) {
        if (array == null) {
            return null;
        }

        if (array.length == 0) {
            return new int[0];
        }

        if (start < 0) {
            start = Math.max(0, array.length + start); // 负无穷大取0
        }

        if (end <= 0) {
            end = array.length + end;
        }

        end = Math.min(end, array.length); // 正无穷大取length

        if (start >= array.length || end > array.length || start >= end) {
            return new int[0];
        }

        int[] result = new int[end - start];
        System.arraycopy(array, start, result, 0, end - start);

        return result;
    }

    /**
     * 根据给定长度将数组分割成若干子数组
     *
     * @param array  数组
     * @param length 分割长度. 如果小于等于0, 则默认为array数组的长度.
     * @return 二维数组，包含分割后的子数组(源数组的复制), 按下标顺序排列.
     */
    public static <T> T[][] split(T[] array, int length) {
        return split(array, length, false);
    }

    /**
     * 根据给定长度将数组分割成若干子数组
     *
     * @param array  数组
     * @param length 分割长度. 如果小于等于0, 则默认为array数组的长度.
     * @return 二维数组，包含分割后的子数组(源数组的复制), 按下标顺序排列.
     */
    public static int[][] split(int[] array, int length) {
        return split(array, length, false);
    }

    /**
     * 根据给定长度将数组分割成若干子数组
     *
     * @param array  数组
     * @param length 分割长度. 如果小于等于0, 则默认为array数组的长度.
     * @return 二维数组，包含分割后的子数组(源数组的复制), 按下标顺序排列.
     */
    public static byte[][] split(byte[] array, int length) {
        return split(array, length, false);
    }

    /**
     * 根据给定长度将数组分割成若干子数组
     *
     * @param array            数组
     * @param length           分割长度. 如果小于等于0, 则默认为array数组的长度.
     * @param isKeepSameLength 是否保持每个子数组的长度一致. 如果为true, 最后一个子数组的长度等于length, 多出空位填充null.
     *                         如果为false, 则最后一个子数组的长度小于等于length.
     * @return 二维数组，包含分割后的子数组(源数组的复制), 按下标顺序排列.
     */
    public static <T> T[][] split(T[] array, int length, boolean isKeepSameLength) {
        if (array == null) {
            return null;

        } else if (array.length == 0) {
            return newArray(array, new int[]{1, 0});
        }

        if (length <= 0) {
            length = array.length;
        }

        T[][] result = newArray(array, new int[]{(int) Math.ceil((double) array.length / length), 0});
        for (int ai = 0, ri = 0, riLen = length; ai < array.length; ai += length, ri++) {
            if (array.length - ai < length) {
                riLen = array.length - ai;
            }

            result[ri] = newArray(array, isKeepSameLength ? length : riLen);
            System.arraycopy(array, ai, (Object[]) result[ri], 0, riLen);
        }

        return result;
    }

    /**
     * 根据给定长度将数组分割成若干子数组
     *
     * @param array            数组
     * @param length           分割长度. 如果小于等于0, 则默认为array数组的长度.
     * @param isKeepSameLength 是否保持每个子数组的长度一致. 如果为true, 最后一个子数组的长度等于length, 多出空位填充null.
     *                         如果为false, 则最后一个子数组的长度小于等于length.
     * @return 二维数组，包含分割后的子数组(源数组的复制), 按下标顺序排列.
     */
    public static int[][] split(int[] array, int length, boolean isKeepSameLength) {
        if (array == null) {
            return null;

        } else if (array.length == 0) {
            return new int[][]{new int[0]};
        }

        if (length <= 0) {
            length = array.length;
        }

        int[][] result = new int[(int) Math.ceil((double) array.length / length)][];
        for (int ai = 0, ri = 0, riLen = length; ai < array.length; ai += length, ri++) {
            if (array.length - ai < length) {
                riLen = array.length - ai;
            }

            result[ri] = newArray(array, isKeepSameLength ? length : riLen);
            System.arraycopy(array, ai, result[ri], 0, riLen);
        }

        return result;
    }

    /**
     * 根据给定长度将数组分割成若干子数组
     *
     * @param array            数组
     * @param length           分割长度. 如果小于等于0, 则默认为array数组的长度.
     * @param isKeepSameLength 是否保持每个子数组的长度一致. 如果为true, 最后一个子数组的长度等于length, 多出空位填充null.
     *                         如果为false, 则最后一个子数组的长度小于等于length.
     * @return 二维数组，包含分割后的子数组(源数组的复制), 按下标顺序排列.
     */
    public static byte[][] split(byte[] array, int length, boolean isKeepSameLength) {
        if (array == null) {
            return null;

        } else if (array.length == 0) {
            return new byte[][]{new byte[0]};
        }

        if (length <= 0) {
            length = array.length;
        }

        byte[][] result = new byte[(int) Math.ceil((double) array.length / length)][];
        for (int ai = 0, ri = 0, riLen = length; ai < array.length; ai += length, ri++) {
            if (array.length - ai < length) {
                riLen = array.length - ai;
            }

            result[ri] = newArray(array, isKeepSameLength ? length : riLen);
            System.arraycopy(array, ai, result[ri], 0, riLen);
        }

        return result;
    }

    /**
     * 将指定数组集连接成一个数组
     *
     * @param arrays
     * @return
     */
    public static Object[] concat(Object[]... arrays) {
        if (arrays == null) {
            return null;
        }

        Object[] newArray = new Object[length(arrays)];
        for (int i = 0, offset = 0; i < arrays.length; i++) {
            if (arrays[i] != null) {
                System.arraycopy(arrays[i], 0, newArray, offset, arrays[i].length);
                offset += arrays[i].length;
            }
        }

        return newArray;
    }

    /**
     * 将指定数组集连接成一个数组
     *
     * @param arrays
     * @return
     */
    public static int[] concat(int[]... arrays) {
        if (arrays == null) {
            return null;
        }

        int[] newArray = new int[length(arrays)];
        for (int i = 0, offset = 0; i < arrays.length; i++) {
            if (arrays[i] != null) {
                System.arraycopy(arrays[i], 0, newArray, offset, arrays[i].length);
                offset += arrays[i].length;
            }
        }

        return newArray;
    }

    /**
     * 利用反射构建一维数组
     *
     * @param reference 参考对象
     * @param dimension 维度.
     * @return 数组对象. 如果参考对象为null或创建发生异常则返回null.
     */
    @SuppressWarnings("unchecked")
    public static <T> T newArray(Object reference, int dimension) {
        return (T) newArray(reference, new int[]{dimension});
    }

    /**
     * 利用反射构建数组
     *
     * @param reference  参考对象
     * @param dimensions 维度. 例如 new int[]{5}是长度为5的一维数组, new int[]{5, 5}是二维数组
     * @return 数组对象. 如果参考对象为null或创建发生异常则返回null.
     */
    @SuppressWarnings("unchecked")
    public static <T> T newArray(Object reference, int[] dimensions) {
        Class<?> clazz = null;
        if (reference instanceof Class<?>) {
            clazz = (Class<?>) reference;

        } else if (reference != null) {
            Class<?> refType = reference.getClass();
            if (refType.isArray()) {
                // 如果reference为数组, 则取其元素类型
                clazz = refType.getComponentType();

            } else {
                clazz = refType;
            }
        }

        if (clazz != null) {
            try {
                return (T) Array.newInstance(clazz, dimensions);
            } catch (Exception e) {
            }
        }

        return null;
    }

    /**
     * 将多个对象打造成数组
     *
     * @param objs 零到多个对象
     * @return 数组. 如果objs是无指定类型的null, 则返回null.
     */
    public static <T> T[] asArray(T... objs) {
        return objs;
    }

    /**
     * 创建列表对象
     *
     * @param objs 多个对象
     * @return 列表对象
     */
    public static <T> List<T> asList(T... objs) {
        return asList(false, objs);
    }

    /**
     * 创建列表对象，并忽略所有为null元素
     *
     * @param objs 多个对象
     * @return 列表对象，如果objs全部为null，则返回null
     */
    public static <T> List<T> asListNoNull(T... objs) {
        return asList(true, objs);
    }

    /**
     * 创建列表对象
     *
     * @param ignoreNull 是否忽略null
     * @param objs       多个对象
     * @return 列表对象，如果ignoreNull为true且objs全部为null，则返回null
     */
    private static <T> List<T> asList(boolean ignoreNull, T... objs) {
        if (objs == null) {
            return null;
        }

        List<T> list = new ArrayList<T>(objs.length);
        for (T obj : objs) {
            if (ignoreNull == false || obj != null) {
                list.add(obj);
            }
        }

        return (ignoreNull && list.isEmpty() ? null : list);
    }

    /**
     * 创建集合对象
     *
     * @param objs 多个对象
     * @return 集合对象
     */
    public static <T> Set<T> asSet(T... objs) {
        return asSet(false, objs);
    }

    /**
     * 创建集合对象，忽略所有为null元素
     *
     * @param objs 多个对象
     * @return 集合对象，如果objs全部为null，则返回null
     */
    public static <T> Set<T> asSetNoNull(T... objs) {
        return asSet(true, objs);
    }

    /**
     * 创建集合对象
     *
     * @param ignoreNull 是否忽略为null元素
     * @param objs       多个对象
     * @return 集合对象，如果ignoreNull为true且objs全部为null，则返回null
     */
    private static <T> Set<T> asSet(boolean ignoreNull, T... objs) {
        if (objs == null) {
            return null;
        }

        Set<T> set = new LinkedHashSet<T>(objs.length);
        for (T obj : objs) {
            if (ignoreNull == false || obj != null) {
                set.add(obj);
            }
        }

        return (ignoreNull && set.isEmpty() ? null : set);
    }

    /**
     * 尝试将二维数组转换成Map, 其包含的一维数组第一个元素为key, 第二个元素为value. 如果包含的一维数组为空或元素少于2个,
     * 则被会被忽略.
     *
     * @param objs 二维数组对象. 如果包含的一维数组为空或元素少于2个, 则被会被忽略
     * @return 键值对
     */
    public static <T> Map<T, T> asMap(T[][] objs) {
        return asMultiTypeMap(objs);
    }

    /**
     * 尝试将二维数组转换成Map, 其包含的一维数组第一个元素为key, 第二个元素为value. 如果包含的一维数组为空或元素少于2个,
     * 则被会被忽略. T类型必须是K, V类型的子类型, 不然在对Map进行操作时可能会出现运行时错误.
     *
     * @param objs 二维数组对象. 如果包含的一维数组为空或元素少于2个, 则被会被忽略.
     * @return 键值对
     */
    @SuppressWarnings("unchecked")
    public static <K, V, T> Map<K, V> asMultiTypeMap(T[][] objs) {
        if (isEmpty(objs)) {
            return Collections.emptyMap();
        }

        Map<K, V> map = new LinkedHashMap<K, V>(objs.length);
        for (T[] obj : objs) {
            if (obj != null && obj.length >= 2) {
                map.put((K) obj[0], (V) obj[1]);
            }
        }

        return map;
    }

    /**
     * 尝试将多个传入参数转换成Map, 奇数位的元素为key, 偶数位的元素为value. 如果为奇数个对象,
     * 则会自动添加一个null作为最后一个对象.
     *
     * @param objs 包含任意数量对象. 如果为奇数个对象, 则会自动添加一个null作为最后一个对象.
     * @return 键值对
     */
    public static <T> Map<T, T> asMap(T... objs) {
        return asMultiTypeMap(objs);
    }

    /**
     * 尝试将多个传入参数转换成Map, 奇数位的元素为key, 偶数位的元素为value. 如果为奇数个对象,
     * 则会自动添加一个null作为最后一个对象. T类型必须是K, V类型的子类型, 不然在对Map进行操作时可能会出现运行时错误.
     *
     * @param objs 包含任意数量对象. 如果为奇数个对象, 则会自动添加一个null作为最后一个对象.
     * @return 键值对
     */
    @SuppressWarnings("unchecked")
    public static <K, V, T> Map<K, V> asMultiTypeMap(T... objs) {
        if (isEmpty(objs)) {
            return Collections.emptyMap();
        }

        Map<K, V> map = new LinkedHashMap<K, V>(objs.length / 2 + 1);
        for (int i = 0; i < objs.length; ) {
            map.put((K) objs[i++], (V) getElement(objs, i++));
        }

        return map;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] toArray(Collection<T> c, Class<?> componentType) {
        T[] array = (T[]) Array.newInstance(componentType, c.size());
        return c.toArray(array);
    }

    /**
     * 获得第一个不为null的对象
     *
     * @param objs
     * @return
     */
    public static <T> T firstNotNull(T... objs) {
        if (isNotEmpty(objs)) {
            for (T obj : objs) {
                if (obj != null) {
                    return obj;
                }
            }
        }

        return null;
    }

    /**
     * 默认无参构造函数
     */
    private ArrayUtil() {
    }
}
