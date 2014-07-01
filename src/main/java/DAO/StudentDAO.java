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

public class StudentDAO {
	
/*____________________________________________________________________________________________________*/
/*
 * @param	This method is to add the students into database based on the csv file uploaded by user 
 */	
	
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
							
							String retrieveID = "SELECT last_insert_id() AS sID";
							queryStatement = dbConn.prepareStatement(retrieveID);
							rs = queryStatement.executeQuery();
							
							while (rs.next()) {
								studentIDs.put(rs.getInt("sID"));
							}
							
						} else {//Student exists in database
							String retrieveID = "SELECT StudentID AS sID from STUDENT WHERE ";
							retrieveID += "Email='" + email + "';"; 
							queryStatement = dbConn.prepareStatement(retrieveID);
							rs = queryStatement.executeQuery();

							while (rs.next()) {
								studentIDs.put(rs.getInt("sID"));
							}
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
	
/*____________________________________________________________________________________________________*/
/*
 * @param	This method is to get the number of students from a specific class based on the following:
 * 			CourseID, TermID, GroupID from a JSONObject details
 */	
	
	public static int numberOfStudents(JSONObject details){
		Connection dbConn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		int toReturn = 0;
		
		try{
			dbConn = ConnectionManager.getConnection();
			if(details != null){
				String queryDb = "SELECT COUNT(*) AS studentsNum from ENROLLED";
				queryDb += " WHERE CourseId=" + details.getInt("CourseID");
				queryDb += " AND TermId=" + details.getInt("TermID");
				queryDb += " AND GroupId=" + details.getInt("GroupID");
				queryDb += ";"; 
						
				statement = dbConn.prepareStatement(queryDb);
				rs = statement.executeQuery();
			}
			toReturn = rs.getInt("studentsNum");
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			ConnectionManager.close(dbConn, statement, rs);
		}
		return toReturn;
		
	}
}

