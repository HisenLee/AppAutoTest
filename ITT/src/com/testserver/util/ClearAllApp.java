package com.testserver.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.intel.cats.test.log.ILog;
import com.intel.cats.test.log.Util;
import com.itt.IProgressObserver;

/**
 * @author xblia 2014年12月22日
 */
public class ClearAllApp
{
	private String deviceid;
	private IProgressObserver progressObserver;

	public ClearAllApp(String deviceid, IProgressObserver progressObserver)
	{
		this.deviceid = deviceid;
		this.progressObserver = progressObserver;
	}

	private List<String> getPackages()
	{
		List<String> packageNameList = new ArrayList<String>();
		Process process = null;
		InputStream inputStream = null;
		InputStreamReader bufferedInputStream = null;
		BufferedReader bufferedReader = null;
		try
		{
			List<String> cmdList = new ArrayList<String>();
			cmdList.add("adb");
			cmdList.add("-s");
			cmdList.add(deviceid);
			cmdList.add("shell");
			cmdList.add("pm");
			cmdList.add("list");
			cmdList.add("packages");
			cmdList.add("-3");
			ProcessBuilder builder = new ProcessBuilder(cmdList);
			process = builder.start();
			inputStream = process.getInputStream();
			bufferedInputStream = new InputStreamReader(inputStream);
			bufferedReader = new BufferedReader(bufferedInputStream);
			String strLine;
			do
			{
				strLine = bufferedReader.readLine();
				if (null != strLine && strLine.startsWith("package:"))
				{
					packageNameList.add(strLine.substring("package:".length()));
				}
			} while (strLine != null);
			process.waitFor();
		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				if (process != null)
				{
					process.destroy();
				}
				if (null != inputStream)
				{
					inputStream.close();
				}
				if (null != bufferedInputStream)
				{
					bufferedInputStream.close();
				}
				if (null != bufferedReader)
				{
					bufferedReader.close();
				}
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return packageNameList;
	}

	public void unInstallAll()
	{
		List<String> packageNameList = getPackages();
		if(null == packageNameList || packageNameList.isEmpty())
		{
			return;
		}
		int iUninstallSuccCount = 0;
		int iTotalPack = packageNameList.size();
		clearCacheApp();
		for (int i = 0; i < iTotalPack; i++)
		{
			ILog.getLog().info(
			        deviceid,
			        "begin uninstall: package: " + (i + 1) + "/"
			                + packageNameList.size() + " "
			                + packageNameList.get(i));
			progressObserver.onProgress(deviceid, "Uninstall package: " + packageNameList.get(i), iTotalPack, i+1);
			if (remove(packageNameList.get(i)) == 1)
			{
				iUninstallSuccCount++;
			}
		}
		ILog.getLog().info(
		        deviceid,
		        "clear all app finished. total package number: " + iTotalPack
		                + " success:" + iUninstallSuccCount);
	}

	private int clearCacheApp()
	{
		long time = 30; // 30 second.
		ClearCacheAPP clearCacheApp = new ClearCacheAPP(deviceid);
		Thread t = new Thread(clearCacheApp);
		t.start();
		while (--time >= 0)
		{
			Util.block(deviceid, 1000);
			if (clearCacheApp.getStatus() != 0)
			{
				return clearCacheApp.getStatus();
			}
		}
		return 0;
	}

	private int remove(String packagename)
	{
		long time = 30; // 30 second.
		PkgUninstaller uninstall = new PkgUninstaller(deviceid, packagename);
		Thread t = new Thread(uninstall);
		t.start();
		while (--time >= 0)
		{
			Util.block(deviceid, 1000);
			if (uninstall.getStatus() != 0)
			{
				return uninstall.getStatus();
			}
		}
		uninstall.closeProcess();
		return 0;
	}
}
