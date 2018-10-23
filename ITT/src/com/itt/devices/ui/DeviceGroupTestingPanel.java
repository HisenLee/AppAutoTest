package com.itt.devices.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.cats.ui.custome.ProgressBarEx;
import com.cats.utils.IColorFontUtil;
import com.itt.PairValue;
import com.itt.devices.DeviceForSelection;
import com.itt.devices.listener.IDevicePanelCommonActionListener;
import com.itt.devices.listener.IDeviceTestStatusListener;
import com.itt.testcase.ITaskControler;

/**
 * @author xblia
 * 2015年10月20日
 */
public class DeviceGroupTestingPanel extends JPanel implements IDeviceTestStatusListener, IDevicePanelCommonActionListener, Comparable<DeviceGroupTestingPanel>
{
    private static final long serialVersionUID = 3080538357455698655L;
    
    private ITaskControler controler;
    private List<DeviceForSelection> groupDevices;
    private Map<String, IDeviceTestStatusListener> idToStatusListeners = new HashMap<String, IDeviceTestStatusListener>();
    
    private ProgressBarEx progressBarTotal;
    
    private int layoutRows;
    private int layoutColumns;
    private String deviceName;
    
    public DeviceGroupTestingPanel(String deviceName, List<DeviceForSelection> groupDevices)
    {
    	this.deviceName = deviceName;
    	this.groupDevices = groupDevices;
    	this.setBorder(BorderFactory.createLineBorder(IColorFontUtil.COLOR_DEVICE_BORDER, 1, true));
    	
    	progressBarTotal = new ProgressBarEx(deviceName);
    	progressBarTotal.setPreferredSize(new Dimension(-1, 20));
    }
    
    /**
     * @param shouldColumns
     * @return rows, needBlankPanel
     */
	public PairValue<Integer, Integer> packPanel(int shouldColumns)
    {
		int groupDeviceSize = groupDevices.size();
		int needBlankPanel = groupDeviceSize%shouldColumns;
		layoutColumns = shouldColumns;
		
		layoutRows = needBlankPanel == 0 ? groupDeviceSize/shouldColumns : groupDeviceSize/shouldColumns+ 1;
		
		this.setLayout(new BorderLayout(0, 0));
		
		JPanel topPanel = new JPanel(new BorderLayout(0, 0));
		topPanel.add(progressBarTotal, BorderLayout.CENTER);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(layoutRows, layoutColumns, 12, 12));
		centerPanel.setBackground(IColorFontUtil.COLOR_GRACE_GRAY_WHITE);
		
		if(groupDevices.size() > 1)
		{
			centerPanel.setBackground(Color.WHITE);
		}

		for (int i = 0; i < groupDevices.size(); i++)
        {
	        DeviceTestingPanel deviceTestingPanel = new DeviceTestingPanel(groupDevices.get(i), groupDevices.size() > 1, this);
	        centerPanel.add(deviceTestingPanel);
	        idToStatusListeners.put(groupDevices.get(i).getDeviceId(), deviceTestingPanel);
        }
		if(layoutRows > 1)
		{
			for (int i = 0; i < needBlankPanel; i++)
			{
				centerPanel.add(new JPanel());
			}
		}
		
		this.add(topPanel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		return new PairValue<Integer, Integer>(layoutRows, needBlankPanel);
    }
	
	
	@Override
    public void onStatus(String deviceId, final String info)
    {
		if(deviceId != null)
		{
			IDeviceTestStatusListener testStatusListener = idToStatusListeners.get(deviceId);
			if(null != testStatusListener)
			{
				testStatusListener.onStatus(deviceId, info);
				return;
			}
		}
		
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				//labelInfo.setText(info);
				progressBarTotal.setString(null, info);
			}
		});
    }
	
	@Override
    public void onStatus(String deviceId, int iStatusCode, int wparam, Object lparam)
    {
		IDeviceTestStatusListener testStatusListener = idToStatusListeners.get(deviceId);
		if(null != testStatusListener)
		{
			testStatusListener.onStatus(deviceId, iStatusCode, wparam, lparam);
		}
    }
	
	@Override
    public void onProgress(final int total, final int iTotalGroupTask, final int finished)
    {
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				progressBarTotal.setString(finished + "/" + total, null);
				progressBarTotal.setMaximum(total);
				progressBarTotal.setValue(finished);
			}
		});
		
    }

	public void setControler(ITaskControler controler)
    {
		this.controler = controler;
    }


	@Override
    public void onCommonAction(int actionCode, JComponent component)
    {
		if(actionCode == IDevicePanelCommonActionListener.ACTION_CDE_OPENFOLDER)
		{
			if(null != controler)
			{
				controler.openFolder();
			}
		}
    }
	
	public int getDeviceCount()
	{
		return groupDevices.size();
	}
	
	public String getDeviceName()
	{
		return deviceName;
	}

	@Override
    public int compareTo(DeviceGroupTestingPanel deviceGroupTestingPanel)
    {
		boolean isOtherTwinDevice = deviceGroupTestingPanel.getDeviceCount() %2 == 0;
		if(isOtherTwinDevice)
		{
			return 1;
		}else
		{
			return -1;
		}
    }
}