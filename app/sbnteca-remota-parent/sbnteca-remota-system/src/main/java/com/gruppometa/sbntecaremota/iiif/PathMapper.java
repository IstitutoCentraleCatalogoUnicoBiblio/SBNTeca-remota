package com.gruppometa.sbntecaremota.iiif;


import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class PathMapper {

    public static String TDI_PREFIX = "oai:www.internetculturale.sbn.it/Teca:20:NT0000:";

    public boolean isTdi(String id){
        if(id==null)
            return false;
        return id.startsWith(TDI_PREFIX);
    }

    public String getResourceStreamUrl(String teca, String id, String tecaUrl, String tdi){
        return "digitalObject/"+id;
    }

    public String makeIiifUrlPart(String id, String type, String teca, String tdi, String tecaUrl) {
        return id;
    }

    public static String getDirectoryFromOaiId(String oaiid){
        if(oaiid==null)
            return null;
        String ret = encode(oaiid);
        String str2 = ret; //era .replace("_","");
        if(str2.length()>4)
            return ret.substring(str2.length()-2,str2.length())+"/"+ret.substring(str2.length()-4,str2.length()-2)+"/"+ret;
        else
            return "00/00/"+ret;
    }


    public static String getRealPathFromPath(String path){
        String ret = decode(path);
        return ret;
    }

    public static String encode(String str){
        if(str==null)
            return null;
        return Base64.getEncoder().encodeToString(str.getBytes()).replace("=","_");
    }

    public static String decode(String path){
        if(path == null)
            return null;
        String ret = new String( Base64.getDecoder().decode(path.replace("_","=")));
        return ret;
    }

    public boolean isValidUtf8(String string){
        try
        {
            byte[] myBytes = string.getBytes("UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            return false;
        }
        return true;
    }

    public static String decodeSafe(String path){
        if(path == null)
            return null;
        if(path.equals("marciana"))
            return path;
        try {
            String ret = new String(Base64.getDecoder().decode(path.replace("_", "=")));
            return ret;
        }
        catch (Exception e){}
        return path;
    }

    public String stripTdiPrefix(String idReal) {
        if(idReal==null)
            return idReal;
        if(idReal.startsWith(TDI_PREFIX))
            return idReal.substring(TDI_PREFIX.length());
        return idReal;
    }
}
