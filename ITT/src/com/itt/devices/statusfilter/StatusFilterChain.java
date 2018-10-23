package com.itt.devices.statusfilter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xblia
 * 2015年10月22日
 */
public class StatusFilterChain
{
	private List<IStatusFilter> filterChain = new ArrayList<IStatusFilter>();
	
	public StatusFilterChain()
    {
    }
	
	public void addFilter(IStatusFilter filter)
	{
		if(!filterChain.isEmpty())
		{
			IStatusFilter previousFilter = filterChain.get(filterChain.size() - 1);
			previousFilter.setNextFilter(filter);
		}
		this.filterChain.add(filter);
	}
	
	public void removeFilter(IStatusFilter filter)
	{
		for (int i = 0; i < filterChain.size(); i++)
        {
	        if(filterChain.get(i).equals(filter))
	        {
	        	IStatusFilter previousFilter = null;
	        	IStatusFilter nextFilter = null;
	        	if(i > 0)
	        	{
	        		previousFilter = filterChain.get(i-1);
	        	}
	        	
	        	if(i < filterChain.size()-1)
	        	{
	        		nextFilter = filterChain.get(i+1);
	        	}
	        	
	        	if(previousFilter != null)
	        	{
	        		previousFilter.setNextFilter(nextFilter);
	        	}
	        	filterChain.remove(i);
	        	break;
	        }
        }
	}
	
	public void doFilter(int iStatusCode, int wparam, Object lparam)
	{
		if(filterChain.isEmpty())
		{
			return;
		}
		
		IStatusFilter headerFilter = filterChain.get(0);
		
		headerFilter.doFilter(iStatusCode, wparam, lparam);
	}
}
