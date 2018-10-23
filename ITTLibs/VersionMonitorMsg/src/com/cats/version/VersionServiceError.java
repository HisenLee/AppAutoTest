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


/**
 * @author xblia2
 * Jun 12, 2015
 */
public enum VersionServiceError
{
	SUCCESS
	{
		@Override
		public String getErrorDesc()
		{
		    return "SUCC";
		}
	},
	UNKNOW_ERROR
	{
		@Override
		public String getErrorDesc()
		{
			return "Unknow error, pls contact the developer thks";
		}
	},
	HANDLEMSG_POST
	{
		@Override
		public String getErrorDesc()
        {
			return "Post method error, get attribute key 'msg' is empty.";
        }
	},
	HANDLEMSG_JSONPARSE
	{
		@Override
		public String getErrorDesc()
		{
			return "Parse message to json object error, may be message formatter error.";
		}
	}, 
	
	HANDLEMSG_PERMISSION_DENIED
	{
		@Override
		public String getErrorDesc()
		{
			return "Permission denied, may be wrong 'msgIdentified' type.";
		}
	},
	HANDLEMSG_UNSUPPORTMSG
	{
		@Override
		public String getErrorDesc()
		{
			return "Can't find message handler, may be not support msgtype.";
		}
	};
	
	public abstract String getErrorDesc();

	String errorDesc;
}
