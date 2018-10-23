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
 package com.cats.ui.custome.progressbar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

/**
 * @author xiaobolx
 * 2015年11月3日
 */
public class PureProgressBar extends JProgressBar
{

    private static final long serialVersionUID = 1L;
    private ImageIcon img = new ImageIcon(PureProgressBar.class.getResource("pure-progress-bar.png"));
    private ImageIcon img_background = new ImageIcon(PureProgressBar.class.getResource("pure-progress-bar_background.png"));
    private int perImgWidth;
    private int perImgHeight;
    private JLabel labelProgress;
    private JLabel labelInfo;
    private JLabel labelName;
    public PureProgressBar(String name, int min, int max)
    {
    	super(JProgressBar.HORIZONTAL, min, max);
    	this.initView(name);
    }
    
    public PureProgressBar(String name)
    {
    	super();
    	this.initView(name);
    }

    private void initView(String name)
    {
    	this.setBorder(null);
    	this.setBorderPainted(false);
    	perImgWidth = img.getIconWidth();
    	perImgHeight = img.getIconHeight();
    	
    	labelProgress = new JLabel("", JLabel.CENTER);
    	labelInfo = new JLabel("Loading....", JLabel.CENTER);
    	labelName = new JLabel("", JLabel.CENTER);
    	labelProgress.setForeground(Color.gray);
    	labelProgress.setFont(new Font(Font.DIALOG, Font.BOLD, 13));
    	labelInfo.setForeground(Color.gray);
    	labelInfo.setFont(new Font(Font.DIALOG, Font.BOLD, 13));
    	
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
				PureProgressBar.this.setValue(index);
				PureProgressBar.this.setString(index + "/100", "hello..." + index);
				index++;
			}
		}, 1000, 100);
    }
	
	@Override
	protected void paintComponent(Graphics g)
	{
		int width = this.getWidth();
		int value = this.getValue();
		int max = this.getMaximum();
		int remainImgWidth = (int)(width * (value*1.0/max));
		drawProgress(g, width, img_background);
		drawProgress(g, remainImgWidth, img);
	}
	
	private void drawProgress(Graphics g, int needDrawWidth, ImageIcon imgIcon)
    {
		int startPosition = 0;
		int perDrawWidth = 0;
		int drawTotalWidth = 0;
		int remainImgWidth = needDrawWidth;
		int height = Math.min(this.getHeight(), perImgHeight);
		while(remainImgWidth > 0)
		{
			if(remainImgWidth/perImgWidth > 0)
			{
				perDrawWidth = perImgWidth;
			}else
			{
				perDrawWidth = remainImgWidth; 
			}
			remainImgWidth = remainImgWidth - perDrawWidth;
			g.drawImage(imgIcon.getImage(), startPosition, 0, startPosition + perDrawWidth, height, 0, 0, perDrawWidth, height, Color.white, null);
			drawTotalWidth += perDrawWidth;
			startPosition = ((drawTotalWidth)/perImgWidth)*perImgWidth;
		}
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
	    
	    PureProgressBar bar = new PureProgressBar("Nexus@5.1", 1, 100);
	    bar.setStringPainted(true);
	    bar.setPreferredSize(new Dimension(300, 35));
	    frame.add(bar);
	    frame.setVisible(true);
	    bar.test();
    }
}