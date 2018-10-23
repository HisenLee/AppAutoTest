package com.itt.testcase.strategy;

import com.itt.preferences.UserParam;


/**
 * @author xiaobolx
 * 2015年11月26日
 */
public class TestStanderStrategy extends CommonStrategy
{

	@Override
    public boolean isEndTestCase()
    {
		if(apkInfo.getTestedCount() < UserParam.getNeedTestTimes())
		{
			return false;
		}
	    return true;
    }

	@Override
    public boolean isContinueTest(int iTestedCount)
    {
		boolean isNeedTest = !isEndTestCase();
		
		if(isNeedTest)
		{
			if(!testResult.allPass())
			{
				if(isNeedReTestWithOtherDevice())
				{
					return false;
				}
			}
		}
		return isNeedTest;
    }
}
