package com.gruppometa.culturaitalia.admin.objects;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObjectFactory implements ServletContextListener {
    static Logger logger = LoggerFactory.getLogger(ObjectFactory.class);
    static Configuration configConfiguration = null;
    static SessionFactory configFac=null;
    protected static Configuration createConfigConfiguration() {
        if(configConfiguration==null)
            configConfiguration = new Configuration().configure("AdminDataDao.cfg.xml");
        return configConfiguration;
    }
    public static synchronized SessionFactory createConfigSessionFactory(){
        if(configFac==null)
            configFac = createConfigConfiguration().buildSessionFactory();
        return configFac;
    }

    public static void close(){
        if(configFac!=null)
            configFac.close();
    }

    public static Geoname getGeoname(String name) {
        SessionFactory fac = createConfigSessionFactory();
        StatelessSession sess = fac.openStatelessSession();
        Transaction tra = sess.beginTransaction();
        // field_007 = ADM3 comune
        @SuppressWarnings("unchecked")
        List<Geoname> list = (List<Geoname>)sess.createQuery(" from Geoname where " +
                        "(name = ? or asciiname=?) " +
                        // bug: 11-02-2013: deve essere comune
                        "and (tipo ='ADM3' or tipo='PPLA' or tipo='PPLC')")
                .setString(0, name)
                .setString(1, name)
                .setMaxResults(1)
                .list();
        tra.commit();
        sess.close();
        return (list!=null&&list.size()==1)?list.get(0):null;
    }

    public static List<OaiSet> getOaiSets() {
        return getOaiSets(null);
    }
    public static List<OaiSet> getOaiSets(String servletName) {
        SessionFactory fac = createConfigSessionFactory();
        Session sess = fac.getCurrentSession();
        Transaction tra = sess.beginTransaction();
        @SuppressWarnings("unchecked")
        List<OaiSet> props = null;
        if(servletName==null)
            props = sess.createQuery(" from OaiSet").list();
        else
            props = sess.createQuery(" from OaiSet where servletName=?")
                    .setString(0, servletName)
                    .list();
        tra.commit();
        return props;
    }
    public static OaiSet getOaiSet(int id) {
        SessionFactory fac = createConfigSessionFactory();
        Session sess = fac.getCurrentSession();
        Transaction tra = sess.beginTransaction();
        OaiSet props = (OaiSet)sess.createQuery(" from OaiSet where id=?")
                .setInteger(0, id)
                .uniqueResult();
        tra.commit();
        return props;
    }
    public static OaiSet getOaiSet(String spec, String servletName) {
        SessionFactory fac = createConfigSessionFactory();
        Session sess = fac.getCurrentSession();
        Transaction tra = sess.beginTransaction();
        OaiSet props = (OaiSet)sess.createQuery(" from OaiSet where spec=? AND servletName=?")
                .setString(0, spec)
                .setString(1, servletName)
                .uniqueResult();
        tra.commit();
        return props;
    }

    public static List<Object> getList(String string) {
        SessionFactory fac = createConfigSessionFactory();
        Session sess = fac.getCurrentSession();
        Transaction tra = sess.beginTransaction();
        @SuppressWarnings("unchecked")
        List<Object> props = sess.createQuery(" from "+string)
                .list();
        tra.commit();
        return props;
    }

    public static void deleteObject(Object object) {
        SessionFactory fac = createConfigSessionFactory();
        Session sess = fac.getCurrentSession();
        Transaction tra = sess.beginTransaction();
        sess.delete(object);
        tra.commit();
    }
    public static void updateObject(Object object) {
        SessionFactory fac = createConfigSessionFactory();
        Session sess = fac.getCurrentSession();
        sess.setFlushMode(FlushMode.AUTO);
        Transaction tra = sess.beginTransaction();
        sess.update(object);
        sess.flush();
        tra.commit();

    }
    public static void saveObject(Object object) {
        SessionFactory fac = createConfigSessionFactory();
        Session sess = fac.getCurrentSession();
        sess.setFlushMode(FlushMode.AUTO);
        Transaction tra = sess.beginTransaction();
        sess.save(object);
        sess.flush();
        tra.commit();

    }

    public static OaicatConfiguration getOaicatConfiguration(String servletName) {
        SessionFactory fac = createConfigSessionFactory();
        Session sess = fac.getCurrentSession();
        Transaction tra = sess.beginTransaction();
        OaicatConfiguration item = (OaicatConfiguration)sess.createQuery(" from OaicatConfiguration where servletName = ?")
                .setString(0, servletName)
                .uniqueResult();
        tra.commit();
        return item;
    }
}
