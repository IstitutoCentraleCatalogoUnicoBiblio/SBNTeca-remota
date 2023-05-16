<?xml version="1.0" encoding="utf-8" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="3.0"
                xmlns:fn="http://www.w3.org/2005/xpath-functions"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:dc="http://purl.org/dc/elements/1.1/"
                xmlns:xlink="http://www.w3.org/TR/xlink"
                xmlns:mag="http://www.iccu.sbn.it/metaAG1.pdf"
                xmlns:niso="http://www.niso.org/pdfs/DataDict.pdf"
                exclude-result-prefixes="fn mag"
>
    <xsl:output method="xml" indent="yes" encoding="UTF-8"/>

    <xsl:param name="baseUrl"></xsl:param>
    <xsl:param name="usages">2,3</xsl:param>

    <!--<xsl:namespace-alias stylesheet-prefix="mag" result-prefix=""/>-->

    <xsl:template match="  element() | text() " >
        <xsl:variable name="ends">:' </xsl:variable>
        <xsl:if test="normalize-space(string(.)) != ''
                and not(fn:ends-with(string(.),$ends))
                    ">
            <xsl:copy>
                <xsl:apply-templates select="element() | text()"/>
            </xsl:copy>
        </xsl:if>
    </xsl:template>

    <xsl:template match="mag:file">
        <xsl:if test="fn:contains($usages,../mag:usage/text()) or (fn:contains($usages,'5') and ../mag:usage/text() eq '4')">
            <xsl:if test="not(fn:contains($usages,'5')) or (../mag:usage/text() ne '3') or (../mag:usage/text() eq '3' and not(name(../..) eq 'audio'))">
                <file xmlns="http://www.iccu.sbn.it/metaAG1.pdf">
                    <!--<xsl:attribute name="Location"><xsl:value-of select="@Location"/></xsl:attribute>-->
                    <xsl:attribute name="href"><xsl:value-of select="$baseUrl"/><xsl:value-of select="@xlink:href"/></xsl:attribute>
                    <xsl:apply-templates/>
                </file>
                <xsl:if test="../@imggroupID">
                    <xsl:variable name="idtemp"><xsl:value-of select="../@imggroupID"/></xsl:variable>
                    <format  xmlns="http://www.iccu.sbn.it/metaAG1.pdf">
                        <mime xmlns="http://www.iccu.sbn.it/metaAG1.pdf"><xsl:value-of select="//mag:img_group[@ID=$idtemp]/mag:format/niso:mime"/></mime>
                    </format>
                </xsl:if>
                <xsl:if test="../@audiogroupID">
                    <xsl:variable name="idtemp"><xsl:value-of select="../@audiogroupID"/></xsl:variable>
                    <format  xmlns="http://www.iccu.sbn.it/metaAG1.pdf">
                        <mime xmlns="http://www.iccu.sbn.it/metaAG1.pdf"><xsl:value-of select="//mag:audio_group[@ID=$idtemp]/mag:format/niso:mime|//mag:audio_group[@ID=$idtemp]/mag:format/mag:mime"/></mime>
                    </format>
                </xsl:if>
            </xsl:if>
        </xsl:if>
    </xsl:template>

    <xsl:template match="mag:bib">
        <bib xmlns="http://www.iccu.sbn.it/metaAG1.pdf"></bib>
    </xsl:template>

    <xsl:template match="mag:img">
        <img xmlns="http://www.iccu.sbn.it/metaAG1.pdf">
            <xsl:apply-templates/>
        </img>
    </xsl:template>

    <xsl:template match="mag:nomenclature">
        <nomenclature xmlns="http://www.iccu.sbn.it/metaAG1.pdf">
            <xsl:variable name="seq"><xsl:value-of select="../mag:sequence_number"/></xsl:variable>
            <xsl:variable name="media"><xsl:value-of select="../name()"/></xsl:variable>
            <xsl:variable name="mediaFull">
                <xsl:choose>
                    <xsl:when test="$media eq 'img'">image</xsl:when>
                    <xsl:otherwise><xsl:value-of select="$media"/></xsl:otherwise>
                </xsl:choose>
            </xsl:variable>
            <xsl:for-each select="//mag:stru/mag:element/mag:resource[text() eq $mediaFull]">
                <xsl:if test="../mag:start[fn:number(@sequence_number) &lt;= fn:number($seq)]">
                <xsl:if test="../mag:stop[fn:number(@sequence_number) &gt;= fn:number($seq)]">
                    <xsl:value-of select="../../mag:nomenclature"/> / </xsl:if>
                </xsl:if>
            </xsl:for-each>
            <xsl:value-of select="."/>
        </nomenclature>
    </xsl:template>

    <xsl:template match="mag:usage">
        <xsl:if test="fn:contains($usages, text()) or (fn:contains($usages,'5') and ../mag:usage/text() eq '4')">
            <usage xmlns="http://www.iccu.sbn.it/metaAG1.pdf"><xsl:choose>
                <xsl:when test="(fn:contains($usages,'5') and text() eq '4')">3</xsl:when>
                <xsl:otherwise><xsl:value-of select="."/></xsl:otherwise>
            </xsl:choose></usage>
        </xsl:if>
    </xsl:template>

    <xsl:template match="mag:altimg">
        <xsl:choose>
            <xsl:when test="fn:contains($usages, mag:usage/text()) and not(fn:contains($usages,'3'))">
                <altimg xmlns="http://www.iccu.sbn.it/metaAG1.pdf">
                    <xsl:apply-templates/>
                </altimg>
            </xsl:when>
            <xsl:when test="fn:contains($usages, mag:usage/text()) and fn:contains($usages,'3') and not(fn:contains($usages,'2'))">
                <xsl:apply-templates/>
            </xsl:when>
            <xsl:when test="fn:contains($usages, mag:usage/text())">
                <altimg xmlns="http://www.iccu.sbn.it/metaAG1.pdf">
                    <xsl:apply-templates/>
                </altimg>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="mag:proxies">
        <xsl:choose>
            <xsl:when test="fn:contains($usages, '5') and mag:usage/text() eq '3' and (name(..) eq 'audio')">
            </xsl:when>
            <xsl:when test="mag:usage/text()='3' and not(fn:contains($usages,'3'))">
                <altauodio xmlns="http://www.iccu.sbn.it/metaAG1.pdf">
                    <xsl:apply-templates/>
                </altauodio>
            </xsl:when>
            <xsl:when test="mag:usage/text()='3' and fn:contains($usages,'2')">
                <altauodio xmlns="http://www.iccu.sbn.it/metaAG1.pdf">
                    <xsl:apply-templates/>
                </altauodio>
            </xsl:when>
            <xsl:when test="mag:usage/text()='4' and fn:contains($usages,'2')">
                <altauodio xmlns="http://www.iccu.sbn.it/metaAG1.pdf">
                    <xsl:apply-templates/>
                </altauodio>
            </xsl:when>
            <xsl:when test="mag:usage/text()='3' and fn:contains($usages,'3') and not(fn:contains($usages,'5'))">
                    <xsl:apply-templates/>
            </xsl:when>
            <xsl:otherwise>
                <!--<name><xsl:value-of select="mag:usage/text()"/></name>-->
                <xsl:apply-templates/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="mag:image_dimensions"></xsl:template>
    <xsl:template match="mag:filesize"></xsl:template>
    <xsl:template match="mag:md5"></xsl:template>
    <xsl:template match="mag:datetimecreated"></xsl:template>
    <xsl:template match="mag:audio_dimensions"></xsl:template>
    <xsl:template match="comment()"></xsl:template>
    <xsl:template match="mag:gen"></xsl:template>
    <xsl:template match="mag:stru"></xsl:template>

</xsl:stylesheet>