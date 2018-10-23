package com.intel.cats.test.log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * @author xblia
 * 2014年12月9日
 */
public class LogProcessor extends Thread
{
	private String strBasePath;
	private IFatalMsgCallBack fatalMsgCallBack;
	
	public LogProcessor(String strBasePath, IFatalMsgCallBack fatalMsgCallBack)
    {
	    super();
	    this.strBasePath = strBasePath;
	    this.fatalMsgCallBack = fatalMsgCallBack;
    }
	
	public void startLog()
	{
		this.start();
	}
	
	public void stopLog()
	{
		LogManager.getInstance().pushMsg(new LogMessager("log end"));
	}

	@Override
	public void run()
	{
		while(LogManager.getInstance().isRunning())
		{
			LogMessager logMsg = LogManager.getInstance().popMsg();
			if(logMsg != null)
			{
				filteAndControlFatalMsg(logMsg);
				writeLogToDisk(logMsg);
			}
		}
	}
	
	/**
	 * add by xblia 2015-04-10, notify important module fatal msg;
	 * @param logMsg
	 */
	public void filteAndControlFatalMsg(LogMessager logMsg)
	{
		if(logMsg.getLevel().equals(LogLevel.FATAL))
		{
			if(null != fatalMsgCallBack)
			{
				fatalMsgCallBack.fatalCallBack(logMsg.getLogMsg());
			}
		}
	}

	private void writeLogToDisk(LogMessager logMsg)
    {
		logMsg.generate();
		//High Line fatal or error message.
		if(logMsg.getLevel() != null && (logMsg.getLevel().equals(LogLevel.ERROR) || logMsg.getLevel().equals(LogLevel.FATAL)))
		{
			System.err.println(logMsg.getLogMsg());
		}else
		{
			System.out.print(logMsg.getLogMsg());
		}
		String strFilePath = getLogPathFullPath(logMsg.getCategory());
		FileOutputStream fos = null;
		try
        {
	        fos = new FileOutputStream(strFilePath, true);
	        fos.write(logMsg.getLogMsg().getBytes());
	        fos.flush();
        } catch (FileNotFoundException e)
        {
	        e.printStackTrace();
        } catch (IOException e)
        {
	        e.printStackTrace();
        }catch(Exception e)
		{
        	e.printStackTrace();
		}finally
        {
        	Util.closeRes(fos);
        }
    }
	
	private String getLogPathFullPath(String category)
    {
		File baseDir = new File(strBasePath + File.separator + "aspect");
		if(!baseDir.exists())
		{
			baseDir.mkdirs();
		}
		
		String strLogFullPath = baseDir.getAbsolutePath() + File.separator + Util.getCurrentDay() + "_" + category + ".log";
		if(!new File(strLogFullPath).exists())
		{
			try
            {
	            new File(strLogFullPath).createNewFile();
            } catch (IOException e)
            {
	            e.printStackTrace();
            }
		}
	    return strLogFullPath;
    }
}
