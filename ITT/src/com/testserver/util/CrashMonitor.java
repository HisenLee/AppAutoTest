package com.testserver.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.intel.cats.test.log.ILog;

/*
 * 监听crash的监听器
 * 通过检查logcat中的 
 * E/AndroidRuntime
 * W/ActivityManager --Force finishing activity Force removing ActivityRecord
 * E/ActivityManager --ANR in 
 * */
public class CrashMonitor implements ICrashMonitor
{

	private ArrayList<CrashCallback> mCallbacks = new ArrayList<CrashCallback>();

	private String serialNo = null;
	private Process deamon = null;

	private boolean running = true;

	public CrashMonitor(String deviceSerialNo)
	{
		ILog.getLog().info(deviceSerialNo, "CrashLis");
		serialNo = deviceSerialNo;
	}

	@Override
	public void addCallback(CrashCallback cb)
	{
		synchronized (mCallbacks)
		{
			if (mCallbacks.contains(cb))
			{

			} else
			{
				mCallbacks.add(cb);
				ILog.getLog().info(serialNo, "cb added");
			}
		}
	}

	@Override
	public void start()
	{
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				ProcessBuilder b;
				ILog.getLog().info(serialNo, "CrashLis started: " + serialNo);
				if (serialNo == null)
				{
					b = new ProcessBuilder();
					b.command().add("adb");
					b.command().add("logcat");
					//b.command().add("*:I");
				} else
				{
					b = new ProcessBuilder();
					b.command().add("adb");
					b.command().add("-s");
					b.command().add(serialNo);
					b.command().add("logcat");
					//b.command().add("*:I");
				}
				try
				{
					deamon = b.start();
				} catch (IOException e)
				{
					ILog.getLog().info(serialNo, e.getMessage());
				}
				running = true;
				if (deamon != null)
				{
					BufferedReader br = new BufferedReader(
					        new InputStreamReader(deamon.getInputStream()));
					String line = null;
					while (running)
					{
						try
						{
							line = br.readLine();
						} catch (IOException e)
						{
							ILog.getLog().info(serialNo, e.getMessage());
							break;
						}
						if (line != null)
						{
							// FileOperate.println(logpath,"line-> "+line);
							if (line.contains("E/AndroidRuntime")
							        && line.contains("Process: ")
							        && line.contains("PID: "))
							{
								String packageName = line.substring(
								        line.indexOf("Process: ") + 9,
								        line.indexOf(','));
								ILog.getLog().info(serialNo,
								        "Process died: " + packageName);
								synchronized (mCallbacks)
								{
									for (CrashCallback cb : mCallbacks)
									{
										cb.onProcessDied(packageName, serialNo,
										        "Process Died");
									}
								}
							} else if (line.contains("W/ActivityManager")
							        && line.contains("Force finishing activity "))
							{
								String packageName = line.substring(
								        line.indexOf("Force finishing activity ") + 25,
								        line.lastIndexOf('/'));
								ILog.getLog().info(serialNo,
								        "Process died: " + packageName);
								synchronized (mCallbacks)
								{
									for (CrashCallback cb : mCallbacks)
									{
										cb.onProcessDied(packageName, serialNo,
										        "Process Died");
									}
								}
							} else if (line.contains("W/ActivityManager")
							        && line.contains("Force removing ActivityRecord")
							        && line.contains("app died, no saved state"))
							{
								String packageName = line.substring(
								        line.indexOf("{") + 1,
								        line.lastIndexOf('/'));
								packageName = packageName.substring(
								        packageName.lastIndexOf(" ") + 1,
								        packageName.length());
								ILog.getLog().info(serialNo,
								        "Process died: " + packageName);
								synchronized (mCallbacks)
								{
									for (CrashCallback cb : mCallbacks)
									{
										cb.onProcessDied(packageName, serialNo,
										        "Process Died");
									}
								}
							} else if (line.contains("E/ActivityManager")
							        && line.contains("ANR in "))
							{
								ILog.getLog().info(serialNo, line);
								String packageName = "";
								int firstindex = line.lastIndexOf('(');
								int secondindex = line.lastIndexOf('/');
								if (firstindex > secondindex)
								{
									packageName = line.substring(line
									        .lastIndexOf(" ") + 1);
								} else
									packageName = line.substring(
									        line.lastIndexOf('(') + 1,
									        line.lastIndexOf('/'));
								packageName = packageName.trim();
								System.err.println("ANR in process: "
								        + packageName);
								synchronized (mCallbacks)
								{
									for (CrashCallback cb : mCallbacks)
									{
										cb.onProcessDied(packageName, serialNo,
										        "ANR In process");
									}
								}
							}// else if(line.contains("I/ActivityManager") &&
							 // line.contains("Process ") &&
							 // line.contains(" has died.")){
							 // String packageName =
							 // line.substring(line.indexOf("Process ") + 8,
							 // line.lastIndexOf('('));
							// packageName = packageName.trim();
							// FileOperate.println(logpath,"Process died: " +
							// packageName);
							// synchronized (mCallbacks) {
							// for (CrashCallback cb : mCallbacks) {
							// cb.onProcessDied(packageName, serialNo);
							// }
							// }
							// }
						}
					}
				}
			}
		}).start();
	}

	@Override
	public void finish()
	{
		running = false;
		if (deamon != null)
		{
			deamon.destroy();
			try
			{
				deamon.waitFor();
			} catch (InterruptedException e)
			{
			}
		}
	}
}
