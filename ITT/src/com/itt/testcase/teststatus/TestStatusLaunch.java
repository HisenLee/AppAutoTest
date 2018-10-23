package com.itt.testcase.teststatus;

import com.intel.cats.test.log.ILog;
import com.intel.cats.test.log.Util;
import com.testserver.util.AdbOperate;

/**
 * @author xblia
 * 2015年10月21日
 */
public class TestStatusLaunch extends CommonTestStatus
{
	public TestStatusLaunch(ITTTestContext context)
    {
	    super(context);
    }
	
	public int getStatusCode()
	{
		return STATUS_LAUNCH;
	}


	@Override
	public boolean doAction()
    {
		String deviceId = deviceInfo.getDeviceId();
		int startflag = -1;
		try
        {
	        startflag = AdbOperate.start(testApkInfo.getPkgInfo() + "/"
	                + testApkInfo.getMainActivity(), deviceId);
	        
	        ILog.getLog().info(deviceId, "end activity ");
	        Util.block(deviceId, 5000);
	        
	        String snapshotImage = beginSnapShot("launch");
	        testResult.setLaunchimgurl(snapshotImage);
        } catch (Exception e)
        {
	        e.printStackTrace();
        }
		
		boolean isCrashed = isCrash();
		if(startflag == 1 && !isCrashed)
		{
			context.setStatus(STATUS_WAITTING_USER);
			testResult.setLaunchsuccess(RESULT_PASS);
			return true;
		}else
		{
			context.setStatus(STATUS_UNINSTALL);
			testResult.setLaunchsuccess(RESULT_FAIL);
			testResult.setLaunchFailReason(crashObserable.getCrashReason(testApkInfo.getPkgInfo()));
			return false;
		}
    }

}
