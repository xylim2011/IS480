<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create a new class</title>
</head>
<body>
	<form name="CreateClass" action="createclass" method="get">
		<fieldset>
			<legend>Create A New Class</legend>
			<fieldset>
	   			<label>Course Code: </label>
				<input class="input-xlarge" type="text" placeholder="IS425" name="CourseCode" />
				<br><br>
				<label>Course Name: </label>
				<input class="input-xlarge" type="text" placeholder="AISM" name="CourseName" />
				<br><br>
				<label>Term: </label>
				<input class="input-xlarge" type="text" placeholder="1" name="TermName" />
				<br><br>
				<label>Section/Class:</label>
				<input class="input-xlarge" type="text" placeholder="G1" name="SectionName" />
	  		</fieldset>
	  		 		
	  		<fieldset>
	  			<label> Start Date: </label>
	  			<input class="input-large" type="text" placeholder="dd/mm/yyyy" name="startDate"><br>
	  			<br>
	  			<label> Start Time: </label>
	  			<input class="input-large" type="text" placeholder="HH:mm" name="startTime"><br>
	  			<br>
	  			<label> End Date: </label>
	  			<input class="input-large" type="text" placeholder="dd/mm/yyyy" name="endDate"><br>
	  			<br>
	  			<label> End Time: </label>
	  			<input class="input-large" type="text" placeholder="HH:mm" name="endTime"><br>
	  		</fieldset>
	
			<fieldset>
				<label>Select a csv file to upload</label>
				<br>
				<input type="file" accept=".csv" ID="fileSelect"/><br>
				<button onclick = "myfunction()">Upload File</button>
				<script>
					function myfunction(){
						var x = document.getElementById("myFile");
						x.disabled = true;
						out.print(x);
					}
				</script>
			</fieldset>
		</fieldset>
		<button type="submit" >Submit</button>
	</form> 
</body>
</html>