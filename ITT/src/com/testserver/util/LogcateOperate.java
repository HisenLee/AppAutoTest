package com.testserver.util;

/*
 * 线程不断的将读取的logcat写入文档中
 * 
 * */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.intel.cats.test.log.ILog;
import com.testserver.test.lock.LockStatus;
import com.testserver.test.lock.ScriptLock;

public class LogcateOperate
{
	private String deviceid;
	private String path;
	private Process deamon = null;
	
	private Thread thread = null;
	private ScriptLock stopSync = null;

	public LogcateOperate(String deviceid, String path)
	{
		super();
		this.deviceid = deviceid;
		this.path = path;
		stopSync = new ScriptLock();
	}

	public void finish()
	{
		try
        {
	        if (deamon != null)
	        {
	        	deamon.destroy();
	        }
	        
	        if(thread != null)
	        {
	        	thread.interrupt();
	        }
        } catch (Exception e)
        {
	        e.printStackTrace();
        }
		synchronized(stopSync)
		{
			if(stopSync.getLockStatus() == LockStatus.enNotifyed)
			{
				return;
			}else
			{
				try
                {
	                stopSync.wait(10000);
                } catch (InterruptedException e)
                {
	                e.printStackTrace();
                }
			}
		}
		System.out.println(deviceid + "-------------Logcat finish method end-------------");
	}

	public void start()
	{
		final ScriptLock startSync = new ScriptLock();
		thread = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				ProcessBuilder b;
				if (deviceid == null)
				{
					b = new ProcessBuilder();
					b.command().add("adb");
					b.command().add("logcat");
					b.command().add("-c");
				} else
				{
					b = new ProcessBuilder();
					b.command().add("adb");
					b.command().add("-s");
					b.command().add(deviceid);
					b.command().add("logcat");
					b.command().add("-c");
				}
				try
				{
					deamon = b.start();
				} catch (IOException e)
				{
					ILog.getLog().info(deviceid, e.getMessage());
				}

				b = new ProcessBuilder();
				b.command().add("adb");
				b.command().add("-s");
				b.command().add(deviceid);
				b.command().add("logcat");
				FileOutputStream fos = null;
				BufferedWriter bw = null;
				BufferedReader bf = null;
				try
				{
					fos = new FileOutputStream(path);
					bw = new BufferedWriter(
					        new OutputStreamWriter(fos));
					b.redirectErrorStream(true);
					deamon = b.start();
					synchronized(startSync)
					{
						startSync.notifyAll();
						startSync.setLockStatus(LockStatus.enNotifyed);
					}
					bf = new BufferedReader(
					        new InputStreamReader(deamon.getInputStream(),
					                "UTF-8"));
					String str = "";
					while ((str = bf.readLine()) != null)
					{
						// FileOperate.println(logpath,str);
						bw.write(str + "\n");
						bw.flush();

					}
					deamon.destroy();
				} catch (Exception e)
				{
					e.printStackTrace();
				}finally
				{
					try
                    {
						if(fos != null)fos.close();
	                    if(bw != null)bw.close();
	                    if(bf != null)bf.close();
	                    synchronized(stopSync)
	                    {
	                    	stopSync.notifyAll();
	                    }
	                    System.out.println(deviceid + "--------------LOGCAT-FINISHED--------------");
                    } catch (IOException e)
                    {
	                    e.printStackTrace();
                    }
				}
			}
		});
		thread.start();
		synchronized(startSync)
		{
			if(startSync.getLockStatus() == LockStatus.enNotifyed)
			{
				return;
			}else
			{
				try
                {
	                startSync.wait(10000);
                } catch (InterruptedException e)
                {
	                e.printStackTrace();
                }
			}
		}
	}
	
/*	public static void main(String[] args) throws Exception
    {
		String deviceid = "E8OKBC278696"; 
		String path = "D:\\1_logcat.txt";
	    LogcateOperate logcateOperate = new LogcateOperate(deviceid, path);
	    logcateOperate.start();
	    System.out.println("finish....01");
	    logcateOperate.finish();
	    Util.block(1000);
	    Files.delete(Paths.get(path));
    }*/
}
