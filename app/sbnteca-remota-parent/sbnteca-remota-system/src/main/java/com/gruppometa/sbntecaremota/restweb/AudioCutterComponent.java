package com.gruppometa.sbntecaremota.restweb;

import com.gruppometa.sbntecaremota.objects.DataResourceDelivery;
import com.gruppometa.sbntecaremota.util.ContentStatic;
import inet.ipaddr.IPAddressString;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.builder.FFmpegOutputBuilder;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;

@Component
public class AudioCutterComponent {

    public static final String SUFFIX = "_cut";
    public static final String LABEL_SUFFIX = " (preview)";
    @Value("${audio.fullview.ips}")
    protected String addresses;

    @Value("${audio.internal.ips}")
    protected String addressesInternal;

    @Value("${audio.cutter.active:false}")
    protected boolean active;

    @Value("${audio.cutter.usages:}")
    protected String usages;

    @Value("${audio.ffmpeg.debug:false}")
    protected boolean debug;

    @Value("${audio.ffmpeg.fade:false}")
    protected boolean fade;

    @Value("${audio.ffmpeg.cut.seconds:30}")
    protected int cutSeconds;

    @Value("${audio.ffmpeg.cut.start:0}")
    protected int cutStart;

    @Value("${audio.ffmpeg.fade-seconds:2}")
    protected int fadeSeconds;

    protected List<IPAddressString> ipAddressStrings = new ArrayList<>();

    protected static final Logger logger = LoggerFactory.getLogger(AudioCutterComponent.class);

    @PostConstruct
    protected void postConstruct(){
        if(addresses!=null) {
            String[] ips = addresses.split(",| ");
            for(String ip : ips)
                if(hasText(ip))
                    ipAddressStrings.add(new IPAddressString(ip));
        }
        if(addressesInternal!=null) {
            String[] ips = addressesInternal.split(",| ");
            for(String ip : ips)
                if(hasText(ip))
                    ipAddressStrings.add(new IPAddressString(ip));
        }
    }

    public void makeAudioCut(String inputFilename, String outputFilename){
        try{
            long now = System.currentTimeMillis();
            String command = "/usr/bin/ffmpeg";
            FFmpeg ffmpeg = new FFmpeg(ContentStatic.getProperties()!=null?
                    ContentStatic.getProperties().getProperty("ffmpegFFmpegCommand"):
                    command);
            FFmpegExecutor executor = new FFmpegExecutor(ffmpeg);
            FFmpegBuilder builder = new FFmpegBuilder();
            if(fade)
                builder.setAudioFilter("afade=t=out:st="+(cutSeconds-fadeSeconds)+":d="+fadeSeconds);
            FFmpegOutputBuilder fFmpegOutputBuilder = builder
                    .setInput(inputFilename)
                    .addOutput(outputFilename);
            if(!fade)
                fFmpegOutputBuilder.setAudioCodec("copy");
            builder = fFmpegOutputBuilder.done();
            int margin = 0;
            builder.addExtraArgs("-t", String.valueOf(cutSeconds+margin));
            if(cutStart>0)
                builder.addExtraArgs("-ss", String.valueOf( cutStart ));
            if(debug){
                builder.addExtraArgs("-v", "8");
            }
            executor.createJob(builder).run();
            logger.info("Created audio cut for "+inputFilename + " ("+outputFilename+") in "+(System.currentTimeMillis()-now)+"ms.");
        } catch (IOException e) {
            logger.error("", e);
        }

    }
    public Response getAudioPreview(DataResourceDelivery data){
        try {
            if(data.getStream()!=null)
                data.getStream().close();
        } catch (IOException e) {
            logger.error("", e);
        }
        String inputFile = data.getFileName();
        String contentType = data.getContentType();
        try {
            long now = System.currentTimeMillis();
            FFmpeg ffmpeg = new FFmpeg(ContentStatic.getProperties().getProperty("ffmpegFFmpegCommand"));
            FFmpegExecutor executor = new FFmpegExecutor(ffmpeg);
            File tempFile = File.createTempFile("ffmpeg",getFileExtensionFromContentType(contentType));
            java.nio.file.Path tempFilePath = Paths.get(tempFile.getAbsolutePath());
            FFmpegBuilder builder = new FFmpegBuilder();
            int cut = Integer.parseInt(ContentStatic.getProperties().getProperty("audioPreviewCutInSeconds"));
            if(fade)
                builder.setAudioFilter("afade=t=out:st="+(cut-fadeSeconds)+":d="+fadeSeconds);
            FFmpegOutputBuilder fFmpegOutputBuilder = builder
                    .setInput(inputFile)
                    .addOutput(tempFile.getAbsolutePath());
            if(!fade)
            fFmpegOutputBuilder.setAudioCodec("copy");
            builder = fFmpegOutputBuilder.done();
            int margin = 0;
            builder.addExtraArgs("-t", String.valueOf(cut+margin));
            if(cutStart>0)
                builder.addExtraArgs("-ss", String.valueOf( cutStart ));
            if(debug){
                builder.addExtraArgs("-v", "8");
            }
            executor.createJob(builder).run();
            logger.info("Created audio cut for "+data.getResourceName()+ " in "+(System.currentTimeMillis()-now)+"ms.");
            byte[] bytes = Files.readAllBytes(tempFilePath);
            Files.deleteIfExists(tempFilePath);
            String disposition = contentType.startsWith("audio") || contentType.startsWith("video") ? "attachment" : "inline";
            return Response.ok(new ByteArrayInputStream(bytes)).
                    header("Content-Type", contentType).
                    header("Content-Length", bytes.length).
                    header("Content-Transfer-Encoding", "binary").
                    header("Content-Disposition", disposition + "; filename=\"" + makeResponseExtension(data.getResourceName()) + "\"").
                    header("Last-Modified", data.getLastModified())
                    .build();
        } catch (IOException e) {
            logger.error("Errore: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    public static String makeResponseExtension(String resourceName) {
        if(resourceName!=null && resourceName.endsWith(".mpg")){
            return resourceName.substring(0, resourceName.length()-4)+".mp3";
        }
        return resourceName;
    }

    protected static String getFileExtensionFromContentType(String contentType){
        MimeTypes mimeTypes = MimeTypes.getDefaultMimeTypes();
        MimeType mimeType = null;
        try {
            mimeType = mimeTypes.forName(contentType);
        } catch (MimeTypeException e) {
        }
        if(mimeType!=null)
            return mimeType.getExtension().equalsIgnoreCase(".mpga")?".mp3":mimeType.getExtension();
        else if(contentType.contains("mpeg"))
            return ".mp3";
        else
            return ".mp3";
    }


    public boolean isAudio4Cut(DataResourceDelivery data, String mode, HttpServletRequest request, String thumbCall) {
        if(!active)
            return false;
        if(data==null || !"audio".equals(getResourceTypeByContentType(data.getContentType()))
                || thumbCall!=null // anche essi sono preview
                || "preview".equalsIgnoreCase(mode)) // preview per audio è un immagine)
            return false;
        if(StringUtils.hasText(usages) && data.getUsage()!=null && !usages.contains(data.getUsage()))
            return false;
        logger.debug("Remote IP = " +request.getRemoteAddr()+", X-Forwarded-For:"+ request.getHeader("X-Forwarded-For"));
        if(!containsIp(request.getHeader("X-Forwarded-For"), ipAddressStrings) && request.getHeader("X-Forwarded-For")!=null)
            return true;
        if(request.getHeader("X-Forwarded-For")==null && !containsIp(request.getRemoteAddr(), ipAddressStrings))
            return true;
        return "cut".equalsIgnoreCase(mode);
    }

    protected static boolean containsIp(String ip, List<IPAddressString> ipAddressStrings) {
        IPAddressString ipAddressString = new IPAddressString(ip);
        for(IPAddressString ipAddressString1: ipAddressStrings)
            if(ipAddressString1.contains(ipAddressString))
                return true;
        return false;
    }

    /**
     * Restituisce il tipo di risorsa digitale dato il suo content type
     *
     * @param contentType content type
     * @return String tipo di risorsa digitale (IMG, AUDIO, VIDEO, DOC)
     */
    public static String getResourceTypeByContentType(String contentType) {
        String[] parts = contentType.split("\\/");

        if(parts.length == 0 || "image".equalsIgnoreCase(parts[0]))
            return "img";

        else if("audio".equalsIgnoreCase(parts[0]) || "video".equalsIgnoreCase(parts[0]))
            return parts[0];

        else
            return "doc";
    }

}
