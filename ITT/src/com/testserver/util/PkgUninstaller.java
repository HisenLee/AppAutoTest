package com.testserver.util;

/*
 * 卸载apk，通过adb uninstall packagename来调用
 * 
 * 
 * */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.intel.cats.test.log.ILog;

public class PkgUninstaller implements Runnable
{

	private int status = 0;
	private String deviceSerialNo;
	private String packagename;
	private String lineResult = "";

	private Process p = null;

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

	public PkgUninstaller(String deviceSerialNo, String packagename)
	{
		super();
		this.deviceSerialNo = deviceSerialNo;
		this.packagename = packagename;
	}

	public final int uninstall()
	{
		ProcessBuilder b = null;
		if (deviceSerialNo == null)
		{
			b = new ProcessBuilder();
			b.command().add("adb");
			b.command().add("uninstall");
			b.command().add(packagename);
		} else
		{
			b = new ProcessBuilder();
			b.command().add("adb");
			b.command().add("-s");
			b.command().add(deviceSerialNo);
			b.command().add("uninstall");
			b.command().add(packagename);

		}

		try
		{
			b.redirectErrorStream(true);
			p = b.start();
		} catch (IOException e)
		{
			lineResult = "start uninstall command exception";
			status = -1;
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
				if (line.contains("Success"))
				{
					status = 1;
					try
					{
						p.waitFor();
						p.destroy();
					} catch (InterruptedException e)
					{
						// ignore
					}
					return status;
				}
			}
		}

		lineResult = lineInfo;
		p.destroy();
		p = null;
		status = -1;
		return -1;
	}

	@Override
	public void run()
	{
		uninstall();
		// remove cache png
		removePng1();
		removePng2();
	}
	
	public void removePng1()
	{
//		if(null == path)
//		{
//			return;
//		}
		ProcessBuilder cmd = null;
		Process processor = null;
		cmd = new ProcessBuilder();
		cmd.command().add("adb");
		if (deviceSerialNo != null)
		{
			cmd.command().add("-s");
			cmd.command().add(deviceSerialNo);
		}
		cmd.command().add("shell");
		cmd.command().add("rm");
		cmd.command().add("-rf");
//		cmd.command().add(path);
		cmd.command().add("/data/local/tmp/*.png"); // add this
		System.out.println(cmd.command());
		try
		{
			cmd.redirectErrorStream(true);
			processor = cmd.start();
			processor.waitFor();
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally
		{
			if(null != processor)
			{
				try
                {
	                processor.destroy();
                } catch (Exception e)
                {
	                e.printStackTrace();
                }
			}
		}
	}
	
	public void removePng2()
	{
//		if(null == path)
//		{
//			return;
//		}
		ProcessBuilder cmd = null;
		Process processor = null;
		cmd = new ProcessBuilder();
		cmd.command().add("adb");
		if (deviceSerialNo != null)
		{
			cmd.command().add("-s");
			cmd.command().add(deviceSerialNo);
		}
		cmd.command().add("shell");
		cmd.command().add("rm");
		cmd.command().add("-rf");
//		cmd.command().add(path);
		cmd.command().add("/sdcard/*.png"); // add this
		System.out.println(cmd.command());
		try
		{
			cmd.redirectErrorStream(true);
			processor = cmd.start();
			processor.waitFor();
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally
		{
			if(null != processor)
			{
				try
                {
	                processor.destroy();
                } catch (Exception e)
                {
	                e.printStackTrace();
                }
			}
		}
	}

}
