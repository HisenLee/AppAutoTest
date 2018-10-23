package com.testserver.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.cats.utils.IOUtils;
import com.intel.cats.test.log.ILog;
import com.itt.ITTConstant;
import com.test.po.TestResult;

public class exceloperate
{
	private static Object lockFile = new Object();
	// 将测试结果放入excel中
	public static void writeresult(String path, TestResult result)
	{
		synchronized(lockFile)
		{
			ILog.getLog().logMain(result.getLaunchimgurl());
			int colindex = 0;
			// 从0开始，0行0列是第一行第一列
			InputStream input = null;
			OutputStream output = null;
			try
			{
				input = new FileInputStream(new File(path));
				Workbook wb = null;
				wb = new XSSFWorkbook(input);
				Sheet sheet = wb.getSheetAt(0);
				int row = sheet.getLastRowNum();
				ILog.getLog().logMain("excelOperate : " + row + " rows");
				Row rw = null;
				rw = sheet.createRow(++row);
				Cell cl = getCell(rw, colindex);
				setValue(result.getApkname(), cl, wb);
				colindex++;
				
				// add appName
				cl = getCell(rw, colindex);
				setValue(result.getAppName(), cl, wb);
				colindex++;
				// add apkVersion 
				cl = getCell(rw, colindex);
				setValue(result.getApkVersion(), cl, wb);
				colindex++;
				
				
				cl = getCell(rw, colindex);
				setValue(result.getPkgName(), cl, wb);
				colindex++;
				cl = getCell(rw, colindex);
				setValue(result.getInstallsuccess(), cl, wb);
				colindex++;
				cl = getCell(rw, colindex);
				setValue(result.getLaunchsuccess(), cl, wb);
				colindex++;
				cl = getCell(rw, colindex);
				setValue(result.getLaunchimgurl(), cl, wb);
				colindex++;
				cl = getCell(rw, colindex);
				setValue(result.getRandomsuccess(), cl, wb);
				colindex++;
				cl = getCell(rw, colindex);
				setValue(result.getRandomimgurl(), cl, wb);
				colindex++;
				cl = getCell(rw, colindex);
				setValue(result.getQuitsuccess(), cl, wb);
				colindex++;
				cl = getCell(rw, colindex);
				setValue(result.getQuiturl(), cl, wb);
				colindex++;
				cl = getCell(rw, colindex);
				setValue(result.getUninstallsuccess(), cl, wb);
				colindex++;
				cl = getCell(rw, colindex);
				setValue(result.getDeviceid(), cl, wb);
				colindex++;
				cl = getCell(rw, colindex);
				setValue(result.getModel_ver(), cl, wb);
				colindex++;
				cl = getCell(rw, colindex);
				setValue(ITTConstant.VERSION, cl, wb);
				colindex++;
				cl = getCell(rw, colindex);
				setValue(result.getDeviceVersionId(), cl, wb);
	
				// add by xblia 2014-12-18 for fail reason remind
				colindex++;
				cl = getCell(rw, colindex);
				setValue(result.getInstallFailReason(), cl, wb);
				colindex++;
				cl = getCell(rw, colindex);
				setValue(result.getLaunchFailReason(), cl, wb);
				colindex++;
				cl = getCell(rw, colindex);
				setValue(result.getTouchFailReason(), cl, wb);
				colindex++;
				cl = getCell(rw, colindex);
				setValue(result.getQuitFailReason(), cl, wb);
				colindex++;
				cl = getCell(rw, colindex);
				setValue(result.getUninstallFailReason(), cl, wb);
				// end
				try
	            {
		            input.close();
	            } catch (Exception e)
	            {
		            e.printStackTrace();
	            }
				
				output = new FileOutputStream(new File(path));
				wb.write(output);
			} catch (FileNotFoundException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				e.printStackTrace();
			}finally
			{
				if(null != output)
				{
					try
	                {
		                output.close();
	                } catch (IOException e)
	                {
		                e.printStackTrace();
	                }
				}
			}
		}
	}

	// 设置每个单元格的字体和内容
	private static void setValue(String content, Cell cell, Workbook wb)
	{

		// CellStyle linkStyle = cell.getCellStyle();
		// Font cellFont= wb.getFontAt((short) 0);

		// Font cellFont=wb.createFont();
		// cellFont.setFontName("Verdana");
		// cellFont.setFontHeightInPoints((short)6);
		// Font cellFont=new Font("");
		// linkStyle.setFont(cellFont);//add by xblia 2014-11-21 for Garbage
		// problem
		// cell.setCellStyle(linkStyle);

		String strValue = content == null ? "--":content;
		try
		{
			strValue = new String(content.getBytes(ITTConstant.LOCAL_CHARSET),
			        "UTF-8");
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		cell.setCellValue(strValue);
	}

	// 检查如果单元格存在，则获取，否则创建
	private static Cell getCell(Row rw, int col)
	{
		Cell cl = null;
		if (rw != null)
		{
			cl = rw.getCell(col);
			if (cl == null)
			{
				cl = rw.createCell(col);
			}
		}
		return cl;
	}

	/**
	 * add by xblia 2015-02-15, recover testing break.
	 * 
	 * @param path
	 * @return
	 */
    @SuppressWarnings("resource")
    public static List<String> readApkFileName(String path)
	{
		synchronized (lockFile)
		{
			List<String> apkFileNameList = new ArrayList<String>();
			InputStream is = null;
			try
			{
				is = new FileInputStream(new File(path));
				Workbook wb = null;
				wb = new XSSFWorkbook(is);
				Sheet sheet = wb.getSheetAt(0);
				int iTotalRow = sheet.getLastRowNum();
				for (int i = 0; i <= iTotalRow; i++)
				{
					Row row = sheet.getRow(i);
					if (null != row)
					{
						Cell cell = row.getCell(0);
						if (null != cell)
						{
							String fileName = cell.getStringCellValue();
							apkFileNameList.add(fileName);
						}
					}
				}

			} catch (FileNotFoundException e)
			{
				e.printStackTrace();
			} catch (Exception e)
			{
				e.printStackTrace();
			} finally
			{
				if (null != is)
				{
					try
					{
						is.close();
					} catch (IOException e)
					{
						e.printStackTrace();
					}
				}
			}
			return apkFileNameList;
		}
	}

	public static void writeMultiResult(String path, List<TestResult> testMutilResult)
	{
		synchronized (lockFile)
		{
			
			InputStream input = null;
			OutputStream output = null;
			try
			{
				input = new FileInputStream(new File(path));
				Workbook wb = null;
				wb = new XSSFWorkbook(input);
				Sheet sheet = wb.getSheetAt(1);
				int groupResultSize = testMutilResult.size();
				int lastRow = sheet.getLastRowNum();
				Row rw = null;
                recordApkBaseInfoAndUnifiedResult(testMutilResult, wb, sheet, groupResultSize, lastRow);
				
				for (TestResult result : testMutilResult)
				{
					//int colindex = 4; // add version old 3
					int colindex = 5; // add appName old 3
					rw = sheet.getRow(++lastRow);
					if(rw == null)
					{
						rw = sheet.createRow(lastRow);
					}
					Cell cl = getCell(rw, colindex);
					setValue(result.getInstallsuccess(), cl, wb);
					colindex++;
					cl = getCell(rw, colindex);
					setValue(result.getLaunchsuccess(), cl, wb);
					colindex++;
					cl = getCell(rw, colindex);
					setValue(result.getLaunchimgurl(), cl, wb);
					colindex++;
					cl = getCell(rw, colindex);
					setValue(result.getRandomsuccess(), cl, wb);
					colindex++;
					cl = getCell(rw, colindex);
					setValue(result.getRandomimgurl(), cl, wb);
					colindex++;
					cl = getCell(rw, colindex);
					setValue(result.getQuitsuccess(), cl, wb);
					colindex++;
					cl = getCell(rw, colindex);
					setValue(result.getQuiturl(), cl, wb);
					colindex++;
					cl = getCell(rw, colindex);
					setValue(result.getUninstallsuccess(), cl, wb);
					colindex++;
					cl = getCell(rw, colindex);
					setValue(result.getDeviceid(), cl, wb);
					colindex++;
					cl = getCell(rw, colindex);
					setValue(result.getModel_ver(), cl, wb);
					colindex++;
					cl = getCell(rw, colindex);
					setValue(ITTConstant.VERSION, cl, wb);
					colindex++;
					cl = getCell(rw, colindex);
					setValue(result.getDeviceVersionId(), cl, wb);

					// add by xblia 2014-12-18 for fail reason remind
					colindex++;
					cl = getCell(rw, colindex);
					setValue(result.getInstallFailReason(), cl, wb);
					colindex++;
					cl = getCell(rw, colindex);
					setValue(result.getLaunchFailReason(), cl, wb);
					colindex++;
					cl = getCell(rw, colindex);
					setValue(result.getTouchFailReason(), cl, wb);
					colindex++;
					cl = getCell(rw, colindex);
					setValue(result.getQuitFailReason(), cl, wb);
					colindex++;
					cl = getCell(rw, colindex);
					setValue(result.getUninstallFailReason(), cl, wb);

				}
				// end
				IOUtils.closeResource(input);

				output = new FileOutputStream(new File(path));
				wb.write(output);
			} catch (FileNotFoundException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				e.printStackTrace();
			} finally
			{
				IOUtils.closeResource(output);
			}
		}
	}

	private static void recordApkBaseInfoAndUnifiedResult(
            List<TestResult> testMutilResult, Workbook wb, Sheet sheet,
            int groupResultSize, int lastRow)
    {
		//Style
		 CellStyle cellStyle = wb.createCellStyle();
		 cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		 cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
		
		//APK Name
		int startRow = lastRow + 1;
	    if(groupResultSize > 1)
	    {
	    	CellRangeAddress apkNameCellRgion = new CellRangeAddress(startRow, startRow +  groupResultSize-1, 0, 0);
	    	sheet.addMergedRegion(apkNameCellRgion);
	    	
	    	// add appName 
	    	CellRangeAddress appNameCellRgion = new CellRangeAddress(startRow, startRow +  groupResultSize-1, 1, 1);
	    	sheet.addMergedRegion(appNameCellRgion);
	    	
	    	//ApkVersion // add apkVersion
	    	CellRangeAddress apkVersionCellRgion = new CellRangeAddress(startRow, startRow +  groupResultSize-1, 2, 2);
	    	sheet.addMergedRegion(apkVersionCellRgion);
	    	
	    	CellRangeAddress pkgNameCellRgion = new CellRangeAddress(startRow, startRow +  groupResultSize-1, 3, 3);
	    	sheet.addMergedRegion(pkgNameCellRgion);
	    	
	    	CellRangeAddress resultCellRgion = new CellRangeAddress(startRow, startRow +  groupResultSize-1, 4, 4);
	    	sheet.addMergedRegion(resultCellRgion);
	    }
	    Row rw = sheet.createRow(startRow);
	    Cell apkNameCell = rw.createCell(0);
	    setValue(testMutilResult.get(0).getApkname(), apkNameCell, wb);
	    apkNameCell.setCellStyle(cellStyle);
	    
	    // add appName
	    Cell appNameCell =  rw.createCell(1);
	    setValue(testMutilResult.get(0).getAppName(), appNameCell, wb);
	    appNameCell.setCellStyle(cellStyle);
	    
	    //ApkVersion // add apkVersion
	    Cell apkVersionCell =  rw.createCell(2);
	    setValue(testMutilResult.get(0).getApkVersion(), apkVersionCell, wb);
	    apkVersionCell.setCellStyle(cellStyle);
	    
	    //PkgName
	    Cell pkgNameCell =  rw.createCell(3);
	    setValue(testMutilResult.get(0).getPkgName(), pkgNameCell, wb);
	    pkgNameCell.setCellStyle(cellStyle);
	    
	    //Unified Test Result
	    Cell resultCell =  rw.createCell(4);
	    setValue(getGroupResult(testMutilResult), resultCell, wb);
	    resultCell.setCellStyle(cellStyle);
    }

	private static String getGroupResult(List<TestResult> testMutilResult)
    {
		boolean isPass = true;
		for (TestResult testResult : testMutilResult)
        {
			isPass &= testResult.allPass();
        }
		
	    return isPass ? "Pass":"Fail";
    }
	
	
	/*public static void main(String[] args)
    {
		List<TestResult> testMutilResult = new ArrayList<TestResult>();
		for (int i = 0; i < 3; i++)
        {
			TestResult testResult = new TestResult();
			testResult.setApkname("QQ.apk");
			testMutilResult.add(testResult);
        }
		
		writeMultiResult("T.xlsx", testMutilResult);
    }*/
}
