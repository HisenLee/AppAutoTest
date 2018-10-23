package com.itt.ui.custome.preference;

import java.awt.event.ActionListener;

/**
 * @author xblia
 * 2015年10月21日
 */
public interface IControlableComponent
{
	void startComponent(ActionListener actionListener);
	void endComponent(ActionListener actionListener);
	void recoverOrigin();
}
