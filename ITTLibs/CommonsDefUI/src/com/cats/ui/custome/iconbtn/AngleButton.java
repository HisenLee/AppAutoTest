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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import com.cats.utils.IColorFontUtil;

/**
 * @author xiaobolx
 * 2015年11月20日
 */
public class AngleButton extends JButton implements MouseListener
{
    private static final long serialVersionUID = 1L;
    private static final int CORNER_ANGLE = 3;
    private static final Color COLOR_NORMAL = new Color(0x28A7E1);
    private static final Color COLOR_ENTERL = new Color(0x28A7C0);
    private static final Color COLOR_PRESS = new Color(0xA7DAE8);
    
    private ImageIcon icon;
    private JLabel infoLabel;
    private AngleBtnMouseStatus mouseStatus = AngleBtnMouseStatus.normal;
    private ButtonStyle btnStyle = ButtonStyle.Corner;
    
    private enum ButtonStyle
    {
    	Corner,
    	Rectangle,
    	Rectangle3D,
    }
    
    private enum AngleBtnMouseStatus
    {
    	normal(COLOR_NORMAL),
    	enter(COLOR_ENTERL),
    	press(COLOR_PRESS);
    	
    	private Color bgColor;

		AngleBtnMouseStatus(Color bgColor)
        {
	        this.bgColor = bgColor;
        }
		public Color getColor()
		{
			return this.bgColor;
		}
    }
    
    public AngleButton(String name)
    {
    	this.infoLabel = new JLabel(name, JLabel.CENTER);
    	this.infoLabel.setForeground(Color.WHITE);
    	this.infoLabel.setFont(IColorFontUtil.FONT_BTN);
    	this.setPreferredSize(new Dimension(180, 22));
    	
    	this.initIconButton();
    }


	protected void initIconButton()
	{
		this.setLayout(new GridLayout(1, 1, 0, 0));
		this.add(infoLabel, BorderLayout.CENTER);
		
	    this.setOpaque(false);
	    this.setBorder(new EmptyBorder(0, 0, 0, 0));
		this.setFocusPainted(false);
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		this.addMouseListener(this);
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		//Draw Background.
		if(isEnabled())
		{
			g.setColor(mouseStatus.getColor());
		}else
		{
			g.setColor(Color.WHITE.darker());
		}
		switch (btnStyle)
        {
		case Corner:
			g.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, CORNER_ANGLE, CORNER_ANGLE);
			g.fillRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, CORNER_ANGLE, CORNER_ANGLE);
			break;
		case Rectangle:
			g.drawRect(0, 0, this.getWidth(), this.getHeight());
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			break;
		case Rectangle3D:
			g.fill3DRect(0, 0, this.getWidth(), this.getHeight(), true);
			break;
		default:
			break;
		}
	}

	@Override
    public void mouseClicked(MouseEvent e)
    {
	    
    }

	@Override
	public void mousePressed(MouseEvent e)
	{
		if (isEnabled())
		{
			mouseStatus = AngleBtnMouseStatus.press;
			this.repaint();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		if (isEnabled())
		{
			mouseStatus = AngleBtnMouseStatus.normal;
			this.repaint();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		if (isEnabled())
		{
			mouseStatus = AngleBtnMouseStatus.enter;
			this.repaint();
		}
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		if (isEnabled())
		{
			mouseStatus = AngleBtnMouseStatus.normal;
			this.repaint();
		}
	}
	
	
	public void setAngleBtnIcon(ImageIcon defaultIcon)
	{
		this.icon = defaultIcon;
		if(isEnabled())
		{
			this.infoLabel.setIcon(icon);
		}
		else
		{
			this.infoLabel.setIcon(null);
		}
	}
	
	@Override
	public void setEnabled(boolean b)
	{
	    super.setEnabled(b);
	    if(!b)
	    {
	    	this.infoLabel.setIcon(null);
	    	this.infoLabel.setForeground(Color.gray);
	    }else
	    {
	    	this.infoLabel.setForeground(Color.WHITE);
	    	this.infoLabel.setIcon(icon);
	    }
	}
	
	public static void main(String[] args)
    {
	    JFrame frame = new JFrame();
	    frame.setSize(500, 300);
	    frame.setLocation(300,  300);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setLayout(new FlowLayout());
	    
	    for (int i = 0; i < 1; i++)
        {
	    	AngleButton btn = new AngleButton("OpenFolder");
	    	btn.setEnabled(true);
	    	btn.setAngleBtnIcon(new ImageIcon(AngleButton.class.getResource("/com/cats/ui/custome/iconbtn/res_pass.png")));
	    	frame.add(btn);
        }
	    frame.setVisible(true);
    }

}
