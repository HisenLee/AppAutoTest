package trialversion;

import com.cats.ui.alertdialog.AlertDialog;

/**
 * @author xiaobolx
 * 2016年2月22日
 */
public class LienceseMsg
{
	public static void showTrialVerDueWarning()
	{
		String message = "The trial version has expired, please contact the developer ( caihong.qian@intel.com)";
		AlertDialog.show("TrialVersion Warning.", message);
	}
}
