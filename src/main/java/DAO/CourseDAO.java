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

public class CourseDAO {

/*____________________________________________________________________________________________________*/
/*
 * @param	This method is to add the course into database based on the course code and 
 * 			course name input by user
 */	
	
	public static boolean addCourse(JSONObject courseDetails) throws SQLException, JSONException{
		Connection dbConn = null;
		Statement statement = null;
		ResultSet rs = null;		
		boolean status = false;
		try{
			dbConn = ConnectionManager.getConnection();
			if(courseDetails != null){
				String crsCode = courseDetails.getString("CourseCode");
				String crsName = courseDetails.getString("CourseName");
				
				String insertDb = "INSERT INTO COURSE (CourseCode,CourseName)";
				insertDb += " VALUES ('" + crsCode + "','" + crsName + "');";
				
				statement = (Statement)(dbConn.createStatement());
				statement.executeUpdate(insertDb);
				
				//Indicate the addition of the course is successful
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
 * @param	This method is to get a specific course ID from the database 
 */	
	
	public static int getCourseID(JSONObject details){
		Connection dbConn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;		
		int toReturn = -1;
		try{
			dbConn = ConnectionManager.getConnection();
			if(details != null){
				String queryDb = "SELECT CourseID FROM course WHERE ";
				queryDb += "CourseCode='" + details.getString("CourseCode") + "' AND ";
				queryDb += "CourseName='" + details.getString("CourseName") +"';";
				//Preparing the query statement 
				statement = dbConn.prepareStatement(queryDb);
				rs = statement.executeQuery();
				
				while(rs.next()){
					toReturn = rs.getInt("CourseID");
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
