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
 
package com.cats.version;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xblia2 Jun 8, 2015
 */
public class VersionInfoDetail implements Serializable
{
	private static final long serialVersionUID = 4746531216020983910L;
	private String title;
	private List<String> detail = new ArrayList<String>();

	public VersionInfoDetail()
    {
    }
	
	public VersionInfoDetail(String title, List<String> detail)
    {
	    super();
	    this.title = title;
	    if(null != detail)
	    {
	    	this.detail = detail;
	    }
    }

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public List<String> getDetail()
	{
		return detail;
	}

	public void setDetail(List<String> detail)
	{
		if(null  != detail)
		{
			this.detail = detail;
		}
	}

	@Override
    public String toString()
    {
	    return this.title;
    }
}
