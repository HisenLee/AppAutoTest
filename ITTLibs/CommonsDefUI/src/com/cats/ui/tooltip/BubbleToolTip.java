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
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import java.util.StringTokenizer;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.cats.utils.Utils;

/**
 * @author xblia
 * 2015-11-29
 * Power by xblia, All right reserved.
 * Mail:worldandyou@163.com
 */
class BubbleToolTip extends JPanel implements MouseListener
{
	public enum TooltipFont
	{
		YAHEI(new Font("Microsoft YaHei", Font.PLAIN, 13)),
		SEGOE_UI(new Font("Segoe UI", Font.PLAIN, 13));
		private Font font;

		private TooltipFont(Font font)
		{
			this.font = font;
		}
		
		public Font getFont()
		{
			return this.font;
		}
	}
	
	private static final long serialVersionUID = 1L;
	private static final int CORNER_ANGLE = 12;
	
	private Font font = TooltipFont.SEGOE_UI.getFont();
	private Color backGroundCol = new Color(0xFDFDFD);
	private Color foregroundColorCol = new Color(0x000000);
	private Color borderCol = new Color(0x000000);
	
	private int remainTooltipWidth = 0;
	private String content = null;
	private JFrame frame = null;
	private boolean alignTop = true;

	public BubbleToolTip(String tooltipContent, Font textFont, Color backGroundColor,
	        Color foregroundColor, Color borderColor, JFrame frame)
	{
		this.content = tooltipContent;
		this.font = textFont;
		this.backGroundCol = backGroundColor;
		this.foregroundColorCol = foregroundColor;
		this.borderCol = borderColor;
		this.frame = frame;
		this.initView();
	}
	
	public BubbleToolTip(String tooltipContent, JFrame frame)
	{
		this.content = tooltipContent;
		this.frame = frame;
		this.initView();
	}

	public BubbleToolTip(Font font, String content, JFrame frame)
	{
		super();
		this.font = font;
		this.content = content;
		this.frame = frame;
		this.initView();
	}

	private void initView()
	{
		String maxCharactorsLine = getMaxCharactorsLine();

		this.setSize(SwingUtilities.computeStringWidth(this.getFontMetrics(font), maxCharactorsLine) + 20,
		        new StringTokenizer(content, Utils.LINE).countTokens() * 20 + 20);
		this.setOpaque(false);
		this.setVisible(false);

		frame.getLayeredPane().add(this, JLayeredPane.POPUP_LAYER);
	}

	private String getMaxCharactorsLine()
	{
		String[] lines = content.split(Utils.LINE);
		String maxCharactorsLine = "";
		if(null != lines && lines.length > 0)
		{
			for (String line : lines)
			{
				if(line.length() > maxCharactorsLine.length())
				{
					maxCharactorsLine = line;
				}
			}
		}
		
		if(maxCharactorsLine.length() != 0)
		{
			return maxCharactorsLine;
		}else
		{
			return content;
		}
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		        RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(backGroundCol);
		/* AlphaComposite.
		 * AlphaComposite composite = AlphaComposite
		        .getInstance(AlphaComposite.SRC_OVER, 90 / 100.0F);
		g2d.setComposite(composite);
		*/
		g2d.fill(getArea());
		g2d.setColor(foregroundColorCol);
		
		/*
		 * Draw Text
		 */
		StringTokenizer contentTokenizer = new StringTokenizer(content, Utils.LINE);
		int contentStartY = alignTop ? 20:30;
		g2d.setFont(font);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		while (contentTokenizer.hasMoreElements())
		{
			g2d.drawString(contentTokenizer.nextToken(), 10, contentStartY);
			contentStartY = contentStartY + 20;
		}
	}

	@Override
	protected void paintBorder(Graphics g)
	{
		super.paintBorder(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		        RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(borderCol);
		g2d.draw(getArea());
	}

	private Area getArea()
	{
		if(alignTop)
		{
			return getAreaOfTop();
		}
		else
		{
			return getAreaOfBottom();
		}
	}
	
	private Area getAreaOfTop()
	{
		Area area = new Area(new RoundRectangle2D.Float(0, 0,
		        this.getWidth() - 1, this.getHeight() - 10, CORNER_ANGLE, CORNER_ANGLE));
		Polygon polygon = new Polygon();
		polygon.addPoint(10 + remainTooltipWidth, this.getHeight() - 10);
		polygon.addPoint(0 + remainTooltipWidth, this.getHeight());
		polygon.addPoint(25 + remainTooltipWidth, this.getHeight() - 10);
		area.add(new Area(polygon));

		return area;
	}
	
	private Area getAreaOfBottom()
	{
		Area area = new Area(new RoundRectangle2D.Float(0, 10,
		        this.getWidth() - 1, this.getHeight()-1-10, CORNER_ANGLE, CORNER_ANGLE));
		Polygon polygon = new Polygon();
		polygon.addPoint(10 + remainTooltipWidth, 10);
		polygon.addPoint(0 + remainTooltipWidth, 0);
		polygon.addPoint(25 + remainTooltipWidth, 10);
		area.add(new Area(polygon));

		return area;
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		Component sourceComponent = e.getComponent();
		Point srcPoint = new Point();
		srcPoint.x = sourceComponent.getWidth();
		srcPoint.y = 0;
		Point realPoint = SwingUtilities.convertPoint(
				sourceComponent, srcPoint, this.frame.getContentPane());
		
		//Calculate remainTooltipWidth
		remainTooltipWidth = frame.getWidth() - ((int)realPoint.getX() + this.getWidth()+20);
		if(remainTooltipWidth < 0)
		{
			remainTooltipWidth *= -1;
		}else{
			remainTooltipWidth = 0;
		}
		//Calculate align top or bottom.
		alignTop = realPoint.getY()-this.getHeight() -10 > 0;
		
	
		Point locationPoint = null;
		if(alignTop)
		{
			locationPoint = new Point(realPoint.x-remainTooltipWidth, realPoint.y - this.getHeight());
		}
		else
		{
			locationPoint = new Point(realPoint.x-remainTooltipWidth, realPoint.y + sourceComponent.getHeight());
		}
		this.setLocation(locationPoint);
		this.setVisible(true);
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		this.setVisible(false);
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
	}
}