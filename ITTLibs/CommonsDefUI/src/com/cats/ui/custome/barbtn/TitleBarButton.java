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
 package com.cats.ui.custome.barbtn;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.cats.ui.graceframe.IGraceFulTitlePanelHandler;

/**
 * @author xiaobolx
 * 2015年11月10日
 */
public class TitleBarButton extends JButton implements MouseListener
{
	/**
	 */
    private static final long serialVersionUID = 1L;

	private Image img;
	private MouseStatus mouseStatus;
	private int btnWidth;
	private int btnHeight;
	
	private BarIconType iconType;
	private IGraceFulTitlePanelHandler handler;
	
	public TitleBarButton(BarIconType iconType, IGraceFulTitlePanelHandler handler)
    {
		this.iconType = iconType;
		this.handler = handler;
		this.mouseStatus = MouseStatus.normal;
		
		ImageIcon imgIcon = new ImageIcon(TitleBarButton.class.getResource("/com/cats/ui/custome/barbtn/system_button_"+iconType.getName()+".png"));
		this.btnWidth = imgIcon.getIconWidth()/3;
		this.btnHeight = imgIcon.getIconHeight();
		this.img = imgIcon.getImage();
		
	    this.setOpaque(false);
	    this.setBorderPainted(false);
		this.setPreferredSize(new Dimension(btnWidth, btnHeight));
		this.setFocusPainted(false);
		this.setBackground(new Color(0xf5,0xf5,0xf5));
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		this.addMouseListener(this);
    }
	
	@Override
	protected void paintComponent(Graphics g)
	{
		int imgX = 0;
		switch (this.mouseStatus)
		{
		case normal:
			imgX = 0;
			break;
		case enter:
			imgX = this.btnWidth;
			break;
		case press:
			imgX = this.btnWidth * 2;
			break;
		}
		g.drawImage(img, 0, 0, btnWidth, btnHeight, imgX, 0, imgX+btnWidth, btnHeight, null);
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		switch (iconType)
		{
		case MENU:
			Point point = new Point(0, this.getHeight());
			handler.onMenuButton(this, point);
			break;
		case MIN:
			handler.onMinButton();
			break;
		case MAX:
			handler.onMaxButton();
			break;
		case CLOSE:
			handler.onCloseButton();
			break;
		default:
			break;
		}
	}

	@Override
    public void mousePressed(MouseEvent e)
    {
		changeMouseStatus(MouseStatus.press);
    }

	@Override
    public void mouseReleased(MouseEvent e)
    {
		changeMouseStatus(MouseStatus.normal);
    }

	@Override
    public void mouseEntered(MouseEvent e)
    {
		changeMouseStatus(MouseStatus.enter);
    }

	@Override
    public void mouseExited(MouseEvent e)
    {
		changeMouseStatus(MouseStatus.normal);
    }
	
	private void changeMouseStatus(MouseStatus mouseStatus)
	{
		this.mouseStatus = mouseStatus;
		this.repaint();
	}
}
