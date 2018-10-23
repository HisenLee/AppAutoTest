package com.testserver.util;

/*
 *此class只用于测试，不用在自动化脚本上 
 * 
 * 
 * */
import java.io.File;
import java.util.Date;

import com.intel.cats.test.log.ILog;
import com.intel.cats.test.log.Util;

public class snapshottime implements Runnable
{
	@SuppressWarnings("unused")
    private String apkpath;
	private String spath;
	private String deviceid;

	public snapshottime(String apkpath, String spath, String deviceid)
	{
		super();
		this.apkpath = apkpath;
		this.spath = spath;
		this.deviceid = deviceid;
	}

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		/*
		 * String apkpath="/home/ytf/Downloads/downapk/density.apk"; String
		 * spath="/home/ytf/Desktop/xuexi"; ArrayList<String> deviceids =
		 * DeviceOperate.checkdevice(); for(String deviceid:deviceids) {
		 * Runnable run=new snapshottime(apkpath,spath,deviceid); Thread t=new
		 * Thread(run); t.start(); }
		 */
		/*
		 * String spath="/home/ytf/Desktop/xuexi"; Thread t=new Thread(new
		 * snapshottime("", spath, "03ff77b922350a6b")); t.start();
		 */
		String str = "1234##@##4567";
		String[] split = str.split("##@##");
		for (int i = 0; i < split.length; i++)
		{
			ILog.getLog().logMain(split[i]);
		}
	}

	@Override
	public void run()
	{
		// TODO Auto-generated method stub
		try
		{
			/*
			 * AdbOperate.install(apkpath, deviceid); String testinfo =
			 * ParseApk.getInfo(apkpath); String packagename =
			 * ParseApk.getpackname(testinfo); String activityname =
			 * ParseApk.getActivity(testinfo);
			 */

			AdbOperate
			        .start("air.com.ifree.mfjs.cardCHS/air.com.ifree.mfjs.cardCHS.AppEntry",
			                deviceid);
			Util.block(deviceid, 10000);
			Date dateo = new Date();
			AdbOperate.snapshot(spath + File.separator + deviceid + ".png",
			        deviceid);
			Date daten = new Date();
			long time = (daten.getTime() - dateo.getTime());
			System.err.println(deviceid + "---" + time + " ms");
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
