package com.testserver.util;

import com.itt.ITTConstant;

/**
 * @author xiaobolx
 * 2015年11月27日
 */
public class CrashMonitorFactory
{
	private static CrashMonitorFactory crashMonitorFactory = new CrashMonitorFactory();
	public static CrashMonitorFactory getMonitorFactory()
	{
		return crashMonitorFactory;
	}
	
	public ICrashMonitor createDeviceMonitor(String deviceSerialNo)
	{
		String strSdkVal = DeviceOperate.getSDKInt(deviceSerialNo);
		int sdkVal = 0;
		try
        {
	        sdkVal = Integer.parseInt(strSdkVal);
        } catch (NumberFormatException e)
        {
	        e.printStackTrace();
        }
		
		if(sdkVal >= ITTConstant.SDK_ANDROID_6_0)
		{
			return new CrashMonitorForAndroid6(deviceSerialNo);
		}else
		{
			return new CrashMonitor(deviceSerialNo);
		}
		
	}
}
