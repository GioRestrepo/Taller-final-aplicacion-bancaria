package com.app.controladores;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TransaccionController extends HttpServlet {

    private ObjectMapper mapper;

    public TransaccionController() {
        mapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        switch (request.getPathInfo()) {
            case "/":
                response.getWriter().println("Hola Mundo desde Get");
                break;
            case "/mundo":
                response.getWriter().println("Hola Mundo mundo");
                break;
            case "/adios":
                response.getWriter().println("Adios Mundo");
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "No se encuentra el recurso");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String contenido = request.getContentType();
        mapper = new ObjectMapper();

        if (contenido == "application/json") {
            Map<String, Object> json = mapper.readValue(request.getInputStream(), HashMap.class);
            System.out.println(json);
        } else {
            response.sendError(HttpServletResponse.SC_CONFLICT, "El contenido debe ser JSON");
        }
    }
}
