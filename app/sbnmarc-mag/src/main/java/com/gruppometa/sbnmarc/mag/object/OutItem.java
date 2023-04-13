package com.gruppometa.sbnmarc.mag.object;

import java.util.List;




public interface OutItem {
	boolean isSkipped();

	String getAbout();

	void setAbout(String string);

	boolean hasNode(String destinationNode, String qualifier);

	Field addField(String string, String qualifier);
	
	public List<Field> getFieldArray(String string);	
	
	public Field addNewField(String string, String qualifier);
	
	public List<Field> getFields();


	void setSkipped(boolean b);
}
