package com.itt.devices.statusfilter;

import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.itt.ui.custome.preference.IControlableComponent;

/**
 * @author xblia
 * 2015年10月22日
 */
public class ProgressStatusTestFilter extends IStatusFilter
{
	private Map<Integer, IControlableComponent> statusToComponentControlable;
	
	public ProgressStatusTestFilter(
            Map<Integer, IControlableComponent> statusToComponentControlable)
    {
	    super();
	    this.statusToComponentControlable = statusToComponentControlable;
    }

	@Override
	public void doFilter(final int iStatusCode, final int wparam,
	        final Object lparam)
	{

		IControlableComponent componentControlable = statusToComponentControlable
		        .get(iStatusCode);
		if (null != componentControlable)
		{
			Set<Entry<Integer, IControlableComponent>> statusToCompoentEntries = statusToComponentControlable
			        .entrySet();
			for (Entry<Integer, IControlableComponent> statusToCompoent : statusToCompoentEntries)
			{
				int iComponentStatus = statusToCompoent.getKey();
				IControlableComponent component = statusToCompoent.getValue();
				component.recoverOrigin();
				if (iComponentStatus < iStatusCode)
				{
					component.endComponent(getActionListener(lparam));
				}
			}

			if (wparam == 0)
			{
				componentControlable.startComponent(getActionListener(lparam));
			} else
			{
				componentControlable.endComponent(getActionListener(lparam));
			}
		}

	}
	
	private ActionListener getActionListener(Object obj)
	{
		if(null != obj)
		{
			return (ActionListener)obj;
		}
		return null;
	}

}
