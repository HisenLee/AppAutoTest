package com.test.po;

//crash 信息
public class CrashInfo
{
	private String packagename;// crash 所发生的报名
	private String crashtype;// crash 的类型

	public CrashInfo(String packagename, String crashtype)
	{
		super();
		this.packagename = packagename;
		this.crashtype = crashtype;
	}

	/*
	 * @Override public int hashCode() { final int prime = 31; int result = 1;
	 * result = prime * result + ((crashtype == null) ? 0 :
	 * crashtype.hashCode()); result = prime * result + ((packagename == null) ?
	 * 0 : packagename.hashCode()); return result; }
	 * 
	 * @Override public boolean equals(Object obj) { if (this == obj) return
	 * true; if (obj == null) return false; if (getClass() != obj.getClass())
	 * return false; CrashInfo other = (CrashInfo) obj; if (crashtype == null) {
	 * if (other.crashtype != null) return false; } else if
	 * (other.crashtype.equals("")) { if(!crashtype.equals(other.packagename))
	 * return false; }else if(!crashtype.equals(other.crashtype)) return false;
	 * if (packagename == null) { if (other.packagename != null) return false; }
	 * else if (!packagename.equals(other.packagename)) return false; return
	 * true; }
	 */

	@Override
    public int hashCode()
    {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result
	            + ((packagename == null) ? 0 : packagename.hashCode());
	    return result;
    }

	@Override
    public boolean equals(Object obj)
    {
	    if (this == obj)
		    return true;
	    if (obj == null)
		    return false;
	    if (getClass() != obj.getClass())
		    return false;
	    CrashInfo other = (CrashInfo) obj;
	    if (crashtype == null)
	    {
	    	return true;
	    } else if (!crashtype.equals(other.crashtype) && !crashtype.isEmpty())
	    {
	    	return false;
	    }
	    
	    if (packagename == null)
	    {
		    if (other.packagename != null)
			    return false;
	    } else if (!packagename.equals(other.packagename))
		    return false;
	    return true;
    }

	public String getPackagename()
	{
		return packagename;
	}

	public void setPackagename(String packagename)
	{
		this.packagename = packagename;
	}

	public String getCrashtype()
	{
		return crashtype;
	}

	public void setCrashtype(String crashtype)
	{
		this.crashtype = crashtype;
	}
}