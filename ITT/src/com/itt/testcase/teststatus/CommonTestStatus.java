package com.itt.testcase.teststatus;

import java.awt.event.ActionEvent;
import java.io.File;

import com.cats.ui.optiondlg.ResultCallback;
import com.cats.utils.IOUtils;
import com.cats.utils.TimerValueUtil;
import com.intel.cats.test.log.ILog;
import com.itt.ITTConstant;
import com.itt.devices.DeviceForSelection;
import com.itt.devices.listener.IDeviceStatusListener;
import com.itt.devices.listener.IDeviceTestStatusListener;
import com.itt.devices.statusfilter.ParamAndResult;
import com.itt.preferences.IPropertyKeys;
import com.itt.preferences.ITTProperties;
import com.itt.testcase.ICrashObserable;
import com.itt.testcase.ITestStepControler;
import com.test.po.TestApkInfo;
import com.test.po.TestResult;
import com.testserver.util.AdbOperate;
import com.testserver.util.ScreenRecorder;

/**
 * @author xblia
 * 2015年10月21日
 */
public abstract class CommonTestStatus extends ITestStatus implements ITestStepControler, IDeviceStatusListener
{
	public static String RESULT_PASS = "Pass";
	public static String RESULT_FAIL = "Fail";
	public static String RESULT_BLOCK = "Block";
	
	protected TestApkInfo testApkInfo;
	protected TestResult testResult;
	protected DeviceForSelection deviceInfo;
	protected IDeviceTestStatusListener statusListener;
	protected ICrashObserable crashObserable;
	protected ScreenRecorder screenRecorder;
	protected CommonTestStatus(ITTTestContext context)
    {
	    super(context);
	    this.testApkInfo = context.getApkInfo();
	    this.testResult = context.getTestResult();
	    this.deviceInfo = context.getDeviceInfo();
	    this.statusListener = context.getStatusListener();
	    this.crashObserable = context.getCrashObserable();
    }
	
	protected String beginSnapShot(String name)
	{
		String deviceId = context.getDeviceInfo().getDeviceId();
		String fullPathTemplate = context.getBasePath() + File.separator
                + deviceId.replace(":", ".") + "_" + genTestingInfo() + "_"+ name + "%s.png";
		
		String fullPath = IOUtils.getNotExistsPath(fullPathTemplate);
		AdbOperate.snapshot(fullPath, deviceId);
		return fullPath;
	}
	
	protected void notifyStatus(String info)
	{
		if(null != statusListener)
		{
			if(null == info)
			{
				statusListener.onStatus(deviceInfo.getDeviceId(), "");
			}
			else
			{
				statusListener.onStatus(deviceInfo.getDeviceId(), genTestingInfo() + " "+ info);
			}
		}
	}

	private String genTestingInfo()
    {
		/*StringBuilder sb = new StringBuilder();
		sb.append(testApkInfo.getTestedCount());
		sb.append("/");
		sb.append(testApkInfo.getApkname_Originname());
	    return sb.toString();*/
		return testApkInfo.getApkname_Originname();
    }
	
	protected void notifyStatus(int iStatusCode, int wparam, Object lparam)
	{
		if(null != statusListener)
		{
			statusListener.onStatus(deviceInfo.getDeviceId(), iStatusCode, wparam, lparam);
		}
	}
	
	protected boolean isCrash()
	{
		return crashObserable.isCrashed(testApkInfo.getPkgInfo());
	}
	
	public void doActionWithDevice()
	{
		ILog.getLog().info(deviceInfo.getDeviceId(), "start status...1");
		notifyStatus(getStatusCode(), 0, this);
		ILog.getLog().info(deviceInfo.getDeviceId(), "start status...2");
		crashObserable.resetCrashInfo();
		
		deviceInfo.registerStatusListener(this);
		waitingIfStatusNotOk();
        boolean isPass = doAction();
        deviceInfo.removeStatusListener(this);
        
		if(!isPass)
		{
		}
		
		ILog.getLog().info(deviceInfo.getDeviceId(), "end status...1");
		notifyStatus(getStatusCode(), 1, this);
		ILog.getLog().info(deviceInfo.getDeviceId(), "end status...2");
	}
	
	@Override
	public String getDeviceId()
	{
		return this.deviceInfo.getDeviceId();
	}

	@Override
	public void onLine(boolean isOnline)
	{
		notifyDeviceStatus();
	}

	@Override
	public void onBatteryLevel(int level)
	{
		notifyDeviceStatus();
	}

	private void notifyDeviceStatus()
	{
		synchronized (deviceInfo)
		{
			deviceInfo.notifyAll();
		}
	}
	
	protected void endScreenRecord(String saveNameSection)
	{
		if(screenRecorder != null)
		{
			screenRecorder.stopRecord();
			String fullPathTemplate = context.getBasePath() + File.separator
	                + getDeviceId().replace(":", ".") + "_" + testApkInfo.getApkname_Originname() + "_" + saveNameSection + "_screen_record" + "%s.mp4";
			
			String fullPath = IOUtils.getNotExistsPath(fullPathTemplate);
			screenRecorder.pullRecordFileToDesktop(fullPath);
			// add remove .mp4
			screenRecorder.clearRecordFiles();
		}
	}

	protected void beginScreenRecord()
	{
		String isNeedingScreenRecord = ITTProperties.getProp(IPropertyKeys.SCREEN_RECORD_TO_MP4);
		if(isNeedingScreenRecord == null 
				|| isNeedingScreenRecord.isEmpty()
				|| isNeedingScreenRecord.equals("0"))
		{
			return;
		}
		
		screenRecorder = new ScreenRecorder(getDeviceId(), ScreenRecorder.RECORD_FILE_NAME, ScreenRecorder.RECORD_TIME_LIMIT);
		if(screenRecorder.isValidRecorder())
		{
			screenRecorder.startRecord();
		}
	}
	
	
	/**
	 * avoid stack over flow. adjust by return value,
	 * if status OK return true, else return false
	 * @return
	 */
	private void waitingIfStatusNotOk()
	{
		boolean deviceStatusOk = false;
		do
		{
			synchronized (deviceInfo)
			{
				if ((deviceInfo.getBatteryLevel() != -1
						&& deviceInfo.getBatteryLevel() <= ITTConstant.ALLOW_LOW_BATTERY_LEVEL) || !deviceInfo.isDeviceOnLine())
				{
					try
					{
						ILog.getLog().info(deviceInfo.getDeviceId(), "Battery is " + deviceInfo.getBatteryLevel() + " device onLine:" + deviceInfo.isDeviceOnLine() + ",, need sleep 1 minute.");
						deviceInfo.wait(TimerValueUtil.ONE_MINUTES);
					} catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}else
				{
					deviceStatusOk = true;
				}
			}
		}while(!deviceStatusOk);
	}
	
	public abstract boolean doAction();
	
	@Override
	public void actionPerformed(ActionEvent e){};
	
	@Override
	public void action(ResultCallback resultCallback, int iActionCode, ParamAndResult param){}
}