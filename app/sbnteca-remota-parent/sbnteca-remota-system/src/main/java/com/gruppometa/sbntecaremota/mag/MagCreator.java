package com.gruppometa.sbntecaremota.mag;

import com.gruppometa.data.mets.Mets;
import com.gruppometa.mets2mag.saxon.SaxonHelper;
import com.gruppometa.sbntecaremota.mets.MetsCreator;
import com.gruppometa.sbntecaremota.objects.Mag;
import com.gruppometa.sbntecaremota.objects.json.JsonDeleteProject;
import com.gruppometa.sbntecaremota.objects.json.JsonImportConfiguration;
import com.gruppometa.sbntecaremota.objects.json.JsonImportReport;
import com.gruppometa.sbntecaremota.util.UtilSolr;
import com.gruppometa.sbntecaremota.util.UtilXML;
import com.gruppometa.sbntecaremota.vfsfilesystem.VfsFile;
import org.apache.solr.common.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.namespace.NamespaceContext;
import javax.xml.transform.Transformer;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;

import static com.gruppometa.sbntecaremota.retrieve.FileMagPersistence.config;

@Component
public class MagCreator extends MetsCreator{

    public XPath getXPath(){
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        xpath.setNamespaceContext(new NamespaceContext(){

            public String getNamespaceURI(String prefix) {
                if(prefix.equals("mag"))
                    return "http://www.iccu.sbn.it/metaAG1.pdf";
                else
                    return null;
            }

            public String getPrefix(String uri) {
                if(uri.equals("http://www.iccu.sbn.it/metaAG1.pdf"))
                    return "mag";
                else
                    return null;
            }

            @SuppressWarnings("rawtypes")
            public Iterator getPrefixes(String arg0) {
                return null;
            }});
        return xpath;
    }
    public JsonDeleteProject createMag(String id, boolean isDraft) {
        boolean force = true;
        Document magForUpdate = null;
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
        String documentFormat = "mag";
        vfsFile.setResourceType(documentFormat);
        List<VfsFile> resources = vfsService.getResources(id, 0, Integer.MAX_VALUE);
        try {
            /**
             * creazione mets
             */
            Mets mets = getMets(id, resources);
            String idSolr =  "mets:"+id;

            Transformer transformer = SaxonHelper.getInstance().getTransformerMods();
            JsonImportConfiguration importMagConfig = getJsonImportConfiguration(parent.getId(), idSolr, documentFormat);
            config(importMagConfig.getConfigurationAsProperties(), transformer);
            StreamSource inputSource = new StreamSource(new StringReader(metsConvertor.convertMets2Xml(mets)));
            StringWriter stringWriter = new StringWriter();
            transformer.transform(inputSource, new StreamResult(stringWriter));
            String magNew = stringWriter.toString();

            /**
             * esiste il mag?
             */
            Mag mag = null;
            boolean isNewMag = false;
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
                mag.setMagOriginal(magNew);
                vfsFile.setContentType(MediaType.APPLICATION_XML_VALUE);
            }
            else {
                /**
                 * prendere il mods del esistente se non viene dall'editore...
                 */
                Document originalDocumentOld = UtilXML.convertStringToDocumentXML(mag.getMagOriginal());
                Document originalDocumentNew = UtilXML.convertStringToDocumentXML(magNew);
                XPath xpath = getXPath();
                /**
                 * copia dalla vecchia alla nuova
                 */
                copySection(originalDocumentOld, originalDocumentNew, xpath,"//mag:gen");
                copySection(originalDocumentOld, originalDocumentNew, xpath,"//mag:bib");

                mag.setMagOriginal(UtilXML.convertDocumentToString(originalDocumentNew));
            }
            // salva mag
            if(mag.getMagOriginal()!=null) {
                /**
                 * update vfs
                 */
                vfsService.writeToVfs(vfsFile, mag.getMagOriginal());
                vfsService.updateVfsFile(vfsFile, true);
                /**
                 * salva in Solr
                 */
                if (!isNewMag) {
                    UtilSolr.setFieldSolr(mag.getIdMag(), "magxmlOriginal", mag.getMagOriginal(), true);
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
                JsonImportReport report = executeJob(parent, idSolr,"mag"); // idSolr punta al container
                if(report.getMagKO()>0){
                    jsonDeleteProject.setStatus(-4);
                    jsonDeleteProject.setMessage("Problemi per la conversione interna.");
                }
                if (magForUpdate != null) {
//                    vfsService.makeAsUsed(resources, magForUpdate);
//                    vfsService.updateOrderAndLabel(magForUpdate);
                }
                else {
                    vfsService.makeAsUsed(resources);
                }
                UtilSolr.setFieldSolr(mag.getIdMag(), "draft", isDraft?"1":"0", true);
                return jsonDeleteProject;
            }
            else{
                jsonDeleteProject.setStatus(-3);
                jsonDeleteProject.setMessage("Nessun file MAG creato.");
                return jsonDeleteProject;
            }
        } catch (Exception e) {
            logger.error("", e);
            jsonDeleteProject.setStatus(-2);
            jsonDeleteProject.setMessage(e.getMessage());
            return jsonDeleteProject;
        }
    }

    private void copySection(Document originalDocumentOld, Document originalDocumentNew, XPath xpath, String expression) throws XPathExpressionException {
        NodeList gensNew = (NodeList) xpath.compile(expression).evaluate(originalDocumentNew, XPathConstants.NODESET);
        NodeList gensOld = (NodeList) xpath.compile(expression).evaluate(originalDocumentOld, XPathConstants.NODESET);
        if(gensOld!=null && gensOld.getLength()>0) {
            Node genOld = originalDocumentNew.importNode(gensOld.item(0), true);
            Node genToSub = gensNew.item(0); // nuovo bib o gen
            Node parentGen = genToSub.getParentNode(); // padre
            parentGen.insertBefore(genOld, genToSub);
            parentGen.removeChild(genToSub); // cancella quello nuovo non da editor
        }
    }

}
