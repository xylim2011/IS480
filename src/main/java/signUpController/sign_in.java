/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signUpController;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;


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
            /* TODO output your page here. You may use following sample code. */
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
            Need to check if current user has his accesstoken saved in our database here before proceeding
            */

            /*
             Check for oauth_token and oauth_verifier. 
             If present, user has authenticated. ->Request for user's access token
             Else Ask Twitter for it first. Twitter's callback url is directed back here
             */
            if (oauth_token == null && oauth_verifier == null) {
                System.out.println("67 Authenticated- no " + oauth_token);
                if (type.equals("twitter")) {
                    JSONObject authToken = twitter.startTwitterAuthentication();
                    String redirectURL = "https://api.twitter.com/oauth/authenticate?oauth_token=" + authToken.getString("oauth_token");
                    response.sendRedirect(redirectURL);
                }
            } 
            else {
               System.out.println("98");
                JSONObject userDetails = twitter.getTwitterAccessTokenFromAuthorizationCode(oauth_token, oauth_verifier);
               
                //save the 4 attributes in database for user
                String user_id = userDetails.getString("user_id");
                String screen_name = userDetails.getString("screen_name");
                String token = userDetails.getString("access_token");
                String secret = userDetails.getString("access_token_secret");
                JSONObject user_session = new JSONObject("{id:'" + user_id +"',username:'" + screen_name + "',token:'" + token + "',secret:'" + secret + "'}");
                session.setAttribute("twitterUser",user_session);
                response.addCookie(new Cookie("i",user_id));
                response.sendRedirect("home");
                
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

}
