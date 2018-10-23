package com.itt.preferences;

import com.itt.testcase.strategy.ITestStrategy.TestMode;

/**
 * @author xblia
 * 2015年5月6日
 */
public class UserParam
{
	public static long getInstallTimeout()
	{
		return getLongVal(IPropertyKeys.TIMEOUT_INSTALL_SECOND);
	}
	
	public static long getUninstallTimout()
	{
		return getLongVal(IPropertyKeys.TIMEOUT_UNINSTALL_SECOND);
	}
	
	public static long getLauchTimeout()
	{
		return getLongVal(IPropertyKeys.TIMEOUT_LAUNCH_SECOND);		
	}
	
	public static long getTouchTimeout()
	{
		return getLongVal(IPropertyKeys.TIMEOUT_TOUCH_SECOND);
	}
	
	public static long getQuitTimeout()
	{
		return getLongVal(IPropertyKeys.TIMEOUT_QUIT_SECOND);
	}
	
	private static long getLongVal(String key)
	{
		String strProperty = ITTProperties.getProp(key);
		if(!strProperty.isEmpty())
		{
			try
            {
	            return Long.parseLong(strProperty);
            } catch (NumberFormatException e)
            {
            	e.printStackTrace();
            }
		}
		return -1;
	}
	
	public static String getBasepath()
	{
		return ITTProperties.getProp(IPropertyKeys.APK_PATH);
	}
	
	public static boolean getAutoTest()
	{
		return getBooleanProp(IPropertyKeys.AUTOTEST);
	}
	
	public static TestMode getTestMode()
	{
		if(getBooleanProp(IPropertyKeys.TESTMODE_FIXEDMODE))
		{
			return TestMode.enFixed;
		}else
		{
			return TestMode.enLazy;
		}
	}
	
	public static int getNeedTestTimes()
	{
		String strTestTimes = ITTProperties.getProp(IPropertyKeys.TEST_TIMES_EVERY_APK);
		int iTestTimes = 1;
		try
        {
	        iTestTimes = Integer.parseInt(strTestTimes);
        } catch (NumberFormatException e)
        {
        	e.printStackTrace();
        }
		return iTestTimes;
	}
	
	private static boolean getBooleanProp(String key)
	{
		String val = ITTProperties.getProp(key);
		return val != null && !val.isEmpty() && val.equals("1");
	}

	public static boolean isLazyTestMode()
    {
	    return UserParam.getTestMode().equals(TestMode.enLazy);
    }
	
	public static boolean isFixedTestMode()
    {
	    return UserParam.getTestMode().equals(TestMode.enFixed);
    }
}