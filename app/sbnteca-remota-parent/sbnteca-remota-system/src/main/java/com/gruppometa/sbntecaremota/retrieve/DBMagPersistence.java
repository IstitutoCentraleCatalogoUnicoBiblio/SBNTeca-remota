package com.gruppometa.sbntecaremota.retrieve;

import java.util.List;
import java.util.Properties;

import org.w3c.dom.Document;

import com.gruppometa.sbntecaremota.objects.validators.ValidationError;

public class DBMagPersistence extends AbstractMagPersistence {

	@Override
	public Document openMag(String id) {
		return null;
	}

	@Override
	public List<ValidationError> getOpeningErrors() {
		return null;
	}

	@Override
	public Document openMetsAsMag(String id, Properties configuration) {
		return null;
	}

}
