package com.itt.testcase.teststatus;

import com.intel.cats.test.log.ILog;
import com.testserver.util.AdbOperate;

/**
 * @author xblia
 * 2015年10月21日
 */
public class TestStatusQuit extends CommonTestStatus
{
	public TestStatusQuit(ITTTestContext context)
    {
	    super(context);
    }
	
	public int getStatusCode()
	{
		return STATUS_QUIT;
	}

	@Override
	public boolean doAction()
    {
		String deviceId = deviceInfo.getDeviceId();
		ILog.getLog().info(deviceId, "begin quit");
		boolean iscrash = false;
		try
        {
			String crashInfo = AdbOperate.quit(testApkInfo.getPkgInfo(),
	        		deviceId);
			iscrash = !crashInfo.isEmpty();
	        ILog.getLog().info(deviceId, "end quit , is crashed:"
	                + iscrash);
	        String snapshotImage = beginSnapShot("quit");
	        testResult.setQuiturl(snapshotImage);
	        testResult.setQuitsuccess(iscrash ? RESULT_FAIL:RESULT_PASS);
			if (iscrash)
			{
				testResult.setQuitFailReason(crashInfo);
			}
        } catch (InterruptedException e)
        {
	        e.printStackTrace();
        }
		
		context.setStatus(STATUS_UNINSTALL);
		return iscrash;
    }

}
