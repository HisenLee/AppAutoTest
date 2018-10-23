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

import com.cats.utils.Utils;

public class ComparatorFileSize implements Comparator<String>
{
	@Override
    public int compare(String o1, String o2)
    {
		String val1 = o1.toUpperCase();
		String val2 = o2.toUpperCase();
		
		if(Utils.isEmpty(val1) || Utils.isEmpty(val2))
		{
			return 0;
		}
		
		if(val1.endsWith("MB"))
		{
			if(val2.endsWith("MB"))
			{
				double dval1 = Double.parseDouble(val1.replace("MB", ""));
				double dva2 = Double.parseDouble(val2.replace("MB", ""));
				return dval1 > dva2 ? 1:-1;
			}else
			{
				return -1;
			}
		}else
		{
			if(val2.endsWith("KB"))
			{
				double dval1 = Double.parseDouble(val1.replace("KB", ""));
				double dva2 = Double.parseDouble(val2.replace("KB", ""));
				return dval1 > dva2 ? 1:-1;
			}
			return -1;
		}
    }

}
