<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"   
    xmlns:dc="http://purl.org/dc/elements/1.1/"
    xmlns:dcterms="http://purl.org/dc/terms/" 
    xmlns:xml="http://www.w3.org/XML/1998/namespace"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 	xmlns:xlink="http://www.w3.org/TR/xlink"
	xmlns:xlink1999="http://www.w3.org/1999/xlink"
    xmlns:PREMIS="info:lc/xmlns/premis-v2"
    xmlns:mag="http://www.iccu.sbn.it/metaAG1.pdf"
    xmlns:marc="http://www.loc.gov/MARC21/slim"
    xmlns:xalan="http://xml.apache.org/xalan"
    xmlns:gbs="http://books.google.com/gbs"
    xmlns:niso="http://www.niso.org/pdfs/DataDict.pdf"
	xmlns:videomd="http://www.loc.gov/videoMD/"
	xmlns:mix="http://www.loc.gov/mix/v20"
	xmlns:audiomd="http://www.loc.gov/audioMD/"
    xmlns:METS="http://www.loc.gov/METS/"
	xmlns:meta="http://gruppometa.com/namespace"
    xmlns:fn="http://www.w3.org/2005/xpath-functions"
	xmlns:ic="http://internetculturale.it/saxon-extension"            
	xmlns:saxon="http://saxon.sf.net/"
	xmlns:mods="http://www.loc.gov/mods/v3"
	extension-element-prefixes="saxon"
    exclude-result-prefixes="xalan ic saxon fn METS gbs PREMIS marc meta xlink1999"
    version="2.0"
    >
    <xsl:output
        method="xml"
        encoding="UTF-8"
        
    />
	<xsl:param name="baseDir"></xsl:param>
	<xsl:param name="stprog"></xsl:param>
	<xsl:param name="collection"></xsl:param>
	<xsl:param name="agency"></xsl:param>
	<xsl:param name="access_rights"></xsl:param>
	<xsl:param name="completeness"></xsl:param>
	<xsl:param name="external">EXTERNAL</xsl:param>
	<xsl:param name="quality">ARCHIVE</xsl:param>

    <xsl:template match="/">
    	<mag:metadigit>
    	<mag:gen>
    		<xsl:attribute name="creation"><xsl:value-of select="//METS:metsHdr/@CREATEDATE"/></xsl:attribute>
            <xsl:choose>
                <xsl:when test="//METS:metsHdr/@LASTMODDATE"><xsl:attribute name="last_update"><xsl:value-of select="//METS:metsHdr/@LASTMODDATE"/></xsl:attribute></xsl:when>
                <xsl:otherwise><xsl:attribute name="last_update"><xsl:value-of select="//METS:metsHdr/@CREATEDATE"/></xsl:attribute></xsl:otherwise>
            </xsl:choose>
    		<mag:stprog><xsl:choose>
				<xsl:when test="fn:string-length($stprog) gt 0"><xsl:value-of select="$stprog"/></xsl:when>
				<xsl:otherwise><xsl:if test="//gbs:sourceLibrary"><xsl:value-of select="ic:getStprog(//gbs:sourceLibrary)"/></xsl:if></xsl:otherwise>
			</xsl:choose></mag:stprog>
    		<mag:collection><xsl:choose>
				<xsl:when test="//METS:dmdSec/METS:mdWrap//mods:relatedItem/mods:titleInfo[@type='digitalCollection']"><xsl:for-each select="//METS:dmdSec/METS:mdWrap//mods:relatedItem/mods:titleInfo[@type='digitalCollection']"><xsl:if test="mods:nonSort"><xsl:value-of select="mods:nonSort"/> </xsl:if><xsl:value-of
						select="mods:title"/><xsl:if test="mods:subTitle"> : <xsl:value-of
						select="mods:subTitle"/></xsl:if></xsl:for-each></xsl:when>
				<xsl:when test="//METS:dmdSec/METS:mdWrap//mods:relatedItem[@otherType='digitalCollection']/mods:titleInfo"><xsl:for-each select="//METS:dmdSec/METS:mdWrap//mods:relatedItem[@otherType='digitalCollection']/mods:titleInfo"><xsl:if test="mods:nonSort"><xsl:value-of select="mods:nonSort"/> </xsl:if><xsl:value-of
						select="mods:title"/><xsl:if test="mods:subTitle"> : <xsl:value-of
						select="mods:subTitle"/></xsl:if></xsl:for-each></xsl:when>
				<xsl:when test="fn:string-length($collection) gt 0"><xsl:value-of select="$collection"/></xsl:when>
				<xsl:otherwise>Collezione generale</xsl:otherwise>
			</xsl:choose></mag:collection>
    		<mag:agency><xsl:choose>
				<xsl:when test="fn:string-length($agency) gt 0"><xsl:value-of select="$agency"/></xsl:when>
				<xsl:otherwise><xsl:if test="//gbs:sourceLibrary"><xsl:value-of select="ic:getAgency(//gbs:sourceLibrary)"/></xsl:if></xsl:otherwise>
			</xsl:choose></mag:agency>
    		<mag:access_rights><xsl:choose>
				<xsl:when test="fn:string-length($access_rights) gt 0"><xsl:value-of select="$access_rights"/></xsl:when>
				<xsl:otherwise>1</xsl:otherwise>
			</xsl:choose></mag:access_rights>
    		<mag:completeness><xsl:choose>
				<xsl:when test="fn:string-length($completeness) gt 0"><xsl:value-of select="$completeness"/></xsl:when>
				<xsl:otherwise>0</xsl:otherwise>
			</xsl:choose></mag:completeness>
    	</mag:gen>
    	<xsl:variable name="level"><xsl:choose>
			<xsl:when test="//METS:dmdSec/METS:mdWrap//mods:originInfo/mods:issuance eq 'continuing'">s</xsl:when>
			<xsl:otherwise>m</xsl:otherwise>
		</xsl:choose></xsl:variable>
    	<mag:bib>
			<xsl:attribute name="level"><xsl:value-of select="$level"/></xsl:attribute>
			<xsl:for-each select="//METS:dmdSec/METS:mdWrap/METS:xmlData/mods:mods">
				<!-- identifier -->
				<xsl:for-each select="mods:identifier[@type='logicalId']">
					<dc:identifier><xsl:value-of select="."/></dc:identifier>
				</xsl:for-each>
				<!-- title -->
				<xsl:for-each select="mods:titleInfo[not(@type)]">
					<dc:title><xsl:call-template name="title"/></dc:title>
				</xsl:for-each>
				<!-- creator -->
				<xsl:for-each select="mods:name">
					<xsl:if test="(mods:role/mods:roleTerm) eq 'Autore' or (mods:role/mods:roleTerm eq 'Mittente')">
						<xsl:call-template name="name"></xsl:call-template>
					</xsl:if>
				</xsl:for-each>
				<!-- publisher -->
				<xsl:for-each select="mods:originInfo">
					<dc:publisher><xsl:value-of select="mods:place/mods:placeTerm"/> : <xsl:value-of select="mods:publisher"/>, <xsl:value-of
						select="mods:dateIssued[@point='start']"/>-<xsl:value-of
							select="mods:dateIssued[@point='end']"/></dc:publisher>
				</xsl:for-each>
				<!-- subject -->
				<xsl:for-each select="mods:subject">
					<xsl:if test="mods:topic">
						<dc:subject><xsl:value-of select="mods:topic"/></dc:subject>
					</xsl:if>
					<xsl:if test="mods:geographic">
						<dc:subject><xsl:value-of select="mods:geographic"/></dc:subject>
					</xsl:if>
					<xsl:if test="mods:temporal">
						<dc:subject><xsl:value-of select="mods:temporal"/></dc:subject>
					</xsl:if>
					<xsl:if test="mods:genre">
						<dc:subject><xsl:value-of select="mods:genre"/></dc:subject>
					</xsl:if>
					<xsl:if test="mods:classification">
						<dc:subject><xsl:value-of select="mods:classification"/></dc:subject>
					</xsl:if>
					<xsl:if test="mods:name/mods:namePart">
						<dc:subject><xsl:value-of select="mods:name/mods:namePart"/></dc:subject>
					</xsl:if>
					<xsl:if test="mods:titleInfo">
						<dc:subject><xsl:value-of select="normalize-space(mods:titleInfo)"/></dc:subject>
					</xsl:if>
				</xsl:for-each>
				<!-- description -->
				<xsl:for-each select="mods:note[not(@type)]">
					<dc:description><xsl:value-of select="."/></dc:description>
				</xsl:for-each>
				<xsl:for-each select="mods:note[@type='dedication']">
					<dc:description>'dedica:'<xsl:value-of select="."/></dc:description>
				</xsl:for-each>
				<xsl:for-each select="mods:note[@type='device']">
					<dc:description>'marca:'<xsl:value-of select="."/></dc:description>
				</xsl:for-each>
				<xsl:for-each select="mods:identifier[@type='fingerprint']">
					<dc:description>'impronta:'<xsl:value-of select="."/></dc:description>
				</xsl:for-each>
				<xsl:for-each select="mods:abstract[@type='summary']">
					<dc:description>'abstract:'<xsl:value-of select="."/></dc:description>
				</xsl:for-each>
				<xsl:for-each select="mods:note[@type='characterPerformer']">
					<dc:description>Personaggi e interpreti: <xsl:value-of select="."/></dc:description>
				</xsl:for-each>
				<xsl:for-each select="mods:location/mods:holdingSimple/mods:note[@type='copyNote']">
					<dc:description><xsl:value-of select="."/></dc:description>
				</xsl:for-each>
				<xsl:for-each select="mods:location/mods:holdingSimple/mods:copyInformation/mods:enumerationAndChronology">
					<dc:description>[consistenza]<xsl:value-of select="."/></dc:description>
				</xsl:for-each>
				<xsl:for-each select="mods:note[@type='sequentialDesignation']">
					<dc:description>[numerazione]<xsl:value-of select="."/></dc:description>
				</xsl:for-each>
				<xsl:for-each select="mods:relatedItem[@type='otherTitle']/mods:titleInfo">
					<dc:description><xsl:call-template name="title"/></dc:description>
					<dc:description>[supplemento di]<xsl:call-template name="title"/></dc:description>
				</xsl:for-each>
				<!-- contributor -->
				<xsl:for-each select="mods:name">
					<xsl:if test="(mods:role/mods:roleTerm) ne 'Autore' and (mods:role/mods:roleTerm ne 'Mittente')">
						<xsl:call-template name="name"></xsl:call-template>
					</xsl:if>
				</xsl:for-each>
				<!-- date -->
				<xsl:for-each select="mods:originInfo">
					<xsl:for-each select="mods:dateCreated|mods:dateIssued">
						<dc:date><xsl:value-of select="."/></dc:date>
					</xsl:for-each>
				</xsl:for-each>
				<!-- type -->
				<xsl:for-each select="mods:typeOfResource">
					<dc:type><xsl:value-of select="."/></dc:type>
				</xsl:for-each>
				<!-- format -->
				<xsl:for-each select="mods:physicalDescription/mods:extent">
					<dc:format><xsl:value-of select="."/></dc:format>
				</xsl:for-each>
				<!-- source -->
				<xsl:for-each select="mods:titleInfo[@type='original']">
					<dc:source><xsl:value-of select="."/></dc:source>
				</xsl:for-each>
				<!-- language -->
				<xsl:for-each select="mods:language/mods:languageTerm">
					<dc:language><xsl:value-of select="@code"/></dc:language>
				</xsl:for-each>
				<!-- relations -->
				<xsl:for-each select="mods:titleInfo[@type and not(@type='original')]">
					<dc:relation><xsl:call-template name="labelRelation">
						<xsl:with-param name="tag"><xsl:value-of select="@type"/></xsl:with-param></xsl:call-template><xsl:call-template
							name="title"/></dc:relation>
				</xsl:for-each>
				<xsl:for-each select="mods:relatedItem[@type='otherTitle']/mods:titleInfo">
					<dc:relation>'supplemento di:'<xsl:call-template name="title"/></dc:relation>
				</xsl:for-each>
				<xsl:for-each select="mods:abstract[@type='incipit']">
					<dc:relation>'incipit:'<xsl:value-of select="."/></dc:relation>
				</xsl:for-each>
				<xsl:for-each select="mods:relatedItem/mods:titleInfo[@type='series']">
					<dc:relation>'collana:'<xsl:call-template name="title"/></dc:relation>
				</xsl:for-each>
				<!-- coverage -->
				<xsl:for-each select="mods:subject/mods:geographic[@type='coverage']">
					<dc:coverage><xsl:value-of select="."/></dc:coverage>
				</xsl:for-each>
				<xsl:for-each select="mods:subject/mods:temporal[@type='coverage']">
					<dc:coverage><xsl:value-of select="."/></dc:coverage>
				</xsl:for-each>
				<!-- rights -->
				<!-- holdings -->
				<xsl:if test="mods:location or mods:identifier[@type='managementId']">
					<mag:holdings>
						<xsl:if test="mods:location/mods:physicalLocation">
							<mag:library><xsl:value-of select="mods:location/mods:physicalLocation"/></mag:library>
						</xsl:if>
						<xsl:if test="mods:identifier[@type='managementId']">
							<mag:inventory_number><xsl:value-of select="mods:identifier[@type='managementId']"/></mag:inventory_number>
						</xsl:if>
						<xsl:if test="mods:location/mods:holdingSimple/mods:copyInformation/mods:shelfLocator">
							<mag:shelfmark><xsl:value-of select="mods:location/mods:holdingSimple/mods:copyInformation/mods:shelfLocator"/></mag:shelfmark>
						</xsl:if>
					</mag:holdings>
				</xsl:if>
				<!-- geo_coord -->
				<xsl:if test="mods:subject/mods:cartographics/mods:coordinates or mods:originInfo/mods:dateOther[@type='notDate']">
					<mag:local_bib>
						<xsl:for-each select="mods:subject/mods:cartographics/mods:coordinates">
							<mag:geo_coord><xsl:value-of select="."/></mag:geo_coord>
						</xsl:for-each>
						<xsl:for-each select="mods:originInfo/mods:dateOther[@type='notDate']">
							<mag:not_date><xsl:value-of select="."/></mag:not_date>
						</xsl:for-each>
					</mag:local_bib>
				</xsl:if>
				<xsl:if test="mods:titleInfo/mods:partNumber or mods:part[@type='issue'] or mods:identifier[@type='dossierId']">
					<mag:piece>
						<xsl:choose>
							<xsl:when test="mods:titleInfo/mods:partNumber">
								<xsl:for-each select="mods:titleInfo/mods:partNumber">
									<mag:part_number><xsl:value-of select="."/></mag:part_number>
								</xsl:for-each>
								<xsl:for-each select="mods:titleInfo/mods:partName">
									<mag:part_name><xsl:value-of select="."/></mag:part_name>
								</xsl:for-each>
							</xsl:when>
							<xsl:otherwise>
								<xsl:for-each select="mods:part[@type='issue']/mods:date">
									<mag:year><xsl:value-of select="."/></mag:year>
								</xsl:for-each>
								<xsl:for-each select="mods:part[@type='issue']/mods:detail/mods:number">
									<mag:issue><xsl:value-of select="."/></mag:issue>
								</xsl:for-each>
								<xsl:choose>
									<!-- solo se è un seriale -->
									<xsl:when test="mods:identifier[@type='dossierId'] and //METS:dmdSec/METS:mdWrap//mods:originInfo/mods:issuance eq 'continuing'">
										<xsl:for-each select="mods:identifier[@type='dossierId']">
											<mag:stpiece_per><xsl:value-of select="."/></mag:stpiece_per>
										</xsl:for-each>
									</xsl:when>
									<xsl:otherwise>
										<!--<mag:stpiece_per>(199312/199401)20:2</mag:stpiece_per>-->
									</xsl:otherwise>
								</xsl:choose>
							</xsl:otherwise>
						</xsl:choose>
					</mag:piece>
				</xsl:if>
			</xsl:for-each>
    	</mag:bib>
    	<mag:stru>
			<xsl:for-each select="//METS:structMap[@TYPE='LOGICAL']">
				<xsl:apply-templates select="METS:div"/>
			</xsl:for-each>
		</mag:stru>
    	
    	<!-- sezione ocr e img -->
    	<xsl:for-each select="//METS:fileSec/METS:fileGrp[@USE=$external]/METS:fileGrp/METS:fileGrp[@USE=$quality]/METS:file">
			<xsl:sort select="meta:getOrderOfGroup(../../@USE)"/>
    		<xsl:variable name="idFile"><xsl:value-of select="@ID"/></xsl:variable>
    		<xsl:variable name="name"><xsl:choose>
				<xsl:when test="../../@USE='IMAGE'">mag:img</xsl:when>
				<xsl:when test="../../@USE='AUDIO'">mag:audio</xsl:when>
				<xsl:when test="../../@USE='VIDEO'">mag:video</xsl:when>
				<xsl:when test="../../@USE='TEXT'">mag:doc</xsl:when>
				<xsl:otherwise>mag:ocr</xsl:otherwise>
			</xsl:choose></xsl:variable>
    		<xsl:element name="{$name}">
    			<mag:sequence_number><xsl:value-of select="//METS:structMap[fn:upper-case(@TYPE)='PHYSICAL']//METS:fptr[@FILEID=$idFile]/parent::METS:div/@ORDER"/></mag:sequence_number>
    			<mag:nomenclature><xsl:value-of select="//METS:structMap[fn:upper-case(@TYPE)='PHYSICAL']//METS:fptr[@FILEID=$idFile]/parent::METS:div/@LABEL"/></mag:nomenclature>
				<xsl:choose>
					<xsl:when test="$name eq 'mag:audio' or $name eq 'mag:video'">
						<mag:proxies>
							<xsl:call-template  name="file">
								<xsl:with-param name="name"><xsl:value-of select="$name"/></xsl:with-param>
							</xsl:call-template>
							<mag:datetimecreated>2022-02-28T00:00:00</mag:datetimecreated>
						</mag:proxies>
					</xsl:when>
					<xsl:otherwise>
						<xsl:call-template  name="file">
							<xsl:with-param name="name"><xsl:value-of select="$name"/></xsl:with-param>
						</xsl:call-template>
						<mag:datetimecreated>2022-02-28T00:00:00</mag:datetimecreated>
					</xsl:otherwise>
				</xsl:choose>
    		</xsl:element>
    	</xsl:for-each>

			<mag:dis>
			<xsl:for-each select="//METS:fileSec/METS:fileGrp[@USE=$external]/METS:fileGrp[@USE='MANIFEST']">
				<mag:dis_item>
					<mag:file Location="URL">
						<xsl:attribute name="xlink:role">manifest</xsl:attribute>
						<xsl:attribute name="xlink:href"><xsl:value-of select="METS:file/METS:FLocat/@xlink1999:href"/></xsl:attribute>
					</mag:file>
				</mag:dis_item>
			</xsl:for-each>
				<xsl:for-each select="//METS:fileSec/METS:fileGrp[@USE=$external]/METS:fileGrp[@USE='VIEWER']">
					<mag:dis_item>
						<mag:file Location="URL">
							<xsl:attribute name="xlink:role">viewer</xsl:attribute>
							<xsl:attribute name="xlink:href"><xsl:value-of select="METS:file/METS:FLocat/@xlink1999:href"/></xsl:attribute>
						</mag:file>
					</mag:dis_item>
				</xsl:for-each>
			<xsl:for-each select="//METS:fileSec/METS:fileGrp[@USE=$external]/METS:fileGrp[@USE='IMAGE']/METS:fileGrp[@USE='PREVIEW']">
				<mag:dis_item>
					<mag:file Location="URL">
						<xsl:attribute name="xlink:role">preview</xsl:attribute>
						<xsl:attribute name="xlink:href"><xsl:value-of select="METS:file/METS:FLocat/@xlink1999:href"/></xsl:attribute>
					</mag:file>
					<mag:preview>thumbnail</mag:preview>
				</mag:dis_item>
			</xsl:for-each>
			</mag:dis>
    	</mag:metadigit>
    </xsl:template>

	<xsl:function name="meta:getOrderOfGroup">
		<xsl:param name="use"/>
		<xsl:sequence select="if($use='IMAGE') then '1' else if($use='AUDIO') then '2' else if($use='VIDEO') then '3' else '4'"/>
	</xsl:function>

	<xsl:template name="title">
		<xsl:if test="mods:nonSort"><xsl:value-of select="mods:nonSort"/> </xsl:if><xsl:value-of
			select="mods:title"/><xsl:if test="mods:subTitle"> : <xsl:value-of
			select="mods:subTitle"/></xsl:if><xsl:if test="mods:identifier[@type='relatedId']"> {<xsl:value-of select="//METS:dmdSec/METS:mdWrap//mods:identifier[@type='relatedId']"/>}</xsl:if>
	</xsl:template>

	<xsl:template name="file">
		<xsl:param name="name"></xsl:param>
		<mag:usage>3</mag:usage>
		<mag:file Location="URL" xlink:type="simple">
			<xsl:attribute name="xlink:href"><xsl:value-of select="METS:FLocat/@xlink1999:href"/></xsl:attribute>
		</mag:file>
		<xsl:if test="@CHECKSUMTYPE='MD5' and not(../../@USE='IMAGE')">
			<mag:md5><xsl:value-of select="@CHECKSUM"/></mag:md5>
		</xsl:if>

		<xsl:if test="$name='mag:ocr'">
			<mag:source xlink:type="simple">
				<xsl:attribute name="xlink:href"><xsl:value-of select="fn:replace(METS:FLocat/@xlink1999:href,'.txt','.jpg')"/></xsl:attribute></mag:source>
		</xsl:if>

		<xsl:if test="number(@SIZE) gt 0 and not(../../@USE='IMAGE')">
			<mag:filesize><xsl:value-of select="@SIZE"/></mag:filesize>
		</xsl:if>

		<xsl:if test="../../@USE='IMAGE'">
			<xsl:if test="@CHECKSUM">
				<mag:md5><xsl:value-of select="@CHECKSUM"/></mag:md5>
			</xsl:if>
			<xsl:if test="@SIZE">
				<mag:filesize><xsl:value-of select="@SIZE"/></mag:filesize>
			</xsl:if>
		</xsl:if>
		<!-- TODO -->
		<xsl:variable name="adminId"><xsl:value-of select="@ADMID"/></xsl:variable>
		<xsl:choose>
			<xsl:when test="$name eq 'mag:img'">
				<mag:image_dimensions>
					<xsl:choose>
						<xsl:when test="@ADMID">
							<niso:imagelength><xsl:choose>
								<xsl:when test="//METS:amdSec[@ID=$adminId]//niso:imagelength"><xsl:value-of select="//METS:amdSec[@ID=$adminId]//niso:imagelength"/></xsl:when>
								<xsl:when test="//METS:amdSec[@ID=$adminId]//mix:imageHeight"><xsl:value-of select="//METS:amdSec[@ID=$adminId]//mix:imageHeight"/></xsl:when>
								<xsl:otherwise>1000</xsl:otherwise>
							</xsl:choose></niso:imagelength>
							<niso:imagewidth><xsl:choose>
								<xsl:when test="//METS:amdSec[@ID=$adminId]//niso:imagewidth"><xsl:value-of select="//METS:amdSec[@ID=$adminId]//niso:imagewidth"/></xsl:when>
								<xsl:when test="//METS:amdSec[@ID=$adminId]//mix:imageWidth"><xsl:value-of select="//METS:amdSec[@ID=$adminId]//mix:imageWidth"/></xsl:when>
								<xsl:otherwise>1000</xsl:otherwise>
							</xsl:choose></niso:imagewidth>
						</xsl:when>
						<xsl:otherwise>
							<niso:imagelength>2000</niso:imagelength>
							<niso:imagewidth>2000</niso:imagewidth>
						</xsl:otherwise>
					</xsl:choose>
				</mag:image_dimensions>
			</xsl:when>
			<xsl:when test="$name eq 'mag:audio' and //METS:amdSec[@ID=$adminId]//audiomd:duration">
				<mag:audio_dimensions>
					<mag:duration><xsl:value-of select="//METS:amdSec[@ID=$adminId]//audiomd:duration"/></mag:duration>
				</mag:audio_dimensions>
			</xsl:when>
			<xsl:when test="$name eq 'mag:audio' and not(//METS:amdSec[@ID=$adminId]//audiomd:duration)">
				<mag:audio_dimensions>
					<mag:duration>00:00:10</mag:duration>
				</mag:audio_dimensions>
			</xsl:when>
			<xsl:when test="$name eq 'mag:video' and //METS:amdSec[@ID=$adminId]//videomd:duration">
				<mag:video_dimensions>
					<mag:duration><xsl:value-of select="//METS:amdSec[@ID=$adminId]//videomd:duration"/></mag:duration>
				</mag:video_dimensions>
			</xsl:when>
			<xsl:when test="$name eq 'mag:video' and not(//METS:amdSec[@ID=$adminId]//videomd:duration)">
				<mag:video_dimensions>
					<mag:duration>00:00:10</mag:duration>
				</mag:video_dimensions>
			</xsl:when>
		</xsl:choose>
		<xsl:choose>
			<xsl:when test="$name eq 'mag:img'">
				<mag:format>
					<niso:name><xsl:choose><xsl:when test="@MIMETYPE='image/jpeg'">JPG</xsl:when><xsl:otherwise>TXT</xsl:otherwise></xsl:choose></niso:name>
					<niso:mime><xsl:value-of select="@MIMETYPE"/></niso:mime>
					<niso:compression><xsl:choose><xsl:when test="@MIMETYPE='image/jpeg'">JPG</xsl:when><xsl:otherwise>Uncompressed</xsl:otherwise></xsl:choose></niso:compression>
				</mag:format>
			</xsl:when>
			<xsl:when test="$name eq 'mag:doc'">
				<mag:format>
					<niso:name><xsl:value-of select="@MIMETYPE"/></niso:name>
					<niso:mime>
						<xsl:choose>
							<xsl:when test="@MIMETYPE='audio/mpeg3'">audio/mpeg</xsl:when>
							<xsl:otherwise><xsl:value-of select="@MIMETYPE"/></xsl:otherwise>
						</xsl:choose>
					</niso:mime>
					<niso:compression>Uncompressed</niso:compression>
				</mag:format>
			</xsl:when>
			<xsl:when test="$name eq 'mag:audio'">
				<mag:format>
					<mag:name><xsl:value-of select="@MIMETYPE"/></mag:name>
					<mag:mime>
						<xsl:choose>
							<xsl:when test="@MIMETYPE='audio/mpeg3'">audio/mpeg</xsl:when>
							<xsl:otherwise><xsl:value-of select="@MIMETYPE"/></xsl:otherwise>
						</xsl:choose>
					</mag:mime>
					<mag:compression>MPEG-1 layer 3</mag:compression>
				</mag:format>
			</xsl:when>
			<xsl:when test="$name eq 'mag:video'">
				<mag:format>
					<mag:name><xsl:value-of select="@MIMETYPE"/></mag:name>
					<mag:mime>
						<xsl:choose>
							<xsl:when test="@MIMETYPE='video/mp4'">video/mpeg</xsl:when>
							<xsl:otherwise><xsl:value-of select="@MIMETYPE"/></xsl:otherwise>
						</xsl:choose>
					</mag:mime>
					<mag:videoformat>Unspecified</mag:videoformat>
				</mag:format>
			</xsl:when>
		</xsl:choose>

		<!--
        <mag:datetimecreated><xsl:value-of select="@CREATED"/></mag:datetimecreated>
        -->
	</xsl:template>

	<!-- stru -->
	<xsl:template match="METS:div">
		<xsl:variable name="linkto"><xsl:value-of select="METS:fptr/@FILEID"/></xsl:variable>
		<mag:stru>
			<xsl:if test="@ORDER">
				<mag:sequence_number><xsl:value-of select="@ORDER"/></mag:sequence_number>
			</xsl:if>
			<mag:nomenclature><xsl:value-of select="@LABEL"/></mag:nomenclature>
			<xsl:if test="@TYPE='FILE'">
				<mag:element>
					<xsl:variable name="start"><xsl:value-of select="//METS:structMap[fn:upper-case(@TYPE)='PHYSICAL']//METS:fptr[@FILEID=$linkto]/../@ORDER"/></xsl:variable>
					<mag:resource><xsl:call-template name="mapType">
						<xsl:with-param name="type"><xsl:value-of select="//METS:file[@ID=$linkto]/@MIMETYPE"/></xsl:with-param>
					</xsl:call-template></mag:resource>
					<mag:start><xsl:attribute name="sequence_number"><xsl:value-of select="$start"/></xsl:attribute></mag:start>
					<mag:stop><xsl:attribute name="sequence_number"><xsl:value-of select="$start"/></xsl:attribute></mag:stop>
				</mag:element>
			</xsl:if>
			<xsl:for-each select="METS:div">
				<xsl:apply-templates select="."/>
			</xsl:for-each>
		</mag:stru>
	</xsl:template>

	<xsl:template name="mapType">
		<xsl:param name="type"></xsl:param>
		<xsl:choose>
			<xsl:when test="$type='image/jpeg'">img</xsl:when>
			<xsl:when test="$type='audio/mpeg3'">audio</xsl:when>
			<xsl:when test="$type='audio/mpeg'">audio</xsl:when>
			<xsl:when test="$type='audio/mp3'">audio</xsl:when>
			<xsl:when test="$type='audio/mpeg4'">video</xsl:when>
			<xsl:when test="$type='application/pdf'">doc</xsl:when>
			<xsl:otherwise>img</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="name">
		<xsl:variable name="element"><xsl:choose>
			<xsl:when test="(mods:role/mods:roleTerm) eq 'Autore' or (mods:role/mods:roleTerm eq 'Mittente')">dc:creator</xsl:when>
			<xsl:otherwise>dc:contributor</xsl:otherwise>
		</xsl:choose></xsl:variable>
		<xsl:element name="{$element}"><xsl:value-of select="mods:namePart[@type='family']"/><xsl:if test="mods:namePart[@type='family'] and mods:namePart[@type='given']">, </xsl:if><xsl:value-of select="mods:namePart[@type='given']"/><xsl:if test="mods:namePart[@type='termsOfAddress' or @type='date']"> &lt;<xsl:value-of
				select="substring(mods:namePart[@type='date'],0,5)"/><xsl:if test="mods:namePart[@type='date'] and mods:namePart[@type='termsOfAddress']"> ; </xsl:if><xsl:value-of select="mods:namePart[@type='termsOfAddress']"/>&gt;</xsl:if><xsl:if test="(mods:role/mods:roleTerm) ne 'Autore'"> [<xsl:value-of
				select="mods:role/mods:roleTerm"/>]</xsl:if><xsl:if test="mods:nameIdentifier">[VID: <xsl:value-of select="mods:nameIdentifier"/>]</xsl:if></xsl:element>
	</xsl:template>

	<xsl:template name="labelRelation">
		<xsl:param name="tag"></xsl:param>
		<xsl:choose>
			<xsl:when test="$tag='uniform'"><xsl:text>'titolo uniforme:'</xsl:text></xsl:when>
			<xsl:when test="$tag='alternative'"><xsl:text>'titolo alternativo:'</xsl:text></xsl:when>
			<xsl:when test="$tag='translated'"><xsl:text>'titolo parallelo:'</xsl:text></xsl:when>
			<xsl:when test="$tag='variantTitle'"><xsl:text>'variante del titolo:'</xsl:text></xsl:when>
			<xsl:when test="$tag='extantTitle'"><xsl:text>'titolo presente:'</xsl:text></xsl:when>
			<xsl:when test="$tag='identifiedTitle'"><xsl:text>'titolo identificato:'</xsl:text></xsl:when>
			<xsl:when test="$tag='addedTitle'"><xsl:text>'titolo aggiunto:'</xsl:text></xsl:when>
			<xsl:when test="$tag='createdTitle'"><xsl:text>'titolo elaborato:'</xsl:text></xsl:when>
			<xsl:when test="$tag='otherTitle'"><xsl:text></xsl:text></xsl:when>
			<xsl:when test="$tag='identifiedTitle'"><xsl:text>'titolo identificato:'</xsl:text></xsl:when>
			<xsl:when test="$tag='issuedWith'"><xsl:text>'pubblicato con:'</xsl:text></xsl:when>
			<xsl:when test="$tag='constituent'"><xsl:text>'fa parte di:'</xsl:text></xsl:when>
			<xsl:when test="$tag='host'"><xsl:text>'comprende:'</xsl:text></xsl:when>
			<xsl:when test="$tag='grouping'"><xsl:text>'raggruppamento:'</xsl:text></xsl:when>
			<xsl:when test="$tag='relatedResource'"><xsl:text>'risorse collegate:'</xsl:text></xsl:when>
			<xsl:when test="$tag='seeAlso'"><xsl:text>'vedi anche:'</xsl:text></xsl:when>
			<xsl:otherwise></xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>

