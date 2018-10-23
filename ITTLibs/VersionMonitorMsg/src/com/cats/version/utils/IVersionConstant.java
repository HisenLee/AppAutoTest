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
 package com.cats.version.utils;

import java.nio.charset.Charset;

/**
 * @author xblia
 * 2015年6月12日
 */
public class IVersionConstant
{
	public static final String LINE = System.getProperty("line.separator");
	
	public static Charset CHARSET_UTF8 = Charset.forName("UTF-8");
	
	public static String TEMPFILE_PREFIX = "TempFile_Upgrade_xx_";
	
	public static char SOCKET_MSG_LINECHAR = '\n';
	
	public static String SOCKET_MSG_STARTUPOK = "STARTUP_OK";
}
