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
 package com.cats.version.client.ui;

import javax.swing.JDialog;

import com.cats.version.utils.Utils;

public class HttpLoaderUI extends JDialog
{

	private static final long serialVersionUID = 5514178720376726084L;
	
	private int width;
	private int height;
	public HttpLoaderUI()
	{
		this.width = 100;
		this.height = 100;
		this.setUndecorated(true);
		this.setAlwaysOnTop(true);
		this.setTitle("Version service");
		this.setSize(width, height);
		Utils.centerWindow(width, height, this);
		this.setVisible(true);
	}
	
	public static void main(String[] args)
	{
		new HttpLoaderUI();
	}

}
