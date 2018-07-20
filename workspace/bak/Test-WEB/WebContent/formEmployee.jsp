<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %><html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>Application</title>
   </head>
   <body><form:form method="post" enctype="multipart/form-data" action="saveEmployee.do" commandName="obj">
      <form:hidden path="entId" /><br>
      firstname: <form:input path="firstname" /><br/>
      lastname: <form:input path="lastname" /><br/>
      birthday: <form:input path="birthday" /><br/>
      photo: <form:input path="photoData" type="file" /><br>
      ${obj.photo}<a href="filestore/${obj.photo}">Download File</a><br/>
      <form:hidden path="photo" />
      salary: <form:input path="salary" /><br/>
      position: <form:input path="position" /><br/>
      <input type="submit" value="Save"></form:form>
   </body>
</html>