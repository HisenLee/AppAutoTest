package com.itt.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.cats.ui.alertdialog.AlertDialog;
import com.cats.ui.alertdialog.AlertDialogOptions;
import com.cats.ui.graceframe.ICON_STYLE;
import com.cats.ui.graceframe.IGraceFullyFrameBase;
import com.cats.utils.IColorFontUtil;
import com.cats.utils.SwingUtil;
import com.itt.ITTConstant;
import com.itt.PairValue;
import com.itt.apks.ui.FileForSelection;
import com.itt.devices.DeviceForSelection;
import com.itt.devices.DeviceStatusMonitorCenter;
import com.itt.devices.ui.DeviceGroupTestingPanel;
import com.itt.preferences.UserParam;
import com.itt.testcase.AndroidDeviceGroup;
import com.itt.testcase.ITaskControler;
import com.test.mgr.ITTMgr;

/**
 * @author xblia
 * 2015年10月20日
 */
public class ITTTestingFrame extends IGraceFullyFrameBase
{
	private static final long serialVersionUID = -7881292413692328851L;
	private static final int BANNER_HEIGHT = 70;
	private int width = 575;
	private int height = 280;
	
	private List<ITaskControler> taskControlerList = new ArrayList<ITaskControler>();
	
	public ITTTestingFrame()
    {
		super();
		this.setIconImage(SwingUtil.genImageResource(ITTMainFrameUI.class, "/icon/icon-interactive_blue.png"));
		this.setSize(width, height);
		this.setBackground(Color.WHITE);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }
	
	public void initTestComponent(List<DeviceForSelection> selectDevices, List<FileForSelection> selectFiles)
	{
		registeDeviceStatusListener(selectDevices);
		Map<String, List<DeviceForSelection>> deviceGrops = groupDevices(selectDevices);
		Set<Entry<String, List<DeviceForSelection>>> entrySet = deviceGrops.entrySet();
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		mainPanel.setBackground(Color.WHITE);
		
		List<DeviceGroupTestingPanel> groupTestingPanels = new ArrayList<DeviceGroupTestingPanel>(); 
		for (Entry<String, List<DeviceForSelection>> deviceGroupEntry : entrySet)
        {
			String groupName = deviceGroupEntry.getKey();
			List<DeviceForSelection> groupDevices = deviceGroupEntry.getValue();
			
			DeviceGroupTestingPanel deviceTestingPanel = new DeviceGroupTestingPanel(groupName, groupDevices);
			AndroidDeviceGroup androidDeviceGroup = new AndroidDeviceGroup(groupDevices, selectFiles, deviceTestingPanel, UserParam.getBasepath());
			androidDeviceGroup.start();
			
			deviceTestingPanel.setControler(androidDeviceGroup);
			groupTestingPanels.add(deviceTestingPanel);
			taskControlerList.add(androidDeviceGroup);
        }

		boolean isTwinColumn = layoutDeviceGroupPanel(selectDevices.size(), entrySet, mainPanel, groupTestingPanels);
		super.initBaseFrame(ITTConstant.TITLE, isTwinColumn ? ICON_STYLE.ITT_BANNER_LARGER : ICON_STYLE.ITT_BANNER_SMALLER);
		
		this.add(mainPanel, BorderLayout.CENTER);
		this.setVisible(true);
	}

	private boolean layoutDeviceGroupPanel(int devicesCount, Set<Entry<String, List<DeviceForSelection>>> entrySet,
            JPanel mainPanel, List<DeviceGroupTestingPanel> groupTestingPanels)
    {
		int shouldColumns = devicesCount >= 3 ? 2 : 1;
		int totalRows = 0;
		GridBagLayout gridBagLayout = new GridBagLayout();
		int[] columnWidth = new int[shouldColumns];
		if(shouldColumns > 1)
		{
			for (int i = 0; i < shouldColumns; i++)
			{
				columnWidth[i] = this.width * shouldColumns;
			}
		}
		
		gridBagLayout.columnWidths = columnWidth;
		GridBagConstraints bagConstraints = new GridBagConstraints();
		bagConstraints.fill = GridBagConstraints.BOTH;
		bagConstraints.insets = new Insets(1, 7, 1, 7);
	    mainPanel.setLayout(gridBagLayout);
	    Collections.sort(groupTestingPanels);
	    
	    List<DeviceGroupTestingPanel> groupTestingPanelTemp = new ArrayList<DeviceGroupTestingPanel>();
	    for (DeviceGroupTestingPanel deviceGroupTestingPanel : groupTestingPanels)
        {
	    	groupTestingPanelTemp.add(deviceGroupTestingPanel);
        }
	    
	    int rows = 0;
	    int column = 0;
	    int needBlankDevice = 0;
	    while(groupTestingPanelTemp.size() > 0)
	    {
	    	DeviceGroupTestingPanel deviceGroupTestingPanel = null;
	    	deviceGroupTestingPanel = getPanelBySingle(groupTestingPanelTemp, false);
	    	PairValue<Integer, Integer> rowsAndBlankItems = deviceGroupTestingPanel.packPanel(shouldColumns);
	    	int needRows = rowsAndBlankItems.getT();
	    	totalRows += needRows;
	    	needBlankDevice = rowsAndBlankItems.getF();
	    	
	    	JLabel deviceNameLabel = new JLabel(deviceGroupTestingPanel.getDeviceName(), JLabel.LEFT);
	    	deviceNameLabel.setForeground(IColorFontUtil.COLOR_GRACE_GRAY);
	    	
	    	bagConstraints.gridx = rows;
	    	bagConstraints.gridy = column;
	    	bagConstraints.weightx = 0;
	    	bagConstraints.weighty = 0;
	    	bagConstraints.gridwidth = deviceGroupTestingPanel.getDeviceCount() >= 2 ? 2 : 1;
	    	mainPanel.add(deviceNameLabel, bagConstraints);
	    	bagConstraints.gridx = rows;
	    	bagConstraints.gridy = column+1;
	    	bagConstraints.weightx = 1;
	    	bagConstraints.weighty = needRows;
	    	bagConstraints.gridwidth = deviceGroupTestingPanel.getDeviceCount() >= 2 ? 2 : 1;
	    	bagConstraints.gridheight = needRows;
	    	mainPanel.add(deviceGroupTestingPanel, bagConstraints);
	    	
	    	if(needBlankDevice > 0)
	    	{
	    		deviceGroupTestingPanel = getPanelBySingle(groupTestingPanelTemp, true);
	    		if(null != deviceGroupTestingPanel)
	    		{
	    			JLabel deviceNameLabel1 = new JLabel(deviceGroupTestingPanel.getDeviceName(), JLabel.LEFT);
	    			deviceNameLabel1.setForeground(IColorFontUtil.COLOR_GRACE_GRAY);
	    			bagConstraints.gridx = rows+1;
	    	    	bagConstraints.gridy = column;
	    	    	bagConstraints.weightx = 0;
	    	    	bagConstraints.weighty = 0;
	    	    	bagConstraints.gridwidth = 1;
	    	    	bagConstraints.gridheight = 1;
	    	    	mainPanel.add(deviceNameLabel1, bagConstraints);
	    	    	bagConstraints.weightx = 1;
	    	    	bagConstraints.weighty = needRows;
	    	    	bagConstraints.gridx = rows+1;
	    	    	bagConstraints.gridy = column+1;
	    	    	bagConstraints.gridwidth = 1;
	    	    	bagConstraints.gridheight = needRows;
	    	    	deviceGroupTestingPanel.packPanel(shouldColumns);
	    	    	mainPanel.add(deviceGroupTestingPanel, bagConstraints);
	    		}
	    		else
	    		{
	    			bagConstraints.gridx = rows+1;
	    	    	bagConstraints.gridy = column;
	    	    	bagConstraints.weightx = 1;
	    	    	bagConstraints.weighty = needRows;
	    	    	bagConstraints.gridwidth = 1;
	    	    	bagConstraints.gridheight = needRows+1;
	    	    	JLabel blankLabel = new JLabel();
	    	    	mainPanel.add(blankLabel, bagConstraints);
	    		}
	    	}
	    	column+=2;
	    }
		
		this.height = getHeight(totalRows);
		this.width = this.width * shouldColumns;
		this.setSize(width, height);
		SwingUtil.centerWindow(width, height, this);
		return shouldColumns >= 2;
    }
	
	public DeviceGroupTestingPanel getPanelBySingle(List<DeviceGroupTestingPanel> groupTestingPanelTemp, boolean needSingle)
	{
		if(needSingle)
		{
			for (DeviceGroupTestingPanel deviceGroupTestingPanel : groupTestingPanelTemp)
            {
	            if(deviceGroupTestingPanel.getDeviceCount() == 1)
	            {
	            	groupTestingPanelTemp.remove(deviceGroupTestingPanel);
	            	return deviceGroupTestingPanel;
	            }
            }
			return null;
		}
		
		if(groupTestingPanelTemp.size() > 0)
		{
			return groupTestingPanelTemp.remove(0);
		}else
		{
			return null;
		}
	}
	
	@Override
	public void onCloseButton()
	{
		if(!isAllFinished())
		{
			int iResult = AlertDialog.show("Exit Warning", "Testcase is running, are you sure exit?", AlertDialogOptions.OPTION_OK | AlertDialogOptions.OPTION_CANCEL, AlertDialog.ALERT_TYPE_MESSAGE);
			if(iResult == AlertDialogOptions.OPTION_CANCEL)
			{
				return;
			}
		}
		ITTMgr.getInstance().restartITT();
	}
	
	
	private boolean isAllFinished()
	{
		boolean hasTestCaseRunning = false;
		for (ITaskControler taskControler : taskControlerList)
        {
			hasTestCaseRunning |= taskControler.isRunning();
        }
		return !hasTestCaseRunning;
	}

	private int getHeight(int rowsCount)
    {
		if(rowsCount > 1)
		{
			return rowsCount * height - BANNER_HEIGHT;
		}
	    return rowsCount * height;
    }

	/**
	 * Monitor Device HeartBeat.
	 * @param selectDevices
	 */
	private void registeDeviceStatusListener(List<DeviceForSelection> selectDevices)
    {
		DeviceStatusMonitorCenter deviceStatusMonitor = new DeviceStatusMonitorCenter();
		for (DeviceForSelection device : selectDevices)
        {
	        deviceStatusMonitor.registrListener(device);
        }
    }

	private Map<String, List<DeviceForSelection>> groupDevices(List<DeviceForSelection> selectDevices)
    {
		Map<String, List<DeviceForSelection>> devicesGroup = new HashMap<String, List<DeviceForSelection>>();
		for (DeviceForSelection deviceInfo : selectDevices)
        {
			String group = deviceInfo.getDeviceName() + "@" + deviceInfo.getAndroidVersion();
			List<DeviceForSelection> groupDevices = devicesGroup.get(group);
			if(null == groupDevices)
			{
				groupDevices = new ArrayList<DeviceForSelection>();
			}
			groupDevices.add(deviceInfo);
			devicesGroup.put(group, groupDevices);
        }
		return devicesGroup;
    }
}