package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class JsonMagDetailAudioGroup implements Serializable {
	private static final long serialVersionUID = -8469568453384712075L;

	// id
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String id;
	
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

	// tipo sorgente
	@JsonProperty("sourcetype")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String sourceType;
	
	// agenzia
	@JsonProperty("transcriptionagency")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String transcriptionAgency;
	
	// data
	@JsonProperty("transcriptiondate")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String transcriptionDate;
	
	// tipo dispositivo
	@JsonProperty("devicesource")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String deviceSource;
	
	
	

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getSamplingFrequency() {
		return samplingFrequency;
	}
	
	public void setSamplingFrequency(String samplingFrequency) {
		this.samplingFrequency = samplingFrequency;
	}

	public String getBitPerSample() {
		return bitPerSample;
	}
	
	public void setBitPerSample(String bitPerSample) {
		this.bitPerSample = bitPerSample;
	}

	public String getBitRate() {
		return bitRate;
	}
	
	public void setBitRate(String bitRate) {
		this.bitRate = bitRate;
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
	
	public String getChannelConfiguration() {
		return channelConfiguration;
	}
	
	public void setChannelConfiguration(String channelConfiguration) {
		this.channelConfiguration = channelConfiguration;
	}
	
	public String getSourceType() {
		return sourceType;
	}
	
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	
	public String getTranscriptionAgency() {
		return transcriptionAgency;
	}
	
	public void setTranscriptionAgency(String transcriptionAgency) {
		this.transcriptionAgency = transcriptionAgency;
	}

	public String getTranscriptionDate() {
		return transcriptionDate;
	}
	
	public void setTranscriptionDate(String transcriptionDate) {
		this.transcriptionDate = transcriptionDate;
	}
	
	public String getDeviceSource() {
		return deviceSource;
	}
	
	public void setDeviceSource(String deviceSource) {
		this.deviceSource = deviceSource;
	}

}
