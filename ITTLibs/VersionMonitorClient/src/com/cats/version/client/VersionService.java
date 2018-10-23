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
 package com.cats.version.client;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.cats.version.msg.IMessageDef;
import com.cats.version.msg.IMessageGetOnlineUserReq;
import com.cats.version.msg.IMessageGetOnlineUserRsp;
import com.cats.version.utils.Utils;

/**
 * @author xblia2
 * Jul 22, 2015
 */
public class VersionService
{
	private static VersionService service = new VersionService();
	
	private IVersionInfoProvider versioninfoProvider;
	
	public static VersionService getInstance()
	{
		return service;
	}
	
	public void initProviderInfo(IVersionInfoProvider versioninfoProvider)
	{
		this.versioninfoProvider = versioninfoProvider;
	}
	
	public Map<String, String> getOnlineUser()
	{
		if(this.versioninfoProvider == null)
		{
			System.out.println("versionInfo provider not have initialization");
			return null;
		}
		String strReq = this.genGetOnlineUserReqMsg();
		IMessageGetOnlineUserRsp getUserRsp = this.postMessageAndGet(strReq);
		if(null != getUserRsp)
		{
			return getUserRsp.getOnlineIPToUser();
		}
		return null;
	}
	
	private String genGetOnlineUserReqMsg()
    {
		IMessageGetOnlineUserReq getUserReq = new IMessageGetOnlineUserReq();
		getUserReq.setMsgIdentified(IMessageDef.MSGIDENTIFIED_VERSIONSERVICE);
		getUserReq.setMsgid(IMessageDef.genMsgId());
		getUserReq.setMsgType(IMessageDef.MSGTYPE_GET_ONLINELIST_REQ);
		getUserReq.setSoftName(versioninfoProvider.getSoftName());
		String strJsonMsg = JSON.toJSONString(getUserReq);
		return strJsonMsg;
    }
	
	public IMessageGetOnlineUserRsp postMessageAndGet(String strReq)
    {
		String strRsp = Utils.postMsgAndGet(strReq);
		if(strRsp!= null && !strRsp.isEmpty())
		{
			IMessageGetOnlineUserRsp checkRsp = JSON.parseObject(strRsp, IMessageGetOnlineUserRsp.class);
			if(null == checkRsp || checkRsp.getMsgResult().equals(IMessageDef.FAIL))
			{
				System.out.println("Rsp error: " + strRsp);
				return null;
			}
			return checkRsp;
		}
        return null;
    }
}
