package com.itt.testcase;


/**
 * @author xblia
 * 2015年10月22日
 */
public interface ICrashObserable
{
	String getCrashReason(String packageName);
	boolean isCrashed(String packageName);
	boolean resetCrashInfo();
}
