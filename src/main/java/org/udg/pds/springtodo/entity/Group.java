package org.udg.pds.springtodo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity(name = "groups") //per SQL, pk group es una paraula reservada
public class Group implements Serializable {

    public Group() {
    }

    public Group(String name, String description){
        this.name = name;
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_owner")
    private User owner;

    @ManyToMany(cascade = CascadeType.ALL)
    private Collection<User> members = new ArrayList<>();

    @JsonView(Views.Private.class)
    public Long getId() {
        return id;
    }

    @JsonIgnore
    public User getUser() {
        return owner;
    }


    public void addMember(User user) {
        members.add(user);
    }

    public void setUser(User user) {
        owner = user;
    }
}
