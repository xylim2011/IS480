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
        Calendar cal_end = Calendar.getInstance();
        try{
        	cal_end.setTime(sdf.parse(classEnd));
        } catch (Exception e){
        	e.printStackTrace();
        }
        long end = cal_end.getTimeInMillis();
        
        String fileName = request.getParameter("fileName");
        
        int courseID = getCourseID(CourseCode,CourseName);
        boolean status = false;
        if(courseID < 0){
        	JSONObject details = new JSONObject();
        	details.put("crsCode", CourseCode);
        	details.put("crsName", CourseName);
			status = addCourse(details);
			if(status){
				courseID = getCourseID(CourseCode,CourseName);
			} else {
				System.out.println ("86 Course not added");
			}
        }
        
    	int termID = getTermID(TermName);
        int sectionID = getSectionID(SectionName);
        
        JSONArray StudentAdded = studentsAdded("C:/Users/chusung/Desktop/Studentlist.csv");
        System.out.println(StudentAdded.length());
        
        JSONObject classDetails = new JSONObject();
        classDetails.put("CourseID", courseID);
        classDetails.put("TermID", termID);
        classDetails.put("SectionID", sectionID);
        classDetails.put("UserID", uID);
        classDetails.put("StartTime", start);
        classDetails.put("EndTime", end);
                
        JSONArray enrolled = new JSONArray();
        for(int i=0; i<StudentAdded.length();i++){
        	String extract = StudentAdded.getString(i);
        	int id = Integer.parseInt(extract);
        	JSONObject info = new JSONObject();
        	info.put("StudentID", id);
        	info.put("CourseID", courseID);
        	info.put("TermID", termID);
        	info.put("SectionID", sectionID);
        	UUID uuid = UUID.randomUUID();
        	info.put("UUID", uuid.toString());
        	
        	enrolled.put(info);
        }
		
        boolean checkEnrolledAdded = enrolledAdded(enrolled);
        //boolean checkClassAdded = classAdded(classDetails);
		/*
        if(checkStudentAdded){
        	//int num = StudentDAO.numberOfStudents(classId,termId,grpId);
			out.print("Students were added successfully!");
		} else {
			out.println("Students were NOT added!");
		}
        if(checkClassAdded){
        	out.println("Class was added successfully!");
        } else {
        	out.println("Class was NOT added successfully!");
        }
        */
        
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
    
    private JSONArray studentsAdded(String fileName){
    	String csvFile = fileName;   
		BufferedReader br = null;
		String line = "";
        String csvSplitBy = ",";
        JSONArray studentList = new JSONArray();
        JSONArray studentIDs = new JSONArray();
        boolean status = false;
        
        if(fileName != null){
	        try {
	        	br = new BufferedReader (new FileReader(csvFile));
	        	while ((line = br.readLine()) != null){
	        		String uuid = UUID.randomUUID().toString();
	        		String[] student =  line.split(csvSplitBy);
	        		JSONObject studentDetails = new JSONObject();
	        		studentDetails.put("name",student[1]); 
	        		studentDetails.put("email",student[2]);
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
        System.out.println(studentList.length());
        return studentIDs; //JSONArray of all student IDs only
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
    
    private boolean enrolledAdded(JSONArray enrolled){
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
    
    private boolean addCourse(JSONObject details){
    	boolean toReturn = false;
    	try{
    		toReturn = CourseDAO.addCourse(details);
    	} catch (Exception e){
    		e.printStackTrace();
    	}
    	return toReturn;
    }
    
    
    
    private int getCourseID(String courseCode, String courseName){
    	if(courseName != null){
    		try{
    			return CourseDAO.getCourseID(courseCode,courseName);
    		} catch (Exception e){
    			e.printStackTrace();
    		}
    	}
		return -1;
    }
    
    private int getTermID(String termName){
    	if(termName != null){
    		try{
    			return ClassDAO.getTermID(termName);
    		} catch (Exception e){
    			e.printStackTrace();
    		}
    	}
    	return -1;
    }
    
    private int getSectionID(String sectionName){
    	if(sectionName != null){
    		try{
    			return ClassDAO.getSectionID(sectionName);
    		} catch (Exception e){
    			e.printStackTrace();
    		}
    	}
    	return -1;
    }
}
