package com.gruppometa.sbnmarc.mag.profile;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.gruppometa.sbnmarc.mag.object.LegameElement;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

public class ParentCache extends RecordCache {
	private Cache cache;
	private static final String CACHENAME = "cacheParents";
	protected ObjectMapper mapper = new ObjectMapper();
	@Override
	protected void init(){
		if(cache==null){
			cache = cm.getCache(CACHENAME);
		}
	}

	public String getParentId(String parent){
		String key = prefix+"parentid::"+parent;
		return cache.get(key)!=null?cache.get(key).getObjectValue().toString():parent;
	}

	public void putChild(String id, String parent, String field){
		String key = prefix+"child::"+id;
		cache.put(new Element(key, parent));
	}

	public List<String> getAllChildren(){
		@SuppressWarnings("unchecked")
		List<String> keys = cache.getKeys();
		List<String> rets = new ArrayList<String>();
		for (String string : keys) {
			if(string.startsWith(prefix+"child::"))
				rets.add(string.substring(new String(prefix+"child::").length()));
		}
		return rets;
	}

	public String getParentOfChild(String id){
		String key = prefix+"child::"+id;
		return cache.get(key)!=null?cache.get(key).getObjectValue().toString():null;
	}

	public void putParentId(String id, String parent){
		String key = prefix+"parentid::"+parent;
		cache.put(new Element(key, id));
	}

	@SuppressWarnings("unchecked")
	public void putParent(String id, String parent, String childTitle, String type){
		String key = prefix+"parent::"+parent;
		List<LegameElement> relations = null;
		if(cache.get(key)!=null)
			try {
				relations = (List<LegameElement>) mapper.readValue((String) cache.get(key).getObjectValue(), 
							new TypeReference<List<LegameElement>>(){});
			} catch (Exception e) {
				logger.error("",e);
			}
		else
			relations = new ArrayList<LegameElement>();
		relations.add(new LegameElement(parent, id, childTitle, type));
		try {
			cache.put(new Element(key, mapper.writerFor(new TypeReference<List<LegameElement>>(){}).
					writeValueAsString(relations)));
		} catch (Exception e) {
			logger.error("",e);
		}

	}
	
	public List<String> getAllParents(){
		@SuppressWarnings("unchecked")
		List<String> keys = cache.getKeys();
		List<String> rets = new ArrayList<String>();
		for (String string : keys) {
			if(string.startsWith(prefix+"parent::"))
				rets.add(string.substring(new String(prefix+"parent::").length()));
		}
		return rets;
	}

	@SuppressWarnings("unchecked")
	public List<LegameElement> getParentInfo(String id){
		String key = prefix+"parent::"+id; 
		if(cache.get(key)!=null){
			try{
				return  (List<LegameElement>) mapper.readValue((String) cache.get(key).getObjectValue(), 
					new TypeReference<List<LegameElement>>(){});
			} catch (Exception e) {
				logger.error("",e);
			}
		}
		return null;
	}
	
	public void finalize(){
	}
}
