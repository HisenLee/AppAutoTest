package com.testserver.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.intel.cats.test.log.ILog;

public class DeviceOperate
{
	// 获取adb存在的设备信息，放入ArrayList
	public static ArrayList<String> checkdevice()
	{
		@SuppressWarnings("unused")
        String strtemp = "";
		ArrayList<String> devices = new ArrayList<String>();
		Runtime run = Runtime.getRuntime();
		try
		{
			// path = "\"" + path + "\"";
			Process process = run.exec("adb devices");
			BufferedReader bf = new BufferedReader(new InputStreamReader(
			        process.getInputStream(), "UTF-8"));
			String str = "";
			while ((str = bf.readLine()) != null)
			{
				strtemp += str;
				String[] split = str.split("\t");
				if (split.length > 1)
				{
					if (split[1].equals("device"))
					{
						devices.add(split[0].trim());
					}

				}
			}
			bf.close();
			// process.waitFor();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return devices;
	}

	// 根据每个deviceid，找它的某个属性的值
	public static String findtype(String deviceid, String type)
	{
		Runtime run = Runtime.getRuntime();
		try
		{
			// path = "\"" + path + "\"";
			String command = null;
			if(deviceid != null)
			{
				command = "adb -s " + deviceid + " shell getprop " + type;
			}
			else
			{
				command = "adb shell getprop " + type;
			}
			Process process = run.exec(command);
			BufferedReader bf = new BufferedReader(new InputStreamReader(
			        process.getInputStream(), "UTF-8"));
			String str = "";
			if ((str = bf.readLine()) != null)
			{
				return str;
			}

			bf.close();
			// process.waitFor();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	// 根据给定的model##@##version查询，对应的型号的devicesid
	public static ArrayList<String> getDeviceInfo(String ModuVer,
	        ArrayList<String> deviceids)
	{
		ArrayList<String> dev = new ArrayList<String>();
		if (null == ModuVer || ModuVer.trim().isEmpty())// Modify by xblia
														// 2014-12-30 if not
														// matched, test stoped.
		{
			for (String s : deviceids)
			{
				dev.add(s);
			}
		} else
		{
			String[] split = ModuVer.split("##@##");
			for (String s : deviceids)
			{
				if (split.length > 1)
				{
					if (findtype(s, "ro.product.model").equals(split[0])
					        && findtype(s, "ro.build.version.release").equals(
					                split[1]) && checkdevice(s))
					{
						dev.add(s);
					}
				} else
				{
					break;
				}
			}
		}
		return dev;
	}

	public static boolean checkdevice(String deviceid)
	{
		boolean isconnect = false;

		ProcessBuilder b;
		b = new ProcessBuilder();
		b.command().add("adb");
		b.command().add("-s");
		b.command().add(deviceid);
		b.command().add("shell");
		b.command().add("ps");

		Process deamon = null;
		try
		{
			deamon = b.start();
		} catch (IOException e)
		{
			ILog.getLog().info(deviceid, e.getMessage());
		}

		BufferedReader bf;
		try
		{
			bf = new BufferedReader(new InputStreamReader(
			        deamon.getInputStream(), "UTF-8"));
			String str = "";
			while ((str = bf.readLine()) != null)
			{
				String[] split = str.split(" ");
				if (split.length > 0)
				{
					String packagename = split[split.length - 1];
					if (packagename.equals("com.android.systemui"))
					{
						isconnect = true;
						break;
					}
				}
			}

		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return isconnect;
	}

	/**
	 * add by xblia 2015-04-20
	 * 
	 * @param deviceId
	 * @return
	 */
	public static String getDeviceVersion(String deviceId)
	{
		String versionInfo = "";
		List<String> cmdList = new ArrayList<String>();
		cmdList.add("adb");
		if (deviceId != null && !deviceId.isEmpty())
		{
			cmdList.add("-s");
			cmdList.add(deviceId);
		}
		cmdList.add("shell");
		cmdList.add("getprop");
		cmdList.add("ro.build.display.id");
		ProcessBuilder processBuilder = null;
		Process process = null;
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;
		try
		{
			processBuilder = new ProcessBuilder(cmdList);
			process = processBuilder.start();
			inputStream = process.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream);
			bufferedReader = new BufferedReader(inputStreamReader);
			String line = null;
			while ((line = bufferedReader.readLine()) != null
			        && !line.isEmpty())
			{
				versionInfo = line;
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			if (null != inputStream)
			{
				try
				{
					inputStream.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			if (null != inputStreamReader)
			{
				try
				{
					inputStreamReader.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			if (null != bufferedReader)
			{
				try
				{
					bufferedReader.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}

			if (null != process)
			{
				process.destroy();
			}
		}
		return versionInfo;
	}

	/**
	 * add by xblia 2015-05-19
	 * 
	 * @param ModuVer
	 * @param deviceids
	 * @return
	 */
	public static Set<String> getDeviceVenders(ArrayList<String> deviceids)
	{
		Set<String> dev = new HashSet<String>();
		for (String s : deviceids)
		{
			String model = findtype(s, "ro.product.model");
			String version = findtype(s, "ro.build.version.release");
			dev.add(model + "##@##" + version);
		}
		return dev;
	}

	public static String getDeviceOSVer(String deviceId)
	{
		return findtype(deviceId, "ro.build.version.release");
	}

	public static String getDeviceName(String deviceId)
	{
		return findtype(deviceId, "ro.product.model");
	}

	public static String getCPUType(String deviceId)
	{
		return findtype(deviceId, "ro.product.cpu.abi");
	}
	
	public static String getSDKInt(String deviceId)
	{
		return findtype(deviceId, "ro.build.version.sdk");
	}
}