package com.gruppometa.culturaitalia.skos;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObjectFactory {
	protected static Logger logger = LoggerFactory.getLogger(ObjectFactory.class);	
	static Configuration semConfiguration = null;
	static SessionFactory semFac=null;
	
	protected static Configuration createSemConfiguration() {
		if(semConfiguration==null)
			semConfiguration = new Configuration().configure("SkosDataDao.cfg.xml");
		return semConfiguration;
	}
	
	public static SessionFactory createSemSessionFactory(){
		if(semFac==null)
			semFac = createSemConfiguration().buildSessionFactory();
		return semFac;
	}
	
	public void test(){
		SessionFactory fac = createSemSessionFactory();
		Session sess =	fac.getCurrentSession();
		Transaction tra = sess.beginTransaction();
		//Concept c = new Concept("root","root", "it", "dewey");
		//Relation r = new Relation();
		//r.setName("hasPart");
		//sess.save(r);
		tra.commit();
	}
	
	public static List<SkosItem> getChildren(SkosItem concept){
		return getChildren(concept,1000000,concept.getThesaurusVersion(), true);
	}
	
	@SuppressWarnings("unchecked")
	public static List<SkosItem> getChildren(SkosItem concept, int revision, String versionSkos, boolean withUpdated){
		SessionFactory fac = createSemSessionFactory();
		Session sess =	fac.getCurrentSession();
		Transaction tra = sess.beginTransaction();
		
		List<SkosItem> list =  (List<SkosItem>)sess.createQuery(
				/*"from Concept where id in " +
				"(select c.conceptB.id from Connection c where c.relation.id = 1" +
				" and c.conceptA.id =?) order by name"*/
				"from SkosItem c" +
				" where c.broader=? "+
				" and thesaurusVersion = ?"+
				" and revision in (Select max(revision) from " +
				" 			SkosItem c2 where c2.name=c.name and revision <=? " +
				"           and c.thesaurusVersion = c2.thesaurusVersion " +
				"           group by c2.name) "+
				(!withUpdated?" and version<=thesaurusVersion ":
					" and c.version in  (Select max(c3.version) from " +
				" 			SkosItem c3 where " +
				"           c3.key=c.key " +
				"            and c.thesaurusVersion = c3.thesaurusVersion " +
				"           group by c3.key)" +
				" order by c.key")
				)
				.setString(0, concept.getName())
				.setString(1, versionSkos)
				.setInteger(2, revision)
				.list();
		tra.commit();
		/**
		for (int i=list.size()-1; i>=0; i--) {
			if(list.get(i).getRemoved()!=null && list.get(i).getRemoved().equals("true"))
				list.remove(i);
		}
		**/
		HashMap<String, String> related = new HashMap<String, String>();
		for (SkosItem skosItem : list) {
			if(skosItem.getRelatedMatch()!=null)
				related.put(skosItem.getRelatedMatch(), skosItem.getRelatedMatch());
		}
		for (int i=list.size()-1; i>=0; i--) {
			if(related.get(list.get(i).getName())!=null){
				list.remove(i);
			}
		}
		return list;
	}
	
	public static boolean hasChildren(SkosItem concept){
		SessionFactory fac = createSemSessionFactory();
		Session sess =	fac.getCurrentSession();
		Transaction tra = sess.beginTransaction();
		
		Long list =  (Long)sess.createQuery(
				/*"from Concept where id in " +
				"(select c.conceptB.id from Connection c where c.relation.id = 1" +
				" and c.conceptA.id =?) order by name"*/
				"select count(*) from SkosItem c" +
				" where c.broader=?" 
				)
				.setString(0, concept.getName()).uniqueResult();
		tra.commit();
		return list>0;
	}
	public static SkosItem getSkosItem(String name){
		
		return getSkosItem(name,1000000,"4.3",getVersionFromUri(name));
	}
	
	public static SkosItem getSkosItem(String name, int revision, String thesaurusVersion, String version){
		SessionFactory fac = createSemSessionFactory();
		Session sess =	fac.getCurrentSession();
		Transaction tra = sess.beginTransaction();		
		SkosItem item = (SkosItem)sess.createQuery(				
				" FROM SkosItem c" +
				" WHERE c.name=?" +
				" AND c.thesaurusVersion = ?" +
				" AND c.version = ?  AND revision in (Select max(revision) from " +
				" SkosItem c2 where c2.name=c.name and revision <=? " +
				" AND c2.thesaurusVersion = ? AND c2.version = ? " +
				" group by c2.name) "
			)
				.setString(0, name)
				.setString(1, thesaurusVersion)
				.setString(2, version)
				.setInteger(3,revision)
				.setString(4, thesaurusVersion)
				.setString(5, version)
				.uniqueResult();
		tra.commit();
		return item;
	}
	
	@SuppressWarnings("unchecked")
	public static SkosItem getSkosItemByKey(String key, String thesaurusVersion, int revision, String version){
		SessionFactory fac = createSemSessionFactory();
		Session sess =	fac.getCurrentSession();
		Transaction tra = sess.beginTransaction();		
		SkosItem item = null;
		List<SkosItem> list = null;
		if(version ==null){
			list = (List<SkosItem>)sess.createQuery(				
					"from SkosItem c" +
					" where c.key=?" +
					" and c.thesaurusVersion=?" +
					" and c.version  in (Select max(version) from " +
					" SkosItem c2 where c2.key=c.key and " +
					"	c2.version <=?" +
					" group by c2.key)" +
					" order by c.revision desc " +
					"  "
				)
					.setString(0, key)
					.setString(1, thesaurusVersion)
					.setString(2, thesaurusVersion)
					.setMaxResults(1)
					.list();		
		}
		else{
			list = (List<SkosItem>)sess.createQuery(				
				"from SkosItem c" +
				" where c.key=?" +
				" and thesaurusVersion=?" +
				" and revision<=? AND version IN (Select max(version) from " +
				" SkosItem c2 where c2.key=c.key" +
				"  AND c2.version <=?" +
				"  AND c2.revision <=?  " +				
				" group by c2.key) "+
				" order by c.revision desc " 
			)
				.setString(0, key)
				.setString(1, thesaurusVersion)
				.setInteger(2, revision)
				.setString(3, version)
				.setInteger(4, revision)
				.list();
		}
		if(list!=null && list.size()>0)
			item = list.get(0);
		tra.commit();
		return item;
	}

	public static SkosItem getSkosItemById(String id){
		SessionFactory fac = createSemSessionFactory();
		Session sess =	fac.getCurrentSession();
		Transaction tra = sess.beginTransaction();
		
		SkosItem item = (SkosItem)sess.createQuery(
				/*"from Concept where id in " +
				"(select c.conceptB.id from Connection c where c.relation.id = 1" +
				" and c.conceptA.id =?) order by name"*/
				"from SkosItem c" +
				" where c.id=?" 
				)
				.setInteger(0, Integer.parseInt( id )).uniqueResult();
		tra.commit();
		return item;
	}
	
	public static void save(SkosItem concept){
		save(concept,false);
	}
	public static List<Thesaurus> getAllThesauri(){
		return getAllThesauri(false);
	}
	@SuppressWarnings("unchecked")
	public static List<Thesaurus> getAllThesauri(boolean allVariants){	
		SessionFactory fac = createSemSessionFactory();
		Session sess =	fac.getCurrentSession();
		Transaction tra = sess.beginTransaction();
		List<Thesaurus> list2 = null;
		String q = "select distinct concat(concat(ifnull(thesaurusVersion,'4.0'),'.'),revision) as id," +
				"'http://culturaitalia.it/pico/thesaurus/' as namespace, thesaurusVersion as version," +
				" revision from skositem" +
				" order by id";
		@SuppressWarnings("unchecked")
		List<Thesaurus> list = (List<Thesaurus>)
				sess.createSQLQuery(q
						)
						.addEntity(Thesaurus.class)
						.list();
		 q = "select distinct concat(concat(ifnull(version,'4.0'),' '),revision) as id," +
				"'http://culturaitalia.it/pico/thesaurus/' as namespace, version as version," +
				" revision from skositem WHERE version > thesaurusVersion " +
				" order by id";
		if(allVariants)
			list2 = (List<Thesaurus>)
				sess.createSQLQuery(q
						)
						.addEntity(Thesaurus.class)
						.list();
		tra.commit();
		if(allVariants){
			for (Thesaurus thesaurus2 : list2) {
				thesaurus2.setVariant(true);
				boolean found = false;
				String id = thesaurus2.getId().replace(" ", ".");
				for (Thesaurus thesaurus : list) {
					if(thesaurus.getId().equals(id)){
						found = true;
						break;
					}
				}
				if(!found){
					thesaurus2.setId(id);
					list.add(thesaurus2);
				}
			}
			Collections.sort(list, new Comparator<Thesaurus>(){
	
				public int compare(Thesaurus arg0, Thesaurus arg1) {
					return arg0.getId().compareTo(arg1.getId());
				}
				
			});
		}
		return list;
		
	}
	
	public static void save(SkosItem concept,boolean isNew){
		SessionFactory fac = createSemSessionFactory();
		Session sess =	fac.getCurrentSession();
		Transaction tra = sess.beginTransaction();
		if(isNew){
			sess.save(concept);
		}
		else{
			if(concept.getId()==0){					
				@SuppressWarnings("unchecked")
				List<SkosItem> list =  (List<SkosItem>)sess.createQuery(
					/*"from Concept where id in " +
					"(select c.conceptB.id from Connection c where c.relation.id = 1" +
					" and c.conceptA.id =?) order by name"*/
					"from SkosItem c" +
					" where c.name=? and revision=? and version=?" 
					)
					.setString(0, concept.getName())
					.setInteger(1, concept.getRevision())
					.setString(2, concept.getVersion())
					.list();
				if(list.size()>0){
					concept.setId(list.get(0).getId());
					sess.update(concept);
				}
				else
					sess.save(concept);
			}
			else
				sess.update(concept);
		}	
		tra.commit();		
	}
	
	public static List<SkosItem> getSkosItems(String name) {
		SessionFactory fac = createSemSessionFactory();
		Session sess =	fac.getCurrentSession();
		Transaction tra = sess.beginTransaction();
		
		@SuppressWarnings("unchecked")
		List<SkosItem> item = (List<SkosItem>)sess.createQuery(
				"from SkosItem c" +
				" where c.name like ?" 
				)
				.setString(0, name).list();
		tra.commit();
		return item;
	}
	
	@SuppressWarnings("unchecked")	
	public static List<SkosItem> getModifiedItems(String lastVersion, String newVersion) {
		SessionFactory fac = createSemSessionFactory();
		Session sess =	fac.getCurrentSession();
		Transaction tra = sess.beginTransaction();
		List<SkosItem> items = null;
		/**
		 * nuova versione
		 */
		if(getVersion(lastVersion).compareTo(getVersion(newVersion))<0){
			items = (List<SkosItem>)sess.createQuery(
					"from SkosItem c" +
					" where (thesaurusVersion = ? AND revision > ?) "
					+ " OR  (version > ? AND version <=?) "
					)
					.setString(0, getVersion(lastVersion))
					.setInteger(1, getRevision(lastVersion))
					.setString(2, getVersion(lastVersion))
					.setString(3, getVersion(newVersion))
					.list();
		}
		else{
			items = (List<SkosItem>)sess.createQuery(
				"from SkosItem c" +
				" where (thesaurusVersion = ? AND revision > ?) "
				+ " AND  (thesaurusVersion = ? AND revision <= ?) "
				)
				.setString(0, getVersion(lastVersion))
				.setInteger(1, getRevision(lastVersion))
				.setString(2, getVersion(newVersion))
				.setInteger(3, getRevision(newVersion))
				.list();
		}
		tra.commit();
		return items;
	}
	
	public static int getRevision(String version){
		String[] lastVersions = version.split("\\.");
		if(lastVersions.length==3)
			return Integer.parseInt(lastVersions[2]);
		else
			return 0;
	}
	
	public static String getVersion(String version){
		String[] lastVersions = version.split("\\.");
		if(lastVersions.length==3)
			return lastVersions[0]+"."+lastVersions[1];
		else
			return version;
	}
	
	public static String getKeyFromUri(String uri){
		String[] s = uri.split("#");
		if(s.length==2)
			return s[1];
		else
			return null;
	}
	public static String getVersionFromUri(String uri){
		String[] s = uri.split("#");
		if(s.length==2)
			return s[0].substring(s[0].lastIndexOf("/")+1);
		else
			return null;
	}
}
