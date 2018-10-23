package com.itt.devices.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.itt.devices.DeviceForSelection;
import com.itt.devices.listener.IDeviceStatusListener;

/**
 * @author xblia
 * 2015年10月27日
 */
public class DeviceStatusPanel extends JPanel implements IDeviceStatusListener
{
    private static final long serialVersionUID = 1L;
    private JLabel statusLabel;
    private JLabel batteryLevelLabel;
    private static ImageIcon ICON_ONLINE = new ImageIcon(DeviceStatusPanel.class.getResource("/icon/status_online.png"));
    private static ImageIcon ICON_OFFLINE = new ImageIcon(DeviceStatusPanel.class.getResource("/icon/status_offline.png"));
    private static ImageIcon ICON_BATTERY = new ImageIcon(DeviceStatusPanel.class.getResource("/icon/icon_battery.png"));
    
    
	public DeviceStatusPanel(DeviceForSelection deviceForSelection)
    {
		this.statusLabel = new JLabel("", JLabel.CENTER);
		this.statusLabel.setIcon(ICON_ONLINE);
		
		this.batteryLevelLabel = new JLabel(deviceForSelection.getBatteryLevel() + "%", JLabel.CENTER);
		this.batteryLevelLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		this.batteryLevelLabel.setPreferredSize(new Dimension(35, 20));
		this.batteryLevelLabel.setIcon(ICON_BATTERY);
		this.batteryLevelLabel.setVerticalTextPosition(JLabel.CENTER); 
		this.batteryLevelLabel.setHorizontalTextPosition(JLabel.CENTER);
		
		this.setOpaque(false);
		this.setLayout(new BorderLayout(2, 5));
		this.add(statusLabel, BorderLayout.WEST);
		this.add(batteryLevelLabel, BorderLayout.CENTER);
    }
	
	@Override
    public String getDeviceId()
    {
	    return null;
    }

	@Override
    public void onLine(final boolean isOnline)
    {
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				statusLabel.setIcon(isOnline ? ICON_ONLINE : ICON_OFFLINE);
			}
		});
    }

	@Override
    public void onBatteryLevel(final int level)
    {
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				batteryLevelLabel.setText(level + "%");
			}
		});
    }
	
	/*public static void main(String[] args)
    {
	    JFrame frame = new JFrame();
	    frame.setSize(500, 400);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    JPanel topPanel = new JPanel();
	    topPanel.setLayout(new BorderLayout());
	    topPanel.add(new DeviceStatusPanel(), BorderLayout.EAST);
	    
	    frame.setLayout(new BorderLayout());
	    frame.add(topPanel, BorderLayout.NORTH);
	    frame.setVisible(true);
    }*/
}
