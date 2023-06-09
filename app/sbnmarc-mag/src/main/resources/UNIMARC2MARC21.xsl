<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:marc="http://www.loc.gov/MARC21/slim" version="1.0"
  xmlns="http://www.loc.gov/MARC21/slim"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform" exclude-result-prefixes="marc">
  <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>

  <!--
  Transformation from UNIMARC XML representation to MARCXML.
  Based upon http://www.loc.gov/marc/unimarctomarc21.html
  
  -->
  <xsl:template match="/">
    <xsl:choose>
      <xsl:when test="marc:collection">
        <collection xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.loc.gov/MARC21/slim http://www.loc.gov/standards/marcxml/schema/MARC21slim.xsd">
          <xsl:for-each select="marc:collection/marc:record">
            <record>
              <xsl:call-template name="record"/>
            </record>
          </xsl:for-each>
        </collection>
      </xsl:when>
      <xsl:otherwise>
        <record xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.loc.gov/MARC21/slim http://www.loc.gov/standards/marcxml/schema/MARC21slim.xsd">
          <xsl:for-each select="marc:record">
            <xsl:call-template name="record"/>
          </xsl:for-each>
        </record>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <xsl:template name="record">
    <xsl:if test="@type">
      <xsl:attribute name="type">
        <xsl:value-of select="@type"/>
      </xsl:attribute>
    </xsl:if>
    <xsl:call-template name="transform-leader"/>
    <xsl:call-template name="copy-control">
      <xsl:with-param name="tag">001</xsl:with-param>
    </xsl:call-template>
    <xsl:call-template name="copy-control">
      <xsl:with-param name="tag">005</xsl:with-param>
    </xsl:call-template>
    <xsl:call-template name="transform-100"/>

    <!--010->020-->
    <xsl:call-template name="transform-datafield">
      <xsl:with-param name="srcTag">010</xsl:with-param>
      <xsl:with-param name="dstTag">020</xsl:with-param>
      <xsl:with-param name="srcCodes">abdz</xsl:with-param>
      <xsl:with-param name="dstCodes">abcz</xsl:with-param>
      <xsl:with-param name="normalizeCodes">-</xsl:with-param>
    </xsl:call-template>

    <!--011->022-->
    <xsl:call-template name="transform-datafield">
      <xsl:with-param name="srcTag">011</xsl:with-param>
      <xsl:with-param name="dstTag">022</xsl:with-param>
      <xsl:with-param name="srcCodes">a</xsl:with-param>
      <xsl:with-param name="dstCodes">a</xsl:with-param>
    </xsl:call-template>

    <!--022->086-->
    <xsl:call-template name="transform-datafield">
      <xsl:with-param name="srcTag">022</xsl:with-param>
      <xsl:with-param name="dstTag">086</xsl:with-param>
      <xsl:with-param name="srcCodes">abz</xsl:with-param>
      <xsl:with-param name="dstCodes">2az</xsl:with-param>
    </xsl:call-template>

    <!-- 101->041 -->
    <xsl:call-template name="transform-datafield">
      <xsl:with-param name="srcTag">101</xsl:with-param>
      <xsl:with-param name="dstTag">041</xsl:with-param>
      <xsl:with-param name="srcCodes">abc</xsl:with-param>
      <xsl:with-param name="dstCodes">ahh</xsl:with-param>
    </xsl:call-template>

    <!-- 102->044 -->
    <xsl:call-template name="transform-datafield">
      <xsl:with-param name="srcTag">102</xsl:with-param>
      <xsl:with-param name="dstTag">044</xsl:with-param>
      <xsl:with-param name="srcCodes">a</xsl:with-param>
      <xsl:with-param name="dstCodes">a</xsl:with-param>
    </xsl:call-template>

    <!-- 200->245 -->
    <xsl:call-template name="transform-datafield">
      <xsl:with-param name="srcTag">200</xsl:with-param>
      <xsl:with-param name="dstTag">245</xsl:with-param>
      <xsl:with-param name="srcCodes">aefb</xsl:with-param>
      <xsl:with-param name="dstCodes">abch</xsl:with-param>
    </xsl:call-template>

    <!-- 210->260 -->
    <xsl:call-template name="transform-datafield">
      <xsl:with-param name="srcTag">210</xsl:with-param>
      <xsl:with-param name="dstTag">260</xsl:with-param>
      <xsl:with-param name="srcCodes">acd</xsl:with-param>
      <xsl:with-param name="dstCodes">abc</xsl:with-param>
    </xsl:call-template>

    <!-- 215->300 -->
    <xsl:call-template name="transform-datafield">
      <xsl:with-param name="srcTag">215</xsl:with-param>
      <xsl:with-param name="dstTag">300</xsl:with-param>
      <xsl:with-param name="srcCodes">acde</xsl:with-param>
      <xsl:with-param name="dstCodes">abce</xsl:with-param>
    </xsl:call-template>

    <!-- 300->500 -->
    <xsl:call-template name="transform-datafield">
      <xsl:with-param name="srcTag">300</xsl:with-param>
      <xsl:with-param name="dstTag">500</xsl:with-param>
      <xsl:with-param name="srcCodes">a</xsl:with-param>
      <xsl:with-param name="dstCodes">a</xsl:with-param>
    </xsl:call-template>

    <!-- 461->774 -->
    <xsl:call-template name="transform-datafield">
      <xsl:with-param name="srcTag">461</xsl:with-param>
      <xsl:with-param name="dstTag">774</xsl:with-param>
      <xsl:with-param name="srcCodes">3tvxy</xsl:with-param>
      <xsl:with-param name="dstCodes">wtgxz</xsl:with-param>
    </xsl:call-template>

    <!-- 464->774 -->
    <xsl:call-template name="transform-datafield">
      <xsl:with-param name="srcTag">464</xsl:with-param>
      <xsl:with-param name="dstTag">774</xsl:with-param>
      <xsl:with-param name="srcCodes">3tvx</xsl:with-param>
      <xsl:with-param name="dstCodes">wtgx</xsl:with-param>
    </xsl:call-template>

    <!-- 610->653 -->
    <xsl:call-template name="transform-datafield">
      <xsl:with-param name="srcTag">610</xsl:with-param>
      <xsl:with-param name="dstTag">653</xsl:with-param>
    </xsl:call-template>

    <!-- 615->650 -->
    <xsl:call-template name="transform-datafield">
      <xsl:with-param name="srcTag">615</xsl:with-param>
      <xsl:with-param name="dstTag">650</xsl:with-param>
      <xsl:with-param name="srcCodes">ax</xsl:with-param>
    </xsl:call-template>

    <!-- 615->072 -->
    <xsl:call-template name="transform-datafield">
      <xsl:with-param name="srcTag">615</xsl:with-param>
      <xsl:with-param name="dstTag">072</xsl:with-param>
      <xsl:with-param name="srcCodes">nm</xsl:with-param>
      <xsl:with-param name="dstCodes">ax</xsl:with-param>
    </xsl:call-template>

    <!-- 700->100 -->
    <xsl:call-template name="transform-personal-name">
      <xsl:with-param name="srcTag">700</xsl:with-param>
      <xsl:with-param name="dstTag">100</xsl:with-param>
    </xsl:call-template>

    <!-- 701->700 -->
    <xsl:call-template name="transform-personal-name">
      <xsl:with-param name="srcTag">701</xsl:with-param>
      <xsl:with-param name="dstTag">700</xsl:with-param>
    </xsl:call-template>

    <!-- 702->700 -->
    <xsl:call-template name="transform-personal-name">
      <xsl:with-param name="srcTag">702</xsl:with-param>
      <xsl:with-param name="dstTag">700</xsl:with-param>
    </xsl:call-template>

	<!-- 856 -> 856 -->
	<xsl:call-template name="selects">
		<xsl:with-param name="i">856</xsl:with-param>
		<xsl:with-param name="count">856</xsl:with-param>
	</xsl:call-template>

    <!--Capture local data -->
    <xsl:call-template name="selects">
      <xsl:with-param name="i">900</xsl:with-param>
      <xsl:with-param name="count">1000</xsl:with-param>
    </xsl:call-template>

    <!--
    <xsl:for-each select="substring(marc:datafield[@tag],1,1)='9'">
        <xsl:call-template name="selects">
          <xsl:with-param name="i">900</xsl:with-param> 
          <xsl:with-param name="count">1000</xsl:with-param>
        </xsl:call-template>
    </xsl:for-each>
    -->
  </xsl:template>

  <xsl:template name="selects">
    <xsl:param name="i" />
    <xsl:param name="count" />

    <xsl:if test="$i &lt;= $count">
      <xsl:call-template name="transform-datafield">
        <xsl:with-param name="srcTag">
          <xsl:value-of select="$i" />
        </xsl:with-param>
        <xsl:with-param name="dstTag">
          <xsl:value-of select="$i" />
        </xsl:with-param>
        <xsl:with-param name="srcCodes">
          <xsl:value-of select="$all-codes"/>
        </xsl:with-param>
        <xsl:with-param name="dstCodes">
          <xsl:value-of select="$all-codes" />
        </xsl:with-param>
      </xsl:call-template>
    </xsl:if>

    <!--begin_: RepeatTheLoopUntilFinished-->
    <xsl:if test="$i &lt; $count">
      <xsl:call-template name="selects">
        <xsl:with-param name="i">
          <xsl:value-of select="$i + 1"/>
        </xsl:with-param>
        <xsl:with-param name="count">
          <xsl:value-of select="$count"/>
        </xsl:with-param>
      </xsl:call-template>
    </xsl:if>
  </xsl:template>


  <xsl:template name="transform-leader">
    <xsl:variable name="leader" select="marc:leader"/>
    <xsl:variable name="leader05" select="translate(substring($leader,06,1), 'o', 'c')"/>
    <xsl:variable name="leader06" select="translate(substring($leader,07,1), 'hmn', 'aor')"/>
    <xsl:variable name="leader07" select="substring($leader,08,1)"/>
    <xsl:variable name="leader08-16" select="'  22     '"/>
    <xsl:variable name="leader17" select="translate(substring($leader,18,1), '23', '87')"/>
    <xsl:variable name="leader18" select="translate(substring($leader,19,1), ' n', 'i ')"/>
    <xsl:variable name="leader19-23" select="' 4500'"/>
    <leader>
      <xsl:value-of select="concat('     ', $leader05, $leader06, $leader07, $leader08-16, $leader17, $leader18, $leader19-23)"/>
    </leader>
  </xsl:template>
  <xsl:template name="copy-control">
    <xsl:param name="tag"/>
    <xsl:for-each select="marc:controlfield[@tag=$tag]">
      <controlfield tag="{$tag}">
        <xsl:value-of select="text()"/>
      </controlfield>
    </xsl:for-each>
  </xsl:template>
  <xsl:template name="transform-100">
    <xsl:variable name="source" select="marc:datafield[@tag='100']/marc:subfield[@code='a']"/>
    <xsl:variable name="dest00-05" select="substring($source,03,6)"/>
    <xsl:variable name="dest06" select="translate(substring($source,09,1), 'abcdefghij', 'cdusrqmtpe')"/>
    <xsl:variable name="dest07-14" select="substring($source,10,8)"/>
    <xsl:variable name="dest15-21" select="'       '"/>
    <xsl:variable name="dest22" select="translate(substring($source,18,1), 'bcadekmu', 'abjcdeg ')"/>
    <xsl:variable name="dest23-27" select="'     '"/>
    <xsl:variable name="dest28" select="translate(substring($source,21,1), 'abcdefghy', 'fsllcizo ')"/>
    <xsl:variable name="dest29-32" select="'    '"/>
    <xsl:variable name="dest33" select="substring($source,35,1)"/>
    <xsl:variable name="dest34-37" select="'    '"/>
    <xsl:variable name="dest38" select="translate(substring($source,22,1), '01', ' o')"/>
    <xsl:variable name="dest39-40" select="'  '"/>
    <controlfield tag="008">
      <xsl:value-of select="concat($dest00-05, $dest06, $dest07-14, $dest15-21, $dest22, $dest23-27, $dest28, $dest29-32, $dest33, $dest34-37, $dest38, $dest39-40)"/>
    </controlfield>
  </xsl:template>
  <xsl:template name="transform-datafield">
    <xsl:param name="srcTag"/>
    <xsl:param name="dstTag" select="@srcTag"/>
    <xsl:param name="srcCodes" select="$all-codes"/>
    <xsl:param name="dstCodes" select="$srcCodes"/>
    <xsl:param name="normalizeCodes" select="''" />

    <xsl:if test="marc:datafield[@tag=$srcTag]/marc:subfield[contains($srcCodes, @code)]">
      <xsl:for-each select="marc:datafield[@tag=$srcTag]">
        <datafield tag="{$dstTag}">
          <xsl:call-template name="copy-indicators"/>
          <xsl:call-template name="transform-subfields">
            <xsl:with-param name="srcCodes" select="$srcCodes"/>
            <xsl:with-param name="dstCodes" select="$dstCodes"/>
            <xsl:with-param name="normalizeCodes" select="$normalizeCodes" />
          </xsl:call-template>
        </datafield>
      </xsl:for-each>
    </xsl:if>
  </xsl:template>
  <xsl:template name="transform-personal-name">
    <xsl:param name="srcTag"/>
    <xsl:param name="dstTag"/>

    <xsl:for-each select="marc:datafield[@tag=$srcTag]">
      <datafield tag="{$dstTag}" ind1="{@ind2}" ind2="">
        <xsl:call-template name="transform-subfields-personal-combine">
          <xsl:with-param name="srcCodes" select="'ab'"/>
          <xsl:with-param name="dstCodes" select="'aa'"/>
        </xsl:call-template>

        <xsl:call-template name="transform-subfields">
          <xsl:with-param name="srcCodes" select="'cdfgp34'"/>
          <xsl:with-param name="dstCodes" select="'cbdqu34'"/>
        </xsl:call-template>
      </datafield>
    </xsl:for-each>
  </xsl:template>
  <xsl:template name="copy-indicators">
    <xsl:attribute name="ind1">
      <xsl:value-of select="translate(@ind1, '#', '')"/>
    </xsl:attribute>
    <xsl:attribute name="ind2">
      <xsl:value-of select="translate(@ind2, '#', '')"/>
    </xsl:attribute>
  </xsl:template>

  <xsl:template name="transform-subfields-personal-combine">
    <xsl:param name="srcCodes" />
    <xsl:param name="dstCodes" />
    <xsl:param name="normalizeCodes" select="''"/>

    <subfield code="a{translate(@code, $srcCodes, $dstCodes)}">
      <xsl:for-each select="marc:subfield[contains($srcCodes, @code)]">
        <xsl:choose>
          <xsl:when test="$normalizeCodes!=''">
            <xsl:value-of select="translate(text(), $normalizeCodes,'')" />
          </xsl:when>
          <xsl:otherwise>
            <xsl:value-of select="text()"/>
          </xsl:otherwise>
        </xsl:choose>
        <!-- la punteggatura è compresa -->
        <!--<xsl:if test="position()!=last()">, </xsl:if>-->
      </xsl:for-each>
    </subfield>
  </xsl:template>

  <xsl:template name="transform-subfields">
    <xsl:param name="srcCodes" select="$all-codes"/>
    <xsl:param name="dstCodes" select="$srcCodes"/>
    <xsl:param name="normalizeCodes" select="''"/>

    <xsl:for-each select="marc:subfield[contains($srcCodes, @code)]">
      <subfield code="{translate(@code, $srcCodes, $dstCodes)}">
        <xsl:choose>
          <xsl:when test="$normalizeCodes!=''">
            <xsl:value-of select="translate(text(), $normalizeCodes,'')" />
          </xsl:when>
          <xsl:otherwise>
            <xsl:value-of select="text()"/>
          </xsl:otherwise>
        </xsl:choose>
      </subfield>
    </xsl:for-each>
  </xsl:template>

  <xsl:variable name="all-codes">abcdefghijklmnopqrstuvwxyz123456789</xsl:variable>
</xsl:stylesheet>
