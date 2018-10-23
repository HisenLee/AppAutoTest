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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class TestTableFilter
{
	public static void main(String args[])
	{
		JFrame frame = new JFrame("JTable的过滤测试");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final Object rows[][] =
		{
		{ "王明", "中国", 44 },
		{ "姚明", "中国", 25 },
		{ "赵子龙", "西蜀", 1234 },
		{ "曹操", "北魏", 2112 },
		{ "Bill Gates", "美国", 45 },
		{ "Mike", "英国", 33 } };
		final String columns[] =
		{ "姓名", "国籍", "年龄" };
		TableModel model = new AbstractTableModel()
		{
            private static final long serialVersionUID = 1L;

			@Override
			public Object getValueAt(int rowIndex, int columnIndex)
			{
				return rows[rowIndex][columnIndex];
			}
			
			@Override
			public int getRowCount()
			{
				return rows.length;
			}
			
			@Override
			public int getColumnCount()
			{
				return 3;
			}

			@Override
            public String getColumnName(int column)
            {
	            return columns[column];
            }
			
		};
		
		final JTable table = new JTable(model);
		final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(
		        model);
		table.setRowSorter(sorter);
		JScrollPane pane = new JScrollPane(table);
		frame.add(pane, BorderLayout.CENTER);
		JPanel panel = new JPanel(new BorderLayout());
		JLabel label = new JLabel("过滤");
		panel.add(label, BorderLayout.WEST);
		final JTextField filterText = new JTextField("");
		panel.add(filterText, BorderLayout.CENTER);
		frame.add(panel, BorderLayout.NORTH);
		JButton button = new JButton("过滤");
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String text = filterText.getText();
				if (text.length() == 0)
				{
					sorter.setRowFilter(null);
				} else
				{
					sorter.setRowFilter(RowFilter.regexFilter(text));
				}
			}
		});
		frame.add(button, BorderLayout.SOUTH);
		frame.setSize(300, 250);
		frame.setVisible(true);
	}
}