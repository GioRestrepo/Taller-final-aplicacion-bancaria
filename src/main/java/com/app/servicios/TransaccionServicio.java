package com.app.servicios;

import com.app.repositorios.Repositorio;
import com.app.repositorios.TransaccionBaseDeDatos;

import java.util.List;
import java.util.Map;

public class TransaccionServicio implements Servicio {
    private Repositorio repositorio;

    public TransaccionServicio(Repositorio repositorio) {
        repositorio = new TransaccionBaseDeDatos();
    }

    @Override
    public Object crear(Map objeto) {
        return null;
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
