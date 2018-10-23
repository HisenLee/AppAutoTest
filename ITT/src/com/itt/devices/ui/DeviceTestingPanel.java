package com.itt.devices.ui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import com.cats.utils.IColorFontUtil;
import com.itt.devices.DeviceForSelection;
import com.itt.devices.listener.IDevicePanelCommonActionListener;
import com.itt.devices.listener.IDeviceTestStatusListener;
import com.itt.devices.statusfilter.IManualStatusTestFilter;
import com.itt.devices.statusfilter.ProgressStatusTestFilter;
import com.itt.devices.statusfilter.StatusFilterChain;
import com.itt.preferences.UserParam;
import com.itt.testcase.teststatus.ITestStatus;
import com.itt.ui.custome.AngleButtonOfDevice;
import com.itt.ui.custome.ButtonProgress;
import com.itt.ui.custome.ProgressForSection;
import com.itt.ui.custome.preference.IControlableComponent;

/**
 * @author xblia
 * 2015年10月20日
 */
public class DeviceTestingPanel extends JPanel implements IDeviceTestStatusListener, ActionListener
{
    private static final long serialVersionUID = 1L;
    
    private IDevicePanelCommonActionListener commonActionListener;
    private DeviceForSelection deviceForSelection;
    private DeviceStatusPanel deviceStatusPanel;
    
    private JLabel labelInfo;
    private JLabel deviceNameLabel;
    private ProgressForSection progressBarInstall;
    private ProgressForSection progressBarLaunch;
    private ProgressForSection progressRandomClick;
    private ProgressForSection progressQuit;
    private ProgressForSection progressUninstall;
    private ButtonProgress btnFinishTest;
    private Map<Integer, IControlableComponent> statusToComponentControlable = new HashMap<Integer, IControlableComponent>();
    private StatusFilterChain statusFilterChain;
    
    private JPanel bottomOptPanel = new JPanel();
    private AngleButtonOfDevice btnSnapShort;
    private AngleButtonOfDevice btnRecordManualResult;
    private AngleButtonOfDevice btnOpenFolder;
    private Timer hiddenOptBtnPanelTimer;
    
    private boolean isAutoTest;
    private boolean atLeastTwoDevice;
    

    public DeviceTestingPanel(DeviceForSelection deviceForSelection, boolean atLeastTwoDevice,  IDevicePanelCommonActionListener commonActionListener)
    {
    	this.deviceForSelection = deviceForSelection;
    	this.atLeastTwoDevice = atLeastTwoDevice;
    	this.commonActionListener = commonActionListener;
    	this.deviceStatusPanel = new DeviceStatusPanel(deviceForSelection);
    	this.deviceForSelection.registerStatusListener(deviceStatusPanel);
    	
    	Border emptyBorder = BorderFactory.createLineBorder(IColorFontUtil.COLOR_DEVICE_GRAY, 5);
    	if(atLeastTwoDevice)
    	{
    		Border insideBorder = BorderFactory.createLineBorder(IColorFontUtil.COLOR_DEVICE_BORDER, 1, true);
    		Border border = BorderFactory.createCompoundBorder(insideBorder, emptyBorder);
    		this.setBorder(border);
    	}else
    	{
    		this.setBorder(emptyBorder);
    	}
    	this.isAutoTest = UserParam.getAutoTest();
    	
    	labelInfo = new JLabel("", JLabel.CENTER);
    	labelInfo.setFont(IColorFontUtil.FONT_MICROSOFT);
    	deviceNameLabel = new JLabel(deviceForSelection.getDeviceId(), JLabel.CENTER);
    	deviceNameLabel.setFont(IColorFontUtil.FONT_DEFAULT);
    	deviceNameLabel.setForeground(IColorFontUtil.COLOR_GRACE_GRAY);
    	
    	progressBarInstall = new ProgressForSection("Install", false);
    	progressBarLaunch = new ProgressForSection("Launch", false, false, isAutoTest);
    	progressRandomClick = new ProgressForSection((isAutoTest ? "Operations":"Manual"), !isAutoTest, !isAutoTest, isAutoTest);
    	progressQuit = new ProgressForSection("Quit", false);
    	progressUninstall = new ProgressForSection("Uninstall", false, false, false);
    	
    	btnFinishTest = new ButtonProgress(progressRandomClick);
    	
    	statusToComponentControlable.put(ITestStatus.STATUS_INSTALL, progressBarInstall);
    	statusToComponentControlable.put(ITestStatus.STATUS_LAUNCH, progressBarLaunch);
    	statusToComponentControlable.put(ITestStatus.STATUS_RANDOMCLICK, progressRandomClick);
    	if(!isAutoTest)statusToComponentControlable.put(ITestStatus.STATUS_WAITTING_USER, btnFinishTest);
    	statusToComponentControlable.put(ITestStatus.STATUS_QUIT, progressQuit);
    	statusToComponentControlable.put(ITestStatus.STATUS_UNINSTALL, progressUninstall);
    	
    	btnSnapShort = new AngleButtonOfDevice("Snapshot", false);
    	btnRecordManualResult = new AngleButtonOfDevice("Record Result", false);
    	btnOpenFolder = new AngleButtonOfDevice("Openfolder", true);
    	
    	beginLayout();
    	initListener();
    	initFilters();
    }
    
	private void initFilters()
    {
		this.statusFilterChain = new StatusFilterChain();
    	this.statusFilterChain.addFilter(new IManualStatusTestFilter(btnSnapShort, btnRecordManualResult));
    	this.statusFilterChain.addFilter(new ProgressStatusTestFilter(statusToComponentControlable));
    }

	private void initListener()
    {
		btnOpenFolder.addActionListener(this);
		if(isAutoTest && !isAutoTest)
		{
			this.addMouseListener(new MouseAdapter()
			{
				@Override
				public void mouseEntered(MouseEvent e)
				{
					cancelPanelHiddenTimer();
					bottomOptPanel.setVisible(true);
				}
				
				@Override
				public void mouseExited(MouseEvent e)
				{
					int x = e.getX();
					int y = e.getY();
					int panelW= (int)Math.ceil(DeviceTestingPanel.this.getBounds().getWidth());
					int panelH = (int)Math.ceil(DeviceTestingPanel.this.getBounds().getHeight());
					
					if(x > 0 && x < panelW && y > 0 && y < panelH)
					{
						return;
					}
					startPanelHiddenTimer();
				}
			});
		}
    }
	
	private void cancelPanelHiddenTimer()
	{
		try
        {
	        if(hiddenOptBtnPanelTimer != null)
	        {
	        	hiddenOptBtnPanelTimer.cancel();
	        }
        } catch (Exception e)
        {
        }
	}
	
	private void startPanelHiddenTimer()
	{
		cancelPanelHiddenTimer();
		hiddenOptBtnPanelTimer = new Timer();
		hiddenOptBtnPanelTimer.schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				bottomOptPanel.setVisible(false);
			}
		}, 2000);
	}

	private void beginLayout()
    {
		this.setLayout(new BorderLayout(0, 0));
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		if (atLeastTwoDevice)
		{
			topPanel.add(deviceNameLabel, BorderLayout.WEST);
		}
		topPanel.add(labelInfo, BorderLayout.CENTER);
		topPanel.add(deviceStatusPanel, BorderLayout.EAST);
		
		JPanel centerPanel = new JPanel();
		GridBagLayout bagLayout = new GridBagLayout();
		centerPanel.setLayout(bagLayout);
		GridBagConstraints bagConstraints = new GridBagConstraints();
		bagConstraints.insets = new Insets(0, 0, 0, 0);
		bagConstraints.ipadx=0;
		bagConstraints.ipady=0;
		int iColumnIndex = 0;
		bagConstraints.gridx = iColumnIndex++;
		bagConstraints.gridy = 0;
		bagConstraints.weightx=1;
		bagConstraints.fill = GridBagConstraints.BOTH;
		centerPanel.add(progressBarInstall, bagConstraints);
		bagConstraints.gridx = iColumnIndex++;
		bagConstraints.gridy = 0;
		bagConstraints.weightx=1;
		centerPanel.add(progressBarLaunch, bagConstraints);
		bagConstraints.gridx = iColumnIndex++;
		bagConstraints.gridy = 0;
		bagConstraints.weightx=1;
		centerPanel.add(progressRandomClick, bagConstraints);
		if(!isAutoTest)
		{
			bagConstraints.gridx = iColumnIndex++;
			bagConstraints.gridy = 0;
			bagConstraints.weightx=0;
			centerPanel.add(btnFinishTest, bagConstraints);
		}
		bagConstraints.gridx = iColumnIndex++;
		bagConstraints.gridy = 0;
		bagConstraints.weightx=1;
		centerPanel.add(progressQuit, bagConstraints);
		bagConstraints.gridx = iColumnIndex++;
		bagConstraints.gridy = 0;
		bagConstraints.weightx=1;
		centerPanel.add(progressUninstall, bagConstraints);
		
		/*iColumnIndex = 0;
		bagConstraints.gridx = iColumnIndex++;
		bagConstraints.gridy = 1;
		bagConstraints.weightx=1;
		bagConstraints.fill = GridBagConstraints.BOTH;
		centerPanel.add(new GraceFulLabel("Install", JLabel.CENTER), bagConstraints);
		bagConstraints.gridx = iColumnIndex++;
		bagConstraints.gridy = 1;
		bagConstraints.weightx=1;
		centerPanel.add(new GraceFulLabel("Launch", JLabel.CENTER), bagConstraints);
		bagConstraints.gridx = iColumnIndex++;
		bagConstraints.gridy = 1;
		bagConstraints.weightx=1;
		centerPanel.add(new GraceFulLabel("Manual", JLabel.CENTER), bagConstraints);
		if(!isAutoTest)
		{
			bagConstraints.gridx = iColumnIndex++;
			bagConstraints.gridy = 1;
			bagConstraints.weightx=0;
			centerPanel.add(new JLabel(), bagConstraints);
		}
		bagConstraints.gridx = iColumnIndex++;
		bagConstraints.gridy = 1;
		bagConstraints.weightx=1;
		centerPanel.add(new GraceFulLabel("Quit", JLabel.CENTER), bagConstraints);
		bagConstraints.gridx = iColumnIndex++;
		bagConstraints.gridy = 1;
		bagConstraints.weightx=1;
		centerPanel.add(new GraceFulLabel("Uninstall", JLabel.CENTER), bagConstraints);*/
		
		
		GridBagLayout bottomGridBagLayout = new GridBagLayout();
		bottomGridBagLayout.columnWidths = new int[]{200, 200, 200};
		bottomGridBagLayout.rowHeights = new int[]{22};
		GridBagConstraints bottomBagConstraints = new GridBagConstraints();
		bottomOptPanel.setLayout(bottomGridBagLayout);
		bottomBagConstraints.gridx = 0;
		bottomBagConstraints.gridy = 0;
		bottomBagConstraints.insets = new Insets(1, 2, 1, 2);
		bottomBagConstraints.fill = GridBagConstraints.BOTH;
		bottomBagConstraints.weightx = 1;
		bottomOptPanel.add(btnSnapShort, bottomBagConstraints);
		bottomBagConstraints.gridx = 1;
		bottomBagConstraints.gridy = 0;
		bottomBagConstraints.weightx = 1;
		bottomOptPanel.add(btnRecordManualResult, bottomBagConstraints);
		bottomBagConstraints.gridx = 2;
		bottomBagConstraints.gridy = 0;
		bottomBagConstraints.weightx = 1;
		bottomOptPanel.add(btnOpenFolder, bottomBagConstraints);
		
		this.add(topPanel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(bottomOptPanel, BorderLayout.SOUTH);
		
		topPanel.setBackground(IColorFontUtil.COLOR_DEVICE_GRAY);
		centerPanel.setBackground(IColorFontUtil.COLOR_DEVICE_GRAY);
		bottomOptPanel.setBackground(IColorFontUtil.COLOR_DEVICE_GRAY);
    }
	
	@Override
    public void onStatus(String deviceId, int iStatusCode, int wparam, Object lparam)
    {
		statusFilterChain.doFilter(iStatusCode, wparam, lparam);
    }
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == btnOpenFolder)
		{
			commonActionListener.onCommonAction(IDevicePanelCommonActionListener.ACTION_CDE_OPENFOLDER, btnOpenFolder);
		}
	}
	
	@Override
    public void onStatus(String deviceId, final String info)
    {
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				labelInfo.setText(info);
			}
		});
    }

	@Override
    public void onProgress(int total, int iTotalGroupTask, int finished)
    {
    }
}