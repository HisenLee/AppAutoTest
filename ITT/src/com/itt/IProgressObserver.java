package com.itt;

/**
 * @author xblia
 * 2015年10月27日
 */
public interface IProgressObserver
{
	/**
	 * @param deviceId
	 * @param info
	 * @param total
	 * @param finished
	 */
	void onProgress(String deviceId, String info,  int total, int finished);
}
