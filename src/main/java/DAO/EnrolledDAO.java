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

public class EnrolledDAO {
	
/*____________________________________________________________________________________________________*/
/*
 * @param	This method is to add enrolled into the database based on the following:
 * 			StudentID, CourseID, TermID, SectionID and UUID
 */	
	
	public static boolean addEnrolled(JSONArray enrolled) throws SQLException, JSONException{
		Connection dbConn = null;
		Statement statement = null;
		ResultSet rs = null;		
		boolean status = false;
		try{
			dbConn = ConnectionManager.getConnection();
			if(enrolled != null){
				for(int i=0; i<enrolled.length(); i++){
					
					int studentID = enrolled.getJSONObject(i).getInt("StudentID");
					int courseID = enrolled.getJSONObject(i).getInt("CourseID");
					int termID = enrolled.getJSONObject(i).getInt("TermID");
					int sectionID = enrolled.getJSONObject(i).getInt("SectionID");
					int score = enrolled.getJSONObject(i).getInt("Score");
					String uuid = enrolled.getJSONObject(i).getString("UUID");
				
					//SQL statement for adding of a new enrolled details
					String insertDb = "INSERT INTO ENROLLED (StudentID,CourseID,TermID,SectionID,UUID,Score)";
					insertDb += " VALUES (" + studentID + "," + courseID +","+ termID + "," + sectionID + ",'" + uuid + "'," + score + ");";
				
					statement = (Statement)(dbConn.createStatement());
					statement.executeUpdate(insertDb);
				}
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
}
