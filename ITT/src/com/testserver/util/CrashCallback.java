package com.testserver.util;

/*
 * crashcallback 接口定义了onProcessDied
 * */
public interface CrashCallback
{
	void onProcessDied(String packageName, String serialNo, String crashtype);
}
