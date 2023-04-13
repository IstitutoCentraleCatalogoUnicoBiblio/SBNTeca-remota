package com.gruppometa.sbntecaremota.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

/**
 * Tale classe si occupa del prelievo delle informazioni di base sul consumo del disco
 *
 *
 */
public class DF {
	public static final long DF_INTERVAL_DEFAULT = 3 * 1000; // default DF refresh interval 
	private String  dirPath;
	private long    dfInterval;	// DF refresh interval in msec
	private long    lastDF;		// last time doDF() was performed
	
	private String filesystem;
	private long capacity;
	private long used;
	private long available;
	private int percentUsed;
	private String mount;
	
	/**
	 * Calcola lo spazio su disco
	 * 
	 * @param path percorso da controllare
	 * @param dfInterval parametro interval
	 * @throws IOException 
	 */
	public DF(File path, long dfInterval) throws IOException {
	    this.dirPath = path.getCanonicalPath();
	    this.dfInterval = dfInterval;
	    lastDF = (dfInterval < 0) ? 0 : -dfInterval;
	    this.doDF();
	}
  
	/**
	 * Calcolo spazio libero su disco fisso (relativo a partizione)
	 * 
	 * @throws IOException
	 */
	private void doDF() throws IOException { 
		if(System.getProperty("os.name").toLowerCase().contains("windows")) {
			File dir = new File(dirPath);
		    this.available = dir.getUsableSpace();
		    this.lastDF = System.currentTimeMillis();
		}
		
		else {
		    if (lastDF + dfInterval > System.currentTimeMillis())
		    	return;
		   
		    Process process;
		    process = Runtime.getRuntime().exec(getExecString());
		
		    try {
		    	if (process.waitFor() != 0)
		    		throw new IOException(new BufferedReader(new InputStreamReader(process.getErrorStream())).readLine());
		      
		    	BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		    	parseExecResult(reader);
		    	reader.close();
		    
		    } catch (InterruptedException e) {
		    	throw new IOException(e.toString());
		    } finally {
		    	process.destroy();
		    }
		}
	}

	/// ACCESSORS

	public String getDirPath() {
		return dirPath;
	}
  
	public String getFilesystem() throws IOException { 
		doDF(); 
		return filesystem; 
	}
  
	public long getCapacity() throws IOException { 
		doDF(); 
		return capacity; 
	}	
  
	public long getUsed() throws IOException { 
		doDF(); 
		return used;
	}
  
	public long getAvailable() throws IOException { 
		doDF(); 
		return available;
	}
  
	public int getPercentUsed() throws IOException {
		doDF();
		return percentUsed;
	}

	public String getMount() throws IOException {
		doDF();
		return mount;
	}
  
	public long getCapacitySkipRefresh() { 
		return capacity; 
	}
  
	public long getUsedSkipRefresh() { 
		return used;
	}
  
	public long getAvailableSkipRefresh() { 
		return available;
	}
  
	public int getPercentUsedSkipRefresh() {
		return percentUsed;
 	}
  
	public String toString() {
		return "df -k " + mount +"\n" +
				filesystem + "\t" +
				capacity / 1024 + "\t" +
				used / 1024 + "\t" +
				available / 1024 + "\t" +
				percentUsed + "%\t" +
				mount;
	}

	private String[] getExecString() {
		return new String[] {"df","-k", dirPath};
	}
  
	private void parseExecResult(BufferedReader lines) throws IOException {
		lines.readLine();                         // skip headings
		StringTokenizer tokens = new StringTokenizer(lines.readLine(), " \t\n\r\f%");
		this.filesystem = tokens.nextToken();
		
		if (!tokens.hasMoreTokens()) {            // for long filesystem name
			tokens = new StringTokenizer(lines.readLine(), " \t\n\r\f%");
		}
		
	    this.capacity = Long.parseLong(tokens.nextToken()) * 1024;
	    this.used = Long.parseLong(tokens.nextToken()) * 1024;
	    this.available = Long.parseLong(tokens.nextToken()) * 1024;
	    this.percentUsed = Integer.parseInt(tokens.nextToken());
	    this.mount = tokens.nextToken();
	    this.lastDF = System.currentTimeMillis();
	}

	public static void main(String[] args) throws Exception {
		String path = ".";
		
		if (args.length > 0)
			path = args[0];

		System.out.println(new DF(new File(path), DF_INTERVAL_DEFAULT).toString());
	}

}
