package com.gruppometa.sbntecaremota.mets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gruppometa.data.mets.*;
import com.gruppometa.data.mets.audiomd.AudioInfoType;
import com.gruppometa.data.mets.audiomd.AudioType;
import com.gruppometa.data.mets.metsrights.RightsDeclarationMD;
import com.gruppometa.data.mets.mix.*;
import com.gruppometa.data.mets.videomd.VideoInfoType;
import com.gruppometa.data.mets.videomd.VideoType;
import com.gruppometa.data.mods.IdentifierDefinition;
import com.gruppometa.data.mods.Mods;
import com.gruppometa.mets2mag.MetsConvertor;
import com.gruppometa.mets2mag.MetsValidator;
import com.gruppometa.mets2mag.saxon.SaxonHelper;
import com.gruppometa.sbntecaremota.objects.Mag;
import com.gruppometa.sbntecaremota.objects.json.*;
import com.gruppometa.sbntecaremota.objects.validators.ValidationError;
import com.gruppometa.sbntecaremota.objects.validators.ValidationResult;
import com.gruppometa.sbntecaremota.restweb.objects.MagImportService;
import com.gruppometa.sbntecaremota.util.UtilSolr;
import com.gruppometa.sbntecaremota.util.UtilXML;
import com.gruppometa.sbntecaremota.vfsfilesystem.VfsFile;
import com.gruppometa.sbntecaremota.vfsfilesystem.VfsService;
import org.apache.solr.common.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.Transformer;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigInteger;
import java.util.*;

import static com.gruppometa.sbntecaremota.retrieve.FileMagPersistence.config;

@Component
public class MetsCreator {

    @Autowired
    protected VfsService vfsService;

    @Autowired
    protected MagImportService magImportService;

    protected MetsConvertor metsConvertor = new MetsConvertor();


    protected static final Logger logger = LoggerFactory.getLogger(MetsCreator.class);

    public JsonDeleteProject createMets(String id, boolean isDraft) {
        return createMets(id, null, isDraft);
    }
    public JsonDeleteProject createMets(String id, Mets metsForUpdate, boolean isDraft) {
        return createMets(id, metsForUpdate, isDraft, true);
    }

    public JsonDeleteProject createMets(String id, Mets metsForUpdate, boolean isDraft, boolean setDraft) {
        boolean force = true;
        JsonDeleteProject jsonDeleteProject = new JsonDeleteProject();
        VfsFile vfsFile = vfsService.getContainerById(id);
        /**
         * controllo se non è già associato
         */
        if(!force && ("mets".equals(vfsFile.getResourceType()) || "mag".equals(vfsFile.getResourceType()))) {
            jsonDeleteProject.setStatus(-1);
            jsonDeleteProject.setMessage("Il "+vfsFile.getResourceType()+" di '" + id + "' esiste già.");
            return jsonDeleteProject;
        }

        VfsFile parent = vfsService.getVfsFileById(vfsFile.getDirectory().get(0), VfsFile.TYPE_DIRECTORY);
        String documentFormat = "mets";
        vfsFile.setResourceType(documentFormat);
        List<VfsFile> resources = vfsService.getResources(id, 0, Integer.MAX_VALUE);
        try {
            /**
             * creazione mets
             */
            Mets mets = metsForUpdate!=null?metsForUpdate:getMets(id, resources);

            /**
             * esiste il mag?
             */
            Mag mag = null;
            boolean isNewMag = false;
            String idSolr =  "mets:"+id;
            if(!StringUtils.isEmpty(vfsFile.getIdPublic()))
                mag = UtilSolr.selectDocumentById(vfsFile.getIdPublic());
            else
                mag = UtilSolr.selectDocumentByPath(vfsFile.getPath());
            if(
                    mag==null
            //        vfsFile.getPath()==null
            ){
                isNewMag = true;
                mag = new Mag();
                mag.setIdMag(idSolr);
                mag.setDocumentFormat(documentFormat);
                vfsFile.setContentType(MediaType.APPLICATION_XML_VALUE);
            }
            else {
                /**
                 * prendere il mods del esistente se non viene dall'editore...
                 */
                if(metsForUpdate==null && !StringUtils.isEmpty(mag.getMetsOriginal())){
                    Mets metsPrevious = metsConvertor.readMetsFromString(mag.getMetsOriginal());
                    if(metsPrevious.getDmdSecs().size()>0 &&
                            metsPrevious.getDmdSecs().get(0).getMdWrap().getXmlData().getMods()!=null) {
                        mets.getDmdSecs().get(0).getMdWrap().getXmlData().setMods(
                                metsPrevious.getDmdSecs().get(0).getMdWrap().getXmlData().getMods()
                        );
                    }
                }
                //mag = UtilSolr.selectDocumentByPath(vfsFile.getPath());
            }

            mag.setMetsOriginal(metsConvertor.convertMets2Xml(mets));
            // salva mets
            if(mag.getMetsOriginal()!=null) {
                /**
                 * update vfs
                 */
                vfsService.writeToVfs(vfsFile, mag.getMetsOriginal());
                vfsService.updateVfsFile(vfsFile, true);
                /**
                 * salva in Solr
                 */
                if (!isNewMag) {
                    UtilSolr.setFieldSolr(mag.getIdMag(), "metsxmlOriginal", mag.getMetsOriginal(), true);
                    // viene preparato dopo
                    //UtilSolr.setFieldSolr(idSolr, "metsxmlExternal", mag.getMetsOriginal(), true);
                } else {
                    mag.setPath(vfsFile.getPath());
                    //mag.setMetsExternal(mag.getMetsOriginal());
                    //UtilSolr.insertListMag(List.of(mag));
                }
                /**
                 * esecuzione della elaborazione del mets
                 */
                vfsService.makeAsUnused(resources);
                JsonImportReport report = executeJob(parent, idSolr,"mets"); // idSolr punta al container
                if(report.getMagKO()>0){
                    jsonDeleteProject.setStatus(-4);
                    jsonDeleteProject.setMessage("Problemi per la conversione interna.");
                }
                if (metsForUpdate != null) {
                    vfsService.makeAsUsed(resources, metsForUpdate);
                    vfsService.updateOrderAndLabel(metsForUpdate);
                }
                else {
                    vfsService.makeAsUsed(resources);
                }
                if(setDraft)
                    UtilSolr.setFieldSolr(mag.getIdMag(), "draft", isDraft?"1":"0", true);
                return jsonDeleteProject;
            }
            else{
                jsonDeleteProject.setStatus(-3);
                jsonDeleteProject.setMessage("Nessun file METS creato.");
                return jsonDeleteProject;
            }
        } catch (Exception e) {
            logger.error("", e);
            jsonDeleteProject.setStatus(-2);
            jsonDeleteProject.setMessage(e.getMessage());
            return jsonDeleteProject;
        }
    }

    protected JsonImportReport executeJob(VfsFile parent, String idSolr, String documentFormat) throws JsonProcessingException {
        JsonImportConfiguration importMagConfig = getJsonImportConfiguration(parent.getId(), idSolr, documentFormat);
        JsonImportReport report = magImportService.initializeImport(importMagConfig, "imp", true);
        report = magImportService.launchImportJob(report.getJobID(), true);
        return report;
    }

    public static JsonImportConfiguration getJsonImportConfiguration(String parent, String idSolr, String documentFormat) throws JsonProcessingException {
        JsonImportConfiguration importMagConfig = new JsonImportConfiguration();
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String, String>> typeRef
                = new TypeReference<HashMap<String, String>>() {};
        String quality = "HIGH";
        String internal = "EXTERNAL";
        String config = "{" +
                //"\"utente\":\"1\"," +
                //"\"usage_acquisizione\":[3],\"usage_esportazione\":[3]," +
                //"\"sorgente\":\"file\"," +
                //"\"flag_update_mag\":\"1\",\"flag_pubblica\":\"0\"," +
                //"\"flag_sovrascrittura\":\"1\",\"vestizione_mag\":\"0\"," +
                //"\"progetti\":[{\"nome\":\"metsd\",\"tutto\":false,\"mag\":[\"/metsSample.xml\"]}]," +
                //"\"configurazione\":{" +
                "\"Validator.DocumentFormat\":\""+documentFormat+"\"," +
                "\"Mets.stprog\":\"\"," +
                "\"Mets.collection\":\"\"," +
                "\"Mets.agency\":\"\"," +
                "\"Mets.access_rights\":\"1\"," +
                "\"Mets.completeness\":\"0\"," +
                "\"Mets.quality\":\""+quality+"\"," +
                "\"Mets.external\":\""+internal+"\"," +
                "\"BatchImportSize\":\"100\"," +
                "\"MBUsableThreshold\":\"512\"," +
                "\"Validator.SchemaErrorFlag\":\"ERROR\"," +
                "\"Validator.SerialErrorFlag\":\"ERROR\"," +
                "\"Validator.SequenceNumberErrorFlag\":\"ERROR\"," +
                "\"Validator.StruErrorFlag\":\"ERROR\"," +
                "\"Validator.UsageErrorFlag\":\"ERROR\"," +
                "\"Validator.MD5ErrorFlag\":\"ERROR\"," +
                "\"Validator.DimensionErrorFlag\":\"WARNING\"," +
                "\"Validator.MIMEErrorFlag\":\"WARNING\"" +
                //"}"+
                "}";
        importMagConfig.setConfiguration(mapper.readValue(config,  typeRef ));
        JsonImportProject jsonImportProject = new JsonImportProject();
        jsonImportProject.setMags(List.of(idSolr));
        jsonImportProject.setAll(false);
        jsonImportProject.setName(parent);
        importMagConfig.setProjects(List.of(jsonImportProject));
        importMagConfig.setUserID(1);
        importMagConfig.setSource("file");
        importMagConfig.setDressMag(0);
        importMagConfig.setOverwrite(1);
        importMagConfig.setPubblica(0);
        importMagConfig.setImportUpdate(1);
        importMagConfig.setUsageA(List.of("3"));
        importMagConfig.setUsageE(List.of("3"));
        return importMagConfig;
    }

    public static Mets getMets(String id, List<VfsFile> resources) throws DatatypeConfigurationException {
        Mets mets = new Mets();
        mets.setMetsHdr(new MetsType.MetsHdr());
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(
                cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH),
                cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),cal.get(Calendar.SECOND),
                cal.get(Calendar.MILLISECOND),
                DatatypeConstants.FIELD_UNDEFINED
        );
        mets.getMetsHdr().setCREATEDATE(xmlGregorianCalendar);
        mets.getMetsHdr().setLASTMODDATE(xmlGregorianCalendar);
        mets.setSchemaLocation("http://www.loc.gov/METS/");
        mets.setID(id.replaceAll("/","_"));
        String bid = getBidFrom(resources, id);
        MdSecType mdSecType = new MdSecType();
        mdSecType.setID("dmdSec"+mets.getID());
        mets.getDmdSecs().add(mdSecType);
        MdSecType.MdWrap mdWrap = new MdSecType.MdWrap();
        mdWrap.setMDTYPE("MODS");
        mdSecType.setMdWrap(mdWrap);
        mdWrap.setXmlData(new MdSecType.MdWrap.XmlData());
        Mods mods = new Mods();
        IdentifierDefinition identifierDefinition = new IdentifierDefinition();
        identifierDefinition.setValue(bid);
        identifierDefinition.setType("logicalId");
        mods.getAbstractsAndAccessConditionsAndClassifications().add(identifierDefinition);
        mdWrap.getXmlData().setMods(mods);

        HashMap<String, AmdSecType> amdSecTypeHashMap = new HashMap<>();

        MetsType.FileSec fileSec = new MetsType.FileSec();
        mets.setFileSec(fileSec);
        MetsType.FileSec.FileGrp fileGrp = new MetsType.FileSec.FileGrp();
        fileGrp.setUSE("EXTERNAL");
        fileSec.getFileGrps().add(fileGrp);
        StructMapType structMapType = new StructMapType();
        structMapType.setTYPE("PHYSICAL");
        structMapType.setDiv(new DivType());
        structMapType.getDiv().setTYPE("FOLDER");
        structMapType.getDiv().setLabelOrderLabel("FisicaMap");
        mets.getStructMaps().add(structMapType);
        int count = 1;
        for(VfsFile resource: resources) {
            count = makeResource(fileGrp, structMapType, count, resource, amdSecTypeHashMap);
        }
        makePreview(fileGrp, count, structMapType, resources, amdSecTypeHashMap);
        amdSecTypeHashMap.keySet().stream().sorted().forEach(k-> mets.getAmdSecs().add(amdSecTypeHashMap.get(k)));
        /**
         * rights
         */
        AmdSecType amdSecTypeRights = new AmdSecType();
        amdSecTypeRights.setID("RIGHTS1");
        MdSecType mdSecTypeRights = new MdSecType();
        mdSecTypeRights.setID("RIGHTS_MD1");
        amdSecTypeRights.getRightsMDs().add(mdSecTypeRights);
        mdSecTypeRights.setMdWrap(new MdSecType.MdWrap());
        mdSecTypeRights.getMdWrap().setMDTYPE("METSRIGHTS");
        mdSecTypeRights.getMdWrap().setXmlData(new MdSecType.MdWrap.XmlData());
        RightsDeclarationMD rightsDeclarationMD = new RightsDeclarationMD();
        mdSecTypeRights.getMdWrap().getXmlData().setRightsDeclarationMD(rightsDeclarationMD);
        mets.getAmdSecs().add(amdSecTypeRights);
        // fine rights
        return mets;
    }

    private static void makePreview(MetsType.FileSec.FileGrp fileGrp, int count, StructMapType structMapType, List<VfsFile> resources, HashMap<String, AmdSecType> amdSecTypeHashMap) {
        Optional<VfsFile> resourceOpt = resources.stream().filter(v->"true".equals(v.getFrontespizio())).findFirst();
        if(!resourceOpt.isPresent())
            return;
        int width = 150;
        int height = 150;
        VfsFile resource = resourceOpt.get();

        VfsFile resourcePreview = new VfsFile();
        resourcePreview.setHeight(""+height);
        resourcePreview.setWidth(""+width);
        resourcePreview.setFileSize(resource.getFileSize());
        resourcePreview.setContentType(MediaType.IMAGE_JPEG_VALUE);
        resourcePreview.setFilename(resource.getFilename()+"?mode=preview&w="+width+"&h="+height);

        FileType fileType = new FileType();
        if(resource.getFileSize()!=null)
            fileType.setSIZE(Long.parseLong(resource.getFileSize()));
        fileType.setMIMETYPE(resourcePreview.getContentType());
        fileType.setID("FILE"+(count+1));
        fileType.setCHECKSUM(resource.getMd5());
        fileType.setCHECKSUMTYPE("MD5");
        AmdSecType amdSecType = makeAmdSecType(amdSecTypeHashMap, resourcePreview);
        if(amdSecType!=null)
            fileType.setADMID(amdSecType);
        FileGrpType fileGrpMime = getFileGrpOfMimeType(fileGrp, fileType, "PREVIEW");
        fileGrpMime.getFiles().add(fileType);
        FileType.FLocat fLocat = new FileType.FLocat();
        fileType.getFLocats().add(fLocat);
        //fLocat.setHref("digitalObject/"+resource.getId());
        fLocat.setHref(resourcePreview.getFilename());
        fLocat.setLOCTYPE("OTHER");
        DivType.Fptr fptr = new DivType.Fptr();
        DivType divType = new DivType();
        divType.setTYPE("FILE");
        divType.setORDER(BigInteger.valueOf(count+1));
        //
        divType.setLabelOrderLabel(resource.getLabel());
        divType.getFptrs().add(fptr);
        fptr.setFILEID(fileType);
        structMapType.getDiv().getDivs().add(divType);
    }

    private static String getBidFrom(List<VfsFile> resources, String id) {
        if(resources!=null){
            for(VfsFile vfsFile : resources){
                if(vfsFile.getFilename()!=null && vfsFile.getFilename().contains("_"))
                    return vfsFile.getFilename().substring(0, vfsFile.getFilename().indexOf("_")).toUpperCase();
            }
        }
        return "BID_"+id.replaceAll("/","_").replaceAll(" ","_");
    }

    private static AmdSecType makeAmdSecType(HashMap<String, AmdSecType> amdSecTypeHashMap, VfsFile vfsFile) {
        /**
         * immagini
         */
        if("IMAGE".equals(getUseFromMimeType(vfsFile.getContentType()))){
            String key = ":"+vfsFile.getHeight()+":"+vfsFile.getWidth();
            if(amdSecTypeHashMap.containsKey(key))
                return amdSecTypeHashMap.get(key);
            if(StringUtils.isEmpty(vfsFile.getWidth())||
                    StringUtils.isEmpty(vfsFile.getHeight()))
                return null;
            Mix mix = new Mix();
            mix.setBasicImageInformation(new BasicImageInformationType());
            mix.getBasicImageInformation().setBasicImageCharacteristics(new BasicImageInformationType.BasicImageCharacteristics());
            if(!StringUtils.isEmpty(vfsFile.getContentType())) {
                StringType format = new StringType();
                format.setValue(vfsFile.getContentType());
                mix.setBasicDigitalObjectInformation(new BasicDigitalObjectInformationType());
                mix.getBasicDigitalObjectInformation().setFormatDesignation(new BasicDigitalObjectInformationType.FormatDesignation());
                mix.getBasicDigitalObjectInformation().getFormatDesignation().setFormatName(format);
                if(!StringUtils.isEmpty(vfsFile.getFileSize())) {
                    mix.getBasicDigitalObjectInformation().setFileSize(new NonNegativeIntegerType());
                    mix.getBasicDigitalObjectInformation().getFileSize().setValue(new BigInteger(vfsFile.getFileSize()));
                }
            }
            PositiveIntegerType height = new PositiveIntegerType();
            height.setValue(BigInteger.valueOf(Integer.parseInt(vfsFile.getHeight())));
            mix.getBasicImageInformation().getBasicImageCharacteristics().setImageHeight(height);
            PositiveIntegerType width = new PositiveIntegerType();
            width.setValue(BigInteger.valueOf(Integer.parseInt(vfsFile.getWidth())));
            mix.getBasicImageInformation().getBasicImageCharacteristics().setImageWidth(width);
            AmdSecType amdSecType = makeAmdSecType(amdSecTypeHashMap,"NISOIMG");
            amdSecType.getTechMDs().get(0).getMdWrap().getXmlData().setMix(mix);
            amdSecTypeHashMap.put(key, amdSecType);
            return amdSecType;
        }
        /**
         * video
         */
        if("VIDEO".equals(getUseFromMimeType(vfsFile.getContentType()))){
            String key = "video:"+vfsFile.getDuration();
            if(amdSecTypeHashMap.containsKey(key))
                return amdSecTypeHashMap.get(key);
            if(StringUtils.isEmpty(vfsFile.getDuration()))
                return null;
            VideoType videoType = new VideoType();
            videoType.setANALOGDIGITALFLAG("FileDigital");
            videoType.setVideoInfo(new VideoInfoType());
            videoType.getVideoInfo().getDurations().add(vfsFile.getDuration());
            AmdSecType amdSecType = makeAmdSecType(amdSecTypeHashMap,"OTHER");
            amdSecType.getTechMDs().get(0).getMdWrap().getXmlData().setVIDEOMD(videoType);
            amdSecTypeHashMap.put(key, amdSecType);
            return amdSecType;
        }
        /**
         * AUDIO
         */
        if("AUDIO".equals(getUseFromMimeType(vfsFile.getContentType()))){
            String key = "audio:"+vfsFile.getDuration();
            if(amdSecTypeHashMap.containsKey(key))
                return amdSecTypeHashMap.get(key);
            if(StringUtils.isEmpty(vfsFile.getDuration()))
                return null;
            AudioType audioType = new AudioType();
            audioType.setAudioInfo(new AudioInfoType());
            audioType.getAudioInfo().getDurations().add(vfsFile.getDuration());
            audioType.setANALOGDIGITALFLAG("FileDigital");
            AmdSecType amdSecType = makeAmdSecType(amdSecTypeHashMap,"OTHER");
            amdSecType.getTechMDs().get(0).getMdWrap().getXmlData().setAUDIOMD(audioType);
            amdSecTypeHashMap.put(key, amdSecType);
            return amdSecType;
        }
        return null;
    }

    private static AmdSecType makeAmdSecType(HashMap<String, AmdSecType> amdSecTypeHashMap, String mdType){
        AmdSecType amdSecType = new AmdSecType();
        String number = fill(amdSecTypeHashMap.size()+1);
        amdSecType.setID("AMDID"+number);
        MdSecType mdSecTypeAdmin = new MdSecType();
        mdSecTypeAdmin.setID("AMDID_SEC_"+number);
        MdSecType.MdWrap mdWrap1 = new MdSecType.MdWrap();
        mdWrap1.setMDTYPE(mdType);
        mdSecTypeAdmin.setMdWrap(mdWrap1);
        mdSecTypeAdmin.getMdWrap().setXmlData(new MdSecType.MdWrap.XmlData());
        amdSecType.getTechMDs().add(mdSecTypeAdmin);
        return amdSecType;
    }

    private static String fill(int i) {
        String ret = ""+i;
        while (ret.length()<5)
            ret = "0"+ret;
        return ret;
    }

    private static int makeResource(MetsType.FileSec.FileGrp fileGrp, StructMapType structMapType, int count, VfsFile resource,
                                    HashMap<String, AmdSecType> amdSecTypeHashMap) {
        FileType fileType = new FileType();
        if(resource.getFileSize()!=null)
            fileType.setSIZE(Long.parseLong(resource.getFileSize()));
        fileType.setMIMETYPE(resource.getContentType());
        fileType.setCHECKSUM(resource.getMd5());
        fileType.setID("FILE"+(count));
        fileType.setCHECKSUMTYPE("MD5");
        AmdSecType amdSecType = makeAmdSecType(amdSecTypeHashMap, resource);
        if(amdSecType!=null)
            fileType.setADMID(amdSecType);
        FileGrpType fileGrpMime = getFileGrpOfMimeType(fileGrp, fileType, "HIGH");
        fileGrpMime.getFiles().add(fileType);
        FileType.FLocat fLocat = new FileType.FLocat();
        fileType.getFLocats().add(fLocat);
        //fLocat.setHref("digitalObject/"+resource.getId());
        fLocat.setHref(resource.getFilename());
        fLocat.setLOCTYPE("OTHER");
        DivType.Fptr fptr = new DivType.Fptr();
        DivType divType = new DivType();
        divType.setTYPE("FILE");
        divType.setORDER(BigInteger.valueOf(count));
        divType.setLabelOrderLabel(resource.getLabel());
        divType.getFptrs().add(fptr);
        fptr.setFILEID(fileType);
        structMapType.getDiv().getDivs().add(divType);
        return ++count;
    }

    private static FileGrpType getFileGrpOfMimeType(FileGrpType fileGrp, FileType fileType, String useHighOrPreview) {
        String use = getUseFromMimeType(fileType.getMIMETYPE());
        Optional<FileGrpType> candidate = fileGrp.getFileGrps().stream().filter(g->g.getUSE().equals(use)).findFirst();
        if(candidate.isPresent()) {
            if(useHighOrPreview.equals("PREVIEW") && candidate.get().getFileGrps().size()>1)
                return candidate.get().getFileGrps().get(1);
            return candidate.get().getFileGrps().get(0);
        }
        MetsType.FileSec.FileGrp fileGrpImages = new MetsType.FileSec.FileGrp();
        fileGrpImages.setUSE(use);
        MetsType.FileSec.FileGrp fileGrpQuality = new MetsType.FileSec.FileGrp();
        fileGrpQuality.setUSE("HIGH");
        fileGrp.getFileGrps().add(fileGrpImages);
        fileGrpImages.getFileGrps().add(fileGrpQuality);
        if(use.equals("IMAGE")) {
            MetsType.FileSec.FileGrp fileGrpQuality2 = new MetsType.FileSec.FileGrp();
            fileGrpQuality2.setUSE("PREVIEW");
            fileGrpImages.getFileGrps().add(fileGrpQuality2);
            if(useHighOrPreview.equals("PREVIEW"))
                return fileGrpQuality2;
        }
        return fileGrpQuality;
    }

    private static String getUseFromMimeType(String mimetype) {
        if(mimetype==null)
            return "IMAGE";
        if(mimetype.toLowerCase().contains("video"))
            return "VIDEO";
        if(mimetype.toLowerCase().contains("audio"))
            return "AUDIO";
        if(mimetype.toLowerCase().contains("image"))
            return "IMAGE";
        if(mimetype.toLowerCase().contains("text")||mimetype.toLowerCase().contains("pdf")||mimetype.toLowerCase().contains("word"))
            return "TEXT";
        logger.info("Unkown mimetype "+mimetype);
        return "TEXT";
    }

}

