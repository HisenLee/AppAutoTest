package com.testserver.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.intel.cats.test.log.ILog;

/*
 * 监测quit发送crash的监听器
 * 
 * */
public class CheckPackageCrashDialog
{
	public static String docheck(String packageName, String deviceid)
	{
		ILog.getLog().info(deviceid,
		        "look for crash dialog with device id: " + deviceid);
		ProcessBuilder b;
		Process deamon = null;
		if (deviceid == null)
		{
			b = new ProcessBuilder();
			b.command().add("adb");
			b.command().add("shell");
			b.command().add("dumpsys");
			b.command().add("window");
			b.command().add("windows");
		} else
		{
			b = new ProcessBuilder();
			b.command().add("adb");
			b.command().add("-s");
			b.command().add(deviceid);
			b.command().add("shell");
			b.command().add("dumpsys");
			b.command().add("window");
			b.command().add("windows");
		}
		String crashInfo = "";
		try
		{
			deamon = b.start();
		} catch (IOException ex)
		{
			ILog.getLog().info(deviceid, ex.getMessage());
		}

		if (deamon != null)
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(
			        deamon.getInputStream()));
			String line = null;
			while (true)
			{
				try
				{
					line = br.readLine();
				} catch (IOException e)
				{
					ILog.getLog().info(deviceid, e.getMessage());
					break;
				}
				// FileOperate.println(logpath,line);
				if (line != null)
				{
					if (line.contains(packageName))
					{
						if (line.contains("Application Error: ")
						        || line.contains("Application Not Responding: "))
						{
							crashInfo = line;
						}
					}
				} else
				{
					break;
				}
			}
		}
		try
		{
			deamon.waitFor();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		deamon.destroy();
		if (!crashInfo.isEmpty())
		{
			ILog.getLog().info(deviceid,
			        "Crash/ANR dialog found for package: " + packageName);
		}
		return crashInfo;
	}
}
