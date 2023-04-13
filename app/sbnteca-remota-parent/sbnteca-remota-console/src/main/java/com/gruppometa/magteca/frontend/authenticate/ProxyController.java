package com.gruppometa.magteca.frontend.authenticate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.mvc.ProxyExchange;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@RestController
public class ProxyController {

    protected static final Logger logger = LoggerFactory.getLogger(ProxyController.class);
    @Value("${configs.baseRestInternal}")
    protected String tecaUrl = "http://tecadigitalesystem:8080/TecaDigitaleSystem/rest/";

    @PostMapping(value = "TecaDigitaleSystem/rest/{action}")
    public ResponseEntity<?>
    getPostProxy(ProxyExchange<Object> proxy,
             @RequestHeader("X-Forwarded-For") String forward,
             @PathVariable("action") String action) throws IOException {
        return proxy
                //.header("Cache-Control","max-age=3600")
                .header("X-Forwarded-For", forward)
                .uri(tecaUrl
                +action).post();
    }

    @GetMapping(value = "TecaDigitaleSystem/rest/{action}")
    public ResponseEntity<?>
    getGetProxy(ProxyExchange<Object> proxy,
                @RequestParam MultiValueMap<String,String> allParams,
                @RequestHeader("X-Forwarded-For") String forward,
             @PathVariable("action") String action) throws IOException {
        UriComponents uriComponents =  UriComponentsBuilder.fromHttpUrl(
                tecaUrl+action).queryParams(allParams).build();
        return proxy
                .header("Cache-Control","max-age=3600")
                .header("X-Forwarded-For", forward)
                .uri(uriComponents.toUri().toString())
                .get();
    }

    @DeleteMapping(value = "TecaDigitaleSystem/rest/{action}/{action2}")
    public ResponseEntity<?>
    getGetProxy(ProxyExchange<Object> proxy,
                @RequestHeader("X-Forwarded-For") String forward,
                @RequestParam MultiValueMap<String,String> allParams,
                @PathVariable("action") String action,
                @PathVariable("action2") String action2) throws IOException {
        UriComponents uriComponents =  UriComponentsBuilder.fromHttpUrl(
                tecaUrl+action+"/"+action2).queryParams(allParams).build();
        return proxy
                //.header("Cache-Control","max-age=3600")
                .uri(uriComponents.toUri().toString())
                .header("X-Forwarded-For", forward)
                .delete();
    }

    @GetMapping(value = "TecaDigitaleSystem/rest/{action}/{action2}")
    public ResponseEntity<?>
    getGetProxy2(ProxyExchange<Object> proxy,
                 @RequestHeader("X-Forwarded-For") String forward,
                @RequestParam MultiValueMap<String,String> allParams,
                @PathVariable("action") String action,
                @PathVariable("action2") String action2) throws IOException {
        UriComponents uriComponents =  UriComponentsBuilder.fromHttpUrl(
                tecaUrl+action+"/"+action2).queryParams(allParams).build();
        return proxy
                .uri(uriComponents.toUri().toString())
                .header("X-Forwarded-For", forward)
                .get();
    }

    @PostMapping(value = "TecaDigitaleSystem/rest/{action}/{action2}")
    public ResponseEntity<?>
    getPostProxy2(ProxyExchange<Object> proxy,
                  @RequestHeader("X-Forwarded-For") String forward,
                 @RequestParam MultiValueMap<String,String> allParams,
                 @PathVariable("action") String action,
                 @PathVariable("action2") String action2) throws IOException {
        return proxy
                .uri(tecaUrl+action+"/"+action2)
                .header("X-Forwarded-For", forward)
                .post();
    }

    @GetMapping(value = "TecaDigitaleSystem/rest/{action}/{action2}/{action3}")
    public ResponseEntity<?>
    getGetProxy(ProxyExchange<Object> proxy,
                @RequestHeader("X-Forwarded-For") String forward,
                @RequestParam MultiValueMap<String,String> allParams,
                @PathVariable("action") String action,
                @PathVariable("action2") String action2,
                @PathVariable("action3") String action3) throws IOException {
        UriComponents uriComponents =  UriComponentsBuilder.fromHttpUrl(
                tecaUrl+action+"/"+action2+"/"+action3).queryParams(allParams).build();
        return proxy
                .uri(uriComponents.toUri().toString())
                .header("X-Forwarded-For", forward)
                .get();
    }

    @GetMapping(value = "TecaDigitaleSystem/digitalObject/{id}")
    public ResponseEntity<?>
    getGetObject(ProxyExchange<Object> proxy,
                 @RequestHeader("X-Forwarded-For") String forward,
                @RequestParam MultiValueMap<String,String> allParams,
                @PathVariable("id") String id) throws IOException {
        UriComponents uriComponents =  UriComponentsBuilder.fromHttpUrl(
                tecaUrl.replace("/rest/","/digitalObject/")+id).queryParams(allParams).build();
        return proxy
                .uri(uriComponents.toUri().toString())
                .header("X-Forwarded-For", forward)
                .get();
    }

    @GetMapping(value = "TecaDigitaleSystem/rest/digitalResource/{mode}/{type}/{param1}/{param2}")
    public ResponseEntity<?>
    getGetProxy3(ProxyExchange<Object> proxy,
                 @RequestHeader("X-Forwarded-For") String forward,
                 @RequestParam MultiValueMap<String,String> allParams,
                 @PathVariable("mode") String mode,
                 @PathVariable("type") String type,
                 @PathVariable("param1") String param1,
                 @PathVariable("param2") String param2
    ) throws IOException {
        UriComponents uriComponents =  UriComponentsBuilder.fromHttpUrl(
                tecaUrl+"digitalResource/"+
                        mode+"/"+type+"/"+param1+"/"+param2).queryParams(allParams).build();
        return proxy
                .uri(uriComponents.toUri().toString())
                .header("X-Forwarded-For", forward)
                .get();
    }

}
