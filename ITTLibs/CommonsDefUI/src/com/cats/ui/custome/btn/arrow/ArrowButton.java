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
 package com.cats.ui.custome.btn.arrow;

import java.awt.Cursor;
import java.awt.FlowLayout;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * @author xiaobolx
 * 2015年11月6日
 */
public class ArrowButton extends JButton
{
    private static final long serialVersionUID = 1L;
    
	private static final ImageIcon icon_gray = new ImageIcon(ArrowButton.class.getResource("btn_gray.png"));
	private static final ImageIcon icon_blue = new ImageIcon(ArrowButton.class.getResource("btn_blue.png"));
	private static final ImageIcon icon_gray_blue_gray = new ImageIcon(ArrowButton.class.getResource("btn_gray_blue_gray.png"));

	public ArrowButton()
	{
		this.setFocusPainted(false);
		this.setOpaque(false);
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		this.setBorder(null);
		this.setBorderPainted(false);
		this.setIcon(icon_gray);
	}
	
	protected void setIconGray()
	{
		this.setIcon(icon_gray);
	}
	
	protected void setIconBlue()
	{
		this.setIcon(icon_blue);
	}
	
	protected void setIconGrayBlueGray()
	{
		this.setIcon(icon_gray_blue_gray);
	}
	
	private void test()
    {
		final Timer timer = new Timer();
		timer.schedule(new TimerTask()
		{
			int index = 1;
			@Override
			public void run()
			{
				index++;
				if(index %2 == 0)
				{
					ArrowButton.this.setIconGrayBlueGray();
				}else
				{
					ArrowButton.this.setIconGray();
				}
				
			}
		}, 1000, 500);
    }
	
	public static void main(String[] args)
    {
		JFrame frame = new JFrame();
	    frame.setSize(500, 300);
	    frame.setLocation(300,  300);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setLayout(new FlowLayout());
	    
	    ArrowButton btn = new ArrowButton();
	    frame.add(btn);
	    frame.setVisible(true);
	    btn.test();
    }

}
