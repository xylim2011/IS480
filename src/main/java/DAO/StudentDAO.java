package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.util.ConnectionManager;

public class StudentDAO {
	
	public static boolean addStudent(JSONArray students) throws SQLException, JSONException{
		Connection dbConn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;		
		boolean status = false;
		try{
			dbConn = ConnectionManager.getConnection();
			if(students != null){
				for(int i = 0; i < students.length(); i++){
					JSONObject extract = students.getJSONObject(i);
					String name = extract.getString("name");
					String email = extract.getString("email");
					
					//check the exact naming convention of the metadata in DB
					String queryDb = "INSERT INTO STUDENT (Name,Email)";
					queryDb += " VALUES (" + name + "," + email + ");";
					
					statement = dbConn.prepareStatement(queryDb);
					rs = statement.executeQuery();
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
	
	public static int numberOfStudents(String crsId, String termId, int grpId){
		Connection dbConn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		int toReturn = 0;
		
		try{
			dbConn = ConnectionManager.getConnection();
			if(crsId != null && termId != null && grpId > 0){
				String queryDb = "SELECT COUNT(*) from STUDENT";
				queryDb += " WHERE CourseId=" + crsId;
				queryDb += " AND TermId=" + termId;
				queryDb += " AND GroupId=" + grpId;
				queryDb += ";"; 
						
				statement = dbConn.prepareStatement(queryDb);
				rs = statement.executeQuery();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			ConnectionManager.close(dbConn, statement, rs);
		}
		return toReturn;
		
	}
}

