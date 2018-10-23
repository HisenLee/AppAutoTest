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
 package com.cats.border;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/**
 * @author xblia
 * 2015年9月18日
 */
public class BorderFactoryEx
{
	private static Font DEFAULT_FONT = new Font("Verdana",Font.PLAIN, 12);
	
	public static Border getTitleBorder(String title)
	{
		EmptyBorder insideBorder = new EmptyBorder(5, 5, 5, 5);
		TitledBorder outsideBorder = BorderFactory.createTitledBorder(
		        new LineBorder(new Color(0xdddddd)), title);
		outsideBorder.setTitleFont(DEFAULT_FONT);
		outsideBorder.setTitleColor(new Color(0xbbbbbb));
    	return BorderFactory.createCompoundBorder(outsideBorder, insideBorder);
	}
	
	public static Border getTitleBorder(String title, Color color, int thickness)
	{
		EmptyBorder insideBorder = new EmptyBorder(2, 2, 2, 2);
		TitledBorder outsideBorder = BorderFactory.createTitledBorder(
		        new LineBorder(color, thickness), title);
		outsideBorder.setTitleFont(DEFAULT_FONT);
		outsideBorder.setTitleColor(color);
    	return BorderFactory.createCompoundBorder(insideBorder, outsideBorder);
	}
	
	public static Border getEtchedTitleBorder(String title)
	{
		EmptyBorder insideBorder = new EmptyBorder(2, 2, 2, 2);
		TitledBorder outsideBorder = BorderFactory.createTitledBorder(
		        BorderFactory.createEtchedBorder(), title);
		outsideBorder.setTitleFont(DEFAULT_FONT);
		outsideBorder.setTitleColor(new Color(0xaaaaaa));
    	return BorderFactory.createCompoundBorder(insideBorder, outsideBorder);
	}
	
	public static Border getBevelTitleBorder(Component component, String title)
	{
		EmptyBorder insideBorder = new EmptyBorder(0, 0, 0, 0);
		
		Border bevelBorder = BorderFactory.createBevelBorder(BevelBorder.RAISED, component.getBackground().darker().darker(), component.getBackground().darker(),
				component.getBackground().darker().darker(), component.getBackground().darker());
		
		TitledBorder outsideBorder = BorderFactory.createTitledBorder(bevelBorder, title);
		outsideBorder.setTitleFont(DEFAULT_FONT);
		outsideBorder.setTitleColor(new Color(0xaaaaaa));
    	return BorderFactory.createCompoundBorder(insideBorder, outsideBorder);
	}
}
