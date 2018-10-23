package com.itt.ui;

import com.cats.version.client.IVersionInfoProvider;
import com.itt.ITTConstant;

/**
 * @author xiaobolx
 * 2015年11月13日
 */
public class ITTVersionInfoProvider implements IVersionInfoProvider
{

	@Override
	public String getSoftName()
	{
		return "ITT";
	}

	@Override
	public int getVersionCode()
	{
		return ITTConstant.VERSION_CODE;
	}

	@Override
	public String getVersionName()
	{
		return ITTConstant.VERSION;
	}

}
