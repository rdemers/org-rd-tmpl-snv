package com.lq.sdj.pdc.secjwt.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.lq.sdj.pdc.secjwt.dto.ERole;
import com.lq.sdj.pdc.secjwt.dto.User;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserUtils userUtils;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userUtils.findByUsername(username);
        if (user == null) 
            new UsernameNotFoundException("Ne retrouve pas l'usager: " + username);

        return UserDetailsImpl.build(user);
    }

    public UserDetails loadUserByToken(String jwt) {

        String username = jwtUtils.getUserNameFromJwtToken(jwt);
        List<ERole> roles = jwtUtils.getAuthoritiesFromJwtToken(jwt);

        User user = new User(username, null, roles);
        return UserDetailsImpl.build(user);
    }
}