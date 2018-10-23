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
 package com.cats.version.client.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.alibaba.fastjson.JSON;
import com.cats.border.BorderFactoryEx;
import com.cats.ui.alertdialog.AlertDialog;
import com.cats.ui.custome.JButtonEx;
import com.cats.ui.graceframe.IGraceFullyFrameBase;
import com.cats.version.VersionInfoDetail;
import com.cats.version.client.IStartupSubProcCallBack;
import com.cats.version.client.IWaitingStreamWithTimeout;
import com.cats.version.client.ListenerSubProcessor;
import com.cats.version.msg.IMessageDef;
import com.cats.version.msg.IMessageVersionCheckRsp;
import com.cats.version.msg.IMessageVersionUpdateReq;
import com.cats.version.msg.IMessageVersionUpdateRsp;
import com.cats.version.utils.IVersionConstant;
import com.cats.version.utils.Utils;
import com.intel.swing.style.GraceFullyScrollBar;

/**
 * @author xblia2 Jun 10, 2015
 */
public class UpgradSoftUI extends IGraceFullyFrameBase implements ActionListener, IStartupSubProcCallBack
{
	private static final long serialVersionUID = 6922348563914110145L;
	
	public static final String UPGRADE_DEAMON = "upgradeDeamon.jar";
	
	private int width;
	private int height;

	private JLabel labelTitle;
	private JTextPane textPane;
	private JButton btnUpgrade;
	private JButton btnRemindMeLater;
	
	private IMessageVersionCheckRsp checkRsp;
	private String upgradeDeamonTempName = "";

	public UpgradSoftUI(IMessageVersionCheckRsp checkRsp)
	{
		super(checkRsp.getSoftName() + "-upgrade@intel-cats", null);
		this.checkRsp = checkRsp;
		width = 400;
		height = 380;
		this.setTitle(checkRsp.getSoftName() + "-upgrade@intel-cats");
		this.setIconImage(new ImageIcon(UpgradSoftUI.class.getResource("icon.png")).getImage());
		this.setSize(width, height);
		this.setAlwaysOnTop(true);
		Utils.centerWindow(width, height, this);
		initView();
		initViewData(checkRsp);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void initView()
	{
		this.labelTitle = new JLabel("Update:");
		this.textPane = new JTextPane();
		this.textPane.setEditable(false);
		this.btnUpgrade = new JButtonEx("Upgrade");
		this.btnRemindMeLater = new JButtonEx("Remind me later");
		this.textPane.setBackground(btnUpgrade.getBackground());
		{//Line spacing
			StyledDocument doc = textPane.getStyledDocument();
			MutableAttributeSet attr = new SimpleAttributeSet();
			StyleConstants.setLineSpacing(attr, 0.3f);
			doc.setParagraphAttributes(0, doc.getLength() - 10, attr, false);
		}

		this.labelTitle.setPreferredSize(new Dimension(-1, 50));
		this.btnUpgrade.setPreferredSize(new Dimension(-1, 35));
		this.btnRemindMeLater.setPreferredSize(new Dimension(-1, 35));
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(new EmptyBorder(5, 10, 5, 10));
		mainPanel.add(labelTitle, BorderLayout.NORTH);
		JScrollPane scrollTextPanel = new JScrollPane(textPane);
		scrollTextPanel.getVerticalScrollBar().setUI(new GraceFullyScrollBar());
		scrollTextPanel.setBorder(BorderFactoryEx.getEtchedTitleBorder("Info"));
		mainPanel.add(scrollTextPanel, BorderLayout.CENTER);

		JPanel southPanel = new JPanel();
		southPanel.setBorder(new EmptyBorder(5, 0, 5, 0));
		GridBagLayout bagLayout = new GridBagLayout();
		GridBagConstraints bagConstraints = new GridBagConstraints();
		southPanel.setLayout(bagLayout);;
		bagConstraints.fill = GridBagConstraints.BOTH;
		bagConstraints.insets = new Insets(1, 3, 1, 3);
		bagConstraints.gridx = 0;
		bagConstraints.gridy = 0;
		bagConstraints.weightx = 1;
		southPanel.add(btnRemindMeLater, bagConstraints);
		bagConstraints.gridx = 1;
		bagConstraints.gridy = 0;
		bagConstraints.weightx = 1;
		southPanel.add(btnUpgrade, bagConstraints);
		mainPanel.add(southPanel, BorderLayout.SOUTH);
		this.add(mainPanel, BorderLayout.CENTER);

		this.btnUpgrade.addActionListener(this);
		this.btnRemindMeLater.addActionListener(this);
	}

	private void initViewData(IMessageVersionCheckRsp checkRsp)
	{
		List<VersionInfoDetail> details = checkRsp.getVersionInfoDetail();
		if(details == null)
		{
			addVersionInfoTitle("Sorry, Upgrade application have exception, you can contact developer(xiaobolx)");
			return;
		}
		StringBuffer titleBuffer = new StringBuffer();
		{
			titleBuffer.append("<html><font color='black'>");
			titleBuffer.append("Upgrade: " + checkRsp.getCurrVersionName());
			titleBuffer.append(" to <font color='blue'>" + checkRsp.getLatestVersionName() + "</font><br/>");
			titleBuffer.append("</font></html>");
		}
		this.labelTitle.setText(titleBuffer.toString());
		
		for (VersionInfoDetail versionInfoDetail : details)
        {
			addVersionInfoTitle(versionInfoDetail.getTitle());
			List<String> detailList = versionInfoDetail.getDetail();
			for (String detailInfo : detailList)
            {
				addVersionInfoDetail(detailInfo);
            }
        }
	}

	private void addVersionInfoTitle(String strTitle)
	{
		Font font = new Font("Microsoft Yahei", Font.BOLD, 14);
		Color bgColor = null;
		Color forcegColor = Color.black;
		setText(strTitle, font, bgColor, forcegColor);
	}

	private void addVersionInfoDetail(String strTitle)
	{
		Font font = new Font("Microsoft Yahei", Font.PLAIN, 13);
		Color bgColor = null;
		Color forcegColor = Color.black;
		setText(strTitle, font, bgColor, forcegColor);
	}

	private void setText(String str, Font font, Color backgroup,
	        Color foreground)
	{
		SimpleAttributeSet set = new SimpleAttributeSet();
		if (backgroup != null)
			StyleConstants.setBackground(set, backgroup);
		if (foreground != null)
			StyleConstants.setForeground(set, foreground);
		if (font != null)
		{
			StyleConstants.setFontSize(set, font.getSize());
			StyleConstants.setFontFamily(set, font.getFontName());
			if (font.isBold())
			{
				StyleConstants.setBold(set, true);
			}
			if (font.isItalic())
			{
				StyleConstants.setItalic(set, true);
			}
		}
		try
		{
			Document doc = textPane.getDocument();
			doc.insertString(doc.getLength(), str + IVersionConstant.LINE, set);
		} catch (BadLocationException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == btnUpgrade)
		{
			beginUpgradeRequest();
			this.setVisible(false);
		} else if (e.getSource() == btnRemindMeLater)
		{
			this.setVisible(false);
			this.dispose();
		}
		((JButton)e.getSource()).setEnabled(false);
	}

	private void beginUpgradeRequest()
	{
		new Thread()
		{
			public void run()
			{
				IMessageVersionUpdateReq updateReq = new IMessageVersionUpdateReq();
				updateReq.setMsgIdentified(IMessageDef.MSGIDENTIFIED_VERSIONSERVICE);
				updateReq.setMsgid(IMessageDef.genMsgId());
				updateReq.setMsgType(IMessageDef.MSGTYPE_VERSION_UPDATE_REQ);
				updateReq.setSoftName(checkRsp.getSoftName());
				String updateJsonReq = JSON.toJSONString(updateReq);
				
				String strRsp = Utils.postMsgAndGet(updateJsonReq);
				if(strRsp!= null && !strRsp.isEmpty())
				{
					IMessageVersionUpdateRsp updateRsp = JSON.parseObject(strRsp, IMessageVersionUpdateRsp.class);
					if(updateRsp != null && updateRsp.getMsgResult().equals(IMessageDef.SUCC))
					{
						if(updateRsp.getResult().equals(IMessageDef.SUCC))
						{
							beginUpgrade(strRsp);
						}
					}else
					{
						System.out.println("Result control fail: " + strRsp);
					}
				}
			}

		}.start();
	}
	
	private void beginUpgrade(String strRsp)
    {
		if(!upgradeBefore())
		{
			return;
		}
		String fileName = IVersionConstant.TEMPFILE_PREFIX + System.currentTimeMillis() + ".txt";
		String strupgradeProfile = Utils.getWorkSpace() + File.separator + fileName;
		File file = new File(strupgradeProfile);
		try
        {
	        file.createNewFile();
	        FileOutputStream fout = new FileOutputStream(file);
	        fout.write(strRsp.getBytes(Charset.forName("UTF-8")));
	        fout.flush();
	        fout.close();
        } catch (IOException e)
        {
	        e.printStackTrace();
	        return;
        }
		//StartDeamon process
		startDeamonProc(fileName);
    }

	private void startDeamonProc(String fileName)
    {
		int iPort = new ListenerSubProcessor(this).startListener();
	    List<String> cmdList = new ArrayList<String>();
		cmdList.add("java");
		cmdList.add("-jar");
		cmdList.add(upgradeDeamonTempName);
		cmdList.add(fileName);
		cmdList.add(upgradeDeamonTempName);
		cmdList.add(String.valueOf(iPort));
		ProcessBuilder builder = new ProcessBuilder(cmdList);
		try
        {
			Process process = builder.start();
			new IWaitingStreamWithTimeout(process.getErrorStream()).registResultHandlerAndStart(this, 3000);
        } catch (Exception e)
        {
        	JOptionPane.showMessageDialog(null, Utils.getException(e));
	        AlertDialog.show(Utils.getException(e));
        }
    }

	private boolean upgradeBefore()
    {
		upgradeDeamonTempName = IVersionConstant.TEMPFILE_PREFIX + "UpgradeDeamon" + System.currentTimeMillis() + ".jar";
		File upgradeDaemonFile = new File(Utils.getWorkSpace() + File.separator + UPGRADE_DEAMON);
		File upgradDaemonTempFile = new File(Utils.getWorkSpace() + File.separator + upgradeDeamonTempName);
		if(!upgradeDaemonFile.exists())
		{
			JOptionPane.showMessageDialog(null, upgradeDaemonFile.getAbsoluteFile() + " Not found, upgrade fail...", title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		Utils.copyFile(upgradeDaemonFile, upgradDaemonTempFile);
		if(!upgradDaemonTempFile.exists())
		{
			JOptionPane.showMessageDialog(null, upgradDaemonTempFile.getAbsolutePath() + " Not found, upgrade fail...", title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
	    return true;
    }

	public static void main(String[] args)
	{
		IMessageVersionCheckRsp checkRsp = new IMessageVersionCheckRsp();
		new UpgradSoftUI(checkRsp);
	}

	@Override
    public void onSubProcResult(String result)
    {
		if(null != result)
		{
			AlertDialog.show(result);
		}
		System.exit(0);
    }
	
	@Override
    public String getIconRelativePath()
    {
	    return "/com/cats/version/client/ui/icon_small.png";
    }
}
