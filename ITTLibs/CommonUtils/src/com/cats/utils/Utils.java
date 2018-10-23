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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.management.ManagementFactory;
import java.math.BigDecimal;

/**
 * @author xblia
 * 2015年9月18日
 */
public class Utils
{
	public static final String LINE = System.getProperty("line.separator");
	
	public static boolean isNumeric(String str) 
	{
		for (int i = 0; i < str.length(); i++) 
		{
			if (!Character.isDigit(str.charAt(i))) 
			{
				return false;
			}
		}
		return true;
	}
	
	
	public static String getFormatSize(long size) {  
        double kiloByte = size/1024;  
        if(kiloByte < 1) {  
            return size + "Byte(s)";  
        }  
          
        double megaByte = kiloByte/1024;  
        if(megaByte < 1) {  
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));  
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";  
        }  
          
        double gigaByte = megaByte/1024;  
        if(gigaByte < 1) {  
            BigDecimal result2  = new BigDecimal(Double.toString(megaByte));  
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";  
        }  
          
        double teraBytes = gigaByte/1024;  
        if(teraBytes < 1) {  
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));  
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";  
        }  
        BigDecimal result4 = new BigDecimal(teraBytes);  
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";  
    }
	
	public static String getFormatSizeByKB(long size) {  
        double kiloByte = size/1024;  
        if(kiloByte < 1) {  
            return size + "KB";  
        }  
          
        double megaByte = kiloByte/1024;  
        if(megaByte < 1) {  
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));  
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";  
        }  
          
        double gigaByte = megaByte/1024;  
        if(gigaByte < 1) {  
            BigDecimal result2  = new BigDecimal(Double.toString(megaByte));  
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";  
        }  
          
        BigDecimal result4 = new BigDecimal(gigaByte);  
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";  
    }
	
	public static String getFormatHZWithUnit(long size)
	{
		 double kiloByte = size/1024;  
	        if(kiloByte < 1) {  
	            return size + "KHZ";  
	        }  
	          
	        double megaByte = kiloByte/1024;  
	        if(megaByte < 1) {  
	            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));  
	            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MHZ";  
	        }  
	          
	        double gigaByte = megaByte/1024;  
	        if(gigaByte < 1) {  
	            BigDecimal result2  = new BigDecimal(Double.toString(megaByte));  
	            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GHZ";  
	        }
	        
	        BigDecimal result4 = new BigDecimal(gigaByte);  
	        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "THZ";  
	        
	}
	
	public static void block(long time)
	{
		try
        {
	        Thread.sleep(time);
        } catch (InterruptedException e)
        {
	        e.printStackTrace();
        }
	}
	
	
	public static String getPid()
	{
		String pid = System.currentTimeMillis() + "";
        try
        {
	        String name = ManagementFactory.getRuntimeMXBean().getName();  
	        System.out.println(name);  
	        pid = name.split("@")[0];  
	        System.out.println("PID" + pid);
        } catch (Exception e)
        {
	        e.printStackTrace();
        }  
		return pid;
	}
	
    public static boolean isEmpty(Object ... obj)
    {
    	boolean isEmpty = false;
    	for (int i = 0; i < obj.length; i++)
        {
    		isEmpty |= (obj[i] == null || obj[i].toString().trim().isEmpty());
        }
    	return isEmpty;
    }
    
    public static String getExceptionInfo(Exception e)
    {
    	StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	e.printStackTrace(pw);
    	pw.close();
    	return sw.toString();
    }
}
