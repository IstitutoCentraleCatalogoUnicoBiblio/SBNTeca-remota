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
		xmlns:mag="http://www.iccu.sbn.it/metaAG1.pdf"
		xmlns:fn="xalan://com.gruppometa.metaoaicat.transformer.StringUtil"
		xmlns:norm="xalan://com.gruppometa.metaoaicat.normalize.LanguageNormalizer"
		xmlns:normdate="xalan://com.gruppometa.metaoaicat.normalize.DateNormalizer"
		xmlns:normtype="xalan://com.gruppometa.metaoaicat.normalize.TypeNormalizer"
		xmlns:xl="http://www.w3.org/1999/XSL/Transform"
		exclude-result-prefixes="norm normdate metaindice fn mag mods normtype"
		version="1.0"
>
	<!-- Attenzione: su IC c'è:
		xmlns:fn="http://www.w3.org/2005/xpath-functions"  -->
    <xsl:output
        method="xml" omit-xml-declaration="yes"
        encoding="UTF-8"
    />
    <xsl:param name="baseUrl">http://localhost/metaindice?id=</xsl:param>
    <xsl:param name="id">testid</xsl:param>
    <xsl:param name="previewUrl">http://iccu01e.caspur.it/ms/thumb.php?size=300&amp;font=0.8&amp;id=</xsl:param>
	<xsl:param name="iiifBaseUrl">http://www.internetculturale.it/iiif/</xsl:param>
    <xsl:template match="//mag:metadigit">
    	<pico:record xsi:schemaLocation="http://purl.org/pico/1.0/ http://www.culturaitalia.it/pico/schemas/1.0/pico.xsd">
			<xsl:apply-templates/>
				<xsl:choose>
					<xsl:when test="count(//dc:date) = 2 and string-length(//dc:date[1])=4 and string-length(//dc:date[2])=4">
						<dcterms:temporal xsi:type="dcterms:Period">start=<xsl:value-of select="//dc:date[1]"/>; end=<xsl:value-of select="//dc:date[2]"/>;</dcterms:temporal>
					</xsl:when>
					<xsl:otherwise>
						<xsl:if test="not(//mag:piece or  //mag:year)">
							<xsl:for-each select="//dc:date">
								<dc:date xsi:type="W3CDTF"><xsl:value-of select="normdate:normalize(.)"/></dc:date>
							</xsl:for-each>
						</xsl:if>
					</xsl:otherwise>
				</xsl:choose>
			<xsl:call-template name="DCMIType">
				<xsl:with-param name="value"><xsl:value-of select="normtype:normalize(//dc:type)"/></xsl:with-param>
				<xsl:with-param name="stprog"><xsl:value-of select="//mag:stprog"/></xsl:with-param>
			</xsl:call-template>
			<dcterms:isReferencedBy xsi:type="pico:Anchor">title=consulta la scheda esterna;URL=<xsl:value-of select="$baseUrl"/><xsl:value-of select="$id"/></dcterms:isReferencedBy>
			<xsl:variable name="testType"><xsl:value-of select="normtype:normalize(//dc:type)"/></xsl:variable>
			<xsl:choose>
				<xsl:when test='fn:toLowerCase($testType) = "registrazione sonora non musicale" or fn:toLowerCase($testType) = "registrazione sonora musicale" or fn:toLowerCase($testType) = "registrazione sonora di musica"  or fn:toLowerCase($testType) = "registrazione sonora di parlato"  or fn:toLowerCase($testType) = "registrazione sonora di ambiente"  or fn:toLowerCase($testType) = "registrazione sonora di programma radiofonico"'>
					<pico:preview xsi:type="pico:Anchor">title=frontespizio;URL=<xsl:value-of select="substring-before($previewUrl,'type=web')"/>type=normal<xsl:value-of select="substring-after($previewUrl,'type=web')"/><xsl:value-of select="$id"/></pico:preview>
				</xsl:when>
				<xsl:otherwise>
					<pico:preview xsi:type="pico:Anchor">title=frontespizio;URL=<xsl:value-of select="$previewUrl"/><xsl:value-of select="$id"/></pico:preview>
				</xsl:otherwise>
			</xsl:choose>
			<xsl:choose>
				<xsl:when test="fn:isTdi($id) and fn:isText(//dc:type)">
					<xsl:variable name="iiifId"><xsl:value-of select="fn:getIiifId($id)"/></xsl:variable>
					<pico:object xsi:type="dcterms:URI"><xsl:value-of select="$iiifBaseUrl"/>image/2.1/<xsl:value-of select="$iiifId"/>/full/full/0/default.jpg</pico:object>
					<pico:iiifResource xsi:type="dcterms:URI"><xsl:value-of select="$iiifBaseUrl"/>image/2.1/<xsl:value-of select="$iiifId"/></pico:iiifResource>
					<pico:iiifCompliant  xsi:type="dcterms:URI">http://iiif.io/api/image</pico:iiifCompliant>
					<pico:iiifImplementation xsi:type="dcterms:URI">http://iiif.io/api/image/2/level0.json</pico:iiifImplementation>
					<pico:iiifManifest  xsi:type="dcterms:URI"><xsl:value-of select="$iiifBaseUrl"/>2.1/<xsl:value-of select="$iiifId"/>/manifest.json</pico:iiifManifest>
				</xsl:when>
				<xsl:when test='fn:toLowerCase($testType) = "registrazione sonora non musicale" or fn:toLowerCase($testType) = "registrazione sonora musicale" or fn:toLowerCase($testType) = "registrazione sonora di musica"  or fn:toLowerCase($testType) = "registrazione sonora di parlato"  or fn:toLowerCase($testType) = "registrazione sonora di ambiente"  or fn:toLowerCase($testType) = "registrazione sonora di programma radiofonico"'>
					<pico:object xsi:type="pico:Anchor">title=Audio;URL=<xsl:value-of select="substring-before($previewUrl,'type=web')"/>type=normal<xsl:value-of select="substring-after($previewUrl,'type=web')"/><xsl:value-of select="$id"/>&amp;getAudio=true</pico:object>
				</xsl:when>
				<xsl:otherwise>
					<pico:object xsi:type="pico:Anchor">title=frontespizio;URL=<xsl:value-of select="substring-before($previewUrl,'type=web')"/>type=normal<xsl:value-of select="substring-after($previewUrl,'type=web')"/><xsl:value-of select="$id"/></pico:object>
				</xsl:otherwise>
			</xsl:choose>
		</pico:record>
    </xsl:template>
    <xsl:template name="DCMIType">
    	<xsl:param name="value"></xsl:param>    	
    	<xsl:param name="stprog"></xsl:param>    	
    		<xsl:choose>
    			<xsl:when test='fn:toLowerCase($value) = "testo a stampa" or fn:toLowerCase($value) = "books"'>
    				<dc:type xsi:type="dcterms:DCMIType">Text</dc:type>
    				<dc:type xsi:type="dcterms:DCMIType">PhysicalObject</dc:type>
    				<dc:subject xsi:type="pico:Thesaurus">http://culturaitalia.it/pico/thesaurus/4.2#beni_librari</dc:subject>
    			</xsl:when>
    			<xsl:when test='fn:toLowerCase($value) = "manoscritto"'>
    				<dc:type xsi:type="dcterms:DCMIType">Text</dc:type>
    				<dc:type xsi:type="dcterms:DCMIType">PhysicalObject</dc:type>
    				<dc:subject xsi:type="pico:Thesaurus">http://culturaitalia.it/pico/thesaurus/4.2#manoscritti</dc:subject>
    			</xsl:when>
    			<xsl:when test='fn:toLowerCase($value) = "musica a stampa"'><dc:type xsi:type="dcterms:DCMIType">StillImage</dc:type>
    				<dc:type xsi:type="dcterms:DCMIType">PhysicalObject</dc:type>
    				<dc:subject xsi:type="pico:Thesaurus">http://culturaitalia.it/pico/thesaurus/4.2#spartiti_musicali</dc:subject>
    			</xsl:when>
    			<xsl:when test='fn:toLowerCase($value) = "musica manoscritta"'>
    				<dc:type xsi:type="dcterms:DCMIType">Text</dc:type>
    				<dc:type xsi:type="dcterms:DCMIType">PhysicalObject</dc:type>
    				<dc:subject xsi:type="pico:Thesaurus">http://culturaitalia.it/pico/thesaurus/4.2#manoscritti</dc:subject>
    				<dc:subject xsi:type="pico:Thesaurus">http://culturaitalia.it/pico/thesaurus/4.2#spartiti_musicali</dc:subject>
    			</xsl:when>
    			<xsl:when test='fn:toLowerCase($value) = "cartografia a stampa"'>
    				<dc:type xsi:type="dcterms:DCMIType">StillImage</dc:type>
    				<dc:type xsi:type="dcterms:DCMIType">PhysicalObject</dc:type>
    				<dc:subject xsi:type="pico:Thesaurus">http://culturaitalia.it/pico/thesaurus/4.2#carte_geografiche_mappe</dc:subject>
    			</xsl:when>
    			<xsl:when test='fn:toLowerCase($value) = "cartografia manoscritta"'>
    				<dc:type xsi:type="dcterms:DCMIType">StillImage</dc:type>
    				<dc:type xsi:type="dcterms:DCMIType">PhysicalObject</dc:type>
    				<dc:subject xsi:type="pico:Thesaurus">http://culturaitalia.it/pico/thesaurus/4.2#carte_geografiche_mappe</dc:subject>
    				<dc:subject xsi:type="pico:Thesaurus">http://culturaitalia.it/pico/thesaurus/4.2#manoscritti</dc:subject>
    			</xsl:when>
    			<xsl:when test='fn:toLowerCase($value) = "materiale video"'>
    				<dc:type xsi:type="dcterms:DCMIType">MovingImage</dc:type>
    				<dc:subject xsi:type="pico:Thesaurus">http://culturaitalia.it/pico/thesaurus/4.2#videoregistrazioni</dc:subject>
    			</xsl:when>
    			<xsl:when test='fn:toLowerCase($value) = "registrazione sonora non musicale"'>
    				<dc:type xsi:type="dcterms:DCMIType">Sound</dc:type>
    				<dc:subject xsi:type="pico:Thesaurus">http://culturaitalia.it/pico/thesaurus/4.2#audioregistrazioni</dc:subject>
    			</xsl:when>
                <xsl:when test='fn:toLowerCase($value) = "registrazione sonora musicale" or fn:toLowerCase($value) = "registrazione sonora di musica"  or fn:toLowerCase($value) = "registrazione sonora di parlato"  or fn:toLowerCase($value) = "registrazione sonora di ambiente"  or fn:toLowerCase($value) = "registrazione sonora di programma radiofonico"'>
					<dc:type xsi:type="dcterms:DCMIType">Sound</dc:type>
    				<dc:subject xsi:type="pico:Thesaurus">http://culturaitalia.it/pico/thesaurus/4.2#audioregistrazioni</dc:subject>
    				<dc:subject xsi:type="pico:Thesaurus">http://culturaitalia.it/pico/thesaurus/4.1#musica</dc:subject></xsl:when>
    			<xsl:when test='fn:toLowerCase($value) = "materiale grafico" or fn:toLowerCase($value) = "image" or fn:toLowerCase($value) = "documento grafico"'>
    				<dc:type xsi:type="dcterms:DCMIType">StillImage</dc:type>
    				<dc:subject xsi:type="pico:Thesaurus">http://culturaitalia.it/pico/thesaurus/4.2#beni_librari</dc:subject>
   				</xsl:when>
    			<xsl:when test='fn:toLowerCase($value) = "oggetto a tre dimensioni"'>
    				<dc:type xsi:type="dcterms:DCMIType">Image</dc:type>
    				<dc:subject xsi:type="pico:Thesaurus">http://culturaitalia.it/pico/thesaurus/4.2#risorse_documentarie_digitali</dc:subject>
   				</xsl:when>
   				<xsl:when test='fn:toLowerCase($value) = "evento"'>
    				<dc:type xsi:type="dcterms:DCMIType">Event</dc:type>
    				<dc:subject xsi:type="pico:Thesaurus">http://culturaitalia.it/pico/thesaurus/4.1#eventi</dc:subject>
   				</xsl:when>
    			<xsl:when test='fn:toLowerCase($value) = "fascicolo"'>
    				<dc:type xsi:type="dcterms:DCMIType">PhysicalObject</dc:type>    		
    				<dc:subject xsi:type="pico:Thesaurus">http://culturaitalia.it/pico/thesaurus/4.2#miscellanee_raccolte</dc:subject>
   				</xsl:when>
    			<xsl:when test='fn:toLowerCase($value) = "internet resources"'>
    				<dc:type xsi:type="dcterms:DCMIType">InteractiveResource</dc:type>    		    			
    				<dc:subject xsi:type="pico:Thesaurus">http://culturaitalia.it/pico/thesaurus/4.2#multimedia_e_risorse_interattive</dc:subject>
   				</xsl:when>
    			<xsl:when test='fn:toLowerCase($value) = "libretto" or fn:toLowerCase($value) = "libretto per musica"'>
    				<dc:type xsi:type="dcterms:DCMIType">PhysicalObject</dc:type>    		
    				<dc:subject xsi:type="pico:Thesaurus">http://culturaitalia.it/pico/thesaurus/4.1#edizioni_musicali</dc:subject>
   				</xsl:when>
    			<xsl:when test='fn:toLowerCase($value) = "stampato"'>
    				<dc:type xsi:type="dcterms:DCMIType">PhysicalObject</dc:type>    		
    				<dc:subject xsi:type="pico:Thesaurus">http://culturaitalia.it/pico/thesaurus/4.2#stampe_incisioni_matrici</dc:subject>
   				</xsl:when>
    			<xsl:when test='fn:toLowerCase($value) = "testo digitale"'>
    				<dc:type xsi:type="dcterms:DCMIType">InteractiveResource</dc:type>    		    			
    				<dc:subject xsi:type="pico:Thesaurus">http://culturaitalia.it/pico/thesaurus/4.2#multimedia_e_risorse_interattive</dc:subject>
   				</xsl:when>
   				<xsl:otherwise>
   					<xsl:choose>
   						<xsl:when test='fn:toLowerCase($stprog) = "http://emeroteca.braidense.it/progetti/eva.php"'>
   							<dc:type xsi:type="dcterms:DCMIType">Text</dc:type>
							<dc:type xsi:type="dcterms:DCMIType">PhysicalObject</dc:type>
							<dc:type>rivista a stampa</dc:type>
							<dc:subject xsi:type="pico:Thesaurus">http://culturaitalia.it/pico/thesaurus/4.2#beni_librari</dc:subject>
							<dc:subject xsi:type="pico:Thesaurus">http://culturaitalia.it/pico/thesaurus/4.2#periodici</dc:subject>
   						</xsl:when>
   						<xsl:when test='fn:toLowerCase($stprog) = "maremagnum"'>
   							<dc:type xsi:type="dcterms:DCMIType">Text</dc:type>
							<dc:type xsi:type="dcterms:DCMIType">PhysicalObject</dc:type>
							<dc:type>manoscritti</dc:type>
							<dc:subject xsi:type="pico:Thesaurus">http://culturaitalia.it/pico/thesaurus/4.2#beni_librari</dc:subject>
							<dc:subject xsi:type="pico:Thesaurus">http://culturaitalia.it/pico/thesaurus/4.2#manoscritti</dc:subject>
   						</xsl:when>
   						<xsl:when test='fn:toLowerCase($stprog) = "http://geoweb.venezia.sbn.it/mag/cartografiamarciana.html"'>
   							<dc:type xsi:type="dcterms:DCMIType">StillImage</dc:type>
							<dc:type xsi:type="dcterms:DCMIType">PhysicalObject</dc:type>
							<dc:subject xsi:type="pico:Thesaurus">http://culturaitalia.it/pico/thesaurus/4.2#beni_librari</dc:subject>
							<dc:subject xsi:type="pico:Thesaurus">http://culturaitalia.it/pico/thesaurus/4.2#carte_geografiche_mappe</dc:subject>
   						</xsl:when>
   						<xsl:when test='fn:toLowerCase($stprog) = "http://www.imss.fi.it/biblio/ibibdig.html"'>
   							<dc:type xsi:type="dcterms:DCMIType">Text</dc:type>
							<dc:type xsi:type="dcterms:DCMIType">PhysicalObject</dc:type>
							<dc:subject xsi:type="pico:Thesaurus">http://culturaitalia.it/pico/thesaurus/4.2#beni_librari</dc:subject>
   						</xsl:when>
   						<xsl:when test='fn:toLowerCase($stprog) = "http://geoweb.venezia.sbn.it/geoweb/geoweb.html"'>
   							<dc:type xsi:type="dcterms:DCMIType">StillImage</dc:type>
							<dc:type xsi:type="dcterms:DCMIType">PhysicalObject</dc:type>
							<dc:subject xsi:type="pico:Thesaurus">http://culturaitalia.it/pico/thesaurus/4.2#beni_librari</dc:subject>
							<dc:subject xsi:type="pico:Thesaurus">http://culturaitalia.it/pico/thesaurus/4.2#carte_geografiche_mappe</dc:subject>
   						</xsl:when>
   						<xsl:when test='fn:toLowerCase($stprog) = "giacomo puccini"'>
	   						<dc:type xsi:type="dcterms:DCMIType">Text</dc:type>
							<dc:type xsi:type="dcterms:DCMIType">PhysicalObject</dc:type>
							<dc:subject xsi:type="pico:Thesaurus">http://culturaitalia.it/pico/thesaurus/4.2#beni_librari</dc:subject>
							<dc:subject xsi:type="pico:Thesaurus">http://culturaitalia.it/pico/thesaurus/4.2#manoscritti</dc:subject>
   						</xsl:when>
   						<xsl:when test='fn:toLowerCase($stprog) = "carte geografiche sgi"'>
   							<dc:type xsi:type="dcterms:DCMIType">StillImage</dc:type>
							<dc:type xsi:type="dcterms:DCMIType">PhysicalObject</dc:type>
							<dc:subject xsi:type="pico:Thesaurus">http://culturaitalia.it/pico/thesaurus/4.2#beni_librari</dc:subject>
							<dc:subject xsi:type="pico:Thesaurus">http://culturaitalia.it/pico/thesaurus/4.2#carte_geografiche_mappe</dc:subject>
   						</xsl:when>
   						<xsl:when test='fn:toLowerCase($stprog) = "http://www.bncf.firenze.sbn.it/pagina.php?id=127"'>
	   						<dc:type xsi:type="dcterms:DCMIType">Text</dc:type>
							<dc:type xsi:type="dcterms:DCMIType">PhysicalObject</dc:type>
							<dc:subject xsi:type="pico:Thesaurus">http://culturaitalia.it/pico/thesaurus/4.2#beni_librari</dc:subject>
							<dc:subject xsi:type="pico:Thesaurus">http://culturaitalia.it/pico/thesaurus/4.2#manoscritti</dc:subject>
							<dc:description>Biblioteca Nazionale Centrale di Firenze, Autografi Ginori Conti</dc:description>
   						</xsl:when>
   						<xsl:when test='fn:toLowerCase($stprog) = "emerografia lucana in digitale"'>
   							<dc:type xsi:type="dcterms:DCMIType">Text</dc:type>
							<dc:type xsi:type="dcterms:DCMIType">PhysicalObject</dc:type>
							<dc:subject xsi:type="pico:Thesaurus">http://culturaitalia.it/pico/thesaurus/4.2#beni_librari</dc:subject>
							<dc:subject xsi:type="pico:Thesaurus">http://culturaitalia.it/pico/thesaurus/4.2#periodici</dc:subject>
   						</xsl:when>
	   					<xsl:when test='fn:toLowerCase($stprog) = "manoscritti musicali_terzo progetto "'>
	   						<dc:type xsi:type="dcterms:DCMIType">StillImage</dc:type>
							<dc:type xsi:type="dcterms:DCMIType">PhysicalObject</dc:type>
							<dc:subject xsi:type="pico:Thesaurus">http://culturaitalia.it/pico/thesaurus/4.2#beni_librari</dc:subject>
							<dc:subject xsi:type="pico:Thesaurus">http://culturaitalia.it/pico/thesaurus/4.2#manoscritti</dc:subject>
							<dc:subject xsi:type="pico:Thesaurus">http://culturaitalia.it/pico/thesaurus/4.2#spartiti_musicali</dc:subject>
   						</xsl:when>
	   					<xsl:when test='fn:toLowerCase($stprog) = "documenti musicali del conservatorio s. pietro a majella"'>
	   						<dc:type xsi:type="dcterms:DCMIType">StillImage</dc:type>
							<dc:type xsi:type="dcterms:DCMIType">PhysicalObject</dc:type>
							<dc:subject xsi:type="pico:Thesaurus">http://culturaitalia.it/pico/thesaurus/4.2#beni_librari</dc:subject>
							<dc:subject xsi:type="pico:Thesaurus">http://culturaitalia.it/pico/thesaurus/4.2#manoscritti</dc:subject>
							<dc:subject xsi:type="pico:Thesaurus">http://culturaitalia.it/pico/thesaurus/4.2#spartiti_musicali</dc:subject>
   						</xsl:when>
	   					<xsl:when test='fn:toLowerCase($stprog) = "http://www.casanatense.it/"'>
	   						<dc:type xsi:type="dcterms:DCMIType">Text</dc:type>
							<dc:type xsi:type="dcterms:DCMIType">PhysicalObject</dc:type>
							<dc:subject xsi:type="pico:Thesaurus">http://culturaitalia.it/pico/thesaurus/4.2#beni_librari</dc:subject>
   						</xsl:when>
	   				</xsl:choose>
   				</xsl:otherwise>
    		</xsl:choose>    	
    		<xsl:if test="$value">
    			<dc:type xml:lang="it"><xsl:value-of select="$value"></xsl:value-of></dc:type>
    		</xsl:if>
    </xsl:template> 
    <xsl:template match="dc:date">
		<xsl:if test="count(//dc:date) != 2">
			<xsl:if test="not(//mag:piece or  //mag:year)">
				<dc:date xsi:type="W3CDTF"><xsl:value-of select="normdate:normalize(.)"/></dc:date>
			</xsl:if>
		</xsl:if>
    </xsl:template>
    <xsl:template match="mag:year">
   		<dc:date  xsi:type="W3CDTF"><xsl:value-of select="normdate:normalize(.)"/></dc:date>
    </xsl:template>
     <xsl:template match="dc:language">
   		<dc:language xsi:type="ISO639-2"><xsl:value-of select="norm:normalize(.)"/></dc:language>
    </xsl:template>
	<xsl:template match="dc:format">
		<xsl:variable name="valueFormat"><xsl:value-of select="."/></xsl:variable>
		<xsl:if test="parent::mag:bib">
			<xsl:choose>
				<xsl:when test="fn:hasExtension(.) = 'true'">
					<dcterms:extent><xsl:value-of select="fn:getExtensionPart($valueFormat)"/></dcterms:extent>
					<dc:format><xsl:value-of select="fn:getWithoutExtensionPart($valueFormat)"/></dc:format>
				</xsl:when>
				<xsl:otherwise><dc:format><xsl:value-of select="."/></dc:format></xsl:otherwise>
			</xsl:choose>
		</xsl:if>
	</xsl:template>
    <xsl:template match="dc:identifier|dc:creator|dc:publisher
                 |dc:subject|dc:contributor
                 |dc:source|dc:relation|dc:coverage|dc:rights">
        <xsl:if test="parent::mag:bib"> 
        	<xsl:element name="{name()}"><xsl:value-of select="."/></xsl:element>
    		<!-- -<xsl:copy><xsl:value-of select="."/></xsl:copy>-->
    	</xsl:if>
    </xsl:template>
    <!-- nuovo template per creazione anche del titolo da description -->
    <xsl:template match="dc:description">
        <xsl:if test="parent::mag:bib"> 
        <xsl:element name="{name()}"><xsl:value-of select="."/></xsl:element>              
    		<!-- -<xsl:copy><xsl:value-of select="."/></xsl:copy>-->
    	</xsl:if>
    	<xsl:if test="not(//dc:title)">
    		<dc:title><xsl:value-of select="substring(.,0,160)"/></dc:title>
    	</xsl:if>
    </xsl:template>
    <xsl:template match="mag:holdings">
    	<!-- rightHolders 14-01-2015 -->
    	<xsl:if test="mag:library/text()">
    		<dcterms:rightsHolder><xsl:value-of select="mag:library/text()"/></dcterms:rightsHolder>
    	</xsl:if>
    	<xsl:if test="./mag:shelfmark/text()">
    		<!--  non type 20/09/2011  xsi:type="iccd:DCSV" -->
    			<dc:identifier>name=segnatura; value=<xsl:choose>
    				<xsl:when test="mag:library/text()">
    					<xsl:value-of select="mag:library/text()"/>
    				</xsl:when>
    				<xsl:otherwise><xsl:value-of select="../../mag:gen/mag:agency/text()"/></xsl:otherwise>
    				</xsl:choose><xsl:text>, </xsl:text>
    				<xsl:if test="mag:inventory_number/text()">
    					<xsl:text>inv. </xsl:text>
    					<xsl:value-of select="mag:inventory_number/text()"/>
    					<xsl:text>, </xsl:text>
    				</xsl:if>
    				<xsl:if test="mag:shelfmark/text()">
    				    <xsl:for-each select="mag:shelfmark">
    				    	<xsl:if test="text()">
    				    		<xsl:value-of select="normalize-space(.)"></xsl:value-of>
    				    		<xsl:if test="position() &lt; last()">
    				    		<xsl:text> ; </xsl:text>
    				    		</xsl:if>
    				    	</xsl:if>
    				    </xsl:for-each>
    					<!-- <xsl:value-of select="iccu:shelfmark/text()"/> -->
    				</xsl:if>    				
    			</dc:identifier>
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
    <xsl:template match="mag:access_rights">
    	<dcterms:accessRights xml:lang="it"><xsl:if test='text() = "0"'>accesso privato</xsl:if>
    	<xsl:if test='text() = "1"'>accesso pubblico</xsl:if></dcterms:accessRights>
    </xsl:template>
    <xsl:template match="mag:completeness"></xsl:template>
    <xsl:template match="mag:stprog">
		    <pico:isDigitisedBy xsi:type="mag:GEN" xml:lang="it">stprog=<xsl:choose><xsl:when test="text() = 'MareMagnum'">http://www.maru.firenze.sbn.it/MareMagnum/mare_magnum.htm</xsl:when><xsl:otherwise><xsl:value-of select="."/></xsl:otherwise></xsl:choose>;<xsl:if test="../mag:collection">collection=<xsl:value-of select="../mag:collection"/>;</xsl:if><xsl:if test="../mag:agency">agency=<xsl:value-of select="../mag:agency"/>;</xsl:if>completeness=<xsl:if test="../mag:completeness = 1">digitalizzazione incompleta</xsl:if><xsl:if test="../mag:completeness = 0">digitalizzazione completa</xsl:if></pico:isDigitisedBy>
    </xsl:template>
    <!-- 
    <xsl:template match="mag:stru"></xsl:template>
    <xsl:template match="mag:img"></xsl:template>
    <xsl:template match="mag:video"></xsl:template>
    <xsl:template match="mag:audio"></xsl:template>
    <xsl:template match="dc:type"></xsl:template>
    -->
    <xsl:template match="text()"></xsl:template>
</xsl:stylesheet>