package com.testserver.util;

/*
 * 
 * 通过adb install来安装apk，
 * 参数 -r 可以使强制安装
 * */

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import com.intel.cats.test.log.ILog;

public class PkgInstaller implements Runnable
{
	// block until install finish
	private int status = 0;
	private String deviceSerialNo;
	private String apkFile;
	private String lineResult;

	private Process p = null;
	
	private InstalledTmpFileClearer installedTmpFileClearer;

	public int getStatus()
	{
		return status;
	}

	public String getLineResult()
	{
		return lineResult;
	}

	public void closeProcess()
	{
		if (p != null)
		{
			p.destroy();

		}
	}

	public PkgInstaller(String deviceSerialNo, String apkFile)
	{
		super();
		this.deviceSerialNo = deviceSerialNo;
		this.apkFile = apkFile;
	}

	public final int installApk()
	{
		ProcessBuilder b = null;
		ILog.getLog().info(deviceSerialNo, "Install apk abs path: " + apkFile);
		if (!new File(apkFile).exists())
		{
			ILog.getLog().info(deviceSerialNo,
			        "apk file not exist : " + apkFile);
		}
		if (deviceSerialNo == null)
		{
			b = new ProcessBuilder();
			b.command().add("adb");
			b.command().add("install");
			b.command().add("-r");
			b.command().add(apkFile);
		} else
		{
			b = new ProcessBuilder();
			b.command().add("adb");
			b.command().add("-s");
			b.command().add(deviceSerialNo);
			b.command().add("install");
			b.command().add("-r");
			b.command().add(apkFile);

		}

		try
		{
			b.redirectErrorStream(true);
			p = b.start();
		} catch (IOException e)
		{
			status = -1;
			ILog.getLog().log(e);
			ILog.getLog().info(deviceSerialNo,
			        "Install have exception." + apkFile);
			lineResult = "start adb process exception.";
			return -1;
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
		        p.getInputStream()));
		String line = null;
		String lineInfo = "";
		while (true)
		{
			try
			{
				line = br.readLine();
			} catch (IOException e)
			{
				break;
			}
			ILog.getLog().info(deviceSerialNo, "Line: " + line);
			if (line == null || line.length() < 0)
			{
				break;
			} else
			{
				lineInfo += line;
				installedTmpFileClearer.tryParsePath(line);
				if (line.contains("Success"))
				{
					status = 1;
					try
					{
						p.destroy();
						p.waitFor();
					} catch (InterruptedException e)
					{
						// ignore
					}
					return status;
				}
			}
		}

		lineResult = lineInfo;
		status = -2;
		p.destroy();
		p = null;
		return -2;
	}

	@Override
	public void run()
	{
		//lazy load
		installedTmpFileClearer = new InstalledTmpFileClearer(deviceSerialNo);
		installApk();
		installedTmpFileClearer.tryDelTmpFile();
	}
}
