package com.gruppometa.magteca.frontend.authenticate;

import com.gruppometa.magteca.frontend.WebAppConfiguration;
import io.jsonwebtoken.impl.Base64Codec;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Component
public class CheckJwtToken {

    @Value("${api.password}")
    protected String apiPassword;

    @Value("${api.username}")
    protected String apiUsername;

    @Autowired
    protected WebAppConfiguration configuration;

    private static final Logger  logger = LoggerFactory.getLogger(CheckJwtToken.class);

    public static Date getExpireDate(String token){
        if(token==null)
            return null;
        if(token.startsWith("Bearer "))
            token = token.substring("Bearer ".length());
        String str = Base64Codec.BASE64.decodeToString(token);
        if(str.indexOf("exp")!=-1) {
            String exp = str.substring(str.indexOf("exp"));
            exp = exp.substring(exp.indexOf(":") + 1, exp.indexOf("}"));
            return new Date(1000 * Long.parseLong(exp));
        }
        else {
            logger.info("Invalid: "+str);
            return null;
        }
    }

    public String getToken(){
        RestTemplate restTemplate = new RestTemplate();
        String token = null;
        try {
            MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
            map.add("username", apiUsername);
            map.add("password", apiPassword);
            ResponseEntity<String> userJwt = restTemplate.postForEntity(configuration.getBaseRestInternal() +
                    "jwt/login", map, String.class);
            token = userJwt.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        }
        catch (Exception e){
        }
        return token;
    }
}
