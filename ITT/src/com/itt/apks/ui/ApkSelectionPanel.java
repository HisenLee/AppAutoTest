package com.itt.apks.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

import com.cats.ui.custome.JBgColorButton;
import com.cats.ui.custome.JBlokLabel;
import com.cats.ui.custome.table.CheckBoxRendererForCell;
import com.cats.ui.custome.table.CheckBoxRendererForHeader;
import com.cats.ui.custome.table.ComparatorDateTime;
import com.cats.ui.custome.table.ComparatorFileSize;
import com.cats.ui.custome.table.ComparatorIntegerSeq;
import com.cats.ui.custome.table.ComparatorString;
import com.cats.ui.custome.table.DefaultTableCellRendererEx;
import com.cats.ui.custome.table.ITableStatusListener;
import com.cats.ui.custome.table.JCheckboxTable;
import com.cats.utils.DateUtils;
import com.cats.utils.IColorFontUtil;
import com.cats.utils.Utils;
import com.intel.swing.style.GraceFullyScrollBar;
import com.itt.ITTConstant;
import com.itt.preferences.IPropertyKeys;
import com.itt.preferences.ITTProperties;
import com.itt.preferences.UserParam;
import com.itt.ui.IUserInputValidChecker;

/**
 * @author xblia
 * 2015年10月19日
 */
public class ApkSelectionPanel extends JPanel implements ActionListener, IUserInputValidChecker, ITableStatusListener
{
    private static final long serialVersionUID = -8990028443183740208L;
	private JCheckboxTable apkTable;
	private ApksTableModel apksTableModel;
	private List<FileForSelection> fileList = new ArrayList<FileForSelection>();
    private JTextField apkFolderField;
    private JButton browseApkBtn;
    
    private List<ITableStatusListener> tableStatusListeners = new ArrayList<ITableStatusListener>();
    
    public ApkSelectionPanel()
    {
    	initView();
    }
    
    private void initView()
    {
    	this.setLayout(new BorderLayout(10, 10));
    	this.setBorder(new EmptyBorder(10, 10, 10, 10));
    	this.apksTableModel = new ApksTableModel(fileList);
    	this.apkTable = new JCheckboxTable(apksTableModel);
    	this.initApkTableRowSorter();
    	this.apkTable.setRowSelectionAllowed(true);
    	this.apkTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    	final CheckBoxRendererForHeader checkRenderer = new CheckBoxRendererForHeader(apkTable);
    	this.apkTable.addTableStatusListener(this);
    	this.apkTable.addTableStatusListener(checkRenderer);
    	
    	this.apkTable.getColumn("Seq").setHeaderRenderer(checkRenderer);
    	this.apkTable.getColumn("Seq").setPreferredWidth(20);
    	this.apkTable.getColumn("File Name").setPreferredWidth(180);
    	
    	this.apkTable.getTableHeader().addMouseListener(new MouseAdapter() {
    	    @Override
    	    public void mouseClicked(MouseEvent e){
    	        if(apkTable.getColumnModel().getColumnIndexAtX(e.getX())==0){
    	            JCheckBox Checkbox = (JCheckBox)checkRenderer.getCheckBox();
    	            boolean selectAll = !Checkbox.isSelected();
    	            Checkbox.setSelected(selectAll);
    	            apkTable.getTableHeader().repaint();
    	            if(selectAll)
    	            {
    	            	apkTable.selectAll();
    	            }else
    	            {
    	            	apkTable.clearSelection();
    	            }
    	        }
    	    }
    	});
    	
    	this.apkTable.getColumn("Seq").setCellRenderer(new CheckBoxRendererForCell());
    	DefaultTableCellRenderer render = new DefaultTableCellRendererEx();
        for(int i = 1; i < this.apkTable.getColumnCount(); i++)
        {
        	apkTable.getColumn(apkTable.getColumnName(i)).setCellRenderer(render);
        }
    	
    	this.apkFolderField = new JTextField(UserParam.getBasepath());
    	this.apkFolderField.setEditable(false);
    	this.browseApkBtn = new JBgColorButton("Browse");
    	
    	JPanel topPanel = new JPanel();
    	layoutTopPanel(topPanel);
    	
    	this.add(topPanel, BorderLayout.NORTH);
    	JScrollPane tableScrollPanel = new JScrollPane(apkTable);
    	JScrollBar sb = tableScrollPanel.getVerticalScrollBar();
		sb.setUI(new GraceFullyScrollBar());
    	tableScrollPanel.setOpaque(true);
    	tableScrollPanel.setBackground(IColorFontUtil.COLOR_GRACE_GRAY_WHITE);
    	tableScrollPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    	
    	this.add(tableScrollPanel, BorderLayout.CENTER);
    	
    	this.browseApkBtn.addActionListener(this);
    	this.loadFiles();
    }

    /**
     * "Seq", "File Name", "FileSize", "Last Modify Date"
     */
	private void initApkTableRowSorter()
    {
		Comparator<?> []rowsComparators = new Comparator<?>[4];
		rowsComparators[0] = new ComparatorIntegerSeq();
		rowsComparators[1] = new ComparatorString();
		rowsComparators[2] = new ComparatorFileSize();
		rowsComparators[3] = new ComparatorDateTime();
		apkTable.initSorter(rowsComparators);
    }

	private void layoutTopPanel(JPanel topPanel)
    {
		topPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		topPanel.setLayout(new BorderLayout());
		JLabel labelApkSelector = new JBlokLabel("APKs Selector");
		JLabel labelApkLocation = new JBlokLabel("APKs Location");
		
		JLabel westPanel = new JLabel(new ImageIcon(ApkSelectionPanel.class.getResource("/icon/icon_2dot.png")));
		westPanel.setBorder(new EmptyBorder(2, 5, 5, 5));
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout(10, 5));
		mainPanel.add(labelApkSelector, BorderLayout.NORTH);
		mainPanel.add(labelApkLocation, BorderLayout.WEST);
		mainPanel.add(apkFolderField, BorderLayout.CENTER);
		mainPanel.add(browseApkBtn, BorderLayout.EAST);
		topPanel.add(westPanel, BorderLayout.WEST);
		topPanel.add(mainPanel, BorderLayout.CENTER);
    }

	@Override
    public void actionPerformed(ActionEvent e)
    {
		if(e.getSource() == browseApkBtn)
		{
			selectionFolder(apkFolderField);
			loadFiles();
		}
    }
	
	private void selectionFolder(JTextField textField)
	{
		JFileChooser chooser = new JFileChooser(new File(textField.getText()));
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setDialogTitle("APKs Folder Selecter");
		chooser.setLocale(Locale.ENGLISH);
		int returnVal = chooser.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			String filePath = "";
			filePath = chooser.getSelectedFile().getAbsolutePath();
			textField.setText(filePath);
			ITTProperties.setPropAndSave(IPropertyKeys.APK_PATH, filePath);
		}
	}
	
	private void loadFiles()
	{
		String apkFolder = apkFolderField.getText();
		if(apkFolder == null || apkFolder.isEmpty())
		{
			return;
		}
		File apkFolderFile = new File(apkFolder);
		if(!apkFolderFile.exists())
		{
			return;
		}
		
		File[] apkFiles = apkFolderFile.listFiles(new FilenameFilter()
		{
			@Override
			public boolean accept(File file, String name)
			{
				if(name.endsWith("apk") && !name.startsWith(ITTConstant.TEMPFILE_MARK))
				{
					return true;
				}
				return false;
			}
		});
		
		fileList.clear();
		for (File file : apkFiles)
        {
	        String name = file.getName();
	        String size = Utils.getFormatSize(file.length());
	        String date = DateUtils.toDate(file.lastModified());
	        FileForSelection fileApk = new FileForSelection(file, name, size, date);
	        fileList.add(fileApk);
        }
		
		//Blank rows
		if(!fileList.isEmpty() && fileList.size() < ITTConstant.APK_FULLPANEL_COUNT)
		{
			int needBlankRows = ITTConstant.APK_FULLPANEL_COUNT - fileList.size();
			for (int i = 0; i < needBlankRows; i++)
            {
				fileList.add(new FileForSelection());
            }
		}
		
		apkTable.updateUI();
		apkTable.getSelectionModel().setSelectionInterval(0, 1);
	}
	
	public List<FileForSelection> getSelections()
	{
		List<FileForSelection> selectionFiles = new ArrayList<FileForSelection>();
		int[] selectionRows = apkTable.getSelectedRows();
		for (int i : selectionRows)
        {
	        selectionFiles.add(fileList.get(apkTable.convertRowIndexToModel(i)));
        }
		return selectionFiles;
	}

	@Override
    public String isValidInput()
    {
		if(this.apkTable.getSelectedValidItemCount() == 0)
		{
			return "Please select at least one apk file";
		}
	    return null;
    }
	
	public void addTableStatusListener(ITableStatusListener listener)
	{
		this.tableStatusListeners.add(listener);
	}

	@Override
    public void onInfo(int totalItems, int selectedItems, String info)
    {
		for (ITableStatusListener tableStatusListener : tableStatusListeners)
        {
			tableStatusListener.onInfo(totalItems, selectedItems, info);
        }
    }
}