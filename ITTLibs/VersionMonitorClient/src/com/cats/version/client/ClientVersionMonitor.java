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

import java.io.File;
import java.io.FilenameFilter;
import java.util.Timer;
import java.util.TimerTask;

import com.alibaba.fastjson.JSON;
import com.cats.ui.alertdialog.AlertDialog;
import com.cats.version.client.ui.IMBroadcastMsgTast;
import com.cats.version.client.ui.UpgradSoftUI;
import com.cats.version.msg.IMessageDef;
import com.cats.version.msg.IMessageVersionCheckReq;
import com.cats.version.msg.IMessageVersionCheckRsp;
import com.cats.version.perference.UserPreference;
import com.cats.version.utils.IVersionConstant;
import com.cats.version.utils.Utils;

/**
 * @author xblia2
 * Jun 9, 2015
 */
public class ClientVersionMonitor implements Runnable
{
	private IVersionInfoProvider versioninfoProvider;
	private boolean isRunning = false;
	private long latestCheckTimeMill = 0;
	private long versionUpdateAlertInterval = 1000 * 60 * 60;
	private UpgradSoftUI upgradSoftUI;
	private Thread monitorThread;
	
	public ClientVersionMonitor(IVersionInfoProvider versioninfoProvider)
    {
	    super();
	    this.versioninfoProvider = versioninfoProvider;
    }

	public void startComponent()
	{
		isRunning = true;
		monitorThread = new Thread(this);
		monitorThread.start();
	}
	
	public void stopComponent()
	{
		isRunning = false;
	}
	
	@Override
	public void run()
	{
		if(!beforeCheck())
		{
			return;
		}
		clearUpgradeTempFile();
		final Timer timer = new Timer();
		timer.schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				if(!isRunning)
				{
					timer.cancel();
				}
				String checkReqMsg = genVersionCheckReqMsg();
				if(!checkReqMsg.isEmpty())
				{
					getLatestVersionAndUpgrade(checkReqMsg);
				}
			}
		}, 1000, UserPreference.getInstance().getVersionCheckInterval());//Message post interval ten second.
	}
	
	private boolean beforeCheck()
    {
		String deamonAbsPath = Utils.getWorkSpace() + File.separator + UpgradSoftUI.UPGRADE_DEAMON;
		if(!new File(deamonAbsPath).exists())
		{
			String title = "Upgrade service";
			String message = "unfortunately, File [" + deamonAbsPath + "] not found, upgrade service stopped...";
			AlertDialog.show(title, message, -1, AlertDialog.ALERT_TYPE_WARNING);
			return false;
		}
	    return true;
    }

	private void clearUpgradeTempFile()
    {
		File currDir = new File(Utils.getWorkSpace());
		File[] tempFiles = currDir.listFiles(new FilenameFilter()
		{
			@Override
			public boolean accept(File arg0, String name)
			{
				if(name.startsWith(IVersionConstant.TEMPFILE_PREFIX))
				{
					return true;
				}
				return false;
			}
		});
		if(null != tempFiles)
		{
			for (File file : tempFiles)
            {
	            file.delete();
            }
		}
    }

	private void getLatestVersionAndUpgrade(String checkReqMsg)
    {
        IMessageVersionCheckRsp checkRsp = postMessageAndGet(checkReqMsg);
        if(checkRsp != null)
        {
        	if(checkRsp.getNeedUpdate().equals(IMessageDef.YES))
        	{
        		if(System.currentTimeMillis() - latestCheckTimeMill > versionUpdateAlertInterval)
        		{
        			latestCheckTimeMill = System.currentTimeMillis();
        			if(upgradSoftUI == null || !upgradSoftUI.isVisible())
        			{
        				upgradSoftUI = new UpgradSoftUI(checkRsp);
        			}
        		}
        	}
        	
        	if(checkRsp.getBroadcastMsg() != null)
        	{
        		new IMBroadcastMsgTast(checkRsp.getBroadcastMsg());
        	}
        }
    }

	public static IMessageVersionCheckRsp postMessageAndGet(String strReq)
    {
		String strRsp = Utils.postMsgAndGet(strReq);
		if(strRsp!= null && !strRsp.isEmpty())
		{
			IMessageVersionCheckRsp checkRsp = JSON.parseObject(strRsp, IMessageVersionCheckRsp.class);
			if(null == checkRsp || checkRsp.getMsgResult().equals(IMessageDef.FAIL))
			{
				System.out.println("Rsp error: " + strRsp);
				return null;
			}
			return checkRsp;
		}
        return null;
    }
	
	private String genVersionCheckReqMsg()
    {
		IMessageVersionCheckReq checkReq = new IMessageVersionCheckReq();
		
		checkReq.setMsgIdentified(IMessageDef.MSGIDENTIFIED_VERSIONSERVICE);
		checkReq.setMsgid(IMessageDef.genMsgId());
		checkReq.setMsgType(IMessageDef.MSGTYPE_VERSION_CHCECK_REQ);
		checkReq.setSoftName(versioninfoProvider.getSoftName());
		checkReq.setCurrVersionName(versioninfoProvider.getVersionName());
		checkReq.setCurrVersionCode(versioninfoProvider.getVersionCode());
		checkReq.fillHostInfo();
		
		String strJsonMsg = JSON.toJSONString(checkReq);
		return strJsonMsg;
    }
}