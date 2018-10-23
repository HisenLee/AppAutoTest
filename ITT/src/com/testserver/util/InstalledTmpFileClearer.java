package com.testserver.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author xiaobolx
 * 2016年2月23日
 */
public class InstalledTmpFileClearer
{
	private static final String pathReg = "[\\w]*:[\\s]?(?<path>[.]*[/]+.+)";
	private String deviceId;
	private String path;
	
	public InstalledTmpFileClearer(String deviceId)
    {
	    super();
	    this.deviceId = deviceId;
    }
	
	public InstalledTmpFileClearer tryParsePath(String info)
	{
		if(null != path || null == info)
		{
			return this;
		}
		
		String pathInfo = info.trim();
		if(pathInfo.startsWith("pkg:"))
		{
			path = pathInfo.substring("pkg:".length()).trim();
		}else if(pathInfo.matches(pathReg))
		{
			Pattern pattern = Pattern.compile(pathReg);
			Matcher matcher = pattern.matcher(pathInfo);
			if(matcher.matches())
			{
				path = matcher.group("path");
			}
		}
		return this;
	}
	
	
	public void tryDelTmpFile()
	{
//		if(null == path)
//		{
//			return;
//		}
		ProcessBuilder cmd = null;
		Process processor = null;
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
//		cmd.command().add(path);
		cmd.command().add("/data/local/tmp/*.apk"); // add this
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
	
	/*public static void main(String[] args)
    {
	    new DelInstalledTmpFile("07220612").parsePath("pkg: /data/local/tmp/2016_1_28_qingting.apk").delTmpFile();
    }*/
}
