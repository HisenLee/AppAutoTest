package com.itt.devices.listener;

/**
 * @author xblia
 * 2015年10月23日
 */
public interface IDeviceStatusListener
{
	String getDeviceId();
	void onLine(boolean isOnline);
	void onBatteryLevel(int level);
}
