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
 package com.cats.ui.tooltip;
import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

/**
 * @author xblia
 * 20151129
 */
public class TooltipLabelForHelp extends JLabel
{

	private static final long serialVersionUID = 1L;
	
	public TooltipLabelForHelp(String tooltipContent, Font font, JFrame frame)
	{
		this.setIcon(new ImageIcon(BubbleToolTip.class.getResource("help.png")));
		BubbleToolTip tooltip = null;
		if(null != font)
		{
			tooltip = new BubbleToolTip(font, tooltipContent, frame);
		}else
		{
			tooltip = new BubbleToolTip(tooltipContent, frame);
		}
		initView(tooltip);
	}
	
	public TooltipLabelForHelp(String tooltipContent, Font font, Color bgColor, Color foreColor, Color borderColor, JFrame frame)
	{
		this.setIcon(new ImageIcon(BubbleToolTip.class.getResource("help.png")));
		BubbleToolTip tooltip = null;
		if(font == null || bgColor == null || foreColor == null || borderColor == null)
		{
			throw new NullPointerException("Tooltip font or background color or foreground color or border color is null.");
		}
		tooltip = new BubbleToolTip(tooltipContent, font, bgColor, foreColor, borderColor, frame);
		initView(tooltip);
	}
	
	private void initView(BubbleToolTip tooltip)
    {
		this.addMouseListener(tooltip);
		this.setBorder(new EmptyBorder(0, 10, 0, 0));
    }
}