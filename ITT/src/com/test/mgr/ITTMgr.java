package com.test.mgr;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.cats.utils.IOUtils;
import com.itt.ITTConstant;



/**
 * @author xblia
 * 2015年10月27日
 */
public class ITTMgr
{
	private static ITTMgr mgr = new ITTMgr();

	private boolean isRunning = true;

	public static ITTMgr getInstance()
	{
		return mgr;
	}

	public boolean isRunning()
	{
		return isRunning;
	}
	
	public void setRunning(boolean isRunning)
	{
		this.isRunning = isRunning;
	}
	
	public void restartITT()
	{
		List<String> cmd = new ArrayList<String>();
		cmd.add("java");
		cmd.add("-jar");
		cmd.add(IOUtils.getUserDir() + File.separator + ITTConstant.SOFT_NAME);
		ProcessBuilder build = new ProcessBuilder(cmd);
		try
        {
	        build.start();
        } catch (IOException e)
        {
	        e.printStackTrace();
        }
		System.exit(0);
	}
}
