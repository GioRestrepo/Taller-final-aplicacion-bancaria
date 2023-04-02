package com.app.servicios;

import com.app.entidades.Usuario;
import com.app.repositorios.CuentaBaseDeDatos;
import com.app.repositorios.Repositorio;

import java.util.List;
import java.util.Map;

public class CuentaServicio implements Servicio{
    private Repositorio repositorio;

    public CuentaServicio(Repositorio repositorio) {
        repositorio = new CuentaBaseDeDatos();
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
