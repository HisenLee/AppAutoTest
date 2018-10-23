package com.itt.testcase;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.cats.utils.IOUtils;
import com.itt.IProgressObserver;
import com.itt.apks.ui.FileForSelection;
import com.itt.devices.DeviceForSelection;
import com.itt.devices.IDeviceGroup;
import com.itt.devices.listener.IDeviceTestStatusListener;
import com.itt.preferences.IPropertyKeys;
import com.itt.preferences.ITTProperties;
import com.itt.preferences.UserParam;
import com.test.mgr.ITTMgr;
import com.test.po.TestApkInfo;
import com.testserver.util.ClearAllApp;
import com.testserver.util.FileOperate;
import com.testserver.util.exceloperate;

/**
 * @author xblia
 * 2015年10月20日
 */
public class AndroidDeviceGroup extends Thread implements ITaskControler, IDeviceGroup, IProgressObserver
{
	private List<DeviceForSelection> deviceInfos;
	private List<FileForSelection> selectionFiles;
	private List<String> testedFileNames;
	private List<AndroidApkTester> androidApkTesters;
	private IDeviceTestStatusListener statusListener;
	private String apkFolder;
	private String basePath;
	private String resultExcelPath;
	
	private boolean isRunning = true;
	private int iTotalTask = 0;
	private int iTotalFiles = 0;
	private int iFinishedTask = 0;
	
	private Object progressLock = new Object();
	
	private BlockingQueue<TestApkInfo> testApkInfos;
	
	public AndroidDeviceGroup(List<DeviceForSelection> deviceInfos,
            List<FileForSelection> selectionFiles,
            IDeviceTestStatusListener statusListener,
            String basePath)
    {
	    super();
	    this.apkFolder = basePath;
	    this.deviceInfos = deviceInfos;
	    this.selectionFiles = selectionFiles;
	    this.statusListener = statusListener;
	    if(selectionFiles != null && !selectionFiles.isEmpty())
	    {
	    	this.testApkInfos = new ArrayBlockingQueue<TestApkInfo>(selectionFiles.size()+1);
	    }else
	    {
	    	this.testApkInfos = new ArrayBlockingQueue<TestApkInfo>(1000);
	    }
	    this.androidApkTesters = new ArrayList<AndroidApkTester>();
    }

	@Override
	public void run()
	{
		if(null == deviceInfos || deviceInfos.isEmpty() || null == selectionFiles || selectionFiles.isEmpty())
		{
			statusListener.onStatus(null, "Init fail, please make check and try again!.");
			return;
		}
		
		initPaths();
		initTestResultFile();
		clearDeviceApp();
		
		if(initTestFiles())
		{	initProgressInfo();
			launchTester();
		}else
		{
			statusListener.onStatus(null, "<html><font color='red'>All files have been tested before</font></html>");
			isRunning = false;
		}
	}

	private void initPaths()
    {
		String workspacePath = apkFolder + File.separator + "ITT_WORKSPACE" + File.separator + deviceInfos.get(0).getDeviceName() + "_" + deviceInfos.get(0).getAndroidVersion();
		IOUtils.mkDirsIfNotExists(workspacePath);
		resultExcelPath = workspacePath + File.separator + "test_result.xlsx";
		
		basePath = workspacePath + File.separator + "result";
		IOUtils.mkDirsIfNotExists(basePath);
		
    }

	private void initTestResultFile()
    {
		String srcTemplateFilePath = IOUtils.getUserDir() + File.separator
		        + "TEMPLATE_RESULT.xlsx";
		File destResultFile = new File(resultExcelPath);
		FileOperate.filechannelcopy(new File(srcTemplateFilePath), destResultFile);
		testedFileNames = exceloperate.readApkFileName(resultExcelPath);
    }

	private void launchTester()
    {
		for (DeviceForSelection deivceInfo : deviceInfos)
        {
			deivceInfo.joinGroup(this);
			AndroidApkTester androidApkTester = new AndroidApkTester(deivceInfo, this, statusListener, basePath, resultExcelPath);
			androidApkTester.start();
			androidApkTesters.add(androidApkTester);
        }
    }
	
	private boolean hasTested(String fileName)
    {
		if(null == testedFileNames || testedFileNames.isEmpty())
		{
			return false;
		}
		
		return testedFileNames.contains(fileName);
    }

	/**
	 * @return Is task queue is empty.
	 */
	private boolean initTestFiles()
	{
		statusListener.onStatus(null, "Obtain authorization! Please waiting a few minutes...");
		synchronized (ITTMgr.getInstance())
		{
			for (FileForSelection selectFile : this.selectionFiles)
			{
				if(hasTested(selectFile.getFileName()) || (null == selectFile.getFile()))
				{
					continue;
				}
				
				TestApkInfo apkInfo = new TestApkInfo(selectFile.getFile(), selectFile.getFile().getName());
				testApkInfos.add(apkInfo);
				if (!isRunning)
				{
					break;
				}
			}
		}
		iTotalTask = testApkInfos.size();
		iTotalFiles = iTotalTask;
		iFinishedTask = 0;
		return !testApkInfos.isEmpty();
	}
	
	private void initProgressInfo()
    {
		if(UserParam.isFixedTestMode())
		{
			iTotalTask *= UserParam.getNeedTestTimes();
		}
		statusListener.onProgress(iTotalTask, iTotalFiles, 0);
		statusListener.onStatus(null, "");
    }
	
	private void clearDeviceApp()
    {
		String needClearDevice = ITTProperties.getProp(IPropertyKeys.CLEAR_DEVICE);
		if(needClearDevice.isEmpty() || !needClearDevice.equals("1"))
		{
			return;
		}
	    for (DeviceForSelection device : deviceInfos)
        {
	        ClearAllApp allApp = new ClearAllApp(device.getDeviceId(), this);
	        allApp.unInstallAll();
	        statusListener.onStatus(device.getDeviceId(), "");
        }
    }

	@Override
    public TestApkInfo getTask()
    {
		TestApkInfo apkInfo = null;
		try
        {
	        apkInfo = testApkInfos.take();
        } catch (Exception e)
        {
	        e.printStackTrace();
        }
	    return apkInfo;
    }

	@Override
    public void putTask(TestApkInfo apkInfo)
    {
		if(null == apkInfo)
		{
			return;
		}
		
		try
        {
	        testApkInfos.put(apkInfo);
        } catch (InterruptedException e)
        {
	        e.printStackTrace();
        }
    }

	@Override
    public boolean isRunning()
    {
	    return isRunning;
    }


	@Override
    public void notifyOneFinished()
    {
		synchronized(progressLock)
		{
			iFinishedTask++;
			statusListener.onProgress(iTotalTask, iTotalFiles, iFinishedTask);
			if(iFinishedTask == iTotalTask)
			{
				statusListener.onStatus(null, "All finished. End of test!!!");
				exitTest();
			}
		}
    }

	@Override
    public void openFolder()
    {
		try
        {
	        Desktop.getDesktop().browse(new File(basePath).toURI());
        } catch (IOException e)
        {
	        e.printStackTrace();
        }
    }
	
	private void exitTest()
    {
		this.isRunning = false;
		for (AndroidApkTester androidApkTester : androidApkTesters)
        {
			androidApkTester.interrupt();
        }
    }
	
	@Override
    public int getOnlineDeviceCount()
    {
		synchronized(deviceInfos)
		{
			int onlineDeviceCount = 0;
			for (DeviceForSelection device : deviceInfos)
			{
				if(device.isDeviceOnLine())
				{
					onlineDeviceCount++;
				}
			}
			return onlineDeviceCount;
		}
    }

	
	@Override
    public void onProgress(String deviceId, String info,  int total, int finished)
    {
	    statusListener.onProgress(total, iTotalFiles, finished);
	    statusListener.onStatus(deviceId, info);
    }
}
