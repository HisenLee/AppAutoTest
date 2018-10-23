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

public class IMessageError extends IMessage
{
    private static final long serialVersionUID = -639644917780770419L;
    private String originRequest;
	public String getOriginRequest()
	{
		return originRequest;
	}
	public void setOriginRequest(String originRequest)
	{
		this.originRequest = originRequest;
	}
	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}
}
