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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

import com.cats.version.utils.IVersionConstant;
import com.cats.version.utils.Utils;

/**
 * @author xblia 2015-08-20
 */
public class ListenerSubProcessor extends Thread
{
	private IStartupSubProcCallBack callBack;
	private ServerSocket serverSocket = null;

	public ListenerSubProcessor(IStartupSubProcCallBack callBack)
	{
		this.callBack = callBack;
	}

	@Override
	public void run()
	{
		Socket socket = null;
		InputStream input = null;
		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		try
		{
			socket = serverSocket.accept();
			input = socket.getInputStream();
			int oneByte = -1;
			do
			{
				oneByte = input.read();
				if (oneByte != -1)
				{
					if (oneByte != IVersionConstant.SOCKET_MSG_LINECHAR)
					{
						arrayOutputStream.write(oneByte);
					} else
					{
						break;
					}
				}
			} while (oneByte != -1);
			byte[] data = arrayOutputStream.toByteArray();
			if (data != null && data.length > 0)
			{
				String result = new String(data, Charset.defaultCharset());
				if (result.equals(IVersionConstant.SOCKET_MSG_STARTUPOK))
				{
					callBack.onSubProcResult(null);
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally
		{
			Utils.closeRes(input);
			if(null != socket)
			{
				try
                {
	                socket.close();
                } catch (IOException e)
                {
	                e.printStackTrace();
                }
			}
		}
	}

	public int startListener()
	{
		try
		{
			serverSocket = new ServerSocket(0);
			this.start();
			return serverSocket.getLocalPort();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return -1;
	}
}
