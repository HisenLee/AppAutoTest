package com.testserver.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.intel.cats.test.log.ILog;
import com.intel.cats.test.log.Util;

/*
 * 如果发现crash框，通过发送
 * KEYCODE_DPAD_DOWN
 * KEYCODE_DPAD_RIGHT
 * KEYCODE_DPAD_CENTER
 * 来关闭crash框，此方法不一定完全点击成功
 * 
 * */
public class CloseCrashDialog
{
	static final int MaxTry = 5;// Add by xblia 2014-12-09 for fixed loop
								// forever problem

	public static void doClose(String deviceid)
	{
		ILog.getLog().info(deviceid, "begin do close crash dialog");
		int iTryIndex = 0;
		boolean crashed = false;
		do
		{
			crashed = false;
			ILog.getLog().info(deviceid,
			        "closeCrashDialog with device id: " + deviceid);
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
						if (line.contains("Application Error: "))
						{
							crashed = true;
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

			if (crashed)
			{
				sendKey(deviceid, 20);
				sendKey(deviceid, 22);
				sendKey(deviceid, 23);
			}
			ILog.getLog().info(deviceid,
			        "do close crash dialog, has crashed, need retry");
			Util.block(deviceid, 1000);
			iTryIndex++;
		} while (iTryIndex < MaxTry && crashed);
		ILog.getLog().info(deviceid, "end do close crash dialog");
	}

	private static void sendKey(String deviceid, int key)
	{
		ProcessBuilder b2;
		Process deamon2 = null;
		if (deviceid == null)
		{
			b2 = new ProcessBuilder();
			b2.command().add("adb");
			b2.command().add("shell");
			b2.command().add("input");
			b2.command().add("keyevent");
			b2.command().add("" + key);
		} else
		{
			b2 = new ProcessBuilder();
			b2.command().add("adb");
			b2.command().add("-s");
			b2.command().add(deviceid);
			b2.command().add("shell");
			b2.command().add("input");
			b2.command().add("keyevent");
			b2.command().add("" + key);
		}
		try
		{
			deamon2 = b2.start();
		} catch (IOException e)
		{
			ILog.getLog().info(deviceid, e.getMessage());
		}

		try
		{
			deamon2.waitFor();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		deamon2.destroy();
	}
}
