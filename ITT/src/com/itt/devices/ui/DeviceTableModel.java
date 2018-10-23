package com.itt.devices.ui;

import java.util.List;

import com.cats.ui.custome.table.JCheckboxTableModel;
import com.cats.utils.Utils;
import com.itt.devices.DeviceForSelection;

/**
 * @author xblia
 * 2015年10月22日
 */
public class DeviceTableModel extends JCheckboxTableModel
{
    private static final long serialVersionUID = 1L;

    private String[] COLUMNS = {"Seq", "Device Name", "Android Version", "CPU Type", "Remaining Battery"};
    private List<DeviceForSelection> deviceList;
    
	public DeviceTableModel(List<DeviceForSelection> fileList)
    {
		this.deviceList = fileList;
    }

	@Override
    public String getColumnName(int column)
    {
	    return COLUMNS[column];
    }

	@Override
	public int getColumnCount()
	{
		return COLUMNS.length;
	}

	@Override
	public int getRowCount()
	{
		return deviceList.size();
	}

	@Override
	public Object getValueAt(int row, int column)
	{
		DeviceForSelection device = deviceList.get(row);
		switch (column)
        {
		case 0:
			if(null == device.getDeviceId())
			{
				return -1;
			}
			return row+1;
		case 1:
			return device.getDeviceName();
		case 2:
			return device.getAndroidVersion();
		case 3:
			return device.getCpuType();
		case 4:
			return device.getBatteryLevel() == -1 ? "": device.getBatteryLevel() + "%";
		default:
			break;
		}
		return null;
	}
	
	@Override
    public int getValidSelectedRows(int[] selectedRows)
    {
		int validSelectedRowCount = 0;
		int totalRows = deviceList.size();
		DeviceForSelection selectDevice;
		for (int i = 0; i < selectedRows.length; i++)
        {
	        int iIndex = selectedRows[i];
	        if(iIndex < totalRows)
	        {
	        	selectDevice = deviceList.get(iIndex);
	        	if(!Utils.isEmpty(selectDevice.getDeviceId()))
	        	{
	        		validSelectedRowCount++;
	        	}
	        }
        }
	    return validSelectedRowCount;
    }

	@Override
    public int getValidTotalRows()
    {
		int iCount = 0;
		for (DeviceForSelection selectDevice : deviceList)
        {
	        if(selectDevice.getDeviceId() != null)
	        {
	        	iCount++;
	        }
        }
	    return iCount;
    }
}
