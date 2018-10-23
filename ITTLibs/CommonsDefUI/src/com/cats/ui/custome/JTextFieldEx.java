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

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import com.cats.utils.IColorFontUtil;

/**
 * @author xblia
 * 2015年10月21日
 */
public class JTextFieldEx extends JTextField
{
    private static final long serialVersionUID = 2492921459023559680L;
    public JTextFieldEx()
    {
    	initView();
    }
    
    public JTextFieldEx(int length)
    {
    	super(length);
    	initView();
    }
    
    private void initView()
    {
    	this.setPreferredSize(new Dimension(-1, 28));
    	this.setBorder(new LineBorder(IColorFontUtil.COLOR_TEXTFIELD_BORDER));
    	setBorder(BorderFactory.createCompoundBorder(
    	        this.getBorder(), 
    	        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
    }

}
