package com.testserver.util;


/**
 * @author xiaobolx
 * 2015年11月27日
 */
public interface ICrashMonitor
{
	void addCallback(CrashCallback cb);

	void start();

	void finish();
}
