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
 package com.ui.stylemenu;

import java.util.ArrayList;
import java.util.List;

import com.cats.ui.alertdialog.AlertDialog;
import com.cats.ui.alertdialog.AlertDialogOptions;

/**
 * @author xblia
 * 2015年9月18日
 */
public class StartSubProcess
{
	protected static void startUp(String uri)
	{
		List<String> cmd = new ArrayList<String>();
		cmd.add("java");
		cmd.add("-jar");
		cmd.add(uri);
		ProcessBuilder builder = new ProcessBuilder(cmd);
		try
        {
	        builder.start();
        } catch (Exception e)
        {
	        e.printStackTrace();
	        alertError(e.toString());
        }
		System.exit(0);
	}
	
	protected static void alertError(String info)
	{
		String message = "Sorry, Upgrade internal error(" +info+ "), you can contact the developer or tray again. thks";
		String title = "Upragde error.";
		AlertDialog.show(title, message, AlertDialogOptions.OPTION_OK, AlertDialog.ALERT_TYPE_WARNING);
	}
}
