package com.itt;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import com.cats.utils.IOUtils;
import com.cats.utils.Utils;

/**
 * @author xiaobolx
 * 2015年11月20日
 */
public class ContentGetterFromFile
{
	private static final String DEFAULT_MSG = "Welcom use Interactive Test Tool";
	public enum ContentType
	{
		enLicense("/help/License.bin"),
		
		enTestModeHelp("/help/TestModeHelp.bin"),
		enAutoTestHelp("/help/AutoTestHelp.bin");
		
		String filePath;
		private ContentType(String filePath)
        {
			this.filePath = filePath;
        }
		
		public String getPath()
		{
			return this.filePath;
		}
	}
	
    public static String getHelpInfo(ContentType contentType)
    {
    	
    	URL url = ContentGetterFromFile.class.getResource(contentType.getPath());
    	if(null == url)
    	{
    		return DEFAULT_MSG;
    	}
    	
    	return readInfoReservedLineSeparator(url);
    }

    public static String getLicenseInfo()
    {
    	String defaultMsg = "Welcom use Interactive Test Tool.%s";
    	URL url = ContentGetterFromFile.class.getResource(ContentType.enLicense.getPath());
    	if(null == url)
    	{
    		return defaultMsg;
    	}
    	
    	String conentInfo = readInfo(url);
		conentInfo = String.format(conentInfo, ITTConstant.VERSION);
	    return conentInfo;
    }

	private static String readInfo(URL url)
    {
    	InputStream inputStream = null;
		InputStreamReader isr = null;
		BufferedReader bfr = null;
		StringBuilder sb = new StringBuilder();
		try
		{
			inputStream = url.openStream();
			if(null == inputStream)
			{
				return DEFAULT_MSG;
			}
			isr = new InputStreamReader(inputStream);
	        bfr = new BufferedReader(isr);
	        String line = null;
	        do{
	        	line = bfr.readLine();
	        	if(null != line)
	        	{
	        		sb.append(line);
	        	}
	        }while(null != line);
        } catch (Exception e)
        {
	        e.printStackTrace();
        }finally
        {
        	IOUtils.closeResource(inputStream);
        	IOUtils.closeResource(isr);
        	IOUtils.closeResource(bfr);
        }
		return sb.toString();
    }
	
	private static String readInfoReservedLineSeparator(URL url)
    {
    	InputStream inputStream = null;
		InputStreamReader isr = null;
		BufferedReader bfr = null;
		StringBuilder sb = new StringBuilder();
		try
		{
			inputStream = url.openStream();
			if(null == inputStream)
			{
				return DEFAULT_MSG;
			}
			isr = new InputStreamReader(inputStream);
	        bfr = new BufferedReader(isr);
	        String line = null;
	        do{
	        	line = bfr.readLine();
	        	if(null != line)
	        	{
	        		sb.append(line);
	        		sb.append(Utils.LINE);
	        	}
	        }while(null != line);
        } catch (Exception e)
        {
	        e.printStackTrace();
        }finally
        {
        	IOUtils.closeResource(inputStream);
        	IOUtils.closeResource(isr);
        	IOUtils.closeResource(bfr);
        }
		return sb.toString();
    }
}