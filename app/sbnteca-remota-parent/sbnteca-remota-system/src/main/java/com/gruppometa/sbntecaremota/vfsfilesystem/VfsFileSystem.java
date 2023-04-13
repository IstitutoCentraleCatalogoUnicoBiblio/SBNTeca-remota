package com.gruppometa.sbntecaremota.vfsfilesystem;

import com.gruppometa.sbntecaremota.util.ContentStatic;
import org.apache.commons.io.IOUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static com.gruppometa.sbntecaremota.util.Utility.getMD5;
import static com.gruppometa.sbntecaremota.util.Utility.getResourceDeliveryID;

public class VfsFileSystem {

    protected static final Logger logger = LoggerFactory.getLogger(VfsFileSystem.class);

    @Autowired
    protected VfsService vfsService;

    public VfsService getVfsService() {
        return vfsService;
    }

    boolean virtual = true;
    public Boolean exists(File file) {
        if(!virtual)
            return file.exists();
        return vfsService.getDirectoryByOriginalPath(file.getAbsolutePath())!=null;
    }

    public File newFile(String basePath, String fileName, String magId, String project,
                        String type, String draftOf,
                        String vfsType) {
        File oldFile = new File(basePath, fileName);
        if(!virtual)
            return oldFile;
        File vFile = new File(createVfsFileOutput(project, magId, oldFile.getAbsolutePath()));
        VfsFile vfsItem = new VfsFile();
        try {
            vfsItem.setId(getResourceDeliveryID(oldFile, false));
            vfsItem.setFilename(fileName);
            vfsItem.setLabel(fileName);
            vfsItem.setResourceType(type);
            vfsItem.setParent(oldFile.getParentFile().getAbsolutePath());
            vfsItem.getContainer().add(magId);
            if(draftOf!=null) {
                VfsFile container = vfsService.getContainerByVfsPath(draftOf);
                if(container!=null)
                    vfsItem.setDraftOf(container.getId());
                else
                    logger.info("Error container not found "+draftOf);
            }
            vfsItem.getDirectory().add(project);
            vfsItem.setVfsType(vfsType);
            vfsItem.setOriginalPath(oldFile.getAbsolutePath());
            vfsItem.setVfsPath(vFile.getAbsolutePath());
            vfsService.save(vfsItem);
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
        return vFile;
    }

    public static String createVfsFileOutput(String project, String container, String path){
        try {
            String md5 = getMD5(project+"/"+container+"/"+path);
            String relativeFile = md5.substring(0,2)+"/"+md5.substring(2,4)+"/"+md5.substring(4,6)+"/"+
                    md5.substring(6);
            File systemFile = new File(ContentStatic.getProperties().
                    getProperty("resourceDIRV"), relativeFile);
            return systemFile.getAbsolutePath();
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;
    }

    public VfsFile getProjectDirectory(String baseFolder, String name) {
        VfsFile vfsFile = new VfsFile();
        vfsFile.getDirectory().add(name);
        vfsFile.setFilename(name);
        vfsFile.setOriginalPath(new File(baseFolder, name).getAbsolutePath());
        try {
            vfsFile.setId(getResourceDeliveryID(new File(baseFolder, name), false));
        } catch (Exception e) {
            logger.error("", e);
        }
        vfsFile.setVfsType(VfsFile.TYPE_DIRECTORY);
        return vfsFile;
    }

    public VfsFile getContainerFile(VfsFile projectFile, String relativePath, boolean checkForUpdate) {
        if(relativePath.contains("[draft_")){

        }
        List<VfsFile> containers =  vfsService.getContainers(projectFile.getFilename(), relativePath,null,0,2, false);
        if(containers.size()>0){
            VfsFile container = containers.get(0);
            updateFileOriginale(checkForUpdate, container);
            try {
                vfsService.save(container);
            } catch (Exception e) {
                logger.error("", e);
            }
            return containers.get(0);
        }
//        if(containers.size()>0 && containers.get(0).getDraftOf()!=null){
//            containers =  vfsService.getContainers(projectFile.getFilename(), containers.get(0).getDraftOf(),0,2);
//            if(containers.size()>0){
//                return containers.get(0);
//            }
//        }
        VfsFile vfsFile = new VfsFile();
        vfsFile.getDirectory().add(projectFile.getId());
        vfsFile.setFilename(relativePath);
        vfsFile.setOriginalPath(new File(projectFile.getOriginalPath(), relativePath).getAbsolutePath());
        vfsFile.setVfsPath(createVfsFileOutput(projectFile.getId(),"undefined", vfsFile.getOriginalPath()));
        updateFileOriginale(checkForUpdate, vfsFile);
        vfsFile.setVfsType(VfsFile.TYPE_CONTAINER);
        return vfsFile;
    }

    private void updateFileOriginale(boolean checkForUpdate, VfsFile container) {
        boolean active = false;
        if(active && checkForUpdate && new File(container.getOriginalPath()).exists()){
            try {
                logger.info("Update file "+ container.getVfsPath());
                if(!new File(container.getVfsPath()).exists())
                    new File(container.getVfsPath()).getParentFile().mkdirs();
                Files.copy(Path.of(container.getOriginalPath()), Path.of(container.getVfsPath()), StandardCopyOption.REPLACE_EXISTING);
                MetadataCreator.makeMetadata(container, container.getVfsPath());
            } catch (Exception e) {
                logger.error("", e);
            }
        }
    }

    public VfsFile getResourceFile(String vfsPath, String pathFolder, String href) {
        if (href == null)
            return null;
        if(href.contains("?"))
            href = href.substring(0, href.indexOf("?"));
        VfsFile vfsFile = vfsService.getResourceByVfsPathAndHref(vfsPath, href, true);
        return vfsFile;
    }

    public boolean isDraft(String currentPath) {
        VfsFile vfsFile = getVfsService().getVfsFileByVfsPath(currentPath);
        return vfsFile!=null && (vfsFile.isDraft()||vfsFile.getDraftOf()!=null);
    }
}
