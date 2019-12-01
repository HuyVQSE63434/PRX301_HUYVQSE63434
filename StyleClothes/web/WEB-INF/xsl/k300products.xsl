<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : k300products.xsl
    Created on : December 1, 2019, 3:53 PM
    Author     : Dell
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml"/>
    
    <xsl:template match="text()"></xsl:template>
    <xsl:template match="/">
        <xsl:element name="products">
            <xsl:call-template name="getProducts" />
        </xsl:element>
    </xsl:template>
    <xsl:template name="getProducts">
        <xsl:for-each select=".//div[@class='product-item']" >
            <xsl:element name="product">
                <xsl:element name="name">
                    <xsl:value-of select=".//a[@class='product-title a']" ></xsl:value-of>
                </xsl:element>
                <xsl:element name="color">
                </xsl:element>
                <xsl:element name="price">
                    <xsl:value-of select=".//span[@class='current-price']" ></xsl:value-of>
                </xsl:element>
                <xsl:element name="picture">
                    <xsl:value-of select=".//img[1]/@src" ></xsl:value-of> 
                </xsl:element>
                <xsl:element name="link">
                    <xsl:value-of select=".//a[@class='product-title a']/@href" ></xsl:value-of>
                </xsl:element>
            </xsl:element>
        </xsl:for-each>
        <xsl:element name="nextlink">
            <xsl:value-of select=".//span[@class='nextPage']/a/@href" ></xsl:value-of>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>
