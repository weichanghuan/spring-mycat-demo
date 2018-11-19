package com.base.util;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

/**
 * 秒表测试类
 * 
 *
 */
public class StopWatchTest {

	@Test
	public void test() {
		StopWatch stopWatch = new StopWatch();
		Assert.assertEquals(-1, stopWatch.getStartTime());
		Assert.assertEquals(-1, stopWatch.getStopTime());
		Assert.assertEquals(-1, stopWatch.getLastMarkTime());

		// 1
		int i = 10;
		while (i-- > 0) {
			Assert.assertFalse(stopWatch.isRunning());

			long startTime = stopWatch.start();
			Assert.assertTrue(System.currentTimeMillis() - startTime <= 5);
			Assert.assertEquals(startTime, stopWatch.getStartTime());
			Assert.assertTrue(stopWatch.isRunning());

			randomSleep();

			long markDuration = stopWatch.mark();
			long markTime1 = stopWatch.getLastMarkTime();
			Assert.assertTrue(System.currentTimeMillis() - markTime1 <= 5);
			Assert.assertEquals(markDuration, markTime1 - startTime);

			randomSleep();

			markDuration = stopWatch.mark();
			long markTime2 = stopWatch.getLastMarkTime();
			Assert.assertTrue(System.currentTimeMillis() - markTime2 <= 5);
			Assert.assertEquals(markDuration, markTime2 - markTime1);
			
			randomSleep();
			
			long runTime = stopWatch.getRunTime();
			Assert.assertTrue(System.currentTimeMillis() - startTime - runTime <= 5);

			randomSleep();

			long totalDuration = stopWatch.stop();
			long stopTime = stopWatch.getStopTime();
			Assert.assertTrue(System.currentTimeMillis() - stopTime <= 5);
			Assert.assertEquals(totalDuration, stopTime - startTime);
			Assert.assertFalse(stopWatch.isRunning());
		}

		// 2
		try {
			stopWatch.mark(); // stopped
			Assert.fail("Expect exception");
		} catch (Exception e) {
		}

		try {
			stopWatch.stop(); // stopped
			Assert.fail("Expect exception");
		} catch (Exception e) {
		}

		stopWatch = StopWatch.startNew(); // create and start
		Assert.assertTrue(System.currentTimeMillis() - stopWatch.getStartTime() <= 5);
		Assert.assertTrue(stopWatch.isRunning());

		try {
			stopWatch.start(); // already started
			Assert.fail("Expect exception");
		} catch (Exception e) {
		}
	}

	/**
	 * 随机睡眠(5 - 100ms以内)
	 */
	private void randomSleep() {
		SleepUtil.sleep(5 + new Random().nextInt(96));
	}
}
