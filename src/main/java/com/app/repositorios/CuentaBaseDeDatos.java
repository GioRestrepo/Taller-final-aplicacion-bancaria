package com.app.repositorios;

import com.app.entidades.Cuenta;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class CuentaBaseDeDatos implements Repositorio {
    private String conexionBD;

    public CuentaBaseDeDatos() {
        try {
            DriverManager.registerDriver(new org.sqlite.JDBC());
            conexionBD = "jdbc:sqlite:banco.db";
        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e);
        }
    }

    @Override
    public Object crear(Object objeto) {
        try (Connection conexion = DriverManager.getConnection(conexionBD)) {
            Cuenta cuenta = (Cuenta) objeto;
            String sentenciaSql = "INSERT INTO cuentas (NUMERO_CUENTA, SALDO, TIPO_CUENTA, ID_USUARIO) " +
                    " VALUES('" + cuenta.getNumeroCuenta()
                    + "', " + cuenta.getSaldo()
                    + ", '" + cuenta.getTipoCuenta()
                    + "', " + cuenta.getIdUsuario() + ");";
            System.out.println(sentenciaSql);
            Statement sentencia = conexion.createStatement();
            sentencia.execute(sentenciaSql);
        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e);
        } catch (Exception e) {
            System.err.println("Error " + e.getMessage());

        }
        return "Cuenta registrada";
     }

     @Override
     public String eliminar (Object objeto){
         try (Connection conexion = DriverManager.getConnection(conexionBD)) {
             Cuenta BorrarCuenta = (Cuenta) objeto;

             // * Eliminamos las transacciones
             String sentenciaEliminarTransaccionsSql = "DELETE FROM TRANSACCIONES WHERE ID_CUENTA = ?";
             PreparedStatement sentenciaEliminarTransacciones = conexion.prepareStatement(sentenciaEliminarTransaccionsSql);

             sentenciaEliminarTransacciones.setInt(1, BorrarCuenta.getId());
             sentenciaEliminarTransacciones.executeQuery();

             // * Eliminamos las cuentas
             String sentenciaEliminarCuentasSql = "DELETE FROM CUENTAS WHERE ID = ?";
             PreparedStatement sentenciaEliminarCuentas = conexion.prepareStatement(sentenciaEliminarCuentasSql);
             sentenciaEliminarCuentas.setInt(1, BorrarCuenta.getId());
             sentenciaEliminarCuentas.executeQuery();

             return "La cuenta ha sido eliminada";

         } catch (SQLException e) {
             return "Hubo un error de conexión: " + e;
         } catch (Exception e) {
             return "Error " + e.getMessage();
         }
    }


    @Override
    public List<?> listar () {
        List<Cuenta> cuentas = new ArrayList<>();

        try (Connection conexion = DriverManager.getConnection(conexionBD)) {
            String sentenciaSql = "SELECT * FROM CUENTAS";
            PreparedStatement sentencia = conexion.prepareStatement(sentenciaSql);
            ResultSet resultadoConsulta = sentencia.executeQuery();

            if (resultadoConsulta != null) {
                while (resultadoConsulta.next()) {
                    Cuenta cuenta = null;
                    int id = resultadoConsulta.getInt("ID");
                    String numeroCuenta = resultadoConsulta.getString("NUMERO_CUENTA");
                    float saldo = resultadoConsulta.getFloat("SALDO");
                    String tipoCuenta = resultadoConsulta.getString("TIPO_CUENTA");
                    int idUsuario = resultadoConsulta.getInt("ID_USUARIO ");

                    cuenta = new Cuenta(id, numeroCuenta, saldo, tipoCuenta, idUsuario);
                    cuentas.add(cuenta);
                }
                return cuentas;
            }
        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e);
        }
        return null;
    }

}

