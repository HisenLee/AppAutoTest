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
 package com.cats.ui.alertdialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.cats.ui.custome.JBgColorButton;

/**
 * @author xblia
 * 2015年7月3日
 */
public class JAlertButton extends JBgColorButton implements ActionListener
{
    private static final long serialVersionUID = -4081393154373604828L;
    
    private ClickResCallback clickValCallback;
    private int btnId;
    
    public JAlertButton(String name)
    {
        super(name);
        init(AlertDialogOptions.OPTION_OK, null);
    }
    
    public JAlertButton(String name, int btnId)
	{
    	super(name);
    	init(btnId, null);
	}
    
	public JAlertButton(String name, int btnId, ClickResCallback clickValCallback)
	{
		super(name);
	}
	
	private void init(int iOption, ClickResCallback clickValCallback)
	{
		this.addActionListener(this);
		this.btnId = iOption;
		this.clickValCallback = clickValCallback;
		this.setFocusPainted(false);
	}

	@Override
    public void actionPerformed(ActionEvent e)
    {
	    if(null != clickValCallback)
	    {
	    	clickValCallback.setVal(btnId);
	    }
    }

    public void injectCallBackAndInfo(ClickResCallback clickValCallback, int btnId)
    {
        this.clickValCallback = clickValCallback;
        this.btnId = btnId;
    }
}
