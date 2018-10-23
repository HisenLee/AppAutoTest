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
 package com.cats.ui.custome.arrowprogressbar;

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
public class ArrowProgressBar extends JProgressBar
{
    private static final long serialVersionUID = 1L;
    public static int MAX_VALUE = 100;
    
    private ImageIcon img_middle = new ImageIcon(ArrowProgressBar.class.getResource("progressbar_middle.png"));
    private ImageIcon img_arrow = new ImageIcon(ArrowProgressBar.class.getResource("progressbar_right.png"));
    private ImageIcon img_background = new ImageIcon(ArrowProgressBar.class.getResource("progressbar_bg_middle.png"));
    private ImageIcon img_background_arrow = new ImageIcon(ArrowProgressBar.class.getResource("progressbar_bg_right.png"));
    private JLabel labelInfo;
    
    private Timer timer;
    private ImageIcon img_arrow_blue_gray;
    private int currentPosition;
    private int positionOffset = 3;
    
    private boolean needIndeterMinate = false;
    private boolean isIndeterminate = false;
    private boolean frontArrow = false;
    private boolean behindArrow = false;
    
    public ArrowProgressBar(String name, boolean frontArrow, boolean behindArrow)
    {
    	this.frontArrow = frontArrow;
    	this.behindArrow = behindArrow;
    	this.initView(name);
    }
    
    public ArrowProgressBar(String name, boolean isIndeterminate, boolean frontArrow, boolean behindArrow)
    {
    	this.isIndeterminate = isIndeterminate;
    	this.needIndeterMinate = isIndeterminate;
    	this.frontArrow = frontArrow;
    	this.behindArrow = behindArrow;
    	this.initView(name);
    	
    	if(isIndeterminate)
    	{
    		img_arrow_blue_gray = new ImageIcon(ArrowProgressBar.class.getResource("icon_arrow_blue_gray.png"));
    		currentPosition = frontArrow ? img_arrow.getIconWidth()+1:0;
    	}
    }
    
    public ArrowProgressBar(String name, int min, int max)
    {
    	this.initView(name);
    }
    
    public ArrowProgressBar(String name)
    {
    	this.initView(name);
    }

    private void initView(String name)
    {
    	this.setOrientation(JProgressBar.HORIZONTAL);
    	this.setBorder(null);
    	this.setOpaque(false);
    	
    	labelInfo = new JLabel(name, JLabel.CENTER);
    	labelInfo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    	this.setLayout(new BorderLayout());
    	this.add(labelInfo, BorderLayout.CENTER);
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
				ArrowProgressBar.this.setValue(index);
				index++;
				if(index > 100)
				{
					index = 1;
				}
			}
		}, 1000, 10);
    }
	
	@Override
	protected void paintComponent(Graphics g)
	{
		int width = this.getWidth();
		drawProgressBackground(g, width);
		if (this.getValue() > 1)
		{
			if (isIndeterminate)
			{
				drawIndeterminateProgress(g, img_middle);
			} else
			{
				drawProgress(g, img_middle);
			}
		}
	}
	
	private void drawProgressBackground(Graphics g, int needDrawWidth)
    {
		int height = Math.min(this.getHeight(), img_background.getIconHeight());
		int startPosition = 0;
		int perDrawWidth = 0;
		int drawTotalWidth = 0;
		int arrowImgWidth = img_background_arrow.getIconWidth();
		int remainImgWidth = needDrawWidth;
		if(frontArrow)
		{
			remainImgWidth -= arrowImgWidth;
		}
		if(behindArrow)
		{
			remainImgWidth -= arrowImgWidth;
		}
		
		
		int perMinddleImgWidth = img_background.getIconWidth();
		int perMiddleImgHeight = img_background.getIconHeight();
		
		if(frontArrow)
		{
			g.drawImage(img_background_arrow.getImage(), 0, 0, img_background_arrow.getIconWidth(), height, 0, 0, img_background_arrow.getIconWidth(), height, null);
			startPosition = arrowImgWidth;
		}
		
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
			startPosition = ((drawTotalWidth)/perMinddleImgWidth)*perMinddleImgWidth + (frontArrow ? arrowImgWidth : 0);
			remainImgWidth = remainImgWidth - perDrawWidth;
		}
		
		if(behindArrow)
		{
			int endX = needDrawWidth - arrowImgWidth;
			g.drawImage(img_background_arrow.getImage(), endX, 0, endX + arrowImgWidth, height, 0, 0, arrowImgWidth, height, null);
		}
    }
	
	private void drawProgress(Graphics g, ImageIcon imgIcon)
    {
		int startPosition = 0;
		int perDrawWidth = 0;
		int drawTotalWidth = 0;
		int perImgHeight = img_middle.getIconHeight();
		int perImgWidth = img_middle.getIconWidth();
		int height = Math.min(this.getHeight(), perImgHeight);
		
		int value = this.getValue();
		int max = this.getMaximum();
		int img_arrow_width = img_arrow.getIconWidth();
		int progressWidth = this.getWidth();
		if (frontArrow)
		{
			progressWidth -= img_arrow_width;
			startPosition += img_arrow_width;
			g.drawImage(img_arrow.getImage(), 0, 0, img_arrow_width, height, 0, 0, img_arrow_width, height, null);
		}
		if (behindArrow)
		{
			progressWidth -= img_arrow_width;
		}
		
		int remainImgWidth = (int)(progressWidth* (value*1.0/max));
		boolean isFinished = progressWidth == remainImgWidth;
		
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
			startPosition = ((drawTotalWidth)/perImgWidth)*perImgWidth +( frontArrow ? img_arrow_width:0);
		}
		
		if(isFinished && behindArrow)
		{
			int endX = progressWidth + (frontArrow ? img_arrow_width : 0);
			g.drawImage(img_arrow.getImage(), endX, 0, endX + img_arrow_width, height, 0, 0, img_arrow_width, height,null);
		}
		
    }
	
	private void drawIndeterminateProgress(Graphics g, ImageIcon imgIcon)
    {
		int startPosition = 0;
		int endPosition = this.getWidth();
		int perImgHeight = img_middle.getIconHeight();
		int perImgWidth = img_middle.getIconWidth();
		int height = Math.min(this.getHeight(), perImgHeight);
		
		int img_arrow_width = img_arrow.getIconWidth();
		if (frontArrow)
		{
			startPosition += img_arrow_width;
			ImageIcon fontIcon = isIndeterminate ? img_arrow_blue_gray : img_arrow;
			g.drawImage(fontIcon.getImage(), 0, 0, img_arrow_width, height, 0, 0, img_arrow_width, height, null);
		}
		if (behindArrow)
		{
			endPosition -= img_arrow_width;
		}
		endPosition -= perImgWidth;
		
		
		int currPos = getNextPosition(startPosition, endPosition);
		g.drawImage(img_middle.getImage(), currPos, 0, currPos + perImgWidth, height, 0, 0, perImgWidth, height,null);
		
		if(behindArrow &&  this.getValue() == this.getMaximum())
		{
			g.drawImage(img_arrow.getImage(), endPosition+perImgWidth, 0, endPosition+perImgWidth + img_arrow.getIconWidth(), height, 0, 0, img_arrow.getIconWidth(), height,null);
		}
    }

	private int getNextPosition(int startPosition, int endPostion)
    {
		if(currentPosition > endPostion || currentPosition < startPosition)
		{
			positionOffset *= -1;
		}
	    currentPosition += positionOffset;
	    return currentPosition;
    }
	
	@Override
	public void setIndeterminate(boolean isIndeterminate)
	{
		this.isIndeterminate = isIndeterminate;
	}
	
	public boolean startIndeterminateProgress()
	{
		if(!needIndeterMinate)
		{
			return false;
		}
		if(timer != null)
		{
			timer.cancel();
		}
		timer = new Timer();
		timer.schedule(new TimerTask()
		{
			int iValue = 1;
			@Override
			public void run()
			{
				setValue(iValue);
				if(iValue >= 100)
				{
					iValue = 1;
				}
				iValue++;
			}
		}, 100, 50);
		this.setIndeterminate(needIndeterMinate);
		return true;
	}
	
	public boolean stopIndeterminateProgress()
	{
		if(!isIndeterminate)
		{
			return false;
		}
		this.setIndeterminate(false);
		if(timer != null)
		{
			timer.cancel();
			timer = null;
		}
		this.setValue(MAX_VALUE);
		return true;
	}

	public static void main(String[] args)
    {
	    JFrame frame = new JFrame();
	    frame.setSize(500, 300);
	    frame.setLocation(300,  300);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setLayout(new FlowLayout());
	    
	    for (int i = 0; i < 200; i++)
        {
	    	ArrowProgressBar bar = new ArrowProgressBar("Install", false, false, false);
	    	bar.setPreferredSize(new Dimension(150, 35));
	    	frame.add(bar);
	    	//bar.startIndeterminateProgress();
	    	bar.test();
        }
	    frame.setVisible(true);
    }
}