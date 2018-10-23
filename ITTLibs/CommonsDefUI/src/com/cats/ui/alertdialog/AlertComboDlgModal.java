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
 package com.cats.ui.alertdialog;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ListDataListener;

import com.cats.ui.custome.barbtn.BarIconType;
import com.cats.utils.SwingUtil;

/**
 * @author xiaobolx
 * 2016年5月9日
 */

class ComboBoxModelInfo implements ComboBoxModel<String>
{
	private List<String> comboModel;
	private String selectedItem;
	public ComboBoxModelInfo(List<String> comboModel)
    {
		this.comboModel = comboModel;
		if(null != this.comboModel && !this.comboModel.isEmpty())
		{
			this.selectedItem = this.comboModel.get(0);
		}
    }

	@Override
    public int getSize()
    {
        return comboModel.size();
    }

	@Override
    public String getElementAt(int index)
    {
        return comboModel.get(index);
    }

	@Override
    public void addListDataListener(ListDataListener l)
    {
        
    }

	@Override
    public void removeListDataListener(ListDataListener l)
    {
        
    }

	@Override
    public void setSelectedItem(Object anItem)
    {
		this.selectedItem = anItem.toString();
    }

	@Override
    public Object getSelectedItem()
    {
        return this.selectedItem;
    }
}

public class AlertComboDlgModal extends AlertDialog
{
	private int width;
	private int height;
	private String desc;
	private JLabel descLabel;
	private ComboBoxModelInfo comboBoxModel;
	private JComboBox<String> objComboBox;
	/**
	 */
    private static final long serialVersionUID = 1L;
    
	protected AlertComboDlgModal(String title, String desc, List<String> comboBoxData)
    {
		 super(title, "", AlertDialogOptions.OPTION_OK | AlertDialogOptions.OPTION_CANCEL, AlertDialog.ALERT_TYPE_INPUT, BarIconType.TITLE.getType());
		 this.desc = desc;
		 this.descLabel.setText(this.desc);
		 this.comboBoxModel = new ComboBoxModelInfo(comboBoxData);
		 this.objComboBox.setModel(comboBoxModel);
    }
	
	public AlertComboDlgModal(String title, String desc, String message, int iOpts,
            int iDlgType, int barStyle)
    {
		super(title, message, iOpts, iDlgType, barStyle);
		this.desc = desc;
		this.descLabel.setText(this.desc);
    }
	
	@Override
    protected void initSize()
    {
		width = 400;
	    height = 150;
		this.setSize(width, height);
		SwingUtil.centerWindow(width, height, this);
    }

	@Override
    protected void layoutAlert()
    {
		this.descLabel = new JLabel();
		this.objComboBox = new JComboBox<String>();
		JPanel mainPanel = new JPanel();
		JPanel mainCenterPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		GridBagConstraints bagConstraints = new GridBagConstraints();
		GridBagLayout bagLayout = new GridBagLayout();
		mainCenterPanel.setLayout(bagLayout);
    	bagConstraints.gridwidth = 0;
    	bagConstraints.fill = GridBagConstraints.BOTH;
    	bagConstraints.weightx = 1;
    	bagConstraints.insets = new Insets(5, 15, 5, 15);
    	mainCenterPanel.add(new JLabel(), bagConstraints);
    	mainCenterPanel.add(descLabel, bagConstraints);
    	mainCenterPanel.add(objComboBox, bagConstraints);
    	
    	mainPanel.add(mainCenterPanel, BorderLayout.CENTER);
    	mainPanel.add(genLayoutBottom(), BorderLayout.SOUTH);
    	this.add(mainPanel, BorderLayout.CENTER);
    }
	
	private String getSelectString()
    {
		Object selectedObj = comboBoxModel.getSelectedItem();
		if(null == selectedObj)
		{
			return null;
		}
		return selectedObj.toString();
    }

	public static String showInfo(String title,String desc, List<String> comboModel)
    {
		AlertComboDlgModal dlg = new AlertComboDlgModal(title, desc, comboModel);
    	dlg.setVisible(true);
    	if(iResult == AlertDialogOptions.OPTION_OK)
    	{
    		return dlg.getSelectString();
    	}
    	return null;
    }
}