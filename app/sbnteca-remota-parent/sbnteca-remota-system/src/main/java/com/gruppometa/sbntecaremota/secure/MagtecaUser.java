package com.gruppometa.sbntecaremota.secure;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class MagtecaUser extends User {
    private static final long serialVersionUID = 8963663573922279903L;

    // id
    private int id;

    public MagtecaUser(int id, String username, String password, boolean enabled, boolean accountNonExpired,
                       boolean credentialsNonExpired, boolean accountNonLocked,
                       Collection<? extends GrantedAuthority> authorities) {

        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
    }

    public int getID() {
        return id;
    }
}
