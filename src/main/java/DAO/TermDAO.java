package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mysql.jdbc.Statement;
import com.util.ConnectionManager;

public class TermDAO {
	
/*____________________________________________________________________________________________________*/
	
/*
 * @param	This method is to add the term into database based on the term name input by user
 */	
	public static boolean addTerm(JSONObject details) throws SQLException, JSONException{
		Connection dbConn = null;
		Statement statement = null;
		ResultSet rs = null;		
		boolean status = false;
		
		try{
			dbConn = ConnectionManager.getConnection();
			if(details != null){
	
				String insertDb = "INSERT INTO TERM (TermName)";
				insertDb += " VALUES ('" + details.getString("TermName") + "');";
				
				statement = (Statement)(dbConn.createStatement());
				statement.executeUpdate(insertDb);
				
				//Indicate the addition of the course into database is successful
				status = true;
			}
		} catch (Exception e){
			e.printStackTrace();
		}		
		finally{
			ConnectionManager.close(dbConn, statement, rs);
		}
		return status;
	}
/*____________________________________________________________________________________________________*/
		
/*
 * @param	This method is to get a specific term ID from the database based on the term name
 */	
	public static int getTermID(JSONObject details) {
		Connection dbConn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;		
		int toReturn = -1;
		
		try{
			dbConn = ConnectionManager.getConnection();
			if(details != null){
		
				String queryDb = "SELECT TermID FROM term WHERE ";
				queryDb += "TermName='" + details.getString("TermName") + "';";

				statement = dbConn.prepareStatement(queryDb);
				rs = statement.executeQuery();
				
				while(rs.next()){
					toReturn = rs.getInt("TermID");
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}		
		finally{
			ConnectionManager.close(dbConn, statement, rs);
		}
		return toReturn;
	}
/*____________________________________________________________________________________________________*/
}
