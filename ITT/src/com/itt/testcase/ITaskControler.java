package com.itt.testcase;

import com.test.po.TestApkInfo;

/**
 * @author xblia
 * 2015年10月20日
 */
public interface ITaskControler
{
	/**
	 * Get task
	 * @return
	 */
	TestApkInfo getTask();
	
	/**
	 * Push Task
	 * @param apkInfo
	 */
	void putTask(TestApkInfo apkInfo);
	
	/**
	 * Running control
	 * @return
	 */
	boolean isRunning();
	
	/**
	 * One APK Test Finished notify.
	 */
	void notifyOneFinished();

	/**
	 * Open Test Result Folder
	 */
	void openFolder();
	
}
