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
 package test;

import java.util.Map;

import com.cats.version.client.ClientVersionMonitor;
import com.cats.version.client.IVersionInfoProvider;
import com.cats.version.client.VersionService;

/**
 * @author xblia2
 * Jun 10, 2015
 */
public class ClientTest implements IVersionInfoProvider
{

	@Override
    public String getSoftName()
    {
	    return "easy_transfer";
    }

	@Override
    public int getVersionCode()
    {
	    return 49;
    }

	@Override
    public String getVersionName()
    {
	    return "easy_transfer4.9";
    }
	
	public void start()
	{
		ClientVersionMonitor clientVersionMonitor = new ClientVersionMonitor(this);
		clientVersionMonitor.startComponent();
		VersionService.getInstance().initProviderInfo(this);
		Map<String, String> user = VersionService.getInstance().getOnlineUser();
		System.out.println("user: " + user);
	}
	
	public static void main(String[] args)
    {
	   new ClientTest().start();
    }

}
