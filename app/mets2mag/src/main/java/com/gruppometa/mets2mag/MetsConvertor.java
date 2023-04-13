package com.gruppometa.mets2mag;

import com.gruppometa.data.mets.FileGrpType;
import com.gruppometa.data.mets.Mets;
import com.gruppometa.data.mets.MetsType;
import com.gruppometa.data.mods.Mods;
import javax.xml.bind.*;

import com.gruppometa.data.mods.ModsCollection;
import org.eclipse.persistence.jaxb.JAXBContextProperties;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MetsConvertor {

    protected static final String context = "com.gruppometa.data.mets:com.gruppometa.data.mods";
    protected static final Logger logger = LoggerFactory.getLogger(MetsConvertor.class);
    protected JAXBContext jc = null;
    protected static Object lock = new Object();

    public Mets readMetsFromUrl(URL url) throws JAXBException {
        Unmarshaller unmarshaller = getJAXBContext().createUnmarshaller();
        return (Mets) unmarshaller.unmarshal(url);
    }

    public MetsConvertor(){
    }

    public JAXBContext getJAXBContext(){
        if(jc == null){
            synchronized (lock){
                if(jc == null){
                    try {
                        jc = JAXBContext.newInstance(new Class[]{Mets.class, Mods.class});
                    } catch (JAXBException e) {
                        logger.error("", e);
                    }
                }
            }
        }
        return jc;
    }
    public String convertMets2Json(Mets mets) throws JAXBException {
        return convertMets2Json(mets, null);
    }

    public String convertMets2Json(Mets mets, StringWriter stringWriterErrors) throws JAXBException {
        Marshaller marshaller = getJAXBContext().createMarshaller();
        StringWriter stringWriter = new StringWriter();
        marshaller.setProperty(MarshallerProperties.MEDIA_TYPE,"application/json");
        marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, true);
        marshaller.setProperty(MarshallerProperties.JSON_TYPE_ATTRIBUTE_NAME, "typeInternal");
        marshaller.setProperty(MarshallerProperties.MEDIA_TYPE,"application/json");
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(JAXBContextProperties.NAMESPACE_PREFIX_MAPPER, getNamespaces());
        marshaller.setEventHandler(new ValidationEventHandler() {
            @Override
            public boolean handleEvent(ValidationEvent validationEvent) {
                logger.error(validationEvent.getMessage());
                return false;
            }
        });
        marshaller.marshal(mets, stringWriter);
        return stringWriter.toString();
    }

    public String convertMods2Json(Mods mods, StringWriter stringWriterErrors) throws JAXBException {
        Marshaller marshaller = getJAXBContext().createMarshaller();
        StringWriter stringWriter = new StringWriter();
        marshaller.setProperty(MarshallerProperties.MEDIA_TYPE,"application/json");
        marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, true);
        marshaller.setProperty(MarshallerProperties.JSON_TYPE_ATTRIBUTE_NAME, "typeInternal");
        marshaller.setProperty(MarshallerProperties.MEDIA_TYPE,"application/json");
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(JAXBContextProperties.NAMESPACE_PREFIX_MAPPER, getNamespaces());
        marshaller.setEventHandler(new ValidationEventHandler() {
            @Override
            public boolean handleEvent(ValidationEvent validationEvent) {
                logger.error(validationEvent.getMessage());
                return false;
            }
        });
        marshaller.marshal(mods, stringWriter);
        return stringWriter.toString();
    }

    public Mets convertJson2Mets(String json) throws JAXBException {
        Unmarshaller unmarshaller = getJAXBContext().createUnmarshaller();
        unmarshaller.setProperty(MarshallerProperties.MEDIA_TYPE,"application/json");
        unmarshaller.setProperty(JAXBContextProperties.NAMESPACE_PREFIX_MAPPER, getNamespaces());
        unmarshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, true);
        return (Mets) unmarshaller.unmarshal(new StringReader(json));
    }

    public Mets convertJson2Mets(InputStream inputStream) throws JAXBException {
        Unmarshaller unmarshaller = getJAXBContext().createUnmarshaller();
        unmarshaller.setProperty(MarshallerProperties.MEDIA_TYPE,"application/json");
        unmarshaller.setProperty(JAXBContextProperties.NAMESPACE_PREFIX_MAPPER, getNamespaces());
        unmarshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, true);
        return (Mets) unmarshaller.unmarshal(inputStream);
    }

    public Map getNamespaces(){
        Map<String, String> namespaces = new HashMap<>();
        namespaces.put(javax.xml.XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI, "xsi");
        namespaces.put("http://www.loc.gov/METS/", "mets");
        namespaces.put("http://www.loc.gov/mods/v3", "mods");
        namespaces.put("http://www.w3.org/1999/xlink", "xlink");
        namespaces.put("http://cosimo.stanford.edu/sdr/metsrights/","rights");
        namespaces.put("http://www.niso.org/pdfs/DataDict.pdf","niso");
        namespaces.put("http://www.loc.gov/audioMD/","audiomd");
        namespaces.put("http://www.loc.gov/videoMD/","videomd");
        namespaces.put("http://www.loc.gov/mix/v20","mix");
        namespaces.put("http://www.w3.org/XML/1998/namespace","xml");
        return namespaces;
    }

    public String convertMets2Xml(Mets mets) throws JAXBException {
        Marshaller marshaller = getJAXBContext().createMarshaller();
        marshaller.setEventHandler(new ValidationEventHandler() {
            @Override
            public boolean handleEvent(ValidationEvent validationEvent) {
                logger.error(validationEvent.getMessage());
                return false;
            }
        });
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(JAXBContextProperties.NAMESPACE_PREFIX_MAPPER, getNamespaces());
        StringWriter stringWriter2 = new StringWriter();
        marshaller.marshal(mets, stringWriter2);
        return stringWriter2.toString();
    }

    public Mets readMetsFromString(String metsXmlString) throws JAXBException {
        Unmarshaller unmarshaller = getJAXBContext().createUnmarshaller();
        unmarshaller.setEventHandler(new ValidationEventHandler() {
            @Override
            public boolean handleEvent(ValidationEvent validationEvent) {
                logger.error(validationEvent.getMessage());
                return false;
            }
        });
        return (Mets) unmarshaller.unmarshal(new StringReader(metsXmlString));
    }

    public List<String> getResourcesIds(Mets mets) {
        List<String> ids = new ArrayList<>();
        getResourcesIds(mets.getFileSec().getFileGrps(), ids);
        return ids;
    }

    private void getResourcesIds(List<MetsType.FileSec.FileGrp> fileGrps, List<String> ids) {
        if(fileGrps==null)
            return;
        for(MetsType.FileSec.FileGrp fileGrp: fileGrps){
            getResourcesIdsType(fileGrp.getFileGrps(), ids);
            fileGrp.getFiles().stream().forEach(f-> ids.add(filterDigitalObject(f.getFLocats().get(0).getHref())));
        }
    }

    public static String filterDigitalObject(String href) {
        if(href==null)
            return null;
        String prefix = "digitalObject/";
        String prefix2 = "/digitalObject/";
        if(href.startsWith(prefix))
            return href.substring(prefix.length());
        if(href.startsWith(prefix2))
            return href.substring(prefix2.length());
        return href;
    }

    private void getResourcesIdsType(List<FileGrpType> fileGrps, List<String> ids) {
        if(fileGrps==null)
            return;
        for(FileGrpType fileGrp: fileGrps){
            getResourcesIdsType(fileGrp.getFileGrps(), ids);
            fileGrp.getFiles().stream().forEach(f-> ids.add(filterDigitalObject(f.getFLocats().get(0).getHref())));
        }
    }

    public ModsCollection readModsFromString(String mods) throws JAXBException {
        Unmarshaller unmarshaller = JAXBContext.newInstance(new Class[]{ModsCollection.class}).createUnmarshaller();
        unmarshaller.setEventHandler(new ValidationEventHandler() {
            @Override
            public boolean handleEvent(ValidationEvent validationEvent) {
                logger.error(validationEvent.getMessage());
                return false;
            }
        });
        //logger.info(mods);
        return (ModsCollection) unmarshaller.unmarshal(new StringReader(mods));
    }
}
