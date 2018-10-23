package com.itt.ui.custome.preference;

import java.awt.Dimension;

import javax.swing.event.DocumentEvent;
import javax.swing.text.Document;

import com.cats.ui.custome.JTextFieldEx;
import com.itt.preferences.ITTProperties;

/**
 * @author xblia
 * 2015年10月21日
 */
public class JPreferenceTextField extends JTextFieldEx implements IPreferenceComponent
{
    private static final long serialVersionUID = 2492921459023559680L;
    private String preferenceKey;
    private String defaultVal;
    private PreferenceComponentChangeListener changeListener;
    
    public JPreferenceTextField()
    {
    	initView();
    }
    
    public JPreferenceTextField(int length, String preferenceKey, String defaultVal)
    {
    	super(length);
    	this.preferenceKey = preferenceKey;
    	this.defaultVal = defaultVal;
    	initView();
    	initDocumentListener();
    }
    
	private void initDocumentListener()
	{
		Document dt = this.getDocument();
		dt.addDocumentListener(new javax.swing.event.DocumentListener()
		{
			public void insertUpdate(DocumentEvent e)
			{
				notifyChange();
			}

			public void removeUpdate(DocumentEvent e)
			{
				notifyChange();
			}

			public void changedUpdate(DocumentEvent e)
			{
				notifyChange();
			}
		});
	}

	public void initView()
    {
    	this.setPreferredSize(new Dimension(-1, 28));
    	this.setText(ITTProperties.getProp(preferenceKey));
    }

	@Override
    public String resetDefaultVal()
    {
		this.setText(this.defaultVal);
	    return defaultVal;
    }

	@Override
    public void saveProperty()
    {
		String value = this.getText().trim();
		if(!value.isEmpty())
		{
			ITTProperties.setProp(this.preferenceKey, value);
		}
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
	
	private void notifyChange()
	{
		if(null != changeListener)
		{
			changeListener.onChange(preferenceKey, this.getText().trim());
		}
	}

}
