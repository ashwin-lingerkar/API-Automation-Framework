package framework;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.*;
import java.util.*;

import org.apache.poi.POIXMLProperties;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.PackageProperties;
import org.apache.poi.openxml4j.opc.internal.PackagePropertiesPart;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;


/**
 * 
 * @author ashwin
 *
 */



public class ExcelData extends Thread {
	private String workbookname = null;
	private Workbook workbook = null;
	private String adminLogin = "automation";
	private String adminPassword = "password";
	private String driversheetname = null;
	private HashMap<String, Object> sheets = null;
	private String defaultsheetname = null;
	private HashMap<String, ExcelDataSheet> usersheets = null;
	private BaseFramework parent = null;
	
	

	public ExcelData(){
        sheets = new HashMap<String, Object>();
        usersheets = new HashMap<String, ExcelDataSheet>();
    }
	
	public ExcelData(BaseFramework parent){
        this.parent = parent;
        sheets = new HashMap<String, Object>();
        usersheets = new HashMap<String, ExcelDataSheet>();
    }
	
	/**
     * Opens a user defined workbook
     * @param String - workbook name
     * @throws Exception
     */
    public void setWorkBook(String wb) throws Exception {
        try{
            System.out.println("Trying to open up the workbook normally...");
             	wb = wb.replace("automationportal", "");
           
            workbookname = wb;
        	InputStream input = new FileInputStream(wb);
        	this.workbook = WorkbookFactory.create(input);
        }catch(Exception e0){
        	System.out.println(e0.getMessage());
        	System.out.println("Opps...  The file does not exist on the normal file system.");
        }
    }
	
    
    /**
	 * Opens a user defined Spreadsheet specifically designed as a Automation Driver Sheet
	 * @param name
	 * @param userdefined
	 * @return
	 * @throws Exception
	 */
	public void driverSheet(String name) throws Exception {
        this.driversheetname = name;
        try{
        	sheets.put(name, new ExcelDriverSheet(name, workbook.getSheet(name), workbook));
        	//System.out.println("Driver Sheet: " + name);
        }catch(Exception e){
        	throw new Exception("Cannot set driver sheet: " + name);
        }
    }
	
	/**
	 * Opens a user defined Spreadsheet
	 * @param name
	 * @param userdefined
	 * @return
	 * @throws Exception
	 */
    public ExcelDriverSheet getDriverSheet() throws Exception {
        ExcelDriverSheet ds = null;
    	try {
        	ds = (ExcelDriverSheet)sheets.get(driversheetname);
        } catch (Exception e) {
        	throw e;
        }
        return ds;
    }
    
    /**
     * Opens a SpreadSheet
     * @param name
     * @throws Exception
     */
	public void dataSheet(String name) throws Exception {
    	try {
    		if (this.defaultsheetname == null) this.defaultsheetname = name;
    		sheets.put(name, new ExcelDataSheet(name, workbook.getSheet(name), this));
    	} catch(Exception e) {
    		throw new Exception("Unable to locate/open data sheet: " + name );
    	}
    }
    
	/**
	 * Opens a user defined Spreadsheet
	 * @param name
	 * @return
	 * @throws Exception
	 */
    public ExcelDataSheet getDataSheet(String name) throws Exception {
    	if(!sheets.containsKey(name)){
    		dataSheet(name);
    	}
    	return (ExcelDataSheet)sheets.get(name);
    }
    
    /**
	 * Opens a user defined Spreadsheet
	 * @param name
	 * @return
	 * @throws Exception
	 */
    @SuppressWarnings("unused")
	public String getLastModifiedBy() throws Exception {
    	String lastModifiedBy = "";
    	try{
    		POIXMLProperties props =  ((XSSFWorkbook)this.workbook).getProperties(); 
			PackagePropertiesPart pprops = props.getCoreProperties().getUnderlyingProperties();
	    	lastModifiedBy = ((PackageProperties) props).getLastModifiedByProperty().getValue();
    	}catch(Exception e){}
    	return lastModifiedBy;
    }
    
    /**
	 * Opens a user defined Spreadsheet
	 * @param name
	 * @return
	 * @throws Exception
	 */
    @SuppressWarnings({ "unused", "deprecation" })
	public String getLastModifiedDate() throws Exception {
    	Date modified = null;
    	try{
	    	POIXMLProperties props =  ((XSSFWorkbook)this.workbook).getProperties(); 
	    	PackagePropertiesPart pprops = props.getCoreProperties().getUnderlyingProperties();
	    	modified = ((PackageProperties) props).getModifiedProperty().getValue();
    	}catch(Exception e){}
    	if(modified != null){
    		return modified.toGMTString();
    	}else{
    		return "";
    	}
    }
    
    /**
	 * Opens a user defined Spreadsheet
	 * @return
	 * @throws Exception
	 */
    public ExcelDataSheet getDataSheet(){
        if(defaultsheetname != null){
            return (ExcelDataSheet)sheets.get(defaultsheetname);
        }else{
            return null;
        }
    }
    

}
