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
 package com.cats.ui.custome;


import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import com.cats.utils.IColorFontUtil;

/**
 * @author xiaobolx
 * 2015年11月11日
 */
public class JBgColorButton extends JButton implements MouseListener
{
    private static final long serialVersionUID = -319192911384600429L;
	
	public JBgColorButton(String title)
	{
		super(title);
		initView();
	}
	
	public JBgColorButton(String title, boolean isEnable)
	{
		super(title);
		initView();
		this.setEnabled(isEnable);
	}
	
	private void initView()
	{
		this.setFocusPainted(false);
		this.setBorderPainted(false);
		this.setForeground(Color.WHITE);
		this.setBackground(IColorFontUtil.COLOR_BTN_BLUE);
		this.setFont(IColorFontUtil.FONT_BLOK);
		//new RatationCursor(this);
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	@Override
    public void mouseClicked(MouseEvent e)
    {
    }

	@Override
    public void mousePressed(MouseEvent e)
    {
		this.setBackground(IColorFontUtil.COLOR_BTN_PRESS_GRAY);
    }

	@Override
    public void mouseReleased(MouseEvent e)
    {
		this.setBackground(IColorFontUtil.COLOR_BTN_BLUE);
    }

	@Override
    public void mouseEntered(MouseEvent e)
    {
    }

	@Override
    public void mouseExited(MouseEvent e)
    {
		this.setBackground(IColorFontUtil.COLOR_BTN_BLUE);
    }
	
	@Override
	public void setEnabled(boolean enable)
	{
	    super.setEnabled(enable);
	    if(enable)
	    {
	    	this.setBackground(IColorFontUtil.COLOR_BTN_BLUE);
	    }else
	    {
	    	this.setBackground(IColorFontUtil.COLOR_BTN_PRESS_GRAY);
	    }
	}
}
