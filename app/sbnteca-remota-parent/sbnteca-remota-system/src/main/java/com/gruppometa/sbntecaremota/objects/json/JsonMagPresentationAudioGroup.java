package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonMagPresentationAudioGroup implements Serializable {
	private static final long serialVersionUID = -8469568453384712075L;

	// id
	private String id;
	
	// frequenza campionamento
	@JsonProperty("frequenza")
	private String samplingFrequency;
	
	// bit per campione
	@JsonProperty("bit_campione")
	private String bitPerSample;

	// bit rate
	@JsonProperty("velocita")
	private String bitRate;

	// nome formato
	@JsonProperty("nome_formato")
	private String formatName;
	
	// mime formato
	@JsonProperty("mime_formato")
	private String formatMime;
	
	// compressione formato
	@JsonProperty("compressione_formato")
	private String formatCompression;
	
	// configurazione canale
	@JsonProperty("canale_formato")
	private String channelConfiguration;
	
	
	

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

}
