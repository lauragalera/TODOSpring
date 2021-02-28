package org.udg.pds.springtodo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

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

    @JsonView(Views.Private.class)
    public Long getId() {
        return id;
    }

    public void setUser(User user) {
        owner = user;
    }
}
