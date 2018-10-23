package trialversion;

import java.util.Date;

/**
 * @author xiaobolx
 * 2016年2月22日
 */
public class Trialversion extends Thread
{
	public static final boolean isTrialVersion = false;
	private static Date LASTDATE = new Date(1483027200000l);
	
	@Override
	public void run()
	{
		if(!isTrialVersion)
		{
			return;
		}
		
		while(true)
		{
			try
            {
				if(new Date().after(LASTDATE))
				{
					LienceseMsg.showTrialVerDueWarning();
					System.exit(0);
				}
	            Thread.sleep(1000*60*60);
            } catch (InterruptedException e)
            {
	            e.printStackTrace();
            }
		}
	}
}