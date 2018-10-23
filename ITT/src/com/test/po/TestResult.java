package com.test.po;

import com.itt.testcase.teststatus.CommonTestStatus;

//测试结果的信息
public class TestResult
{
	private String installsuccess = "Fail";// install 的测试情况“Fail” or “Pass”
	private String launchimgurl = "Block"; // launch 截图路径
	private String launchsuccess = "Block";
	private String randomimgurl = "Block";
	private String randomsuccess = "Block";
	private String installimgurl = "Block";
	private String uninstallimgurl = "Block";
	private String quiturl = "Block";
	private String quitsuccess = "Block";
	private String uninstallsuccess = "Block";

	// add by xblia 2017-12-18, probe block or fail reason.
	private String installFailReason = "";
	private String launchFailReason = "";
	private String touchFailReason = "";
	private String quitFailReason = "";
	private String uninstallFailReason = "";
	// end

	// start 这些参数现在无用，但是以后会扩展使用
	private double[][] cpumem;
	private double avgcpu = 0.0;
	private double avgmem = 0.0;
	private String launchtime = "Block";
	private final int testnum = 10;
	// end
	private String lastresult = "Fail";
	private String apkname="";
	private String pkgName="";
	private String appName = ""; // add appName
	private String apkVersion = ""; // add apkVersion getter/setter method
	private String deviceid="";
	private String model_ver="";
	private String deviceVersionId="";

	public String getModel_ver()
	{
		return model_ver;
	}

	public void setModel_ver(String model_ver)
	{
		this.model_ver = model_ver;
	}

	public String getDeviceid()
	{
		return deviceid;
	}

	public void setDeviceid(String deviceid)
	{
		this.deviceid = deviceid;
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
	
	public String getPkgName()
	{
		return pkgName;
	}

	public void setPkgName(String pkgName)
	{
		this.pkgName = pkgName;
	}

	public TestResult()
	{
		super();
		// TODO Auto-generated constructor stub
		cpumem = new double[testnum][2];

	}

	public String getInstallimgurl()
	{
		return installimgurl;
	}

	public String getLaunchsuccess()
	{
		return launchsuccess;
	}

	public void setLaunchsuccess(String launchsuccess)
	{
		this.launchsuccess = launchsuccess;
	}

	public String getRandomsuccess()
	{
		return randomsuccess;
	}

	public void setRandomsuccess(String randomsuccess)
	{
		this.randomsuccess = randomsuccess;
	}

	public String getQuitsuccess()
	{
		return quitsuccess;
	}

	public void setQuitsuccess(String quitsuccess)
	{
		this.quitsuccess = quitsuccess;
	}

	public void setInstallimgurl(String installimgurl)
	{
		this.installimgurl = installimgurl;
	}

	public String getUninstallimgurl()
	{
		return uninstallimgurl;
	}

	public void setUninstallimgurl(String uninstallimgurl)
	{
		this.uninstallimgurl = uninstallimgurl;
	}

	public String getLastresult()
	{
		return lastresult;
	}

	public void setLastresult(String lastresult)
	{
		this.lastresult = lastresult;
	}

	public String getLaunchtime()
	{
		return launchtime;
	}

	public void setLaunchtime(String launchtime)
	{
		this.launchtime = launchtime;
	}

	public String getInstallsuccess()
	{
		return installsuccess;
	}

	public void setInstallsuccess(String installsuccess)
	{
		this.installsuccess = installsuccess;
	}

	public String getLaunchimgurl()
	{
		return launchimgurl;
	}

	public void setLaunchimgurl(String launchimgurl)
	{
		this.launchimgurl = launchimgurl;
	}

	public String getRandomimgurl()
	{
		return randomimgurl;
	}

	public void setRandomimgurl(String randomimgurl)
	{
		this.randomimgurl = randomimgurl;
	}

	public String getQuiturl()
	{
		return quiturl;
	}

	public void setQuiturl(String quiturl)
	{
		this.quiturl = quiturl;
	}

	public String getUninstallsuccess()
	{
		return uninstallsuccess;
	}

	public void setUninstallsuccess(String uninstallsuccess)
	{
		this.uninstallsuccess = uninstallsuccess;
	}

	public double[][] getCpumem()
	{
		return cpumem;
	}

	public void setCpumem(int row, int col, double value)
	{
		this.cpumem[row][col] = value;
	}

	public double getAvgcpu()
	{
		return avgcpu;
	}

	public void setAvgcpu(double avgcpu)
	{
		this.avgcpu = avgcpu;
	}

	public double getAvgmem()
	{
		return avgmem;
	}

	public void setAvgmem(double avgmem)
	{
		this.avgmem = avgmem;
	}
	
	public String getLaunchFailReason()
	{
		return launchFailReason;
	}

	public void setLaunchFailReason(String launchFailReason)
	{
		this.launchFailReason = launchFailReason;
	}

	public String getInstallFailReason()
	{
		return installFailReason;
	}

	public void setInstallFailReason(String installFailReason)
	{
		this.installFailReason = installFailReason;
	}

	public String getTouchFailReason()
	{
		return touchFailReason;
	}

	public void setTouchFailReason(String touchFailReason)
	{
		this.touchFailReason = touchFailReason;
	}

	public String getQuitFailReason()
	{
		return quitFailReason;
	}

	public void setQuitFailReason(String quitFailReason)
	{
		this.quitFailReason = quitFailReason;
	}

	public String getUninstallFailReason()
	{
		return uninstallFailReason;
	}

	public void setUninstallFailReason(String uninstallFailReason)
	{
		this.uninstallFailReason = uninstallFailReason;
	}

	public String getDeviceVersionId()
	{
		return deviceVersionId;
	}

	public void setDeviceVersionId(String deviceVersionId)
	{
		this.deviceVersionId = deviceVersionId;
	}

	public boolean allPass()
    {
		return this.installsuccess.equals(CommonTestStatus.RESULT_PASS)&&
				this.launchsuccess.equals(CommonTestStatus.RESULT_PASS)&&
				this.randomsuccess.equals(CommonTestStatus.RESULT_PASS)&&
				this.quitsuccess.equals(CommonTestStatus.RESULT_PASS)&&
				this.uninstallsuccess.equals(CommonTestStatus.RESULT_PASS);
    }

}