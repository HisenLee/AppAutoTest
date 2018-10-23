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

/**
 * @author xblia2
 * Jul 22, 2015
 */
public class IMessageGetOnlineUserReq extends IMessage
{

	private static final long serialVersionUID = 3754851303932825586L;

	private String softName;

	public String getSoftName()
	{
		return softName;
	}

	public void setSoftName(String softName)
	{
		this.softName = softName;
	}

	@Override
	public String toString()
	{
		return "IMessageGetOnlineUserReq [softName=" + softName + "]";
	}

}
