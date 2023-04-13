<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="3.0"
                xmlns:fn="http://www.w3.org/2005/xpath-functions"
                xmlns:magextension="http://magextension.it/saxon-extension"
                xmlns:xsk="http://www.w3.org/1999/XSL/Transform" xmlns:xsò="http://www.w3.org/1999/XSL/Transform"
                exclude-result-prefixes="fn magextension"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:dc="http://purl.org/dc/elements/1.1/"
                xmlns:mods="http://www.loc.gov/mods/v3"
                xmlns:mag="http://www.iccu.sbn.it/metaAG1.pdf"
                xmlns:sbn="http://www.iccu.sbn.it/opencms/opencms/documenti/2016/SBNMarcv202.xsd"
>
    <xsl:output method="xml" indent="yes" encoding="UTF-8"/>
    <xsl:param name="type"></xsl:param>

    <xsl:template match="/">
        <mods:mods>
            <xsl:apply-templates/>
        </mods:mods>
    </xsl:template>

    <xsl:template match="sbn:Documento/sbn:DatiDocumento">
        <mods:identifier><xsl:value-of select="sbn:T001"/></mods:identifier>
        <!-- title -->
        <xsl:for-each select="sbn:T200[@id1 ne '0']">
            <mods:titleInfo>
                <mods:title><xsl:value-of select="magextension:format(.,'a|c|d|e|f|g')"/></mods:title>
            </mods:titleInfo>
        </xsl:for-each>
        <xsl:for-each select="//sbn:LegameElementoAut">
            <xsl:variable name="temp200"><xsl:value-of select="sbn:ElementoAutLegato/sbn:DatiElementoAut/sbn:T200/sbn:c_200"/></xsl:variable>
            <xsl:variable name="temp210"><xsl:value-of select="sbn:ElementoAutLegato/sbn:DatiElementoAut/sbn:T210/sbn:c_210"/></xsl:variable>
            <xsl:if test="@tipoLegame='700' or @tipoLegame='701' or @tipoLegame='710' or @tipoLegame='711'">
                <xsl:if test="sbn:ElementoAutLegato/sbn:DatiElementoAut/sbn:T200">
                    <xsl:choose><xsl:when test="fn:contains($temp200,'&lt;omonini non identificati&gt;')
                                or fn:contains($temp200,'&lt;autore indifferenziato&gt;')">
                        <mods:name>
                            <mods:namePart><xsl:value-of select="magextension:format(sbn:ElementoAutLegato/sbn:DatiElementoAut/sbn:T200,'a|b|d|f')"/></mods:namePart>
                        </mods:name>
                    </xsl:when>
                        <xsl:otherwise>
                            <mods:name>
                            <mods:namePart><xsl:value-of select="magextension:format(sbn:ElementoAutLegato/sbn:DatiElementoAut/sbn:T200,'a|b|c|d|f')"/></mods:namePart>
                            </mods:name>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:if>
                <!-- enti -->
                <xsl:if test="sbn:ElementoAutLegato/sbn:DatiElementoAut/sbn:T210">
                    <xsl:choose><xsl:when test="fn:contains($temp210,'&lt;omonini non identificati&gt;')
                                or fn:contains($temp210,'&lt;autore indifferenziato&gt;')">
                        <mods:name type="corporate">
                            <mods:namePart><xsl:value-of select="magextension:format(sbn:ElementoAutLegato/sbn:DatiElementoAut/sbn:T210,'a|b|d|f')"/></mods:namePart>
                        </mods:name>
                    </xsl:when>
                        <xsl:otherwise>
                            <mods:name type="corporate">
                                <mods:namePart><xsl:value-of select="magextension:format(sbn:ElementoAutLegato/sbn:DatiElementoAut/sbn:T210,'a|b|c|d|f')"/></mods:namePart>
                            </mods:name>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:if>
            </xsl:if>
        </xsl:for-each>

    </xsl:template>

    <xsl:template match="text()">
    </xsl:template>
</xsl:stylesheet>