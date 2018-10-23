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
 package com.cats.ui.custome.menu;

import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.border.EmptyBorder;

import com.cats.utils.IColorFontUtil;

/**
 * @author xiaobolx
 * 2015年11月19日
 */
public class MenuItemEx extends JMenuItem
{
    private static final long serialVersionUID = 1L;

	public MenuItemEx(String name, ActionListener listener)
    {
		super(name);
		this.addActionListener(listener);
		this.initMenu();
    }
	
	private void initMenu()
	{
		this.setFont(IColorFontUtil.FONT_BLOK);
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
	}
}
