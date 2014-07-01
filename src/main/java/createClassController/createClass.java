package createClassController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
			throws ServletException, IOException, JSONException{
		
		response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);
        String userId = ((JSONObject) session.getAttribute("twitterUser")).getString("id");
        long uID = Long.parseLong(userId);
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY HH:mm");
        
        //Data from the createClass.jsp
        String CourseCode = request.getParameter("CourseCode");
        String TermName = request.getParameter("TermName");
        String SectionName = request.getParameter("SectionName");
        String CourseName = request.getParameter("CourseName");
        
        String startDate = request.getParameter("startDate");
        String startTime = request.getParameter("startTime");
        String classStart = startDate + " " + startTime;
        System.out.println("\n53 Start: " + classStart);
        Calendar cal_start = Calendar.getInstance();
        
        try{
        	cal_start.setTime(sdf.parse(classStart));
        } catch (Exception e){
        	e.printStackTrace();
        }
        
        long start = cal_start.getTimeInMillis();
        
        String endDate = request.getParameter("endDate");
        String endTime = request.getParameter("endTime");
        String classEnd = endDate + " " + endTime;
        System.out.println("67 End: " + classEnd);
        Calendar cal_end = Calendar.getInstance();
        
        try{
        	cal_end.setTime(sdf.parse(classEnd));
        } catch (Exception e){
        	e.printStackTrace();
        }
        long end = cal_end.getTimeInMillis();
        
        String fileName = request.getParameter("fileName");
        
/*____________________________________________________________________________________________________*/
        
/*
 * @param 	This section is for checking of the course details after retrieving the user's input
 * 			Two main methods are used - getCourseID and addCourse
 */
        
        //To create the JSONObject of the course details with the course code and course name
        JSONObject courseDetails = new JSONObject();
        courseDetails.put("CourseCode", CourseCode);
    	courseDetails.put("CourseName", CourseName);
    	
    	//To check whether course exists in database
    	int courseID = getCourseID(courseDetails);
        //If course does not exist, a value of -1 will be received here and the course will be added
        if(courseID < 0){        	
			boolean status_course = addCourse(courseDetails);
			//To retrieve the courseID after adding the course into database
			if(status_course){
				courseID = getCourseID(courseDetails);
			} else {
				System.out.println ("100 Course was not added");
			}
        }
/*____________________________________________________________________________________________________*/
        
/*
 * @param 	This section is for checking of the term details after retrieving the user's input
 * 			Two main methods are used - getTermID and addTerm
 */
        
        //To create JSONObject of the term details with the term name
    	JSONObject termDetails = new JSONObject();
    	termDetails.put("TermName", TermName);
        
    	//To check whether term exists in database
    	int termID = getTermID(termDetails);
    	System.out.println("118: "+termID);
    	//If term does not exist, a value of -1 will be received here and the term will be added
        if(termID < 0){
			boolean status_term = addTerm(termDetails);
			//To retrieve the TermID after adding the course into database
			System.out.println("123: "+status_term);
			if(status_term){
				termID = getTermID(termDetails);
			} else {
				System.out.println ("123 Term was not added");
			}
        }
/*____________________________________________________________________________________________________*/

/*
 * @param 	This section is for checking of the section details after retrieving the user's input
 * 			Two main methods are used - getSectionID and addSection
 */
    	
        //To create JSONObject of the section details with the section name
    	JSONObject sectionDetails = new JSONObject();
    	sectionDetails.put("SectionName", SectionName);
        
    	//To check whether section exists in database
    	int sectionID = getSectionID(sectionDetails);
    	//If section does not exist, a value of -1 will be received here and the section will be added
        if(sectionID < 0){
        	boolean status_section = addSection(sectionDetails);
        	//To retrieve the SectionID after adding the course into database
        	if(status_section){
        		sectionID = getSectionID(sectionDetails);
        	} else {
        		System.out.println ("146 Section was not added");
        	}
        }
        
/*____________________________________________________________________________________________________*/
        
/*
 * @param 	This section is for adding of students after user uploaded the csv file
 * 			The method used to add student into the database is addStudents
 */
        
        JSONArray studentIdList = addStudents("C:/Users/chusung/Desktop/Studentlist.csv");
        if(studentIdList.length()<=0){
        	System.out.println("159 Students were not added!");
        } else {
        	System.out.println("160 " + studentIdList.length() + " students were added");
        }
/*____________________________________________________________________________________________________*/
        
/*
 * @param 	This section is for adding of class into the database
 * 			The method used to add student into the database is addClass
 */
        
        //To create JSONObject of the class details
        JSONObject classDetails = new JSONObject();
        classDetails.put("CourseID", courseID);
        classDetails.put("TermID", termID);
        classDetails.put("SectionID", sectionID);
        classDetails.put("UserID", uID);
        classDetails.put("StartTime", start);
        classDetails.put("EndTime", end);
        
        boolean status_class = addClass(classDetails);
        if(status_class){
        	System.out.println("176 Class added successfully!");
        } else {
        	System.out.println("178 Class was not added!");
        }
/*____________________________________________________________________________________________________*/

/*
 * @param 	This section is for adding of enrolled into the database
 * 			The method used to add enrolled into the database is addEnrolled
 */
        
        JSONArray enrolled = new JSONArray();
        for(int i=0; i<studentIdList.length();i++){
        	int id = studentIdList.getInt(i);
        	JSONObject info = new JSONObject();
        	info.put("StudentID", id);
        	info.put("CourseID", courseID);
        	info.put("TermID", termID);
        	info.put("SectionID", sectionID);
        	int score = 0;
        	info.put("Score", score);
        	UUID uuid = UUID.randomUUID();
        	info.put("UUID", uuid.toString());
        	
        	enrolled.put(info);
        }
        
        boolean status_enrolled = addEnrolled(enrolled);
        if(status_enrolled){
        	System.out.println("209 Enrolled added successfully!");
        } else {
        	System.out.println("211 Enrolled was not added!");
        }
/*____________________________________________________________________________________________________*/
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
   
/*____________________________________________________________________________________________________*/

/*
 * @param 	This method is for adding of students into the database via StudentDAO
 */
    private JSONArray addStudents(String fileName){
    	String csvFile = fileName;   
		BufferedReader br = null;
		String line = "";
        String csvSplitBy = ",";
        JSONArray studentList = new JSONArray();
        JSONArray studentIDs = new JSONArray();
        
        if(fileName != null){
	        try {
	        	br = new BufferedReader (new FileReader(csvFile));
	        	while ((line = br.readLine()) != null){
	        		String[] student =  line.split(csvSplitBy);
	        		JSONObject studentDetails = new JSONObject();
	        		
	        		for(int i=0; i<student.length; i++){
	        			try{
	        				Integer.parseInt(student[i]);
	        			} catch (Exception e) {
	        				boolean verifyEmail = checkWhetherEmail(student[i]);
	        				if(verifyEmail){
	        					studentDetails.put("email",student[i]);
	        				} else {
	        					studentDetails.put("name",student[i]); 
	        				}
	        			} 
	        		}
	               	studentList.put(studentDetails);
	        	}
	        	//Calling the StudentDAO to add the students into database
	        	studentIDs = StudentDAO.addStudent(studentList);
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
        return studentIDs; //JSONArray of all student IDs only
    }
/*____________________________________________________________________________________________________*/

/*
 * @param 	This method is for adding of classes into the database via ClassDAO		
 */
    private boolean addClass(JSONObject classDetails){
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
/*____________________________________________________________________________________________________*/

/*
 * @param 	This method is for adding of enrolled into the database via EnrolledDAO		
 */
    private boolean addEnrolled(JSONArray enrolled){
    	boolean status = false;
    	if(enrolled != null){
    		try{
    			status = EnrolledDAO.addEnrolled(enrolled);
    		} catch (Exception e){
    			e.printStackTrace();
    		}
    		
    	}
    	return status;
    }
/*____________________________________________________________________________________________________*/

/*
 * @param 	This method is for adding of classes into the database via ClassDAO		
 */
    private boolean addCourse(JSONObject details){
    	boolean toReturn = false;
    	try{
    		toReturn = CourseDAO.addCourse(details);
    	} catch (Exception e){
    		e.printStackTrace();
    	}
    	return toReturn;
    }
/*____________________________________________________________________________________________________*/

/*
 * @param 	This method is for getting specific course ID from the database via CourseDAO
 */		
    private int getCourseID(JSONObject details){
    	if(details != null){
    		try{
    			return CourseDAO.getCourseID(details);
    		} catch (Exception e){
    			e.printStackTrace();
    		}
    	}
		return -1;
    }    
/*____________________________________________________________________________________________________*/  
    
/*
 * @param 	This method is for adding of terms into the database via TermDAO
 */		
    private boolean addTerm(JSONObject details){
    	boolean toReturn = false;
    	if(details != null){
    		try{
    			toReturn = TermDAO.addTerm(details);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
    	return toReturn;
    }
/*____________________________________________________________________________________________________*/  
    
/*
 * @param 	This method is for getting specific term ID from the database via TermDAO
 */	    
    private int getTermID(JSONObject details){
    	if(details != null){
    		try{
    			return TermDAO.getTermID(details);
    		} catch (Exception e){
    			e.printStackTrace();
    		}
    	}
    	return -1;
    }
/*____________________________________________________________________________________________________*/ 

/*
 * @param 	This method is for getting specific section ID from the database via SectionDAO
 */	
    
    private int getSectionID(JSONObject details){
    	if(details != null){
    		try{
    			return SectionDAO.getSectionID(details);
    		} catch (Exception e){
    			e.printStackTrace();
    		}
    	}
    	return -1;
    }
/*____________________________________________________________________________________________________*/ 
    
/*
 * @param 	This method is for adding of sections into the database via SectionDAO
 */	
    
    private boolean addSection(JSONObject details){
    	boolean toReturn = false;
    	if(details != null){
    		try{
    			toReturn = SectionDAO.addSection(details);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
    	return toReturn;
    }
/*____________________________________________________________________________________________________*/ 

/*
 * @param 	This method is for checking whether an entry in the csv is an email address or not
 */	
        
    private boolean checkWhetherEmail(String address){
    	Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(address);
        if(mat.matches()){
            return true;
        }else{
        	return false;
        }
    }
/*____________________________________________________________________________________________________*/ 
}
