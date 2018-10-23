package com.itt.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import trialversion.Trialversion;

import com.cats.ui.alertdialog.AlertDialog;
import com.cats.ui.alertdialog.AlertDialogOptions;
import com.cats.ui.custome.JBgColorButton;
import com.cats.ui.custome.barbtn.BarIconType;
import com.cats.ui.custome.table.ITableStatusListener;
import com.cats.ui.graceframe.ICON_STYLE;
import com.cats.ui.graceframe.IGraceFullyFrameBase;
import com.cats.utils.IColorFontUtil;
import com.cats.utils.SwingUtil;
import com.cats.utils.UserDirRestore;
import com.cats.version.client.ClientVersionMonitor;
import com.intel.cats.test.log.LogManager;
import com.itt.ITTConstant;
import com.itt.apks.ui.ApkSelectionPanel;
import com.itt.devices.ui.DeviceSelectionPanel;
import com.itt.preferences.UserParam;
import com.itt.setting.ui.SettingsPanel;

/**
 * @author xblia
 * 2015年10月19日
 */
public class ITTMainFrameUI extends IGraceFullyFrameBase implements ActionListener, ITableStatusListener
{
    private static final long serialVersionUID = 7332387639136249181L;
	private int width = 800;
	private int height = 545;
	
	private JPanel mainPanel = new JPanel();
	private JPanel centerPanel = new JPanel();
	private ApkSelectionPanel apkSelectionPanel;
	private DeviceSelectionPanel deviceSelectionPanel;
	private SettingsPanel settingsPanel;
	private JButton backBtn;
	private JButton nextBtn;
	private JLabel statusBarLabel;
	private int totalSheet = 3;
	private int currentSheet = 1;
	
	private List<IUserInputValidChecker> mustValidCheckUIs = new ArrayList<IUserInputValidChecker>();
	
	public ITTMainFrameUI()
    {
		super(ITTConstant.TITLE, ICON_STYLE.ITT_BANNER_NORMAL, BarIconType.BANNER.getType()
				| BarIconType.MENU.getType()
		        | BarIconType.MIN.getType()
		        | BarIconType.CLOSE.getType());
		this.setIconImage(SwingUtil.genImageResource(ITTMainFrameUI.class, "/icon/icon-interactive_blue.png"));
		this.setSize(width, height);
		SwingUtil.centerWindow(width, height, this);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.initView();
		
		new ClientVersionMonitor(new ITTVersionInfoProvider()).startComponent();
		new Trialversion().start();
		AlertDialog.injectIconResult("/icon/icon-interactive.png");
    }
	
	private void initView()
    {
		apkSelectionPanel = new ApkSelectionPanel();
		deviceSelectionPanel = new DeviceSelectionPanel();
		settingsPanel = new SettingsPanel(this);
		mustValidCheckUIs.add(apkSelectionPanel);
		mustValidCheckUIs.add(deviceSelectionPanel);
		mustValidCheckUIs.add(settingsPanel);
		
		backBtn = new JBgColorButton("<< Back");
		nextBtn = new JBgColorButton("Next >>");
		statusBarLabel = new JLabel(" ");
		statusBarLabel.setBorder(new EmptyBorder(5, 30, 5, 0));
		statusBarLabel.setOpaque(true);
		statusBarLabel.setBackground(IColorFontUtil.COLOR_DEVICE_GRAY);
		
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		
		centerPanel.setLayout(new CardLayout());
		centerPanel.add(apkSelectionPanel, "1");
		centerPanel.add(deviceSelectionPanel, "2");
		centerPanel.add(settingsPanel, "3");
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		
		JPanel bottomPanel = new JPanel(new BorderLayout());
		JPanel bottomBtnPanel = new JPanel();
		bottomBtnPanel.setBorder(new EmptyBorder(0, 0, 0, 15));
		bottomBtnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 3, 5));
		bottomBtnPanel.add(backBtn);
		bottomBtnPanel.add(nextBtn);
		bottomPanel.add(bottomBtnPanel, BorderLayout.EAST);
		
		bottomPanel.add(statusBarLabel, BorderLayout.SOUTH);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);
		
		this.add(mainPanel, BorderLayout.CENTER);
		
		this.backBtn.addActionListener(this);
		this.nextBtn.addActionListener(this);
		this.backBtn.setEnabled(false);
		
		this.apkSelectionPanel.addTableStatusListener(this);
		this.deviceSelectionPanel.addTableStatusListener(this);
    }
	
	@Override
    public void actionPerformed(ActionEvent e)
    {
		CardLayout cardLayout = (CardLayout)centerPanel.getLayout();
	    if(e.getSource() == backBtn)
	    {
	    	cardLayout.previous(centerPanel);
	    	currentSheet = currentSheet - 1;
	    	clearStatusInfo();
	    }else if(e.getSource() == nextBtn)
	    {
	    	if( !isValidInput(mustValidCheckUIs.get(currentSheet-1)))
	    	{
	    		return;
	    	}
	    	if(currentSheet == totalSheet)
	    	{
	    		beginTest();
	    		return;
	    	}
	    	cardLayout.next(centerPanel);
	    	currentSheet = currentSheet + 1;
	    	clearStatusInfo();
	    }
	    
    	backBtn.setEnabled(!(currentSheet == 1));
    	if(currentSheet == totalSheet)
    	{
    		nextBtn.setText("Start Testing >>");
    	}else if(currentSheet < totalSheet)
    	{
    		nextBtn.setText("Next >>");
    	}
    }
	
	private void clearStatusInfo()
    {
		onInfo(-1, -1, " ");
    }

	private void beginTest()
    {
		if(isValidInput(null))
		{
			initCore();
			ITTTestingFrame ittTestingFrame = new ITTTestingFrame();
			ittTestingFrame.initTestComponent(deviceSelectionPanel.getSelection(), apkSelectionPanel.getSelections());
			this.setVisible(false);
			this.deviceSelectionPanel.stopDeviceMonitor();
			this.dispose();
		}
    }

	private void initCore()
    {
		String strBasePath = UserParam.getBasepath() + File.separator + "ITT_LOG";
		File file = new File(strBasePath);
		if(!file.exists())
		{
			file.mkdirs();
		}
		LogManager logManager = LogManager.getInstance();
		logManager.start(strBasePath, null);
    }
	
	private boolean isValidInput(IUserInputValidChecker validChecker)
	{
		String validError = null;
		if(null == validChecker)
		{
			for (IUserInputValidChecker uiValidChecker : mustValidCheckUIs)
			{
				validError = uiValidChecker.isValidInput();
				if(validError != null)
				{
					break;
				}
			}
		}
		else
		{
			validError = validChecker.isValidInput();
		}
		
		if(validError != null)
		{
			AlertDialog.show("Interactive Test Tool", validError, AlertDialogOptions.OPTION_OK, AlertDialog.ALERT_TYPE_MESSAGE);
		}
		return validError == null;
	}

	public static void main(String[] args) throws Exception
	{
		UserDirRestore.restoreUserDir();
		/*try
		{
			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e)
		{
			e.printStackTrace();
		}*/
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				SwingUtil.initGracefulStyle();
				ITTMainFrameUI frameUI = new ITTMainFrameUI();
				frameUI.setVisible(true);
			}
		});
	}

	@Override
    public void onInfo(int totalItems, int selectedItems, String info)
    {
		if(null == info)
		{
			statusBarLabel.setText("Total:" + totalItems + "  Selected: " + selectedItems);
		}
		else
		{
			statusBarLabel.setText(info);
		}
    }
	
	@Override
	public void onMenuButton(Component component, Point point)
	{
		new ITTMainMenu().showMenu(component, this, point);
	}
}