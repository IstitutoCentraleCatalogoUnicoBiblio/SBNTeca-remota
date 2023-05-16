package com.gruppometa.sbntecaremota.vfsfilesystem;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.gruppometa.sbntecaremota.Views;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.solr.common.StringUtils;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@Schema(description = "VFSFile")
public class VfsFile {
    public static String TYPE_OBJECT = "object";
    public static String TYPE_CONTAINER = "container";
    public static String TYPE_DIRECTORY = "directory";
    @JsonView(Views.PublicView.class)
    protected String id;
    protected String draftOf;
    protected boolean draft;
    protected String vfsType;
    protected String filename;
    protected String frontespizio;
    protected String parent;
    protected String createdFrom;

    public String getCreatedFrom() {
        return createdFrom;
    }

    public void setCreatedFrom(String createdFrom) {
        this.createdFrom = createdFrom;
    }

    public boolean isDraft() {
        return draft;
    }

    public void setDraft(boolean draft) {
        this.draft = draft;
    }

    public String getFrontespizio() {
        return frontespizio;
    }

    public void setFrontespizio(String frontespizio) {
        this.frontespizio = frontespizio;
    }

    @Schema(description = "Url dell'oggetto")
    @JsonView(Views.PublicView.class)
    protected String url;
    @JsonView(Views.PublicView.class)
    protected String preview;
    protected List<String> container;
    protected String originalPath;
    protected String vfsPath;
    protected String tecaPath;
    @JsonView(Views.PublicView.class)
    protected String contentType;
    protected String resourceType;
    @Schema(description = "Altezza dell'immagine")
    @JsonView(Views.PublicView.class)
    protected String height;
    @Schema(description = "Largezza dell'immagine")
    @JsonView(Views.PublicView.class)
    protected String width;
    @JsonView(Views.PublicView.class)
    @Schema(description = "Lunghezza del audio o video")
    protected String duration;
    @JsonView(Views.PublicView.class)
    protected String note;
    protected String idPublic;
    @JsonView(Views.PublicView.class)
    protected String used;
    @JsonView(Views.PublicView.class)
    protected Integer order;
    protected boolean takeFromOriginalPath = false;
    @JsonView(Views.PublicView.class)
    protected String label;
    @JsonView(Views.PublicView.class)
    protected String usage = "3";
    @JsonView(Views.PublicView.class)
    protected String fileSize;
    protected Date timestamp;
    @JsonView(Views.PublicView.class)
    protected String md5;
    protected List<String> directory;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    @JsonIgnore
    public boolean isTakeFromOriginalPath() {
        return takeFromOriginalPath;
    }

    public void setTakeFromOriginalPath(boolean takeFromOriginalPath) {
        this.takeFromOriginalPath = takeFromOriginalPath;
    }

    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getIdPublic() {
        return idPublic;
    }

    public void setIdPublic(String idPublic) {
        this.idPublic = idPublic;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getTecaPath() {
        return tecaPath;
    }

    public void setTecaPath(String tecaPath) {
        this.tecaPath = tecaPath;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getDraftOf() {
        return draftOf;
    }

    public void setDraftOf(String draftOf) {
        this.draftOf = draftOf;
    }

    public boolean exists(){
        if(TYPE_DIRECTORY.equals(vfsType))
            return true;
        return getFile().exists();
    }

    @JsonIgnore
    public String getPath(){
        File file = getFile();
        if(file!=null)
            return file.getPath();
        else
            return null;
    }

    @JsonIgnore
    public File getFile(){
        if(!StringUtils.isEmpty(vfsPath))
            return new File(vfsPath);
        else if(!StringUtils.isEmpty(tecaPath))
            return new File(tecaPath);
        else if(!StringUtils.isEmpty(originalPath))
            return new File(originalPath);
        else
            return null;
    }

    public String getVfsType() {
        return vfsType;
    }

    public void setVfsType(String vfsType) {
        this.vfsType = vfsType;
    }

    public List<String> getDirectory() {
        if(directory==null)
            directory = new ArrayList<>();
        return directory;
    }

    public void setDirectory(List<String> directory) {
        this.directory = directory;
    }

    public String getVfsPath() {
        return vfsPath;
    }

    public void setVfsPath(String vfsPath) {
        this.vfsPath = vfsPath;
    }

    public List<String> getContainer() {
        if(container==null)
            container = new ArrayList<>();
        return container;
    }
    public void setContainer(List<String> container) {
        this.container = container;
    }

    public String getOriginalPath() {
        return originalPath;
    }

    public void setOriginalPath(String originalPath) {
        this.originalPath = originalPath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }
}

