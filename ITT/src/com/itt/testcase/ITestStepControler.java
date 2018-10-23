package com.itt.testcase;

import java.awt.event.ActionListener;

import com.cats.ui.optiondlg.ResultCallback;
import com.itt.devices.statusfilter.ParamAndResult;

/**
 * @author xblia
 * 2015年10月22日
 */
public interface ITestStepControler extends ActionListener
{
	int ACTION_SNAPSHOT 			= 0x01;
	int ACTION_RECORDRESULT 		= 0x02;
	
	void action(ResultCallback resultCallBack, int iActionCode, ParamAndResult param);
}
