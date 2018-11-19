package com.base.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 集合工具类
 * 
 *
 */
public final class CollectionUtil {
	/**
	 * 是否是集合类型，为null或其他类型返回false
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isCollection(Object obj) {
		return (obj instanceof Collection);
	}

	/**
	 * 获得集合对象包含的对象数量
	 * 
	 * @param collection
	 *            集合
	 * @return 集合大小，如果collection为null，则返回0
	 */
	public static int size(Collection<?> collection) {
		return (collection == null ? 0 : collection.size());
	}

	/**
	 * 获得所有集合对象包含的对象总数量
	 * 
	 * @param collections
	 *            集合集
	 * @return 集合集大小，如果collections为null，则返回0
	 */
	public static int size(Collection<?>... collections) {
		int size = 0;
		if (collections != null) {
			for (Collection<?> c : collections) {
				if (c != null) {
					size += c.size();
				}
			}
		}

		return size;
	}

	/**
	 * 判断集合对象是否为null或空
	 * 
	 * @param collection
	 *            集合
	 * @return 是否为null或空
	 */
	public static boolean isEmpty(Collection<?> collection) {
		return (collection == null || collection.isEmpty());
	}

	/**
	 * 判断集合对象是否不为null或空
	 * 
	 * @param collection
	 *            集合
	 * @return 是否不为null或空
	 */
	public static boolean isNotEmpty(Collection<?> collection) {
		return (collection != null && !collection.isEmpty());
	}

	/**
	 * 判断集合是否包含指定对象
	 * 
	 * @param c
	 *            集合
	 * @param target
	 *            对象
	 * @return 是否包含
	 */
	public static boolean contains(Collection<?> c, Object target) {
		return (isEmpty(c) ? false : c.contains(target));
	}

	/**
	 * 判断集合是否包含任何指定对象
	 * 
	 * @param c
	 *            集合
	 * @param targets
	 *            对象数组
	 * @return 是否包含
	 */
	public static boolean containsAny(Collection<?> c, Object... targets) {
		if (isNotEmpty(c) && ArrayUtil.isNotEmpty(targets)) {
			for (Object target : targets) {
				if (contains(c, target)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * 判断集合是否包含所有指定对象
	 * 
	 * @param c
	 *            集合
	 * @param targets
	 *            对象数组
	 * @return 是否包含
	 */
	public static boolean containsAll(Collection<?> c, Object... targets) {
		if (isEmpty(c) || ArrayUtil.isEmpty(targets)) {
			return false;
		}

		for (Object target : targets) {
			if (contains(c, target) == false) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 根据指定下标从给定的集合中获得数据
	 * 
	 * @param <T>
	 *            元素类型
	 * @param c
	 *            集合
	 * @param index
	 *            下标
	 * @return 元素
	 */
	public static <T> T getElement(Collection<T> c, int index) {
		return getElement(c, index, null);
	}

	/**
	 * 根据指定下标从给定的集合中获得数据，集合为空或下标越界时返回默认元素
	 * 
	 * @param <T>
	 *            元素类型
	 * @param c
	 *            集合
	 * @param index
	 *            下标, 负数代表从最后一个元素开始倒数，负无穷大时认为下标越界
	 * @param defElement
	 *            默认元素
	 * @return 元素
	 */
	public static <T> T getElement(Collection<T> c, int index, T defElement) {
		if (isEmpty(c) || index >= c.size()) {
			return defElement;
		}

		if (index < 0) {
			index = c.size() + index;
			if (index < 0) {
				return defElement;
			}
		}

		if (c instanceof List) {
			return ((List<T>) c).get(index);

		} else {
			for (T element : c) {
				if (index-- == 0) {
					return element;
				}
			}

			// unreachable
			return defElement;
		}
	}

	/**
	 * 在根据给定下标从集合中取出最多指定数量的元素
	 * 
	 * @param <T>
	 * @param c
	 *            集合
	 * @param offset
	 *            起始下标(偏移), 负数代表从最后一个元素开始倒数，负无穷大时位置为0
	 * @param size
	 *            最大记录数，正数，当起始下标加length超过集合大小时，length等于集合大小减起始下标
	 * @return 取出的元素集合
	 */
	public static <T, C extends Collection<T>> C getElements(C c, int offset, int size) {
		if (c == null) {
			return null;

		} else if (c.isEmpty() || offset >= c.size() || size <= 0) {
			return newCollection(c);
		}

		if (offset < 0) {
			offset = Math.max(0, c.size() + offset);
		}

		if (offset + size > c.size()) {
			size = c.size() - offset;
		}

		C result = newCollection(c);
		if (c instanceof List) {
			result.addAll(((List<T>) c).subList(offset, offset + size));

		} else {
			for (int i = offset; i < offset + size; i++) {
				result.add(getElement(c, i));
			}
		}

		return result;
	}

	/**
	 * 返回包含目标集合指定范围[start, size)内元素的集合
	 * 
	 * @param <T>
	 * @param c
	 *            集合
	 * @param start
	 *            起始下标(包含), 负数代表从最后一个元素开始倒数，负无穷大时位置为0，正无穷大位置为length
	 * @return 取出的元素集合
	 */
	public static <T, C extends Collection<T>> C subCollection(C c, int start) {
		return subCollection(c, start, 0);
	}

	/**
	 * 返回包含目标集合指定范围[start, end)内元素的集合
	 * 
	 * @param <T>
	 * @param c
	 *            集合
	 * @param start
	 *            起始下标(包含), 负数代表从最后一个元素开始倒数，负无穷大和零时位置为0，正无穷大位置为length
	 * @param end
	 *            结束下标(不包含), 负数代表从最后一个元素开始倒数，负无穷大时位置为0，零和正无穷大位置为length
	 * @return 取出的元素集合
	 */
	public static <T, C extends Collection<T>> C subCollection(C c, int start, int end) {
		if (c == null) {
			return null;
		}

		if (c.size() == 0) {
			return newCollection(c);
		}

		if (start < 0) {
			start = Math.max(0, c.size() + start); // 负无穷大取0
		}

		if (end <= 0) {
			end = c.size() + end;
		}

		end = Math.min(end, c.size()); // 正无穷大取length

		if (start >= c.size() || end > c.size() || start >= end) {
			return newCollection(c);
		}

		C result = newCollection(c);
		if (c instanceof List) {
			result.addAll(((List<T>) c).subList(start, end));

		} else {
			for (int i = start; i < end; i++) {
				result.add(getElement(c, i));
			}
		}

		return result;
	}

	/**
	 * 根据给定长度将集合分割成若干子集合
	 * 
	 * @param c
	 *            集合
	 * @param length
	 *            分割长度. 如果小于等于0, 则默认为集合的长度.
	 * @return 子集合数组，包含分割后的子集合, 按下标顺序排列.
	 */
	@SuppressWarnings("unchecked")
	public static <T, C extends Collection<T>> C[] split(C c, int length) {
		if (c == null) {
			return null;

		} else if (c.isEmpty()) {
			return ArrayUtil.asArray((C) newCollection(c));
		}

		if (length <= 0) {
			length = c.size();
		}

		C[] result = ArrayUtil.newArray(c, new int[] { (int) Math.ceil((double) c.size() / length) });
		for (int ci = 0, ri = 0, riLen = length; ci < c.size(); ci += length, ri++) {
			if (c.size() - ci < length) {
				riLen = c.size() - ci;
			}

			result[ri] = getElements(c, ci, riLen);
		}

		return result;
	}

	/**
	 * 利用反射根据指定类型建立集合对象
	 * 
	 * @param reference
	 *            参考对象
	 * @return 新建的集合对象. 返回null, 如果参考对象为空或者不是集合类型或构建失败.
	 */
	@SuppressWarnings("unchecked")
	public static <C extends Collection<?>> C newCollection(Object reference) {
		Class<?> clazz = null;
		if (reference instanceof Collection<?>) {
			clazz = (Class<?>) reference.getClass();

		} else if (reference instanceof Class<?> && Collection.class.isAssignableFrom((Class<?>) reference)) {
			clazz = (Class<?>) reference;

			if (clazz.equals(List.class)) {
				clazz = (Class<?>) ArrayList.class;

			} else if (clazz.equals(Set.class)) {
				clazz = (Class<?>) LinkedHashSet.class;
			}
		}

		if (clazz != null) {
			try {
				return (C) clazz.newInstance();
			} catch (Exception e) {
			}
		}

		return null;
	}

	/**
	 * 如果元素不为null，则添加至指定集合
	 * 
	 * @param c
	 * @param obj
	 * @return
	 */
	public static <T> boolean addIfNotNull(Collection<T> c, T obj) {
		return ((obj == null || c == null) ? false : c.add(obj));
	}

	/**
	 * 如果c2不为empty，则将c2所有元素添加至指定集合
	 * 
	 * @param c1
	 * @param c2
	 * @return
	 */
	public static <T> boolean addAllIfNotEmpty(Collection<T> c1, Collection<T> c2) {
		return (c1 == null || isEmpty(c2) ? false : c1.addAll(c2));
	}

	/**
	 * 如果元素为集合或数组类型，则会将内部元素逐个添加至集合c，否则直接添加到集合c
	 * 
	 * @param c
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> boolean addEach(Collection<T> c, Object obj) {
		if (c == null) {
			return false;
		}

		if (obj instanceof Collection) {
			// 集合类型
			boolean succ = false;
			for (T o : (Collection<T>) obj) {
				succ |= c.add(o);
			}

			return succ;

		} else if (obj instanceof Object[]) {
			// 数组类型
			boolean succ = false;
			for (T val : (T[]) obj) {
				succ |= c.add(val);
			}

			return succ;

		} else {
			return c.add((T) obj);
		}
	}

	/**
	 * 如果元素为集合或数组类型，则会将内部元素逐个添加至集合c，否则如果元素不为null则直接添加到集合c
	 * 
	 * @param c
	 * @param obj
	 * @return
	 */
	public static <T> boolean addEachIfNotNull(Collection<T> c, Object obj) {
		return ((obj == null || c == null) ? false : addEach(c, obj));
	}

	/**
	 * 过滤集合，保留满足指定条件的元素
	 * 
	 * @param c
	 * @param filter
	 */
	public static <E> void filter(Collection<E> c, Filter<E> filter) {
		if (isEmpty(c) || filter == null) {
			return;
		}

		for (Iterator<E> i = c.iterator(); i.hasNext();) {
			if (filter.retain(i.next()) == false) {
				i.remove();
			}
		}
	}

	/**
	 * 默认无参构造函数
	 */
	private CollectionUtil() {
	}

	/**
	 * 过滤器接口
	 * 
	 *
	 */
	public interface Filter<E> {
		/**
		 * 过滤集合内容，返回是否保留该元素
		 * 
		 * @param element
		 * @return true 表示保留，false表示移除
		 */
		boolean retain(E element);
	}
}
