package com.itt.devices.listener;

import javax.swing.JComponent;

/**
 * @author xblia
 * 2015年10月27日
 */
public interface IDevicePanelCommonActionListener
{
	int ACTION_CDE_OPENFOLDER = 0x01;
	/**
	 * Open Test Result Folder
	 */
	void onCommonAction(int actionCode, JComponent component);
}
