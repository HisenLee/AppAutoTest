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
 package com.cats.ui.optiondlg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.LineBorder;

import com.cats.ui.custome.JIconCheckBox;
import com.cats.utils.IColorFontUtil;

public class OptionListCellRenderer extends JPanel implements ListCellRenderer<String>
{
    private static final long serialVersionUID = 1L;
    
    private JCheckBox selectBox;
	private JLabel infoLabel;
	
	private Color originColor = Color.white;
	private Color selectedColor = IColorFontUtil.COLOR_SELECTED_COLOR_BABY_GREEN;
	public OptionListCellRenderer()
    {
		selectBox = new JIconCheckBox();
		infoLabel = new JLabel("", JLabel.CENTER);
		infoLabel.setOpaque(true);
		selectBox.setBackground(originColor);
		infoLabel.setBackground(originColor);
		
		this.setLayout(new BorderLayout(0, 0));
		this.add(selectBox, BorderLayout.WEST);
		this.add(infoLabel, BorderLayout.CENTER);
    }

	@Override
	public Component getListCellRendererComponent(JList<? extends String> list,
	        String value, int index, boolean isSelected, boolean cellHasFocus)
	{
		this.setBorder(new LineBorder(selectedColor, 1));
		this.setPreferredSize(new Dimension(-1, 30));
		this.selectBox.setSelected(isSelected);
		this.infoLabel.setText(value);
		this.infoLabel.setBackground(isSelected ? selectedColor:originColor);
		this.selectBox.setBackground(isSelected ? selectedColor:originColor);
		return this;
	}
	
}