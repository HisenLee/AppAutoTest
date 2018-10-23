package com.itt.testcase;

import java.util.ArrayList;

import com.cats.utils.TimerValueUtil;
import com.cats.utils.Utils;
import com.intel.cats.test.log.ILog;
import com.itt.devices.DeviceForSelection;
import com.itt.devices.listener.IDeviceTestStatusListener;
import com.itt.preferences.UserParam;
import com.itt.testcase.strategy.ITestStrategy;
import com.itt.testcase.strategy.TestStrategyFactory;
import com.itt.testcase.teststatus.ITTTestContext;
import com.itt.testcase.teststatus.ITestStatus;
import com.itt.testcase.teststatus.TestStatusInstall;
import com.itt.testcase.teststatus.TestStatusLaunch;
import com.itt.testcase.teststatus.TestStatusPrepare;
import com.itt.testcase.teststatus.TestStatusQuit;
import com.itt.testcase.teststatus.TestStatusRandomClick;
import com.itt.testcase.teststatus.TestStatusRecordTestResult;
import com.itt.testcase.teststatus.TestStatusUninstall;
import com.itt.testcase.teststatus.TestStatusWattingManualTest;
import com.test.po.CrashInfo;
import com.test.po.TestApkInfo;
import com.test.po.TestResult;
import com.testserver.util.CrashCallback;
import com.testserver.util.CrashMonitorFactory;
import com.testserver.util.ICrashMonitor;

/**
 * @author xblia
 * 2015年10月20日
 */
public class AndroidApkTester extends Thread implements CrashCallback, ICrashObserable
{
	protected DeviceForSelection deivceInfo;
	private ITaskControler taskControler;
	private IDeviceTestStatusListener statusListener;
	private String basePath;
	private String resultExcelPath;
	private String deviceId;
	
	private ArrayList<CrashInfo> crashapk;
	private ICrashMonitor crashMonitor;
	private ITestStrategy testStrategy;
	public AndroidApkTester(DeviceForSelection deivceInfo, ITaskControler taskControler, IDeviceTestStatusListener statusListener, String basePath, String resultExcelPath)
    {
	    super();
	    this.deivceInfo = deivceInfo;
	    this.taskControler = taskControler;
	    this.statusListener = statusListener;
	    this.basePath = basePath;
	    this.resultExcelPath = resultExcelPath;
	    this.deviceId = deivceInfo.getDeviceId();
	    this.testStrategy = TestStrategyFactory.getStrategyFactory().createStrategy(UserParam.getTestMode());
    }
	
	@Override
	public void run()
    {
		crashapk = new ArrayList<CrashInfo>();
		crashMonitor = CrashMonitorFactory.getMonitorFactory().createDeviceMonitor(deviceId);
		crashMonitor.addCallback(this);
		crashMonitor.start();
		while(taskControler.isRunning())
		{
			TestApkInfo testApkInfo = taskControler.getTask();
			if(null == testApkInfo || !testApkInfo.isAllowDeviceTest(deviceId))
			{
				taskControler.putTask(testApkInfo);
				Utils.block(TimerValueUtil.ONE_SECOND*10);
				continue;
			}
			
			if(beginTest(testApkInfo))
			{
				taskControler.putTask(testApkInfo);
			}
		}
		crashMonitor.finish();
    }

	/**
	 * @param testApkInfo
	 * @param testResult
	 * @return 是否需要其它设备测试
	 */
	private boolean beginTest(TestApkInfo testApkInfo)
    {
		int iTestCount = 0;
		TestResult testResult = null;
		ITTTestContext testContext = null;
		do
		{
			iTestCount++;
			testResult = new TestResult();
			testStrategy.injectTestInfo(testApkInfo, deivceInfo, testResult);
			testContext = initTestContext(testApkInfo, testResult, testStrategy);
			while(testContext.getStatus() != -1)
			{
				ITestStatus iTestStatus = testContext.getStatusInstance(testContext.getStatus());
				if(iTestStatus != null)
				{
					ILog.getLog().info(deviceId, "before doActionWithDevice " + iTestStatus.getStatusCode());
					iTestStatus.doActionWithDevice();
					ILog.getLog().info(deviceId, "end doActionWithDevice, context status: " + testContext.getStatus() );
				}
			}
			if(UserParam.isFixedTestMode())
			{
				taskControler.notifyOneFinished();
			}
			testContext.stopContext();
		}while(testStrategy.isContinueTest(iTestCount));
		
		testApkInfo.setDeviceTestPermissions(deviceId, false);
		
		if(testContext.isFinishedTest())
		{
			if(UserParam.isLazyTestMode())
			{
				taskControler.notifyOneFinished();
			}
			return false;
		}else
		{
			return true;//Is Need other devices testing.
		}
    }

	private ITTTestContext initTestContext(TestApkInfo testApkInfo, TestResult testResult, ITestStrategy tStrategy)
    {
		ITTTestContext testContext = new ITTTestContext(tStrategy, this, ITestStatus.STATUS_PREPARE, statusListener, deivceInfo, testApkInfo, testResult, basePath, resultExcelPath);
		
		testContext.registeStatusClass(ITestStatus.STATUS_PREPARE, 		TestStatusPrepare.class);
		testContext.registeStatusClass(ITestStatus.STATUS_INSTALL, 		TestStatusInstall.class);
		testContext.registeStatusClass(ITestStatus.STATUS_LAUNCH, 		TestStatusLaunch.class);
		testContext.registeStatusClass(ITestStatus.STATUS_WAITTING_USER,TestStatusWattingManualTest.class);
		testContext.registeStatusClass(ITestStatus.STATUS_RANDOMCLICK, 	TestStatusRandomClick.class);
		testContext.registeStatusClass(ITestStatus.STATUS_QUIT, 		TestStatusQuit.class);
		testContext.registeStatusClass(ITestStatus.STATUS_UNINSTALL, 	TestStatusUninstall.class);
		testContext.registeStatusClass(ITestStatus.STATUS_RECORD_TESTRESULT, TestStatusRecordTestResult.class);
		
		return testContext;
    }

	@Override
    public void onProcessDied(String packageName, String serialNo,
            String crashtype)
    {
		if (serialNo != null)
		{
			if (serialNo.equals(deviceId))
			{
				if (crashapk != null)
				{
					crashapk.add(new CrashInfo(packageName, crashtype));
				}
			}
		}
    }
	
	@Override
	public String getCrashReason(String packagename)
    {
		for (CrashInfo info : crashapk)
        {
	        if(info.getPackagename().equals(packagename))
	        {
	        	return info.getCrashtype();
	        }
        }
	    return "not found.";
    }

	@Override
    public boolean isCrashed(String packageName)
    {
		if (crashapk.contains(new CrashInfo(packageName, "")))
		{
			return true;
		}
	    return false;
    }

	@Override
    public boolean resetCrashInfo()
    {
		crashapk.clear();
	    return true;
    }
}