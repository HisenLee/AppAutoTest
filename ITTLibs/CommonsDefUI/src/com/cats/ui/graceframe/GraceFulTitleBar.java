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
 package com.cats.ui.graceframe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.cats.ui.custome.barbtn.BarIconType;
import com.cats.ui.custome.barbtn.TitleBarButton;
import com.cats.utils.IColorFontUtil;

/**
 * @author xblia
 * 2015年10月23日
 */
public class GraceFulTitleBar extends JPanel
{
	private static final long serialVersionUID = 1L;
	private int width;
	private int height;
	private int titleHeight = 25;
	private ImageIcon bannerIcon;
	private int barStyle;
	private IGraceFulIconInfoProvider graceFulIconInfoProvider;
	
	private IGraceFulTitlePanelHandler handler;
	
	private String title;

	public GraceFulTitleBar(int barStyle, IGraceFulIconInfoProvider graceFulIconInfoProvider, String title,
            IGraceFulTitlePanelHandler handler)
    {
		this.barStyle = barStyle;
		this.graceFulIconInfoProvider = graceFulIconInfoProvider;
		this.title = title;
		this.handler = handler;
		
		initView();
    }

	private void initView()
    {
		if(graceFulIconInfoProvider.getBannerRelativePath() != null)
		{
			bannerIcon = new ImageIcon(GraceFulTitleBar.class.getResource(graceFulIconInfoProvider.getBannerRelativePath()));
			width = bannerIcon.getIconWidth();
			height = bannerIcon.getIconHeight();
		}

		if((barStyle & BarIconType.BANNER.getType()) > 0)
		{
			this.setPreferredSize(new Dimension(width, height + titleHeight));
		}
		else
		{
			this.setPreferredSize(new Dimension(width, titleHeight));
		}
		
		
		this.setLayout(new BorderLayout());
		JPanel topPanel = new JPanel(new BorderLayout());
		JPanel topEastPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		JLabel topCenterLabel = new JLabel(this.title, JLabel.LEFT);
		JLabel topWestLael = new JLabel(new ImageIcon(GraceFulTitleBar.class.getResource(graceFulIconInfoProvider.getIconRelativePath())));
		
		topPanel.setPreferredSize(new Dimension(this.getSize().width, titleHeight));
		topCenterLabel.setBorder(new EmptyBorder(2, 2, 2, 2));
		topCenterLabel.setForeground(Color.WHITE);
		topCenterLabel.setFont(IColorFontUtil.FONT_BLOK);
		topWestLael.setBorder(new EmptyBorder(2, 2, 2, 2));

		topPanel.setBackground(IColorFontUtil.COLOR_TITLE_BG_BLUE);//new Color(0,0,0,55)
		topPanel.setOpaque(true);
		topEastPanel.setOpaque(false);
		
		initBarButton(topEastPanel);
		
		topPanel.add(topWestLael, BorderLayout.WEST);
		topPanel.add(topCenterLabel, BorderLayout.CENTER);
		topPanel.add(topEastPanel, BorderLayout.EAST);
		
		this.add(topPanel, BorderLayout.NORTH);
    }

	private void initBarButton(JPanel topEastPanel)
    {
		if ((barStyle & BarIconType.MENU.getType()) > 0)
		{
			JButton minBtn = new TitleBarButton(BarIconType.MENU, handler);
			topEastPanel.add(minBtn);
		}
		
	    if ((barStyle & BarIconType.MIN.getType()) > 0)
		{
			JButton minBtn = new TitleBarButton(BarIconType.MIN, handler);
			topEastPanel.add(minBtn);
		}
	    
	    if ((barStyle & BarIconType.MAX.getType()) > 0)
		{
			JButton minBtn = new TitleBarButton(BarIconType.MAX, handler);
			topEastPanel.add(minBtn);
		}
	    
		if ((barStyle & BarIconType.CLOSE.getType()) > 0)
		{
			JButton closeBtn = new TitleBarButton(BarIconType.CLOSE, handler);
			topEastPanel.add(closeBtn);
		}
    }
	
	@Override
	protected void paintComponent(Graphics arg0)
	{
		super.paintComponent(arg0);
		if((barStyle & BarIconType.BANNER.getType()) == 0)
		{
			return;
		}
		bannerIcon.setImage(bannerIcon.getImage().getScaledInstance(this.getSize().width,
		        this.getSize().height-titleHeight, Image.SCALE_SMOOTH));
		arg0.drawImage(bannerIcon.getImage(), 0, titleHeight, null);
		System.gc();

	}
}