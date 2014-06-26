package getClassController;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

@WebServlet(name = "get_class", urlPatterns = {"/get_class"})
public class getClass extends HttpServlet {
	
	private String userId = null;
	private String userName = null;
	private JSONObject taught = null;
	

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, JSONException {
		response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);
        
        try{
        	JSONObject userDetails = (JSONObject)session.getAttribute("twitterUser");
        	userId = userDetails.getString("id");
        	userName = userDetails.getString("username");
        	taught = getClassTaught(userId);
        } catch (Exception e){
        	e.printStackTrace();
        }
        
        /*
         * Get the list of classes taught by the admin user based on the userId
         */
        
	}
	
	public static JSONObject getClassTaught(String id) throws JSONException{
		String mod_code = null;
		String term = null;
		JSONObject classTaught = null;
		
		if (id != null){
			
			classTaught = new JSONObject("{code:'" + mod_code + "',term: '" + term +"'}");
		}
		
		return classTaught;
		
	}
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
			processRequest(request, response);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
			processRequest(request, response);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
