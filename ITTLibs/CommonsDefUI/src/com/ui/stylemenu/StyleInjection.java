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

import java.lang.reflect.Method;
import java.util.Properties;

import javax.swing.UIManager;

/**
 * @author xblia
 * 2015年9月18日
 */
public class StyleInjection
{
	protected void initComstomeStyle()
    {
		String styleClass = StyleStore.getStore().getStyleFromStoreFile();
		if(null != styleClass)
		{
			try
	        {
				setPropertyies(styleClass);
		        UIManager.setLookAndFeel(styleClass);
	        } catch (Exception e)
	        {
		        e.printStackTrace();
	        }
		}
    }
	
	private void setPropertyies(String style)
    {
		Properties props = new Properties();
		props.put("logoString", "xblia");
		props.put("licenseKey", "intel");
		try
        {
	        Object styleObj = Class.forName(style).newInstance();
	        Method []styleMethod = styleObj.getClass().getMethods();
	        if(styleMethod != null)
	        {
	        	for (int i = 0; i < styleMethod.length; i++)
                {
	                if(styleMethod[i].getName().equals("setTheme"))
	                {
	                	Class<?> [] paramType = styleMethod[i].getParameterTypes();
	                	if((paramType.length == 1) && paramType[0].equals(Properties.class))
	                	{
	                		styleMethod[i].invoke(styleObj, props);
	                		break;
	                	}
	                }
                }
	        }
        } catch (Exception e)
        {
	        e.printStackTrace();
        }
    }
}
