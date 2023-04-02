package com.app.servicios;

import com.app.entidades.Usuario;
import com.app.repositorios.Repositorio;
import com.app.repositorios.UsuarioBaseDeDatos;

import java.util.List;
import java.util.Map;

public class UsuarioServicio implements Servicio {
    private Repositorio repositorio;

    public UsuarioServicio(Repositorio repositorio) {
        repositorio = new UsuarioBaseDeDatos();
    }

    @Override
    public Object crear(Map objeto) {

        String nombre = (String) objeto.get("nombre");
        String apellido = (String) objeto.get("apellido");
        String cedula = (String) objeto.get("cedula");

        Usuario newPerson = new Usuario(0, nombre, apellido, cedula);
        return repositorio.crear(newPerson);
    }

    @Override
    public String eliminar(Object id) {
        return null;
    }

    @Override
    public List<?> listar() {
        return null;
    }
}
