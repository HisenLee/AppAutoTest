package com.itt.apks.ui;

import java.util.List;

import com.cats.ui.custome.table.JCheckboxTableModel;

/**
 * @author xblia
 * 2015年10月22日
 */
public class ApksTableModel extends JCheckboxTableModel
{

    private static final long serialVersionUID = 1L;

    private String[] COLUMNS = {"Seq", "File Name", "FileSize", "Last Modify Date"};
    private List<FileForSelection> fileList;
    
	public ApksTableModel(List<FileForSelection> fileList)
    {
		this.fileList = fileList;
    }

	@Override
    public String getColumnName(int column)
    {
	    return COLUMNS[column];
    }

	@Override
	public int getColumnCount()
	{
		return COLUMNS.length;
	}

	@Override
	public int getRowCount()
	{
		return fileList.size();
	}

	@Override
	public Object getValueAt(int row, int column)
	{
		FileForSelection apk = fileList.get(row);
		switch (column)
        {
		case 0:
			if(null == apk.getFile())
			{
				return -1;
			}
			return row+1;
		case 1:
			return apk.getFileName();
		case 2:
			return apk.getFileSize();
		case 3:
			return apk.getFileDate();
		default:
			break;
		}
		return null;
	}

	@Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
	    return false;
    }

	@Override
    public int getValidSelectedRows(int[] selectedRows)
    {
		int validSelectedRowCount = 0;
		int totalRows = fileList.size();
		FileForSelection selectFile;
		for (int i = 0; i < selectedRows.length; i++)
        {
	        int iIndex = selectedRows[i];
	        if(iIndex < totalRows)
	        {
	        	selectFile = fileList.get(iIndex);
	        	if(selectFile.getFile() != null)
	        	{
	        		validSelectedRowCount++;
	        	}
	        }
        }
	    return validSelectedRowCount;
    }

	@Override
    public int getValidTotalRows()
    {
		int iCount = 0;
		for (FileForSelection selectFile : fileList)
        {
	        if(selectFile.getFile() != null)
	        {
	        	iCount++;
	        }
        }
	    return iCount;
    }
}
