/*
 * Copyright 2015 lixiaobo
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
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.cats.ui.custome.barbtn.BarIconType;
import com.cats.utils.SwingUtil;

/**
 * @author xiaobolx
 * 2016年1月7日
 */
public class AlertInputDialog extends AlertDialog
{
	private static final long serialVersionUID = 1L;
	private int width;
	private int height;
	private JTextField textField;
	private String resultVal = "";
	private String defaultVal = "";
    
	protected AlertInputDialog(String title, String message, String defaultVal)
    {
		 super(title, message, AlertDialogOptions.OPTION_OK | AlertDialogOptions.OPTION_CANCEL,
				 -1, BarIconType.TITLE.getType() | BarIconType.CLOSE.getType());
		 this.defaultVal = defaultVal;
		 if(null != textField && null != this.defaultVal)
		 {
			 textField.setText(defaultVal);
		 }
    }
	
	public AlertInputDialog(String title, String message, int iOpts,
            int iDlgType, int barStyle)
    {
		super(title, message, AlertDialogOptions.OPTION_OK, iDlgType, barStyle);
    }
	
	@Override
    protected void initSize()
    {
		width = 400;
	    height = 140;
		this.setSize(width, height);
		SwingUtil.centerWindow(width, height, this);
    }


	@Override
    protected void layoutAlert()
    {
		JPanel mainPanel = new JPanel();
		JPanel mainCenterPanel = new JPanel();
    	mainPanel.setLayout(new BorderLayout());
    	mainCenterPanel.setLayout(new BorderLayout());
    	mainPanel.setBorder(new EmptyBorder(0, 5, 0, 0));

    	JLabel iconLabel = new JLabel(new ImageIcon(AlertInputDialog.class.getResource("icon_input.png")));
    	JLabel messageLabel = new JLabel("<html><font size='14px'>" + this.message + "</font></html>");
    	messageLabel.setBorder(new EmptyBorder(5, 5, 5, 5));
    	textField = new JTextField();
    	textField.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(0, 5, 5, 5), new LineBorder(Color.gray)));
    	
    	mainCenterPanel.add(messageLabel, BorderLayout.NORTH);
    	mainCenterPanel.add(textField, BorderLayout.CENTER);
    	
    	mainPanel.add(iconLabel, BorderLayout.WEST);
    	mainPanel.add(mainCenterPanel, BorderLayout.CENTER);
    	mainPanel.add(genLayoutBottom(), BorderLayout.SOUTH);
    	
    	this.add(mainPanel, BorderLayout.CENTER);
    }
	
	@Override
    public void setVal(int iVal)
    {
		this.resultVal = textField.getText();
		iResult = iVal;
		this.setVisible(false);
		this.dispose();
    }
	
	public String getResult()
	{
		if(iResult == AlertDialogOptions.OPTION_OK)
		{
			return this.resultVal;
		}
		return null;
	}

	public static String showInfoInput(String title, String message, String defaultVal)
    {
		AlertInputDialog dlg = new AlertInputDialog(title, message, defaultVal);
    	dlg.setVisible(true);
    	return dlg.getResult();
    }
}