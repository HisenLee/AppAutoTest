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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

import com.cats.ui.custome.JIconCheckBox;
import com.cats.utils.IColorFontUtil;

/**
 * @author xblia
 * 2015年10月22日
 */
public class CheckBoxRendererForCell implements TableCellRenderer{
 
    private JCheckBox checkBox;
    private JPanel mainPanel;
	public CheckBoxRendererForCell () {
		checkBox = new JIconCheckBox();
		checkBox.setHorizontalAlignment(SwingConstants.CENTER);
		checkBox.setBackground(Color.WHITE);
		checkBox.setBorderPainted(false);
		
		mainPanel = new JPanel();
    	mainPanel.setOpaque(false);
    	mainPanel.setLayout(new BorderLayout(0, 0));
    	
    	JLabel topLabel = new JLabel();
    	topLabel.setPreferredSize(new Dimension(-1, 5));
    	
    	mainPanel.add(topLabel, BorderLayout.NORTH);
    	mainPanel.add(checkBox, BorderLayout.CENTER);
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
    	String info = value.toString();
    	if(info.equals("-1"))
    	{
    		return new DefaultTableCellRendererEx().getTableCellRendererComponent(table, "", isSelected, hasFocus, row, column);
    	}
    	checkBox.setText(info);
    	checkBox.setSelected(isSelected);
    	if(isSelected)
    	{
    		checkBox.setBackground(IColorFontUtil.COLOR_SELECTED_COLOR_BABY_GREEN);
    	}else
    	{
    		checkBox.setBackground(Color.WHITE);
    	}
        return mainPanel;
    }
}