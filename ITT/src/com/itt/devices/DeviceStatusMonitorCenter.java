package com.itt.devices;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.cats.utils.TimerValueUtil;
import com.itt.devices.listener.IDeviceStatusListener;
import com.testserver.util.BatteryInfoGetter;
import com.testserver.util.DeviceOperate;

/**
 * @author xblia
 * 2015年10月23日
 */
public class DeviceStatusMonitorCenter extends TimerTask
{
	private static final long HEART_BEAT_INTERVAL = TimerValueUtil.ONE_SECOND * 5;
	
	private List<IDeviceStatusListener> listenerList = new ArrayList<IDeviceStatusListener>();
	
	public DeviceStatusMonitorCenter()
    {
		Timer timer = new Timer();
		timer.schedule(this, 1000, HEART_BEAT_INTERVAL);
    }

	public synchronized boolean registrListener(IDeviceStatusListener device)
	{
		if(listenerList.contains(device))
		{
			return false;
		}
		listenerList.add(device);
		return true;
	}
	
	public synchronized boolean unRegistrListener(IDeviceStatusListener device)
	{
		if(listenerList.contains(device))
		{
			listenerList.remove(device);
			return true;
		}
		return false;
	}
	
	@Override
    public void run()
    {
		List<String> deviceIdList = DeviceOperate.checkdevice();
		for (IDeviceStatusListener listener : listenerList)
        {
	        listener.onLine(deviceIdList.contains(listener.getDeviceId()));
	        listener.onBatteryLevel(BatteryInfoGetter.refreshBatteryInfo(listener.getDeviceId()));
        }
    }

}
