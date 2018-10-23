package com.itt.testcase.teststatus;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import com.cats.utils.IOUtils;
import com.itt.devices.DeviceForSelection;
import com.itt.devices.listener.IDeviceTestStatusListener;
import com.itt.testcase.ICrashObserable;
import com.itt.testcase.strategy.ITestStrategy;
import com.test.po.TestApkInfo;
import com.test.po.TestResult;
import com.testserver.util.LogcateOperate;

/**
 * @author xblia
 * 2015年10月21日
 */
public class ITTTestContext
{
	private Map<Integer, Class<?>> statusClassMap = new HashMap<Integer, Class<?>>();
	private int status;
	private IDeviceTestStatusListener statusListener;
	private ICrashObserable crashObserable;
	
	private DeviceForSelection deviceInfo;
	private TestApkInfo apkInfo;
	private TestResult testResult;
	private String basePath;
	private String resultExcelPath;
	
	private LogcateOperate logcateOperate;
	private boolean isFinishedTest = false;
	private ITestStrategy testStrategy;
	
	public ITTTestContext(ITestStrategy testStrategy, ICrashObserable crashObserable, int status, IDeviceTestStatusListener statusListener, DeviceForSelection deviceInfo, TestApkInfo apkInfo,
            TestResult testResult, String basePath, String resultExcelPath)
    {
	    super();
	    this.testStrategy = testStrategy;
	    this.crashObserable = crashObserable;
	    this.status = status;
	    this.statusListener = statusListener;
	    this.deviceInfo = deviceInfo;
	    this.apkInfo = apkInfo;
	    this.testResult = testResult;
	    this.basePath = basePath;
	    this.resultExcelPath = resultExcelPath;
	    
	    String logcatFullPathTemplate = basePath + File.separator + deviceInfo.getDeviceId() + "_" + apkInfo.getApkname_Originname() + "_logcat%s.txt";
	    String logcatFullPath = IOUtils.getNotExistsPath(logcatFullPathTemplate);
	    logcateOperate = new LogcateOperate(deviceInfo.getDeviceId(), logcatFullPath);
	    logcateOperate.start();
    }

	public void stopContext()
	{
		 logcateOperate.finish();
	}
	
	public ICrashObserable getCrashObserable()
	{
		return crashObserable;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}
	
	public IDeviceTestStatusListener getStatusListener()
	{
		return statusListener;
	}

	public void setStatusListener(IDeviceTestStatusListener statusListener)
	{
		this.statusListener = statusListener;
	}

	public DeviceForSelection getDeviceInfo()
	{
		return deviceInfo;
	}

	public void setDeviceInfo(DeviceForSelection deviceInfo)
	{
		this.deviceInfo = deviceInfo;
	}

	public String getBasePath()
	{
		return basePath;
	}

	public void setBasePath(String basePath)
	{
		this.basePath = basePath;
	}
	
	public String getResultExcelPath()
	{
		return resultExcelPath;
	}

	public void setResultExcelPath(String resultExcelPath)
	{
		this.resultExcelPath = resultExcelPath;
	}
	

	public TestApkInfo getApkInfo()
	{
		return apkInfo;
	}

	public void setApkInfo(TestApkInfo apkInfo)
	{
		this.apkInfo = apkInfo;
	}

	public TestResult getTestResult()
	{
		return testResult;
	}

	public void setTestResult(TestResult testResult)
	{
		this.testResult = testResult;
	}
	
	public boolean isFinishedTest()
	{
		return isFinishedTest;
	}

	public void setFinishedTest(boolean isFinishedTest)
	{
		this.isFinishedTest = isFinishedTest;
	}

	public void registeStatusClass(int istatusCode, Class<?> cls)
	{
		statusClassMap.put(istatusCode, cls);
	}
	
	public ITestStatus getStatusInstance(int iStatusCode)
	{
		Class<?> cls = statusClassMap.get(iStatusCode);
		if (cls != null)
		{
			try
			{
				Constructor<?> cons[] = cls.getConstructors();
				return (ITestStatus) cons[0].newInstance(this);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public ITestStrategy getTestStrategy()
	{
		return this.testStrategy;
	}
}