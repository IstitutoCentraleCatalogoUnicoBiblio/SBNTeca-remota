-- MySQL dump 10.13  Distrib 5.7.21, for Linux (x86_64)
--
-- Host: localhost    Database: icconfig2_vuoto
-- ------------------------------------------------------
-- Server version	5.7.21-0ubuntu0.16.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `comuni`
--

create database icconfig2;

use icconfig2;

DROP TABLE IF EXISTS `comuni`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comuni` (
  `codice` int(11) NOT NULL,
  `provincia` int(11) NOT NULL,
  `comune` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`codice`,`provincia`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comuni`
--

LOCK TABLES `comuni` WRITE;
/*!40000 ALTER TABLE `comuni` DISABLE KEYS */;
/*!40000 ALTER TABLE `comuni` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `destination`
--

DROP TABLE IF EXISTS `destination`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `destination` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `pword` varchar(255) DEFAULT NULL,
  `driver` varchar(255) DEFAULT NULL,
  `nota` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `destination`
--

LOCK TABLES `destination` WRITE;
/*!40000 ALTER TABLE `destination` DISABLE KEYS */;
/*!40000 ALTER TABLE `destination` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `downloads`
--

DROP TABLE IF EXISTS `downloads`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `downloads` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Teca` varchar(200) NOT NULL,
  `Url` varchar(1000) DEFAULT NULL,
  `Full` tinyint(1) NOT NULL DEFAULT '1',
  `Partial` tinyint(1) NOT NULL DEFAULT '1',
  `Page` tinyint(1) NOT NULL DEFAULT '1',
  `Img` tinyint(1) NOT NULL DEFAULT '1',
  `Audio` tinyint(1) NOT NULL DEFAULT '1',
  `Video` tinyint(1) NOT NULL DEFAULT '1',
  `Testo` tinyint(1) NOT NULL DEFAULT '1',
  `MailTo` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `downloads`
--

LOCK TABLES `downloads` WRITE;
/*!40000 ALTER TABLE `downloads` DISABLE KEYS */;
/*!40000 ALTER TABLE `downloads` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fieldfilter`
--

DROP TABLE IF EXISTS `fieldfilter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fieldfilter` (
  `id` int(11) NOT NULL,
  `fieldKey` varchar(255) DEFAULT NULL,
  `fieldName` varchar(255) DEFAULT NULL,
  `attributeName` varchar(255) DEFAULT NULL,
  `templateType` varchar(255) DEFAULT NULL,
  `template` text,
  `multivalued` bit(1) DEFAULT NULL,
  `onlyFullText` bit(1) DEFAULT NULL,
  `templateoutputtype` varchar(255) DEFAULT NULL,
  `alternativePrefixes` varchar(255) DEFAULT NULL,
  `id_fieldfilterset` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK353548F2EBD04A4A` (`id_fieldfilterset`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fieldfilter`
--

LOCK TABLES `fieldfilter` WRITE;
/*!40000 ALTER TABLE `fieldfilter` DISABLE KEYS */;
/*!40000 ALTER TABLE `fieldfilter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fieldfilterset`
--

DROP TABLE IF EXISTS `fieldfilterset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fieldfilterset` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fieldfilterset`
--

LOCK TABLES `fieldfilterset` WRITE;
/*!40000 ALTER TABLE `fieldfilterset` DISABLE KEYS */;
/*!40000 ALTER TABLE `fieldfilterset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `geonames`
--

DROP TABLE IF EXISTS `geonames`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `geonames` (
  `geonameid` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `asciiname` varchar(255) DEFAULT NULL,
  `provincia` varchar(255) DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `field_007` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`geonameid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `geonames`
--

LOCK TABLES `geonames` WRITE;
/*!40000 ALTER TABLE `geonames` DISABLE KEYS */;
/*!40000 ALTER TABLE `geonames` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jobbase`
--

DROP TABLE IF EXISTS `jobbase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jobbase` (
  `jobId` int(11) NOT NULL DEFAULT '0',
  `name` varchar(255) DEFAULT NULL,
  `dcfilter` varchar(255) DEFAULT NULL,
  `descSource` varchar(255) DEFAULT NULL,
  `file` varchar(255) DEFAULT NULL,
  `filenamefilter` varchar(255) DEFAULT NULL,
  `jobClass` varchar(255) DEFAULT NULL,
  `urlBase` varchar(255) DEFAULT NULL,
  `xsl` varchar(1000) DEFAULT NULL,
  `offset` varchar(255) DEFAULT NULL,
  `fromm` varchar(255) DEFAULT NULL,
  `untill` varchar(255) DEFAULT NULL,
  `rows` varchar(255) DEFAULT NULL,
  `lastModified` datetime DEFAULT NULL,
  `descSourceLevel2` varchar(255) DEFAULT NULL,
  `acl` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`jobId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jobbase`
--

LOCK TABLES `jobbase` WRITE;
/*!40000 ALTER TABLE `jobbase` DISABLE KEYS */;
/*!40000 ALTER TABLE `jobbase` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jobstatus`
--

DROP TABLE IF EXISTS `jobstatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jobstatus` (
  `id` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `totale` int(11) DEFAULT NULL,
  `catalogsTotale` int(11) DEFAULT NULL,
  `catalogsCount` int(11) DEFAULT NULL,
  `jobId` int(11) DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `errors` int(11) NOT NULL DEFAULT '0',
  `deleted` int(11) NOT NULL DEFAULT '0',
  `warnings` int(11) NOT NULL DEFAULT '0',
  `started` datetime DEFAULT NULL,
  `downloadTime` bigint(20) NOT NULL DEFAULT '0',
  `downloadItems` int(11) NOT NULL DEFAULT '0',
  `conservionTime` bigint(20) NOT NULL DEFAULT '0',
  `kbData` bigint(20) NOT NULL DEFAULT '0',
  `scartati` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jobstatus`
--

LOCK TABLES `jobstatus` WRITE;
/*!40000 ALTER TABLE `jobstatus` DISABLE KEYS */;
/*!40000 ALTER TABLE `jobstatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jobstatus_messages`
--

DROP TABLE IF EXISTS `jobstatus_messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jobstatus_messages` (
  `id` int(11) NOT NULL,
  `message` mediumtext,
  `type` varchar(255) DEFAULT NULL,
  `id_record` varchar(255) DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  `idx` int(11) NOT NULL,
  `category` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`,`idx`),
  KEY `FKFD78059C4B83D8E4` (`id`),
  CONSTRAINT `FKFD78059C4B83D8E4` FOREIGN KEY (`id`) REFERENCES `jobstatus` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jobstatus_messages`
--

LOCK TABLES `jobstatus_messages` WRITE;
/*!40000 ALTER TABLE `jobstatus_messages` DISABLE KEYS */;
/*!40000 ALTER TABLE `jobstatus_messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `logevent`
--

DROP TABLE IF EXISTS `logevent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `logevent` (
  `id` int(11) NOT NULL DEFAULT '0',
  `message` text,
  `stacktrace` text,
  `type` varchar(255) DEFAULT NULL,
  `jobId` bigint(20) DEFAULT NULL,
  `lastModified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logevent`
--

LOCK TABLES `logevent` WRITE;
/*!40000 ALTER TABLE `logevent` DISABLE KEYS */;
/*!40000 ALTER TABLE `logevent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mappingitem`
--

DROP TABLE IF EXISTS `mappingitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mappingitem` (
  `id` int(11) NOT NULL,
  `source` varchar(255) DEFAULT NULL,
  `destination` varchar(255) DEFAULT NULL,
  `id_mappingset` int(11) DEFAULT NULL,
  `idx` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKB9A87CE1184174B2` (`id_mappingset`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mappingitem`
--

LOCK TABLES `mappingitem` WRITE;
/*!40000 ALTER TABLE `mappingitem` DISABLE KEYS */;
/*!40000 ALTER TABLE `mappingitem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mappingset`
--

DROP TABLE IF EXISTS `mappingset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mappingset` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mappingset`
--

LOCK TABLES `mappingset` WRITE;
/*!40000 ALTER TABLE `mappingset` DISABLE KEYS */;
/*!40000 ALTER TABLE `mappingset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `normitem`
--

DROP TABLE IF EXISTS `normitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `normitem` (
  `id` int(11) NOT NULL,
  `value` varchar(255) DEFAULT NULL,
  `valueNorm` varchar(255) DEFAULT NULL,
  `fieldName` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `modified` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `normitem`
--

LOCK TABLES `normitem` WRITE;
/*!40000 ALTER TABLE `normitem` DISABLE KEYS */;
/*!40000 ALTER TABLE `normitem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notification` (
  `id` int(11) NOT NULL,
  `regexjobs` varchar(255) DEFAULT NULL,
  `enabled` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `regexusername` varchar(255) DEFAULT NULL,
  `withreport` varchar(255) DEFAULT NULL,
  `typejobend` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oaicacheitem`
--

DROP TABLE IF EXISTS `oaicacheitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oaicacheitem` (
  `id` int(11) NOT NULL,
  `identifier` varchar(255) DEFAULT NULL,
  `query` varchar(255) DEFAULT NULL,
  `content` longtext,
  `modified` datetime DEFAULT NULL,
  `datestamp` varchar(255) DEFAULT NULL,
  `metadataPrefix` varchar(255) DEFAULT NULL,
  `setspec` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `plaincontent` text,
  `id_provider` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_cache` (`query`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oaicacheitem`
--

LOCK TABLES `oaicacheitem` WRITE;
/*!40000 ALTER TABLE `oaicacheitem` DISABLE KEYS */;
/*!40000 ALTER TABLE `oaicacheitem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oaicatconfiguration`
--

DROP TABLE IF EXISTS `oaicatconfiguration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oaicatconfiguration` (
  `id` int(11) NOT NULL,
  `servletName` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oaicatconfiguration`
--

LOCK TABLES `oaicatconfiguration` WRITE;
/*!40000 ALTER TABLE `oaicatconfiguration` DISABLE KEYS */;
INSERT INTO `oaicatconfiguration` VALUES (1,'OAIHandlerDati'),(2,'OAIHandler');
/*!40000 ALTER TABLE `oaicatconfiguration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oaiset`
--

DROP TABLE IF EXISTS `oaiset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oaiset` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `spec` varchar(255) DEFAULT NULL,
  `description_it` varchar(255) DEFAULT NULL,
  `description_en` varchar(255) DEFAULT NULL,
  `servletName` varchar(255) DEFAULT NULL,
  `project` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `solrquery` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oaiset`
--

LOCK TABLES `oaiset` WRITE;
/*!40000 ALTER TABLE `oaiset` DISABLE KEYS */;
/*!40000 ALTER TABLE `oaiset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oaiset_constant`
--

DROP TABLE IF EXISTS `oaiset_constant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oaiset_constant` (
  `id` int(11) NOT NULL,
  `fieldName` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `idx` int(11) NOT NULL,
  PRIMARY KEY (`id`,`idx`),
  KEY `FK26C64938E87455AC` (`id`),
  CONSTRAINT `FK26C64938E87455AC` FOREIGN KEY (`id`) REFERENCES `oaiset` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oaiset_constant`
--

LOCK TABLES `oaiset_constant` WRITE;
/*!40000 ALTER TABLE `oaiset_constant` DISABLE KEYS */;
/*!40000 ALTER TABLE `oaiset_constant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oaiset_limiter`
--

DROP TABLE IF EXISTS `oaiset_limiter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oaiset_limiter` (
  `id` int(11) NOT NULL,
  `limiter` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `idx` int(11) NOT NULL,
  PRIMARY KEY (`id`,`idx`),
  KEY `FK8073ED94E87455AC` (`id`),
  CONSTRAINT `FK8073ED94E87455AC` FOREIGN KEY (`id`) REFERENCES `oaiset` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oaiset_limiter`
--

LOCK TABLES `oaiset_limiter` WRITE;
/*!40000 ALTER TABLE `oaiset_limiter` DISABLE KEYS */;
/*!40000 ALTER TABLE `oaiset_limiter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oaiset_profile`
--

DROP TABLE IF EXISTS `oaiset_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oaiset_profile` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `idx` int(11) NOT NULL,
  PRIMARY KEY (`id`,`idx`),
  KEY `FK63832195E87455AC` (`id`),
  CONSTRAINT `FK63832195E87455AC` FOREIGN KEY (`id`) REFERENCES `oaiset` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oaiset_profile`
--

LOCK TABLES `oaiset_profile` WRITE;
/*!40000 ALTER TABLE `oaiset_profile` DISABLE KEYS */;
/*!40000 ALTER TABLE `oaiset_profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oiacatconfigitem`
--

DROP TABLE IF EXISTS `oiacatconfigitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oiacatconfigitem` (
  `id` int(11) NOT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `value` longtext COLLATE utf8_unicode_ci,
  `id_oiacatconfig` int(11) NOT NULL,
  `idx` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKE7CE744226B226` (`id_oiacatconfig`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oiacatconfigitem`
--

LOCK TABLES `oiacatconfigitem` WRITE;
/*!40000 ALTER TABLE `oiacatconfigitem` DISABLE KEYS */;
INSERT INTO `oiacatconfigitem` VALUES (0,'SolrFieldXSLT.schemaLocation','http://www.openarchives.org/OAI/2.0/oai_dc/ http://www.openarchives.org/OAI/2.0/oai_dc.xsd',1,16),(1,'OAIHandler.baseURL','http://www.internetculturale.it/magteca',1,0),(2,'OAIHandler.styleSheet','/metaoaicat/oai2.xsl',1,1),(3,'AbstractCatalog.oaiCatalogClassName','com.gruppometa.metaoaicat.SolrOAISetsCatalog',1,2),(4,'AbstractCatalog.recordFactoryClassName','com.gruppometa.metaoaicat.SolrRecordFactory',1,3),(5,'AbstractCatalog.secondsToLive','3600',1,4),(6,'AbstractCatalog.granularity','YYYY-MM-DDThh:mm:ssZ',1,5),(7,'SolrOAICatalog.solrUrl2','http://localhost:8983/solr',1,6),(8,'Identify.repositoryName','Solr Repository',1,7),(9,'Identify.adminEmail','mailto:info@localhost',1,8),(10,'Identify.earliestDatestamp','2000-01-01T00:00:00Z',1,9),(11,'Identify.deletedRecord','yes',1,10),(12,'Identity.prefix','oai:www.internetculturale.it/magteca',1,11),(13,'Identify.description.1','<description><oai-identifier\n xmlns=\"http://www.openarchives.org/OAI/2.0/oai-identifier\" \n xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" \n xsi:schemaLocation=\"http://www.openarchives.org/OAI/2.0/oai-identifier http://www.openarchives.org/OAI/2.0/oai-identifier.xsd\">\n<scheme>oai</scheme>\n<repositoryIdentifier>oaicat.oclc.org</repositoryIdentifier>\n<delimiter>:</delimiter>\n<sampleIdentifier>oai:oaicat.oclc.org:OCLCNo/ocm00000012</sampleIdentifier>\n</oai-identifier></description>',1,12),(14,'Crosswalks.oai_dc','com.gruppometa.metaoaicat.crosswalk.SolrFieldXSLT',1,13),(15,'SolrOAICatalog.setFields2','dc_type_facet',1,14),(16,'Cross__walks.pico','com.gruppometa.metaoaicat.crosswalk.SolrFieldXSLT',1,15),(18,'SolrFieldXSLT.xsltName','mag2dc.xsl',1,17),(19,'SolrFieldXSLT.xmlField','magxmlExternal',1,18),(20,'SolrOAICatalog.solrUrl','http://127.0.0.1:8984/solr/tecadigitale',1,19),(21,'SolrOAICatalog.setFields','agency',1,20),(22,'SolrFieldXSLT.viewUrl','http://localhost/viewer?id=',1,21),(23,'Crosswalks.mag','com.gruppometa.metaoaicat.crosswalk.Solr2mag',1,22),(24,'Solr2mag.xmlField','magxmlExternal',1,23),(25,'AbstractCatalog.harvestable','true',1,24),(26,'OAIHandler.urlEncodeSetSpec','false',1,24),(27,'SolrOAICatalog.metadataFilter.mag','*:*',1,25),(28,'Crosswalks__.mets','com.gruppometa.metaoaicat.crosswalk.Solr2mets',1,26),(29,'SolrOAICatalog.metadataFilter.mets','mets_t:*',1,27),(30,'Solr2mets__.xmlField','mets_t',1,28),(31,'OAIHandler.version','true',1,29),(32,'OAIHandler.serviceUnavailable','false',1,30),(227,'SolrOAICatalog.allQuery','publicFlag:1',1,33),(228,'SolrOAICatalog.onlyNeededFields','true',1,34),(229,'SolrOAICatalog.idField','oai_identifier',1,35);
/*!40000 ALTER TABLE `oiacatconfigitem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parsemagresult`
--

DROP TABLE IF EXISTS `parsemagresult`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parsemagresult` (
  `id` int(11) NOT NULL,
  `identifier` varchar(255) DEFAULT NULL,
  `agency` varchar(255) DEFAULT NULL,
  `teca` varchar(255) DEFAULT NULL,
  `collection` varchar(255) DEFAULT NULL,
  `audios` int(11) DEFAULT NULL,
  `docs` int(11) DEFAULT NULL,
  `videos` int(11) DEFAULT NULL,
  `ocrs` int(11) DEFAULT NULL,
  `images` int(11) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parsemagresult`
--

LOCK TABLES `parsemagresult` WRITE;
/*!40000 ALTER TABLE `parsemagresult` DISABLE KEYS */;
/*!40000 ALTER TABLE `parsemagresult` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `periodoissue`
--

DROP TABLE IF EXISTS `periodoissue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `periodoissue` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `anno` varchar(255) DEFAULT NULL,
  `annoOrder` varchar(255) DEFAULT NULL,
  `bid` varchar(255) DEFAULT NULL,
  `fascicoloNumber` varchar(255) DEFAULT NULL,
  `fascicoloOrder` varchar(255) DEFAULT NULL,
  `fascicoloString` varchar(255) DEFAULT NULL,
  `idBd` varchar(455) DEFAULT NULL,
  `idTestata` int(11) DEFAULT NULL,
  `importType` varchar(255) DEFAULT NULL,
  `meseCode` varchar(255) DEFAULT NULL,
  `meseString` varchar(255) DEFAULT NULL,
  `titolo` varchar(455) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  `stpieceper` varchar(255) DEFAULT NULL,
  `edizione` varchar(255) DEFAULT NULL,
  `doppio` varchar(255) DEFAULT NULL,
  `meseOrder` int(11) DEFAULT NULL,
  `filename` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `periodoissue_idx_idbd` (`idBd`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `periodoissue`
--

LOCK TABLES `periodoissue` WRITE;
/*!40000 ALTER TABLE `periodoissue` DISABLE KEYS */;
/*!40000 ALTER TABLE `periodoissue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `periodotestata`
--

DROP TABLE IF EXISTS `periodotestata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `periodotestata` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `agency` varchar(455) DEFAULT NULL,
  `bid` varchar(455) DEFAULT NULL,
  `collection` varchar(455) DEFAULT NULL,
  `importType` varchar(255) DEFAULT NULL,
  `inizial` varchar(255) DEFAULT NULL,
  `inventario` varchar(255) DEFAULT NULL,
  `issue` varchar(255) DEFAULT NULL,
  `language` varchar(255) DEFAULT NULL,
  `location` varchar(455) DEFAULT NULL,
  `luogo` varchar(455) DEFAULT NULL,
  `publisher` varchar(255) DEFAULT NULL,
  `regione` varchar(255) DEFAULT NULL,
  `segnatura` varchar(255) DEFAULT NULL,
  `titolo` varchar(455) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `materie` varchar(255) DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  `menuLevels` int(11) DEFAULT '3',
  PRIMARY KEY (`id`),
  KEY `periodotestata_idx_bid` (`bid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `periodotestata`
--

LOCK TABLES `periodotestata` WRITE;
/*!40000 ALTER TABLE `periodotestata` DISABLE KEYS */;
/*!40000 ALTER TABLE `periodotestata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pico_scheduler`
--

DROP TABLE IF EXISTS `pico_scheduler`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pico_scheduler` (
  `id` int(11) NOT NULL,
  `NOME_PROVIDER` varchar(255) DEFAULT NULL,
  `URL_PROVIDER` varchar(255) DEFAULT NULL,
  `SET_PROVIDER` varchar(255) DEFAULT NULL,
  `PROTOCOLLO` varchar(255) DEFAULT NULL,
  `URL_SITE` varchar(255) DEFAULT NULL,
  `METATAGPREFIX` varchar(255) DEFAULT NULL,
  `nota` text,
  `id_mappingset` int(11) DEFAULT NULL,
  `id_fieldfilterset` int(11) DEFAULT NULL,
  `convertorclass` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `QUALITA` varchar(255) DEFAULT NULL,
  `descsource` varchar(255) DEFAULT NULL,
  `encoding` varchar(255) DEFAULT NULL,
  `disabled` varchar(255) DEFAULT NULL,
  `onlycat` varchar(255) DEFAULT NULL,
  `onlyloc` varchar(255) DEFAULT NULL,
  `onlypreview` varchar(255) DEFAULT NULL,
  `geoencoderclass` varchar(255) DEFAULT NULL,
  `categorizatorclass` varchar(255) DEFAULT NULL,
  `mmsclass` varchar(255) DEFAULT NULL,
  `mmsurl` varchar(255) DEFAULT NULL,
  `mmsmethod` varchar(255) DEFAULT NULL,
  `mmscontenttype` varchar(255) DEFAULT NULL,
  `mmstemplate` varchar(255) DEFAULT NULL,
  `CheckMD5` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pico_scheduler`
--

LOCK TABLES `pico_scheduler` WRITE;
/*!40000 ALTER TABLE `pico_scheduler` DISABLE KEYS */;
/*!40000 ALTER TABLE `pico_scheduler` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `provider_category`
--

DROP TABLE IF EXISTS `provider_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `provider_category` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `sets` varchar(255) DEFAULT NULL,
  `idx` int(11) NOT NULL,
  PRIMARY KEY (`id`,`idx`),
  KEY `FK341D078C93DB7E12` (`id`),
  CONSTRAINT `FK341D078C93DB7E12` FOREIGN KEY (`id`) REFERENCES `pico_scheduler` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `provider_category`
--

LOCK TABLES `provider_category` WRITE;
/*!40000 ALTER TABLE `provider_category` DISABLE KEYS */;
/*!40000 ALTER TABLE `provider_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `provider_setmapping`
--

DROP TABLE IF EXISTS `provider_setmapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `provider_setmapping` (
  `id` int(11) NOT NULL,
  `source` varchar(255) DEFAULT NULL,
  `destination` varchar(255) DEFAULT NULL,
  `idx` int(11) NOT NULL,
  PRIMARY KEY (`id`,`idx`),
  KEY `FKD3A3E93A93DB7E12` (`id`),
  CONSTRAINT `FKD3A3E93A93DB7E12` FOREIGN KEY (`id`) REFERENCES `pico_scheduler` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `provider_setmapping`
--

LOCK TABLES `provider_setmapping` WRITE;
/*!40000 ALTER TABLE `provider_setmapping` DISABLE KEYS */;
/*!40000 ALTER TABLE `provider_setmapping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `provider_type`
--

DROP TABLE IF EXISTS `provider_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `provider_type` (
  `id` int(11) NOT NULL,
  `type` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `idx` int(11) NOT NULL,
  PRIMARY KEY (`id`,`idx`),
  KEY `FKFA4850C893DB7E12` (`id`),
  CONSTRAINT `FKFA4850C893DB7E12` FOREIGN KEY (`id`) REFERENCES `pico_scheduler` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `provider_type`
--

LOCK TABLES `provider_type` WRITE;
/*!40000 ALTER TABLE `provider_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `provider_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `providerstatus`
--

DROP TABLE IF EXISTS `providerstatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `providerstatus` (
  `id` int(11) NOT NULL,
  `countCache` int(11) DEFAULT NULL,
  `countSolr` int(11) DEFAULT NULL,
  `id_provider` int(11) DEFAULT NULL,
  `providerSet` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `providerName` varchar(255) DEFAULT NULL,
  `lastCache` datetime DEFAULT NULL,
  `firstCache` datetime DEFAULT NULL,
  `lastSolr` datetime DEFAULT NULL,
  `firstSolr` datetime DEFAULT NULL,
  `cached` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `providerstatus`
--

LOCK TABLES `providerstatus` WRITE;
/*!40000 ALTER TABLE `providerstatus` DISABLE KEYS */;
INSERT INTO `providerstatus` VALUES (1,2,0,1,'','BNCF','BNCF','2014-07-21 15:55:14','2014-07-21 15:55:59',NULL,'2014-07-21 15:55:59','2014-07-21 15:55:59');
/*!40000 ALTER TABLE `providerstatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `province`
--

DROP TABLE IF EXISTS `province`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `province` (
  `codice` int(11) NOT NULL,
  `provincia` varchar(255) DEFAULT NULL,
  `regione_nome` varchar(255) DEFAULT NULL,
  `sigla` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`codice`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `province`
--

LOCK TABLES `province` WRITE;
/*!40000 ALTER TABLE `province` DISABLE KEYS */;
/*!40000 ALTER TABLE `province` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `province_regioni`
--

DROP TABLE IF EXISTS `province_regioni`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `province_regioni` (
  `id` int(11) NOT NULL,
  `provincia` varchar(255) DEFAULT NULL,
  `regione` varchar(255) DEFAULT NULL,
  `sigla` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `province_regioni`
--

LOCK TABLES `province_regioni` WRITE;
/*!40000 ALTER TABLE `province_regioni` DISABLE KEYS */;
/*!40000 ALTER TABLE `province_regioni` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `regioni`
--

DROP TABLE IF EXISTS `regioni`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `regioni` (
  `codice` int(11) NOT NULL,
  `regione` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`codice`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `regioni`
--

LOCK TABLES `regioni` WRITE;
/*!40000 ALTER TABLE `regioni` DISABLE KEYS */;
/*!40000 ALTER TABLE `regioni` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `statisticobject`
--

DROP TABLE IF EXISTS `statisticobject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `statisticobject` (
  `agency` varchar(255) DEFAULT NULL,
  `teca` varchar(255) DEFAULT NULL,
  `collection` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `count` bigint(20) DEFAULT NULL,
  `audios_sum` bigint(20) DEFAULT NULL,
  `docs_sum` bigint(20) DEFAULT NULL,
  `videos_sum` bigint(20) DEFAULT NULL,
  `ocrs_sum` bigint(20) DEFAULT NULL,
  `images_sum` bigint(20) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `statisticobject`
--

LOCK TABLES `statisticobject` WRITE;
/*!40000 ALTER TABLE `statisticobject` DISABLE KEYS */;
/*!40000 ALTER TABLE `statisticobject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tecaresolvers`
--

DROP TABLE IF EXISTS `tecaresolvers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tecaresolvers` (
  `TecaId` varchar(20) COLLATE latin1_general_cs NOT NULL,
  `TecaName` varchar(300) COLLATE latin1_general_cs NOT NULL,
  `Class` varchar(300) COLLATE latin1_general_cs NOT NULL,
  `Url` varchar(300) COLLATE latin1_general_cs DEFAULT NULL,
  `Method` varchar(10) COLLATE latin1_general_cs DEFAULT NULL,
  `ContentType` varchar(100) COLLATE latin1_general_cs DEFAULT NULL,
  `Template` varchar(100) COLLATE latin1_general_cs DEFAULT NULL,
  PRIMARY KEY (`TecaId`),
  UNIQUE KEY `id_UNIQUE` (`TecaId`),
  UNIQUE KEY `tecaname_UNIQUE` (`TecaName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_general_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tecaresolvers`
--

LOCK TABLES `tecaresolvers` WRITE;
/*!40000 ALTER TABLE `tecaresolvers` DISABLE KEYS */;
/*!40000 ALTER TABLE `tecaresolvers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `tecaresolvers_v`
--

DROP TABLE IF EXISTS `tecaresolvers_v`;
/*!50001 DROP VIEW IF EXISTS `tecaresolvers_v`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `tecaresolvers_v` AS SELECT 
 1 AS `TecaId`,
 1 AS `TecaName`,
 1 AS `Class`,
 1 AS `Url`,
 1 AS `Method`,
 1 AS `ContentType`,
 1 AS `Template`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `test`
--

DROP TABLE IF EXISTS `test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test` (
  `name` varchar(80) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test`
--

LOCK TABLES `test` WRITE;
/*!40000 ALTER TABLE `test` DISABLE KEYS */;
/*!40000 ALTER TABLE `test` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thesaurusstatus`
--

DROP TABLE IF EXISTS `thesaurusstatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `thesaurusstatus` (
  `id` int(11) NOT NULL,
  `modified` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `version` varchar(255) DEFAULT NULL,
  `revision` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thesaurusstatus`
--

LOCK TABLES `thesaurusstatus` WRITE;
/*!40000 ALTER TABLE `thesaurusstatus` DISABLE KEYS */;
/*!40000 ALTER TABLE `thesaurusstatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `urilookup`
--

DROP TABLE IF EXISTS `urilookup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `urilookup` (
  `id` int(11) NOT NULL,
  `chiave` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `urilookup`
--

LOCK TABLES `urilookup` WRITE;
/*!40000 ALTER TABLE `urilookup` DISABLE KEYS */;
/*!40000 ALTER TABLE `urilookup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `tecaresolvers_v`
--

/*!50001 DROP VIEW IF EXISTS `tecaresolvers_v`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE VIEW `tecaresolvers_v` AS select concat('teca',`pico_scheduler`.`id`) AS `TecaId`,`pico_scheduler`.`descsource` AS `TecaName`,`pico_scheduler`.`mmsclass` AS `Class`,`pico_scheduler`.`mmsurl` AS `Url`,`pico_scheduler`.`mmsmethod` AS `Method`,`pico_scheduler`.`mmscontenttype` AS `ContentType`,`pico_scheduler`.`mmstemplate` AS `Template` from `pico_scheduler` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-02-19 13:59:01
