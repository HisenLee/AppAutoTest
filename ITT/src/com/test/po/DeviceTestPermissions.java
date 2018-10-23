package com.test.po;

/**
 * @author xblia
 * 2015年10月23日
 */
public class DeviceTestPermissions
{
	@SuppressWarnings("unused")
    private String deviceId = "";
	private int testedCount = 0;
	private boolean isAllowTest = true;

	public DeviceTestPermissions(String deviceId)
	{
		super();
		this.deviceId = deviceId;
	}

	public int getTestedCount()
	{
		return testedCount;
	}

	public void increaseTestCount()
	{
		this.testedCount++;
	}

	public boolean isAllowTest()
	{
		return isAllowTest;
	}

	public void setAllowTest(boolean isAllowTest)
	{
		this.isAllowTest = isAllowTest;
	}
}
