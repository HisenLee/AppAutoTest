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

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.cats.utils.IColorFontUtil;
import com.cats.utils.Utils;

/**
 * @author xiaobolx
 * 2015年11月10日
 */
public class DefaultTableCellRendererEx extends DefaultTableCellRenderer
{
    private static final long serialVersionUID = 1L;
    
    private JPanel mainPanel;
    private JLabel infoLabel;
    public DefaultTableCellRendererEx()
    {
    	infoLabel = new JLabel("", JLabel.CENTER);
    	infoLabel.setPreferredSize(new Dimension(this.getWidth(), 25));
    	infoLabel.setFont(IColorFontUtil.FONT_MICROSOFT);
    	infoLabel.setOpaque(true);
    	infoLabel.setBackground(Color.white);
    	
    	mainPanel = new JPanel();
    	mainPanel.setOpaque(false);
    	mainPanel.setLayout(new BorderLayout(0, 0));
    	
    	JLabel topLabel = new JLabel();
    	topLabel.setPreferredSize(new Dimension(this.getWidth(), 5));
    	
    	mainPanel.add(topLabel, BorderLayout.NORTH);
    	mainPanel.add(infoLabel, BorderLayout.CENTER);
    }

	@Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column)
    {
		if(isSelected && !Utils.isEmpty(value))
		{
			infoLabel.setBackground(IColorFontUtil.COLOR_SELECTED_COLOR_BABY_GREEN);
		}else
		{
			infoLabel.setBackground(Color.WHITE);
		}
		infoLabel.setText(value.toString());
		return mainPanel;
    }
}