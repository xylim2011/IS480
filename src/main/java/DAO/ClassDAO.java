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
}