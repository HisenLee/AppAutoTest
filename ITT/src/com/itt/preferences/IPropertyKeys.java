package com.itt.preferences;

/**
 * @author xblia
 * 2015年10月19日
 */
public interface IPropertyKeys
{
	String APK_PATH 						= "apk_location";
	String AUTOTEST							= "auto_test";
	String SCREEN_RECORD_TO_MP4				= "screenrecord_to_mp4";
	String TIMEOUT_INSTALL_SECOND 			= "timeout_install";
	String TIMEOUT_UNINSTALL_SECOND 		= "timeout_uninstall";
	String TIMEOUT_LAUNCH_SECOND 			= "timeout_launch";
	String TIMEOUT_TOUCH_SECOND				= "timeout_touch";
	String TIMEOUT_QUIT_SECOND				= "timeout_quit";
	
	String TEST_TIMES_EVERY_APK				= "testtimes_apk";
	String TESTMODE_FIXEDMODE 				= "fixedmode";
	String TESTMODE_LAZYMODE 				= "lazymode";
	String CLEAR_DEVICE		 				= "clear_device";
}
