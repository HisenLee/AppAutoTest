package com.itt.testcase.strategy;

import com.itt.testcase.strategy.ITestStrategy.TestMode;

/**
 * @author xiaobolx
 * 2015年11月26日
 */
public class TestStrategyFactory
{
	private static TestStrategyFactory factory = new TestStrategyFactory();
	
	public static TestStrategyFactory getStrategyFactory()
	{
		return factory;
	}
	
	public ITestStrategy createStrategy(TestMode testMode)
	{
		switch (testMode)
        {
		case enLazy:
			return new TestLazyStrategy();
		case enFixed:
			return new TestStanderStrategy();
		default:
			return null;
		}
	}
}
