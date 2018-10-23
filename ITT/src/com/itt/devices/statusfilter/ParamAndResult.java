package com.itt.devices.statusfilter;

/**
 * @author xblia 2015年10月28日
 */
public class ParamAndResult
{
	private Object param;
	private Object result;
	

	public ParamAndResult(Object param)
    {
	    super();
	    this.param = param;
    }

	public Object getParam()
	{
		return param;
	}

	public void setParam(Object param)
	{
		this.param = param;
	}

	public Object getResult()
	{
		return result;
	}

	public void setResult(Object result)
	{
		this.result = result;
	}

}
