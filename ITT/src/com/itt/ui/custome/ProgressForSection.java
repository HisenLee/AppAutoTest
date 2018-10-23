package com.itt.ui.custome;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.SwingUtilities;

import com.cats.ui.custome.arrowprogressbar.ArrowProgressBar;
import com.itt.ui.custome.preference.IControlableComponent;

/**
 * @author xblia
 * 2015年10月22日
 */
public class ProgressForSection extends ArrowProgressBar implements
        IControlableComponent
{
    private static final long serialVersionUID = 6708659499698988305L;

    private Timer timer = new Timer();
    public ProgressForSection(String name, boolean isIndeterminate, boolean frontArrow, boolean behindArrow)
    {
    	super(name, isIndeterminate, frontArrow, behindArrow);
    	initSecionProgress();
    }
    
    public ProgressForSection(String name, boolean isIndeterminate)
    {
    	super(name, isIndeterminate, false, true);
    	initSecionProgress();
    }
    
    private void initSecionProgress()
    {
    	this.setMaximum(MAX_VALUE);
    	this.setMinimum(0);
    	this.setPreferredSize(new Dimension(40, 25));
    }
    
	@Override
	public void startComponent(ActionListener actionListener)
	{
		if(startIndeterminateProgress())
		{
			return;
		}
		if(timer != null)
		{
			timer.cancel();
		}
		timer = new Timer();
		timer.schedule(new TimerTask()
		{
			int iValue = 1;
			@Override
			public void run()
			{
				SwingUtilities.invokeLater(new Runnable()
				{
					@Override
					public void run()
					{
						setValue(iValue);
						if(iValue >= 95) iValue = 95;
						else iValue++;
					}
				});
			}
		}, 100, 400);
	}

	@Override
	public void endComponent(ActionListener actionListener)
	{
		if(stopIndeterminateProgress())
		{
			return;
		}
		if(timer != null)
		{
			timer.cancel();
			timer = null;
		}
		
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				ProgressForSection.this.setValue(MAX_VALUE);
			}
		});
	}

	@Override
    public void recoverOrigin()
    {
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				ProgressForSection.this.setValue(0);
			}
		});
    }

}
