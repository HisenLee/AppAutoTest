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
 package com.ui.stylemenu;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;

/**
 * @author xblia
 * 2015年9月18日
 */
public class StyleMenuActions implements Action
{
	private String styleName;
	private String styleClass;
	private String executableName;
	public StyleMenuActions(String styleName, String styleClass, String executableName)
    {
		this.styleName = styleName;
		this.styleClass = styleClass;
		this.executableName = executableName;
    }


	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		StyleStore.getStore().storeStyleToFile(styleClass);
		StartSubProcess.startUp(executableName);
	}

	@Override
    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
	    
    }

	@Override
    public Object getValue(String key)
    {
		if(key == Action.NAME)
		{
			return this.styleName;
		}
	    return null;
    }

	@Override
    public boolean isEnabled()
    {
	    return true;
    }

	@Override
    public void putValue(String key, Object value)
    {
    }

	@Override
    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
	    
    }

	@Override
    public void setEnabled(boolean b)
    {
	    
    }
}
