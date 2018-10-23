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
 package com.cats.version.client.ui;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import com.cats.ui.custome.barbtn.BarIconType;
import com.cats.ui.graceframe.IGraceFullyFrameBase;
import com.cats.version.msg.BroadcastMsg;
import com.cats.version.msg.IMessageDef;

/**
 * @author xiaobolx
 * 2016年1月8日
 */
public class IMBroadcastMsgTast extends IGraceFullyFrameBase implements Runnable
{
    private static final long serialVersionUID = 6225773380678269602L;
    
    private static Font FONT = new Font("微软雅黑", Font.PLAIN, 12);
    private static String TITLE = "From Version Service Notify.";
    
    private static int BARSTYLE = BarIconType.TITLE.getType() | BarIconType.CLOSE.getType();
    private JTextArea contentTextArea;
    private ImageIcon msgPriorityIcon;
    private int windowHeiht = Toolkit.getDefaultToolkit().getScreenSize().height;
    private boolean isFirst = true;
    private long frameDisplayLongtime = 5000;
    private boolean isRunning = false;
	
	public IMBroadcastMsgTast(BroadcastMsg msg)
    {
		super(TITLE, null, BARSTYLE);
		controlBroadcastMsg(msg);
		this.initSize(msg.getMsgInfo());
		this.setIconImage(new ImageIcon(IMBroadcastMsgTast.class.getResource("icon_broadcast_msg.png")).getImage());
		this.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth())/2, -1 * this.getHeight());
		this.setAlwaysOnTop(true);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		contentTextArea = new JTextArea();
		contentTextArea.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentTextArea.setFont(FONT);
		contentTextArea.setWrapStyleWord(true);
		contentTextArea.setLineWrap(true);
		contentTextArea.setEditable(false);
		contentTextArea.setText(msg.getMsgInfo());
		JScrollPane contentTextScrollPanel = new JScrollPane(contentTextArea);
		
		JLabel iconLabel = new JLabel(msgPriorityIcon, JLabel.LEFT);
		iconLabel.setBorder(new EmptyBorder(5, 5, 5, 5));
		centerPanel.add(iconLabel, BorderLayout.WEST);
		centerPanel.add(contentTextScrollPanel, BorderLayout.CENTER);
		this.add(centerPanel, BorderLayout.CENTER);
		
		this.addClickListener();
		this.setVisible(true);
		isRunning = true;
		new Thread(this).start();
		
		
    }
	
	private void controlBroadcastMsg(BroadcastMsg msg)
    {
		String priorityMsg = msg.getMsgPriority();
		String iconPath = "icon_priority_normal.png";
		if(IMessageDef.BroadcastMsgPriority.Normal.toString().equals(priorityMsg))
		{
			frameDisplayLongtime = 5000;
		}else if(IMessageDef.BroadcastMsgPriority.Important.toString().equals(priorityMsg))
		{
			iconPath = "icon_priority_important.png";
			frameDisplayLongtime = 1000*60;
		}if(IMessageDef.BroadcastMsgPriority.VeryImportant.toString().equals(priorityMsg))
		{
			iconPath = "icon_priority_veryimportant.png";
			frameDisplayLongtime = 1000*60*60*3;
		}
		msgPriorityIcon = new ImageIcon(IMBroadcastMsgTast.class.getResource(iconPath));
    }

	protected void initSize(String message)
    {
    	FontMetrics fm = this.getFontMetrics(FONT);
    	int w = fm.stringWidth(message) + 200;
    	int h = fm.getHeight() + 120;
    	if(w > Toolkit.getDefaultToolkit().getScreenSize().width*0.6)
    	{
    		w = (int)(Toolkit.getDefaultToolkit().getScreenSize().width*0.6);
    	}
    	this.setSize(w, h);
    }
	
	@Override
    public String getIconRelativePath()
    {
	    return "/com/cats/version/client/ui/icon_broadcast_msg.png";
    }
	
	private void addClickListener()
    {
		this.getRootPane().addMouseListener(new MouseAdapter()
		{
			long lastClickTime = 0;
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if(System.currentTimeMillis() - lastClickTime < 800)
				{
					IMBroadcastMsgTast.this.setUndecorated(false);
					IMBroadcastMsgTast.this.repaint();
				}
				lastClickTime = System.currentTimeMillis();
			}
		});
    }
	
	@Override
    public void run()
    {
	    int originalX = this.getLocation().x;
	    int moveLen = Toolkit.getDefaultToolkit().getScreenSize().height;
	    moveWindow(true, originalX, moveLen - this.getHeight());
	    try
        {
	    	long remainSleep = frameDisplayLongtime;
	    	int perSleepTime = 500;
	    	while(remainSleep > 0 && isRunning)
	    	{
	    		if(remainSleep < perSleepTime)
	    		{
	    			Thread.sleep(remainSleep);
	    		}
	    		else
	    		{
	    			Thread.sleep(perSleepTime);
	    		}
	    		remainSleep = remainSleep - perSleepTime;
	    	}
        } catch (InterruptedException e)
        {
	        e.printStackTrace();
        }
	    this.dispose();
    }
	
	private void moveWindow(boolean isDown, int originalX, int moveLen)
	{
		int y = this.getLocation().y;
		int Len = moveLen;
		if(isDown)
		{
			int i = 1;
			while (y + this.getHeight() < windowHeiht)
			{
				i++;
				y += i;
				this.setLocation(originalX, y);
				try
				{
					Thread.sleep(10);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}else
		{
			int i = 1;
			while (Len > 0)
			{
				i--;
				y += i;
				Len = Len + i;
				this.setLocation(originalX, y);
				try
				{
					Thread.sleep(10);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
		if(isFirst)
		{
			isFirst = false;
			moveLen = moveLen/8;
		}
		
		if(moveLen > 0)
		{
			moveWindow(!isDown, originalX, (int)(moveLen/1.4));
		}
	}
	
	@Override
	public void onCloseButton()
	{
		isRunning = false;
		this.dispose();
	}

	public static void main(String[] args)
    {
		new IMBroadcastMsgTast(new BroadcastMsg("Important", "Hello"));
    }
}