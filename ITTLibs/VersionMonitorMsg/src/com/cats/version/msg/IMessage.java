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

import java.io.Serializable;
import java.net.InetAddress;

import com.cats.version.utils.Utils;

/**
 * @author xblia2 Jun 10, 2015
 */
public abstract class IMessage implements Serializable
{
	private static final long serialVersionUID = -6167593333169498461L;
	protected int msgid;
	protected int msgType;
	protected int msgIdentified;
	protected String msgResult;
	protected String msgCause;
	
	private static String userName;
	private static String localIP;

	public int getMsgid()
	{
		return msgid;
	}

	public void setMsgid(int msgid)
	{
		this.msgid = msgid;
	}

	public int getMsgType()
	{
		return msgType;
	}

	public void setMsgType(int msgType)
	{
		this.msgType = msgType;
	}

	public int getMsgIdentified()
	{
		return msgIdentified;
	}

	public void setMsgIdentified(int msgIdentified)
	{
		this.msgIdentified = msgIdentified;
	}
	
	public String getMsgResult()
	{
		return msgResult;
	}

	public void setMsgResult(String msgResult)
	{
		this.msgResult = msgResult;
	}

	public String getMsgCause()
	{
		return msgCause;
	}

	public void setMsgCause(String msgCause)
	{
		this.msgCause = msgCause;
	}

	public static String getUserName()
	{
		if(userName == null)
		{
			userName = System.getProperty("user.name");
		}
		return userName;
	}
	
	public static String getLocalIp()
	{
		if(localIP == null)
		{
			localIP = Utils.getLocalHostIp();
			localIP = converseToDomain(localIP);
		}
		return localIP;
	}

	private static String converseToDomain(String ip)
    {
		String ipInfo = ip;
		InetAddress adress = null;
		try
        {
	        adress = InetAddress.getByName(ip);
	        if(null != adress)
	        {
	        	String hostName = adress.getHostName();
	        	if(null != hostName && !hostName.isEmpty())
	        	{
	        		ipInfo = hostName;
	        	}
	        }
        } catch (Exception e)
        {
	        e.printStackTrace();
        }
	    return ipInfo;
    }

	@Override
    public String toString()
    {
	    return "IMessage [msgid=" + msgid + ", msgType=" + msgType
	            + ", msgIdentified=" + msgIdentified + "]";
    }

}
