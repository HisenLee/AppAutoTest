package com.itt.ui.custome;

import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import com.cats.ui.custome.btn.arrow.ArrowButton;
import com.itt.ui.custome.preference.IControlableComponent;

/**
 * @author xblia
 * 2015年10月20日
 */
public class ButtonProgress extends ArrowButton implements IControlableComponent
{

    private static final long serialVersionUID = 1997009229581015734L;

    private Timer timer = null;
    private IControlableComponent componentControlable;
    public ButtonProgress(IControlableComponent componentControlable)
    {
    	super();
    	this.componentControlable = componentControlable;
    }

	@Override
    public void startComponent(ActionListener actionListener)
    {
		if(null != timer)
		{
			timer.cancel();
		}
		timer = new Timer();
		timer.schedule(new TimerTask()
		{
			int iVal = 1;
			@Override
			public void run()
			{
				if (iVal % 2 == 0)
				{
					setIconGray();
				}
				else
				{
					setIconGrayBlueGray();
				}
				iVal++;
			}
		}, 100, 500);
		this.addActionListener(actionListener);
		componentControlable.startComponent(actionListener);
    }

	@Override
    public void endComponent(ActionListener actionListener)
    {
		if(null != timer)
		{
			timer.cancel();
			timer = null;
		}
		this.setIconBlue();
		this.removeActionListener(actionListener);
		componentControlable.endComponent(actionListener);
    }

	@Override
    public void recoverOrigin()
    {
		setIconGray();
    }
    
}
