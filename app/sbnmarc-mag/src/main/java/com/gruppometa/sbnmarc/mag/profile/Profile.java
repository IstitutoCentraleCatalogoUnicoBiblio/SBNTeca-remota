package com.gruppometa.sbnmarc.mag.profile;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.gruppometa.sbnmarc.mag.mapping.MappingDefinition;
import com.gruppometa.sbnmarc.mag.object.OutItem;
import org.marc4j.marc.Leader;
import org.marc4j.marc.Record;

public interface Profile {
	public static final String[] ALL = new String[] { "ALL" };
	public static final String[] ALL_LETTERS =new String[] { "a","b","c","d","e","f"};
	public static final String[] A = new String[] { "a" };
	public static final String[] B = new String[] { "b" };
	boolean scarta(OutItem desc);
	String getMapVersion();
	void makeId(OutItem desc, Record record);
	boolean makeLeader(OutItem desc, Leader leader);
	public void makeSpecialOne(OutItem desc,Record record) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException;
	public void makeSpecialTwo(OutItem desc,Record record) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException;
	public void makeDefinitions(OutItem desc,Record record) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException;
	void normalize(OutItem output);
	void setFilename(String filename);
	MappingDefinition[] getDefinitions();
	MappingDefinition getDefinition(String destination);
	MappingDefinition getDefinitionFromMarcField(String marcField);
	void init();
	String getFilename();
	ParentCache getParentCache();
	String getParentIdField();
	boolean isIdAlsoParentId();
	void setParentCache(ParentCache cache);
	void notifyFullFilename(String filename);
	boolean isFinished();
	void setForFe(boolean forFe);
	String getClearFilter();
	String getClearFilterMetaindice();
}
