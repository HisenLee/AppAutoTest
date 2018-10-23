package com.itt.ui;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import com.cats.ui.alertdialog.AlertDialog;
import com.cats.ui.alertdialog.AlertDialogOptions;
import com.cats.ui.alertdialog.InfoDialogModal;
import com.cats.ui.custome.menu.MenuItemEx;
import com.itt.ContentGetterFromFile;

/**
 * @author xiaobolx
 * 2015年11月19日
 */
public class ITTMainMenu implements ActionListener
{
	private JMenuItem helpDocMenuItem;
	private JMenuItem aboutMenuItem;
	public void showMenu(Component srcComponent, Component destComponent, Point point)
    {
		JPopupMenu popMenu = new JPopupMenu("Interactive test tool");
		helpDocMenuItem = new MenuItemEx("Help", this);
		helpDocMenuItem.setIcon(new ImageIcon(ITTMainMenu.class.getResource("/icon/menu/help.png")));
		
		aboutMenuItem = new MenuItemEx("About", this);
		aboutMenuItem.setIcon(new ImageIcon(ITTMainMenu.class.getResource("/icon/menu/about.png")));
		
		popMenu.add(helpDocMenuItem);
		popMenu.add(aboutMenuItem);
		
		Point destPoint = SwingUtilities.convertPoint(srcComponent, point, destComponent);
		popMenu.show(destComponent, (int)destPoint.getX(), (int)destPoint.getY());
    }

	/**
	 * This guy is too lazy to written the document
	 */
	@Override
    public void actionPerformed(ActionEvent e)
    {
		if(e.getSource() == helpDocMenuItem)
		{
			AlertDialog.show("Help", "Nothing in the world!", AlertDialogOptions.OPTION_OK, AlertDialog.ALERT_TYPE_MESSAGE);
		}
		else if(e.getSource() == aboutMenuItem)
		{
			InfoDialogModal.showInfo("About", ContentGetterFromFile.getLicenseInfo(), AlertDialogOptions.OPTION_OK, -1);
		}
    }
}
