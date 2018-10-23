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
 package com.cats.ui.custome;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;

/**
 * @author xiaobolx
 * 2015年11月11日
 */
public class ProgressBarEx extends JProgressBar
{
	int[][] barStyles = {
			{0xD7EDF6, 0x8EC31E}, //小清新
			{0xDCDDDD, 0x86CFE6},//GraceFul
	};

    private static final long serialVersionUID = 1L;
    private JLabel labelProgress;
    private JLabel labelInfo;
    private JLabel labelName;
    private int []barStyle = barStyles[1];
    public ProgressBarEx(String name, int min, int max)
    {
    	super(JProgressBar.HORIZONTAL, min, max);
    	this.initView(name);
    }
    
    public ProgressBarEx(String name)
    {
    	super();
    	this.initView(name);
    }

    private void initView(String name)
    {
    	this.setBorder(null);
    	this.setBorderPainted(false);
    	this.setStringPainted(false);
    	this.setBackground(new Color(barStyle[0]));
    	this.setForeground(new Color(barStyle[1]));
    	
    	labelProgress = new JLabel("", JLabel.CENTER);
    	labelProgress.setBorder(new EmptyBorder(0, 5, 0, 0));
    	labelInfo = new JLabel("Welcome Use Android Interactive Test Tool!", JLabel.CENTER);
    	labelName = new JLabel("", JLabel.CENTER);
    	
    	this.setLayout(new BorderLayout());
    	this.add(labelProgress, BorderLayout.WEST);
    	this.add(labelInfo, BorderLayout.CENTER);
    	this.add(labelName, BorderLayout.EAST);
    }

    @Deprecated
	private void test()
    {
		final Timer timer = new Timer();
		timer.schedule(new TimerTask()
		{
			int index = 1;
			@Override
			public void run()
			{
				if(index > 100)
				{
					timer.cancel();
					System.out.println("cancel....");
					return;
				}
				ProgressBarEx.this.setValue(index);
				ProgressBarEx.this.setString(index + "/100", "hello..." + index);
				index++;
			}
		}, 1000, 100);
    }
	
	public void setString(String progress, String info)
	{
		if(progress != null)
		{
			this.labelProgress.setText(progress);
		}
		if(info != null)
		{
			this.labelInfo.setText(info);
		}
	}
	
	
	public static void main(String[] args)
    {
	    JFrame frame = new JFrame();
	    frame.setSize(500, 300);
	    frame.setLocation(300,  300);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setLayout(new FlowLayout());
	    
	    ProgressBarEx bar = new ProgressBarEx("Nexus@5.1", 1, 100);
	    bar.setPreferredSize(new Dimension(300, 35));
	    frame.add(bar);
	    frame.setVisible(true);
	    bar.test();
    }
}