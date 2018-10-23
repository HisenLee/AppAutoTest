package com.itt.apks.ui;

import java.io.File;
import java.io.Serializable;

/**
 * @author xblia 2015年10月19日
 */
public class FileForSelection implements Serializable
{
	private static final long serialVersionUID = -8535135397172388610L;

	private File file = null;
	private String fileName = "";
	private String fileSize = "";
	private String fileDate = "";

	public FileForSelection()
	{
	}

	public FileForSelection(File file, String fileName, String fileSize, String fileDate)
	{
		super();
		this.file = file;
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.fileDate = fileDate;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public String getFileSize()
	{
		return fileSize;
	}

	public void setFileSize(String fileSize)
	{
		this.fileSize = fileSize;
	}

	public String getFileDate()
	{
		return fileDate;
	}

	public void setFileDate(String fileDate)
	{
		this.fileDate = fileDate;
	}

	public File getFile()
	{
		return file;
	}

	public void setFile(File file)
	{
		this.file = file;
	}

	@Override
    public String toString()
    {
	    return "FileForSelection [file=" + file + ", fileName=" + fileName
	            + ", fileSize=" + fileSize + ", fileDate=" + fileDate + "]";
    }
}