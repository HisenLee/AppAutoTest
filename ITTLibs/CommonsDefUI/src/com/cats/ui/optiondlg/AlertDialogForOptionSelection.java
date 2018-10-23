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
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;

import com.cats.ui.custome.JBgColorButton;
import com.cats.utils.SwingUtil;

/**
 * @author xblia 2015年10月28日
 */
public class AlertDialogForOptionSelection extends JDialog implements  JDialogParent, 
        MouseListener,WindowFocusListener
{

	private static final long serialVersionUID = 1L;

	private int width;
	private int height;
	private String[] message;
	private String value;
	private String failReason;
	private JList<String> resultList;
	private InputDialog inputDialog;
	private ResultCallback resultCallback;
	private boolean isMouseOver = false;

	private AlertDialogForOptionSelection(ResultCallback resultCallback, String defaultVal, String defaultFailReason, String[] message, int x, int y)
	{
		this.resultCallback = resultCallback;
		this.value = defaultVal;
		this.failReason = defaultFailReason;
		this.message = message;
		this.width = 150;
		this.height = 90;

		//this.setModal(true);
		this.setUndecorated(true);
		this.setAlwaysOnTop(true);
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		this.setSize(width, height);
		
		if(-1 == x || -1 == y)
		{
			SwingUtil.centerWindow(width, height, this);
		}else
		{
			this.setLocation(x, y);
		}
		initView();
	}

	private void initView()
	{
		this.resultList = new JList<String>(this.message);
		this.resultList.setCellRenderer(new OptionListCellRenderer());
		this.setLayout(new BorderLayout());
		this.add(resultList);

		if(null != value) resultList.setSelectedValue(this.value, false);
		this.resultList.addMouseListener(this);
		this.addWindowFocusListener(this);

	}
	
	public static String[] show(ResultCallback resultCallback, String[] message, String defaultSelectitem, String failReason, int x, int y)
	{
		AlertDialogForOptionSelection dlg = new AlertDialogForOptionSelection(resultCallback, defaultSelectitem, failReason, 
		        message, x, y);
		dlg.setVisible(true);
		return new String[]{dlg.value, dlg.failReason};//This Result value invalid, because this is not modal dialog.
	}

	public static void main(String[] args)
	{
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		SwingUtil.centerWindow(500, 500, frame);
		frame.setLayout(new FlowLayout());
		JBgColorButton btn = new JBgColorButton("Open");
		frame.add(btn);
		frame.setVisible(true);
		btn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				AlertDialogForOptionSelection.show(new ResultCallback()
				{
					@Override
					public void onResult(String result, String lParam, String wParam)
					{
						System.err.println("OnResult: " + result + "  " +lParam + "  " + wParam);
					}
				}, new String[]{ "Pass", "Fail", "Block" }, null, null, -1, -1);
			}
		});
	}

	@Override
	public void mouseClicked(MouseEvent arg0)
	{
	}

	@Override
	public void mouseEntered(MouseEvent arg0)
	{
		isMouseOver = true;
	}

	@Override
	public void mouseExited(MouseEvent arg0)
	{
		isMouseOver = false;
	}

	@Override
	public void mousePressed(MouseEvent arg0)
	{
	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		String value = this.resultList.getSelectedValue();
		if(value != null && this.value != null && !this.value.equals(value))
		{
			this.failReason = "";
		}
		
		if (null != value)
		{
			this.value = value;
			if(null != inputDialog)
			{
				inputDialog.dispose();
			}
			if(!value.toLowerCase().equals("pass"))
			{
				Point point = new Point();
				point.x = (int)(this.getLocation().getX() + this.getWidth());
				point.y = (int)MouseInfo.getPointerInfo().getLocation().getY();
				
				
				inputDialog = new InputDialog(this, point, failReason);
				inputDialog.setVisible(true);
			}else
			{
				this.dispose();
			}
		}
	}

	@Override
    public void dispose(String result, String wParam, String lParam, CLOSE_TYPE closeType)
    {
		if(closeType == CLOSE_TYPE.BY_LOSTFOCUS && isMouseOver)
		{
			return;
		}
		this.failReason = wParam;
		this.dispose();
		this.resultCallBack();
    }
	
	@Override
    public void windowGainedFocus(WindowEvent e)
    {
    }

	@Override
    public void windowLostFocus(WindowEvent e)
    {
		if(null != inputDialog)
		{
			if(inputDialog.isVisible())
			{
				return;
			}
		}
		this.dispose();
		resultCallBack();
    }
	
	private void resultCallBack()
	{
		if(null != resultCallback)
		{
			resultCallback.onResult(value, failReason, null);
		}
	}

}
