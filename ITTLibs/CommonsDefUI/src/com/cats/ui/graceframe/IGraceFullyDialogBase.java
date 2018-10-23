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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import com.cats.ui.custome.barbtn.BarIconType;
import com.cats.utils.IColorFontUtil;

/**
 * @author xiaobolx
 * 2015年11月10日
 */
public abstract class IGraceFullyDialogBase extends JDialog implements IGraceFulTitlePanelHandler, IGraceFulIconInfoProvider
{
    private static final long serialVersionUID = 1L;
    
    protected String title;
    protected ICON_STYLE barSize;
    protected int barStyle;
    
	protected IGraceFullyDialogBase(String title, ICON_STYLE barSize, int barStyle)
    {
		this.title = title;
		this.barSize = barSize;
		this.barStyle = barStyle;
		this.initBaseFrame(title, barSize);
    }
	
	protected void initBaseFrame(String title, ICON_STYLE barSize)
    {
		this.setUndecorated(true);
		this.setResizable(false);
		this.getRootPane().setBorder(new LineBorder(IColorFontUtil.COLOR_TITLE_BG_BLUE, 1));
		this.moveWindowInit();
		
		this.setLayout(new BorderLayout());
		if((barStyle & BarIconType.TITLE.getType()) > 0)
		{
			this.add(new GraceFulTitleBar(barStyle, this, title, this), BorderLayout.NORTH);
		}
		
		initBottomPanel();
    }
	
	protected void initBottomPanel()
	{
		JLabel bottomPanel = new JLabel();
		bottomPanel.setOpaque(true);
		bottomPanel.setPreferredSize(new Dimension(this.getWidth(), 5));
		bottomPanel.setBackground(IColorFontUtil.COLOR_TITLE_BG_BLUE);
		this.add(bottomPanel, BorderLayout.SOUTH);
	}

	Point origin = new Point();
	public void moveWindowInit()
	{
		this.getRootPane().addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				origin.x = e.getX();
				origin.y = e.getY();
			}
		});
		
		this.getRootPane().addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				Point p = IGraceFullyDialogBase.this.getLocation();
				IGraceFullyDialogBase.this.setLocation(p.x + e.getX() - origin.x, p.y + e.getY()- origin.y);
			}
		});
	}
	
	@Override
    public void onMenuButton(Component component, Point point)
    {
    }
	
	@Override
    public void onMinButton()
    {
    }
	
	@Override
    public void onMaxButton()
    {
    }
	
	@Override
    public void onCloseButton()
    {
		/*Window window = getWindows()[0];
        if (window != null) 
        {
            window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
        }*/
		this.dispose();
    }

	@Override
    public String getIconRelativePath()
    {
	    return ICON_STYLE.ITT_ICON.getIconName();
    }

	@Override
    public String getBannerRelativePath()
    {
		if(null != barSize)
		{
			return barSize.getIconName();
		}
		return null;
    }
}
