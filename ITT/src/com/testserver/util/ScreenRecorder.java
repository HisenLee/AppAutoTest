/**
* 	ScreenRecorder recorder = new ScreenRecorder(null, "1_0_1.mp4", 20);
*	recorder.startRecord();
*	System.out.println(".....");
*	Thread.sleep(20000);
*	System.out.println(".....");
*	recorder.stopRecord();
*	recorder.pullRecordFileToDesktop("D:/1.1.1.1.mp4");
*/
package com.testserver.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.cats.utils.Utils;
import com.intel.cats.test.log.ILog;
import com.itt.ITTConstant;

/**
 * @author xiaobolx
 * 2016年5月20日
 */
public class ScreenRecorder extends Thread
{
	public static final String RECORD_FILE_NAME = "ITT_ScreenRecord.mp4";
	public static final int RECORD_TIME_LIMIT = 60*2;//second
	private static final String BASE_DIR = "/sdcard/";
	private String deviceId;
	private String fileName;
	private int timeLimit;
	private boolean isRunning = true;
	private Process subProcess = null;
	public ScreenRecorder(String deviceId, String fileName, int timeLimit)
	{
		this.deviceId = deviceId;
		this.fileName = fileName;
		this.timeLimit = timeLimit;
	}
	
	public boolean isValidRecorder()
	{
		String intStr = DeviceOperate.getSDKInt(deviceId);
		if(null == intStr || intStr.isEmpty())
		{
			return false;
		}
		try
		{
			int sdkInt = Integer.parseInt(intStr);
			if(sdkInt >= ITTConstant.SDK_ANDROID_5_0)
			{
				return true;
			}
		} catch (NumberFormatException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public void run()
	{
		ProcessBuilder b;

		b = new ProcessBuilder();
		b.command().add("adb");
		if (deviceId != null && !deviceId.isEmpty())
		{
			b.command().add("-s");
			b.command().add(deviceId);
		}
		b.command().add("shell");
		b.command().add("screenrecord");
		b.command().add("--time-limit");
		b.command().add(String.valueOf(timeLimit));
		b.command().add(BASE_DIR + fileName);
		try
		{
			subProcess = b.start();
			printInputStream(subProcess.getInputStream());
		} catch (IOException e)
		{
			ILog.getLog().info(deviceId, e.getMessage());
		}
		try
		{
			subProcess.waitFor();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		subProcess.destroy();
	}
	
	public void pullRecordFileToDesktop(String strDestPath)
	{
		Process p = null;
		try
		{
			ProcessBuilder b;

			b = new ProcessBuilder();
			b.command().add("adb");
			if (deviceId == null || deviceId.equals(""))
			{

			} else
			{
				b.command().add("-s");
				b.command().add(deviceId);
			}
			b.command().add("pull");
			b.command().add(BASE_DIR + this.fileName);
			b.command().add(strDestPath);
			try
			{
				p = b.start();
			} catch (IOException e)
			{
				ILog.getLog().info(deviceId, e.getMessage());
			}
			BufferedReader bf = new BufferedReader(new InputStreamReader(
			        p.getInputStream(), "UTF-8"));
			String str = "";
			while ((str = bf.readLine()) != null)
			{
				ILog.getLog().info(deviceId, str);
			}
			bf.close();
			p.waitFor();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void clearRecordFiles()
	{
		ProcessBuilder cmd = null;
		cmd = new ProcessBuilder();
		cmd.command().add("adb");
		if (deviceId != null)
		{
			cmd.command().add("-s");
			cmd.command().add(deviceId);
		}
		cmd.command().add("shell");
		cmd.command().add("rm");
		cmd.command().add("-rf");
		cmd.command().add(BASE_DIR + "*.mp4");
		System.out.println(cmd.command());
		try
		{
			cmd.redirectErrorStream(true);
			cmd.start();
		} catch (IOException e)
		{
		}
	}
	
	public void startRecord()
	{
		this.start();
	}
	
	public void stopRecord()
	{
		this.isRunning = false;
		this.interrupt();
		if(null != subProcess)
		{
			subProcess.destroy();
		}
		Utils.block(2000);
	}
	
	private void printInputStream(InputStream inputStream)
	{
		if(null == inputStream)
		{
			return;
		}
		byte[] data = new byte[128];
		try
		{
			int readLen = -1;
			do{
				readLen = inputStream.read(data);
				if(readLen != -1)
				{
					System.out.println(new String(data, 0, readLen));
				}
			}while(readLen != -1 && isRunning);
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally
		{
			try
			{
				inputStream.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}