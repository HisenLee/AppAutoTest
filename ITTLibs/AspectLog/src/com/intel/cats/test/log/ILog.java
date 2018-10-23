package com.intel.cats.test.log;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author xblia 2014年12月9日
 */
public class ILog implements Thread.UncaughtExceptionHandler
{
	private static ILog logger = new ILog();
	
	public ILog()
    {
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	public static ILog getLog()
	{
		return logger;
	}

	public void fatal(String info)
	{
		log(LogMessager.DEFAULT_CATEGORY, LogLevel.FATAL, info);
	}
	
	public void logToUi(String info)
	{
		log(LogMessager.DEFAULT_CATEGORY, LogLevel.FATAL, info);
	}

	public void info(String categroy, String... info)
	{
		log(categroy, LogLevel.INFO, info);
	}

	public void logMain(String... info)
	{
		log(LogMessager.DEFAULT_CATEGORY, LogLevel.INFO, info);
	}

	public void log(Throwable e)
	{
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		log(LogMessager.DEFAULT_CATEGORY, LogLevel.ERROR, sw.toString());
	}

	@Override
	public void uncaughtException(Thread t, Throwable e)
	{
		log(e);
	}

	public void log(String category, LogLevel logLevel, String... info)
	{
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < info.length; i++)
		{
			buffer.append(info[i] + " ");
		}
		LogManager.getInstance().pushMsg(
		        new LogMessager(buffer.toString(), category, logLevel));
	}
}
