<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : productDetails.xsl
    Created on : December 1, 2019, 8:55 AM
    Author     : Dell
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml" encoding="UTF-8"/>
    <xsl:template match="text()"></xsl:template>
    <xsl:template match="/">
        <xsl:element name="colors">
            <xsl:element name="color1">
                <xsl:value-of select=".//div[@id='variant-swatch-0']/div[@class='select-swap']//span" />
            </xsl:element>
            <xsl:element name="color2">
                <xsl:value-of select=".//div[@id='variant-swatch-1']/div[@class='select-swap']//span" />
            </xsl:element>
            <xsl:element name="soldold">
                <xsl:value-of select=".//span[@class='soldold']" />
            </xsl:element> 
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>
