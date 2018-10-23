package com.testserver.util;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.intel.cats.test.log.Util;

class fileFilter implements FileFilter
{
	private String type = "";

	public fileFilter(String typeName)
	{
		this.type = typeName;
	}

	@Override
	public boolean accept(File pathname)
	{
		if (pathname.getName().endsWith(type))
			return true;
		return false;
	}
}

class filePinyinFilter implements FileFilter
{
	static String regEx = "[\u4e00-\u9fa5]";
	static Pattern pat = Pattern.compile(regEx);

	private String type = "";

	public filePinyinFilter(String typeName)
	{
		this.type = typeName;
	}

	@Override
	public boolean accept(File pathname)
	{
		if (pathname.getName().endsWith(type))
		{
			// 判断是否含中文字符
			String name = pathname.getName();
			Matcher matcher = pat.matcher(name);
			boolean flag = matcher.find();
			if (flag)
				return true;
			else
				return false;
		}
		return false;
	}
}

class fileNotAsciiFilter implements FileFilter
{
	static String regEx = "[^\\w]+";
	static Pattern pat = Pattern.compile(regEx);

	private String type = "";

	public fileNotAsciiFilter(String typeName)
	{
		this.type = typeName;
	}

	@Override
	public boolean accept(File pathname)
	{
		if (pathname.getName().endsWith(type))
		{
			// 判断是否含中文字符
			String name = pathname.getName();
			Matcher matcher = pat.matcher(name);
			boolean flag = matcher.find();
			if (flag)
				return true;
			else
				return false;
		}
		return false;
	}
}

public class Rename
{
	public static final String TEMP_MARK = "11858668";
	@SuppressWarnings("unused")
    private static fileFilter filefilter = new fileFilter("apk");
	@SuppressWarnings("unused")
    private static filePinyinFilter filepinyinfilter = new filePinyinFilter(
	        "apk");

	public static boolean rename(String filePath, String newFileName,
	        String fileSuffix)
	{
		boolean flag = false;
		File file = new File(filePath);
		if (file != null && file.exists() && file.isFile()
		        && file.getName().endsWith(fileSuffix))
		{
			String newPath = file.getParent() + File.separator + newFileName
			        + '.' + fileSuffix;
			flag = file.renameTo(new File(newPath));
		}
		return flag;
	}

	public static boolean rename(File file, String newFileName,
	        String fileSuffix)
	{
		boolean flag = false;
		if (file != null && file.exists() && file.isFile()
		        && file.getName().endsWith(fileSuffix))
		{
			String newPath = file.getParent() + File.separator + newFileName;
			flag = file.renameTo(new File(newPath));
		}
		return flag;
	}

	@SuppressWarnings("unused")
    private static String convertNamebyPackagename(File file)
	{
		String info = ParseApk.getInfo(file.getAbsolutePath());
		String packageName = ParseApk.getpackname(info);
		return packageName;
	}

	// add at 2014.10.16 非字母，数字，和标点符号就删除，兼容所有文字了
	static String regEx = "[^\\w.]+";
	static Pattern pat = Pattern.compile(regEx);
	static String currentPid = Util.getPid();

	private static String convertNamebyNotAscii(File file)
	{
		String pinyin = file.getName();
		Matcher matcher = pat.matcher(pinyin);
		boolean flag = matcher.find();
		if (flag)
		{
			// pinyin=Calendar.getInstance().getTimeInMillis()+'_'+pinyin.replaceAll("[^\\w.]+",
			// "");
			do
			{
				pinyin = TEMP_MARK + "_" + currentPid + "_"
				        + System.currentTimeMillis() + ".apk";
				Util.block(1);
			} while (new File(file.getParent() + File.separator + pinyin)
			        .exists());
			// Add by xblia 2014-12-03, distinguish Temp apk file
		}
		return pinyin;
	}

	/**
	 * 如果文件名存在中文，则复制一份备份.apktemp，并将原始文件名改为英文名
	 * 
	 * @param file
	 * @return 英文文件名
	 */
	/*
	 * public static String copyAndRenameIfChineseName(File file){ String
	 * newfilename=convertNamebyNotAscii(file); String
	 * oldfilename=file.getName(); if(!oldfilename.equals(newfilename)){ String
	 * tmpfilepath
	 * =file.getAbsolutePath()+System.currentTimeMillis()+".apktemp";//Multi
	 * process synchronized for xblia 2014-12-03 File tmpfile=new
	 * File(tmpfilepath); FileOperate.filechannelcopy(file,tmpfile);
	 * file.renameTo(new File(file.getParent()+File.separator+newfilename)); new
	 * File(tmpfilepath).renameTo(new
	 * File(tmpfile.getParent()+File.separator+oldfilename));//rename original
	 * name by xblia 2014-11-21 //tmpfile.renameTo(new
	 * File(tmpfile.getParent()+File.separator+oldfilename)); return
	 * newfilename; } return oldfilename; }
	 */

	/**
	 * refact by xblia 2014-12-18
	 * 
	 * @param file
	 * @return 英文文件名
	 */
	public static String copyAndRenameIfChineseName(File file)
	{
		String newfilename = convertNamebyNotAscii(file);
		String oldfilename = file.getName();
		if (!oldfilename.equals(newfilename))
		{
			String tmpfilepath = file.getParent() + File.separator
			        + newfilename;
			File tmpfile = new File(tmpfilepath);
			FileOperate.filechannelcopy(file, tmpfile);
			return newfilename;
		}
		return oldfilename;
	}
}
