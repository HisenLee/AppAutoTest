package com.testserver.util;

/*
 * adb的全部操作
 * 
 * */
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import com.intel.cats.test.log.ILog;
import com.intel.cats.test.log.LogLevel;
import com.intel.cats.test.log.Util;
import com.itt.preferences.UserParam;
import com.test.po.TestResult;

public class AdbOperate
{
	// 唤醒屏幕 发送power键
	public static void awake(String deviceid)
	{
		Process p = null;
		// path = "'" + path + "'";
		ProcessBuilder b;

		b = new ProcessBuilder();
		b.command().add("adb");
		if (deviceid == null || deviceid.equals(""))
		{

		} else
		{
			b.command().add("-s");
			b.command().add(deviceid);
		}
		b.command().add("shell");
		b.command().add("input");
		b.command().add("keyevent");
		b.command().add("26");
		try
		{
			p = b.start();
		} catch (IOException e)
		{
			ILog.getLog().info(deviceid, e.getMessage());
		}
		try
		{
			ILog.getLog().info(deviceid, "begin awake wait for");
			p.waitFor();
			ILog.getLog().info(deviceid, "end awake wait for");
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		p.destroy();
	}

	// 截图，首先使用screencap截图，然后使用adb pull将图片copy到pc上
	public static void snapshot(String path, String deviceid)
	{
		ILog.getLog().info(deviceid, path, "begin snapshot");
		snaptodevice(deviceid);
		Process p = null;
		try
		{
			// path = "'" + path + "'";
			ProcessBuilder b;

			b = new ProcessBuilder();
			b.command().add("adb");
			if (deviceid == null || deviceid.equals(""))
			{

			} else
			{
				b.command().add("-s");
				b.command().add(deviceid);
			}
			b.command().add("pull");
			b.command().add("/data/local/tmp/snapshot.png");
			b.command().add(path);
			try
			{
				p = b.start();
			} catch (IOException e)
			{
				ILog.getLog().info(deviceid, e.getMessage());
			}
			// Process process = run.exec("aapt dump badging " + path);
			BufferedReader bf = new BufferedReader(new InputStreamReader(
			        p.getInputStream(), "UTF-8"));
			String str = "";
			while ((str = bf.readLine()) != null)
			{
				ILog.getLog().info(deviceid, str);
			}
			bf.close();
			// process.waitFor();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.err.print(strtemp);
		ILog.getLog().info(deviceid, path, "end snapshot");
	}

	// 使用adb screencap将截图放入手机指定的文件夹中
	private static void snaptodevice(String deviceid)
	{
		Process p = null;
		try
		{
			// path = "'" + path + "'";
			ProcessBuilder b;

			b = new ProcessBuilder();
			b.command().add("adb");
			if (deviceid == null || deviceid.equals(""))
			{

			} else
			{
				b.command().add("-s");
				b.command().add(deviceid);
			}

			b.command().add("shell");
			b.command().add("/system/bin/screencap");
			b.command().add("-p");
			b.command().add("/data/local/tmp/snapshot.png");

			try
			{
				p = b.start();
			} catch (IOException e)
			{
				ILog.getLog().info(deviceid, e.getMessage());
			}
			// Process process = run.exec("aapt dump badging " + path);
			BufferedReader bf = new BufferedReader(new InputStreamReader(
			        p.getInputStream(), "UTF-8"));
			String str = "";
			while ((str = bf.readLine()) != null)
			{
				ILog.getLog().info(deviceid, str);
			}
			bf.close();
			// process.waitFor();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.err.print(strtemp);

	}

	// install启动安装线程，如果在规定的时间内，不能执行，返回0，成功返回1，不成功返回-1；
	public static int install(TestResult result, String path, String deviceid)
	        throws Exception
	{
		File f = new File(path);
		long size = f.length();
		long time = UserParam.getInstallTimeout();
		if(time == -1)
		{
			time = size / (1024 * 100);// 100KB/s
			if (time >= 0 && time < 10)
			{
				time = 10;
			}
		}
				
		PkgInstaller install = new PkgInstaller(deviceid, path);
		Thread t = new Thread(install);
		t.start();
		ILog.getLog().info(deviceid, path,
		        "install maybe cost time: " + time + "s");
		while (--time >= 0)
		{
			Util.block(deviceid, 1000);
			if (install.getStatus() != 0)
			{
				if (null != result)
				{
					result.setInstallFailReason(install.getLineResult() == null ? ""
					        : install.getLineResult());
				}
				return install.getStatus();
			}
		}

		if (null != result)
		{
			result.setInstallFailReason("install time out: " + time + "s");// install
																		   // timeout.
		}
		install.closeProcess();
		ILog.getLog().log(deviceid, LogLevel.ERROR, deviceid, path,
		        "install fail, get Status is 0");
		return 0;

	}

	// start 启动launch线程，如果在规定的时间内，不能执行，返回0，成功返回1，不成功返回-1；
	public static int start(String packagename, String deviceid)
	        throws InterruptedException
	{
		int time = 20;
		long lTime = UserParam.getLauchTimeout();
		if(lTime != -1)
		{
			time = (int)lTime;
		}
		LaunchOperate lo = new LaunchOperate(deviceid, packagename);
		Thread t = new Thread(lo);
		t.start();
		ILog.getLog().info(deviceid, packagename,
		        "start activity maybe need " + time + "s");
		while (--time >= 0)
		{
			Util.block(deviceid, 1000);
			if (lo.getStatus() != 0)
			{
				return lo.getStatus();
			}
		}
		lo.closeProcess();
		return 0;
	}

	// touch 启动randomclick，如果在规定的时间内，不能执行，返回0，成功返回1，不成功返回-1；
	public static int touch(TestResult result, String packagename,
	        String deviceid) throws InterruptedException
	{
		/*
		 * Runtime run = Runtime.getRuntime(); try {
		 * 
		 * Process process = run .exec("adb -s " + deviceid +
		 * " shell monkey -v -p " + packagename +
		 * " --throttle 500 --ignore-crashes --ignore-timeouts --ignore-security-exceptions --monitor-native-crashes -s 2345 30"
		 * );
		 * 
		 * // process.waitFor(); } catch (IOException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
		int time = 15;
		long lTime = UserParam.getTouchTimeout();
		if(-1 != lTime)
		{
			time = (int)lTime;
		}
		TouchOperate to = new TouchOperate(deviceid, packagename);
		Thread t = new Thread(to);
		t.start();
		while (--time >= 0)
		{
			Util.block(deviceid, 1000);
			if (to.getStatus() != 0)
			{
				if (null != result)
				{
					result.setTouchFailReason(to.getLineResult() == null ? ""
					        : to.getLineResult());
				}
				return to.getStatus();
			}
		}
		if (null != result)
		{
			result.setTouchFailReason("touch timeout : " + time + "s");
		}
		to.closeProcess();
		ILog.getLog().log(deviceid, LogLevel.ERROR, deviceid, packagename,
		        "touch operator fail, get Status is 0");
		return 0;
	}

	// remove 启动uninstall，如果在规定的时间内，不能执行，返回0，成功返回1，不成功返回-1；
	public static int remove(TestResult result, String path,
	        String packagename, String deviceid)
	{
		File f = new File(path);
		long size = f.length();
		long time = UserParam.getUninstallTimout();
		if(time == -1)
		{
			time = size / (1024 * 100);
		}
		if (time >= 0 && time < 10)
		{
			time = 10;
		}
		PkgUninstaller uninstall = new PkgUninstaller(deviceid, packagename);
		Thread t = new Thread(uninstall);
		t.start();
		ILog.getLog().info(deviceid, path, packagename,
		        "remove maybe cost time : " + time + "s");
		while (--time >= 0)
		{
			Util.block(deviceid, 1000);
			if (uninstall.getStatus() != 0)
			{
				if (null != result)
				{
					result.setUninstallFailReason(uninstall.getLineResult() == null ? ""
					        : uninstall.getLineResult());
				}
				return uninstall.getStatus();
			}
		}

		if (null != result)// timeout.
		{
			result.setUninstallFailReason("uninstall timeout : " + time + "s");
		}
		uninstall.closeProcess();
		ILog.getLog().log(deviceid, LogLevel.ERROR, deviceid, packagename,
		        "uninstall operator fail, get Status is 0");
		return 0;

	}

	// quit 启动quit，通过点击back键来退出，并通过CheckPackageCrashDialog.docheck来分析是否crash
	public static String quit(String packageName, String deviceid)
	        throws InterruptedException
	{
		int iTime = (int)UserParam.getQuitTimeout();
		if(-1 == iTime)
		{
			iTime = 5;
		}
		String carashInfo = "";
		for (int i = 0; i < 5; i++)
		{
			try
			{
				Runtime run = Runtime.getRuntime();
				String strtemp = "";
				Process process = run.exec("adb -s " + deviceid
				        + " shell input keyevent 4");
				BufferedReader bf = new BufferedReader(new InputStreamReader(
				        process.getInputStream(), "UTF-8"));
				String str = "";
				while ((str = bf.readLine()) != null)
				{
					strtemp += str;
				}
				ILog.getLog().info(deviceid, strtemp);
				bf.close();

				// process.waitFor();
				process.destroy();
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			Util.block(deviceid, iTime * 1000);// add at 2014.11.3 返回操作点完后 等5秒
										   // 再去检查log
			carashInfo = CheckPackageCrashDialog.docheck(packageName, deviceid);
			if (!carashInfo.isEmpty())
			{
				break;
			}
		}

		return carashInfo;
	}
}
