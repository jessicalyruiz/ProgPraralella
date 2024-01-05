package com.distribuida.servicios;

import com.distribuida.db.Persona;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
@ApplicationScoped
public class ServicioPersonaImpl implements IServicioPersona{
    @Inject
    EntityManager entityManager;
    @Override
    public void create(Persona persona) {
        var tx=entityManager.getTransaction();

        try {
            tx.begin();
            entityManager.persist(persona);
            tx.commit();
        }
        catch (Exception ex){
            tx.rollback();
        }
    }

    @Override
    public Persona read(Integer id) {
        return entityManager.find(Persona.class, id);
    }

    @Override
    public void update(Persona persona) {
        entityManager.merge(persona);
    }

    @Override
    public void delete(Integer id) {
        entityManager.remove(this.read(id));
    }

    @Override
    public List<Persona> findAll() {
        return entityManager.createQuery("Select p from Persona p").getResultList();
    }

    @Override
    public Persona findByCedula(String cedula) {
        TypedQuery<Persona> myQuery=entityManager.createQuery("Select p from Persona p where p.cedula=:valor", Persona.class);
        myQuery.setParameter("valor", cedula);
        return myQuery.getSingleResult();
    }
}
