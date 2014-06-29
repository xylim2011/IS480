/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Get;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.json.DataObjectFactory;
import TwitterEndpoints.*;
/**
 *
 * @author Sherman
 */
@WebServlet(name = "getTweets", urlPatterns = {"/getTweets"})
public class getTweets extends HttpServlet {

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
        try {
            //get word searched
            String searchItem = (String) request.getParameter("keyword");

            String load = null;
            String update = null;
            if (request.getParameter("load") != null) {
                load = (String) request.getParameter("load");
            }
            if (request.getParameter("update") != null) {
                update = (String) request.getParameter("update");
            }

            //set up keys locally
            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setJSONStoreEnabled(true);
            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey("NZ1vO3msP4o10b3dh3XVmHLfZ")
                    .setOAuthConsumerSecret("WwtUvjnyLAPXmghFBARbmcxDgVw06QwiMmpwBGCyP8XQWsmcXr")
                    .setOAuthAccessToken("2331328867-qgnT1qjFEwFtwO4bGNKWO7wznvDL9nKcZkFcb7t")
                    .setOAuthAccessTokenSecret("hkocXDkZPGYuBLNU6lSw76DT2JakyqXk1yOzWjgmY8MxK");

            TwitterFactory tf = new TwitterFactory(cb.build());
            Twitter twitter = tf.getInstance();
            JSONObject batch = new JSONObject();
      
            try {
               // Query query = new Query(searchItem + "+exclude:retweets");
                 Query query = new Query(searchItem);
                long latest_tweet = 0;
                long oldest_tweet = 0;
                //query.setLang("en");
                query.setCount(25);
                query.setResultType(Query.RECENT);
                if (load != null) {
                    query.setMaxId(Long.parseLong(load) - 1);
                }else if(update != null){
                    query.setSinceId(Long.parseLong(update) + 1);
                }
                QueryResult result;
                //do{
                result = twitter.search(query);
                List<Status> tweets = result.getTweets();
               
                JSONArray statuses = new JSONArray();
                for (int i = 0; i < tweets.size(); i++) {
                    Status tweet = tweets.get(i);
                    JSONObject twit = new JSONObject(DataObjectFactory.getRawJSON(tweet));
                    System.out.println(twit);
                    if (i == tweets.size() - 1) {
                        oldest_tweet = tweet.getId();
                    } else if (i == 0) {
                        latest_tweet = tweet.getId();
                    }
                   
                    statuses.put(twit);
                }
                //} while ((query = result.nextQuery()) != null);
                //get STATUSES JSON ARRAY FROM HERE
                //statuses =  TwitterDAO.categorizeweets(statuses);
                
                
                batch.put("tweets", statuses);
                batch.put("latest", latest_tweet);
                batch.put("oldest", oldest_tweet);
                out.println(batch);
                //  query.setSinceId(lastID);
            } catch (Exception te) {
                te.printStackTrace();
                System.out.println("Failed to search tweets: " + te.getMessage());
                System.exit(-1);
            }
        } finally {
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
