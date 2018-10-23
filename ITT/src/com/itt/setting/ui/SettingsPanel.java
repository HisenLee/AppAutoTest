package com.itt.setting.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.cats.ui.custome.JBgColorButton;
import com.cats.ui.custome.JBlokLabel;
import com.cats.ui.tooltip.TooltipLabelForHelp;
import com.cats.utils.IColorFontUtil;
import com.itt.ContentGetterFromFile;
import com.itt.preferences.IPropertyKeys;
import com.itt.preferences.ITTProperties;
import com.itt.ui.IUserInputValidChecker;
import com.itt.ui.custome.preference.IPreferenceComponent;
import com.itt.ui.custome.preference.JPreferenceCheckBox;
import com.itt.ui.custome.preference.JPreferenceRadioButton;
import com.itt.ui.custome.preference.JPreferenceTextField;
import com.itt.ui.custome.preference.PreferenceComponentChangeListener;

/**
 * @author xblia 2015年10月21日
 */
public class SettingsPanel extends JPanel implements ActionListener, PreferenceComponentChangeListener, IUserInputValidChecker
{
	private static final long serialVersionUID = -8990028443183740208L;
	private JPreferenceTextField installTimeoutValField;
	private JPreferenceTextField uninstallTimeoutValField;
	private JPreferenceTextField launchTimeoutValField;
	private JPreferenceTextField touchTimeoutValField;
	private JPreferenceTextField quitTimeoutValField;
	
	private JPreferenceRadioButton fixedModeRadioBtn;
	private JPreferenceRadioButton lazyModeRadioBtn;
	private JPreferenceCheckBox autoTestCheckBox;
	private JPreferenceCheckBox screenRecordToMp4CheckBox;
	private JPreferenceTextField oneApkTestTimesField;
	private List<IPreferenceComponent> preferenceComponentList = new ArrayList<IPreferenceComponent>();

	private JFrame parent;
	private JButton resetBtn;
	private JButton applyBtn;

	public SettingsPanel(JFrame parent)
	{
		this.parent = parent;
		initView();
		initComponentListener();
	}

	private void initView()
	{
		this.installTimeoutValField = new JPreferenceTextField(20, IPropertyKeys.TIMEOUT_INSTALL_SECOND, "150");
		this.uninstallTimeoutValField = new JPreferenceTextField(20, IPropertyKeys.TIMEOUT_UNINSTALL_SECOND, "120");
		this.launchTimeoutValField = new JPreferenceTextField(20, IPropertyKeys.TIMEOUT_LAUNCH_SECOND, "50");
		this.touchTimeoutValField = new JPreferenceTextField(20, IPropertyKeys.TIMEOUT_TOUCH_SECOND, "180");
		this.quitTimeoutValField = new JPreferenceTextField(20, IPropertyKeys.TIMEOUT_QUIT_SECOND, "5");
		
		this.fixedModeRadioBtn = new JPreferenceRadioButton(IPropertyKeys.TESTMODE_FIXEDMODE, "1", "FixedMode");
		this.lazyModeRadioBtn = new JPreferenceRadioButton(IPropertyKeys.TESTMODE_LAZYMODE, "0", "LazyMode");
		this.autoTestCheckBox = new JPreferenceCheckBox("AutoTest", IPropertyKeys.AUTOTEST, "0");
		this.autoTestCheckBox.setBackground(IColorFontUtil.COLOR_WHITE_GRAY);
		this.oneApkTestTimesField = new JPreferenceTextField(10, IPropertyKeys.TEST_TIMES_EVERY_APK, "1");
		
		this.screenRecordToMp4CheckBox = new JPreferenceCheckBox("ScreenRecord", IPropertyKeys.SCREEN_RECORD_TO_MP4, "0");
		this.screenRecordToMp4CheckBox.setBackground(IColorFontUtil.COLOR_WHITE_GRAY);
		
		
		preferenceComponentList.add(installTimeoutValField);
		preferenceComponentList.add(uninstallTimeoutValField);
		preferenceComponentList.add(launchTimeoutValField);
		preferenceComponentList.add(touchTimeoutValField);
		preferenceComponentList.add(quitTimeoutValField);
		preferenceComponentList.add(fixedModeRadioBtn);
		preferenceComponentList.add(lazyModeRadioBtn);
		preferenceComponentList.add(autoTestCheckBox);
		preferenceComponentList.add(screenRecordToMp4CheckBox);
		preferenceComponentList.add(oneApkTestTimesField);

		this.resetBtn = new JBgColorButton("Reset Default");
		this.applyBtn = new JBgColorButton("Apply");

		this.setLayout(new BorderLayout(0, 0));
		//this.setBorder(new EmptyBorder(10, 10, 10, 10));

		GridBagLayout bagLayout = new GridBagLayout();
		GridBagConstraints bagConstraints = new GridBagConstraints();

		int rowIndex = 0;
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(bagLayout);
		bagConstraints.insets = new Insets(2, 0, 2, 0);
		bagConstraints.fill = GridBagConstraints.BOTH;
		bagConstraints.gridx = 0;
		bagConstraints.gridy = rowIndex++;
		bagConstraints.gridwidth = 5;
		centerPanel.add(genTitleLabel("Timeout Configurations(S)"), bagConstraints);

		bagConstraints.gridwidth = 1;
		bagConstraints.gridx = 0;
		bagConstraints.gridy = rowIndex;
		centerPanel.add(new JLabel("Install"), bagConstraints);
		bagConstraints.gridwidth = 4;
		bagConstraints.gridx = 1;
		bagConstraints.gridy = rowIndex++;
		centerPanel.add(installTimeoutValField, bagConstraints);
		bagConstraints.gridwidth = 1;
		bagConstraints.gridx = 0;
		bagConstraints.gridy = rowIndex;
		centerPanel.add(new JLabel("Uninstall "), bagConstraints);
		bagConstraints.gridwidth = 4;
		bagConstraints.gridx = 1;
		bagConstraints.gridy = rowIndex++;
		centerPanel.add(uninstallTimeoutValField, bagConstraints);
		bagConstraints.gridwidth = 1;
		bagConstraints.gridx = 0;
		bagConstraints.gridy = rowIndex;
		centerPanel.add(new JLabel("Launch "), bagConstraints);
		bagConstraints.gridwidth = 4;
		bagConstraints.gridx = 1;
		bagConstraints.gridy = rowIndex++;
		centerPanel.add(launchTimeoutValField, bagConstraints);
		
		bagConstraints.gridwidth = 1;
		bagConstraints.gridx = 0;
		bagConstraints.gridy = rowIndex;
		centerPanel.add(new JLabel("Touch "), bagConstraints);
		bagConstraints.gridwidth = 4;
		bagConstraints.gridx = 1;
		bagConstraints.gridy = rowIndex;
		centerPanel.add(touchTimeoutValField, bagConstraints);
		bagConstraints.gridwidth = 1;
		bagConstraints.gridx = 5;
		bagConstraints.gridy = rowIndex++;
		centerPanel.add(screenRecordToMp4CheckBox, bagConstraints);
		
		bagConstraints.gridwidth = 1;
		bagConstraints.gridx = 0;
		bagConstraints.gridy = rowIndex;
		centerPanel.add(new JLabel("Quit "), bagConstraints);
		bagConstraints.gridwidth = 4;
		bagConstraints.gridx = 1;
		bagConstraints.gridy = rowIndex++;
		centerPanel.add(quitTimeoutValField, bagConstraints);
		

		//Normal Configurations
		bagConstraints.gridx = 0;
		bagConstraints.gridy = rowIndex++;
		bagConstraints.gridwidth = 5;
		centerPanel.add(genTitleLabel("Normal Configurations"), bagConstraints);
		{//TestModePanel
		bagConstraints.gridx = 0;
		bagConstraints.gridy = rowIndex++;
		bagConstraints.gridwidth = 5;
		centerPanel.add(genTestModePanel(), bagConstraints);
		}
		bagConstraints.gridx = 0;
		bagConstraints.gridy = rowIndex;
		bagConstraints.gridwidth = 1;
		centerPanel.add(new JLabel("Test Times"), bagConstraints);
		bagConstraints.gridx = 1;
		bagConstraints.gridy = rowIndex;
		centerPanel.add(oneApkTestTimesField, bagConstraints);
		bagConstraints.gridx = 2;
		bagConstraints.gridy = rowIndex;
		centerPanel.add(autoTestCheckBox, bagConstraints);
		bagConstraints.gridx = 3;
		bagConstraints.gridwidth=1;
		bagConstraints.gridy = rowIndex;
		TooltipLabelForHelp helpLabel = new TooltipLabelForHelp(ContentGetterFromFile.getHelpInfo(ContentGetterFromFile.ContentType.enAutoTestHelp), null,  parent);
		centerPanel.add(helpLabel, bagConstraints);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		bottomPanel.add(resetBtn);
		bottomPanel.add(applyBtn);
		
		JLabel titleLabel = new JBlokLabel("Settings");
		titleLabel.setIcon(new ImageIcon(SettingsPanel.class.getResource("/icon/icon_1dot.png")));
		titleLabel.setBorder(new EmptyBorder(10, 10, 0, 0));
		JPanel mainPanel = new JPanel(new BorderLayout(0, 0));
		mainPanel.setBorder(new EmptyBorder(5, 10, 10, 10));
		
		mainPanel.add(titleLabel, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);
		
		centerPanel.setBackground(IColorFontUtil.COLOR_WHITE_GRAY);
		bottomPanel.setBackground(IColorFontUtil.COLOR_WHITE_GRAY);
		
		this.add(titleLabel, BorderLayout.NORTH);
		this.add(mainPanel, BorderLayout.CENTER);
	}
	
	private JLabel genTitleLabel(String info)
	{
		JLabel label = new JLabel(info);
		label.setBorder(new EmptyBorder(3, 0, 3, 0));
		label.setFont(new Font("Segoe UI", Font.BOLD, 13));
		return label;
	}
	
	private Component genTestModePanel()
    {
		JPanel panel = new JPanel();
		panel.setBackground(IColorFontUtil.COLOR_WHITE_GRAY);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		panel.setBorder(new EmptyBorder(0, 0, 3, 0));
		panel.add(new JLabel("Test Mode "));
		
		ButtonGroup testModeBtnGroup = new ButtonGroup();
		testModeBtnGroup.add(fixedModeRadioBtn);
		testModeBtnGroup.add(lazyModeRadioBtn);
		panel.add(fixedModeRadioBtn);
		panel.add(lazyModeRadioBtn);
		
		TooltipLabelForHelp helpLabel = new TooltipLabelForHelp(ContentGetterFromFile.getHelpInfo(ContentGetterFromFile.ContentType.enTestModeHelp), null,  parent);
		panel.add(helpLabel);
		
	    return panel;
    }

	private void initComponentListener()
	{
		this.resetBtn.addActionListener(this);
		this.applyBtn.addActionListener(this);
		this.resetBtn.setEnabled(true);
		this.applyBtn.setEnabled(false);
		for (IPreferenceComponent component : preferenceComponentList)
        {
	        component.setChangeListener(this);
        }
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == resetBtn)
		{
			for (IPreferenceComponent preferenceComponent : preferenceComponentList)
			{
				preferenceComponent.resetDefaultVal();
			}
			resetBtn.setEnabled(false);
		} else if (e.getSource() == applyBtn)
		{
			for (IPreferenceComponent preferenceComponent : preferenceComponentList)
			{
				preferenceComponent.saveProperty();
			}
			applyBtn.setEnabled(false);
		}
		ITTProperties.saveProp();
	}

	@Override
    public void onChange(String preferenceKey, String value)
    {
		applyBtn.setEnabled(true);
		resetBtn.setEnabled(true);
    }

	@Override
    public String isValidInput()
    {
		if(applyBtn.isEnabled())
		{
			return "Configuration has changed,  please click Apply button";
		}
	    return null;
    }

}