package com.itt.devices.statusfilter;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.cats.ui.optiondlg.ResultCallback;
import com.itt.testcase.ITestStepControler;
import com.itt.testcase.teststatus.ITestStatus;
import com.itt.ui.custome.AngleButtonOfDevice;

/**
 * @author xblia 2015年10月22日
 */
public class IManualStatusTestFilter extends IStatusFilter implements
        ActionListener, ResultCallback
{
	private JButton btnSnapShort;
	private AngleButtonOfDevice btnRecordManualResult;
	private ITestStepControler controler;

	public IManualStatusTestFilter(JButton btnSnapShort,
			AngleButtonOfDevice btnRecordManualResult)
	{
		this.btnSnapShort = btnSnapShort;
		this.btnRecordManualResult = btnRecordManualResult;
	}

	@Override
	public void doFilter(int iStatusCode, int wparam, Object lparam)
	{
		if (iStatusCode == ITestStatus.STATUS_WAITTING_USER)
		{
			btnSnapShort.setEnabled(wparam == 0);
			btnRecordManualResult.setEnabled(wparam == 0);
			controler = (ITestStepControler) lparam;

			if (wparam == 0)
			{
				btnSnapShort.addActionListener(this);
				btnRecordManualResult.addActionListener(this);
			} else
			{
				btnSnapShort.removeActionListener(this);
				btnRecordManualResult.removeActionListener(this);
				btnRecordManualResult.changeIcons("/icon/btn/", "");
			}
		}

		doNextFilter(iStatusCode, wparam, lparam);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == btnSnapShort)
		{
			if (null != controler)
			{
				controler.action(null, ITestStepControler.ACTION_SNAPSHOT, null);
			}
		} else if (e.getSource() == btnRecordManualResult)
		{
			Point point = btnRecordManualResult.getLocationOnScreen();
			point.y = point.y + btnRecordManualResult.getHeight();
			ParamAndResult paramAndResult = new ParamAndResult(point);
			controler.action(this, ITestStepControler.ACTION_RECORDRESULT,
			        paramAndResult);
		}
	}

	@Override
	public void onResult(String result, String lParam, String wParam)
	{
		String resultInfo = result;
		if (null == resultInfo)
		{
			resultInfo = "";
		}
		btnRecordManualResult.changeIcons("/icon/btn/result/", resultInfo
		        .toString().toLowerCase());
	}
}
