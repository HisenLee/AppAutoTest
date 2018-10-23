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
 package com.cats.ui.custome.progressbarex;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JProgressBar;

/**
 * @author xiaobolx
 * 2015年11月3日
 */
public class PureProgressBarEx extends JProgressBar
{

    private static final long serialVersionUID = 1L;
    private ImageIcon img = new ImageIcon(PureProgressBarEx.class.getResource("progressbar_middle.png"));
    private ImageIcon img_left = new ImageIcon(PureProgressBarEx.class.getResource("progressbar_left.png"));
    private ImageIcon img_right = new ImageIcon(PureProgressBarEx.class.getResource("progressbar_right.png"));
    private ImageIcon img_background = new ImageIcon(PureProgressBarEx.class.getResource("progressbar_bg_middle.png"));
    private ImageIcon img_background_left = new ImageIcon(PureProgressBarEx.class.getResource("progressbar_bg_left.png"));
    private ImageIcon img_background_right = new ImageIcon(PureProgressBarEx.class.getResource("progressbar_bg_right.png"));
    public PureProgressBarEx(int min, int max)
    {
    	super(JProgressBar.HORIZONTAL, min, max);
    	this.initView();
    }
    
    public PureProgressBarEx()
    {
    	super();
    	this.initView();
    }

    private void initView()
    {
    	this.setBorder(null);
    	this.setOpaque(false);
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
				PureProgressBarEx.this.setValue(index);
				index++;
			}
		}, 1000, 100);
    }
	
	@Override
	protected void paintComponent(Graphics g)
	{
		int width = this.getWidth();
		drawProgressBackground(g, width);
		if(this.getValue() > 1)
		{
			drawProgress(g, img);
		}
		
	}
	
	private void drawProgressBackground(Graphics g, int needDrawWidth)
    {
		int height = Math.min(this.getHeight(), img_background.getIconHeight());
		int startPosition = 0;
		int perDrawWidth = 0;
		int drawTotalWidth = 0;
		int middleImgWidth = needDrawWidth - img_background_right.getIconWidth() - img_background_left.getIconWidth();
		
		g.drawImage(img_background_left.getImage(), 0, 0, img_background_left.getIconWidth(), height, 0, 0, img_background_left.getIconWidth(), height,null);
		
		int remainImgWidth = middleImgWidth;
		startPosition = img_background_left.getIconWidth();
		int perMinddleImgWidth = img_background.getIconWidth();
		int perMiddleImgHeight = img_background.getIconHeight();
		while(remainImgWidth > 0)
		{
			if(remainImgWidth/perMinddleImgWidth > 0)
			{
				perDrawWidth = perMinddleImgWidth;
			}else
			{
				perDrawWidth = remainImgWidth; 
			}
			g.drawImage(img_background.getImage(), startPosition, 0, startPosition + perDrawWidth, height, 0, 0, perDrawWidth, perMiddleImgHeight, Color.white, null);
			drawTotalWidth += perDrawWidth;
			startPosition = ((drawTotalWidth)/perMinddleImgWidth)*perMinddleImgWidth + img_background_left.getIconWidth();
			remainImgWidth = remainImgWidth - perDrawWidth;
		}
		int endX = middleImgWidth+img_background_left.getIconWidth();
		g.drawImage(img_background_right.getImage(), endX, 0, endX + img_background_right.getIconWidth(), height, 0, 0, img_background_right.getIconWidth(), height, null);
    }
	

	private void drawProgress(Graphics g, ImageIcon imgIcon)
    {
		int value = this.getValue();
		int max = this.getMaximum();
		int img_left_width = img_left.getIconWidth();
		int img_right_width = img_right.getIconWidth();
		int remainImgWidth = (int)((this.getWidth()-img_left_width - img_right_width) * (value*1.0/max));
		
		int startPosition = img_left_width;
		int perDrawWidth = 0;
		int drawTotalWidth = 0;
		int perImgHeight = img.getIconHeight();
		int perImgWidth = img.getIconWidth();
		int height = Math.min(this.getHeight(), perImgHeight);
		g.drawImage(img_left.getImage(), 0, 0, img_left_width, height, 0, 0, img_left_width, height,null);
		
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
			g.drawImage(img_right.getImage(), startPosition+perDrawWidth, 0, startPosition + perDrawWidth + img_right.getIconWidth(), height, 0, 0, img_right.getIconWidth(), height,null);
			drawTotalWidth += perDrawWidth;
			startPosition = ((drawTotalWidth)/perImgWidth)*perImgWidth + img_left_width;
		}
		
    }

	public static void main(String[] args)
    {
	    JFrame frame = new JFrame();
	    frame.setSize(500, 300);
	    frame.setLocation(300,  300);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setLayout(new FlowLayout());
	    
	    PureProgressBarEx bar = new PureProgressBarEx(1, 100);
	    bar.setPreferredSize(new Dimension(300, 35));
	    frame.add(bar);
	    frame.setVisible(true);
	    bar.test();
    }
}