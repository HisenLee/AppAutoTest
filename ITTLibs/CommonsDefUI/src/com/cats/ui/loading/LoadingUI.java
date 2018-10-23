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
 package com.cats.ui.loading;

import java.awt.BorderLayout;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * @author xblia
 * 2015-07-23
 */
public class LoadingUI extends JDialog
{
    private static final long serialVersionUID = 8965211789191801378L;
	private int width;
	private int height;
	private JLabel imgLabel;
	private JLabel infoLabel;
	
	private Timer timer = new Timer();
	private int imageIndex=1;
	
	public LoadingUI(final int x, final int y)
	{
		new Thread()
		{
			@Override
			public void run()
			{
				initLoading(x, y);
			}
		}.start();
	}
	
	public LoadingUI(final JFrame frame)
	{
		new Thread()
		{
			@Override
			public void run()
			{
				int x = frame.getX() + frame.getWidth()/2 - width/2; 
				int y = frame.getY() + frame.getHeight()/2 - height/2; 
				initLoading(x, y);
			}
		}.start();
	}
	
	private void initLoading(int x, int y)
	{
		this.width = 100;
		this.height = 100;
		this.setUndecorated(true);
		this.setAlwaysOnTop(true);
		this.setTitle("Version service");
		this.setSize(width, height);
		this.setLocation(x, y);
		
		imgLabel = new JLabel("",JLabel.CENTER);
		infoLabel = new JLabel("Loading...", JLabel.CENTER);
		this.setLayout(new BorderLayout(0, 0));
		this.add(imgLabel, BorderLayout.CENTER);
		this.add(infoLabel, BorderLayout.SOUTH);

		timer.schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				printLoading();
			}
		}, 0, 100);
		this.setModal(true);
		this.setVisible(true);
	}
	
	private void printLoading()
	{
		if(imageIndex > 18) imageIndex = 1;
		String imageName = "loading_";
		imageName += (imageIndex < 10 ? "0":"") + imageIndex;
		imageName += ".png";
		
		ImageIcon img = new ImageIcon(LoadingUI.class.getResource(imageName));
		imgLabel.setIcon(img);
		imageIndex++;
	}
	
	public void stopLoading()
	{
		try
        {
	        timer.cancel();
	        this.dispose();
        } catch (Exception e)
        {
	        e.printStackTrace();
        }
	}
	
	
	public static void main(String[] args) throws Exception
	{
		LoadingUI ui = new LoadingUI(200, 100);
		Thread.sleep(5000);
		ui.stopLoading();
	}
}
