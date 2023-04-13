package com.gruppometa.culturaitalia.admin.objects;

/**
 * Geoname
 *
 */
public class Geoname {
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAsciiname() {
		return asciiname;
	}
	public void setAsciiname(String asciiname) {
		this.asciiname = asciiname;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	protected int id;
	protected String name;
	protected String asciiname;
	protected double latitude;
	protected double longitude;
	protected String provincia;
	protected String tipo;
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
