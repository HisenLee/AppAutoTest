package com.itt.preferences;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.cats.utils.IOUtils;

/**
 * @author xblia
 * 2015年10月19日
 */
public class ITTProperties
{
	private static Properties properties = new Properties();
	private static File file = new File(IOUtils.getUserDir()
	        + File.separator + "itt.properties");

	static
	{
		FileInputStream fis = null;
		try
		{
			if (!file.exists())
			{
				file.createNewFile();
			}
			fis = new FileInputStream(file);
			properties.load(fis);
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			IOUtils.closeResource(fis);
		}
	}

	public static String getProp(String key)
	{
		return properties.getProperty(key, "");
	}
	
	public static void setPropAndSave(String key, String val)
	{
		setProp(key, val);
		saveProp();
	}

	public static void setProp(String key, String val)
	{
		if (key == null || key.isEmpty() || val == null || val.isEmpty())
		{
			return;
		}
		properties.put(key, val);
	}
	
	public static void saveProp()
	{
		FileOutputStream fos = null;
		try
		{
			fos = new FileOutputStream(file);
			properties.store(fos, "--");
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			IOUtils.closeResource(fos);
		}
	}

}
