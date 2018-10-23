package com.intel.cats.test.log;

import java.io.Serializable;

/**
 * 每个设备应该打到单独的文件夹里
 * @author xblia
 * 2014年12月18日
 */
public class LogMessager implements Serializable
{
    private static final long serialVersionUID = 1L;
	public static final String DEFAULT_CATEGORY = "_main_";
    private String logMsg;
    private String category;
    private LogLevel level;
    
	public LogMessager(String logMsg)
    {
	    super();
	    this.category = DEFAULT_CATEGORY;
	    this.logMsg = logMsg;
	    this.level = LogLevel.INFO;
    }
	public LogMessager(String logMsg, String category, LogLevel logLevel)
    {
	    super();
	    this.logMsg = logMsg;
	    this.category = category;
	    this.level = logLevel;
    }
	public String getLogMsg()
	{
		return logMsg;
	}
	public void setLogMsg(String logMsg)
	{
		this.logMsg = logMsg;
	}
	public String getCategory()
	{
		return category;
	}
	public void setCategory(String category)
	{
		this.category = category;
	}
	public LogLevel getLevel()
	{
		return level;
	}
	public void setLevel(LogLevel level)
	{
		this.level = level;
	}
	
	public void generate()
	{
		String logLevelDesc = "Normal";
		if(level == LogLevel.FATAL)
		{
			logLevelDesc = "_FATAL_";
		}else if(level == LogLevel.ERROR)
		{
			logLevelDesc = "_ERROR_";
		}
		else if(level == LogLevel.INFO)
		{
			logLevelDesc = "_INFO_";
		}
		else if(level == LogLevel.DEBUG)
		{
			logLevelDesc = "_DEBUG_";
		}
		logMsg = packingLog(logLevelDesc, category == null ? "":category, logMsg == null ? "":logMsg);
	}
	
	private String packingLog(String logLevelDesc, String device, String info)
    {
	    StringBuffer buffer = new StringBuffer();
	    buffer.append(Util.getTime());
	    buffer.append(" ");
	    buffer.append(logLevelDesc);
	    buffer.append(" ");
		if (!device.isEmpty())
		{
			buffer.append(" ");
			buffer.append(device);
			buffer.append(" ");
		}
	    buffer.append(info);
	    buffer.append(System.getProperty("line.separator"));
	    return buffer.toString();
    }
	
	
}
