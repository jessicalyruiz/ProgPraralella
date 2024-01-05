package com.distribuida.db;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "persona")
public class Persona {

    @Id
    @Getter @Setter
    private Integer id;
    @Getter @Setter
    private String cedula;
    @Getter @Setter
    private String nombre;
    @Getter @Setter
    private String direccion;
    @Getter @Setter
    private Integer edad;
}
