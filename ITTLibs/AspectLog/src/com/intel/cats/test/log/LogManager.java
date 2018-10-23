package com.intel.cats.test.log;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author xblia 2014年12月9日
 */
public class LogManager
{
	private static final LogManager LOG_MANAGER = new LogManager();
	private static final int QUEUE_MAXLEN = 1000;

	private ArrayBlockingQueue<LogMessager> queue = new ArrayBlockingQueue<LogMessager>(QUEUE_MAXLEN);

	private LogProcessor logProcessor;
	private boolean isRunning = false;
	public static LogManager getInstance()
	{
		return LOG_MANAGER;
	}

	public void pushMsg(LogMessager msg)
	{
		try
		{
			if (queue.size() < QUEUE_MAXLEN)
			{
				queue.put(msg);
			} else
			{
				System.out.println("LogQueue has max 10000 msg, Log module pause.");
			}
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public LogMessager popMsg()
	{
		try
		{
			return queue.take();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public boolean isRunning()
	{
		return isRunning;
	}

	public void setRunning(boolean isRunning)
	{
		this.isRunning = isRunning;
	}

	public void start(String strBasePath, IFatalMsgCallBack fatalMsgCallBack)
	{
		isRunning = true;
		logProcessor = new LogProcessor(strBasePath, fatalMsgCallBack);
		logProcessor.start();
	}

	public void stop()
	{
		isRunning = false;
		if (null != logProcessor)
		{
			logProcessor.stopLog();
		}
	}
}
