package getClassController;

import TwitterEndpoints.Twitter;

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

import DAO.ClassDAO;
import org.json.JSONArray;

@WebServlet(name = "get_class", urlPatterns = {"/get_class"})
public class getClass extends HttpServlet {
	
	private String userId = null;
	private String userName = null;	

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, JSONException {
		response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);
        String userId = (String) request.getParameter("userID");
        try{
        	JSONObject userDetails = (JSONObject)session.getAttribute("twitterUser");
        	JSONArray classes = ClassDAO.getClassTaught(userId);
        	out.println(classes);
        } catch (Exception e){
        	e.printStackTrace();
        }
        
        /*
         * Get the list of classes taught by the admin user based on the userId
         */
        
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
