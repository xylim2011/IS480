package createClassController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import DAO.*;

import org.json.JSONArray;

@WebServlet(name = "create_class", urlPatterns = {"/create_class"})

public class createClass extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, JSONException {
		
		response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);
        String userId = (String) ((JSONObject) session.getAttribute("twitterUser")).getString("id");
        
        //Data from the createClass.jsp
        String classId = request.getParameter("classId");
        String termId = request.getParameter("termId");
        String form_id = request.getParameter("grpId");
        int grpId = Integer.parseInt(form_id);
        String crsName = request.getParameter("crsName");
        String fileName = request.getParameter("fileName");
        
        JSONObject classDetails = 
        		new JSONObject("{id:'" + classId + "',termId:'" + termId + "',grpId:'" + grpId + "',crsName:" + crsName);
        
        
        boolean checkStudentAdded = studentsAdded(fileName);
		boolean checkClassAdded = classAdded(classDetails);
		        
        if(checkStudentAdded){
        	int num = StudentDAO.numberOfStudents(classId,termId,grpId);
			out.print(num + " students were added successfully!");
		} else {
			out.println("Students were NOT added!");
		}
        if(checkClassAdded){
        	out.println("Class was added successfully!");
        } else {
        	out.println("Class was NOT added successfully!");
        }
        
        /* For future use
        if(classAdded && studentAdded){
        	response.sendRedirect("home");
        }
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
    
    private boolean studentsAdded(String fileName){
    	String csvFile = fileName;   
		BufferedReader br = null;
		String line = "";
        String csvSplitBy = ",";
        JSONArray studentList = new JSONArray();
        boolean status = false;
        
        if(fileName != null){
	        try {
	        	br = new BufferedReader (new FileReader(csvFile));
	        	while ((line = br.readLine()) != null){
	        		String[] student =  line.split(csvSplitBy);
	        		JSONObject studentDetails = new JSONObject();
	        		studentDetails.put("name",student[1]); 
	        		studentDetails.put("email",student[2]);
	        		studentList.put(studentDetails);
	        	}
	        	status = StudentDAO.addStudent(studentList);
			 } catch (Exception e){
				 e.printStackTrace();
			 } finally {
				 if(br != null){
					 try{
						 br.close();
					 } catch (IOException e){
						 e.printStackTrace();
					 }
				 }
			 }
        }
        return status;
    }
    
    private boolean classAdded(JSONObject classDetails){
    	boolean status = false;
    	if(classDetails != null){
	    	try {
				status = ClassDAO.addClass(classDetails);
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	return status;
    }
}
