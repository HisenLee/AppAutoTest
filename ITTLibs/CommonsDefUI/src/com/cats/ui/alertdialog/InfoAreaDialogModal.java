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

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import com.cats.ui.custome.barbtn.BarIconType;
import com.cats.utils.SwingUtil;
import com.intel.swing.style.GraceFullyScrollBar;

/**
 * @author xiaobolx
 * 2015年11月19日
 */
public class InfoAreaDialogModal extends AlertDialog
{
	private int width;
	private int height;
	/**
	 */
    private static final long serialVersionUID = 1L;
    
	protected InfoAreaDialogModal(String title, String message, int iOpts,
            int iDlgType)
    {
		 super(title, message, iOpts, iDlgType, BarIconType.TITLE.getType());
    }
	
	protected InfoAreaDialogModal(String title, String message, int iOpts,
            int iDlgType, int width, int height)
    {
		 super(title, message, iOpts, iDlgType, BarIconType.TITLE.getType());
		 this.width = width;
		 this.height = height;
		 this.setSize(width, height);
		 SwingUtil.centerWindow(width, height, this);
    }
	
	public InfoAreaDialogModal(String title, String message, int iOpts,
            int iDlgType, int barStyle)
    {
		super(title, message, iOpts, iDlgType, barStyle);
    }
	
	@Override
    protected void initSize()
    {
		width = 650;
	    height = 540;
		this.setSize(width, height);
		SwingUtil.centerWindow(width, height, this);
    }


	@Override
    protected void layoutAlert()
    {
		JPanel mainPanel = new JPanel();
    	mainPanel.setLayout(new BorderLayout());
    	
    	JTextArea textPane = new JTextArea();
    	textPane.setLineWrap(true);
    	textPane.setText(message);
    	textPane.setBorder(new EmptyBorder(15, 15, 15, 15));
    	textPane.setEditable(false);
    	textPane.setSelectionStart(0);
    	textPane.setSelectionEnd(0);
    	JScrollPane msgScroll = new JScrollPane(textPane);
    	msgScroll.setBorder(null);
    	msgScroll.getVerticalScrollBar().setUI(new GraceFullyScrollBar());
    	mainPanel.add(msgScroll, BorderLayout.CENTER);
    	mainPanel.add(genLayoutBottom(), BorderLayout.SOUTH);
    	
    	this.add(mainPanel, BorderLayout.CENTER);
    }
	
	public static void showInfo(String title, String message, int iOptions, int iAlertType, int width, int height)
    {
		InfoAreaDialogModal dlg = new InfoAreaDialogModal(title, message, iOptions, iAlertType, width, height);
    	dlg.setVisible(true);
    }

	public static void showInfo(String title, String message, int iOptions, int iAlertType)
    {
		InfoAreaDialogModal dlg = new InfoAreaDialogModal(title, message, iOptions, iAlertType);
    	dlg.setVisible(true);
    }
}
