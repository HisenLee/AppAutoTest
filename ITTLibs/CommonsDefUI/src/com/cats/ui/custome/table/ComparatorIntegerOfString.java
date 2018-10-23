/*
 * Copyright 2015 lixiaobo
 *
 * VersionUpgrade project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
 package com.cats.ui.custome.table;

import java.util.Comparator;

/**
 * @author xiaobolx
 * 2015年11月20日
 */
public class ComparatorIntegerOfString implements Comparator<String>
{

	private String tag;
	
	public ComparatorIntegerOfString(String tag)
    {
	    super();
	    this.tag = tag;
    }

	@Override
    public int compare(String o1, String o2)
    {
		if(null == o1 || o1.isEmpty() || null == o2 || o2.isEmpty())
		{
			return 0;
		}
		
		try
        {
	        int value1 = Integer.parseInt(o1.replaceAll(tag, ""));
	        int value2 = Integer.parseInt(o2.replaceAll(tag, ""));
	        if(value1 > value2)
	        {
	        	return 1;
	        }else if(value1 < value2)
	        {
	        	return -1;
	        }else
	        {
	        	return 0;
	        }
        } catch (NumberFormatException e)
        {
	        e.printStackTrace();
        }
		return o1.compareTo(o2);
    }

	
}
