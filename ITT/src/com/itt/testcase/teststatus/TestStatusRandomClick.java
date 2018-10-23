package com.itt.testcase.teststatus;

import com.intel.cats.test.log.ILog;
import com.intel.cats.test.log.Util;
import com.testserver.util.AdbOperate;

/**
 * @author xblia
 * 2015年10月21日
 */
public class TestStatusRandomClick extends CommonTestStatus
{
	public TestStatusRandomClick(ITTTestContext context)
    {
	    super(context);
    }
	
	public int getStatusCode()
	{
		return STATUS_RANDOMCLICK;
	}

	@Override
	public boolean doAction()
    {
		beginScreenRecord();
		int touchflag = -1;
		boolean isPass = false;
		try
        {
	        String deviceId = deviceInfo.getDeviceId();
	        ILog.getLog().info(deviceId, "begin touch");
	        touchflag = AdbOperate.touch(testResult, testApkInfo.getPkgInfo(), deviceId);
	        ILog.getLog().info(deviceId, "end touch " + touchflag);
	        Util.block(deviceId, 15000);
	        
	        String snapshotImag = beginSnapShot("random_click");
	        testResult.setRandomimgurl(snapshotImag);
        } catch (Exception e)
        {
	        e.printStackTrace();
        }
		
		endScreenRecord("operation");
		isPass = touchflag == 1 && !isCrash();
		testResult.setRandomsuccess(isPass ? RESULT_PASS:RESULT_FAIL);
		if(isCrash())
		{
			testResult.setTouchFailReason(crashObserable.getCrashReason(testApkInfo.getPkgInfo()));
		}
		
		context.setStatus(STATUS_QUIT);
		return isPass;
    }
	
}
