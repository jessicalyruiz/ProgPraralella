package com.distribuida;

import com.distribuida.db.Persona;
import com.distribuida.servicios.IServicioPersona;
import com.google.gson.Gson;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import spark.Request;
import spark.Response;

import java.util.List;

import static spark.Spark.*;

public class Principal {
    static SeContainer container;

    static List<Persona> listarPersonas(Request req, Response resp){
        var servicio=container.select(IServicioPersona.class).get(); //obtener una instancia del bean ed. una instancia de IServicioPersona
        resp.type("application/json");
        return servicio.findAll();
    }

    //buscar por id

    static Persona buscarPersonaById(Request req, Response resp){
        var servicio=container.select(IServicioPersona.class).get();
        resp.type("application/json");

        //recupero el id
        String id=req.params(":id"); //usa el request para obtener el id desde la url

        Persona persona=servicio.read(Integer.valueOf(id));

        if(persona==null){
            // 404 - Persona no encontrada
       halt(404,"Persona no encontrada"); //etener la ejecución del manejo de una solicitud y enviar una respuesta HTTP al cliente indicando que el recurso solicitado no se ha encontrado.
        }
        return persona;

    }
    //update

    static String actualizar(Request req, Response resp){
        var servicio=container.select(IServicioPersona.class).get();
        int id= Integer.parseInt(req.params(":id"));
        //recupero el body
        String body=req.body();
        Gson gson=new Gson();
        Persona p=gson.fromJson(body, Persona.class);
        p.setId(id);
        servicio.update(p);
        resp.status(200);
        return "Persona actualizada correctamente";
    }

    static String insertar(Request req, Response resp){
        var servicio=container.select(IServicioPersona.class).get();

        String body= req.body();
        Gson gson=new Gson();
        Persona p=gson.fromJson(body, Persona.class);
        servicio.create(p);
        resp.status(201);  // Código 201 indica "Created"
        return "Persona insertada exitosamente";
    }

    static String delete(Request req, Response resp){
        var servicio=container.select(IServicioPersona.class).get();

        Integer id = Integer.valueOf(req.params(":id"));
        /*Persona p=servicio.read(id);
        if(p==null){
            halt(404,"Persona no encontrada");
        }*/
        servicio.delete(id);
        resp.status(201);
        return "Persona eliminada";
    }

    static Persona buscarCedula(Request req, Response resp){
        var servicio=container.select(IServicioPersona.class).get();
        return servicio.findByCedula(req.params(":cedula"));

    }




    public static void main(String[] args) {
        container= SeContainerInitializer.newInstance().initialize();

        //Obtengo una instancia del Iservicio persona
        IServicioPersona servicio=container.select(IServicioPersona.class).get();
        port(8080);


        // Configuración de rutas para manejar solicitudes HTTP

        Gson gson = new Gson();
        get("/personas", Principal::listarPersonas, gson::toJson); //Configura una ruta para manejar solicitudes HTTP GET a la ruta "/personas".
        get("/personas/:id", Principal::buscarPersonaById, gson::toJson);
        //post("/personas", Principal::insertar);
        post("/personas", Principal::insertar, gson::toJson);
        /*delete("/personas/:id",
                Principal::delete,
                new Gson()::toJson);*/
        //delete("/personas/:id", (req, resp)->{Principal.delete(req,resp);});

//delete("/personas/:id",(request, response) -> Principal.delete(request,response),gson::toJson);


        put("/personas/:id",Principal::actualizar,gson::toJson);
        get("/personas/ced/:cedula", Principal::buscarCedula, gson::toJson);
    }



}
