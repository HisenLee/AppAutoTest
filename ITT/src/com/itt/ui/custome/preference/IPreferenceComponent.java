package com.itt.ui.custome.preference;

/**
 * @author xblia
 * 2015年10月22日
 */
public interface IPreferenceComponent
{
	String SELECTED_VAL = "1";
    String UNSELECTED_VAL = "0";
	
	String resetDefaultVal();
	void saveProperty();
	void setChangeListener(PreferenceComponentChangeListener changeListener);
	void removeChangeListener(PreferenceComponentChangeListener changeListener);
}
