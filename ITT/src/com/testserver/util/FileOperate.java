package com.testserver.util;

/*
 * 文件操作
 * 
 * 
 * */
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

import com.cats.utils.IOUtils;
import com.intel.cats.test.log.ILog;

public class FileOperate
{
	// 对结果存放的文件夹初始化，新建tmp文件夹
	public static String initFile(String resultpath)
	{
		// mkdir tmp/
		// create file tmp/result.txt
		File resultfile = new File(resultpath);
		String tmppath = "";
		if (!resultfile.exists())
		{
			tmppath = IOUtils.getUserDir() + File.separator + "tmp";
		} else
		{
			tmppath = resultpath + File.separator + "tmp";
		}
		System.err.println(IOUtils.getUserDir());
		File tmp = new File(tmppath);
		if (!tmp.exists())
		{
			tmp.mkdir();
			tmp.setWritable(true);
			tmp.setExecutable(true);
			tmp.setReadable(true);
		} else
		{
			// delefile(tmp); clear direct need user action.
		}
		return tmppath;

	}

	// 删除文件夹下的文件
	@SuppressWarnings("unused")
    private static void delefile(File file)
	{
		if (file != null && file.exists())
		{
			File[] fs = file.listFiles();
			for (File f : fs)
			{
				if (f.isFile() && !f.getName().endsWith(".xlsx"))
				{
					f.delete();
				} else if (f.isDirectory())
				{
					delefile(f);
				}
			}
		}
	}

	// 删除单个文件
	public static void deletesinglefile(String path)
	{
		Path file = Paths.get(path);
		if(Files.exists(file))
		{
			try
			{
				Files.delete(file);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	// 复制单个文件
	public static void filechannelcopy(File s, File t)
	{
		if (t.exists())
		{
			return;
		}
		FileInputStream fi = null;
		FileOutputStream fo = null;
		FileChannel in = null;
		FileChannel out = null;
		try
		{
			fi = new FileInputStream(s);
			fo = new FileOutputStream(t);
			in = fi.getChannel();
			out = fo.getChannel();
			in.transferTo(0, in.size(), out);
		} catch (Exception e)
		{
			ILog.getLog().log(e);
		} finally
		{
			try
			{
				if (fi != null)
					fi.close();
				if (in != null)
					in.close();
				if (fo != null)
					fo.close();
				if (out != null)
					out.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	// 从给定的文件夹中找到apk文件
	public static File[] FindApkFromFile(String excelPath, String apkpath)
	{
		File apkfile = new File(apkpath);
		if (!apkfile.exists())
		{
			return null;
		}
		final List<String> alreadyTestedApk = exceloperate
		        .readApkFileName(excelPath);
		File[] listapk = apkfile.listFiles(new FileFilter()
		{
			@Override
			public boolean accept(File pathname)
			{
				// add by xblia 2014-12-03, 不扫描Temp mark apk file
				if (pathname.getName().endsWith(".apk")
				        && !pathname.getName().startsWith(Rename.TEMP_MARK)
				        && !alreadyTestedApk.contains(pathname.getName()))
				{
					return true;
				} else
				{
					return false;
				}
			}
		});
		return listapk;
	}

	// 创建一个文件
	public static void createFile(String filepath)
	{
		File f = new File(filepath);
		try
		{
			f.createNewFile();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	// 读取测试完成的进度
	public synchronized static String readprogress(String filepath, String key)
	{
		Properties pros = new Properties();
		try
		{
			InputStream in = new BufferedInputStream(new FileInputStream(
			        filepath));
			pros.load(in);
			String value = pros.getProperty(key);
			if (in != null)
			{
				in.close();
			}
			return value;

		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
			return null;
		} catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}

	}

	// 写入测试进度
	public static synchronized void writeprogress(String filepath, String key,
	        String value)
	{
		Properties pros = new Properties();
		InputStream in = null;
		try
		{
			in = new FileInputStream(filepath);
			pros.load(in);
			OutputStream fos = new FileOutputStream(filepath);
			pros.setProperty(key, value);
			pros.store(fos, "");
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally
		{
			if (in != null)
			{
				try
				{
					in.close();
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	// 输出内容到屏幕并追加保存到文件
	private static void privatePrintln(String fileName, String content,
	        boolean usePrefix)
	{
		String prefix = usePrefix ? "[" + Thread.currentThread().getName()
		        + "] " : "";
		System.out.println(prefix + content);
		try
		{
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName,
			        true));
			writer.append(prefix + content);
			writer.newLine();
			writer.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void println(String fileName, String content)
	{
		privatePrintln(fileName, content, false);
	}

	public static void println(String fileName, String content,
	        boolean usePrefix)
	{
		privatePrintln(fileName, content, usePrefix);
	}
}