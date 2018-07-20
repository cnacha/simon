<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %><html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>Application</title>
   </head>
   <body><form:form method="post" enctype="multipart/form-data" action="saveSaleOrder.do" commandName="obj">
      <form:hidden path="entId" /><br>
      description: <form:input path="description" /><br/>
      amount: <form:input path="amount" /><br/>
      orderDate: <form:input path="orderDate" /><br/>
      price: <form:input path="price" /><br/>
      <form:hidden path="customerEntId" />
      <input type="submit" value="Save"></form:form>
   </body>
</html>