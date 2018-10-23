package com.intel.cats.test.log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
//////
/**
 * @author xiaobolx
 * Sep 29, 2014
 * Util
 */
public class Util
{

	public static String genApkFilePath(String strPackName)
    {
	    return strPackName + "_" + getCurrentDate() + ".apk";
    }
	
	public static String getCurrentDate()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmSS");
		return sdf.format(System.currentTimeMillis());
	}
	
	public static String getCurrentDay()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(System.currentTimeMillis());
	}
	
	public static String getCurrentDayStub()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(System.currentTimeMillis() - (24*3600*1000));
	}
	
	public static String getLastDay()
	{
		Calendar calendar = Calendar.getInstance();
		int iDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		if(iDayOfWeek == 2)
		{
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 3);
		}else
		{
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
			//calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 3);
			
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(calendar.getTime());
	}
	
	public static String getTime()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss");
		return sdf.format(System.currentTimeMillis());
	}

	public static void closeRes(OutputStream out)
    {
		if(null != out)
		{
			try
            {
	            out.close();
            } catch (Exception e)
            {
	            e.printStackTrace();
            }
		}
    }

	public static void closeRes(InputStream input)
    {
		if(null != input)
		{
			try
            {
				input.close();
            } catch (Exception e)
            {
	            e.printStackTrace();
            }
		}
    }
	
	public static void closeRes(Reader reader)
    {
		if(null != reader)
		{
			try
            {
	            reader.close();
            } catch (IOException e)
            {
	            e.printStackTrace();
            }
		}
    }
	
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
	
	public static void block(String category, long time)
	{
		ILog.getLog().info(category, "sleep " + (time*1.0/1000) + "s");
		block(time);
	}
	
	public static void fileChannelCopy(File s, File t)
	{
		FileInputStream fi = null;
		FileOutputStream fo = null;
		FileChannel in = null;
		FileChannel out = null;
		try
		{
			fi = new FileInputStream(s);
			fo = new FileOutputStream(t);
			in = fi.getChannel();
			out = fo.getChannel();
			in.transferTo(0, in.size(), out);
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				fi.close();
				in.close();
				fo.close();
				out.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
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
	
	public static String fixField(String stringCellValue)
    {
		stringCellValue = stringCellValue.replace(".", "");
		stringCellValue = stringCellValue.replace(":", "");
		stringCellValue = stringCellValue.replace("&", "");
		stringCellValue = stringCellValue.replace("[", "");
		stringCellValue = stringCellValue.replace("]", "");
		stringCellValue = stringCellValue.replace("(", "");
		stringCellValue = stringCellValue.replace(")", "");
		stringCellValue = stringCellValue.replace("!", "");
		stringCellValue = stringCellValue.replace("/", "");
		stringCellValue = stringCellValue.replace("?", "");
		stringCellValue = stringCellValue.replace("~", "");
		stringCellValue = stringCellValue.replace("@", "");
		stringCellValue = stringCellValue.replace("#", "");
		stringCellValue = stringCellValue.replace("$", "");
		stringCellValue = stringCellValue.replace("%", "");
		stringCellValue = stringCellValue.replace("^", "");
		stringCellValue = stringCellValue.replace("*", "");
	    return stringCellValue;
    }
	
	public static String getLocalHostIP(){
        try{
            InetAddress addr=InetAddress.getLocalHost();
            return addr.getHostAddress();
        }catch(Exception e){
            return "";
        }
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
        	closeRes(fis);
        	closeRes(fos);
        }
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
}
