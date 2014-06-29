package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.util.ConnectionManager;

public class CourseDAO {
	
	public static boolean addCourse(JSONObject courseDetails) throws SQLException, JSONException{
		Connection dbConn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;		
		boolean status = false;
		try{
			dbConn = ConnectionManager.getConnection();
			if(courseDetails != null){
				String crsCode = courseDetails.getString("CourseCode");
				String crsName = courseDetails.getString("CourseName");
				
				String queryDb = "INSERT INTO COURSE (CourseCode,CourseName)";
				queryDb += " VALUES ('" + crsCode + "','" + crsName + "');";
				
				statement = dbConn.prepareStatement(queryDb);
				rs = statement.executeQuery();
			}
		} catch (Exception e){
			e.printStackTrace();
		}		
		finally{
			ConnectionManager.close(dbConn, statement, rs);
		}
		return status;
	}
	
	public static int getCourseID(String courseCode,String courseName){
		Connection dbConn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;		
		int toReturn = -1;
		try{
			dbConn = ConnectionManager.getConnection();
			if(courseCode != null && courseName != null){
		
				String queryDb = "SELECT CourseID FROM course WHERE ";
				queryDb += " CourseCode='" + courseCode + " AND ";
				queryDb += " CourseName='" + courseName +"';";
				
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
}
