package com.example.rest;

import controller.Dao.servicies.HtmlValidadorServicies;
import models.HtmlValidador;

import java.util.HashMap;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;



@Path("validador")
public class HtmlValidadorApi {  

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPersons() {

        HashMap map = new HashMap<>();

        HtmlValidadorServicies vs = new HtmlValidadorServicies();
        map.put("msg", "Ok");
        map.put("data", vs.listAll().toArray());

        if (vs.listAll().isEmpty()) {
            map.put("data", new Object[]{});
           
        }
        return Response.ok(map).build();
    }
    
    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(HashMap map) {
        //todo
        //Validation

        HashMap res = new HashMap<>();

        try {
            HtmlValidadorServicies vs = new HtmlValidadorServicies();
            HtmlValidador hv = new HtmlValidador();
            hv.setHtmlCode(map.get("htmlCode").toString());
            System.out.println("Este es el htmlCode: " + map.get("htmlCode").toString());

            vs.save(hv);

            res.put("msg", "Ok");
            res.put("data", "Guardado correctamente");
            return Response.ok(res).build();
           
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    // @Path("/update")
    // @POST
    // @Consumes(MediaType.APPLICATION_JSON)
    // @Produces(MediaType.APPLICATION_JSON)
    // public Response update(HashMap map) {
    //     //todo
    //     //Validation

    //     HashMap res = new HashMap<>();

    //     try {

    //         FamiliaServicies ps = new FamiliaServicies();
    //         ps.setFamilia(ps.get(Integer.parseInt(map.get("id").toString())));
    //         ps.getFamilia().setCanton(map.get("canton").toString());
    //         ps.getFamilia().setApellidoPaterno(map.get("apellidoPaterno").toString());
    //         ps.getFamilia().setApellidoMaterno(map.get("apellidoMaterno").toString());
    //         ps.getFamilia().setIntegrantes(Integer.parseInt(map.get("integrantes").toString()));
    //         ps.getFamilia().setTieneGenerador(Boolean.parseBoolean(map.get("tieneGenerador").toString()));
    
    //         ps.update();

    //         res.put("msg", "Ok");
    //         res.put("data", "Guardado correctamente");
    //         return Response.ok(res).build();
           
    //     } catch (Exception e) {
    //         res.put("msg", "Error");
    //         res.put("data", e.toString());
    //         return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
    //     }
    // }
    
    // // @Path("/listType")

    // // @GET
    // // @Produces(MediaType.APPLICATION_JSON)
    // // public Response getType() {
    // //     HashMap map = new HashMap<>();
    // //     FamiliaServicies ps = new FamiliaServicies();
    // //     map.put("msg", "Ok");
    // //     map.put("data", ps.getFamilia());
    // //     return Response.ok(map).build();
    // // }

    @Path("/delete/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteFamilia(@PathParam("id") int id) {
        HashMap<String, Object> res = new HashMap<>();
    
        try {
            HtmlValidadorServicies fs = new HtmlValidadorServicies();
            
            boolean validadorDeleted = fs.delete(id - 1);       // Intentamos eliminar la familia

            if (validadorDeleted) {
                res.put("message", "Familia y Generador eliminados exitosamente");
                

                return Response.ok(res).build();
            } else {
            
                res.put("message", "Familia no encontrada o no eliminada");     // Si no se eliminó, enviar un error 404
                return Response.status(Response.Status.NOT_FOUND).entity(res).build();

            }
        } catch (Exception e) {
            
            res.put("message", "Error al intentar eliminar la familia"); // En caso de error, devolver una respuesta de error interno
            res.put("error", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

}
