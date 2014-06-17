package matchUserController;


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

	import java.io.BufferedReader;
	import java.io.FileReader;
	import java.io.FileWriter;
	import java.util.StringTokenizer;
	import java.util.*;

	/**
	 *
	 * @author lee Jin
	 */
	@WebServlet(name = "matchUser", urlPatterns = {"/matchUser"})
	public class matchUser  extends HttpServlet {
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
			 try {
				      //csv file containing data
				 //csv file containing data
				  String strFile = "C:\\fyp\\classList.csv";
				  String tweetUser = "qc";
				  String tweetUserName = "";
				  BufferedReader reader = new BufferedReader(new FileReader(strFile));
				  String nextLine = "";
				  List<List<String>> classInformation = new ArrayList<List<String>>();
					ArrayList<String> users = new ArrayList<String>();
					ArrayList<String> IDs = new ArrayList<String>();
				  while ((nextLine = reader.readLine()) != null) {
					
					StringTokenizer st = null;

					st = new StringTokenizer(nextLine, ",");
					int counter = 0;
					String token = "";
					
					while(st.hasMoreTokens())
					{
							//display csv values
							token = st.nextToken();
							System.out.println(
											"Token : "+ token);
							if (counter == 0){
								users.add(token);
							}else{
								IDs.add(token);
							}
						counter = 1;
					// nextLine[] is an array of values from the line
					}
					System.out.println("break");
				  }
				  classInformation.add(users);
				  classInformation.add(IDs);
				  for (int i =0 ;classInformation.get(1).size()>i;i++){
				  
					if(classInformation.get(1).get(i).equals(tweetUser)){
						tweetUserName =classInformation.get(0).get(i);
					}
				}
				if(tweetUserName.equals("")){
					System.out.println("tweet is not in Class list");
				}else {
					System.out.println("real name is : " + tweetUserName);
				}
				      
			 }catch(Exception e){
				 
			 }finally{
				 
			 }
			 
		 }
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

