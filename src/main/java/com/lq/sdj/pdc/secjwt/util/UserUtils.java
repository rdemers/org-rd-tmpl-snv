package com.lq.sdj.pdc.secjwt.util;

import java.util.ArrayList;
import java.util.List;

import com.lq.sdj.pdc.secjwt.dto.ERole;
import com.lq.sdj.pdc.secjwt.dto.User;

public class UserUtils {

    private List<User> users;

    public UserUtils() {
        super();
        this.users = new ArrayList<>();
    }

    public void add(String username, String password, List<ERole> roles) {
        users.add(new User(username, password, roles));
    }

    public User findByUsername(String username) {
        User findUser = 
                 users.stream()
                      .filter(user -> username.equals(user.getUsername()))
                      .findAny()
                      .orElse(null);
        return findUser;
    }
}