package com.gruppometa.sbntecaremota.mets;

import com.gruppometa.data.mets.Mets;
import com.gruppometa.data.mods.Mods;
import com.gruppometa.mets2mag.MetsConvertor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
@Consumes(MediaType.APPLICATION_JSON)
public class MetsProvider implements MessageBodyReader {

    protected MetsConvertor metsConvertor = new MetsConvertor();

    protected Logger logger = LoggerFactory.getLogger(MetsProvider.class);

    @Override
    public boolean isReadable(Class aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        //logger.info(aClass.getCanonicalName());
        return aClass.equals(Mets.class);
    }

    @Override
    public Object readFrom(Class aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap multivaluedMap, InputStream inputStream) throws IOException, WebApplicationException {
        try {
            //if(aClass.equals(Mets.class))
                return metsConvertor.convertJson2Mets(inputStream);
        } catch (JAXBException ex) {
            throw new RuntimeException(ex);
        }
    }
}
