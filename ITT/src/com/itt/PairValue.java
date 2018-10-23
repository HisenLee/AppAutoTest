package com.itt;

/**
 * @author xiaobolx
 * 2015年11月9日
 * @param <T>
 * @param <F>
 */
public class PairValue<T, F>
{
	private T t;
	private F f;
	public PairValue(T t, F f)
    {
	    super();
	    this.t = t;
	    this.f = f;
    }
	public T getT()
	{
		return t;
	}
	public void setT(T t)
	{
		this.t = t;
	}
	public F getF()
	{
		return f;
	}
	public void setF(F f)
	{
		this.f = f;
	}
}
