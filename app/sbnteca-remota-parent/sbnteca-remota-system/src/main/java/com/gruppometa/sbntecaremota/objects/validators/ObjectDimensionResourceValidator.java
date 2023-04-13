package com.gruppometa.sbntecaremota.objects.validators;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.gruppometa.sbntecaremota.util.ContentStatic;
import com.gruppometa.sbntecaremota.util.UtilXML;

import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;

public class ObjectDimensionResourceValidator implements ResourceValidator {

	@Override
	public List<ValidationError> validate(String resourcePath, 
			Element resource, List<Element> groups,
			Document currentMag, Properties configuration) {
		

		// flag gestione errore, default error
		String flagError = null;
		
		if(configuration.containsKey("Validator.DimensionErrorFlag"))
			flagError = configuration.getProperty("Validator.DimensionErrorFlag");
		
		else
			flagError = ValidationError.ERROR;
		
		if(ValidationError.OK.equals(flagError))
			return new ArrayList<ValidationError>();
		
		try {
			List<ValidationError> errors = new ArrayList<ValidationError>();
				File file = new File(resourcePath);
			
			// confronto dimensioni immagine
			if("img".equals(resource.getLocalName()) || "altimg".equals(resource.getLocalName())) {
				try {
					ImageIO.setUseCache(false);
					BufferedImage image = ImageIO.read(file);
					int width = image.getWidth();
					int height = image.getHeight();
					
					NodeList imgMagWidthNode = ((Element) resource).
							getElementsByTagNameNS("http://www.niso.org/pdfs/DataDict.pdf", "imagewidth");
					
					NodeList imgMagHeightNode = ((Element) resource).
							getElementsByTagNameNS("http://www.niso.org/pdfs/DataDict.pdf", "imagelength");
					
					boolean noValuesFound = imgMagWidthNode.getLength() > 0
							 && Integer.parseInt(imgMagWidthNode.item(0).getTextContent())==2000 &&
								imgMagHeightNode.getLength() > 0 && Integer.parseInt(imgMagHeightNode.item(0).getTextContent())==2000;
					if(imgMagWidthNode.getLength() > 0) {
						int imgMagWidth = Integer.parseInt(imgMagWidthNode.item(0).getTextContent());
						
						if(imgMagWidth != width) {
							if(noValuesFound)
								errors.add(new ValidationError(flagError, "Larghezza dell'immagine " +
										" '" +
										LabelMapper.getFilename(resourcePath) + "' non specificata"));
							else
								errors.add(new ValidationError(flagError, "Larghezza dell'immagine " +
									imgMagWidth + "!=" + width + " '" +
									LabelMapper.getFilename(resourcePath) + "' specificata non corretta"));
						}
					}
					
					if(imgMagHeightNode.getLength() > 0) {
						int imgMagHeight = Integer.parseInt(imgMagHeightNode.item(0).getTextContent());
						
						if(imgMagHeight != height) {
							if(noValuesFound)
								errors.add(new ValidationError(flagError, "Altezza dell'immagine " +
										" '" +
										LabelMapper.getFilename(resourcePath) + "' non specificata"));
							else
								errors.add(new ValidationError(flagError, "Altezza dell'immagine " +
									imgMagHeight + "!=" + height +
									" '" + LabelMapper.getFilename(resourcePath) + "' specificata non corretta"));
						}
					}
					
				} catch(Exception e) {
					errors.add(new ValidationError(ValidationError.ERROR, "Lettura immagine '" + LabelMapper.getFilename(resourcePath) + "' non riuscita: " + e.getMessage()));
				}
			}
			
			// confronto durata media
			else if("proxies".equals(resource.getLocalName())) {
				FFprobe ffprobe = new FFprobe(ContentStatic.getProperties().getProperty("ffmpegCommand"));
				FFmpegProbeResult probeResult = ffprobe.probe(file.getPath());
				double seconds = probeResult.getFormat().duration;
				NodeList durationNode = ((Element) resource).getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "duration");
				
				if(durationNode.getLength() > 0) {
					String duration = durationNode.item(0).getTextContent();
					String[] durationParts = duration.split("\\:", 3);
					
					if(durationParts.length != 3)
						return Arrays.asList(new ValidationError(flagError, "Formato durata del file '" + LabelMapper.getFilename(resourcePath) + "' specificato non corretto"));
					
					int hourDuration = Integer.parseInt(durationParts[0]);
					int minuteDuration = Integer.parseInt(durationParts[1]);
					int secondsDuration = (int) Math.floor(Double.parseDouble(durationParts[2]));
					
					int totalDurationSeconds = secondsDuration + (minuteDuration * 60) + (hourDuration * 60 * 60);
					
					if(totalDurationSeconds != (int) Math.floor(seconds))
						errors.add(new ValidationError(flagError, "Durata del file '" + LabelMapper.getFilename(resourcePath) + "' specificata non corretta"));
				}
			}
			
			// confronto tra le dimensioni reali del file e la dimensione specificata nel MAG
			List<Element> node = UtilXML.searchInResource(resource, "http://www.iccu.sbn.it/metaAG1.pdf", "filesize");
			
			if(!node.isEmpty() && !ValidationError.OK.equals(flagError)) {
				if(file.length() != Long.parseLong(node.get(0).getTextContent().trim()))
					errors.add(new ValidationError(flagError, "Dimensione del file '" + LabelMapper.getFilename(resourcePath) + "' specificata non corretta"));
			}
			
			return errors;
			
		} catch(NumberFormatException e) {
			return Arrays.asList(new ValidationError(flagError, "Dimensioni del file " + LabelMapper.getFilename(resourcePath) + " non corrette"));
		} catch (IOException e) {
			return Arrays.asList(new ValidationError(flagError, "Problema di lettura player"));
		}
	}

}
