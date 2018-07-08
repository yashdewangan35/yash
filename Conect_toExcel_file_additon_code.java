package frame_work;

import java.io.File;
import jxl.write.Number;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class Conect_toExcel_file_additon_code 
{
	public static void main(String[] args) throws Exception, Exception 
	{
		// connect to excel file 
		File f=new File ("Book1.xls");
		
		// open that excel file for reading
        Workbook rwb = Workbook.getWorkbook(f);   // Workbook,Sheet,Writable import to jxl.file
        Sheet rsh=rwb.getSheet(0);                 // 0 means sheet 1
        int nour=rsh.getRows();
        
        //open excel file for writing
        WritableWorkbook wwb=Workbook.createWorkbook(f,rwb);
        WritableSheet wsh=wwb.getSheet(0);         // 0 means sheet1
        
        //Data driven fron 2nd row (index is 1)
        
        for(int i=1; i<nour; i++)
        {
        	int x=Integer.parseInt(rsh.getCell(0,i).getContents());
        	int y=Integer.parseInt(rsh.getCell(1,i).getContents());
        	int z=x+y;
        	Number n=new Number(2,i,z);                   // we have to miport manually import jxl.write.Number;
        	wsh.addCell(n);
        }
        
        // save excel
        wwb.write();
        rwb.close();
        wwb.close();
        	
        }
	}


