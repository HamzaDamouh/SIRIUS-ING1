package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.LinkedHashSet;
import java.util.Set;

public class Users {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("users")
    private  Set<User> users = new LinkedHashSet<User>();

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public final Users add (final User user) {
        users.add(user);
        return this;
    }

    @Override
    public String toString() {
        return "Users{" +
                "Users=" + users +
                '}';
    }
}
