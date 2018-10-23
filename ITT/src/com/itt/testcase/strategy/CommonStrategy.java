package com.itt.testcase.strategy;

import com.itt.devices.DeviceForSelection;
import com.itt.preferences.UserParam;
import com.test.po.TestApkInfo;
import com.test.po.TestResult;

/**
 * @author xiaobolx
 * 2015年11月26日
 */
public abstract class CommonStrategy implements ITestStrategy
{
	protected TestApkInfo apkInfo;
	protected DeviceForSelection deviceInfo;
	protected TestResult testResult;
	
	public void injectTestInfo(TestApkInfo apkInfo, DeviceForSelection deviceInfo,
            TestResult testResult)
    {
	    this.apkInfo = apkInfo;
	    this.deviceInfo = deviceInfo;
	    this.testResult = testResult;
    }
	
	
	@Override
	public boolean isNeedReTestWithOtherDevice()
	{
		if(apkInfo.getTestedCount() < UserParam.getNeedTestTimes() 
				&& apkInfo.getTestedDeviceCount() < deviceInfo.getDeviceGroup().getOnlineDeviceCount())
		{
			return true;
		}
		return false;
	}
	
}
