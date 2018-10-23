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
 package com.cats.version;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xblia2 Jun 8, 2015
 */
public class VersionInfo implements Serializable
{

	private static final long serialVersionUID = 1L;

	private String appName;
	private int versionCode;
	private String version;
	private String path;
	private String startupName;
	private List<VersionInfoDetail> details = new ArrayList<>();
	private List<String> ignoreFiles = new ArrayList<>();

	public String getAppName()
	{
		return appName;
	}

	public void setAppName(String appName)
	{
		this.appName = appName;
	}

	public int getVersionCode()
	{
		return versionCode;
	}

	public void setVersionCode(int versionCode)
	{
		this.versionCode = versionCode;
	}

	public String getVersion()
	{
		return version;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}

	public String getPath()
	{
		return path;
	}

	public void setPath(String path)
	{
		this.path = path;
	}

	public List<VersionInfoDetail> getDetails()
	{
		return details;
	}

	public void setDetails(List<VersionInfoDetail> details)
	{
		this.details = details;
	}

	public String getStartupName()
	{
		return startupName;
	}

	public void setStartupName(String startupName)
	{
		this.startupName = startupName;
	}
	
	public List<String> getIgnoreFiles()
	{
		return ignoreFiles;
	}

	public void setIgnoreFiles(List<String> ignoreFiles)
	{
		this.ignoreFiles = ignoreFiles;
	}

	@Override
    public String toString()
    {
	    return this.appName;
    }
}
