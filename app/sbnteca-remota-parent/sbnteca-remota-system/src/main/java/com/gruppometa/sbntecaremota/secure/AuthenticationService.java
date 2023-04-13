package com.gruppometa.sbntecaremota.secure;

import com.gruppometa.sbntecaremota.objects.db.DBTecaUser;
import com.gruppometa.sbntecaremota.objects.db.DaoException;
import com.gruppometa.sbntecaremota.restweb.objects.MagUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthenticationService implements UserDetailsService {

    @Autowired
    protected MagUserService magUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            DBTecaUser user = magUserService.searchUserByUsername(username);
            if(user==null)
                return null;
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority("ROLE_API_ADMIN"));
            return new MagtecaUser(user.getId(), user.getUsername(), user.getPassword(),
                   true, true, true,true,
                    authorities);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return null;
    }
}
