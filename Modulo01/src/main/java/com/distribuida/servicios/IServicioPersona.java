package com.distribuida.servicios;

import com.distribuida.db.Persona;

import java.util.List;

public interface IServicioPersona {

    void create(Persona persona);//insert
    Persona read(Integer id); //findById
    void update(Persona persona);
    void delete(Integer id);

    List<Persona> findAll();
    Persona findByCedula(String cedula);
}
