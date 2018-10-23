package com.itt.devices.listener;

/**
 * @author xblia
 * 2015年10月20日
 */
public interface IDeviceTestStatusListener
{
	/**
	 * @param info description
	 */
	void onStatus(String deviceId, String info);
	
	/**
	 * @param iStatusCode
	 * @param wparam start or stop
	 * @param lparam ? is main test status?
	 */
	void onStatus(String deviceId, int iStatusCode, int wparam, Object lparam);
	
	/**
	 * Progress notify
	 */
	void onProgress(int total, int iTotalGoupTask, int finished);
}
