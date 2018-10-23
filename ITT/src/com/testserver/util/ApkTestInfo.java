package com.testserver.util;

public class ApkTestInfo
{
	private int row;
	private String app_name;
	private String result;

	public ApkTestInfo()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public ApkTestInfo(int row, String app_name, String result)
	{
		super();
		this.row = row;
		this.app_name = app_name;
		this.result = result;
	}

	public int getRow()
	{
		return row;
	}

	public void setRow(int row)
	{
		this.row = row;
	}

	public String getApp_name()
	{
		return app_name;
	}

	public void setApp_name(String app_name)
	{
		this.app_name = app_name;
	}

	public String getResult()
	{
		return result;
	}

	public void setResult(String result)
	{
		this.result = result;
	}

}
