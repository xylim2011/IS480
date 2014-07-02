package welcomeController;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import DAO.UserDAO;
/**
 * Servlet implementation class welcome
 */
@WebServlet(name = "welcome", urlPatterns = {"/welcome"})
public class welcome extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	String redirect = "index";
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
        	for (int i = 0; i < cookies.length; i++) {
        		if (cookies[i].getName().equals("i")) {
        			// split cookie based on our format: <twitterid>_<uuid> into a string array
        			if (cookies[i].getValue() != null) {
	        			String[] cookie = cookies[i].getValue().split("_");
	        			// check if string array is valid
	        			if (cookie.length == 2) {
	        				JSONObject userDetails = new JSONObject();
	        				try{
	        					JSONObject authDetails = new JSONObject();
	        					authDetails.put("userid", cookie[0]);
	        					authDetails.put("uuid", cookie[1]);
	        					userDetails = UserDAO.getUser(authDetails);
	        					if (userDetails != null) {
	        						redirect = "home";
	        					}
	        				}
	        				catch (Exception e) {
	        					e.printStackTrace();
	        				}
	        			}
	        			break;
        			}
        		}
        	}
        }
    	response.sendRedirect(redirect);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

}
