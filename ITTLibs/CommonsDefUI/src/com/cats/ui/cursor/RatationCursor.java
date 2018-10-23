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

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;

/**
 * @author xiaobolx
 * 2015年11月18日
 */
public class RatationCursor
{
    private int cursorDirction = 1;
    private int cursorIndex = 1;
	
	public RatationCursor(final Component component)
    {
		Timer timer = new Timer();
		timer.schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				component.setCursor(getCursor());
			}
		}, 0, 90);
    }
	
	public Cursor getCursor()
	{
		String gameCursorIcon = "/com/cats/ui/cursor/cursor_%s.png";
		String cursorIcon = String.format(gameCursorIcon, getCursorIndex());
		Cursor customeCursor = Toolkit
		        .getDefaultToolkit()
		        .createCustomCursor(
		                new ImageIcon(
		                        BeautifulCursor.class
		                                .getResource(cursorIcon))
		                        .getImage(), new Point(10, 10), "custome");
		return customeCursor;
	}
	
	public int getCursorIndex()
	{
		cursorIndex += cursorDirction;
		if(cursorIndex >= 10 || cursorIndex <= 1)
		{
			cursorDirction *= -1;
		}
		return cursorIndex;
	}
}
