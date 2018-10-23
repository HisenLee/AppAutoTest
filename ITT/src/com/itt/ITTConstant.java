package com.itt;

import trialversion.Trialversion;


/**
 * @author xblia
 * 2015年10月19日
 */
public interface ITTConstant
{
	int VERSION_CODE = 41;
	
	String VERSION  = "v4.1";
	
	String TITLE = "Intel ® Interactive Test Tool " + VERSION + (Trialversion.isTrialVersion ? " (Trial Version)":"");
	
	String TEMPFILE_MARK = "ITT_Testing_";

	String SOFT_NAME = "InteractiveTestTool.jar";

	String LOCAL_CHARSET = "utf-8";
	
	byte ALLOW_LOW_BATTERY_LEVEL = 10;
	
	int APK_FULLPANEL_COUNT = 9;
	int DEVICE_FULLPANEL_COUNT = 10;
	
	
	int SDK_ANDROID_5_0 = 21;
	int SDK_ANDROID_6_0 = 23;
}
