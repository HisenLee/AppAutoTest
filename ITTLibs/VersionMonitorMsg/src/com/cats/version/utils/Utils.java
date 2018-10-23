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
 package com.cats.version.utils;

import java.awt.Component;
import java.awt.Toolkit;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.cats.version.perference.UserPreference;

/**
 * @author xblia2 Jun 10, 2015
 */
public class Utils
{
	static HttpClient client = HttpClients.createDefault();
	static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd: HH:mm:SS");
	
	public static String postMsgAndGet(String msg)
	{
		HttpPost request = new HttpPost(UserPreference.getInstance().getUrl());
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("msg", msg));

		try
		{
			UrlEncodedFormEntity formEntiry = new UrlEncodedFormEntity(
			        parameters, IVersionConstant.CHARSET_UTF8);
			request.setEntity(formEntiry);
			HttpResponse response = client.execute(request);
			if(response.getStatusLine().getStatusCode() == 200)
			{
				return EntityUtils.toString(response.getEntity(), IVersionConstant.CHARSET_UTF8);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	
	@SuppressWarnings("unused")
    private static String getResponseMsg(HttpResponse response)
    {
		InputStream input = null;
		try
        {
	        input = response.getEntity().getContent();
	        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
	        int iReadLen = -1;
	        do
	        {
	        	byte []data = new byte[1024];
	        	iReadLen = input.read(data);
	        	if(iReadLen != -1)
	        	{
	        		arrayOutputStream.write(data, 0, iReadLen);
	        	}
	        }while(iReadLen != -1);
	        byte []rspData = arrayOutputStream.toByteArray();
	        return new String(rspData, IVersionConstant.CHARSET_UTF8);
        } catch (UnsupportedOperationException e)
        {
	        e.printStackTrace();
        } catch (IOException e)
        {
	        e.printStackTrace();
        }
	    finally
	    {
	    	Utils.closeRes(input);
	    }
	    return null;
    }


	public static String getWorkSpace()
	{
		return System.getProperty("user.dir");
	}
	
	public static String getLocalHostIp()
    {
        String regex = "[\\d]+.[\\d]+.[\\d]+.[\\d]+";
        Enumeration<?> e =
        null;
        try
        {
            e = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e1)
        {
            e1.printStackTrace();
        }
        while(e.hasMoreElements())
        {
            NetworkInterface n = (NetworkInterface) e.nextElement();
            Enumeration<?> ee = n.getInetAddresses();
            while (ee.hasMoreElements())
            {
                InetAddress i = (InetAddress) ee.nextElement();
                String ip = i.getHostAddress();
                if(ip.matches(regex) && !ip.equals("127.0.0.1"))
                {
                    return ip;
                }
            }
        }
        return null;
    }
	
	public static String getCurrenTime()
	{
		return dateFormat.format(new Date());
	}


	public static void copyFile(File srcFile, File destFile)
    {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try
        {
	        fis = new FileInputStream(srcFile);
	        fos = new FileOutputStream(destFile);
	        FileChannel inChannel = fis.getChannel();
	        FileChannel outChannel = fos.getChannel();
	        inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (FileNotFoundException e)
        {
	        e.printStackTrace();
        } catch (IOException e)
        {
	        e.printStackTrace();
        }finally
        {
        	Utils.closeRes(fis);
        	Utils.closeRes(fos);
        }
    }

	public static void closeRes(InputStream fis)
    {
	    if(null != fis)
	    {
	    	try
            {
	            fis.close();
            } catch (IOException e)
            {
	            e.printStackTrace();
            }
	    }
    }


	public static void closeRes(OutputStream fos)
    {
		if(null != fos)
		{
			try
            {
	            fos.close();
            } catch (IOException e)
            {
	            e.printStackTrace();
            }
		}
    }

	public static void centerWindow(int width, int height, Component ui)
	{
		ui.setLocation((int) (Toolkit.getDefaultToolkit().getScreenSize()
		        .getWidth() - width) / 2, (int) (Toolkit.getDefaultToolkit()
		        .getScreenSize().getHeight() - height) / 2);
	}


	public static String getException(Exception e)
    {
		StringWriter sw = new StringWriter();
		PrintWriter printWriter = new PrintWriter(sw);
		e.printStackTrace(printWriter);
		return sw.toString();
    }
	
	public static String getResultFromStream(InputStream input)
	{
		if (null != input)
		{
			int iLen = -1;
			byte[] data = new byte[512];
			ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
			try
			{
				do
				{
					iLen = input.read(data);
					if (iLen != -1)
					{
						arrayOutputStream.write(data, 0, iLen);
					}
				} while (iLen != -1);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			data = arrayOutputStream.toByteArray();
			return new String(data, Charset.defaultCharset());
		}
		return "Process starup failed. inputstream empty";
	}
}
