<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>Application</title>
   </head>
   <body>
      <table border="1"><c:forEach items="${objList}" var="obj">
         <tr>
            <td>${obj.entId}</td>
            <td>${obj.firstname}</td>
            <td>${obj.lastname}</td>
            <td>${obj.birthday}</td>
            <td>${obj.photo}</td>
            <td>${obj.salary}</td>
            <td>${obj.position}</td>
            <td><a href="editAddressByEmployee.do?id=${obj.entId}">Edit Address</a></td>
            <td><a href="listPhoneByEmployee.do?id=${obj.entId}">Manage Phone</a></td><td><a href="editEmployee.do?id=${obj.entId}">Edit</a></td><td><a href="deleteEmployee.do?id=${obj.entId}">Remove</a></td>
         </tr></c:forEach>
      </table><a href="newEmployee.do">New Employee</a>
   </body>
</html>