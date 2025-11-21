package ConexionBaseDeDatos;

import javax.swing.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Ventas {
    private int id;
    private int cliente_id;
    private String fecha;
    private int total;
    private ConexionBD conexionBD;


    public Ventas() {
    }

    public Ventas(int id, int cliente_id, String fecha, int total) {
        this.id = id;
        this.cliente_id = cliente_id;
        this.fecha = fecha;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(int cliente_id) {
        this.cliente_id = cliente_id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ConexionBD getConexionBD() {
        return conexionBD;
    }

    public void setConexionBD(ConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    public boolean guardarVentas() {
        boolean conf;
        this.conexionBD = new ConexionBD();
        String sql = "INSERT INTO Ventas(cliente_id, fecha , total )" + "VALUES('" + this.cliente_id + "','" + this.fecha +
                "','" + this.total + "');";

        if (this.conexionBD.setAutoCommitBD(false)) {
            if (this.conexionBD.insertarBD(sql)) {
                this.conexionBD.commitBD();
                this.conexionBD.cerrarConexion();
                conf = true;

            } else {
                this.conexionBD.rollbackBD();
                this.conexionBD.cerrarConexion();
                conf = true;

            }
        } else {
            this.conexionBD.cerrarConexion();
            conf = false;
        }
        return conf;
    }

    public List<Ventas> listaventas() {
        List<Ventas> listaventas = new ArrayList<>();

        this.conexionBD = new ConexionBD();

        String sql = "SELECT * FROM Ventas;";
        try {
            ResultSet rs = this.conexionBD.consultarBD(sql);
            Ventas ventas;
            while (rs.next()) {
                ventas = new Ventas();
                ventas.setId(rs.getInt("id"));
                ventas.setCliente_id(rs.getInt("cliente_id"));
                ventas.setFecha(rs.getString("fecha"));
                ventas.setTotal(rs.getInt("total"));


                listaventas.add(ventas);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "\nError en ventas : " + e);
        } finally {
            this.conexionBD.cerrarConexion();
        }
        return listaventas;

    }

    public boolean actualizarVentas() {
        boolean conf;
        this.conexionBD = new ConexionBD();

        String sql = "UPDATE Ventas SET " + "cliente_id = '" + this.cliente_id + "'," + "fecha = '" + this.fecha+ "'," +
                "total = '" + this.total +  "' WHERE id = " + this.id + ";";


        if (this.conexionBD.setAutoCommitBD(false)) {
            if (this.conexionBD.actualizarBD(sql)) {
                this.conexionBD.commitBD();
                this.conexionBD.cerrarConexion();
                conf = true;
            } else {
                this.conexionBD.rollbackBD();
                this.conexionBD.cerrarConexion();
                conf = false;
            }

        } else {
            this.conexionBD.cerrarConexion();
            conf = false;
        }
        return conf;


    }
    public boolean eliminarVentas(){
        boolean conf;

        this.conexionBD = new ConexionBD();

        String sql = "DELETE FROM Ventas " + "WHERE id = " + this.id + ";";

        if ( this.conexionBD.setAutoCommitBD(false)){
            if(this.conexionBD.borrarBD(sql)){
                this.conexionBD.commitBD();
                this.conexionBD.cerrarConexion();
                conf = true;
            }
            else{
                this.conexionBD.rollbackBD();
                this.conexionBD.cerrarConexion();
                conf = false;

            }
        }
        else{
            this.conexionBD.cerrarConexion();
            conf = false;
        }
        return conf;
    }
}