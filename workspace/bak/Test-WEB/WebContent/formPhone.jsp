<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %><html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>Application</title>
   </head>
   <body><form:form method="post" enctype="multipart/form-data" action="savePhone.do" commandName="obj">
      <form:hidden path="entId" /><br>
      label: <form:input path="label" /><br/>
      number: <form:input path="number" /><br/>
      <form:hidden path="employeeEntId" />
      <input type="submit" value="Save"></form:form>
   </body>
</html>