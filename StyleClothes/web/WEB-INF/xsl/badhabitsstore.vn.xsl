<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : newstylesheet.xsl
    Created on : November 28, 2019, 3:17 PM
    Author     : Dell
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml"/>
<xsl:template match="text()"></xsl:template>
    <xsl:template match="/">
        <xsl:element name="products">
            <xsl:apply-templates></xsl:apply-templates>
        </xsl:element>
    </xsl:template>
    
</xsl:stylesheet>
