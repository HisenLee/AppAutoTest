/*
 * Copyright 2016 lixiaobo
 *
 * VersionUpgrade project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
 package com.cats.ui.alertdialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.cats.ui.custome.barbtn.BarIconType;
import com.cats.utils.SwingUtil;

/**
 * @author xiaobolx
 * 2016年5月12日
 */
public class AlertInputDlg extends AlertDialog
{
	private int width;
	private int height;
	private String desc;
	private String defaultVal;
	private JLabel descLabel;
	private JTextField inputField;
	/**
	 */
    private static final long serialVersionUID = 1L;
    
	protected AlertInputDlg(String title, String desc, String defaultVal)
    {
		 super(title, "", AlertDialogOptions.OPTION_OK | AlertDialogOptions.OPTION_CANCEL, AlertDialog.ALERT_TYPE_INPUT, BarIconType.TITLE.getType()|BarIconType.CLOSE.getType());
		 this.desc = desc;
		 this.descLabel.setText(this.desc);
		 this.defaultVal = defaultVal;
    }
	
	@Override
    protected void initSize()
    {
		width = 400;
	    height = 150;
		this.setSize(width, height);
		SwingUtil.centerWindow(width, height, this);
    }

	@Override
    protected void layoutAlert()
    {
		this.descLabel = new JLabel();
		this.inputField = new JTextField(11);
		this.inputField.setPreferredSize(new Dimension(-1, 30));
		this.inputField.setText(defaultVal == null ? "":defaultVal);
		JPanel mainPanel = new JPanel();
		JPanel mainCenterPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		GridBagConstraints bagConstraints = new GridBagConstraints();
		GridBagLayout bagLayout = new GridBagLayout();
		mainCenterPanel.setLayout(bagLayout);
    	bagConstraints.gridwidth = 0;
    	bagConstraints.fill = GridBagConstraints.BOTH;
    	bagConstraints.weightx = 1;
    	bagConstraints.insets = new Insets(5, 15, 5, 15);
    	mainCenterPanel.add(new JLabel(), bagConstraints);
    	mainCenterPanel.add(descLabel, bagConstraints);
    	mainCenterPanel.add(inputField, bagConstraints);
    	
    	mainPanel.add(mainCenterPanel, BorderLayout.CENTER);
    	mainPanel.add(genLayoutBottom(), BorderLayout.SOUTH);
    	this.add(mainPanel, BorderLayout.CENTER);
    }
	
	private String getInputText()
    {
		String inputVal = inputField.getText();
		if(null == inputVal || inputVal.isEmpty())
		{
			return null;
		}
		return inputVal;
    }

	public static String showInfo(String title,String desc, String defaultVal)
    {
		AlertInputDlg dlg = new AlertInputDlg(title, desc, defaultVal);
    	dlg.setVisible(true);
    	if(iResult == AlertDialogOptions.OPTION_OK)
    	{
    		return dlg.getInputText();
    	}
    	return null;
    }
	
	public static void main(String[] args)
    {
	    String val = showInfo("Input", "Name", "Column");
	    System.out.println(val);
    }
}