<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : products.xsl
    Created on : November 29, 2019, 1:08 PM
    Author     : Dell
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml"/>
    <xsl:template match="/">
        <xsl:element name="products">
            <xsl:call-template name="getProducts" />
        </xsl:element>
    </xsl:template>
    <xsl:template name="getProducts">
        <xsl:for-each select=".//div[contains(@class, 'product-block product-resize site-animation')]" >
            <xsl:element name="product">
                <xsl:element name="name">
                    <xsl:value-of select=".//a/@title" ></xsl:value-of>
                </xsl:element>
                <xsl:element name="color">
                </xsl:element>
                <xsl:element name="price">
                    <xsl:value-of select=".//div[contains(@class, 'box-pro-prices')]//p" ></xsl:value-of>
                </xsl:element>
                <xsl:element name="picture">
                    <xsl:value-of select=".//source/@srcset" ></xsl:value-of> 
                </xsl:element>
                <xsl:element name="link">
                    <xsl:value-of select=".//a/@href" ></xsl:value-of>
                </xsl:element>
            </xsl:element>
        </xsl:for-each>
        <xsl:element name="nextlink">
            <xsl:value-of select=".//a[contains(@class,'next')]/@href" ></xsl:value-of>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>

