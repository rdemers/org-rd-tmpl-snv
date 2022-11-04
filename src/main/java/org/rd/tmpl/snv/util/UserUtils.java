package org.rd.tmpl.snv.util;

import java.util.ArrayList;
import java.util.List;

import org.rd.tmpl.snv.dto.ERole;
import org.rd.tmpl.snv.dto.User;

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