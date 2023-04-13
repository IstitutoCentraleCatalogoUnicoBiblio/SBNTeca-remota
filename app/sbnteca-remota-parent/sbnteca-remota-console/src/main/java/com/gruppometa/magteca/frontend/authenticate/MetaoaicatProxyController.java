package com.gruppometa.magteca.frontend.authenticate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.mvc.ProxyExchange;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@RestController
public class MetaoaicatProxyController {

    protected static final Logger logger = LoggerFactory.getLogger(MetaoaicatProxyController.class);

    @Value("${configs.baseRestMetaOaicatInternal}")
    protected String metaoaicatUrl = "http://metaoaicat:9000/metaoaicat/rest/";

    @GetMapping(value = "metaoaicat/rest/{param}")
    public ResponseEntity<?>
    getGetProxyMetaOaicat(ProxyExchange<Object> proxy,
                          @RequestParam MultiValueMap<String,String> allParams,
                          @PathVariable("param") String param) throws IOException {
        UriComponents uriComponents =  UriComponentsBuilder
                .fromHttpUrl(metaoaicatUrl+param)
                .queryParams(allParams).build();
//        logger.info("Url "+ uriComponents.toUri().toString());
//        logger.info(proxy
//                .uri(uriComponents.toUri().toString())
//                .get().getStatusCode().getReasonPhrase());
        return proxy
                .uri(uriComponents.toUriString())
                .get();
    }

    @GetMapping(value = "metaoaicat/rest/{param1}/{param2}")
    public ResponseEntity<?>
    getGetProxyMetaOaicat2(ProxyExchange<Object> proxy,
                          @RequestParam MultiValueMap<String,String> allParams,
                          @PathVariable("param1") String param1,
                           @PathVariable("param2") String param2
                           ) throws IOException {
        UriComponents uriComponents =  UriComponentsBuilder.fromHttpUrl(
                metaoaicatUrl+param1+"/"+param2).queryParams(allParams).build();
        //logger.info("Url "+ uriComponents.toUri());
        return proxy
                .uri(uriComponents.toUri().toString())
                .get();
    }

    @PostMapping(value = "metaoaicat/rest/{param}")
    public ResponseEntity<?>
    getGetProxyMetaOaicatPost(ProxyExchange<Object> proxy,
                          @PathVariable("param") String param) throws IOException {
        return proxy
                .uri(metaoaicatUrl+param)
                .post();
    }

    @DeleteMapping(value = "metaoaicat/rest/{param1}/{param2}")
    public ResponseEntity<?>
    getGetProxyMetaOaicatDelete(ProxyExchange<Object> proxy,
                              @PathVariable("param1") String param1,
                                @PathVariable("param2") String param2
                                ) throws IOException {
        return proxy
                .uri(metaoaicatUrl+param1+"/"+param2)
                .delete();
    }

}
