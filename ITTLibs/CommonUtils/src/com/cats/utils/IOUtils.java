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
 package com.cats.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Enumeration;

import javax.xml.bind.DatatypeConverter;

/**
 * @author xblia
 * 2015年9月18日
 */
public class IOUtils
{
	public static void closeResource(OutputStream out, InputStream in,
	        Socket socket)
	{
		try
		{
			if (null != out)
			{
				out.close();
			}
			if (null != in)
			{
				in.close();
			}
			if (null != socket)
			{
				socket.close();
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void closeResource(OutputStream out)
	{
		if (null != out)
		{
			try
			{
				out.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public static void closeResource(InputStream in)
	{
		if (null != in)
		{
			try
			{
				in.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public static void closeResource(Writer out)
	{
		if (null != out)
		{
			try
			{
				out.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public static void closeResource(Reader in)
	{
		if (null != in)
		{
			try
			{
				in.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public static void closeResource(Socket socket)
	{
		if (null != socket)
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
		} finally
		{
			IOUtils.closeResource(fis);
			IOUtils.closeResource(fos);
		}
	}

	public static String getLocalHostIp()
	{
		String ip = "";
		try
		{
			InetAddress addr = InetAddress.getLocalHost();
			ip = addr.getHostAddress();
		} catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
		return ip;
	}
	
    /*
     * get the local host name
     */
    public static String getLocalHostName(){
        try{
            InetAddress addr=InetAddress.getLocalHost();
            return addr.getHostName();
        }catch(Exception e){
            return "";
        }
    }

	public static String getLocalHostIpEx()
	{
		String regex = "[\\d]+.[\\d]+.[\\d]+.[\\d]+";
		Enumeration<?> e = null;
		try
		{
			e = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e1)
		{
			e1.printStackTrace();
		}
		while (e.hasMoreElements())
		{
			NetworkInterface n = (NetworkInterface) e.nextElement();
			Enumeration<?> ee = n.getInetAddresses();
			while (ee.hasMoreElements())
			{
				InetAddress i = (InetAddress) ee.nextElement();
				String ip = i.getHostAddress();
				if (ip.matches(regex) && !ip.equals("127.0.0.1"))
				{
					return ip;
				}
			}
		}
		return null;
	}

	public static void mkDirsIfNotExists(String deviceBasePath)
    {
		File file = new File(deviceBasePath);
		if(!file.exists())
		{
			file.mkdirs();
		}
    }
	
	public static String getUserDir()
	{
		return System.getProperty("user.dir");
	}
	
	public static String getNotExistsPath(String pathTemplate)
	{
		File file = null;
		int index = 1;
		String tag = "";
		do{
			file = new File(String.format(pathTemplate, tag));
			if(!file.exists())
			{
				break;
			}
			tag = "_" + index;
			index++;
		}while(file.exists());
		
		return String.format(pathTemplate, tag);
	}
	
	public static String getFileMD5(String filePath)
	{
		try
        {
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        md.update(Files.readAllBytes(Paths.get(filePath)));
	        byte[] digest = md.digest();
	        return DatatypeConverter.printHexBinary(digest).toLowerCase();
        }catch (Exception e)
        {
	        e.printStackTrace();
        }
		return null;
	}
	
	public static void main(String[] args)
    {
		System.out.println(getFileMD5("D:\\External_test\\360\\com.tencent.mm.apk"));
    }
}
