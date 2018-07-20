<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
<xsl:output name="text" method="text" encoding="UTF-8"/>
  <xsl:template match="/">
	<xsl:apply-templates select="application/data-model/entity">
		<xsl:with-param name="appname" select="application/@name"></xsl:with-param>
	</xsl:apply-templates>
	<xsl:variable name="appname" select="application/@name"></xsl:variable>
	<xsl:result-document href="workspace/{$appname}-SERV/datastore-indexes.xml" format="text">
<xsl:text disable-output-escaping="yes"><![CDATA[<?xml version="1.0" encoding="utf-8"?>
<datastore-indexes autoGenerate="false">

]]></xsl:text>
		<xsl:for-each select="application/data-model/entity" >
		<xsl:text disable-output-escaping="yes"><![CDATA[	<datastore-index kind="]]></xsl:text><xsl:value-of select="@name"/><xsl:text disable-output-escaping="yes"><![CDATA[" ancestor="false">
]]></xsl:text>
			<xsl:for-each select="attribute" >
				<xsl:if test="@searchable='yes'">
				<xsl:text disable-output-escaping="yes"><![CDATA[		<property name="]]></xsl:text><xsl:value-of select="@name"/><xsl:text disable-output-escaping="yes"><![CDATA[" direction="asc" />
]]></xsl:text>
				</xsl:if>
				<xsl:if test="@relationtype ne '' and @parent='no'">
				<xsl:text disable-output-escaping="yes"><![CDATA[		<property name="]]></xsl:text><xsl:value-of select="@name"/>Id<xsl:text disable-output-escaping="yes"><![CDATA[" direction="asc" />
]]></xsl:text>
				</xsl:if>
			</xsl:for-each>
		<xsl:text disable-output-escaping="yes"><![CDATA[	</datastore-index>
]]></xsl:text>
		</xsl:for-each>
<xsl:text disable-output-escaping="yes"><![CDATA[ 
</datastore-indexes>
	]]></xsl:text>
	</xsl:result-document>
  </xsl:template>
 
   <xsl:template name="capitalizeFirstLetter">
		    <xsl:param name="str" />
		    <xsl:value-of select="concat(upper-case(substring($str, 1, 1)),substring($str, 2))"/>
  </xsl:template>	
</xsl:stylesheet>