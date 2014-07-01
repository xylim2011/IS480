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

public class ClassDAO {

/*____________________________________________________________________________________________________*/
/*
 * @param	This method is to get classes associated to a specific admin user's id
 */	
	
	public static JSONArray getClassTaught(String id) throws JSONException, SQLException{
		JSONObject classTaught = null;
		JSONArray classes = new JSONArray();
		Connection dbConn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		try{
			dbConn = ConnectionManager.getConnection();
			if (id != null){
				String queryDb = "SELECT CLASS.*,CourseCode,CourseName,TermName,SectionName from CLASS ";
				queryDb += "inner join COURSE on CLASS.CourseID = COURSE.CourseID ";
				queryDb += "inner join TERM on CLASS.TermID = TERM.TermID ";
				queryDb += "inner join SECTION on CLASS.SectionID = SECTION.SectionID ";
				queryDb += "where UserID = '" + id +"'";
				statement = dbConn.prepareStatement(queryDb);
				rs = statement.executeQuery();
				
				while (rs.next()){
					classTaught = new JSONObject("{code:'" + rs.getString("CourseCode") + "',term: '" + rs.getString("TermName") + "',section: '" + rs.getString("SectionName") +"'}");
					classes.put(classTaught);
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		finally{
			ConnectionManager.close(dbConn, statement, rs);
		}
		return classes;		
	}
/*____________________________________________________________________________________________________*/
/*
 * @param	This method is to add classes into the database
 */	
	
	public static boolean addClass(JSONObject classDetails) throws SQLException, JSONException{
		Connection dbConn = null;
		Statement statement = null;
		ResultSet rs = null;		
		boolean status = false;
		try{
			dbConn = ConnectionManager.getConnection();
			if(classDetails != null){
				int id = classDetails.getInt("CourseID");
				int termId = classDetails.getInt("TermID");
				int sectionId = classDetails.getInt("SectionID");
				long userId = classDetails.getLong("UserID");
				String start = classDetails.getString("StartTime");
				String end = classDetails.getString("EndTime");
				System.out.println("72: "+ id + "," + termId + "," + sectionId + "," + userId + "," + start+ "," + end);
				
				String insertDb = "INSERT INTO CLASS (CourseID,TermID,SectionID,UserID,StartTime,EndTime)";
				insertDb += " VALUES (" + id + "," + termId + "," + sectionId + "," + userId + ",'" + start + "','" + end + "');";
				
				statement = (Statement)(dbConn.createStatement());
				statement.executeUpdate(insertDb);
				
				//Indicate addition of the class into database is successful
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
