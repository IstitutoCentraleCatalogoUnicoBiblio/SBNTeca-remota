package com.gruppometa.sbntecaremota.restweb;

import com.gruppometa.sbntecaremota.restweb.objects.MagUserService;
import com.gruppometa.sbntecaremota.secure.AuthenticationService;
import com.gruppometa.sbntecaremota.secure.JwtTokenUtil;
import com.gruppometa.sbntecaremota.secure.MagtecaUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Path("/rest")
public class LoginResource {

    @Autowired
    protected JwtTokenUtil jwtTokenUtil;

    @Autowired
    protected AuthenticationService authenticationService;

    @Value("${api.password}")
    protected String password;

    @Value("${api.username}")
    protected String username;

    @POST
    @Path("/jwt/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response login(@FormParam("username") String username,
                          @FormParam("password") String password) {
        try {
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority("ROLE_API_ADMIN"));
            MagtecaUser user = new MagtecaUser(
                1,username,"",true,true,true,true,
                 authorities
            );
            //(MagtecaUser) authenticationService.loadUserByUsername(username);
            if(!password.equals(this.password) || !this.username.equals(user.getUsername()))
                throw new BadCredentialsException("Username or password invalid.");
            Map<String, Object> response = new HashMap<>();
            response.put("user", user);
            String token = jwtTokenUtil.generateAccessToken(user);
            response.put("token", token);
            return Response.ok(response)
                    .header(HttpHeaders.AUTHORIZATION,
                            "Bearer "+token).build();
        } catch (BadCredentialsException ex) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
}