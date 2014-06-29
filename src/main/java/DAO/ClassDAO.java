package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.util.ConnectionManager;

public class ClassDAO {
	
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
	
	public static boolean addClass(JSONObject classDetails) throws SQLException, JSONException{
		Connection dbConn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;		
		boolean status = false;
		try{
			dbConn = ConnectionManager.getConnection();
			if(classDetails != null){
				String id = classDetails.getString("id");
				int termId = classDetails.getInt("termId");
				String grpId = classDetails.getString("grpId");
				String crsName = classDetails.getString("crsName");
				
				String queryDb = "INSERT INTO CLASS (CourseID,CourseName,TermID,GroupId)";
				queryDb += " VALUES (" + id + "," + crsName + "," + termId + "," + grpId + ");";
				
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
	
	public static int getTermID(String termName){
		Connection dbConn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;		
		int toReturn = -1;
		try{
			dbConn = ConnectionManager.getConnection();
			if(termName != null){
		
				String queryDb = "SELECT TermID FROM term WHERE ";
				queryDb += " TermName='" + termName + "';";

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
	
	public static int getSectionID(String sectionName){
		Connection dbConn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;		
		int toReturn = -1;
		try{
			dbConn = ConnectionManager.getConnection();
			if(sectionName != null){
		
				String queryDb = "SELECT SectionID FROM section WHERE ";
				queryDb += " SetionName='" + sectionName + "';";

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
	
}
