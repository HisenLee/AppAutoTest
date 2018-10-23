package com.itt.testcase.teststatus;

import com.testserver.util.exceloperate;

/**
 * @author xblia
 * 2015年10月21日
 */
public class TestStatusRecordTestResult extends CommonTestStatus
{
	public TestStatusRecordTestResult(ITTTestContext context)
    {
	    super(context);
    }
	
	public int getStatusCode()
	{
		return STATUS_RECORD_TESTRESULT;
	}

	@Override
	public boolean doAction()
    {
		testApkInfo.addTestResult(testResult);
		
		context.setStatus(-1);
		if(!context.getTestStrategy().isEndTestCase())
		{
			return false;
		}
		
		
		//Record Result
		exceloperate.writeresult(context.getResultExcelPath(), testResult);
		exceloperate.writeMultiResult(context.getResultExcelPath(), testApkInfo.getTestMutilResult());
		context.setFinishedTest(true);
		
		//Clear test status info.
		notifyStatus(null);
		return true;
    }

}
