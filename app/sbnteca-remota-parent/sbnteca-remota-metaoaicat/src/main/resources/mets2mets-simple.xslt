<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xlink="http://www.w3.org/1999/xlink"
    xmlns:METS="http://www.loc.gov/METS/">

    <xsl:output
            method="xml" omit-xml-declaration="yes"
            encoding="UTF-8"
    />
    <xsl:param name="manifestUrl"></xsl:param>
    <xsl:param name="previewUrl"></xsl:param>
    <xsl:template match="@*|node()">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()"/>
        </xsl:copy>
    </xsl:template>

    <xsl:template match="METS:fileSec">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()"/>
            <!-- creazione con un VIEWER? -->
            <METS:fileGrp USE="EXTERNAL">
                <METS:fileGrp USE="MANIFEST">
                    <METS:file ID="MANIFEST1" MIMETYPE="application/json">
						<METS:FLocat LOCTYPE="URL">
						    <xsl:attribute name="xlink:href"><xsl:value-of select="$manifestUrl"/></xsl:attribute>
						</METS:FLocat>
					</METS:file>
                </METS:fileGrp>
                <METS:fileGrp USE="IMAGE">
                    <METS:fileGrp USE="PREVIEW">
                        <METS:file ID="PREVIEW1" MIMETYPE="image/jpeg">
                            <METS:FLocat LOCTYPE="URL">
                                <xsl:attribute name="xlink:href"><xsl:value-of select="$previewUrl"/><xsl:value-of
                                        select="//METS:fileGrp[@USE='IMAGE']/METS:fileGrp/METS:file/METS:FLocat/@xlink:href"/>/original?w=300&amp;h=300</xsl:attribute>
                            </METS:FLocat>
                        </METS:file>
                    </METS:fileGrp>
                </METS:fileGrp>
            </METS:fileGrp>
        </xsl:copy>
    </xsl:template>
    <xsl:template match="METS:fileGrp|METS:structMap">  
    </xsl:template>

</xsl:stylesheet>

