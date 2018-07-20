<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
<xsl:output name="html" method="html" encoding="UTF-8"/>
  <xsl:template match="/">
	<xsl:apply-templates select="application/data-model/entity">
		<xsl:with-param name="appname" select="application/@name"></xsl:with-param>
	</xsl:apply-templates>
  </xsl:template>
   
  <xsl:template match="entity">
	<xsl:param name="appname" />
	<xsl:variable name="filename1" select="concat('list',@name,'.jsp')"></xsl:variable>
	<xsl:variable name="filename2" select="concat('form',@name,'.jsp')"></xsl:variable>
	<xsl:variable name="entityname" select="@name"></xsl:variable>
	<xsl:result-document href="workspace/{$appname}-WEB/WebContent/{$filename1}" format="html">
		<xsl:text disable-output-escaping="yes"><![CDATA[<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>]]></xsl:text>
		<html>
		<head>
		<title>Application</title>
		</head>
		<body>
		<xsl:for-each select="attribute" >
			<xsl:if test="@parent='no' and ends-with(@relationtype,'-1')">
				<xsl:variable name="attrname">
					<xsl:call-template name="capitalizeFirstLetter">
						      <xsl:with-param name="str" select="@name"></xsl:with-param>
					</xsl:call-template>
				</xsl:variable>
			<xsl:text disable-output-escaping="yes"><![CDATA[<a href="list]]></xsl:text><xsl:value-of select="$attrname"/><xsl:text disable-output-escaping="yes"><![CDATA[.do">]]></xsl:text><xsl:value-of select="$attrname"/><xsl:text disable-output-escaping="yes"><![CDATA[</a>]]></xsl:text> / <xsl:value-of select="$entityname"/><br/>
			</xsl:if>

		</xsl:for-each>
<!--
		<xsl:for-each select="query" >
			<xsl:text>&#10;</xsl:text><xsl:text disable-output-escaping="yes"><![CDATA[<a align="left" href="form]]></xsl:text><xsl:value-of select="@name"/><xsl:text disable-output-escaping="yes"><![CDATA[.jsp">]]></xsl:text><xsl:value-of select="@name"/><xsl:text disable-output-escaping="yes"><![CDATA[</a>]]></xsl:text><BR/>
		</xsl:for-each>
-->
		<table border="1">
		<xsl:text disable-output-escaping="yes"><![CDATA[<c:forEach items="${objList}" var="obj">]]></xsl:text>
			<tr>
				<td>${obj.entId}</td>
			    <xsl:for-each select="attribute" >
			      <xsl:choose>
				<xsl:when test="not(@parent)">
				<td>${obj.<xsl:value-of select="@name"/>}</td>
				</xsl:when>
				<xsl:when test="@parent='yes' and ends-with(@relationtype,'-n')">
				<xsl:variable name="attrname">
					<xsl:call-template name="capitalizeFirstLetter">
						      <xsl:with-param name="str" select="@name"></xsl:with-param>
					</xsl:call-template>
				</xsl:variable>
				<td><xsl:text disable-output-escaping="yes"><![CDATA[<a href="list]]></xsl:text><xsl:value-of select="$attrname"/>By<xsl:value-of select="$entityname"/>.do<xsl:text disable-output-escaping="yes"><![CDATA[?id=${obj.entId}">]]></xsl:text>Manage <xsl:value-of select="$attrname"/><xsl:text disable-output-escaping="yes"><![CDATA[</a>]]></xsl:text></td>
				</xsl:when>
				<xsl:when test="@parent='yes' and ends-with(@relationtype,'-1')">
				<xsl:variable name="attrname">
					<xsl:call-template name="capitalizeFirstLetter">
						      <xsl:with-param name="str" select="@name"></xsl:with-param>
					</xsl:call-template>
				</xsl:variable>
				<td><xsl:text disable-output-escaping="yes"><![CDATA[<a href="edit]]></xsl:text><xsl:value-of select="$attrname"/>By<xsl:value-of select="$entityname"/>.do<xsl:text disable-output-escaping="yes"><![CDATA[?id=${obj.entId}">]]></xsl:text>Edit <xsl:value-of select="$attrname"/><xsl:text disable-output-escaping="yes"><![CDATA[</a>]]></xsl:text></td>
				</xsl:when>
			      </xsl:choose>
			    </xsl:for-each>
			    <xsl:text disable-output-escaping="yes"><![CDATA[<td><a href="edit]]></xsl:text><xsl:value-of select="@name"/><xsl:text disable-output-escaping="yes"><![CDATA[.do?id=${obj.entId}">Edit</a></td>]]></xsl:text>
			    <xsl:text disable-output-escaping="yes"><![CDATA[<td><a href="delete]]></xsl:text><xsl:value-of select="@name"/><xsl:text disable-output-escaping="yes"><![CDATA[.do?id=${obj.entId}">Remove</a></td>]]></xsl:text>
			</tr>
		<xsl:text disable-output-escaping="yes"><![CDATA[</c:forEach>]]></xsl:text>
		</table>
		<xsl:text disable-output-escaping="yes"><![CDATA[<a href="new]]></xsl:text><xsl:value-of select="@name"/><xsl:text disable-output-escaping="yes"><![CDATA[.do">New ]]></xsl:text><xsl:value-of select="@name"/><xsl:text disable-output-escaping="yes"><![CDATA[</a>]]></xsl:text>
		</body>
		</html>
	</xsl:result-document>

	
	<xsl:result-document href="workspace/{$appname}-WEB/WebContent/{$filename2}" format="html">
		<xsl:text disable-output-escaping="yes"><![CDATA[<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>]]></xsl:text>
		<html >
		<head>
		<title>Application</title>
		</head>
		<body>
		<xsl:text disable-output-escaping="yes"><![CDATA[<form:form method="post" enctype="multipart/form-data" action="save]]></xsl:text><xsl:value-of select="@name"/><xsl:text disable-output-escaping="yes"><![CDATA[.do" commandName="obj">]]></xsl:text>
			<xsl:text>&#10;</xsl:text><xsl:text disable-output-escaping="yes"><![CDATA[<form:hidden path="entId" />]]></xsl:text><br/>
			<xsl:for-each select="attribute" >
			   <xsl:choose>
			     <xsl:when test="@datatype='file'">
				<xsl:text>&#10;</xsl:text><xsl:value-of select="@name"/>: <xsl:text disable-output-escaping="yes"><![CDATA[<form:input path="]]></xsl:text><xsl:value-of select="@name"/><xsl:text disable-output-escaping="yes"><![CDATA[Data" type="file" />]]></xsl:text><br/>
				<xsl:text>&#10;</xsl:text>${obj.<xsl:value-of select="@name"/>}<xsl:text disable-output-escaping="yes"><![CDATA[<a href="filestore/${]]></xsl:text>obj.<xsl:value-of select="@name"/><xsl:text disable-output-escaping="yes"><![CDATA[}">Download File</a><br/>]]></xsl:text>
			        <xsl:text>&#10;</xsl:text><xsl:text disable-output-escaping="yes"><![CDATA[<form:hidden path="]]></xsl:text><xsl:value-of select="@name"/><xsl:text disable-output-escaping="yes"><![CDATA[" />]]></xsl:text>
			     </xsl:when>
			     <xsl:when test="not(@parent)">
				<xsl:text>&#10;</xsl:text><xsl:value-of select="@name"/>: <xsl:text disable-output-escaping="yes"><![CDATA[<form:input path="]]></xsl:text><xsl:value-of select="@name"/><xsl:text disable-output-escaping="yes"><![CDATA[" /><br/>]]></xsl:text>
			     </xsl:when>
			     <xsl:when test="@parent='no' and ends-with(@relationtype,'-1')">
				<xsl:text>&#10;</xsl:text><xsl:text disable-output-escaping="yes"><![CDATA[<form:hidden path="]]></xsl:text><xsl:value-of select="@name"/>EntId<xsl:text disable-output-escaping="yes"><![CDATA[" />]]></xsl:text>
			     </xsl:when>
			   </xsl:choose>
			</xsl:for-each>
			<xsl:text>&#10;</xsl:text><input type="submit" value="Save" />
		<xsl:text disable-output-escaping="yes"><![CDATA[</form:form>]]></xsl:text>
		</body>
		</html>
	</xsl:result-document>
<!--
	<xsl:apply-templates select="query">
		<xsl:with-param name="entityname" select="@name"></xsl:with-param>
		<xsl:with-param name="appname" select="$appname"></xsl:with-param>
	</xsl:apply-templates>
-->
  </xsl:template>

  <xsl:template match="query">
	<xsl:param name="appname" />
	<xsl:param name="entityname" />
	<xsl:variable name="filename" select="concat('form',@name,'.jsp')"></xsl:variable>
	<xsl:result-document href="workspace/{$appname}-WEB/WebContent/{$filename}" format="html">
		<xsl:text disable-output-escaping="yes"><![CDATA[<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>]]></xsl:text>
		<html>
		<head>
		<title>Application</title>
		</head>
		<body>
		<xsl:text disable-output-escaping="yes"><![CDATA[<form method="POST" action="]]></xsl:text><xsl:value-of select="@name"/><xsl:text disable-output-escaping="yes"><![CDATA[.do">]]></xsl:text>
		<xsl:for-each select="param" >
				<xsl:value-of select="@name"/><xsl:text disable-output-escaping="yes"><![CDATA[:<input type="text" name="]]></xsl:text><xsl:value-of select="@name"/><xsl:text disable-output-escaping="yes"><![CDATA["/><br/>]]></xsl:text>
		</xsl:for-each>
		<input type="submit" value="Search"/>
		<xsl:text disable-output-escaping="yes"><![CDATA[</form>]]></xsl:text>
		</body>
		</html>
	</xsl:result-document>
  </xsl:template>



   <xsl:template name="capitalizeFirstLetter">
		    <xsl:param name="str" />
		    <xsl:value-of select="concat(upper-case(substring($str, 1, 1)),substring($str, 2))"/>
  </xsl:template>	
</xsl:stylesheet>