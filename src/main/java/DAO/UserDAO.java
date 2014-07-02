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

public class UserDAO {
	
/*____________________________________________________________________________________________________*/
	
/*
 * @param	This method is to add the a logged-in user into database
 */	
	
	public static boolean addUser(JSONObject details) throws SQLException, JSONException{
		Connection dbConn = null;
		Statement statement = null;
		ResultSet rs = null;		
		boolean status = false;
		
		try{
			dbConn = ConnectionManager.getConnection();
			if(details != null){
	
				String insertDb = "INSERT INTO user (UserID,UUID,AccessToken,AccessSecret)";
				insertDb += " VALUES (" + details.getLong("userid") + ",'";
				insertDb += details.getString("uuid") + "','";
				insertDb += details.getString("token") + "','";
				insertDb += details.getString("secret") + "');";
										
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
 * @param	This method is to check whether a specific user exists in the database based on a userID
 */	
	
	public static int checkUserExist(JSONObject details) {
		Connection dbConn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;		
		int toReturn = 0;
		
		try{
			dbConn = ConnectionManager.getConnection();
			if(details != null){
				
				String queryDb = "SELECT COUNT(*) AS result FROM user WHERE ";
				queryDb += "UserID=" + details.getLong("userid") + ";";

				statement = dbConn.prepareStatement(queryDb);
				rs = statement.executeQuery();
			
				while(rs.next()){
					toReturn = rs.getInt("result");
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

/*
 * @param	This method is to update a specific user's UUID in the database based on a userID
 */	
	public static boolean updateUUID(JSONObject details){
		Connection dbConn = null;
		Statement statement = null;
		ResultSet rs = null;
		boolean toReturn = false;
		
		try{
			dbConn = ConnectionManager.getConnection();
			if(details != null){
				String updateDb = "UPDATE user SET ";
				updateDb += "UUID='" + details.getString("uuid");
				updateDb += "' WHERE UserID=" + details.getLong("userid") + ";";

				statement = (Statement)(dbConn.createStatement());
				statement.executeUpdate(updateDb);
				
				toReturn = true;
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

/*
 * @param	This method is to get user credentials by authenticating cookie information
 */	
	
	public static JSONObject getUser(JSONObject details) throws SQLException, JSONException{
		Connection dbConn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		JSONObject userDetails = null;
		
		try{
			dbConn = ConnectionManager.getConnection();
			if(details != null){
				String queryDb = "SELECT * FROM user WHERE ";
				queryDb += "UserID=" + details.getLong("userid") + " AND UUID='" + details.getString("uuid") + "';" ;

				statement = dbConn.prepareStatement(queryDb);
				rs = statement.executeQuery();
			
				while(rs.next()){
					userDetails = new JSONObject();
					userDetails.put("userid", rs.getLong("UserID"));
					userDetails.put("uuid", rs.getString("UUID"));
					userDetails.put("token", rs.getString("AccessToken"));
					userDetails.put("secret", rs.getString("AccessSecret"));
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}		
		finally{
			ConnectionManager.close(dbConn, statement, rs);
		}
		return userDetails;
	}	
}
