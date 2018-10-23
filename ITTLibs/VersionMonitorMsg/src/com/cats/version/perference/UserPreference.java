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
 package com.cats.version.perference;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import com.cats.version.utils.Utils;

/**
 * @author xblia2
 * Jun 10, 2015
 */
public class UserPreference
{
	private static final String KEY_URL = "URL";
	private static final String KEY_VESIONCHECK_INTERVAL = "check_interval";
	
	private static final UserPreference PREFERENCE = new UserPreference();

	
	private File file = new File(Utils.getWorkSpace() + File.separator + "version_cfg.properties");
	
	private Properties properties = new Properties();;
	
	public UserPreference()
    {
		initCfg();
    }
	
	private void initCfg()
	{
		if(!file.exists())
		{
			properties.setProperty(KEY_URL, "http://xblia2-OptiPlex-9020:8023");
			properties.setProperty(KEY_VESIONCHECK_INTERVAL, "30000");
			try
            {
	            properties.store(new FileWriter(file), "init");
            } catch (IOException e)
            {
	            e.printStackTrace();
            }
		}else
		{
			try
            {
	            properties.load(new FileReader(file));
            } catch (FileNotFoundException e)
            {
	            e.printStackTrace();
            } catch (IOException e)
            {
	            e.printStackTrace();
            }
		}
	}
	
	public String getUrl()
	{
		return properties.getProperty(KEY_URL);
	}
	
	public long getVersionCheckInterval()
	{
		long lInterval = 30000;
		try
        {
			String strInterval = properties.getProperty(KEY_VESIONCHECK_INTERVAL);
			if(null != strInterval && !strInterval.isEmpty())
			{
				lInterval = Long.parseLong(strInterval);
			}
        } catch (NumberFormatException e)
        {
	        e.printStackTrace();
        }
		return lInterval;
		
	}
	
	public static UserPreference getInstance()
	{
		return PREFERENCE;
	}
}
