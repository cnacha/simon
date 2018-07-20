<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %><html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>Application</title>
   </head>
   <body><form:form method="post" enctype="multipart/form-data" action="saveAddress.do" commandName="obj">
      <form:hidden path="entId" /><br>
      houseNo: <form:input path="houseNo" /><br/>
      street: <form:input path="street" /><br/>
      province: <form:input path="province" /><br/>
      <form:hidden path="employeeEntId" />
      <input type="submit" value="Save"></form:form>
   </body>
</html>