package com.itt.ui.custome.preference;

import java.awt.event.ItemEvent;

import com.cats.ui.custome.JRadioButtonEx;
import com.itt.preferences.ITTProperties;

/**
 * @author xiaobolx
 * 2015年11月26日
 */
public class JPreferenceRadioButton extends JRadioButtonEx implements
        IPreferenceComponent
{
    private static final long serialVersionUID = 1L;

    private PreferenceComponentChangeListener changeListener;
    private String preferenceKey;
    private String defaultVal;
    
	public JPreferenceRadioButton(String preferenceKey, String defaultVal, String name)
    {
	    super(name);
	    this.preferenceKey = preferenceKey;
	    this.defaultVal = defaultVal;
	    
	    String value = ITTProperties.getProp(preferenceKey);
	    if(null == value || value.isEmpty())
	    {
	    	this.setSelected(defaultVal.equals(SELECTED_VAL));
	    }else
	    {
	    	this.setSelected(value != null && value.equals(SELECTED_VAL));
	    }
	    saveProperty();
    }

	@Override
	public String resetDefaultVal()
	{
		this.setSelected(this.defaultVal.equals(SELECTED_VAL));
	    return this.defaultVal;
	}

	@Override
	public void saveProperty()
	{
		String value = this.isSelected() ? SELECTED_VAL:UNSELECTED_VAL;
		ITTProperties.setProp(this.preferenceKey, value);
	}

	@Override
    public void setChangeListener(
            PreferenceComponentChangeListener changeListener)
    {
		this.changeListener = changeListener;
    }

	@Override
    public void removeChangeListener(
            PreferenceComponentChangeListener changeListener)
    {
		this.changeListener = null;
    }
	
	@Override
	protected void itemStateChangeEx(ItemEvent e)
	{
		if(null != changeListener)
		{
			this.changeListener.onChange(preferenceKey, this.isSelected()? SELECTED_VAL:UNSELECTED_VAL);
		}
	}
}
