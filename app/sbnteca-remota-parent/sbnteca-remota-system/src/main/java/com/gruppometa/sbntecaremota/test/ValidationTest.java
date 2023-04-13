package com.gruppometa.sbntecaremota.test;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.w3c.dom.Document;

import com.gruppometa.sbntecaremota.objects.validators.GenericStruValidator;
import com.gruppometa.sbntecaremota.objects.validators.MagExistenceValidator;
import com.gruppometa.sbntecaremota.objects.validators.ResourceListValidator;
import com.gruppometa.sbntecaremota.objects.validators.SequenceNumberValidator;
import com.gruppometa.sbntecaremota.objects.validators.SerialMagValidator;
import com.gruppometa.sbntecaremota.objects.validators.ValidationError;
import com.gruppometa.sbntecaremota.objects.validators.ValidationResult;
import com.gruppometa.sbntecaremota.objects.validators.Validator;
import com.gruppometa.sbntecaremota.objects.validators.XsdValidator;
import com.gruppometa.sbntecaremota.retrieve.FileMagPersistence;
import com.gruppometa.sbntecaremota.retrieve.MagPersistence;
import com.gruppometa.sbntecaremota.util.ContentStatic;

// test per i validatori
public class ValidationTest {

	@Test
	public void testValidators() {
		// inizializzazione
		String path = "/var/lib/magteca/mag/test_3/mag.xml";
		MagPersistence magPersistence = new FileMagPersistence();
		Document doc = magPersistence.openMag(path);
		ContentStatic.loadConfiguration("config.properties");
		
		
		// validatori
		List<Validator> validators = Arrays.asList(new Validator[] {  new MagExistenceValidator(),
				new XsdValidator(), new SerialMagValidator(), new SequenceNumberValidator(), 
				new GenericStruValidator(), new ResourceListValidator()
		});
		
		// lancio e print su schermo
		for(Validator v : validators) {
			ValidationResult result = v.validate(magPersistence, path, doc, ContentStatic.getProperties());
			
			for(ValidationError err : result.getErrors())
				System.out.println(err.getStatus() + ": " + err.getMessage());
		}
	}

}
