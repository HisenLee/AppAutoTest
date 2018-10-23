package com.testserver.test.lock;

/**
 * @author xblia
 * 2015年6月24日
 */
public class ScriptLock
{
	private LockStatus lockStatus;
	
	public ScriptLock()
    {
		lockStatus = LockStatus.enUnNotify;
    }

	public LockStatus getLockStatus()
	{
		return lockStatus;
	}

	public void setLockStatus(LockStatus lockStatus)
	{
		this.lockStatus = lockStatus;
	}
	
}
