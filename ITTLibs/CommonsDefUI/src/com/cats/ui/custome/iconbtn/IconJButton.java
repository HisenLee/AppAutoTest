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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * @author xiaobolx
 * 2015年11月10日
 */
public class IconJButton extends JButton implements MouseListener
{

    private static final long serialVersionUID = 1L;
    private ImageIcon normalIcon;
    private ImageIcon enterIcon;
    private ImageIcon pressIcon;
    
    
    public IconJButton()
    {
	    super();
    }


	protected void initIconButton(ImageIcon normalIcon, ImageIcon enterIcon,
            ImageIcon pressIcon)
	{
		this.normalIcon = normalIcon;
	    this.enterIcon = enterIcon;
	    this.pressIcon = pressIcon;
	    
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
    public void mouseClicked(MouseEvent e)
    {
	    
    }


	@Override
    public void mousePressed(MouseEvent e)
    {
		this.setIcon(pressIcon);
    }


	@Override
    public void mouseReleased(MouseEvent e)
    {
	    this.setIcon(normalIcon);
    }


	@Override
    public void mouseEntered(MouseEvent e)
    {
		this.setIcon(enterIcon);
    }


	@Override
    public void mouseExited(MouseEvent e)
    {
	    this.setIcon(normalIcon);
    }

}
