package com.itt.testcase.teststatus;

import java.io.File;

import com.cats.utils.IOUtils;
import com.itt.ITTConstant;
import com.testserver.util.ParseApk;


/**
 * @author xblia
 * 2015年10月21日
 */
public class TestStatusPrepare extends CommonTestStatus
{

	public TestStatusPrepare(ITTTestContext context)
	{
		super(context);
	}

	public int getStatusCode()
	{
		return STATUS_PREPARE;
	}

	@Override
	public boolean doAction()
	{
		preparFile();
		
		testApkInfo.getDeviceTestPermissions(deviceInfo.getDeviceId()).increaseTestCount();

		testResult.setApkname(testApkInfo.getApkname_Originname());
		// add appName
		testResult.setAppName(testApkInfo.getAppName());
		// add apkVersion 
		testResult.setApkVersion(testApkInfo.getApkVersion());
		testResult.setPkgName(testApkInfo.getPkgInfo());
		testResult.setDeviceid(deviceInfo.getDeviceId());
		testResult.setModel_ver(deviceInfo.getDeviceName() + "@"
		        + deviceInfo.getAndroidVersion());
		testResult.setDeviceVersionId(deviceInfo.getAndroidImageSerial());

		context.setStatus(STATUS_INSTALL);
		
		notifyStatus("...");
		return true;
	}

	private void preparFile()
    {
		File originFile = testApkInfo.getOriginFile();
		File apkFolder = originFile.getParentFile();
		
		String pathTemplate = apkFolder.getAbsolutePath() + File.separator + ITTConstant.TEMPFILE_MARK + this.getDeviceId() + "_"
		        + System.currentTimeMillis() + "%s.apk";
		String fullPathOfNewApk = IOUtils.getNotExistsPath(pathTemplate);
		
		File newTempFile = new File(fullPathOfNewApk);
		IOUtils.copyFile(originFile, newTempFile);

		testApkInfo.setFile(newTempFile);
		testApkInfo.setApkname(newTempFile.getName());
		
		if(null == testApkInfo.getPkgInfo() || testApkInfo.getPkgInfo().isEmpty())
		{
			String testinfo = ParseApk.getInfo(newTempFile.getAbsolutePath());
			String packagename = ParseApk.getpackname(testinfo);
			String activityname = ParseApk.getActivity(testinfo);
			// add appName
			String appName= ParseApk.getApkRealName(testinfo);
			// add apkVersion 
			String apkVersion = ParseApk.getApkVersion(testinfo);
			testApkInfo.setPkgInfo(packagename);
			// add appName
			testApkInfo.setAppName(appName);
			// add apkVersion
			testApkInfo.setApkVersion(apkVersion);
			testApkInfo.setMainActivity(activityname);
		}
		
    }

}
