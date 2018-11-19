package com.base.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 数字范围的交集检查器，判断各数字区间是否有交集
 * 
 *
 */
public class IntersectionChecker {
	/**
	 * 数字集合
	 */
	private List<N> nums = new ArrayList<N>();

	/**
	 * 添加数字区域(闭区间，start不能大于end)
	 * 
	 * @param start
	 * @param end
	 */
	public void addRange(long start, long end) {
		if (start > end) {
			throw new IllegalArgumentException(String.format(
					"Start must be large than or equivalent to end, start=%d, end=%d", start, end));
		}

		int id = nums.size() / 2;
		nums.add(new N(id, start));
		nums.add(new N(id, end));
	}

	/**
	 * 是否有交集
	 * 
	 * @return
	 */
	public boolean hasIntersection() {
		if (nums.isEmpty()) {
			return false;
		}

		Collections.sort(nums);

		N n1 = null, n2 = null;
		for (int i = 0; i < nums.size();) {
			n1 = nums.get(i++);
			if (n2 != null && n2.num == n1.num) {
				return true;
			}

			n2 = nums.get(i++);
			if (n1.id != n2.id) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 重置
	 */
	public void reset() {
		nums.clear();
	}

	private static class N implements Comparable<N> {
		int id;
		long num;

		N(int id, long num) {
			this.id = id;
			this.num = num;
		}

		public int compareTo(N n) {
			if (num > n.num) {
				return 1;
			} else if (num < n.num) {
				return -1;
			} else {
				return id - n.id;
			}
		}
	}
}
