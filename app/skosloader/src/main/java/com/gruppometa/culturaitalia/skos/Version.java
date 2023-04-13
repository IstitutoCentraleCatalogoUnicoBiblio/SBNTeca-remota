package com.gruppometa.culturaitalia.skos;

public class Version {
	protected String majorVersion;
	protected int revision=0;
	public Version(){		
	}
	public Version(String ver){
		setMajorVersion(ObjectFactory.getVersion(ver));
		setRevision(ObjectFactory.getRevision(ver));
	}
	public String getMajorVersion() {
		return majorVersion;
	}
	public void setMajorVersion(String majorVersion) {
		this.majorVersion = majorVersion;
	}
	public int getRevision() {
		return revision;
	}
	public void setRevision(int revision) {
		this.revision = revision;
	}
	public boolean equals(Object obj){
		if(obj instanceof Version){
			return ((Version)obj).getMajorVersion().equals(getMajorVersion()) && ((Version)obj).getRevision()==getRevision(); 
		}
		else
			return super.equals(obj);
	}
	public Version addMajorVersion(int x1, int x2) { 
		if(majorVersion==null)
			return null;
		String[] split = majorVersion.split("\\.");
		majorVersion = ""+(Integer.parseInt(split[0])+x1)+"."+(Integer.parseInt(split[1])+x2);
		return this;
	}
	public boolean isGreater(Version version2) {
		String[] split = majorVersion.split("\\.");
		String[] split2 = version2.getMajorVersion().split("\\.");
		if(split.length<2 || split2.length<2)
			return false;
		return Integer.parseInt(split[0]) > Integer.parseInt(split2[0]) || 
				(Integer.parseInt(split[0]) == Integer.parseInt(split2[0]) && Integer.parseInt(split[1]) > Integer.parseInt(split2[1]));
	}
	public boolean sameMaxVersion(Version versionObj) {
		String[] split = majorVersion.split("\\.");
		String[] split2 = versionObj.getMajorVersion().split("\\.");
		if(split.length<2 || split2.length<2)
			return false;
		return Integer.parseInt(split[0]) == Integer.parseInt(split2[0]) ;
	}
	public String getMajor1Version() {
		String[] split = majorVersion.split("\\.");
		return split[0];
	}
	public int getMajor1VersionAsInt() {
		return Integer.parseInt(getMajor1Version());
	}
}
