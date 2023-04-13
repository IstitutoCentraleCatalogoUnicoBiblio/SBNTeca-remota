package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;


public class JsonMagEditorImgGroup implements Serializable {
	private static final long serialVersionUID = -1510910428942796369L;

	// id
	private String id;
	
	// unità di frequenza campionamento
	@JsonProperty("samplingfrequencyunit")
	private String samplingFrequencyUnit;
	
	// piano di frequenza campionamento
	@JsonProperty("samplingfrequencyplane")
	private String samplingFrequencyPlane;
	
	// frequenza di campionamento x
	@JsonProperty("xsamplingfrequency")
	private String xSamplingFrequency;

	// frequenza di campionamento y
	@JsonProperty("ysamplingfrequency")
	private String ySamplingFrequency;

	// interpretazione fotometrica
	@JsonProperty("photometricinterpretation")
	private String photometricInterpretation;
	
	// bit per campione
	@JsonProperty("bitpersample")
	private String bitPerSample;
	
	// nome formato
	@JsonProperty("format_name")
	private String formatName;
	
	// mime formato
	@JsonProperty("format_mime")
	private String formatMime;
	
	// compressione formato
	@JsonProperty("format_compression")
	private String formatCompression;
	
	// ppi
	private String ppi;
	
	// dpi
	private String dpi;
	
	// tipo sorgente
	@JsonProperty("sourcetype")
	private String sourceType;
	
	// agenzia scansione
	@JsonProperty("scanningagency")
	private String scanningAgency;
	
	// dispositivo sorgente
	@JsonProperty("devicesource")
	private String deviceSource;
	
	// marca scanner
	@JsonProperty("scanner_manufacturer")
	private String scannerManufacturer;
	
	// modello scanner
	@JsonProperty("scanner_model")
	private String scannerModel;
	
	// software di cattura
	@JsonProperty("capture_software")
	private String captureSoftware;
	
	
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
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

}
