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
 package com.cats.ui.custome.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * @author xiaobolx
 * 2015年11月10日
 */
public class DefaultTableHeaderCellRendererEx extends DefaultTableCellRenderer
{

    private static final long serialVersionUID = -6260252053505806317L;
	private JLabel label;
    private ImageIcon sortUpIcon = new ImageIcon(DefaultTableHeaderCellRendererEx.class.getResource("/com/cats/ui/custome/table/sort_up.png"));
    private ImageIcon sortDownIcon = new ImageIcon(DefaultTableHeaderCellRendererEx.class.getResource("/com/cats/ui/custome/table/sort_down.png"));
    private int clickedColumn = -1;
    private boolean isUpIcon = false;
    
    public int getSelectColumn()
	{
		return clickedColumn;
	}

	public void setclickedColumn(int clickColumn)
	{
		if(this.clickedColumn != clickColumn)
		{
			this.clickedColumn = clickColumn;
			isUpIcon = false;
		}
		isUpIcon = !isUpIcon;
		this.repaint();
	}

	public DefaultTableHeaderCellRendererEx()
    {
    	label = new JLabel("", JLabel.CENTER);
    	label.setOpaque(true);
    	label.setBackground(new Color(0x717071));
    	label.setForeground(new Color(0xFFFFFF));
    	label.setFont(new Font("Segoe UI", Font.BOLD, 12));
    	label.setHorizontalAlignment(SwingConstants.CENTER);
    }
    
	@Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column)
    {
		label.setText(value.toString());
		
		if(clickedColumn != -1)
		{
			if(column == clickedColumn)
			{
				if(isUpIcon)
				{
					label.setIcon(sortUpIcon);
				}
				else
				{
					label.setIcon(sortDownIcon);
				}
			}else
			{
				label.setIcon(null);
			}
		}
		return label;
    }
}