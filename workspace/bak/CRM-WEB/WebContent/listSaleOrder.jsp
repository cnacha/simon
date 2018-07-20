<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>Application</title>
   </head>
   <body><a href="listCustomer.do">Customer</a> / SaleOrder<br><table border="1"><c:forEach items="${objList}" var="obj">
         <tr>
            <td>${obj.entId}</td>
            <td>${obj.description}</td>
            <td>${obj.amount}</td>
            <td>${obj.orderDate}</td>
            <td>${obj.price}</td><td><a href="editSaleOrder.do?id=${obj.entId}">Edit</a></td><td><a href="deleteSaleOrder.do?id=${obj.entId}">Remove</a></td>
         </tr></c:forEach>
      </table><a href="newSaleOrder.do">New SaleOrder</a>
   </body>
</html>