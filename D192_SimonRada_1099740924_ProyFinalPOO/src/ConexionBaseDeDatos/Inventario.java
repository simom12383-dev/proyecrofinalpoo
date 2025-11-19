package ConexionBaseDeDatos;

import javax.swing.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Inventario {
    private int id;
    private String nombreprodu;
    private int cantidad;
    private int precio;
    private String categoria;
    private String fechaingreso;
    private String estado;
    private ConexionBD conexionBD;

    //metodos___funciones


    public Inventario() {
    }

    public Inventario(int id, String nombreprodu, int cantidad, int precio, String categoria, String fechaingreso, String estado) {
        this.id = id;
        this.nombreprodu = nombreprodu;
        this.cantidad = cantidad;
        this.precio = precio;
        this.categoria = categoria;
        this.fechaingreso = fechaingreso;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreprodu() {
        return nombreprodu;
    }

    public void setNombreprodu(String nombreprodu) {
        this.nombreprodu = nombreprodu;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getFechaingreso() {
        return fechaingreso;
    }

    public void setFechaingreso(String fechaingreso) {
        this.fechaingreso = fechaingreso;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    public ConexionBD getConexionBD(){return conexionBD;}
    public void setConexionBD(ConexionBD conexionBD){this.conexionBD = conexionBD;}

    public boolean guardarInventario(){
        boolean conf;

        this.conexionBD = new ConexionBD();
        String sql = "INSERT INTO Inventario(nombreprodu, cantidad,categoria, precio,estado,fechaingreso)" + "VALUES('" + this.nombreprodu + "',"+ this.cantidad +
                ",'" + this.categoria + "'," + this.precio + ",'"+ this.estado + "','" + this.fechaingreso +"');";

        if (this.conexionBD.setAutoCommitBD(false)){
            if(this.conexionBD.insertarBD(sql)){
                this.conexionBD.commitBD();
                this.conexionBD.cerrarConexion();
                conf=true;

            }
            else{
                this.conexionBD.rollbackBD();
                this.conexionBD.cerrarConexion();
                conf = true;

            }
        }
        else{
            this.conexionBD.cerrarConexion();
            conf = false;
        }
        return conf;
    }

    public List<Inventario>listainventario(){
        List<Inventario> listainventario = new ArrayList<>();

        this.conexionBD = new ConexionBD();

        String sql = "SELECT * FROM Inventario;";
        try{
            ResultSet rs = this.conexionBD.consultarBD(sql);
            Inventario inventario;
            while (rs.next()){
                inventario = new Inventario();
                inventario.setId(rs.getInt("id"));
                inventario.setNombreprodu(rs.getString("nombreprodu"));
                inventario.setCantidad(rs.getInt("cantidad"));
                inventario.setCategoria(rs.getString("categoria"));
                inventario.setPrecio(rs.getInt("precio"));
                inventario.setEstado(rs.getString("estado"));
                inventario.setFechaingreso(rs.getString("fechaingreso"));
                listainventario.add(inventario);

            }
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,"\nError en el Inventario : " + e);
        }
        finally {
            this.conexionBD.cerrarConexion();
        }
        return listainventario;
    }

    public boolean actualizarInventario(){
        boolean conf;
        this.conexionBD = new ConexionBD();

        String sql = "UPDATE Inventario SET " + "nombreprodu = '" + this.nombreprodu +"'," + "cantidad = " + this.cantidad + ","+
                "categoria = '" + this.categoria + "'," + "precio = " + this.precio + "," + "estado = '" + this.estado + "'," + "fechaingreso = '" + this.fechaingreso + "' WHERE id = " + this.id + ";";


        if(this.conexionBD.setAutoCommitBD(false)){
            if(this.conexionBD.actualizarBD(sql)){
                this.conexionBD.commitBD();
                this.conexionBD.cerrarConexion();
                conf= true;
            }
            else{
                this.conexionBD.rollbackBD();
                this.conexionBD.cerrarConexion();
                conf= false;
            }

        }
        else{
            this.conexionBD.cerrarConexion();
            conf = false;
        }
        return conf;
    }

    public boolean eliminarInventario(){
        boolean conf;

        this.conexionBD = new ConexionBD();

        String sql = "DELETE FROM Inventario " + "WHERE id = " + this.id + ";";

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
