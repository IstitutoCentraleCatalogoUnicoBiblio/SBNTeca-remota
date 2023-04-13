<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"   
    xmlns:metaindice="http://www.internetculturale.it/metaindice"
          xmlns:dc="http://purl.org/dc/elements/1.1/"
          xmlns:dcterms="http://purl.org/dc/terms/" 
         xmlns:xml="http://www.w3.org/XML/1998/namespace"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"    
         xmlns:mods="http://www.loc.gov/mods/v3"
          xmlns:pico="http://purl.org/pico/1.0/"
          xmlns:oai_dc="http://www.openarchives.org/OAI/2.0/oai_dc/"
          xmlns:mag="http://www.iccu.sbn.it/metaAG1.pdf" 
          xmlns:fn="http://www.w3.org/2005/xpath-functions" 
          xmlns:norm="xalan://com.gruppometa.metaoaicat.normalize.LanguageNormalizer"        
          xmlns:normdate="xalan://com.gruppometa.metaoaicat.normalize.DateNormalizer"
          xmlns:normtype="xalan://com.gruppometa.metaoaicat.normalize.TypeNormalizer"
          exclude-result-prefixes="norm normdate metaindice fn mag mods normtype"          
    version="1.0"
    >
    <xsl:output
        method="xml" omit-xml-declaration="yes"
        encoding="UTF-8"
    />
    <xsl:param name="baseUrl">http://localhost/metaindice?id=</xsl:param>
    <xsl:param name="id">testid</xsl:param>
    <xsl:template match="/">
          <oai_dc:dc>
     		    <xsl:apply-templates></xsl:apply-templates>
          </oai_dc:dc>
    </xsl:template>
	 <xsl:template match="dc:*">
        <xsl:element name="{name()}"><xsl:value-of select="."/></xsl:element>              
    </xsl:template>
	 <xsl:template match="dcterms:*">
        <xsl:element name="{name()}"><xsl:value-of select="."/></xsl:element>              
    </xsl:template>
    <xsl:template match="text()">
    </xsl:template>
</xsl:stylesheet>