package com.itt.ui.custome.preference;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.cats.ui.custome.JIconCheckBox;
import com.itt.preferences.ITTProperties;

/**
 * @author xblia
 * 2015年10月22日
 */
public class JPreferenceCheckBox extends JIconCheckBox implements IPreferenceComponent, ActionListener
{
    private static final long serialVersionUID = -1105686826029115747L;
    private String preferenceKey;
    private String defaultValue;
    private PreferenceComponentChangeListener changeListener;
    public JPreferenceCheckBox(String name, String preferenceKey, String defaultValue)
    {
    	super();
    	this.preferenceKey = preferenceKey;
    	this.defaultValue = defaultValue;
		this.setText(name);
		this.setFocusable(false);
		
		String value = ITTProperties.getProp(preferenceKey);
		this.setSelected(value != null && value.equals(SELECTED_VAL));
		
		this.addActionListener(this);
    }

	@Override
    public String resetDefaultVal()
    {
		this.setSelected(this.defaultValue.equals(SELECTED_VAL));
	    return this.defaultValue;
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
	public void actionPerformed(ActionEvent e)
	{
		if(null != changeListener)
		{
			this.changeListener.onChange(preferenceKey, this.isSelected()? SELECTED_VAL:UNSELECTED_VAL);
		}
	}
}
