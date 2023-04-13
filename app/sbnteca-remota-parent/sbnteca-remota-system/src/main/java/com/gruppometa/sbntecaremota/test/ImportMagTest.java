package com.gruppometa.sbntecaremota.test;

import java.io.File;

import org.junit.Test;
import org.w3c.dom.Document;

import com.gruppometa.sbntecaremota.retrieve.MagPersistence;
import com.gruppometa.sbntecaremota.retrieve.MagPersistenceFactory;
import com.gruppometa.sbntecaremota.retrieve.MagPersistenceTypes;

public class ImportMagTest {
	
	@Test
	public void importAllMagTest() {
		// inizializzazioni per indicizzazione
		MagPersistence getMag = MagPersistenceFactory.create(MagPersistenceTypes.FILE);
		File dir = new File("/var/lib/magteca/mag/inserimentoMassivo");
		int i = 1;
		
		for(File child : dir.listFiles()) {
			System.out.println(i + ".");
			Document doc = getMag.openMag(child.getPath());
			i++;
		}
	}

}
