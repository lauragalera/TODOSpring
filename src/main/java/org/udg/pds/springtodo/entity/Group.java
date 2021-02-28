package org.udg.pds.springtodo.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity(name = "groups") //per SQL, pk group es una paraula reservada
public class Group implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // es una clau primaria
    protected Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private User owner;

    @NotNull
    private String name;

    @NotNull
    private String description;
}
