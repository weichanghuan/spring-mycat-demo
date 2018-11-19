package com.base.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Object工具类
 * 
 *
 */
public final class ObjectUtil {
	/**
	 * 默认值映射
	 */
	private static final Map<Class<?>, Object> DEFAULTS = new HashMap<Class<?>, Object>();

	static {
		// 保存基础类型默认值，其他类型均为null
		DEFAULTS.put(boolean.class, false);
		DEFAULTS.put(char.class, '\u0000');
		DEFAULTS.put(byte.class, (byte) 0);
		DEFAULTS.put(short.class, (short) 0);
		DEFAULTS.put(int.class, 0);
		DEFAULTS.put(long.class, 0L);
		DEFAULTS.put(float.class, 0.0f);
		DEFAULTS.put(double.class, 0.0d);
	}

	/**
	 * 获取类型对应默认值
	 * 
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T defaultValue(Class<T> type) {
		return (T) DEFAULTS.get(type);
	}

	/**
	 * 比较两个对象的对应属性名的属性值是否相同(非宽松模式)，不指定属性名则比较所有属性。
	 * 如果任一对象为空，或比较全属性且属性数量不一致，或指定属性不存在或属性值不一致，则返回不相同，不然返回相同
	 * 
	 * @param obj1
	 *            对象1
	 * @param obj2
	 *            对象2
	 * @param propNames
	 *            属性名集合
	 * @return 是否相同
	 */
	public static boolean isSame(Object obj1, Object obj2, String... propNames) {
		return isSame(obj1, obj2, false, propNames);
	}

	/**
	 * 比较两个对象的对应属性名的属性值是否相同，不指定属性名则比较所有属性。
	 * 宽松模式：如果两个对象不全为空，或指定属性值不一致，则返回不相同，否则返回相同(允许属性不存在或不一致)。
	 * 非宽松模式：如果任一对象为空，或比较全属性且属性数量不一致，或指定属性不存在或属性值不一致，则返回不相同，否则返回相同。
	 * 
	 * @param obj1
	 *            对象1
	 * @param obj2
	 *            对象2
	 * @param isLenient
	 *            是否为宽松模式
	 * @param propNames
	 *            属性名集合
	 * @return 是否相同
	 */
	public static boolean isSame(Object obj1, Object obj2, boolean isLenient, String... propNames) {
		try {
			return isSameImpl(obj1, obj2, isLenient, propNames);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 比较两个对象的对应属性名的属性值是否相同，不指定属性名则比较所有属性。
	 * 宽松模式：如果两个对象不全为空，或指定属性值不一致，则返回不相同，否则返回相同(允许属性不存在或不一致)。
	 * 非宽松模式：如果任一对象为空，或比较全属性且属性数量不一致，或指定属性不存在或属性值不一致，则返回不相同，否则返回相同。
	 * 
	 * @param obj1
	 *            对象1
	 * @param obj2
	 *            对象2
	 * @param isLenient
	 *            是否为宽松模式
	 * @param propNames
	 *            属性名集合
	 * @return 是否相同
	 */
	private static boolean isSameImpl(Object obj1, Object obj2, boolean isLenient, String... propNames)
			throws Exception {
		if (obj1 == obj2) {
			/*
			 * 宽松模式下，引用相同则相同
			 * 非宽松模式下，如果对象引用相同且都不为空且比较所有属性，则返回相同，否则即使引用相同且不为空也必须验证属性是否存在
			 */
			if (isLenient || (obj1 != null && ArrayUtil.isEmpty(propNames))) {
				return true;
			}
		}

		// 任意一个为null则不相同
		if (obj1 == null || obj2 == null) {
			return false;
		}

		// 打造对象1的属性名与属性表述信息的映射关系
		Map<String, PropertyDescriptor> propDefMap1 = BeanUtil.getPropertyDescriptors(obj1.getClass());

		// 如果对象2与对象1的类型相同，则使用相同的映射关系
		Map<String, PropertyDescriptor> propDefMap2 = propDefMap1;
		if (obj1.getClass() != obj2.getClass()) {
			// 不同则解析
			propDefMap2 = BeanUtil.getPropertyDescriptors(obj2.getClass());
		}

		// 如果未指定属性名，则比较所有属性(选取任何一个对象的属性映射作为参考)
		if (ArrayUtil.isEmpty(propNames)) {
			// 比较所有属性时，如果是非宽松模式则属性数量必须一致
			if (!isLenient && (propDefMap1.size() != propDefMap2.size())) {
				return false;
			}

			propNames = propDefMap1.keySet().toArray(new String[propDefMap1.size()]);
		}

		// 遍历所有属性，比较属性值
		for (String propName : propNames) {
			PropertyDescriptor pd1 = propDefMap1.get(propName);
			PropertyDescriptor pd2 = propDefMap2.get(propName);

			if (pd1 == null || pd1.getReadMethod() == null || pd2 == null || pd2.getReadMethod() == null) {
				// 宽松模式则继续判断下一个字段
				if (isLenient) {
					continue;
				}

				return false;
			}

			// 如果不一致则返回不相同
			if (isNotEqual(pd1.getReadMethod().invoke(obj1), pd2.getReadMethod().invoke(obj2))) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 判断两个对象是否不相等(equals方法)，如果全部为null，则根据返回isEqualIfAllNull值
	 * 
	 * @param obj1
	 *            对象1
	 * @param obj2
	 *            对象2
	 * @param isEqualIfAllNull
	 *            全部为null是否认为是相等
	 * @return 是否不相等
	 */
	public static boolean isNotEqual(Object obj1, Object obj2, boolean isEqualIfAllNull) {
		if (obj1 == null || obj2 == null) {
			return (obj1 == obj2 ? !isEqualIfAllNull : true);
		}

		return !obj1.equals(obj2);
	}

	/**
	 * 判断两个对象是否不相等(equals方法)，如果全部为null，则返回false。
	 * 
	 * @param obj1
	 *            对象1
	 * @param obj2
	 *            对象2
	 * @return 是否不相等
	 */
	public static boolean isNotEqual(Object obj1, Object obj2) {
		return isNotEqual(obj1, obj2, true);
	}

	/**
	 * 判断两个对象是否相等(equals方法)，如果全部为null，则根据返回isEqualIfAllNull值
	 * 
	 * @param obj1
	 *            对象1
	 * @param obj2
	 *            对象2
	 * @param isEqualIfAllNull
	 *            全部为null是否认为是相等
	 * @return 是否相等
	 */
	public static boolean isEqual(Object obj1, Object obj2, boolean isEqualIfAllNull) {
		if (obj1 == null || obj2 == null) {
			return (obj1 == obj2 ? isEqualIfAllNull : false);
		}

		return obj1.equals(obj2);
	}

	/**
	 * 判断两个对象是否相等(equals方法)，如果全部为null，则返回true。
	 * 
	 * @param obj1
	 *            对象1
	 * @param obj2
	 *            对象2
	 * @return 是否相等
	 */
	public static boolean isEqual(Object obj1, Object obj2) {
		return isEqual(obj1, obj2, true);
	}

	/**
	 * 判断源对象是否与目标对象集合中任何一个对象相同
	 * 
	 * @param obj
	 *            源对象
	 * @param targets
	 *            对象集合
	 * @return 是否相等
	 */
	public static boolean isEqualAny(Object obj, Object... targets) {
		if (ArrayUtil.isNotEmpty(targets)) {
			for (Object target : targets) {
				if (isEqual(obj, target)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * 判断源对象是否与目标对象集合中所有对象相同
	 * 
	 * @param obj
	 *            源对象
	 * @param targets
	 *            对象集合
	 * @return 是否相等
	 */
	public static boolean isEqualAll(Object obj, Object... targets) {
		if (ArrayUtil.isEmpty(targets)) {
			return false;
		}

		for (Object target : targets) {
			if (isEqual(obj, target) == false) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 调用指定对象的toString方法，如果对象为null则返回null
	 * 
	 * @param obj
	 *            对象
	 * @return toString字符串
	 */
	public static String toString(Object obj) {
		return (obj != null ? obj.toString() : null);
	}

	/**
	 * 只取对象中的某个属性 返回集合
	 * 
	 * @param ts
	 * @param keyProp
	 * @return
	 */
	public static <T> List<T> getPropList(List<?> ts, String keyProp) {
		List<T> propList = new ArrayList<T>();

		Method method = null;
		for (Object object : ts) {
			if (method == null) {
				method = BeanUtil.getReadMethod(object.getClass(), keyProp);
			}

			T value = BeanUtil.invokeMethodQuietly(object, method);
			propList.add(value);
		}
		return propList;
	}

	/**
	 * 只取对象中的某个属性 返回集合
	 * 
	 * @param ts
	 * @param keyProp
	 * @return
	 */
	public static <T> Set<T> getPropSet(List<?> ts, String keyProp) {
		Set<T> propList = new LinkedHashSet<T>();

		Method method = null;
		for (Object object : ts) {
			if (method == null) {
				method = BeanUtil.getReadMethod(object.getClass(), keyProp);
			}

			T value = BeanUtil.invokeMethodQuietly(object, method);
			propList.add(value);
		}
		return propList;
	}

	/**
	 * 将list转化为map
	 * 
	 * @param ts
	 * @param keyProp
	 * @param keyValueCls
	 *            key的类型
	 * @return
	 */
	public static <K, T> Map<K, T> toMap(List<T> ts, String keyProp, Class<K> keyValueCls) {
		Map<K, T> map = new LinkedHashMap<K, T>();
		Method method = null;
		for (T t : ts) {
			if (method == null) {
				method = BeanUtil.getReadMethod(t.getClass(), keyProp);
			}

			K key = BeanUtil.invokeMethodQuietly(t, method);
			map.put(key, t);
		}
		return map;
	}

	/**
	 * 默认key是string类型
	 * 
	 * @param ts
	 * @param keyProp
	 * @return
	 */
	public static <T> Map<String, T> toMap(List<T> ts, String keyProp) {
		Map<String, T> map = new LinkedHashMap<String, T>();
		Method method = null;
		for (T t : ts) {
			if (method == null) {
				method = BeanUtil.getReadMethod(t.getClass(), keyProp);
			}
			String key = (String) BeanUtil.getValue(method, t);
			map.put(key, t);
		}
		return map;
	}

	/**
	 * 将list转化为map 值是list
	 * 
	 * @param ts
	 * @param keyProp
	 * @param keyValueCls
	 * @return
	 */
	public static <K, T> Map<K, List<T>> toMapWithListValue(List<T> ts, String keyProp, Class<K> keyValueCls) {
		Map<K, List<T>> map = new LinkedHashMap<K, List<T>>();
		Method method = null;
		for (T t : ts) {
			if (method == null) {
				method = BeanUtil.getReadMethod(t.getClass(), keyProp);
			}

			K key = BeanUtil.invokeMethodQuietly(t, method);
			List<T> list = map.get(key);
			if (list == null) {
				list = new ArrayList<T>();
				map.put(key, list);
			}
			list.add(t);
		}
		return map;
	}

	private ObjectUtil() {
	}
}
