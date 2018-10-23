package com.itt.testcase.teststatus;

import com.intel.cats.test.log.ILog;
import com.testserver.util.AdbOperate;

/**
 * @author xblia
 * 2015年10月21日
 */
public class TestStatusInstall extends CommonTestStatus
{

	public TestStatusInstall(ITTTestContext context)
    {
	    super(context);
    }
	
	public int getStatusCode()
	{
		return STATUS_INSTALL;
	}

	@Override
    public boolean doAction()
    {
		String deviceId = deviceInfo.getDeviceId();
		int installflag = -1;
		boolean isPass = false;
	    try
        {
	        installflag = AdbOperate.install(testResult, testApkInfo.getFile().getAbsolutePath(),
	        		deviceId);
	        ILog.getLog().info(deviceId, "end install " + installflag);
	        isPass = installflag == 1;
        } catch (Exception e)
        {
	        e.printStackTrace();
        }
	    
	    if(isPass)
	    {
	    	context.setStatus(STATUS_LAUNCH);
	    }else
	    {
	    	context.setStatus(STATUS_UNINSTALL);
	    }
	    
	    String passFail = isPass ? RESULT_PASS : RESULT_FAIL;
	    testResult.setInstallsuccess(passFail);
	    
	    return isPass;
    }

}
