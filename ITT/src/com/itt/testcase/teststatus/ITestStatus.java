package com.itt.testcase.teststatus;



/**
 * @author xblia
 * 2015年10月21日
 */
public abstract class ITestStatus
{
	public static int STATUS_PREPARE				= 0x01;
	public static int STATUS_INSTALL				= 0x10;
	public static int STATUS_LAUNCH					= 0x20;
	public static int STATUS_WAITTING_USER 			= 0x30;	
	public static int STATUS_RANDOMCLICK 			= 0x40;
	public static int STATUS_QUIT					= 0x50;
	public static int STATUS_UNINSTALL				= 0x60;
	public static int STATUS_RECORD_TESTRESULT 		= 0x70;
	
	
	public static int STATUS_RESULT_INSTALL_PASS		= 2^0;
	public static int STATUS_RESULT_INSTALL_FAIL		= 2^1;
	public static int STATUS_RESULT_LAUNCH_PASS			= 2^2;
	public static int STATUS_RESULT_LAUNCH_FAIL			= 2^3;
	public static int STATUS_RESULT_RANDOMCLICK_PASS	= 2^4;
	public static int STATUS_RESULT_RANDOMCLICK_FAIL	= 2^5;
	public static int STATUS_RESULT_QUIT_PASS			= 2^6;
	public static int STATUS_RESULT_QUIT_FAIL			= 2^7;
	public static int STATUS_RESULT_UNINSTALL_PASS		= 2^8;
	public static int STATUS_RESULT_UNINSTALL_FAIL		= 2^9;
	
	public static int STATUS_RESULT_CONTINUETEST		= 2^10;
	public static int STATUS_RESULT_TEST_ON_OTHERDEV	= 2^11;
	
	
	protected ITTTestContext context;
	
	protected ITestStatus(ITTTestContext context)
    {
		this.context = context;
    }
	
	public abstract void doActionWithDevice();
	
	public abstract int getStatusCode();
}
