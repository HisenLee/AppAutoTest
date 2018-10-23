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

import com.cats.version.VersionInfoDetail;

/**
 * @author xblia2 Jun 10, 2015
 */
public class IMessageVersionCheckRsp extends IMessage
{
	private static final long serialVersionUID = 3646409830480271901L;

	private String softName;
	private int currVersionCode;
	private String currVersionName;
	private int latestVersionCode;
	private String latestVersionName;
	private String needUpdate;
	private List<VersionInfoDetail> versionInfoDetail;
	private BroadcastMsg broadcastMsg;

	public String getSoftName()
	{
		return softName;
	}

	public void setSoftName(String softName)
	{
		this.softName = softName;
	}

	public int getCurrVersionCode()
	{
		return currVersionCode;
	}

	public void setCurrVersionCode(int currVersionCode)
	{
		this.currVersionCode = currVersionCode;
	}

	public String getCurrVersionName()
	{
		return currVersionName;
	}

	public void setCurrVersionName(String currVersionName)
	{
		this.currVersionName = currVersionName;
	}

	public int getLatestVersionCode()
	{
		return latestVersionCode;
	}

	public void setLatestVersionCode(int latestVersionCode)
	{
		this.latestVersionCode = latestVersionCode;
	}

	public String getLatestVersionName()
	{
		return latestVersionName;
	}

	public void setLatestVersionName(String latestVersionName)
	{
		this.latestVersionName = latestVersionName;
	}

	public String getNeedUpdate()
	{
		return needUpdate;
	}

	public void setNeedUpdate(String needUpdate)
	{
		this.needUpdate = needUpdate;
	}

	public List<VersionInfoDetail> getVersionInfoDetail()
	{
		return versionInfoDetail;
	}

	public void setVersionInfoDetail(List<VersionInfoDetail> versionInfoDetail)
	{
		this.versionInfoDetail = versionInfoDetail;
	}
	
	public BroadcastMsg getBroadcastMsg()
	{
		return broadcastMsg;
	}

	public void setBroadcastMsg(BroadcastMsg broadcastMsg)
	{
		this.broadcastMsg = broadcastMsg;
	}

	@Override
	public String toString()
	{
		return "IMessageVersionCheckRsp [softName=" + softName
		        + ", currVersionCode=" + currVersionCode + ", currVersionName="
		        + currVersionName + ", latestVersionCode=" + latestVersionCode
		        + ", latestVersionName=" + latestVersionName + ", needUpdate="
		        + needUpdate + ", versionInfoDetail=" + versionInfoDetail + "]";
	}
}
