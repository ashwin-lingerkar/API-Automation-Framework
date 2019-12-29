package framework;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.logging.*;


/**
 * 
 * @author ashwin
 *
 */

@SuppressWarnings("unused")
public class DataBase {
	private PropertiesConfig config = new PropertiesConfig();
    private java.sql.Connection conn;
    private java.sql.Statement stat;
    private String url = "";

   
    @SuppressWarnings("static-access")
	public DataBase() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            url = "jdbc:sqlserver://" + config.properties.getProperty("dbserver") + ":1433;databaseName=" + config.properties.getProperty("database") + ";user=" + config.properties.getProperty("dbuser") + ";password=" + config.properties.getProperty("dbpassword");
            conn = DriverManager.getConnection(url);
            stat = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
           // System.out.println(url);
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    /*
     * Closes the connection to the database
     */
    public void close() {
        try {
            conn.close();
        } catch (SQLException ex) {
            //Logger.getLogger(ATCDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Queries database with given SQL statement.
     *
     * @param sql SQL statement
     * @return Resultset - returns the resultset.
     */
    public ResultSet sqlSelect(String sql) throws SQLException {
        ResultSet result = stat.executeQuery(sql);
        return result;
    }
    
    public int sqlInsert(String sql) throws SQLException {
    	stat.execute(sql, Statement.RETURN_GENERATED_KEYS);
    	int currentid = 0;
    	ResultSet result =  stat.getGeneratedKeys();
    	result.next();
    	currentid = result.getInt(1);
    	result.close();
    	return currentid;
    }

    public void sqlExecute(String sql) throws SQLException {
        stat.execute(sql);
    }
    
    public Statement getStatement(){
    	return this.stat;
    }
}