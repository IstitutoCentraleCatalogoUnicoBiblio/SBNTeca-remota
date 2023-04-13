package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class JsonMagPresentationResource implements Serializable {
	private static final long serialVersionUID = 5874473545499286184L;
	
	// sequence number
	@JsonProperty("numero_sequenza")
	private String sequenceNumber;
	
	// nomenclatura
	@JsonProperty("nomenclatura")
	private String nomenclature;
	
	// usage
	@JsonProperty("usage")
	private List<String> usages = new ArrayList<String>();
	
	// file
	private String file;
	
	// md5
	private String md5;
	
	// file size
	@JsonProperty("dimensioni_file")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String fileSize;

	// creazione
	@JsonProperty("data_creazione")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String datetimeCreated;
	
	// note
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String note;
	
	// sorgente
	@JsonProperty("sorgente_ocr")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String source;
	
	// software ocr
	@JsonProperty("software_ocr")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String softwareOcr;
	
	// posti immagine
	@JsonProperty("pagine_immagine")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String imageSide;
	
	// scala immagine
	@JsonProperty("scala_immagine")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String imageScale;
	
	// nome formato
	@JsonProperty("nome_formato")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String formatName;
	
	// mime formato
	@JsonProperty("mime_formato")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String formatMime;
	
	// compressione formato
	@JsonProperty("compressione_formato")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String formatCompression;

	// configurazione canale
	@JsonProperty("canale_formato")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String channelConfiguration;
	
	// unità di frequenza campionamento
	@JsonProperty("unita_campionamento")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String samplingFrequencyUnit;
	
	// piano di frequenza campionamento
	@JsonProperty("piano_campionamento")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String samplingFrequencyPlane;
	
	// frequenza di campionamento x
	@JsonProperty("x_campionamento")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String xSamplingFrequency;

	// frequenza di campionamento y
	@JsonProperty("y_campionamento")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String ySamplingFrequency;

	// interpretazione fotometrica
	@JsonProperty("fotometrica")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String photometricInterpretation;
	
	// frequenza campionamento
	@JsonProperty("frequenza")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String samplingFrequency;
	
	// bit per campione
	@JsonProperty("bit_campione")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String bitPerSample;

	// bit rate
	@JsonProperty("velocita")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String bitRate;

	// dimensione video
	@JsonProperty("dimensioni_video")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String videoSize;
	
	// aspect ratio
	@JsonProperty("rapporto")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String aspectRatio;
	
	// frame rate
	@JsonProperty("velocita_frame")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String frameRate;
	
	// formato video
	@JsonProperty("video_formato")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String formatVideo;
	
	// codifica formato
	@JsonProperty("codifica_formato")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String formatEncode;
	
	// tipo di stream video
	@JsonProperty("tipo_stream_formato")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String formatStreamType;
	
	// codec
	@JsonProperty("codec_formato")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String formatCodec;
	
	// durata
	@JsonProperty("durata")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String duration;
	
	// width immagine
	@JsonProperty("larghezza_immagine")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String imageWidth;
	
	// height immagine
	@JsonProperty("altezza_immagine")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String imageHeight;
	
	// immagine dimensione X
	@JsonProperty("x_immagine")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String imageX;
	
	// immagine dimensione y
	@JsonProperty("y_immagine")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String imageY;
	
	// ppi
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String ppi;
	
	// dpi
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String dpi;
	
	// tipo sorgente
	@JsonProperty("tipo_sorgente")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String sourceType;
	
	// agenzia scansione
	@JsonProperty("agenzia_scansione")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String scanningAgency;
	
	// dispositivo sorgente
	@JsonProperty("dispositivo_sorgente")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String deviceSource;
	
	// marca scanner
	@JsonProperty("produttore_scanner")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String scannerManufacturer;
	
	// modello scanner
	@JsonProperty("modello_scanner")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String scannerModel;
	
	// software di cattura
	@JsonProperty("software_cattura")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String captureSoftware;
	
	// lista target
	@JsonProperty("target")
	private List<JsonMagPresentationImgTarget> targets = new ArrayList<JsonMagPresentationImgTarget>();
	
	// gruppo immagine
	@JsonProperty("id_gruppo")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String groupID;
	
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
	
	public List<JsonMagPresentationImgTarget> getTargets() {
		return targets;
	}
	
	public void setTargets(List<JsonMagPresentationImgTarget> targets) {
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

}
