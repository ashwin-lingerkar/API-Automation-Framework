package framework;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


/**
 * 
 * @author ashwin
 *
 */


public class ExcelDriverSheet {
private Sheet sheet = null;	
private String name = null;
private int colcount = 0;
private int numberofrows = 0;
private int currentcolumn = 1;


	@SuppressWarnings("rawtypes")
	private HashMap<String,ArrayList>datasection = null;
    private int start = 0;
	private ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
    private String section = "global";
    private String cellValue = "";

	@SuppressWarnings("rawtypes")
	public ExcelDriverSheet(String name, Sheet sheet2, Workbook wb) throws Exception{
        this.name = name;
        this.sheet = sheet2;
        //FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
        //evaluator.clearAllCachedResultValues();
        this.datasection = new HashMap<String,ArrayList>();
        DataFormatter format = new DataFormatter();
        try{
	        numberofrows = sheet2.getPhysicalNumberOfRows();
	        //System.out.println("number of rows in " + name + " => " + numberofrows);
	        for(int x = 0; x < numberofrows; x++){
	            Row row = sheet2.getRow(x);
	            
	            if(colcount <= 0){
	                colcount = sheet2.getRow(x).getPhysicalNumberOfCells();
	                //System.out.println("number of columns in " + name + " => " + colcount);
	            }
	            //Vector values = new Vector();
	            ArrayList<String> values = new ArrayList<String>();
	            for(int i = start; i < colcount; i++){
	               try{
	            	    //System.out.println("Getting row number => " + row.getRowNum());
	            	    //System.out.println("Getting cell number => " + i);
	            	    //System.out.println("Getting cell value = " + row.getCell(i));
	            	   Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK );  
	            	    //System.out.println(((String)format.formatCellValue(cell)).trim());
	                    //String cellValue = evaluator.evaluate(cell).formatAsString().trim();
	                    cellValue = ((String)format.formatCellValue(cell)).trim();
	                    
	                    //System.out.println("Cell Value = " + cellValue);
	                    //@SuppressWarnings("unused")
						//CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
	                    //System.out.println("Cell Index #: " + cellRef.formatAsString() + " Cell Value: " + cellValue);
	                    values.add(cellValue);
	            }catch(IllegalArgumentException e){
	            	 e.printStackTrace();
	            }catch(NullPointerException e){
	               
	            }catch(Exception e){
	            	   e.printStackTrace();
	            	   throw e;
	               } //end try
	            }//end for
	            if(values.size() != 0){
	                if(!values.get(0).equalsIgnoreCase("")){
	                    data.add(values);
	                }
	            }
	        }//end for
	        //populates the Sections
	        Iterator<ArrayList<String>> iterator  = data.iterator();
	        ArrayList<ArrayList> global = new ArrayList<ArrayList>();
	        datasection.put("global", null);
	        while( iterator.hasNext() ){
	            String tmpsection = null;
	            ArrayList section = iterator.next();
	            if(((String)section.get(0)).indexOf(">") == -1){
	                //System.out.println("Adding: " + section);
	                global.add(section);
	            }//end if
	            if(((String)section.get(0)).indexOf(">") != -1){
	                tmpsection = ((String)section.get(0)).trim().toLowerCase();
	                tmpsection = tmpsection.substring(0, tmpsection.length() - 1);
	                //System.out.println("Found new section called: " + tmpsection);
	                ArrayList<ArrayList> tmp = new ArrayList<ArrayList>();
	                while( iterator.hasNext() ){
	                    ArrayList innersection = iterator.next();
	                    if(((String)innersection.get(0)).indexOf(">") == -1){
	                        tmp.add(innersection);
	                        datasection.put(tmpsection, tmp);
	                    }else{
	                        tmpsection = ((String)innersection.get(0)).trim().toLowerCase();
	                        tmpsection = tmpsection.substring(0, tmpsection.length() - 1);
	                        //System.out.println("Found new section called: " + tmpsection);
	                        tmp = new ArrayList<ArrayList>();
	                    }//end if
	                }//end while
	            }//end if
	        }//end while
	        datasection.put("global", global);
        }catch(Exception e){
        	throw e;
        }
    }
	
	
	
	/**
     * Gets the value of the cell defined in the spreadsheet
     * @param int - value
     * @return String
     */
	public String getValue(String value) {
    	//System.out.println("Looking for: " + value);
        String retval = "";
        boolean flag = false;
        try{
            Iterator<String> iterator  = datasection.keySet().iterator();
            while( iterator.hasNext() ){
                String key = iterator.next();
                if(key.equalsIgnoreCase(section)){
                    //System.out.println(key);
                    ArrayList<?> tmp = datasection.get(key);
                    Iterator<?> i  = tmp.listIterator();
                    while( i.hasNext() ){
                        ArrayList<?> al = (ArrayList<?>)i.next();
                        //System.out.println("Finding...: " + al.get(0));
                        if(((String)al.get(0)).equalsIgnoreCase(value)){
                            retval = (String)al.get(currentcolumn);
                            //System.out.println("Found...: " + retval);
                            flag = true;
                            break;
                        }
                    }
                }
                if(flag == true){
                	break;
                }
            }
            if(flag == false){
            	ArrayList<?> tmp = datasection.get("global");
                Iterator<?> i  = tmp.listIterator();
                while( i.hasNext() ){
                    ArrayList<?> al = (ArrayList<?>)i.next();
                    if(((String)al.get(0)).equalsIgnoreCase(value)){
                    	retval = (String)al.get(currentcolumn);
                        //System.out.println("Found...: " + retval);
                        flag = true;
                        break;
                    }
                }
            }
            
        }catch(Exception e){
            //e.printStackTrace();
            retval = "<no_data>";
        }
        return retval;
    }
	
	/**
     * Gets the value of the cell defined in the spreadsheet
     * @param String - currentcolumn
     * @return String
     */
	public String getValue(int currentcolumn) {
    	//System.out.println("Looking for: " + value);
        String retval = "";
        boolean flag = false;
        try{
            Iterator<String> iterator  = datasection.keySet().iterator();
            while( iterator.hasNext() ){
                String key = iterator.next();
                if(key.equalsIgnoreCase(section)){
                    //System.out.println(key);
                    ArrayList<?> tmp = datasection.get(key);
                    retval = (String)tmp.get(currentcolumn);
                    flag = true;
                }
                if(flag == true){
                	break;
                }
            }
            if(flag == false){
            	ArrayList<?> tmp = datasection.get("global");
            	//System.out.println(key);
                retval = (String)tmp.get(currentcolumn);
                flag = true;
            }
            
        }catch(Exception e){
            //e.printStackTrace();
            retval = "<no_data>";
        }
        return retval;
    }

 
	/**
     * Sets the driver column by name
     * @param String - value
     * @return boolean
     * @throws Exception
     */
    public boolean setDriverColumn(String value) throws Exception {
    	boolean retval = false;
    	//System.out.println("Driver column: " + value);
        try{
        	ArrayList<?> tmp = datasection.get("global");
            Iterator<?> i  = tmp.listIterator();
            // iterate through global data section until find Test row 
            while( i.hasNext() ){
                ArrayList<?> al = (ArrayList<?>)i.next();
                if(((String)al.get(0)).equalsIgnoreCase("test")){
                	//iterate through columns of Test row until match or run out of rows
                	for (int x=1; x<this.colcount; x++) {
                		if (((String)al.get(x)).equalsIgnoreCase(value)) {
                			currentcolumn = x;
                            //System.out.println("Found...: " + x);
                            retval = true;
                			break; //found row - stop searching
                		}
                	}
                    break;
                }
                break; // break after finding the "test" row
            }
            if (retval == false) {
            	throw new Exception("Cannot set driver column: " + value);
            }
        }catch(Exception e){
            throw new Exception("Cannot set driver column: " + value);
        }

    	return retval;
    }
    
    /**
     * A specific section defined in the driver sheet
     * @param section
     */
    public void dataTopic(String section){
        this.section = section.trim().toLowerCase();
    }
    
    
}
