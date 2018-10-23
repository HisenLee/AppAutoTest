package com.testserver.util;

/*
 *  使用adb命令来launch apk
 *  adb shell am start 
 * 
 * */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.intel.cats.test.log.ILog;

public class LaunchOperate implements Runnable
{
	private String deviceid;
	private String packagename;
	private int status = 0;
	Process deamon = null;

	public void closeProcess()
	{
		if (deamon != null)
		{
			deamon.destroy();

		}
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public LaunchOperate(String deviceid, String packagename)
	{
		super();
		this.deviceid = deviceid;
		this.packagename = packagename;
	}

	@Override
	public void run()
	{
		// TODO Auto-generated method stub
		String action = "android.intent.action.MAIN";
		String category = "android.intent.category.LAUNCHER";
		ProcessBuilder pb;

		pb = new ProcessBuilder();
		pb.command().add("adb");
		pb.command().add("-s");
		pb.command().add(deviceid);
		pb.command().add("shell");
		pb.command().add("am");
		pb.command().add("start");
		pb.command().add("-n");
		pb.command().add(packagename);
		pb.command().add("-a");
		pb.command().add(action);
		pb.command().add("-c");
		pb.command().add(category);
		try
		{
			pb.redirectErrorStream(true);
			deamon = pb.start();
		} catch (IOException e)
		{
			ILog.getLog().info(deviceid, e.getMessage());
			status = -1;
			return;
		}
		BufferedReader bf = null;
		try
		{
			bf = new BufferedReader(new InputStreamReader(
			        deamon.getInputStream(), "UTF-8"));
			String str = "";
			String strtemp = "";
			while ((str = bf.readLine()) != null)
			{
				strtemp += str;
			}
			ILog.getLog().info(deviceid, strtemp);
			bf.close();
		} catch (UnsupportedEncodingException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try
		{
			deamon.waitFor();
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		deamon.destroy();
		deamon = null;
		status = 1;

	}

}
