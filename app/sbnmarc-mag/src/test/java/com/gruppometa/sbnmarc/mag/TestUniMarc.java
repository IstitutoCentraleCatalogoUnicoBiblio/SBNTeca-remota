package com.gruppometa.sbnmarc.mag;

import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.StringReader;

/**
 */
public class TestUniMarc {

    //@Test
    public void testUniClient(){
        MagTransformer magTransformer = new MagTransformer();
        UnimarcClient unimarcClient = new UnimarcClient();

            String identifier = "CFI0345043";
        String mag = null;
        try {
            mag = unimarcClient.getOpacSbn2Mag(magTransformer, identifier);
            System.out.println(mag);
        } catch (MagException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void testReader() throws Exception {
        UnimarcReader reader = new UnimarcReader(
                TestUniMarc.class.getResourceAsStream(
                "/USM2001674-uni.mrc"
                        // TODO uni non va MARC21 si //"/TO0E122864.mrc"
            ),"unimarc2mag"
        );
        System.out.println(reader.next().getId());
    }

    //@Test
    public void test(){

        MagTransformer magTransformer = new MagTransformer();

        /**
         * unimarc da OPAC SBN
         */
        UnimarcClient unimarcClient = new UnimarcClient();
        String mag = null;

        try {
           mag = unimarcClient.getOpacSbn2Mods(
                   //"MUS0001212" // MUS0001212,MUS0035133
                   //"MUS0002804"
                   //"MUS0001212"
                   //"MSM0000069"
                   //"MSM0000424"
                   //"MSM0000722"
                   //"MUS0056776"
                   //"NAP0668034"
                   //"NAP0668034"
                   //"CFIE018416"
                   "TO0E122864"
                   //, true,false
           );
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(mag);
        if(true)
           return;

        /**
         * SBNMARC dal polo
         */
        SbnMarcClient sbnMarcClient = new SbnMarcClient();
        sbnMarcClient.setUsername("user");
        sbnMarcClient.setUrl("https://polo/SbnMarcWeb/SbnMarcTest");

        String mag2 = null;
        try {
            //sbnMarcClient.getResponseAsString(biblioteca,bid,type,version,false);
            mag2 = sbnMarcClient.getSbnMarc2Mods(magTransformer,
                    //"NAP0138823"
                    "NAP0668034"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(mag2);

    }
}
