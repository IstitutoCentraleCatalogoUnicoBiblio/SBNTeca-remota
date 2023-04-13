package com.gruppometa.sbntecaremota.iiif;

import com.gruppometa.sbntecaremota.model.iiif.v3.*;
import com.gruppometa.sbntecaremota.util.ContentStatic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class MagHandler3 extends DefaultHandler {

    protected static Logger logger = LoggerFactory.getLogger(MagHandler3.class);

    private static boolean isLevelZero = false;
    protected boolean isIiif3activeForImages = false; // usiamo IIIF v2 di cantaloupe
    protected String id;
    protected String baseIiif;
    protected String base;
    protected Manifest manifest;
    protected Canvas canvas;
    protected String currentMimeType;
    protected StringBuffer stringBuffer = new StringBuffer();
    protected String pageId;
    protected AnnotationBody annotationBody;
    protected int height = 0;
    protected int width = 0;
    protected int previewHeight = 150;
    protected int previewWidth = 150;
    protected double duration = 0;
    protected String nomenclature;
    protected String nomenclatureStru;
    protected String teca = "ad";
    protected String tecaUrl = "";
    protected String jmmsBase;
    protected String audioUrl = null;
    protected HashMap<String, String > prefixMapping = new HashMap<>();
    protected HashMap<String, GroupInfo> groupInfos = new HashMap<String, GroupInfo>();
    protected String lastGroupId = null;
    protected String currentId = null;
    protected String currentType = null;
    protected String audiogroupID = null;
    protected CurrentMedia currentMedia = new CurrentMedia();
    protected String imggroupID = null;
    protected Range range = null;
    protected Range element = null;
    private int elementCounter = 0;
    private String struResourceType = null;
    private int struLevel = 0;
    private boolean inElement = false;
    private boolean inOcr = false;
    protected boolean shiftAltImg;

    private String usage;
    private String targetUsage;
    private boolean rendingOnCanvas = false; // appendi non sotto "Related" del manifesto, ma sotto il canvas
    protected boolean docWithCanvas = false; // crea canvas per doc (sembra non supportato da Mirador)
    private Rendering lastRendering;
    protected List<Range> rangeParent = new ArrayList<>();
    protected PathMapper pathMapper = new PathMapper();

    boolean doubleAudioForUsage = true; // TODO bugfix per Usage 2 e 3.....

    public MagHandler3(String id, Manifest manifest, String baseIiif, String base, String jmmsBase, String teca, String targetUsage){
        this.id = id;
        this.base = base;
        this.baseIiif = baseIiif;
        this.manifest = manifest;
        this.jmmsBase = jmmsBase;
        this.teca = teca;
        this.targetUsage = targetUsage;
        this.shiftAltImg = false;
    }

    @Override
    public void startPrefixMapping(String prefix, String uri) throws SAXException {
        //logger.info("Put "+uri+"=>"+prefix);
        if(!prefix.equals("targetNamespace"))
            prefixMapping.put(uri, prefix);
    }

    protected String getPrefix(String uri){
        String prefix =  prefixMapping.get(uri);
        if(prefix==null||prefix.equals(""))
            return "";
        else
            return prefixMapping.get(uri)+":";
    }

    protected boolean isMag(String qName, String test){
        boolean ret = qName.equals(getPrefix("http://www.iccu.sbn.it/metaAG1.pdf")+test)
                || qName.equals("mag:"+test)
                || qName.equals(test);
        if(ret)
            logger.trace(qName+" = "+test);
        return ret;
    }

    protected boolean isNiso(String qName, String test){
        boolean ret = qName.equals(getPrefix("http://www.niso.org/pdfs/DataDict.pdf")+test)
                || qName.equals("niso:"+test);
        if(ret)
            logger.trace(qName+" = "+test);
        return ret;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        /**
         * si salta la sezione ocr per ora....
         */
        if(inOcr || shiftAltImg)
            return;
        currentId = attributes.getValue("ID");
        if(isMag(qName,"proxies")) {
            audiogroupID = attributes.getValue("audiogroupID");
        }
        else if(isMag(qName,"audio_group")) {
            lastGroupId = currentId;
            groupInfos.put(currentId, new GroupInfo());
        }
        else if(isMag(qName,"ocr")) {
            inOcr = true;
        }
        else if(isMag(qName,"dis_item")) {
            currentType = "dis_item";
        }
        else if(isMag(qName,"img")) {
            currentType = "img";
            imggroupID = attributes.getValue("imggroupID");
        }
        else if(isMag(qName,"altimg")) {
            if(usage.equals(targetUsage)) {
                makeEndImg(true);
                shiftAltImg = true;
            }
        }
        else if(isMag(qName,"audio")) {
            currentType = "audio";
        }
        else if(isMag(qName,"doc")) {
            currentType = "doc";
        }
        else if(isMag(qName,"stru")) {
            elementCounter = 0;
            currentType = "stru";
            struLevel++;
        }
        else if(isMag(qName,"element")) {
            inElement = true;
            Range lastRange = range;
            pageId = "element/"+(++elementCounter);
            if(lastRange!=null) {
                element = new Range(lastRange.getId()+"/"+pageId);
                lastRange.getItems().add(element);
            }
            else{
                logger.warn("Not implemented");
            }
        }
        else if(isMag(qName,"video")) {
            currentType = "video";
        }
        else if(isMag(qName,"bib")) {
            if (attributes != null) {
                for (int i = 0; i < attributes.getLength(); i++) {
                    if (isMag(attributes.getQName(i),"level")) {
                        String level = attributes.getValue(i);
                        if("a".equals(level))
                            level = "spoglio";
                        else if("m".equals(level))
                            level = "monografia";
                        else if("s".equals(level))
                            level = "seriale";
                        else if ("c".equals(level))
                            level = "accolta prodotta dall'istituzione";
                        addMetadata(manifest, MetadataService.LEVELL_BIBLIOGRAFICO, level);
                    }
                }
            }
        }
        else if(isMag(qName, "start") || isMag(qName, "stop")){
            String sequenceNumber = attributes.getValue("sequence_number");
            String canvasId = base + tecaUrl + "/" + id + "/canvas/" +
                    (struResourceType!=null?struResourceType:"img")
                    +"/"+sequenceNumber;
            // doc non in range
            if(struResourceType==null || !"doc".equals(struResourceType) || docWithCanvas) {
                if(element!=null) {
                    if (element.getItems().size() == 0
                            || !((JsonLdIdType) element.getItems().get(element.getItems().size() - 1)).getId().equals(canvasId))
                    element.getItems().add(new Canvas(canvasId));
                }
                else if (range != null && (range.getItems().size() == 0
                        || !((JsonLdIdType) range.getItems().get(range.getItems().size() - 1)).getId().equals(canvasId))) {
                    range.getItems().add(new Canvas(canvasId));
                }
            }
            else {
                /**
                 * element = range con doc non supportato da IIIF
                 */
                if(element!=null){
                    if(isMag(qName, "start"))
                        range.getItems().remove(range.getItems().size()-1);
                }
            }
        }
        else if(isMag(qName,"file")){
            String tdi = null;
            if(attributes!=null){
                for(int i = 0; i < attributes.getLength(); i++) {
                    if(attributes.getQName(i).equals(getPrefix("http://www.w3.org/TR/xlink")+"href")
                        || attributes.getQName(i).equals("href")
                    ){
                        tdi = attributes.getValue(i).replace("digitalObject/", "");
                        audioUrl = tdi;//attributes.getValue(i);
                        audioUrl = jmmsBase + pathMapper.getResourceStreamUrl(teca, audioUrl, tecaUrl, tdi);
//                        if(audioUrl.startsWith("cache/normal/"))
//                            audioUrl = cacheMan+audioUrl.substring("cache/".length());
                    }
                }
            }
            if(currentType!=null && currentType.equals("dis_item")) {
                Thumbnail thumbnail = new Thumbnail(
                        //baseIiif + pathMapper.makeIiifUrlPart(tdi, "preview", teca, tdi, tecaUrl) + "/full/full/0/default" + makeExtension(tdi)
                        jmmsBase+"digitalObject/"+tdi //+"?&mode=preview&w="+previewWidth+"&h="+previewHeight
                        //baseIiif+ pathMapper.makeIiifUrlPart(tdi,"preview", teca, tdi, tecaUrl)
                );
//                    thumbnail.setHeight(Configs.getInstance().getInt("cache.img.preview.size.y", 150));
//                    thumbnail.setWidth(Configs.getInstance().getInt("cache.img.preview.size.x", 150));
                thumbnail.setHeight( previewHeight );
                thumbnail.setWidth( previewWidth );
                thumbnail.setFormat("image/jpeg");
                manifest.setThumbnail(thumbnail);
            }
            else if(currentType==null || !currentType.equals("doc")) {
                AnnotationPage annotationPage = new AnnotationPage(base + tecaUrl + "/" + id + "/page/" + pageId);
                Annotation annotation = new Annotation(base + tecaUrl + "/" + id + "/annotation/" + pageId);
                annotationPage.getItems().add(annotation);
                boolean skipIt = false;
                if(("audio".equalsIgnoreCase(currentType) || "video".equalsIgnoreCase(currentType)) && doubleAudioForUsage){
                    if(!targetUsage.equals(usage)){
                        logger.debug("Skip target usage: " + targetUsage + " usage:" + usage);
                        skipIt = true;
                    }
                    else {
                        String type = "audio".equalsIgnoreCase(currentType)?
                                "Sound":"Video";
                        logger.debug("Get "+currentType+" usage " + usage + " for " + targetUsage);
                        currentMedia.setUsage(usage);
                        currentMedia.setAnnotationPage(annotationPage);
                        annotationBody = new AnnotationBody(audioUrl,type);
                        annotationBody.setFormat(currentMimeType);
                        annotationBody.setType(type);
                        annotationBody.setId(audioUrl);
                        annotationPage.setId(audioUrl);
                        annotationBody.setService(null);
                        annotationBody.setDuration(duration);
                        annotation.setBody(annotationBody);
                        annotation.setTarget(base + tecaUrl + "/" + id + "/page/" + pageId);
                    }
                }
                else {
                    annotationBody = new AnnotationBody(baseIiif + pathMapper.makeIiifUrlPart(tdi, "normal", teca, tdi, tecaUrl)
                            + "/full/full/0/default" + makeExtension(tdi), "Image");
                    annotationBody.setFormat("image/jpeg");
                    String serviceType = "ImageService2";
                    String serviceProfile = "http://iiif.io/api/image/2/level2.json";
                    if (isIiif3activeForImages) {
                        serviceType = "ImageService3";
                        serviceProfile = "level2";
                    }
                    Service service = new Service(baseIiif + pathMapper.makeIiifUrlPart(tdi, "normal", teca, tdi, tecaUrl),
                            serviceType);
                    service.setProfile(serviceProfile);
                    annotationBody.setService(service);
                    currentMedia.setUsage(usage);
                    annotation.setBody(annotationBody);
                    annotation.setTarget(base + tecaUrl + "/" + id + "/page/" + pageId);
                }
                if (canvas != null && !skipIt) {
                    Thumbnail thumbnail = new Thumbnail(
                            //baseIiif + pathMapper.makeIiifUrlPart(tdi, "preview", teca, tdi, tecaUrl) + "/full/full/0/default" + makeExtension(tdi)
                            jmmsBase+"digitalObject/"+tdi+"?&mode=preview&w="+previewWidth+"&h="+previewHeight
                            //baseIiif+ pathMapper.makeIiifUrlPart(tdi,"preview", teca, tdi, tecaUrl)
                    );
//                    thumbnail.setHeight(Configs.getInstance().getInt("cache.img.preview.size.y", 150));
//                    thumbnail.setWidth(Configs.getInstance().getInt("cache.img.preview.size.x", 150));
                    thumbnail.setHeight( previewHeight );
                    thumbnail.setWidth( previewWidth );
                    thumbnail.setFormat("image/jpeg");
                    currentMedia.setAnnotationPage(annotationPage);
                    currentMedia.setThumbnail(thumbnail);
                    //makeCurrentMedia(currentMedia, canvas);
                } else {
                    if(!skipIt)
                        logger.warn("No canvas for " + tdi);
                }
            }
            else if(docWithCanvas){
                if (canvas != null) {
                    if(lastRendering!=null) {
                        if (canvas.getRendering() == null)
                            canvas.setRendering(new ArrayList<>());
                        canvas.getRendering().add(lastRendering);
                        lastRendering = null;
                    }
                }
                else {
                    logger.warn("No canvas for " + tdi);
                }
            }
        }

    }

    private void makeCurrentMedia(CurrentMedia currentMedia, Canvas canvas) {
        canvas.getItems().add(currentMedia.getAnnotationPage());
        canvas.setThumbnail(new ArrayList<>());
        canvas.getThumbnail().add(currentMedia.getThumbnail());
    }

    private String makeExtension(String tdi) {
        if(id==null)
            return null;
//        if(tdi.toLowerCase().endsWith(".tiff"))
//            return ".tiff";
        return ".jpg";
    }


    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        stringBuffer.append(new String(ch, start, length));
    }

    protected static void addMetadata(Manifest manifest, String name, String value){
        manifest.getMetadata().add(new MetaData(
                new LanguageMap("none",name),
                new LanguageMap("none",value)));
    }

    protected void add2Metadata(Manifest manifest, String name, String value){
        Optional<MetaData> metaData = manifest.getMetadata().stream().filter(c->c.getLabel().get("none")[0].equals(name)).findFirst();
        if(metaData.isPresent()) {
            String[] value2 = metaData.get().getValue().get("none");
            value2[0] = value2[0]+  " ; "+ value;
            //metaData.get().getValue().put(new LanguageMap("none", value2[0]));
        }
        else
            addMetadata(manifest, name, value);
    }

    public static void overrideMetadata(Manifest manifest, String name, String value){
        Optional<MetaData> metaData = manifest.getMetadata().stream().filter(c->c.getLabel().get("none")[0].equals(name)).findFirst();
        if(metaData.isPresent()) {
            metaData.get().getValue().clear();
            metaData.get().getValue().put(new LanguageMap("none", value));
        }
        else
            addMetadata(manifest, name, value);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(isMag(qName,"ocr")) {
            inOcr = false;
        }
        else if(inOcr){
            return;
        }
        else if(!isMag(qName,"img") && shiftAltImg) {
            return;
        }
        else if(isMag(qName,"usage")) {
            usage = stringBuffer.toString().trim();
            logger.debug("get usage"+usage);
        }
        else if(qName.equals(getPrefix("http://purl.org/dc/elements/1.1/")+"title")){
            manifest.getLabel().put("none",new String[]{clear27(stringBuffer.toString().trim())});
            addMetadata(manifest, MetadataService.TITOLO, clear27(stringBuffer.toString().trim()));
        }
//        if(qName.equals(getPrefix("http://purl.org/dc/elements/1.1/")+"description")){
//            addMetadata(manifest,"description",stringBuffer.toString().trim());
//        }
        /**
         * TODO: "Tipo documento" e "Livello bibliografico" dal servizio
         * dal mag: "Localizzazione" e "Biblioteca"
         */
        else if(isMag(qName,"library")) {
            addMetadata(manifest,MetadataService.LOCALIZZAZIONE, stringBuffer.toString().trim());
        }
        else if(isMag(qName,"inventory_number")) {
            add2Metadata(manifest,MetadataService.LOCALIZZAZIONE, stringBuffer.toString().trim());
        }
        else if(isMag(qName,"shelfmark")) {
            add2Metadata(manifest,MetadataService.LOCALIZZAZIONE, stringBuffer.toString().trim());
        }
        else if(isMag(qName,"collection")) {
            addMetadata(manifest,MetadataService.COLLEZIONE, stringBuffer.toString().trim());
        }
        else if(isMag(qName,"access_rights")) {
            String value = stringBuffer.toString().trim();
            addMetadata(manifest,MetadataService.DIRITTI, "1".equals(value)?"pubblico":"privato");
        }
        else if(isMag(qName,"duration")) {
            duration = getSeconds(stringBuffer.toString().trim());
        }
        else if(qName.equals(getPrefix("http://purl.org/dc/elements/1.1/")+"publisher")) {
            addMetadata(manifest,MetadataService.EDITORE, stringBuffer.toString().trim());
        }
//        if(qName.equals(getPrefix("http://purl.org/dc/elements/1.1/")+"date")) {
//            addMetadata(manifest,"date", stringBuffer.toString().trim());
//        }
        else if(qName.equals(getPrefix("http://purl.org/dc/elements/1.1/")+"format")) {
            addMetadata(manifest,MetadataService.FORMATO, stringBuffer.toString().trim());
        }
        else if(qName.equals(getPrefix("http://purl.org/dc/elements/1.1/")+"type")) {
            addMetadata(manifest,MetadataService.TIPO_DOCUMENTO, stringBuffer.toString().trim());
        }
//        if(qName.equals(getPrefix("http://purl.org/dc/elements/1.1/")+"language")) {
//            addMetadata(manifest,"language", stringBuffer.toString().trim());
//        }
        else if(qName.equals(getPrefix("http://purl.org/dc/elements/1.1/")+"creator")) {
            addMetadata(manifest,MetadataService.AUTORI, stringBuffer.toString().trim());
        }
        else if(qName.equals(getPrefix("http://www.niso.org/pdfs/DataDict.pdf")+"imagelength")){
            height = Integer.parseInt(stringBuffer.toString().trim());
        }
        else if(qName.equals(getPrefix("http://www.niso.org/pdfs/DataDict.pdf")+"imagewidth")){
            width = Integer.parseInt(stringBuffer.toString().trim());
        }
        else if(isMag(qName,"resource")) {
            struResourceType =  stringBuffer.toString().trim();
        }
        else if(isMag(qName,"element")){ // di stru
            element = null;
            struResourceType = null;
            inElement = false;
        }
        else if(isMag(qName,"nomenclature")){
            if(!inElement) {
                nomenclature = stringBuffer.toString().trim();
                if (currentType == null || !currentType.equals("doc") || docWithCanvas) {
                    if (canvas != null && !"stru".equals(currentType))
                        canvas.setLabel(new LanguageMap("none", nomenclature));
                }
                if("stru".equals(currentType)) {
                    nomenclatureStru = stringBuffer.toString().trim();
                    if(struLevel == 1 && range!=null)
                        range.setLabel(new LanguageMap("none", nomenclatureStru));
                }
            }
            else{
                    element.setLabel(new LanguageMap("none", stringBuffer.toString().trim()));
            }
        }
        else if(isMag(qName,"sequence_number")){
            pageId = currentType+"/"+stringBuffer.toString().trim();
            if(currentType!=null && currentType.equalsIgnoreCase("stru")){
                Range lastRange = range;
                /**
                 * primo livella
                 */
                if(struLevel==1) {
                    range = new Range(base + tecaUrl + "/" + id + "/range/" + pageId);
                    manifest.getStructures().add(range);
                    rangeParent.add(range);
                }
                else {
                    /**
                     * fratelli
                     */
                    if(struLevel==rangeParent.size() && struLevel>0){
                        Range parent = rangeParent.get(rangeParent.size()-1);
                        range = new Range(parent.getId()+"/"+pageId);
                        parent.getItems().add(range);
                    }
                    /**
                     * figli
                     */
                    else {
                        if(lastRange!=null) {
                            range = new Range(lastRange.getId()+"/"+pageId);
                            lastRange.getItems().add(range);
                            rangeParent.add(lastRange);
                        }
                        else{
                            logger.warn("Not implemented");
                        }
                    }
                }
            }
            else if(currentType==null || !currentType.equals("doc") || docWithCanvas){
                canvas = new Canvas(base + tecaUrl + "/" + id + "/canvas/" + pageId);
                manifest.getItems().add(canvas);
            }
//            if(nomenclature!=null)
//                canvas.setLabel(new PropertyValue(nomenclature));
        }
        else if(isMag(qName,"mime")) {
            currentMimeType = checkMimeType(stringBuffer.toString().trim());
            if(annotationBody!=null) {
                annotationBody.setFormat(currentMimeType);
            }
            else if(lastGroupId !=null && groupInfos!=null && currentMimeType!=null
             && groupInfos.get(lastGroupId)!=null){
                groupInfos.get(lastGroupId).setFormat(currentMimeType);
            }
        }
        else if(isNiso(qName,"mime")) {
            currentMimeType = checkMimeType(stringBuffer.toString().trim());
        }
        else if(isMag(qName,"audio")){
            if(!targetUsage.equals(currentMedia.getUsage())){
                logger.debug("Skip target usage: " + targetUsage + " usage:" + usage);
            }
            else {
                logger.debug("Target usage: " + targetUsage + " usage:" + usage);
//                annotationBody.setType("Sound");
//                annotationBody.setId(audioUrl);
//                canvas.setDuration(duration);
//                canvas.getThumbnail().clear();
//                canvas.getThumbnail().add(getThumbnail("audio"));
//                annotationBody.setDuration(duration);
//                annotationBody.setService(null);
                if (audiogroupID != null && groupInfos.get(audiogroupID) != null)
                    annotationBody.setFormat(groupInfos.get(audiogroupID).getFormat());
                currentMedia.setThumbnail(getThumbnail("audio"));
                makeCurrentMedia(currentMedia, canvas);
            }
        }
        else if(isMag(qName,"video")){
            if(!targetUsage.equals(currentMedia.getUsage())){
                logger.debug("Skip target usage: " + targetUsage + " usage:" + usage);
            }
            else {
                logger.debug("Target usage: " + targetUsage + " usage:" + usage);
//                annotationBody.setType("Video");
//                annotationBody.setId(audioUrl);
//                canvas.setDuration(duration);
//                canvas.getThumbnail().clear();
//                canvas.getThumbnail().add(getThumbnail("video"));
//                annotationBody.setDuration(duration);
//                annotationBody.setService(null);
                currentMedia.setThumbnail(getThumbnail("video"));
                makeCurrentMedia(currentMedia, canvas);
            }
        }
        else if(isMag(qName,"doc")){
            if(manifest.getRendering()==null)
                manifest.setRendering(new ArrayList<>());
            Rendering rendering = new Rendering();
            rendering.setType("Text");
            rendering.setId(audioUrl);
            rendering.setFormat(currentMimeType);
            rendering.setLabel(new LanguageMap("none", nomenclature));
            if(!rendingOnCanvas)
                manifest.getRendering().add(rendering);
            else
                lastRendering = rendering;
        }
        else if(isMag(qName, "stru")){
            if (range != null && nomenclatureStru!=null)
              range.setLabel(new LanguageMap("none", nomenclatureStru));
            nomenclatureStru = null;
            range = null;
            if(rangeParent.size()== struLevel)
                rangeParent.remove(rangeParent.size()-1);
            if(rangeParent.size()>0)
                range = rangeParent.get(rangeParent.size()-1);
            struLevel--;
        }
        else if(isMag(qName, "img")){
            if(shiftAltImg)
                shiftAltImg = false;
            else
                makeEndImg(false);
        }
        stringBuffer = new StringBuffer();
    }

    private void makeEndImg(boolean isAltImg) {
        if(!targetUsage.equals(usage)){
            logger.debug("Skip target usage: " + targetUsage + " usage:" + usage);
        }
        else {
            logger.debug("Target usage: " + targetUsage + " usage:" + usage);
            makeCurrentMedia(currentMedia, canvas);
            Integer widthTemp = width;
            Integer heightTemp = height;
            if (widthTemp == 0 || heightTemp == 0) {
                /**
                 * manca il calcolo delle dimensioni
                 * TODO
                 */
                logger.warn("Image without size: id:" + id + ", page:" + pageId);
                widthTemp = ContentStatic.getInt("cache.img.preview.size.x", 150);
                heightTemp = ContentStatic.getInt("cache.img.preview.size.y", 150);
            }
            /**
             * non standard però serve per universal-viewer
             */
            canvas.setWidth(widthTemp);
            canvas.setHeight(heightTemp);
            /**
             * standard
             */
            annotationBody.setWidth(widthTemp);
            annotationBody.setHeight(heightTemp);
//            ImageService imageService = ((ImageService)img.getServices().get(0));
//            setDimension(imageService, widthTemp, heightTemp);
//            //((Annotation)canvas.getImages().get(0)).setIdentifier(new URI(canvas.getIdentifier().toString().replace("/canvas","/annotation")));
//            sequence.addCanvas(canvas);
            canvas = null;
            nomenclature = null;
        }
    }

    private Thumbnail getThumbnail(String type) {
        Thumbnail thumbnail = new Thumbnail(
                jmmsBase+"images/"+type+"_icon.png"
        );
//        thumbnail.setHeight(Configs.getInstance().getInt("cache.img.preview.size.y", 150));
//        thumbnail.setWidth(Configs.getInstance().getInt("cache.img.preview.size.x", 150));
        thumbnail.setHeight( 150 );
        thumbnail.setWidth( 150 );
        thumbnail.setFormat("image/png");
        return thumbnail;
    }

    private String clear27(String string) {
        if(string==null)
            return null;
        return string.replace("\uFFFD","");
    }

    private String checkMimeType(String mime) {
        /**
         * correzione: video/mpeg non sembra veramente mpeg ma mp4
         */
        if(mime!=null && "video/mpeg".equals(mime))
            return "video/mp4";
        else
            return mime;
    }

    public static double getSeconds(String trim) {
        if(trim==null)
            return 0;
        String[] split = trim.split(":");
        double ret = 0;
        int quote = 1;
        for(int i = split.length-1; i>=0; i--){
            ret = ret + Double.parseDouble(split[i])*quote;
            quote = quote * 60;
        }
        return ret;
    }

//    public static void setDimension(ImageService imageService, int width,int height) {
//        if(isLevelZero) {
//            imageService.setSizes(new ArrayList<>());
//            imageService.setTiles(new ArrayList<>());
//            imageService.getSizes().add(new Size(width, height));
//            imageService.getTiles().add(new TileInfo(width));
//            imageService.getTiles().get(0).setHeight(height);
//        }
//        // serve a UniversalViewer
//        imageService.setWidth(width);
//        imageService.setHeight(height);
//    }

}
