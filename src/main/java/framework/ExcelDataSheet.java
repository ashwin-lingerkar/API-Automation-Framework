package framework;

import java.util.HashMap;
import java.util.Vector;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;


/**
 * 
 * @author ashwin
 *
 */


public class ExcelDataSheet{
	private HashMap<Integer, Vector<String>> data = null;
    private Sheet sheet = null;
    @SuppressWarnings("unused")
	private String name = null;
    private int colcount = 0;
    private int rowcount = 0;
    private int start = 1;
    private int currentrow = 0;
    private int currentcolumn = 0;
    private String cellValue = "";
    private ExcelData parent = null;
    @SuppressWarnings("unused")
	private String [] headers = null;
    @SuppressWarnings("unused")
	private boolean hasHeaders = true;


    /**
     * Constructor
     * The first row in the sheet is marked as a header row
     * Reads in a spreadsheet and starts the rowcount at 1
     * @param name
     * @param sheet
     * @param parent
     */
	public ExcelDataSheet(String name, Sheet sheet, ExcelData parent) {
        this.name = name;
        this.sheet = sheet;
        this.data = new HashMap<Integer, Vector<String>>();
        this.parent = parent;
        DataFormatter format = new DataFormatter();
//        System.out.println("Reading datasheet... " + name);
//        System.out.println(sheet.getLastRowNum());
//        System.out.println("physical nbr of rows: " + sheet.getPhysicalNumberOfRows());
//        System.out.println("rowcount: " + rowcount);
    	
        //System.out.println(sheet.getPhysicalNumberOfRows());
        for(int x = 0; x < sheet.getPhysicalNumberOfRows(); x++){
            Row row = sheet.getRow(x);
            if(colcount <= 0){
                colcount = row.getLastCellNum() + 1;
                start = row.getFirstCellNum();
                currentrow = row.getRowNum() + 1;
            }
            Vector<String>  values = new Vector<String>();
            int rowlen = 0;
            for(int i = start; i < colcount; i++){
            	try{
            	    Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK );                   
                    if(Cell.CELL_TYPE_FORMULA == cell.getCellType()){
                        try{
                        	  if(Cell.CELL_TYPE_STRING == cell.getCachedFormulaResultType()){
                        		  cellValue = cell.getStringCellValue();
                        	  }else{
	                        	  //System.out.println("cellValue: " + cellValue);
	                              Double cellValue2 = cell.getNumericCellValue();
	                              //System.out.println("checking for a real double: " + cellValue2);
	                              double d = cellValue2.doubleValue();
	                              if(Math.floor(d) == d){
	                            	  //System.out.println("got a int");
	                            	  cellValue = String.valueOf(cellValue2.intValue());
	                            	  //System.out.println("String cellValue: " + cellValue);
	                              }else{
	                            	 // System.out.println("got a real double");
	                            	  cellValue = cellValue2.toString();
	                              }
                        	  }
                       }
                        catch(Exception e){
                              cellValue = cell.getStringCellValue();
                        }
                    }else /*if(Cell.CELL_TYPE_NUMERIC == cell.getCellType() || Cell.CELL_TYPE_STRING == cell.getCellType() || Cell.CELL_TYPE_BLANK == cell.getCellType())*/{
                        cellValue = ((String)format.formatCellValue(cell)).trim();
                    }


                  
                    //CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
                    //System.out.println("Cell Index #: " + cellRef.formatAsString() + " Cell Value: " + format.formatCellValue(cell));
                    if (i == start){
                    	//srv: break for first memo-comment in left column, starting with **
                    	if (cellValue.startsWith("**")) break;
                    	rowlen = 0;
                    }
                    rowlen += cellValue.length();
                    values.add(cellValue);
               }catch(Exception e){} //end try
            }//end for
            
        	//System.out.println("rowlen=" + rowlen);
            // srv: break for first blank row AFTER the rows containing data
            //System.out.println("<>rowcount: " + rowcount);
        	
            if (rowlen == 0 && rowcount > 0) break;
            if (rowlen == 0 && rowcount == 0) start++;
            if (rowlen > 0) {
            	rowcount = rowcount+1;
                data.put(row.getRowNum(), values); 
            }
        }//end for
        if (rowcount > 0) rowcount--;
       // System.out.println("data rows =" + rowcount);
    }
	
    /**
     * Brings back the total Row Count
     * @return
     */
    public int rowCount(){
        return this.rowcount;
    }

    /**
     * Brings Back the total Column Count
     * @return
     */
    public int columnCount(){
        int retval = 0;
        retval = colcount;
        retval += -1;
        return retval;
    }

    /**
     * Gets the next row
     * @return
     */
    public boolean nextRow(){
    	currentrow++;
        if(currentrow <= rowcount){
            return true;
        }else{
            currentrow = 1;
        	return false;
        }
    }

    /**
     * Gets the Previous Row
     * @return
     */
    public boolean previousRow(){
        currentrow--;
        if(currentrow > 0){
            return true;
        }else{
            currentrow = rowcount;
        	return false;
        }
    }
    
    /**
     * Goes to the First Row
     */
    public void gotoFirstRow() {
    	currentrow = 1;
    }
    
    /**
     * Goes to a specific row
     * @param row
     * @throws Exception
     */
    public void gotoRow(int row) throws Exception {
    	if (row > 0 && row <= rowcount) {
    		this.currentrow = row;
    	} else {
    		throw new Exception("Cannot set the current row to " + row + "; the row number must be in a range between 1 and " + rowcount);
    	}
    }
    
    /**
     * Goes to the last row
     */
    public void gotoLastRow() {
    	currentrow = rowcount;
    }

    /**
     * Gets the current sheet name
     * @return String
     */
    public String getName(){
        return sheet.getSheetName();
    }

    /**
     * Gets the Current Headers of the SpreadSheet
     * @return String - Comma Seperated
     */
    @SuppressWarnings("unused")
	public String getHeaders(){
    	String tmpheadernames = "";
    	Vector<?> tmp = (Vector<?>)data.get(start);
        int loc = 0;
        for(int i = start; i < colcount - 1; i++){
        	if(i == 0){
        		tmpheadernames += ((String)tmp.elementAt(i)).trim();
        	}else{
        		tmpheadernames += "," + ((String)tmp.elementAt(i)).trim();
        	}
        }
        return tmpheadernames;
    }
    
    /**
     * Gets the User Defined Data from the SpreadSheet by Column Name and Row Number
     * Column name is the first column in the spreadsheet
     * @param colname
     * @return
     */
    public String getUserData(String colname, int row) throws Exception {
    	gotoRow(row);
    	return getUserData(colname);
    }

    /**
     * Gets the User Defined Data from the SpreadSheet by Column Name
     * Column name is the first column in the spreadsheet
     * @param colname
     * @return
     */
	public String getUserData(String colname){
        String retval = "";
        try{
            Vector<?> tmp = (Vector<?>)data.get(start);
            int loc = 0;
            for(int i = start; i < colcount - 1; i++){
                if(((String)tmp.elementAt(i)).trim().equalsIgnoreCase(colname.trim().toLowerCase())){
                    //System.out.println("Column Name: " + loc);
                    loc = i;
                    currentcolumn = loc;
                    break;
                }
            }
            if(loc >= 0){
                tmp = (Vector<?>)data.get(currentrow);
                if(tmp != null){
                    //System.out.println("Line Number: " + loc);
                    if(((String)tmp.elementAt(loc)).equals("")){
                        retval = parent.getDriverSheet().getValue(colname.trim());
                    }else{
                        retval = (String)tmp.elementAt(loc);
                    }
                }
            }else{
                retval = parent.getDriverSheet().getValue(colname.trim());
            }
           
        }catch(Exception e){
            retval = "<error>";
        }
        return retval;
    }
	
 
	/**
     * Gets the User Defined Data from the SpreadSheet by Column Number
     * Column name is the first column in the spreadsheet
     * @param colnum
     * @return
     */
    public String getUserData(int loc){
        String retval = "";
        try{
            Vector<?> tmp = (Vector<?>)data.get(start);
            if(loc >= 0){
                tmp = (Vector<?>)data.get(currentrow);
                if(tmp != null){
                    //System.out.println("Line Number: " + loc);
                    if(((String)tmp.elementAt(loc)).equals("")){
                        retval = parent.getDriverSheet().getValue(loc);
                    }else{
                        retval = (String)tmp.elementAt(loc);
                    }
                }
            }else{
                retval = parent.getDriverSheet().getValue(loc);
            }
           
        }catch(Exception e){
            retval = "<error>";
        }
        return retval;
    }

    
    /**
     * Gets the current row number
     * @return int
     */
    public int getCurrentRow(){
    	return this.currentrow;
    }
    
    /**
     * Gets the current column number
     * @return int
     */
    public int getCurrentColumn(){
    	return this.currentcolumn;
    }
    
    
    /**
     * Sets the Column
     * @param colname
     */
    public int setColumn(String colname){    	
    	try{
            Vector<?> tmp = (Vector<?>)data.get(start);
            int loc = 0;
            for(int i = start; i < colcount - 1; i++){
                if(((String)tmp.elementAt(i)).trim().equalsIgnoreCase(colname.trim().toLowerCase())){
                    loc = i;
                    currentcolumn = loc;
                    break;
                }
            }
            
        }catch(Exception e){
           
        }
    	return currentcolumn;
    }
    
    /**
     * Checks whether a column is present in the datasheet
     * @param colname
     * @return boolean
     */

    public boolean isColumnPresent(String colname){
		boolean retval = false;
		try{
			 Vector<?> tmp = (Vector<?>)data.get(start);
	         for(int i = start; i < colcount - 1; i++){
	             if(((String)tmp.elementAt(i)).trim().equalsIgnoreCase(colname.trim().toLowerCase())){
	                 retval = true;
	                 break;
	             }
	         }
		}catch(Exception e){
			
		}
		return retval;
	}
    
    
}
