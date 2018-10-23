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

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ImageIcon;
import javax.swing.JRadioButton;

import com.cats.utils.IColorFontUtil;

/**
 * @author xiaobolx
 * 2015年11月26日
 */
public class JRadioButtonEx extends JRadioButton implements ItemListener
{
    private static final long serialVersionUID = 1L;
    private static final ImageIcon ICON_CHECKED = new ImageIcon(JRadioButtonEx.class.getResource("checked_radio.png"));
    private static final ImageIcon ICON_UNCHECK = new ImageIcon(JRadioButtonEx.class.getResource("uncheck_radio.png"));

	public JRadioButtonEx(String name)
    {
		super(name);
		
		this.initView();
    }
	
	private void initView()
	{
		this.setBackground(IColorFontUtil.COLOR_WHITE_GRAY);
		this.setFocusable(false);
		this.setIcon(isSelected() ?ICON_CHECKED : ICON_UNCHECK);
		this.addItemListener(this);
	}

	@Override
    public void itemStateChanged(ItemEvent e)
    {
		this.setIcon(isSelected() ?ICON_CHECKED : ICON_UNCHECK);
		itemStateChangeEx(e);
    }
	
	protected void itemStateChangeEx(ItemEvent e)
	{
		
	}
}
