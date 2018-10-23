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
import java.awt.Dimension;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

import com.cats.ui.custome.JIconCheckBox;
import com.cats.utils.IColorFontUtil;

/**
 * @author xblia
 * 2015年10月22日
 */
public class CheckBoxRendererForHeader implements TableCellRenderer, ITableStatusListener
{
 
	private JCheckboxTable parentComponent;
    private JCheckBox checkBox;
    private boolean lastSelectAllStatus = false;
	public CheckBoxRendererForHeader (JCheckboxTable parentComponent) {
		this.parentComponent = parentComponent;
		checkBox = new JIconCheckBox(JIconCheckBox.STYLE.WHITE);
		checkBox.setFont(IColorFontUtil.FONT_BLOK);
		checkBox.setHorizontalAlignment(SwingConstants.CENTER);
		checkBox.setBorderPainted(false);
		checkBox.setForeground(new Color(0xFFFFFF));
		checkBox.setBackground(new Color(0x717071));
		checkBox.setPreferredSize(new Dimension(checkBox.getWidth(), 25));
    }
    
	@Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
    	checkBox.setText("Seq");
        return checkBox;
    }
    
	public JCheckBox getCheckBox()
    {
	    return checkBox;
    }

	@Override
    public void onInfo(int totalItems, int selectedItems, String info)
    {
		boolean isSelectAll = totalItems == selectedItems;
		if(lastSelectAllStatus != isSelectAll)//Improve Refresh Performance
		{
			checkBox.setSelected(isSelectAll);
			parentComponent.getTableHeader().repaint();
			lastSelectAllStatus = isSelectAll;
		}
    }
}