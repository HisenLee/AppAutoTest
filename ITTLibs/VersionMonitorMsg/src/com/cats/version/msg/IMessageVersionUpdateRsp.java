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
 package com.cats.version.msg;

import java.util.List;

/**
 * @author xblia2 Jun 10, 2015
 */
public class IMessageVersionUpdateRsp extends IMessage
{

	private static final long serialVersionUID = 8533555956172024115L;

	private String result;
	private String softName;
	private String latestVersionName;
	private List<String> updateFilePath;
	private String startupName;

	public String getResult()
	{
		return result;
	}

	public void setResult(String result)
	{
		this.result = result;
	}

	public List<String> getUpdateFilePath()
	{
		return updateFilePath;
	}

	public void setUpdateFilePath(List<String> updateFilePath)
	{
		this.updateFilePath = updateFilePath;
	}

	public String getSoftName()
	{
		return softName;
	}

	public void setSoftName(String softName)
	{
		this.softName = softName;
	}

	public String getLatestVersionName()
	{
		return latestVersionName;
	}

	public void setLatestVersionName(String latestVersionName)
	{
		this.latestVersionName = latestVersionName;
	}

	public String getStartupName()
	{
		return startupName;
	}

	public void setStartupName(String startupName)
	{
		this.startupName = startupName;
	}
}
