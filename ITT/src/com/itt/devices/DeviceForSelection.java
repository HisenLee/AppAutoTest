package com.itt.devices;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.itt.devices.listener.IDeviceStatusListener;

/**
 * @author xblia
 * 2015年10月19日
 */
public class DeviceForSelection implements Serializable, IDeviceStatusListener
{
	
	public enum EnDeviceType
	{
		VIRTUAL_DEVICE,
		BLANK_DEVICE,
		REAL_DEVICE
	}
	
	private static final long serialVersionUID = -8535135397172388610L;

	private EnDeviceType deviceType = EnDeviceType.REAL_DEVICE;
	private String deviceId;
	private String deviceName;
	private String androidVersion;
	private String cpuType;
	private String androidImageSerial;
	private boolean isOnLine = true;
	private int batteryLevel = -1;
	private IDeviceGroup deviceGroup;
	private List<IDeviceStatusListener> statusListeners = new ArrayList<IDeviceStatusListener>();

	public DeviceForSelection(String deviceId, String deviceName, String androidVersion, String cpuType, String androidImageSerial)
    {
	    super();
	    this.deviceId = deviceId;
	    this.deviceName = deviceName;
	    this.androidVersion = androidVersion;
	    this.cpuType = cpuType;
	    this.androidImageSerial = androidImageSerial;
    }
	
	public DeviceForSelection(String deviceId, String deviceName, String androidVersion, String cpuType, String androidImageSerial, EnDeviceType deviceType)
    {
	    super();
	    this.deviceId = deviceId;
	    this.deviceName = deviceName;
	    this.androidVersion = androidVersion;
	    this.cpuType = cpuType;
	    this.androidImageSerial = androidImageSerial;
	    this.deviceType = deviceType;
    }
	
	@Override
	public String getDeviceId()
	{
		return deviceId;
	}

	public String getDeviceName()
	{
		return deviceName;
	}


	public String getAndroidVersion()
	{
		return androidVersion;
	}

	public String getCpuType()
	{
		return cpuType;
	}

	public String getAndroidImageSerial()
	{
		return androidImageSerial;
	}

	public boolean isDeviceOnLine()
	{
		return this.isOnLine;
	}

	@Override
    public int hashCode()
    {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result
	            + ((androidVersion == null) ? 0 : androidVersion.hashCode());
	    result = prime * result
	            + ((deviceId == null) ? 0 : deviceId.hashCode());
	    result = prime * result
	            + ((deviceName == null) ? 0 : deviceName.hashCode());
	    return result;
    }

	@Override
    public boolean equals(Object obj)
    {
	    if (this == obj)
		    return true;
	    if (obj == null)
		    return false;
	    if (getClass() != obj.getClass())
		    return false;
	    DeviceForSelection other = (DeviceForSelection) obj;
	    if (androidVersion == null)
	    {
		    if (other.androidVersion != null)
			    return false;
	    } else if (!androidVersion.equals(other.androidVersion))
		    return false;
	    if (deviceId == null)
	    {
		    if (other.deviceId != null)
			    return false;
	    } else if (!deviceId.equals(other.deviceId))
		    return false;
	    if (deviceName == null)
	    {
		    if (other.deviceName != null)
			    return false;
	    } else if (!deviceName.equals(other.deviceName))
		    return false;
	    return true;
    }

	public void joinGroup(IDeviceGroup androidDeviceGroup)
    {
		this.deviceGroup = androidDeviceGroup;
    }
	
	public IDeviceGroup getDeviceGroup()
	{
		return deviceGroup;
	}
	
	public int getBatteryLevel()
	{
		return batteryLevel;
	}
	
	public EnDeviceType getDeviceType()
	{
		return deviceType;
	}

	public void setDeviceType(EnDeviceType deviceType)
	{
		this.deviceType = deviceType;
	}

	@Override
    public synchronized void onBatteryLevel(int level)
    {
		if(level != -1 && this.batteryLevel != level)
		{
			this.batteryLevel = level;
			for (IDeviceStatusListener listener : statusListeners)
	        {
		        listener.onBatteryLevel(level);
	        }
		}
    }
	
	@Override
    public synchronized void onLine(boolean isOnline)
    {
		if(this.isOnLine != isOnline)
		{
			this.isOnLine = isOnline;
			for (IDeviceStatusListener listener : statusListeners)
			{
				listener.onLine(isOnline);
			}
		}
    }
	
	public  synchronized void registerStatusListener(IDeviceStatusListener deviceStatusListener)
	{
		if(!this.statusListeners.contains(deviceStatusListener))
		{
			statusListeners.add(deviceStatusListener);
		}
	}
	
	public synchronized void removeStatusListener(IDeviceStatusListener deviceStatusListener)
	{
		if(this.statusListeners.contains(deviceStatusListener))
		{
			statusListeners.remove(deviceStatusListener);
		}
	}
	
	public boolean isBlankdevice()
	{
		return deviceType == EnDeviceType.BLANK_DEVICE;
	}
}