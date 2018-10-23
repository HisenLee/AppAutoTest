package com.test.po;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author xblia
 * 2015年10月23日
 */
public class TestApkInfo
{
	private File originFile;
	private File file;
	private String apkname_Originname;
	private String apkname;
	private String pkgInfo;
	private String appName;// add appName
	private String apkVersion; // add apkVersion getter/setter method
	private String mainActivity;
	private Map<String, DeviceTestPermissions> deviceToPromissions;
	private TestResult result;
	private List<TestResult> multiTestResult = new ArrayList<TestResult>();
	
	public TestApkInfo(File originFile, String apkname_Originname)
	{
		this.originFile = originFile;
		this.apkname_Originname = apkname_Originname;
		init();
	}
	
	private void init()
	{
		this.deviceToPromissions = new HashMap<String, DeviceTestPermissions>();
	}

	// end
	public TestResult getResult()
	{
		return result;
	}

	public void setResult(TestResult result)
	{
		this.result = result;
	}

	public String getApkname_Originname()
	{
		return apkname_Originname;
	}

	public void setApkname_Originname(String apkname_Originname)
	{
		this.apkname_Originname = apkname_Originname;
	}

	public String getApkname()
	{
		return apkname;
	}

	public void setApkname(String apkname)
	{
		this.apkname = apkname;
	}
	
	public String getAppName()
	{
		return appName;
	}

	public void setAppName(String appName)
	{
		this.appName = appName;
	}
	
	public String getApkVersion()
	{
		return apkVersion;
	}

	public void setApkVersion(String apkVersion)
	{
		this.apkVersion = apkVersion;
	}
	
	public String getPkgInfo()
	{
		return pkgInfo;
	}

	public void setPkgInfo(String pkgInfo)
	{
		this.pkgInfo = pkgInfo;
	}
	

	public String getMainActivity()
	{
		return mainActivity;
	}

	public void setMainActivity(String mainActivity)
	{
		this.mainActivity = mainActivity;
	}

	public void setFile(File file)
	{
		this.file = file;
	}
	
	public File getOriginFile()
	{
		return originFile;
	}

	public void setOriginFile(File originFile)
	{
		this.originFile = originFile;
	}

	public boolean isFileExist(String basePath)
	{
		if (new File(basePath + File.separator + this.apkname).exists())
		{
			return true;
		}
		return false;
	}

	public File getFile()
    {
	    return this.file;
    }
	
	public DeviceTestPermissions getDeviceTestPermissions(String deviceId)
	{
		return this.deviceToPromissions.get(deviceId);
	}
	
	public int getTestedCount()
	{
		int iTestCount = 0;
		Set<Entry<String, DeviceTestPermissions>> idToPermissionEntry = deviceToPromissions.entrySet();
		if(null != idToPermissionEntry)
		{
			for (Entry<String, DeviceTestPermissions> entry : idToPermissionEntry)
            {
				iTestCount += entry.getValue().getTestedCount();
            }
		}
		return iTestCount;
	}
	
	public int getTestedDeviceCount()
	{
		return deviceToPromissions.size();
	}
	
	public void setDeviceTestPermissions(String deviceId, boolean isAllowTest)
	{
		DeviceTestPermissions deviceTestPermission = deviceToPromissions.get(deviceId);
		if(null == deviceTestPermission)
		{
			deviceTestPermission = new DeviceTestPermissions(deviceId);
		}
		deviceTestPermission.setAllowTest(isAllowTest);
		
		deviceToPromissions.put(deviceId, deviceTestPermission);
	}
	
	public boolean isAllowDeviceTest(String deviceId)
	{
		DeviceTestPermissions deviceTestPermission = deviceToPromissions.get(deviceId);
		if(null != deviceTestPermission)
		{
			return deviceTestPermission.isAllowTest();
		}else
		{
			deviceToPromissions.put(deviceId, new DeviceTestPermissions(deviceId));
		}
		return true;
	}

	public void addTestResult(TestResult testResult)
    {
		multiTestResult.add(testResult);
    }
	
	public List<TestResult> getTestMutilResult()
	{
		return multiTestResult;
	}
	
}