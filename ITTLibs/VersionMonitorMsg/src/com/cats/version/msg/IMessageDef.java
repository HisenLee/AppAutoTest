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

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xblia2
 * Jun 10, 2015
 */
public abstract class IMessageDef
{
	public static final int MSGIDENTIFIED_VERSIONSERVICE = 0x11585668;
	
	public static final int MSGTYPE_REQUEST_ERROR = 0x0000;
	public static final int MSGTYPE_VERSION_CHCECK_REQ = 0x0101;
	public static final int MSGTYPE_VERSION_CHCECK_RSP = 0x0102;
	
	public static final int MSGTYPE_VERSION_UPDATE_REQ = 0x0201;
	public static final int MSGTYPE_VERSION_UPDATE_RSP = 0x0202;
	
	public static final int MSGTYPE_GET_ONLINELIST_REQ = 0x0301;
	public static final int MSGTYPE_GET_ONLINELIST_RSP = 0x0302;
	
	public static final String YES 			= "YES";
	public static final String NO 			= "NO";
	public static final String SUCC 		= "SUCC";
	public static final String FAIL 		= "FAIL";
	
	
	private static AtomicInteger atomicInteger = new AtomicInteger(); 
	
	public static synchronized int genMsgId()
	{
		return atomicInteger.getAndIncrement();
	}
	
	public enum BroadcastMsgPriority
	{
		Normal,
		Important,
		VeryImportant,
	}
}
