package com.app.repositorios;


import com.app.entidades.Transaccion;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class TransaccionBaseDeDatos implements  Repositorio{
    private String conexionBD;

    public TransaccionBaseDeDatos(){
        try {
            DriverManager.registerDriver(new org.sqlite.JDBC());
            conexionBD = "jdbc:sqlite:banco.db";
        }catch (SQLException e){
            System.err.println("Error de conexión: " + e);
        }
    }


    @Override
    public Object crear(Object objeto) {
        try (Connection conexion = DriverManager.getConnection(conexionBD)) {
            Transaccion transaccion = (Transaccion) objeto;
            String sentenciaSql = "INSERT INTO TRANSACCIONES (FECHA, HORA, TIPO_TRANSACCION, MONTO, ID_CUENTA, TIPO_CUENTA_DESTINO) " +
                    " VALUES('" + transaccion.getFecha() + "', '" + transaccion.getHora()
                    + "', '" + transaccion.getTipoTransaccion() + "', " + transaccion.getMonto()
                    + ","  + transaccion.getIdCuenta() + ",'" + transaccion.getTipoCuentaDestino() + "');";
            Statement sentencia = conexion.createStatement();
            sentencia.execute(sentenciaSql);
        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e);
        } catch (Exception e) {
            System.err.println("Error " + e.getMessage());
        }
        return "Transacción realizada";
    }

    @Override
    public String eliminar(Object objeto) {
        try (Connection conexion = DriverManager.getConnection(conexionBD)) {
            Transaccion transaccionAEliminar = (Transaccion) objeto;

            // * Eliminamos las transacciones
            String sentenciaEliminarTransaccionsSql = "DELETE FROM TRANSACCIONES WHERE ID = ?";
            PreparedStatement sentenciaEliminarTransacciones = conexion.prepareStatement(sentenciaEliminarTransaccionsSql);

            sentenciaEliminarTransacciones.setInt(1, transaccionAEliminar.getId());
            sentenciaEliminarTransacciones.executeQuery();

            return "La transacción ha sido eliminada";

        } catch (SQLException e) {
            return "Hubo un error con la conexión: " + e;
        } catch (Exception e) {
            return "Error " + e.getMessage();
        }
    }

    @Override
    public List<?> listar() {
        List<Transaccion> transacciones = new ArrayList<>();

        try (Connection conexion = DriverManager.getConnection(conexionBD)) {
            String sentenciaSql = "SELECT * FROM TRANSACCIONES";
            PreparedStatement sentencia = conexion.prepareStatement(sentenciaSql);
            ResultSet resultadoConsulta = sentencia.executeQuery();

            if (resultadoConsulta != null) {
                while (resultadoConsulta.next()) {
                    Transaccion transaccion = null;
                    int id = resultadoConsulta.getInt("ID");
                    String fecha = resultadoConsulta.getString("FECHA");
                    String hora = resultadoConsulta.getString("HORA");
                    String tipoTransaccion = resultadoConsulta.getString("TIPO_TRANSACCION");
                    float monto = resultadoConsulta.getFloat("MONTO");
                    int idCuenta = resultadoConsulta.getInt("ID_CUENTA");
                    String tipoCuenta = resultadoConsulta.getString("TIPO_CUENTA");

                    transaccion = new Transaccion(id, fecha, hora, tipoTransaccion, monto, idCuenta,tipoCuenta);
                    transacciones.add(transaccion);
                }
                return transacciones;
            }
        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e);
        }
        return null;
    }
}
