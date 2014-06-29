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

import java.util.UUID;

public class StudentDAO {
	
	//To add student list into DB from createClassController
	public static JSONArray addStudent(JSONArray students) throws SQLException, JSONException{
		Connection dbConn = null;
		Statement statement = null;
		PreparedStatement queryStatement = null;
		ResultSet rs = null;		
		int count = 0;
		JSONArray studentIDs = new JSONArray();
		
		try{
			dbConn = ConnectionManager.getConnection();
			if(students != null){
				for(int i = 0; i < students.length(); i++){
					JSONObject extract = students.getJSONObject(i);
					String name = extract.getString("name");
					String email = extract.getString("email");
					
					String checkDb = "SELECT COUNT(Email) AS counter from STUDENT WHERE ";
					checkDb += "Email='" + email + "';";
					
					queryStatement = dbConn.prepareStatement(checkDb);
					rs = queryStatement.executeQuery();
					
					while(rs.next()){
						count = rs.getInt("counter");
						if(count == 0){//Student does not exist in database and needs to be inserted
							//SQL statement for adding for student
							String insertDb = "INSERT INTO STUDENT (StudentName,Email)";
							insertDb += " VALUES ('" + name + "','" + email + "');";
							
							statement = (Statement)(dbConn.createStatement());
							statement.executeUpdate(insertDb);
							
							String retriveID = "SELECT last_insert_id() AS sID";
							queryStatement = dbConn.prepareStatement(retriveID);
							rs = queryStatement.executeQuery();
							
							JSONObject student = new JSONObject();
							student.put("id",(Integer)rs.getInt("sID"));
							
							studentIDs.put(student);
							
						} else {//Student exists in database
							String retriveID = "SELECT StudentID AS sID from STUDENT WHERE ";
							retriveID += "Email='" + email + "';"; 
							queryStatement = dbConn.prepareStatement(retriveID);
							rs = queryStatement.executeQuery();
							
							JSONObject student = new JSONObject();
							student.put("id", (Integer)rs.getInt("sID"));
							
							studentIDs.put(student);
						}
							
					}
					
					
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}		
		finally{
			ConnectionManager.close(dbConn, statement, rs);
		}
		return studentIDs;
	}
	
	//To return the number of students whom successfully provided their twitter username to the system
	public static int numberOfStudents(String crsId, String termId, int grpId){
		Connection dbConn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		int toReturn = 0;
		
		try{
			dbConn = ConnectionManager.getConnection();
			if(crsId != null && termId != null && grpId > 0){
				String queryDb = "SELECT COUNT(*) from ENROLLED";
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

