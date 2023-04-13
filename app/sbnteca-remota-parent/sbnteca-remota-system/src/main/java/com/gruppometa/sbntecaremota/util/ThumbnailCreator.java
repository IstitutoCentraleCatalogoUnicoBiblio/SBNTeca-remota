package com.gruppometa.sbntecaremota.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.ws.rs.core.MediaType;

import org.apache.jcs.JCS;
import org.apache.jcs.access.exception.CacheException;
import org.apache.jcs.engine.behavior.ICacheElement;
import org.apache.jcs.engine.behavior.IElementAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThumbnailCreator implements Serializable {
	private static final long serialVersionUID = 12323L;
	
	/**
	 * connection timeout in ms.
	 */
	private int connectionTimeout = 5000;
	
	/**
	 * cache timeout
	 */
	protected long timeoutInSeconds = 3600;
	
	/**
	 * use cache
	 */
	protected boolean useCache = true;
	
	/**
	 * cache path
	 */
	protected String cachePath;
	
	/**
	 * placeholders
	 */
	protected Map<String, String> placeholders = new HashMap<String, String>();
	
	// logger
	protected static Logger logger = LoggerFactory.getLogger(ThumbnailCreator.class);


	protected String internalUrl;

	public String getInternalUrl() {
		return internalUrl;
	}

	public void setInternalUrl(String internalUrl) {
		this.internalUrl = internalUrl;
	}

	public String getExternalUrl() {
		return externalUrl;
	}

	public void setExternalUrl(String externalUrl) {
		this.externalUrl = externalUrl;
	}

	protected String externalUrl;

	
	public int getConnectionTimeout()  {
		return connectionTimeout;
	}
	
	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}
	
	public long getTimeoutInSeconds() {
		return timeoutInSeconds;
	}
	
	public void setTimeoutInSeconds(long timeoutInSeconds) {
		this.timeoutInSeconds = timeoutInSeconds;
	}
	
	public boolean isUseCache() {
		return useCache;
	}
	
	public void setUseCache(boolean useCache) {
		this.useCache = useCache;
	}
	
	public String getCachePath() {
		return cachePath;
	}
	
	public void setCachePath(String cachePath) {
		this.cachePath = cachePath;
	}
	
	public Map<String, String> getPlaceholders() {
		return placeholders;
	}
	
	public void setPlaceholders(Map<String, String> placeholders) {
		this.placeholders = placeholders;
	}
	
	/**
	 * Creazione/restituzione di una thumbnail
	 * 
	 * @param urlImage URL della risorsa digitale
	 * @param type tipo di risorsa digitale (IMG, AUDIO, VIDEO, OCR, DOC)
	 * @param dimX larghezza (width) immagine
	 * @param dimY altezza (height) immagine
	 * @return InputStream stream della thumbnail
	 * @throws IOException
	 */
	public void clearThumbCache(String urlImage, String type, int dimX, int dimY) {
		String imageFilename = urlImage.trim();
		if (imageFilename.startsWith(externalUrl))
			imageFilename = internalUrl + imageFilename.substring(externalUrl.length());
		String imageReference = imageFilename + "_" + dimX + "_" + dimY;
		JCS cache = null;
		if (useCache) {
			try {
				cache = JCS.getInstance(cachePath);
				cache.remove(imageReference);
			}
			catch (Exception e){

			}
		}
	}
	public InputStream getThumb(String urlImage, String type, int dimX, int dimY, String cacheFlag) throws IOException {
		String imageFilename = urlImage.trim();
		if(imageFilename.startsWith(externalUrl)) {
			imageFilename = internalUrl + imageFilename.substring(externalUrl.length());
			imageFilename = imageFilename + (imageFilename.contains("?")?"&":"?")+"thumb=true";
		}
		String imageReference = imageFilename + "_" + dimX + "_" + dimY;
		JCS cache = null;
		
		if(useCache && !"false".equals(cacheFlag)) {
			try {
				cache = JCS.getInstance(cachePath);
				ICacheElement el = cache.getCacheElement(imageReference);
				
				if (el != null) {
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					Image image = (Image) el.getVal();
					ImageIO.write(image.getImage(), "JPG", baos);
					logger.debug("Image from cache " + imageFilename);
					return new ByteArrayInputStream(baos.toByteArray());
				}
				
			} catch (Exception e) {
				logger.error("", e);
			}
		}
		
		/**
		 * bug: 12-01-2012: sembra che i spazi per IIS non possono essere + ma devono essere %20.
		 */
		if (imageFilename.contains(" "))
			imageFilename = imageFilename.replace(" ", "%20");
		
		logger.debug("Image per " + imageFilename);
		// And get the thumbnail dimensions as request parameters as well.
		double thumbWidth = dimX;
		double thumbHeight = dimY;
		BufferedImage image = null;
		String text = null;
		boolean isOtherContentype = false;
		URLConnection con = null;
		InputStream imageInput = null;
		
		try {
			/**
			 * controllo del content-type
			 */
			URL url = new URL(imageFilename);
			con = url.openConnection();
			con.setReadTimeout(connectionTimeout);
			con.setConnectTimeout(connectionTimeout);
			String contentType = null;
			int code = 200;
			
			/**
			 * check response code
			 */
			if(con instanceof HttpURLConnection) {
				code = ((HttpURLConnection) con).getResponseCode();
				
				if(code != 200) {
					logger.error("Server response: " + code + ", url: " + imageFilename);
					text = "Errore " + code;
				}
			}
			
			/**
			 * se file trovato = 200
			 */
			if(code == 200) {
				contentType = con.getContentType();
				
				if("img".equals(type))
					imageInput = url.openStream();
				
				else {
					isOtherContentype = true;
					
					try {
						URL placeholder = !"doc".equals(type) ? 
								new URL(placeholders.get(type)) : this.getDocPreviewImage(contentType);
						
						imageInput = placeholder.openStream();
						
					} catch (FileNotFoundException e) {
						logger.error("errore per " + imageFilename, e);
						text = "File not found";
					} catch (Exception e) {
						logger.error("errore per " + imageFilename, e);
						text = e.getMessage();
					}
				}
				
				image = ImageIO.read(imageInput);
			}
			
		} catch (FileNotFoundException e) {
			logger.error("errore 'FileNotFoundException' per " + imageFilename);
			text = "File not found";
		} catch (Exception e) {
			logger.error("errore '" + e.getMessage() + "' per " + imageFilename);
			text = e.getMessage();
		} finally {
			if (imageInput != null)
				imageInput.close();
		}
		
		if (image == null && !isOtherContentype) {
			try {
				imageInput = new URL(placeholders.get("error-image")).openStream();

				// Now scale the image using Java 2D API to the desired thumb size.
				image = ImageIO.read(imageInput);
				
			} catch (FileNotFoundException e) {
				logger.error("errore per " + imageFilename, e);
				text = "File not found";
			} catch (Exception e) {
				logger.error("errore per " + imageFilename, e);
				text = e.getMessage();
			} finally {
				if (imageInput != null)
					imageInput.close();
			}
		}

		double thumbWidth2 = 0; 
		double thumbHeight2 = 0;
		
		if(image != null) {
			if(image.getHeight() > image.getWidth()) {
				if(image.getHeight() <= thumbHeight) {
					thumbHeight2 = image.getHeight();
					thumbWidth2 = image.getWidth();
				} 
				
				else {
					thumbWidth2 = thumbHeight * image.getWidth() / image.getHeight();
					thumbHeight2 = thumbHeight;
				}
			} 
			
			else {
				if (image.getWidth() <= thumbWidth) {
					thumbHeight2 = image.getHeight();
					thumbWidth2 = image.getWidth();
				} 
				
				else {
					thumbWidth2 = thumbWidth;
					thumbHeight2 = thumbHeight * image.getHeight() / image.getWidth();
				}
			}
		}

		// thumbHeight = thumbHeight > 0 ? thumbHeight : (thumbWidth * image.getHeight() / image.getWidth());
		// thumbWidth = thumbWidth > 0 ? thumbWidth : (thumbHeight * image.getWidth() / image.getHeight());
		// if(image.getWidth()/image.getHeight()>thumbWidth/thumbHeight)
		// thumbHeight = thumbHeight * (thumbWidth/image.getWidth());
		BufferedImage thumb = new BufferedImage((int) thumbWidth, (int) thumbHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2D = thumb.createGraphics();
		graphics2D.setBackground(Color.WHITE);
		graphics2D.setPaint(Color.WHITE);
		graphics2D.fillRect(0, 0, (int) thumbWidth, (int) thumbHeight);
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		
		if (image != null) {
			graphics2D.drawImage(image, (int) (0 + ((thumbWidth - thumbWidth2) / 2)), 
					(int) (0 + ((thumbHeight - thumbHeight2) / 2)),
					(int) thumbWidth2, (int) thumbHeight2, null);
		} 
		
		else if (text != null && text.equals("File not found")) {
			graphics2D.setColor(Color.red);
			graphics2D.drawString("File non trovato", 2, 15);
		} 
		
		else if (text != null && isOtherContentype) {
			graphics2D.setColor(Color.darkGray);
			graphics2D.drawString(text, 5, 15);
		} 
		
		else if (text != null) {
			graphics2D.setColor(Color.red);
			graphics2D.drawString("Errore", 5, 15);
		}
		

		// Write the image as JPG to the response along with correct content type.
		if (cache != null) {
			try {
				Image img = new Image();
				img.setImage(thumb);
				IElementAttributes attributes = cache.getDefaultElementAttributes();
				attributes.setMaxLifeSeconds(isOtherContentype ? timeoutInSeconds / 6 : timeoutInSeconds);
				cache.put(imageReference, img, attributes);
				
			} catch (CacheException e) {
				logger.error("", e);
			}
		}
	
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(thumb, "JPG", baos);
		return new ByteArrayInputStream(baos.toByteArray());
	}

	/**
	 * Restituisce il path dell'immagine placeholder di preview basato sul MIME type
	 * 
	 * @param mime MIME type
	 * @return URL url dell'immagine placeholder di preview
	 * @throws MalformedURLException 
	 */
	private URL getDocPreviewImage(String mime) throws MalformedURLException {
		if(MediaType.TEXT_PLAIN.equals(mime))
			return new URL(placeholders.get("txt"));
		
		else if("application/pdf".equals(mime))
			return new URL(placeholders.get("pdf"));
		
		else if(mime.contains("html"))
			return new URL(placeholders.get("html"));

		else if(mime.contains("xml"))
			return new URL(placeholders.get("xml"));
		
		else
			return new URL(placeholders.get("doc"));
	}
	
	protected class Image implements Serializable {
		private static final long serialVersionUID = -296105308302959931L;
		
		private long id;
		private String name;
		private BufferedImage image;
		
		public long getId() {
			return id;
		}
		
		public void setId(long id) {
			this.id=id;
		}
		
		public String getName() {
			return name;
		}
		
		public void setName(String str){
			this.name=str;
		}
		
		public BufferedImage getImage() {
			return image;
		}
		
		public void setImage(BufferedImage image) {
			this.image=image;
		}
		
		private void writeObject(java.io.ObjectOutputStream out)throws IOException{
		    out.writeObject(name);
		    ImageIO.write(image,"jpeg",ImageIO.createImageOutputStream(out));
		}
		
		private void readObject(java.io.ObjectInputStream in)throws IOException, ClassNotFoundException{
		    name=(String)in.readObject();
		    image=ImageIO.read(ImageIO.createImageInputStream(in));
		}
	}

}
