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

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * @author xblia
 * 2015年9月18日
 */
public class StyleMenuOfMainFrame extends JFrame
{
    private static final long serialVersionUID = 7705088241273356450L;

	private static final List<String> STYLE_LIST = new LinkedList<String>();
	
	private static final String STYLE_NAME_REG = ".*\\.(?<styleName>[\\w]+)LookAndFeel";
	private static final Pattern STYLE_NAME_PATTERN = Pattern.compile(STYLE_NAME_REG);
	
	private String executableName;

	static
	{
		STYLE_LIST.add("com.jtattoo.plaf.noire.NoireLookAndFeel");
		STYLE_LIST.add("com.jtattoo.plaf.smart.SmartLookAndFeel");
		STYLE_LIST.add("com.jtattoo.plaf.mint.MintLookAndFeel");
		STYLE_LIST.add("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
		STYLE_LIST.add("com.jtattoo.plaf.luna.LunaLookAndFeel");
		STYLE_LIST.add("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
		STYLE_LIST.add("com.jtattoo.plaf.fast.FastLookAndFeel");
		STYLE_LIST.add("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");
		STYLE_LIST.add("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
		STYLE_LIST.add("com.jtattoo.plaf.aero.AeroLookAndFeel");
		STYLE_LIST.add("com.jtattoo.plafacryl.AcrylLookAndFeel");
		STYLE_LIST.add("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
	}
	
	public StyleMenuOfMainFrame()
    {
    }
	
	public StyleMenuOfMainFrame(String executableName)
    {
	    this.executableName = executableName;
	    initMenuView();
    }
	
	public static void usingComstomeStyle()
	{
		new StyleInjection().initComstomeStyle();
	}
	
	private void initMenuView()
	{
		JMenuBar bar = new JMenuBar();
		JMenu styleMenu = new JMenu("Window-Style");
		
		JMenuItem styleMenuItem;
		ButtonGroup styleMenuItemGroup = new ButtonGroup();
		String styleDefault = StyleStore.getStore().getStyleFromStoreFile();
		
		JMenuItem originStyleMenuItem = new JCheckBoxMenuItem("Origin");
		originStyleMenuItem.setAction(new StyleMenuActions("Origin", "Origin", executableName));
		styleMenu.add(originStyleMenuItem);
        styleMenuItemGroup.add(originStyleMenuItem);
        boolean isOriginStyle = true;
		for (String styleClass : STYLE_LIST)
        {
	        String styleName = getStyleName(styleClass);
	        styleMenuItem = new JCheckBoxMenuItem(styleName);
	        styleMenuItem.setAction(new StyleMenuActions(styleName, styleClass, executableName));
	        if(styleDefault != null && styleDefault.equals(styleClass))
	        {
	        	styleMenuItem.setSelected(true);
	        	isOriginStyle = false;
	        }
	        
	        styleMenu.add(styleMenuItem);
	        styleMenuItemGroup.add(styleMenuItem);
        }
		if(isOriginStyle)
		{
			originStyleMenuItem.setSelected(true);
		}
		bar.add(styleMenu);
		this.setJMenuBar(bar);
	}
	
	private String getStyleName(String style)
	{
		String styleName = null;
		Matcher matcher = STYLE_NAME_PATTERN.matcher(style);
		if(matcher.matches())
		{
			styleName = matcher.group("styleName");
		}
		
		return styleName == null ?  "Style" + System.currentTimeMillis():styleName;
	}
}