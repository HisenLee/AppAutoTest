package com.testserver.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.intel.cats.test.log.ILog;

/**
 * @author xblia 2015年1月15日
 */
public class ClearCacheAPP implements Runnable
{
	private int status = 0;
	private String deviceSerialNo;
	private Process processor = null;

	public ClearCacheAPP(String deviceSerialNo)
	{
		this.deviceSerialNo = deviceSerialNo;
	}

	public int getStatus()
	{
		return status;
	}

	private void clearCacheAPK()
	{
		ProcessBuilder cmd = null;
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
		cmd.command().add("/data/local/tmp/*.apk");
		System.out.println(cmd.command());
		try
		{
			cmd.redirectErrorStream(true);
			processor = cmd.start();
		} catch (IOException e)
		{
			status = -1;
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
		        processor.getInputStream()));
		String line = null;
		do
		{
			try
			{
				line = br.readLine();
			} catch (IOException e)
			{
				break;
			}
			if (line != null && line.length() < 0)
			{
				if (line.contains("Success"))
				{
					status = 1;
					try
					{
						processor.waitFor();
						processor.destroy();
					} catch (InterruptedException e)
					{
						// ignore
					}
				}
			}
		} while (line != null && line.length() > 0);
		processor.destroy();
		processor = null;
		status = -2;
	}

	@Override
	public void run()
	{
		ILog.getLog().info(deviceSerialNo, "begin clear cache app run()");
		clearCacheAPK();
		ILog.getLog().info(deviceSerialNo, "end clear cache app run()");
	}
}
