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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;

/**
 * @author xiaobolx
 * 2015年11月2日
 */
public class JIconCheckBox extends JCheckBox implements ItemListener
{
	public enum STYLE
	{
		BLACK, 
		WHITE,
	}
	
    private static final long serialVersionUID = 1L;
    private static final ImageIcon ICON_CHECKED = new ImageIcon(JIconCheckBox.class.getResource("checked_checkbox.png"));
    private static final ImageIcon ICON_CHECKED_WHITE = new ImageIcon(JIconCheckBox.class.getResource("checked_checkbox_white.png"));
    private static final ImageIcon ICON_UNCHECKED= new ImageIcon(JIconCheckBox.class.getResource("unchecked_checkbox.png"));
    private static final ImageIcon ICON_UNCHECKED_WHITE = new ImageIcon(JIconCheckBox.class.getResource("unchecked_checkbox_white.png"));
    		
    private ImageIcon selectIcon;
    private ImageIcon unselectIcon;
    public JIconCheckBox()
    {
    	selectIcon = ICON_CHECKED;
    	unselectIcon = ICON_UNCHECKED;
    	init();
    }
    
    public JIconCheckBox(String name)
    {
    	super(name);
    	selectIcon = ICON_CHECKED;
    	unselectIcon = ICON_UNCHECKED;
    	init();
    }
    
    public JIconCheckBox(STYLE style)
    {
    	if(style == STYLE.BLACK)
    	{
    		selectIcon = ICON_CHECKED;
    		unselectIcon = ICON_UNCHECKED;
    	}
    	else
    	{
    		selectIcon = ICON_CHECKED_WHITE;
    		unselectIcon = ICON_UNCHECKED_WHITE;
    	}
    	init();
    }
    
	private void init()
    {
		this.setBackground(Color.WHITE);
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		this.addItemListener(this);
		this.setIcon(unselectIcon);
    }
	@Override
    public void itemStateChanged(ItemEvent arg0)
    {
		if(this.isSelected())
		{
			this.setIcon(selectIcon);
		}
		else
		{
			this.setIcon(unselectIcon);
		}
    }
}
