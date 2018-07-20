<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>Application</title>
   </head>
   <body><a href="listEmployee.do">Employee</a> / Phone<br><table border="1"><c:forEach items="${objList}" var="obj">
         <tr>
            <td>${obj.entId}</td>
            <td>${obj.label}</td>
            <td>${obj.number}</td><td><a href="editPhone.do?id=${obj.entId}">Edit</a></td><td><a href="deletePhone.do?id=${obj.entId}">Remove</a></td>
         </tr></c:forEach>
      </table><a href="newPhone.do">New Phone</a>
   </body>
</html>