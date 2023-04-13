package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class JsonMagDetailResource implements Serializable {
	private static final long serialVersionUID = 5874473545499286184L;
	
	// sequence number
	@JsonProperty("sequence_number")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String sequenceNumber;
	
	// nomenclatura
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String nomenclature;
	
	// usage
	@JsonProperty("usage")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<String> usages = new ArrayList<String>();
	
	// file
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String file;
	
	// md5
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String md5;
	
	// file size
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String fileSize;

	// creazione
	@JsonProperty("datetimecreated")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String datetimeCreated;
	
	// note
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String note;
	
	// sorgente
	@JsonProperty("source")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String source;
	
	// software ocr
	@JsonProperty("software_ocr")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String softwareOcr;
	
	// posti immagine
	@JsonProperty("side")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String imageSide;
	
	// scala immagine
	@JsonProperty("scale")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String imageScale;
	
	// nome formato
	@JsonProperty("format_name")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String formatName;
	
	// mime formato
	@JsonProperty("format_mime")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String formatMime;
	
	// compressione formato
	@JsonProperty("format_compression")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String formatCompression;

	// configurazione canale
	@JsonProperty("channel_configuration")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String channelConfiguration;

	// unità di frequenza campionamento
	@JsonProperty("samplingfrequencyunit")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String samplingFrequencyUnit;
	
	// piano di frequenza campionamento
	@JsonProperty("samplingfrequencyplane")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String samplingFrequencyPlane;
	
	// frequenza di campionamento x
	@JsonProperty("xsamplingfrequency")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String xSamplingFrequency;

	// frequenza di campionamento y
	@JsonProperty("ysamplingfrequency")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String ySamplingFrequency;

	// interpretazione fotometrica
	@JsonProperty("photometricinterpretation")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String photometricInterpretation;
	
	// frequenza campionamento
	@JsonProperty("samplingfrequency")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String samplingFrequency;
	
	// bit per campione
	@JsonProperty("bitpersample")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String bitPerSample;

	// bit rate
	@JsonProperty("bitrate")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String bitRate;

	// dimensione video
	@JsonProperty("videosize")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String videoSize;
	
	// aspect ratio
	@JsonProperty("aspectratio")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String aspectRatio;
	
	// frame rate
	@JsonProperty("framerate")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String frameRate;
	
	// formato video
	@JsonProperty("videoformat")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String formatVideo;
	
	// codifica formato
	@JsonProperty("encode")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String formatEncode;
	
	// tipo di stream video
	@JsonProperty("streamtype")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String formatStreamType;
	
	// codec
	@JsonProperty("codec")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String formatCodec;
	
	// durata
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String duration;
	
	// width immagine
	@JsonProperty("imagewidth")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String imageWidth;
	
	// height immagine
	@JsonProperty("imagelength")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String imageHeight;
	
	// immagine dimensione X
	@JsonProperty("source_xdimension")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String imageX;
	
	// immagine dimensione y
	@JsonProperty("source_ydimension")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String imageY;
	
	// ppi
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String ppi;
	
	// dpi
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String dpi;

	// tipo sorgente
	@JsonProperty("sourcetype")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String sourceType;
	
	// agenzia scansione
	@JsonProperty("scanningagency")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String scanningAgency;
	
	// dispositivo sorgente
	@JsonProperty("devicesource")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String deviceSource;
	
	// marca scanner
	@JsonProperty("scanner_manufacturer")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String scannerManufacturer;
	
	// modello scanner
	@JsonProperty("scanner_model")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String scannerModel;
	
	// software di cattura
	@JsonProperty("capture_software")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String captureSoftware;
	
	// gruppo immagine
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String groupID;
	
	// lista target
	@JsonProperty("target")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<JsonMagDetailImgTarget> targets = new 
			ArrayList<JsonMagDetailImgTarget>();
	
	// preview
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String preview;
	
	// original
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String original;
	
	// original
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String data;
	
	
	// ID holdings
	@JsonProperty("id_localizzazione")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String holdingsID;
	
	
	
	
	public String getSequenceNumber() {
		return sequenceNumber;
	}
	
	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	
	public String getNomenclature() {
		return nomenclature;
	}
	
	public void setNomenclature(String nomenclature) {
		this.nomenclature = nomenclature;
	}
	
	public List<String> getUsages() {
		return usages;
	}
	
	public void setUsages(List<String> usages) {
		this.usages = usages;
	}
	
	public String getFile() {
		return file;
	}
	
	public void setFile(String file) {
		this.file = file;
	}
	
	public String getMd5() {
		return md5;
	}
	
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	
	public String getFileSize() {
		return fileSize;
	}
	
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	
	public String getDatetimeCreated() {
		return datetimeCreated;
	}
	
	public void setDatetimeCreated(String datetimeCreated) {
		this.datetimeCreated = datetimeCreated;
	}
	
	public String getSource() {
		return source;
	}
	
	public void setSource(String source) {
		this.source = source;
	}
	
	public String getSoftwareOcr() {
		return softwareOcr;
	}
	
	public void setSoftwareOcr(String softwareOcr) {
		this.softwareOcr = softwareOcr;
	}
	
	public String getImageSide() {
		return imageSide;
	}
	
	public void setImageSide(String imageSide) {
		this.imageSide = imageSide;
	}

	public String getImageScale() {
		return imageScale;
	}
	
	public void setImageScale(String imageScale) {
		this.imageScale = imageScale;
	}
	
	public String getDuration() {
		return duration;
	}
	
	public void setDuration(String duration) {
		this.duration = duration;
	}
	
	public String getImageWidth() {
		return imageWidth;
	}
	
	public void setImageWidth(String imageWidth) {
		this.imageWidth = imageWidth;
	}
	
	public String getImageHeight() {
		return imageHeight;
	}
	
	public void setImageHeight(String imageHeight) {
		this.imageHeight = imageHeight;
	}
	
	public String getImageX() {
		return imageX;
	}
	
	public void setImageX(String imageX) {
		this.imageX = imageX;
	}
	
	public String getImageY() {
		return imageY;
	}
	
	public void setImageY(String imageY) {
		this.imageY = imageY;
	}
	
	public List<JsonMagDetailImgTarget> getTargets() {
		return targets;
	}
	
	public void setTargets(List<JsonMagDetailImgTarget> targets) {
		this.targets = targets;
	}

	public String getSamplingFrequencyUnit() {
		return samplingFrequencyUnit;
	}
	
	public void setSamplingFrequencyUnit(String samplingFrequencyUnit) {
		this.samplingFrequencyUnit = samplingFrequencyUnit;
	}

	public String getSamplingFrequencyPlane() {
		return samplingFrequencyPlane;
	}
	
	public void setSamplingFrequencyPlane(String samplingFrequencyPlane) {
		this.samplingFrequencyPlane = samplingFrequencyPlane;
	}
	
	public String getXSamplingFrequency() {
		return xSamplingFrequency;
	}
	
	public void setXSamplingFrequency(String xSamplingFrequency) {
		this.xSamplingFrequency = xSamplingFrequency;
	}

	public String getYSamplingFrequency() {
		return ySamplingFrequency;
	}
	
	public void setYSamplingFrequency(String ySamplingFrequency) {
		this.ySamplingFrequency = ySamplingFrequency;
	}
	
	public String getPhotometricInterpretation() {
		return photometricInterpretation;
	}
	
	public void setPhotometricInterpretation(String photometricInterpretation) {
		this.photometricInterpretation = photometricInterpretation;
	}
	
	public String getBitPerSample() {
		return bitPerSample;
	}
	
	public void setBitPerSample(String bitPerSample) {
		this.bitPerSample = bitPerSample;
	}
	
	public String getFormatName() {
		return formatName;
	}
	
	public void setFormatName(String formatName) {
		this.formatName = formatName;
	}

	public String getFormatMime() {
		return formatMime;
	}
	
	public void setFormatMime(String formatMime) {
		this.formatMime = formatMime;
	}

	public String getFormatCompression() {
		return formatCompression;
	}
	
	public void setFormatCompression(String formatCompression) {
		this.formatCompression = formatCompression;
	}
	
	public String getPpi() {
		return ppi;
	}
	
	public void setPpi(String ppi) {
		this.ppi = ppi;
	}

	public String getDpi() {
		return dpi;
	}
	
	public void setDpi(String dpi) {
		this.dpi = dpi;
	}
	
	public String getSourceType() {
		return sourceType;
	}
	
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	
	public String getScanningAgency() {
		return scanningAgency;
	}
	
	public void setScanningAgency(String scanningAgency) {
		this.scanningAgency = scanningAgency;
	}
	
	public String getDeviceSource() {
		return deviceSource;
	}
	
	public void setDeviceSource(String deviceSource) {
		this.deviceSource = deviceSource;
	}
	
	public String getScannerManufacturer() {
		return scannerManufacturer;
	}
	
	public void setScannerManufacturer(String scannerManufacturer) {
		this.scannerManufacturer = scannerManufacturer;
	}

	public String getScannerModel() {
		return scannerModel;
	}
	
	public void setScannerModel(String scannerModel) {
		this.scannerModel = scannerModel;
	}
	
	public String getCaptureSoftware() {
		return captureSoftware;
	}
	
	public void setCaptureSoftware(String captureSoftware) {
		this.captureSoftware = captureSoftware;
	}

	public String getSamplingFrequency() {
		return samplingFrequency;
	}
	
	public void setSamplingFrequency(String samplingFrequency) {
		this.samplingFrequency = samplingFrequency;
	}

	public String getBitRate() {
		return bitRate;
	}
	
	public void setBitRate(String bitRate) {
		this.bitRate = bitRate;
	}
	
	public String getChannelConfiguration() {
		return channelConfiguration;
	}
	
	public void setChannelConfiguration(String channelConfiguration) {
		this.channelConfiguration = channelConfiguration;
	}

	public String getVideoSize() {
		return videoSize;
	}
	
	public void setVideoSize(String videoSize) {
		this.videoSize = videoSize;
	}
	
	public String getAspectRatio() {
		return aspectRatio;
	}
	
	public void setAspectRatio(String aspectRatio) {
		this.aspectRatio = aspectRatio;
	}
	
	public String getFrameRate() {
		return frameRate;
	}
	
	public void setFrameRate(String frameRate) {
		this.frameRate = frameRate;
	}
	
	public String getFormatVideo() {
		return formatVideo;
	}
	
	public void setFormatVideo(String formatVideo) {
		this.formatVideo = formatVideo;
	}

	public String getFormatEncode() {
		return formatEncode;
	}
	
	public void setFormatEncode(String formatEncode) {
		this.formatEncode = formatEncode;
	}

	public String getFormatStreamType() {
		return formatStreamType;
	}
	
	public void setFormatStreamType(String formatStreamType) {
		this.formatStreamType = formatStreamType;
	}
	
	public String getFormatCodec() {
		return formatCodec;
	}
	
	public void setFormatCodec(String formatCodec) {
		this.formatCodec = formatCodec;
	}
	
	public String getGroupID() {
		return groupID;
	}
	
	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}
	
	public String getHoldingsID() {
		return holdingsID;
	}
	
	public void setHoldingsID(String holdingsID) {
		this.holdingsID = holdingsID;
	}
	
	public String getPreview() {
		return preview;
	}
	
	public void setPreview(String preview) {
		this.preview = preview;
	}

	public String getOriginal() {
		return original;
	}
	
	public void setOriginal(String original) {
		this.original = original;
	}
	
	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}

}
