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

public class SectionDAO {

/*____________________________________________________________________________________________________*/
/*
 * @param	This method is to add the sections into database based on the section name input by user 
 */	
	public static boolean addSection(JSONObject sectionDetails) throws SQLException, JSONException{
		Connection dbConn = null;
		Statement statement = null;
		ResultSet rs = null;		
		boolean status = false;
		try{
			dbConn = ConnectionManager.getConnection();
			if(sectionDetails != null){
								
				String insertDb = "INSERT INTO section (SectionName)";
				insertDb += " VALUES ('" + sectionDetails.getString("SectionName") + "');";
				
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
 * @param	This method is to get specific section from the database based on a specific section name 
 */	
	
	public static int getSectionID(JSONObject details){
		Connection dbConn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;		
		int toReturn = -1;
		try{
			dbConn = ConnectionManager.getConnection();
			if(details != null){
				String queryDb = "SELECT SectionID FROM section WHERE ";
				queryDb += "SectionName='" + details.getString("SectionName") +"';";
				//Preparing the query statement 
				statement = dbConn.prepareStatement(queryDb);
				rs = statement.executeQuery();
				
				while(rs.next()){
					toReturn = rs.getInt("SectionID");
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

