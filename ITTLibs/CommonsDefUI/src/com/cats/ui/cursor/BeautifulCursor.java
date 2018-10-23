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
 package com.cats.ui.cursor;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

/**
 * @author xiaobolx
 * 2015年11月18日
 */
public class BeautifulCursor
{
	private static Cursor customeCursor;
	private static String gameCursorIcon = "/com/cats/ui/cursor/icon_cursor_hand.png";

	public static Cursor getCursor()
	{
		if (null == customeCursor)
		{
			customeCursor = Toolkit
			        .getDefaultToolkit()
			        .createCustomCursor(
			                new ImageIcon(
			                        BeautifulCursor.class
			                                .getResource(gameCursorIcon))
			                        .getImage(), new Point(10, 10), "custome");

		}

		return customeCursor;
	}
}
