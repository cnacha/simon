<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %><html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>Application</title>
   </head>
   <body><form:form method="post" enctype="multipart/form-data" action="saveCustomer.do" commandName="obj">
      <form:hidden path="entId" /><br>
      name: <form:input path="name" /><br/>
      phone: <form:input path="phone" /><br/>
      address: <form:input path="address" /><br/>
      photo: <form:input path="photoData" type="file" /><br>
      ${obj.photo}<a href="filestore/${obj.photo}">Download File</a><br/>
      <form:hidden path="photo" />
      <input type="submit" value="Save"></form:form>
   </body>
</html>