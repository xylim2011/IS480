/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signUpController;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import DAO.UserDAO;
/**
 *
 * @author Sherman
 */
import TwitterEndpoints.Twitter;
@WebServlet(name = "sign_in", urlPatterns = {"/sign_in"})
public class sign_in extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3799289478980759678L;

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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);
        
        try {
            Twitter twitter = new Twitter();
            String type = (String) request.getParameter("type");
            String oauth_token = null;
            String oauth_verifier = null;
            if (request.getParameter("oauth_token") != null) {
                oauth_token = (String) request.getParameter("oauth_token");
            }
            if (request.getParameter("oauth_verifier") != null) {
                oauth_verifier = (String) request.getParameter("oauth_verifier");
            }
            System.out.println("58 token: " + oauth_token);
            System.out.println("59 verifier: " + oauth_verifier);

            /*
             *Check for oauth_token and oauth_verifier. 
             *	If present, user has authenticated. ->Request for user's access token
             *	Else Ask Twitter for it first. Twitter's callback url is directed back here
             */
            if (oauth_token == null && oauth_verifier == null) {
                if (type.equals("twitter")) {
                    JSONObject authToken = twitter.startTwitterAuthentication();
                    String redirectURL = "https://api.twitter.com/oauth/authenticate?oauth_token=" + authToken.getString("oauth_token");
                    response.sendRedirect(redirectURL);
                }
            } 
            else {
               
                JSONObject userDetails = twitter.getTwitterAccessTokenFromAuthorizationCode(oauth_token, oauth_verifier);
                
                if ((userDetails != null) && (userDetails.getString("response_status").equals("success"))) {
                	userDetails.remove("response_status");
	                
	                boolean checkUserAdded = false; 
	                boolean checkUUIDUpdated = false;
	                String uuid = UUID.randomUUID().toString();
	                
	                String userId = userDetails.getString("userid");
	                long id = 0;
	                try{
	                	id = Long.parseLong(userId);
	                } catch (Exception e) {
	                	e.printStackTrace();
	                }
	                
	                userDetails.put("uuid", uuid);
	                
	                JSONObject checking = new JSONObject();
	                checking.put("userid", id);

	            	if(checkUserExist(checking) <= 0){
	                    checkUserAdded = addUser(userDetails);
	            	} else {
		                checking.put("uuid", uuid);
	            		checkUUIDUpdated = updateUUID(checking);
	            	} 
	                
	                if(checkUserAdded){
	                	System.out.println("117 User was added!");
	                } else {
	                	System.out.println("119 User was not added!");
	                }    
	                
	                if(checkUUIDUpdated){
	                	System.out.println("121 User's UUID was updated!");
	                } else {
	                	System.out.println("123 User was not updated!");
	                }
	                
	                
	                
	                session.setAttribute("twitteruser",userDetails);
	                
	                response.addCookie(new Cookie("i",userId + "_" + uuid));
	                response.sendRedirect("home");
                }
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        } 
        finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    
    public static boolean addUser(JSONObject details){
    	boolean toReturn = false;
    	try{
    		if(details != null){
    			toReturn = UserDAO.addUser(details);
    		}
    	} catch (Exception e){
    		e.printStackTrace();
    	}
    	return toReturn;
    }
    
    public static int checkUserExist(JSONObject details){
    	try{
    		if(details != null){
    			return UserDAO.checkUserExist(details);
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return 0;
    }
    
    public static boolean updateUUID(JSONObject details ){
    	try{
    		if(details != null){
    			return UserDAO.updateUUID(details);
    		}
    	} catch (Exception e){
    		e.printStackTrace();
    	}
    	return false;
    }
}