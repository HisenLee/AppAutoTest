package com.itt.devices.statusfilter;

/**
 * @author xblia
 * 2015年10月22日
 */
public abstract class IStatusFilter
{
	protected IStatusFilter nextFilter;
	public abstract void doFilter(int iStatusCode, int wparam, Object lparam);
	public IStatusFilter getNextFilter()
	{
		return nextFilter;
	}
	public void setNextFilter(IStatusFilter nextFilter)
	{
		this.nextFilter = nextFilter;
	}
	
	protected void doNextFilter(int iStatusCode, int wparam, Object lparam)
    {
		if(null != nextFilter)
		{
			nextFilter.doFilter(iStatusCode, wparam, lparam);
		}
    }
}
