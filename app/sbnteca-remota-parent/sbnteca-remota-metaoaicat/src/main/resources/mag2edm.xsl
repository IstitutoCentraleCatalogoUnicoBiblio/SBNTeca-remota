<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"   
    xmlns:metaindice="http://www.internetculturale.it/metaindice"
          xmlns:dc="http://purl.org/dc/elements/1.1/"
          xmlns:dcterms="http://purl.org/dc/terms/" 
         xmlns:xml="http://www.w3.org/XML/1998/namespace"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"    
         xmlns:mods="http://www.loc.gov/mods/v3"
          xmlns:mag="http://www.iccu.sbn.it/metaAG1.pdf" 
          xmlns:fn="http://www.w3.org/2005/xpath-functions" 
          xmlns:stringutil="xalan://com.gruppometa.metaoaicat.transformer.StringUtil"
          xmlns:norm="xalan://com.gruppometa.metaoaicat.normalize.LanguageNormalizer"        
          xmlns:normdate="xalan://com.gruppometa.metaoaicat.normalize.DateNormalizer"
          xmlns:normtype="xalan://com.gruppometa.metaoaicat.normalize.TypeNormalizer"
          xmlns:edmsound="xalan://com.gruppometa.metaoaicat.transformer.EdmSound"
          xmlns:edm="http://www.europeana.eu/schemas/edm/"   
          exclude-result-prefixes="norm normdate metaindice fn mag mods normtype edmsound stringutil "
          xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
          xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"   
          xmlns:ore="http://www.openarchives.org/ore/terms/"
          xmlns:skos="http://www.w3.org/2004/02/skos/core#"
          xmlns:ebucore="http://www.ebu.ch/metadata/ontologies/ebucore/ebucore#"                   
    version="1.0"
    >
    <xsl:output
        method="xml" omit-xml-declaration="yes"
        encoding="UTF-8"
    />
    <xsl:param name="baseUrl">http://localhost/metaindice?id=</xsl:param>
    <xsl:param name="id">testid</xsl:param>
    <xsl:param name="descSourceLevel2">test</xsl:param>
    <xsl:param name="previewUrl">http://iccu01e.caspur.it/ms/thumb.php?size=300&amp;font=0.8&amp;id=</xsl:param>
    
    <xsl:template match="//mag:metadigit">
	    <rdf:RDF xsi:schemaLocation="http://www.w3.org/1999/02/22-rdf-syntax-ns# http://www.europeana.eu/schemas/edm/EDM.xsd">
   			<edm:ProvidedCHO rdf:about="000000">
	   			<xsl:attribute name="rdf:about">http://mint-projects.image.ntua.gr/data/sounds/<xsl:value-of select="//dc:identifier"/></xsl:attribute>
				<xsl:apply-templates/> 
								
				<xsl:choose>
					<xsl:when test="count(//dc:date)=2">
					<dc:date><xsl:value-of select="normdate:normalize(//dc:date[1])"/>-<xsl:value-of select="normdate:normalize(//dc:date[2])"/></dc:date>
					</xsl:when>
					<xsl:otherwise>
						<xsl:for-each select="//dc:date">
							<xsl:if test="not(//mag:piece or  //mag:year)">
	    						<dc:date><xsl:value-of select="normdate:normalize(.)"/></dc:date>
	    					</xsl:if>
	    				</xsl:for-each>
					</xsl:otherwise>
				</xsl:choose>
				<dcterms:provenance>Istituto Centrale per i Beni Sonori ed Audiovisivi</dcterms:provenance>
				<edm:type>SOUND</edm:type>				    			  			
   			</edm:ProvidedCHO>
   			<edm:WebResource>
				<xsl:attribute name="rdf:about">http://mint-projects.image.ntua.gr/data/sounds/<xsl:value-of select="//dc:identifier"/></xsl:attribute>
    			<dc:format>mp3</dc:format>
    			<xsl:call-template name="edmRights"></xsl:call-template>
    			<xsl:if test="//mag:bib[@level='a']">
    				<ebucore:duration>00:00:30.000000</ebucore:duration>
    			</xsl:if>
    		</edm:WebResource>
   			<ore:Aggregation>
				<xsl:attribute name="rdf:about">http://mint-projects.image.ntua.gr/data/sounds/<xsl:value-of select="//dc:identifier"/>/Aggregation</xsl:attribute>
	   			<edm:aggregatedCHO>
	   				<xsl:attribute name="rdf:resource">http://mint-projects.image.ntua.gr/data/sounds/<xsl:value-of select="//dc:identifier"/></xsl:attribute>
	   			</edm:aggregatedCHO>
   				<edm:dataProvider>Internet Culturale</edm:dataProvider>
   				<edm:isShownAt><xsl:attribute name="rdf:resource"><xsl:value-of select="$baseUrl"/><xsl:value-of select="$id"></xsl:value-of><xsl:text>&amp;teca=</xsl:text><xsl:value-of select="stringutil:urlencode($descSourceLevel2)"/></xsl:attribute>
   				</edm:isShownAt>
   				<xsl:if test="//mag:bib[@level='m']">
   					<edm:object>
   						<xsl:attribute name="rdf:resource"><xsl:value-of select="$previewUrl"/><xsl:value-of select="$id"/><xsl:text>&amp;teca=</xsl:text><xsl:value-of select="stringutil:urlencode($descSourceLevel2)"/></xsl:attribute>
   					</edm:object>   				   	
   				</xsl:if>		
   				<edm:provider>Europeana Sounds</edm:provider>
   				<xsl:call-template name="edmRights"></xsl:call-template>
   			</ore:Aggregation>
    	</rdf:RDF>
    </xsl:template>
    
    <xsl:template name="edmRights">
    		<xsl:variable name="v"><xsl:value-of select="//dc:subject"/></xsl:variable>
    		<xsl:variable name="v2"><xsl:value-of select="//dc:format"/></xsl:variable>
    		<xsl:choose>    		
	    		<xsl:when test="contains($v,'ilind') or contains($v2,'ilind')">
	   				<!-- per i Cilindri inserire il link: -->                                                                                                                              
	    			<edm:rights rdf:resource="https://creativecommons.org/publicdomain/mark/1.0/"/>
	   			</xsl:when>
	   			<xsl:otherwise>
	    			<!--  per Dischi e Compact disc inserire il link:  -->
	   				<edm:rights rdf:resource="http://www.europeana.eu/portal/rights/rr-f/"/>       
	   			</xsl:otherwise>
   			</xsl:choose>	
    </xsl:template>
    
    <xsl:template match="mag:year">
   		<dc:date><xsl:value-of select="normdate:normalize(.)"/></dc:date>
    </xsl:template>
    
    <xsl:template match="mag:duration">
    	<xsl:if test="//mag:bib[@level='a']">   	
   			<ebucore:duration><xsl:value-of select="."/></ebucore:duration>
   		</xsl:if>
    </xsl:template>
    
    <xsl:template match="dc:subject">
    		<xsl:variable name="v"><xsl:value-of select="edmsound:getSubject(.)"/></xsl:variable>    		
    		<xsl:choose>
    			<xsl:when test="$v">
    			<ebucore:hasGenre>
    			<xsl:attribute name="rdf:resource"><xsl:value-of select="$v"/></xsl:attribute>
    			</ebucore:hasGenre>
    			</xsl:when>
    		</xsl:choose>    		 
    		<xsl:variable name="v"><xsl:value-of select="edmsound:getStrumenti(.)"/></xsl:variable>    		
    		<xsl:choose>
    			<xsl:when test="$v">
    			<dc:subject>
    			<xsl:attribute name="rdf:resource"><xsl:value-of select="$v"/></xsl:attribute>
    			</dc:subject>
    			</xsl:when>
    		</xsl:choose>    		 
    </xsl:template>
    
    <xsl:template match="dc:type">
    		<xsl:variable name="v"><xsl:value-of select="edmsound:getType(.)"/></xsl:variable>
    		<xsl:if test="$v">
    			<ebucore:hasGenre>
    				<xsl:attribute name="rdf:resource"><xsl:value-of select="$v"/></xsl:attribute>
    			</ebucore:hasGenre>
			</xsl:if>	    			    	
    </xsl:template>


    <xsl:template match="dc:relation">
    		<xsl:choose>
    			<xsl:when test="starts-with(.,&quot;'comprende:'&quot;)">
    				<xsl:if test="//mag:bib[@level='m']">   	
    			    	<dcterms:hasPart><xsl:value-of select="substring-before(substring(.,14),' {')"></xsl:value-of></dcterms:hasPart>
    			    </xsl:if>	    			    
    			</xsl:when>
    			<xsl:when test="starts-with(.,&quot;'fa parte di:'&quot;)">
    				<xsl:if test="//mag:bib[@level='a']">   	
	    			    <dcterms:isPartOf><xsl:value-of select="substring-before(substring(.,16),' {')"></xsl:value-of></dcterms:isPartOf>
	    			 </xsl:if>       			    
    			</xsl:when>
    			<xsl:when test="not (starts-with(.,&quot;'&quot;))">
    				<dcterms:alternative><xsl:value-of select="."/></dcterms:alternative>
    			</xsl:when>    		
    			<xsl:otherwise></xsl:otherwise>
    		</xsl:choose>    		    		
    </xsl:template>
    
    <xsl:template match="dc:format"> 
    	<xsl:variable name="v"><xsl:value-of select="edmsound:getFormat(.)"/></xsl:variable>
   		<xsl:choose>
   			<xsl:when test="$v">
   			<dcterms:medium>
   				<xsl:attribute name="rdf:resource"><xsl:value-of select="$v"/></xsl:attribute>    			 
   			 </dcterms:medium>
   			</xsl:when>
   			<xsl:otherwise></xsl:otherwise>
   		</xsl:choose>
		<xsl:if test="//mag:bib[@level='m']">
			<dc:format><xsl:value-of select="."/></dc:format>
		</xsl:if>    		
    </xsl:template>
    
     <xsl:template match="dc:language">
   		<dc:language><xsl:value-of select="norm:normalize(.)"/></dc:language>
    </xsl:template>
    
    <xsl:template match="dc:identifier|dc:creator|dc:publisher|dc:contributor">
        <xsl:if test="parent::mag:bib"> 
        <xsl:element name="{name()}"><xsl:value-of select="."/></xsl:element>              
    		<!-- -<xsl:copy><xsl:value-of select="."/></xsl:copy>-->
    	</xsl:if>
    </xsl:template>
    <!-- nuovo template per creazione anche del titolo da description -->
    <xsl:template match="dc:description">    	
        <xsl:if test="parent::mag:bib"> 
        <xsl:element name="{name()}"><xsl:attribute name="xml:lang">it</xsl:attribute><xsl:value-of select="."/></xsl:element>              
    		<!-- -<xsl:copy><xsl:value-of select="."/></xsl:copy>-->
    	</xsl:if>
    	<xsl:if test="not(//dc:title)">
    		<dc:title><xsl:value-of select="substring(.,0,160)"/></dc:title>
    	</xsl:if>
    </xsl:template>
	<xsl:variable name="firstTitle">1</xsl:variable>    
    <xsl:template match="dc:title">
    	<xsl:element name="{name()}"><xsl:value-of select="."/>
    		<xsl:if test="//mag:piece/mag:year and $firstTitle=1 and //mag:bib[@level='s']">
    			<xsl:variable name="firstTitle">0</xsl:variable>
    			 (<xsl:value-of select="//mag:piece/mag:year"/>:<xsl:value-of select="//mag:piece/mag:issue"/>)
    		</xsl:if>
    		<xsl:if test="//mag:part_name and $firstTitle=1 and //mag:bib[@level='s']">
    			<xsl:variable name="firstTitle">0</xsl:variable>
    			 (<xsl:value-of select="//mag:part_name"/>:<xsl:value-of select="//mag:part_number"/>)
    		</xsl:if>
    	</xsl:element>
    </xsl:template>
    
    <xsl:template match="text()"></xsl:template>
    
</xsl:stylesheet>