package com.itt.testcase.strategy;

import com.itt.preferences.UserParam;

/**
 * @author xiaobolx
 * 2015年11月26日
 */
public class TestLazyStrategy extends CommonStrategy
{
	
	@Override
    public boolean isEndTestCase()
    {
		boolean isAllPass = testResult.allPass();
		if((isAllPass && apkInfo.getTestedCount() < UserParam.getNeedTestTimes())
				|| (!isAllPass && isNeedReTestWithOtherDevice()))
		{
			return false;
		}
	    return true;
    }

	@Override
    public boolean isContinueTest(int iTestedCount)
    {
		return apkInfo.getTestedCount() < UserParam.getNeedTestTimes() && testResult.allPass();
    }

}
