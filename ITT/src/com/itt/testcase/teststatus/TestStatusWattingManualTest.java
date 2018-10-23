package com.itt.testcase.teststatus;

import java.awt.Point;
import java.awt.event.ActionEvent;

import com.cats.ui.alertdialog.AlertDialog;
import com.cats.ui.alertdialog.AlertDialogOptions;
import com.cats.ui.optiondlg.AlertDialogForOptionSelection;
import com.cats.ui.optiondlg.ResultCallback;
import com.intel.cats.test.log.Util;
import com.itt.devices.statusfilter.ParamAndResult;
import com.itt.preferences.UserParam;
import com.itt.testcase.ITestStepControler;

/**
 * @author xblia
 * 2015年10月21日
 */
public class TestStatusWattingManualTest extends CommonTestStatus implements ResultCallback
{

	private boolean userConfirmed = false;
	private String userInputResult;
	private String failReason;
	private ResultCallback resultCallback;
	
	public TestStatusWattingManualTest(ITTTestContext context)
    {
	    super(context);
    }

	@Override
    public boolean doAction()
    {
		if(UserParam.getAutoTest())
		{
			context.setStatus(STATUS_RANDOMCLICK);
			return true;
		}
		
		while(!userConfirmed)
		{
			Util.block(1000);
		}
		if(isCrash())
		{
			testResult.setRandomsuccess(RESULT_FAIL);
		}
		
		context.setStatus(STATUS_QUIT);
		return true;
    }

	@Override
    public int getStatusCode()
    {
	    return STATUS_WAITTING_USER;
    }

	@Override
    public void actionPerformed(ActionEvent e)
    {
		if(null == userInputResult)
		{
			int iRes = AlertDialog.show("Test Result", "You haven’t record the result, are you sure?", AlertDialogOptions.OPTION_YES | AlertDialogOptions.OPTION_NO, AlertDialog.ALERT_TYPE_MESSAGE);
			if(iRes != AlertDialogOptions.OPTION_YES)
			{
				return;
			}
		}
		this.userConfirmed = true;
    }
	
	@Override
	public void action(ResultCallback resultCallback, int iActionCode, ParamAndResult param)
	{
		this.resultCallback = resultCallback;
		if(iActionCode == ITestStepControler.ACTION_SNAPSHOT)
		{
			String snapshotImage = beginSnapShot("random_click");
			testResult.setRandomimgurl(snapshotImage);
			AlertDialog.show("Snapshot success!~");
		}else if(iActionCode == ITestStepControler.ACTION_RECORDRESULT)
		{
			Point point = (Point)param.getParam();
			AlertDialogForOptionSelection.show(this, new String[]{"Pass", "Fail", "Block"}, userInputResult, failReason, point.x, point.y);
		}
	}

	@Override
    public void onResult(String result, String lParam, String wParam)
    {
		this.userInputResult = result;
		this.failReason = lParam;
		testResult.setRandomsuccess(userInputResult);
		testResult.setTouchFailReason(failReason == null ? "":failReason);
		
		if(null != resultCallback)
		{
			resultCallback.onResult(result, lParam, wParam);
		}
    }
}
