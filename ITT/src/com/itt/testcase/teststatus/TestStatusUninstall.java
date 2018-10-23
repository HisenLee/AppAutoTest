package com.itt.testcase.teststatus;

import java.io.File;

import com.intel.cats.test.log.ILog;
import com.testserver.util.AdbOperate;

/**
 * @author xblia
 * 2015年10月21日
 */
public class TestStatusUninstall extends CommonTestStatus
{
	public TestStatusUninstall(ITTTestContext context)
    {
	    super(context);
    }
	
	public int getStatusCode()
	{
		return STATUS_UNINSTALL;
	}

	@Override
	public boolean doAction()
    {
		String deviceId = deviceInfo.getDeviceId();
		int status = -1;
		ILog.getLog().info(deviceId, "begin remove");
		status = AdbOperate.remove(testResult, testApkInfo.getFile().getAbsolutePath(),
				testApkInfo.getPkgInfo(), deviceId);
		ILog.getLog().info(deviceId, "end remove " + status);
		
		boolean uninstallPass = status == 1;
		boolean installPass = testResult.getInstallsuccess().equals(RESULT_PASS);
		if(installPass)
		{
			testResult.setUninstallsuccess(uninstallPass ? RESULT_PASS:RESULT_FAIL);
		}else
		{
			testResult.setUninstallsuccess(RESULT_BLOCK);
			testResult.setUninstallFailReason("");
		}
		
		context.setStatus(STATUS_RECORD_TESTRESULT);
		
		deleteTestTempFile();
		return status == 1;
    }

	private void deleteTestTempFile()
    {
		File file = testApkInfo.getFile();
		file.delete();
    }

}
