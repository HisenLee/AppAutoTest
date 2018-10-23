package com.testserver.util;

/*
 * 通过monkey命令来执行随机操作
 * 
 * 
 * 
 * */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.intel.cats.test.log.ILog;

public class TouchOperate implements Runnable
{

	private String deviceid;
	private String packagename;
	private int status = 0;
	private Process deamon = null;
	private String lineResult = "";

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

	public String getLineResult()
	{
		return lineResult;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public TouchOperate(String deviceid, String packagename)
	{
		super();
		this.deviceid = deviceid;
		this.packagename = packagename;
	}

	@Override
	public void run()
	{
		ProcessBuilder pb;
		pb = new ProcessBuilder();
		pb.command().add("adb");
		pb.command().add("-s");
		pb.command().add(deviceid);
		pb.command().add("shell");
		pb.command().add("monkey");
		pb.command().add("-v");
		pb.command().add("-p");
		pb.command().add(packagename);
		pb.command().add("--throttle");
		pb.command().add("500");
		pb.command().add("--ignore-crashes");
		pb.command().add("--ignore-timeouts");
		pb.command().add("--ignore-security-exceptions");
		pb.command().add("--monitor-native-crashes");
		pb.command().add("-s");
		pb.command().add("2345");
		pb.command().add("30");
		try
		{
			pb.redirectErrorStream(true);
			deamon = pb.start();
		} catch (IOException e)
		{
			ILog.getLog().info(deviceid, e.getMessage());
			lineResult = "start touch process exception.";
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
			e1.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		try
		{
			deamon.destroy();
			deamon.waitFor();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		deamon = null;
		status = 1;
	}
}
