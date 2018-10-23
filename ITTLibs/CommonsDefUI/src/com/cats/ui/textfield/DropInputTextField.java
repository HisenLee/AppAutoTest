package com.cats.ui.textfield;

import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import com.cats.utils.IColorFontUtil;

/**
 * @author xiaobolx
 * 2016年5月9日
 */
public class DropInputTextField extends JTextField
{

	/**
	 * serialVersionUID
	 */
    private static final long serialVersionUID = 1L;

    public DropInputTextField()
    {
    	initView();
    }
    
    public DropInputTextField(int length)
    {
    	super(length);
    	initView();
    }
    
    private void initView()
    {
    	this.setPreferredSize(new Dimension(-1, 28));
    	this.setBorder(new LineBorder(IColorFontUtil.COLOR_TEXTFIELD_BORDER));
    	setBorder(BorderFactory.createCompoundBorder(
    	        this.getBorder(), 
    	        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
    	dropInit();
    }
    
    
    private void dropInit()
    {
        new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE, new DropTargetAdapter()
        {
            @SuppressWarnings("unchecked")
            @Override
            public void drop(DropTargetDropEvent dtde)
            {
                try
                {
                    if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor))
                    {
                        dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                        List<File> list =  (List<File>) (dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor));
                        fillTextField(list);
                        DropInputTextField.this.updateUI();
                        dtde.dropComplete(true);
                    }
                    else
                    {
                        dtde.rejectDrop();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }
    
    private void fillTextField(List<File> list)
    {
        if(null == list || list.isEmpty())
        {
        	return;
        }
        
        for (File file : list)
        {
        	String strText = this.getText();
        	if(strText != null && !strText.trim().isEmpty())
        	{
        		strText += ";";
        	}
	        strText += file.getAbsolutePath();
	        this.setText(strText);
        }
    }
    
}
