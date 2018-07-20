<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>Application</title>
   </head>
   <body><a href="listEmployee.do">Employee</a> / Address<br><table border="1"><c:forEach items="${objList}" var="obj">
         <tr>
            <td>${obj.entId}</td>
            <td>${obj.houseNo}</td>
            <td>${obj.street}</td>
            <td>${obj.province}</td><td><a href="editAddress.do?id=${obj.entId}">Edit</a></td><td><a href="deleteAddress.do?id=${obj.entId}">Remove</a></td>
         </tr></c:forEach>
      </table><a href="newAddress.do">New Address</a>
   </body>
</html>