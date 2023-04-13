package com.gruppometa.sbntecaremota.test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.junit.Ignore;
import org.junit.Test;

import com.gruppometa.sbntecaremota.restweb.objects.MagEditorService;
import com.gruppometa.sbntecaremota.util.ContentStatic;
import com.gruppometa.sbntecaremota.util.UtilSolr;
import com.gruppometa.sbntecaremota.util.Utility;

public class UtilityTest {
	@Ignore
	public void downloadFromUrlTest() {
		try {
			Utility.downloadFromUrl(new URL("https://www.java.com/ga/images/en/plugin_cache2.jpg"), "/home/tomcat/Scrivaniaplugin.jpg");
		} catch(IOException e) {}
	}
	
	@Ignore
	public void tempTest(){
		Long start=new Long("1425394800000");	
		Long end=System.currentTimeMillis();
		Long interval=end-start;
		String result=Utility.getTime(interval);
		System.out.println("Tempo trascorso "+result);
	}
	
	@Ignore
	public void testTempo() {
		Long start=new Long("1425394800000");
		Date date = new Date(start);
		System.out.println(date.toString());
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTimeInMillis(start);
	    int mYear = calendar.get(Calendar.YEAR);
	    int mMonth = calendar.get(Calendar.MONTH);
	    int mDay = calendar.get(Calendar.DAY_OF_MONTH);
	    int hour=calendar.get(Calendar.HOUR_OF_DAY);
	    int minute=calendar.get(Calendar.MINUTE);
	    int seconds=calendar.get(Calendar.SECOND);
	    System.out.println(mDay+" "+mMonth+" "+mYear+" "+hour+":"+minute+":"+seconds);
	}
	
	@Ignore
	public void testFiles() throws IOException {
		File f = new File("/var/lib/magteca/mag", "./50pags");
		System.out.println(f.getCanonicalPath());
	}
	
	@Ignore
	public void testBuildSolrQuery() {
		Map<String, List<String>> data = new HashMap<String, List<String>>();
		data.put("-project", Arrays.asList("[* TO *]"));
		// System.out.println(UtilSolr.buildSolrQuery(data, new HashMap<String, List<String>>(), false, false));
	}
}
