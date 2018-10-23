package com.testserver.util;

/*
 * 解析apk，通过aapt dump  badging获取apk的信息流，从信息流中再获取相应的数据
 * 获取数据是用的正则表达式
 * 
 * */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.intel.cats.test.log.ILog;

public class ParseApk
{
	public static String getInfo(String path)
	{
		String strtemp = "";
		Process p = null;
		try
		{
			// path = "'" + path + "'";
			ProcessBuilder b;

			b = new ProcessBuilder();
			b.command().add("aapt");
			b.command().add("dump");
			b.command().add("badging");
			b.command().add(path);
			try
			{
				p = b.start();
			} catch (IOException e)
			{
				ILog.getLog().logMain(e.getMessage());
			}
			// Process process = run.exec("aapt dump badging " + path);
			BufferedReader bf = new BufferedReader(new InputStreamReader(
			        p.getInputStream(), "UTF-8"));
			String str = "";
			while ((str = bf.readLine()) != null)
			{
				strtemp += str;
			}
			bf.close();
			// process.waitFor();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.err.print(strtemp);
		return strtemp;
	}

	public static String getpackname(String info)
	{
		String packagename = "";
		packagename = patternbyname(info, "package: name='[\\w\\W]*?'");

		int first = packagename.indexOf("'");
		int last = packagename.lastIndexOf("'");
		if (first < last)
		{
			packagename = packagename.substring(first + 1, last);
		}
		return packagename;
	}
	// add apkVersion 
	public static String getApkVersion(String info)
	{
		String version = "";
		version = patternbyname(info, "versionName='[\\w\\W]*?'");

		int first = version.indexOf("'");
		int last = version.lastIndexOf("'");
		if (first < last)
		{
			version = version.substring(first + 1, last);
		}
		return version;
	}
	// add appName(RealName)
	public static String getApkRealName(String info)
	{
		String apkRealName = "";
		apkRealName = patternbyname(info, "application-label:'[\\w\\W]*?'");

		int first = apkRealName.indexOf("'");
		int last = apkRealName.lastIndexOf("'");
		if (first < last)
		{
			apkRealName = apkRealName.substring(first + 1, last);
		}
		return apkRealName;
	}

	public static String getActivity(String info)
	{
		String packagename = "";
		packagename = patternbyname(info,
		        "launchable-activity: name='[\\w\\W]*?'");
		int first = packagename.indexOf("'");
		int last = packagename.lastIndexOf("'");
		if (first == -1)
			return "";
		packagename = packagename.substring(first + 1, last);
		return packagename;
	}

	private static String patternbyname(String strcurr, String name)
	{
		Pattern pattern = Pattern.compile(name);
		Matcher matcher = pattern.matcher(strcurr);
		String str = "";
		if (matcher.find())
		{
			str = matcher.group();
		}
		return str;
	}

}
