<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>Application</title>
   </head>
   <body>
      <table border="1"><c:forEach items="${objList}" var="obj">
         <tr>
            <td>${obj.entId}</td>
            <td>${obj.name}</td>
            <td>${obj.phone}</td>
            <td>${obj.address}</td>
            <td>${obj.photo}</td>
            <td><a href="listSaleOrderByCustomer.do?id=${obj.entId}">Manage SaleOrder</a></td><td><a href="editCustomer.do?id=${obj.entId}">Edit</a></td><td><a href="deleteCustomer.do?id=${obj.entId}">Remove</a></td>
         </tr></c:forEach>
      </table><a href="newCustomer.do">New Customer</a>
   </body>
</html>