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
 package com.cats.ui.custome.table;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.RowSorterEvent;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import com.cats.utils.IColorFontUtil;

/**
 * @author xiaobolx
 * 2015年11月2日
 */
public class JCheckboxTable extends JTable
{
	private static final long serialVersionUID = 1L;
	private boolean ctrlDown = false;
	private JCheckboxTableModel checkboxTableModel;
	private List<ITableStatusListener> iTableStatusListeners = new ArrayList<ITableStatusListener>();
	private ReentrantLock tableStatusListenerLock = new ReentrantLock();
	private int clickedColumn = -1;
	public JCheckboxTable()
	{
		initTable();
	}

	public JCheckboxTable(JCheckboxTableModel checkboxTableModel)
	{
		super(checkboxTableModel);
		this.checkboxTableModel = checkboxTableModel;
		initTable();
	}

	// override the default selection behavior
	public void changeSelection(int rowIndex, int columnIndex, boolean toggle,
	        boolean extend)
	{
		if (ctrlDown)
		{
			// ctrl + dragging should add the dragged interval to the
			// selection
			super.changeSelection(rowIndex, columnIndex, toggle, extend);
		} else
		{
			// clicking adds a row to the selection without clearing
			// others

			// dragging without holding ctrl clears the selection

			// and starts a new selection at the dragged interval
			super.changeSelection(rowIndex, columnIndex, !extend, extend);
		}
	}

	private void initTable()
	{
		this.setRowHeight(30);
		this.setOpaque(true);
		this.setShowHorizontalLines(false);
		this.setShowVerticalLines(false);
		
		this.setBorder(new EmptyBorder(0, 0, 0, 5));
		this.setIntercellSpacing(new Dimension(0, 0));
//    	this.setRowHeight(this.getRowHeight()+7);
    	this.setBackground(IColorFontUtil.COLOR_GRACE_GRAY_WHITE);
    	this.getTableHeader().setDefaultRenderer(new DefaultTableHeaderCellRendererEx());
    	
		// keep track of when ctrl is held down
		this.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent e)
			{
				ctrlDown = e.isControlDown();
			}

			public void keyReleased(KeyEvent e)
			{
				ctrlDown = e.isControlDown();
			}
		});
		
		final JTableHeader tableHeader = this.getTableHeader();
		tableHeader.addMouseListener(new MouseAdapter(){  
            public void mouseReleased(MouseEvent e)
            {
                clickedColumn=tableHeader.columnAtPoint(e.getPoint());  
                super.mouseReleased(e);
            }  
        });  
		
		this.getSelectionModel().addListSelectionListener(this);
	}
	
	//Row Sorter
	public void initSorter(Comparator<?> []comparators)
	{
		TableRowSorter<JCheckboxTableModel> rowSorter = new TableRowSorter<JCheckboxTableModel>(this.checkboxTableModel);
		if(null != comparators)
		{
			for (int i = 0; i < comparators.length; i++)
            {
	            rowSorter.setComparator(i, comparators[i]);
            }
		}
		this.setRowSorter(rowSorter);
	}
	
	public boolean isSelectedAll()
	{
		int[] selectedRows = this.getSelectedRows();
		if(null == selectedRows || selectedRows.length == 0)
		{
			return false;
		}
		
		return checkboxTableModel.getValidTotalRows() == checkboxTableModel.getValidSelectedRows(selectedRows);
	}
	
	public int getSelectedValidItemCount()
	{
		return checkboxTableModel.getValidSelectedRows(this.getSelectedRows());
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e)
	{
	    super.valueChanged(e);
	    try
        {
	        int iTotalItems = checkboxTableModel.getValidTotalRows();
	        int iSelectedlItems = getSelectedValidItemCount();
	        
	        tableStatusListenerLock.lockInterruptibly();
	        for (ITableStatusListener iTableStatusListener : iTableStatusListeners)
	        {
	        	iTableStatusListener.onInfo(iTotalItems, iSelectedlItems, null);
	        }
        } catch (InterruptedException e1)
        {
	        e1.printStackTrace();
        } finally
        {
        	tableStatusListenerLock.unlock();
        }
	}
	
	public void addTableStatusListener(ITableStatusListener iTableStatusListener)
	{
		try
        {
	        tableStatusListenerLock.lockInterruptibly();
	        if(this.iTableStatusListeners.contains(iTableStatusListener))
	        {
	        	return;
	        }
	        
	        iTableStatusListeners.add(iTableStatusListener);
        } catch (InterruptedException e)
        {
	        e.printStackTrace();
        }finally
        {
        	tableStatusListenerLock.unlock();
        }
	}
	
	public void removeTableStatusListener(ITableStatusListener iTableStatusListener)
	{
		try
        {
	        tableStatusListenerLock.lockInterruptibly();
	        if(this.iTableStatusListeners.contains(iTableStatusListener))
	        {
	        	iTableStatusListeners.remove(iTableStatusListener);
	        }
        } catch (InterruptedException e)
        {
	        e.printStackTrace();
        }finally
        {
        	tableStatusListenerLock.unlock();
        }
	}
	
	@Override
	public void sorterChanged(RowSorterEvent e)
	{
        super.sorterChanged(e);
        if (e.getType() == RowSorterEvent.Type.SORTED) 
        {
        	TableCellRenderer render = this.getTableHeader().getDefaultRenderer();
        	if(render instanceof DefaultTableHeaderCellRendererEx)
            {
        		DefaultTableHeaderCellRendererEx clickedColumnRenderer = (DefaultTableHeaderCellRendererEx)render;
        		clickedColumnRenderer.setclickedColumn(this.clickedColumn);
            }
        }
    }
}