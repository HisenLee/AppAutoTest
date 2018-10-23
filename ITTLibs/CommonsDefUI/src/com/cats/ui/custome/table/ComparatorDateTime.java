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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import com.cats.utils.Utils;

public class ComparatorDateTime implements Comparator<String>
{
	SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss");
	@Override
    public int compare(String o1, String o2)
    {
		String val1 = o1;
		String val2 = o2;
		if(Utils.isEmpty(val1) || Utils.isEmpty(val2))
		{
			return 0;
		}
		
		try
        {
	        Date date1 = sdf.parse(val1);
	        Date date2 = sdf.parse(val2);
	        return date1.compareTo(date2);
        } catch (ParseException e)
        {
	        e.printStackTrace();
        }
		return 0;
    }

}
