package com.itt.ui.custome;

import java.awt.event.MouseListener;
import java.net.URL;

import javax.swing.ImageIcon;

import com.cats.ui.custome.iconbtn.AngleButton;

/**
 * @author xiaobolx
 * 2015年10月30日
 */
public class AngleButtonOfDevice extends AngleButton implements MouseListener
{
    private static final long serialVersionUID = -319192911384600429L;
    
    public AngleButtonOfDevice(String name, boolean enable)
	{
    	super(name);
		this.setEnabled(enable);
	}
	
	public void changeIcons(String basePath, String testResult)
	{
		URL url = AngleButtonOfDevice.class.getResource(basePath +"res_" + testResult + ".png");
		ImageIcon icon = null;
		if(null != url)
		{
			icon = new ImageIcon(url);
		}
		this.setAngleBtnIcon(icon);
		this.repaint();
	}
}
