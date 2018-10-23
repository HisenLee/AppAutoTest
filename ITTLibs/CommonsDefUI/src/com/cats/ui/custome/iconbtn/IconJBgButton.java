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
 package com.cats.ui.custome.iconbtn;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * @author xiaobolx
 * 2015年11月11日
 */

public class IconJBgButton extends JButton implements MouseListener
{

    private static final long serialVersionUID = 1L;
    private static ImageIcon normalIcon = new ImageIcon(IconJBgButton.class.getResource("/com/cats/ui/custome/iconbtn/icon_btn_bg_normal.png"));
    private static ImageIcon enterIcon = new ImageIcon(IconJBgButton.class.getResource("/com/cats/ui/custome/iconbtn/icon_btn_bg_enter.png"));
    private static ImageIcon pressIcon = new ImageIcon(IconJBgButton.class.getResource("/com/cats/ui/custome/iconbtn/icon_btn_bg_press.png"));
    
    private String name;
    private ImageIcon drawIcon = normalIcon;
    
    public IconJBgButton(String name)
    {
    	this.name = name;
    	this.setText(name);
    	
    	this.initIconButton();
    }


	protected void initIconButton()
	{
	    this.setOpaque(false);
	    this.setBorderPainted(false);
		this.setIcon(normalIcon);
		this.setPreferredSize(new Dimension(normalIcon.getIconWidth(), normalIcon.getIconHeight()));
		this.setFocusPainted(false);
		this.setBackground(new Color(0xf5,0xf5,0xf5));
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		this.addMouseListener(this);
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		int startPosition = 0;
		int perDrawWidth = 0;
		int drawTotalWidth = 0;
		int width = this.getWidth();
		int height = this.getHeight();
		
		int remainImgWidth = width;
		startPosition = 0;
		int perMinddleImgWidth = drawIcon.getIconWidth();
		int perMiddleImgHeight = drawIcon.getIconHeight();
		while(remainImgWidth > 0)
		{
			if(remainImgWidth/perMinddleImgWidth > 0)
			{
				perDrawWidth = perMinddleImgWidth;
			}else
			{
				perDrawWidth = remainImgWidth; 
			}
			g.drawImage(drawIcon.getImage(), startPosition, 0, startPosition + perDrawWidth, height, 0, 0, perDrawWidth, perMiddleImgHeight, Color.white, null);
			drawTotalWidth += perDrawWidth;
			startPosition = ((drawTotalWidth)/perMinddleImgWidth)*perMinddleImgWidth;
			remainImgWidth = remainImgWidth - perDrawWidth;
		}
		this.setText(name);
	}

	@Override
    public void mouseClicked(MouseEvent e)
    {
	    
    }

	@Override
    public void mousePressed(MouseEvent e)
    {
		drawIcon = pressIcon;
		this.repaint();
    }

	@Override
    public void mouseReleased(MouseEvent e)
    {
		drawIcon = normalIcon;
		this.repaint();
    }

	@Override
    public void mouseEntered(MouseEvent e)
    {
		drawIcon = enterIcon;
		this.repaint();
    }

	@Override
    public void mouseExited(MouseEvent e)
    {
		drawIcon = normalIcon;
		this.repaint();
    }
	
	public static void main(String[] args)
    {
	    JFrame frame = new JFrame();
	    frame.setSize(500, 300);
	    frame.setLocation(300,  300);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setLayout(new FlowLayout());
	    
	    for (int i = 0; i < 20; i++)
        {
	    	IconJBgButton btn = new IconJBgButton("Nexus@5.1");
	    	btn.setPreferredSize(new Dimension(100, 30));
	    	frame.add(btn);
        }
	    frame.setVisible(true);
    }

}
