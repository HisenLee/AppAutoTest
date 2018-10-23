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
 package com.cats.ui.optiondlg;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.cats.ui.custome.JBgColorButton;
import com.cats.utils.IColorFontUtil;

/**
 * @author xiaobolx
 * 2015年11月2日
 */
public class InputDialog extends JDialog implements ActionListener, WindowFocusListener
{
	private static final long serialVersionUID = 1L;

	private JDialogParent parent;
	private String defaultVal;
	private int width;
	private int height;
	private JTextArea area;
	private JButton okBtn;

	public InputDialog(JDialogParent parent, Point point, String defaultVal)
	{
		this.parent = parent;
		this.defaultVal = defaultVal;
		this.width = 150;
		this.height = 120;
		
		//this.setModal(true);
		this.setUndecorated(true);
		this.setAlwaysOnTop(true);
		this.setSize(width, height);
		this.setLocation(point);
		
		initView();
	}
	
	private void initView()
	{
		area = new JTextArea(defaultVal);
		okBtn = new JBgColorButton("OK");
		okBtn.addActionListener(this);
		
		this.setLayout(new BorderLayout(0, 0));
		this.getRootPane().setBorder(new LineBorder(IColorFontUtil.COLOR_SELECTED_COLOR_BABY_GREEN));
		
		JPanel mainPanel = new JPanel(new BorderLayout(0, 0));
		Border inputBorder = BorderFactory.createTitledBorder(new EmptyBorder(0, 0, 0, 0), "ResultDetail");
		mainPanel.setBorder(inputBorder);
		mainPanel.add(new JScrollPane(area), BorderLayout.CENTER);
		
		JPanel bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.setBorder(new EmptyBorder(2, 2, 2, 2));
		bottomPanel.add(okBtn, BorderLayout.CENTER);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);
		this.add(mainPanel, BorderLayout.CENTER);
		
		this.addWindowFocusListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == okBtn)
		{
			dispose();
		}
	}
	
	@Override
    public void windowGainedFocus(WindowEvent e)
    {
    }

	@Override
    public void windowLostFocus(WindowEvent e)
    {
		this.parent.dispose(null, area.getText(), null, JDialogParent.CLOSE_TYPE.BY_LOSTFOCUS);
		dispose();
    }
}