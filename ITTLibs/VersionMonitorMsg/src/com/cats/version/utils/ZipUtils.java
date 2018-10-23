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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author xblia2
 * Jul 6, 2015
 */
public class ZipUtils
{
	private List<File> allFile = new ArrayList<File>();
	
	public File compress(String filePath, String zipFileName)
	{
		byte[] buffer = new byte[1024];
		String zipAbsPath = new File(filePath).getParentFile().getAbsoluteFile() + File.separator + zipFileName;
		File zipFile = new File(zipAbsPath);
		if(zipFile.exists())
		{
			return zipFile;
		}
		
		File fileDir = new File(filePath);
		getCompressFiles(fileDir);
		try( ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));)
        {
	        for (int i = 0; i < allFile.size(); i++)
	        {
	        	File file = allFile.get(i);
	        	String entryName = genEntryName(allFile.get(i), filePath);
	        	if(null == entryName)
	        	{
	        		continue;
	        	}
	        	int len;
	        	if(file.isFile())
	        	{
	        		out.putNextEntry(new ZipEntry(entryName));
	        		FileInputStream fis = new FileInputStream(allFile.get(i));
	        		while ((len = fis.read(buffer)) > 0)
	        		{
	        			out.write(buffer, 0, len);
	        		}
	        		fis.close();
	        	}else
	        	{
	        		out.putNextEntry(new ZipEntry(entryName + File.separator));
	        	}
	        	out.closeEntry();
	        }
        } catch (Exception e)
        {
	        e.printStackTrace();
	        return null;
        }
		return zipFile;
	}

	private String genEntryName(File file, String filePath)
    {
		String absPath = file.getAbsolutePath();
		if(absPath.length() < filePath.length() + 1)
		{
			return null;
		}
		String entryName = absPath.substring(filePath.length() + 1);
		return entryName;
    }

	private void getCompressFiles(File fileDir)
	{
		if (fileDir != null)
		{
			File[] files = fileDir.listFiles();
			if(files.length == 0)
			{
				allFile.add(fileDir);
			}else
			{
				for (File file : files)
				{
					if (file.isFile())
					{
						allFile.add(file);
					} else
					{
						getCompressFiles(file);
					}
				}
			}
		} else
		{
			allFile.add(fileDir);
		}
	}
}