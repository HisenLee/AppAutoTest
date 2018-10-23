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
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.cats.ui.custome.barbtn.BarIconType;
import com.cats.ui.graceframe.ICON_STYLE;
import com.cats.ui.graceframe.IGraceFullyDialogBase;

/**
 * @author xblia
 * 2015年7月3日
 */
public class AlertDialog extends IGraceFullyDialogBase implements ClickResCallback
{
    private static final long serialVersionUID = 1L;
    
    public static final int ALERT_TYPE_WARNING = 0;
    public static final int ALERT_TYPE_MESSAGE = 1;
    public static final int ALERT_TYPE_INPUT = 3;
    public static final Font font = new Font("Segoe UI", Font.PLAIN, 14);
    private static String iconResource = null;
    
    private static IComstomeAlertDefine alertDef;
    
    private JLabel label;
    private ImageIcon icon;
    
    private String title;
    protected String message;
    protected int iOptions = 1;
    protected static int iResult = -1;
    
    protected AlertDialog(String title, String message, int iOpts, int iDlgType)
    {
    	super(title, ICON_STYLE.ITT_BANNER_SMALLER, BarIconType.TITLE.getType() | BarIconType.CLOSE.getType());
    	this.title = title;
    	this.message = message;
    	
    	initView(iOpts, iDlgType);
    }
    
    protected AlertDialog(String title, String message, int iOpts, int iDlgType, int barStyle)
    {
    	super(title, ICON_STYLE.ITT_BANNER_SMALLER, barStyle);
    	this.title = title;
    	this.message = message;
    	
    	initView(iOpts, iDlgType);
    }
    
	protected ImageIcon genDialogIcon(int iDialogType)
	{
	    if(null != alertDef)
	    {
	        Object[][] typeDef = alertDef.defineAlertType();
	        for (int i = 0; i < typeDef.length; i++)
            {
	            int id = (Integer)typeDef[i][0];
	            if(id == iDialogType)
	            {
	                Class<?> resClass = (Class<?>)typeDef[i][1];
	                String iconName = (String)typeDef[i][2];
	                return new ImageIcon(resClass.getResource(iconName));
	            }
            }
	    }
		switch (iDialogType)
		{
		case ALERT_TYPE_MESSAGE:
			return new ImageIcon(AlertDialog.class.getResource("message.png"));
		case ALERT_TYPE_WARNING:
			return new ImageIcon(AlertDialog.class.getResource("warning.png"));
		default:
			return new ImageIcon(AlertDialog.class.getResource("message.png"));
		}
	}

	private void initView(int iOpts, int iDlgType)
    {
		this.setModal(true);
    	this.setTitle(this.title);
    	
    	this.iOptions = (iOpts == -1 ? AlertDialogOptions.OPTION_OK : iOpts);
    	icon = genDialogIcon(iDlgType);
    	if(null != icon)
    	{
    		this.setIconImage(icon.getImage());
    	}
    	
    	initSize();
    	layoutAlert();
    }

	protected void initSize()
    {
    	FontMetrics fm = this.getFontMetrics(font);
    	int w = fm.stringWidth(message) + 200;
    	int h = fm.getHeight() + 120;
    	this.setSize(w, h);
    	this.setLocation((int)((Toolkit.getDefaultToolkit().getScreenSize().width - w)/2),
    			(int)((Toolkit.getDefaultToolkit().getScreenSize().height - h)/2));
    }

	protected void layoutAlert()
    {
		label = new JLabel(message, icon, JLabel.CENTER);
    	label.setBorder(new EmptyBorder(15, 5, 5, 5));
    	label.setFont(font);
    	
    	JPanel mainPanel = new JPanel();
    	mainPanel.setLayout(new BorderLayout());
    	
    	mainPanel.add(label, BorderLayout.CENTER);
    	mainPanel.add(genLayoutBottom(), BorderLayout.SOUTH);
    	
    	this.add(mainPanel, BorderLayout.CENTER);
    }

	protected JPanel genLayoutBottom()
    {
		JPanel bottomPanel = new JPanel(new FlowLayout());
	    Object [][]idToBtn = AlertDialogOptions.BTN_DEFINE;
    	layoutBtn(bottomPanel, idToBtn);
    	if(null != alertDef)
    	{
    	    layoutBtn(bottomPanel, alertDef.defineAlertDlgBtn());
    	}
    	return bottomPanel;
    }

	protected void layoutBtn(JPanel parentPanel, Object [][]idToBtn)
    {
	    for (int i = 0; i < idToBtn.length; i++)
        {
	        int id = (Integer)idToBtn[i][0];
	        if((iOptions&id) != 0)
	        {
	            String btnName = (String)idToBtn[i][1];
	            JAlertButton btn = new JAlertButton(btnName);
	            btn.injectCallBackAndInfo(this, id);
	            parentPanel.add(btn);
	        }
        }
    }
	
	@Override
    public String getIconRelativePath()
    {
		if(null != iconResource)
		{
			return iconResource;
		}
	    return "/com/cats/ui/alertdialog/icon.png";
    }
	
	public static final void injectIconResult(String iconResource)
	{
		AlertDialog.iconResource = iconResource;
	}

	public static int show(String title, String message, int iOptions, int iAlertType)
    {
		iResult = -1;
    	AlertDialog dlg = new AlertDialog(title, message, iOptions, iAlertType);
    	dlg.setVisible(true);
    	return iResult;
    }
	
	public static int show(String title, String message)
    {
		iResult = -1;
    	AlertDialog dlg = new AlertDialog(title, message, AlertDialogOptions.OPTION_OK, ALERT_TYPE_MESSAGE);
    	dlg.setVisible(true);
    	return iResult;
    }
	
	public static int show(String message)
    {
		return show("Warning", message);
    }
	
	public static void insertDefineBtn(IComstomeAlertDefine btnDef)
	{
	    alertDef = btnDef;
	}

	@Override
    public void setVal(int iVal)
    {
		iResult = iVal;
		this.setVisible(false);
		this.dispose();
    }
}
