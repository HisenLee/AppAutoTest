package com.testserver.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Battery Info Getter
 * @author xblia
 * 2015年10月26日
 */
public class BatteryInfoGetter
{
	public static int refreshBatteryInfo(String deviceId)
	{
		ProcessBuilder b;
		b = new ProcessBuilder();
		b.command().add("adb");
		b.command().add("-s");
		b.command().add(String.valueOf(deviceId));
		b.command().add("shell");
		b.command().add("dumpsys");
		b.command().add("battery");
		Process deamon = null;
		BufferedReader bf = null;
		try
		{
			deamon = b.start();
			bf = new BufferedReader(new InputStreamReader(
			        deamon.getInputStream(), "UTF-8"));
			String str = "";
			while ((str = bf.readLine()) != null)
			{
				String[] split = str.split(":");
				if (split.length == 2)
				{
					if (split[0].trim().equals("level"))
					{
						return Integer.parseInt(split[1].trim());
					}
				}
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				bf.close();
				deamon.destroy();
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return -1;
	}
}
