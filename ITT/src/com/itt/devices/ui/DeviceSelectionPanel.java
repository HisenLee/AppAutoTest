package com.itt.devices.ui;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

import com.cats.ui.custome.JBlokLabel;
import com.cats.ui.custome.table.CheckBoxRendererForCell;
import com.cats.ui.custome.table.CheckBoxRendererForHeader;
import com.cats.ui.custome.table.ComparatorIntegerSeq;
import com.cats.ui.custome.table.ComparatorIntegerOfString;
import com.cats.ui.custome.table.ComparatorString;
import com.cats.ui.custome.table.DefaultTableCellRendererEx;
import com.cats.ui.custome.table.ITableStatusListener;
import com.cats.ui.custome.table.JCheckboxTable;
import com.cats.utils.IColorFontUtil;
import com.cats.utils.Utils;
import com.intel.swing.style.GraceFullyScrollBar;
import com.itt.ITTConstant;
import com.itt.devices.DeviceForSelection;
import com.itt.devices.DeviceForSelection.EnDeviceType;
import com.itt.ui.IUserInputValidChecker;
import com.testserver.util.BatteryInfoGetter;
import com.testserver.util.DeviceOperate;

/**
 * @author xblia
 * 2015年10月19日
 */
public class DeviceSelectionPanel extends JPanel implements IUserInputValidChecker, ITableStatusListener
{
    private static final long serialVersionUID = -8990028443183740208L;
	private JCheckboxTable devicesTables;
	private DeviceTableModel deviceTableModel;
	private List<DeviceForSelection> deviceList = new ArrayList<DeviceForSelection>();
	
	private Timer loadDeviceTimer = new Timer();;
	private List<ITableStatusListener> tableStatusListeners = new ArrayList<ITableStatusListener>();
	
    public DeviceSelectionPanel()
    {
    	initView();
    	//this.genVirtualDevice();
    	initLoadDeviceTimer();
    	this.devicesTables.getSelectionModel().setSelectionInterval(0, 1);
    }
    
    private void initLoadDeviceTimer()
    {
    	loadDeviceTimer.schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				try
                {
	                loadDevice();
                } catch (Exception e)
                {
	                e.printStackTrace();
                }
			}
		}, 1000, 1000);
    }

	private void initView()
    {
    	this.setLayout(new BorderLayout(10, 10));
    	this.setBorder(new EmptyBorder(10, 10, 25, 10));
    	
    	this.deviceTableModel = new DeviceTableModel(deviceList);
    	this.devicesTables = new JCheckboxTable(deviceTableModel);
    	this.initDevicesRowSorter();
    	this.devicesTables.setSelectionMode(ListSelectionModel. MULTIPLE_INTERVAL_SELECTION);
    	this.devicesTables.setSelectionBackground(IColorFontUtil.COLOR_SELECTED_COLOR_BABY_GREEN);
    	final CheckBoxRendererForHeader checkHeaderRenderer = new CheckBoxRendererForHeader(devicesTables);
    	devicesTables.addTableStatusListener(this);
    	devicesTables.addTableStatusListener(checkHeaderRenderer);
    	
    	this.devicesTables.getColumn("Seq").setHeaderRenderer(checkHeaderRenderer);
    	this.devicesTables.getColumn("Seq").setPreferredWidth(20);
    	
    	this.devicesTables.getTableHeader().addMouseListener(new MouseAdapter() {
    	    @Override
    	    public void mouseClicked(MouseEvent e){
    	        if(devicesTables.getColumnModel().getColumnIndexAtX(e.getX())==0){
    	            JCheckBox Checkbox = (JCheckBox)checkHeaderRenderer.getCheckBox();
    	            boolean selectAll = !Checkbox.isSelected();
    	            Checkbox.setSelected(selectAll);
    	            devicesTables.getTableHeader().repaint();
    	            if(selectAll)
    	            {
    	            	devicesTables.selectAll();
    	            }else
    	            {
    	            	devicesTables.clearSelection();
    	            }
    	        }
    	    }
    	});
    	this.devicesTables.getColumn("Seq").setCellRenderer(new CheckBoxRendererForCell());
    	DefaultTableCellRenderer render = new DefaultTableCellRendererEx();
        for(int i = 1; i < this.devicesTables.getColumnCount(); i++)
        {
        	devicesTables.getColumn(devicesTables.getColumnName(i)).setCellRenderer(render);
        }
    	
        JPanel mainPanel = new JPanel(new BorderLayout(5, 5));
        JScrollPane tableScrollPanel = new JScrollPane(devicesTables);
        JScrollBar sb = tableScrollPanel.getVerticalScrollBar();
		sb.setUI(new GraceFullyScrollBar());
    	tableScrollPanel.setBackground(IColorFontUtil.COLOR_GRACE_GRAY_WHITE);
    	tableScrollPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    	
    	JLabel dotLabel = new JBlokLabel("Online Devices");
    	dotLabel.setIcon(new ImageIcon(DeviceSelectionPanel.class.getResource("/icon/icon_1dot.png")));
    	mainPanel.add(dotLabel, BorderLayout.NORTH);
    	mainPanel.add(tableScrollPanel, BorderLayout.CENTER);
    	this.add(mainPanel);
    }
	
	/**
	 * Rows Sorter
	 * "Seq", "Device Name", "Android Version", "CPU Type", "Remaining battery"
	 */
	private void initDevicesRowSorter()
    {
		Comparator<?> []rowsComparators = new Comparator<?>[5];
		rowsComparators[0] = new ComparatorIntegerSeq();
		rowsComparators[1] = new ComparatorString();
		rowsComparators[2] = new ComparatorString();
		rowsComparators[3] = new ComparatorString();
		rowsComparators[4] = new ComparatorIntegerOfString("%");
		devicesTables.initSorter(rowsComparators);
    }

	private void loadDevice() throws Exception
    {
		//Scanning Device and add new device to list.
		ArrayList<String> deviceIds = DeviceOperate.checkdevice();
		List<DeviceForSelection> currenDevList = new ArrayList<DeviceForSelection>();
		if(null != deviceIds && !deviceIds.isEmpty())
		{
			for (String deviceId : deviceIds)
            {
	            String deviceName = DeviceOperate.getDeviceName(deviceId);
	            if(Utils.isEmpty(deviceName))
	            {
	            	continue;
	            }
	            String androidVer = DeviceOperate.getDeviceOSVer(deviceId);
	            String cpuType = DeviceOperate.getCPUType(deviceId);
	            int batteryLevel = BatteryInfoGetter.refreshBatteryInfo(deviceId);
	            String androidImageSerial = DeviceOperate.getDeviceVersion(deviceId);
	            
	            DeviceForSelection androidDevice = new DeviceForSelection(deviceId, deviceName, androidVer, cpuType, androidImageSerial);
	            androidDevice.onBatteryLevel(batteryLevel);
	            currenDevList.add(androidDevice);
	            if(!deviceList.contains(androidDevice))
	            {
	            	this.deviceList.add(deviceTableModel.getValidTotalRows(), androidDevice);
	            }
            }
		}
		
		//Delete Off Line Devices
		Iterator<DeviceForSelection> deviceIt = this.deviceList.iterator();
		DeviceForSelection device;
		while (deviceIt.hasNext())
		{
			device = deviceIt.next();
			if (!currenDevList.contains(device) && device.getDeviceType() != EnDeviceType.VIRTUAL_DEVICE)
			{
				deviceIt.remove();
			}
		}
		
		//Blank Device.
		int needBlankDevice = ITTConstant.DEVICE_FULLPANEL_COUNT - deviceTableModel.getValidTotalRows();
		for (int i = 0; i < needBlankDevice; i++)
		{
			deviceList.add(new DeviceForSelection(null, "", "", "", "", EnDeviceType.BLANK_DEVICE));
		}
		
		//Update Table UI
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				DeviceSelectionPanel.this.devicesTables.updateUI();
			}
		});
    }

	/**
	 * Virtual Android device
	 */
	protected void genVirtualDevice()
    {
		deviceList.add(new DeviceForSelection("aa", "bb", "5.1", "ARM", "fdsafdsasa", EnDeviceType.VIRTUAL_DEVICE));
		deviceList.add(new DeviceForSelection("bb", "bb", "5.1", "ARM", "fdsafdsasa", EnDeviceType.VIRTUAL_DEVICE));
		deviceList.add(new DeviceForSelection("cc", "cc", "5.1", "ARM", "fdsafdsasa", EnDeviceType.VIRTUAL_DEVICE));
		deviceList.add(new DeviceForSelection("dd", "cc", "5.1", "ARM", "fdsafdsasa", EnDeviceType.VIRTUAL_DEVICE));
		deviceList.add(new DeviceForSelection("ee", "dd", "5.1", "ARM", "fdsafdsasa", EnDeviceType.VIRTUAL_DEVICE));
    }

	public List<DeviceForSelection> getSelection()
    {
		List<DeviceForSelection> selectionDevices = new ArrayList<DeviceForSelection>();
		int[] selectionRows = devicesTables.getSelectedRows();
		DeviceForSelection device;
		for (int i : selectionRows)
        {
			device = deviceList.get(devicesTables.convertRowIndexToModel(i));
			if(!Utils.isEmpty(device.getDeviceId()))
			{
				selectionDevices.add(device);
			}
        }
		return selectionDevices;
    }

	@Override
    public String isValidInput()
    {
		if(devicesTables.getSelectedValidItemCount() == 0)
		{
			return "Please select at least one android device";
		}
	    return null;
    }

	public void stopDeviceMonitor()
    {
		try
        {
	        loadDeviceTimer.cancel();
        } catch (Exception e)
        {
	        e.printStackTrace();
        }
    }

	public void addTableStatusListener(ITableStatusListener statusListener)
    {
		tableStatusListeners.add(statusListener);
    }

	@Override
    public void onInfo(int totalItems, int selectedItems, String info)
    {
		for (ITableStatusListener iTableStatusListener : tableStatusListeners)
        {
	        iTableStatusListener.onInfo(totalItems, selectedItems, info);
        }
    }
}