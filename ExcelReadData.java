package com.ims.lib;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReadData {
    public FileInputStream fis = null;
    public XSSFWorkbook workbook = null;
    public XSSFSheet sheet = null;
    public XSSFRow row = null;
    public XSSFCell cell = null;
 
    public String getCellData(String xlFilePath,String sheetName, String colName, int rowNum) throws IOException
    {
    	fis = new FileInputStream(xlFilePath);
        workbook = new XSSFWorkbook(fis);
        try
        {
            int col_Num = -1;
            sheet = workbook.getSheet(sheetName);
            row = sheet.getRow(0);
            for(int i = 0; i < row.getLastCellNum(); i++)
            {
                if(row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
                    col_Num = i;
            }
 
            row = sheet.getRow(rowNum);
            cell = row.getCell(col_Num);
 
            if(cell.getCellType() == XSSFCell.CELL_TYPE_STRING)
                return cell.getStringCellValue();
            else if(cell.getCellType() ==XSSFCell.CELL_TYPE_NUMERIC || cell.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN)
            {
                String cellValue = String.valueOf(cell.getNumericCellValue());
                if(HSSFDateUtil.isCellDateFormatted(cell))
                {
                    DateFormat df = new SimpleDateFormat("dd/MM/yy");
                    Date date = cell.getDateCellValue();
                    cellValue = df.format(date);
                }
                return cellValue;
            }else if(cell.getCellType() ==XSSFCell.CELL_TYPE_BLANK)
                return "";
            else
                return String.valueOf(cell.getBooleanCellValue());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return "row "+rowNum+" or column "+colName +" does not exist  in Excel";
        }
    }
    
    public  int getRowCount(String xlpath,String sheetName)
	{
		int rc=0;
		try
		{
			FileInputStream fis=new FileInputStream(xlpath);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheet(sheetName);
			rc=sheet.getLastRowNum();
		}
		catch(Exception e)
		{
		}
		return rc;
	}


   public static void main(String args[]) throws Exception
    {
	   ExcelReadData eat = new ExcelReadData(); 
        String xpaths = System.getProperty("user.dir")+"/DNA_TestData/TestData_DNA.xlsx";
        System.out.println(xpaths);
        System.out.println(eat.getCellData(xpaths,"DNA_UAT","ServiceName",1));

 
    }
}
