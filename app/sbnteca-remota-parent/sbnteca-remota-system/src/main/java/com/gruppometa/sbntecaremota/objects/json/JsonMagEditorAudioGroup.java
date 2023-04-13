package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


public class JsonMagEditorAudioGroup implements Serializable {
	private static final long serialVersionUID = -8469568453384712075L;

	// id
	private String id;
	
	// frequenza campionamento
	@JsonProperty("samplingfrequency")
	private String samplingFrequency;
	
	// bit per campione
	@JsonProperty("bitpersample")
	private String bitPerSample;

	// bit rate
	@JsonProperty("bitrate")
	private String bitRate;

	// nome formato
	@JsonProperty("format_name")
	private String formatName;
	
	// mime formato
	@JsonProperty("format_mime")
	private String formatMime;
	
	// compressione formato
	@JsonProperty("format_compression")
	private String formatCompression;
	
	// configurazione canale
	@JsonProperty("channel_configuration")
	private String channelConfiguration;

	// tipo sorgente
	@JsonProperty("sourcetype")
	private String sourceType;
	
	// agenzia
	@JsonProperty("transcriptionagency")
	private String transcriptionAgency;
	
	// data
	@JsonProperty("transcriptiondate")
	private String transcriptionDate;
	
	// tipo dispositivo
	@JsonProperty("devicesource")
	private String deviceSource;
	
	// lista chains
	@JsonProperty("transcriptionchain")
	private List<JsonMagEditorTranscriptionChain> chains = 
			new ArrayList<JsonMagEditorTranscriptionChain>();
	
	
	@JsonProperty("transcriptionsummary")
	private List<JsonMagEditorTranscriptionSummary> summaries = 
			new ArrayList<JsonMagEditorTranscriptionSummary>();
	

	@JsonProperty("transcriptiondata")
	private List<JsonMagEditorTranscriptionData> data = 
			new ArrayList<JsonMagEditorTranscriptionData>();
	
	
	

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
	
	public List<JsonMagEditorTranscriptionChain> getChains() {
		return chains;
	}
	
	public void setChains(List<JsonMagEditorTranscriptionChain> chains) {
		this.chains = chains;
	}

	public List<JsonMagEditorTranscriptionSummary> getSummaries() {
		return summaries;
	}
	
	public void setSummaries(List<JsonMagEditorTranscriptionSummary> summaries) {
		this.summaries = summaries;
	}

	public List<JsonMagEditorTranscriptionData> getData() {
		return data;
	}
	
	public void setData(List<JsonMagEditorTranscriptionData> data) {
		this.data = data;
	}

}
