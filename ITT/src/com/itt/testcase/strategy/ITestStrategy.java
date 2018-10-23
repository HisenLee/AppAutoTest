package com.itt.testcase.strategy;

import com.itt.devices.DeviceForSelection;
import com.test.po.TestApkInfo;
import com.test.po.TestResult;


/**
 * @author xiaobolx
 * 2015年11月26日
 */
public interface ITestStrategy
{
	public enum TestMode
	{
		enLazy,
		enFixed
	}
	
	void injectTestInfo(TestApkInfo apkInfo, DeviceForSelection deviceInfo,
            TestResult testResult);
	
	boolean isNeedReTestWithOtherDevice();
	
	boolean isEndTestCase();
	
	boolean isContinueTest(int iTestedCount);
	
}
