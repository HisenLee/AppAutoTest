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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.cats.utils.IOUtils;

/**
 * @author xblia
 * 2015年9月18日
 */
public class StyleStore
{
	private static StyleStore styleStore = new StyleStore();

	protected static StyleStore getStore()
	{
		return styleStore;
	}

	private StyleStore()
	{
	}

	private String STYLE_KEY = "style";
	private String propertyPath = System.getProperty("user.home")
	        + File.separator + "cats_software.property";
	private String propertyName = "common.properties";

	protected String getStyleFromStoreFile()
	{
		File file = new File(propertyPath + File.separator + propertyName);
		if (!file.exists())
		{
			return null;
		}
		Properties properties = new Properties();
		FileInputStream fis = null;
		try
		{
			fis = new FileInputStream(file);
			properties.load(fis);
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			IOUtils.closeResource(fis);
		}
		String styleClass = properties.getProperty(STYLE_KEY, null);
		if(styleClass != null && styleClass.equals("Origin"))
		{
			styleClass = null;
		}
		return styleClass;
	}

	protected void storeStyleToFile(String styleClass)
	{
		File file = new File(propertyPath + File.separator + propertyName);
		if (!file.exists())
		{
			file.getParentFile().mkdirs();
			try
			{
				file.createNewFile();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		Properties properties = new Properties();
		FileInputStream fis = null;
		try
		{
			fis = new FileInputStream(file);
			properties.load(fis);
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			IOUtils.closeResource(fis);
		}

		FileOutputStream fos = null;
		properties.put(STYLE_KEY, styleClass);
		try
		{
			fos = new FileOutputStream(file);
			properties.store(fos, "-");
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			IOUtils.closeResource(fos);
		}
	}
}