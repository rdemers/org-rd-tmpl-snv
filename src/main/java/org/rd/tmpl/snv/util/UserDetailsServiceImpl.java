package org.rd.tmpl.snv.util;

import java.util.List;

import org.rd.tmpl.snv.dto.ERole;
import org.rd.tmpl.snv.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserUtils userUtils;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userUtils.findByUsername(username);
        if (user == null) 
            new UsernameNotFoundException("Username not found: " + username);

        return UserDetailsImpl.build(user);
    }

    public UserDetails loadUserByToken(String jwt) {

        String username = jwtUtils.getUserNameFromJwtToken(jwt);
        List<ERole> roles = jwtUtils.getAuthoritiesFromJwtToken(jwt);

        User user = new User(username, null, roles);
        return UserDetailsImpl.build(user);
    }
}