package ConexionBaseDeDatos;

import javax.swing.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Clientes {
    private int id;
    private String nombre;
    private String telefono;
    private String correo;
    private String direccion;
    private ConexionBD conexionBD;


    public Clientes() {
    }

    public Clientes(int id, String nombre, String telefono, String correo, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public ConexionBD getConexionBD() {
        return conexionBD;
    }

    public void setConexionBD(ConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    public boolean guardarClientes(){
        boolean conf;
        this.conexionBD = new ConexionBD();
        String sql = "INSERT INTO Clientes(nombre, telefono , correo , direccion)" + "VALUES('" + this.nombre + "','" + this.telefono +
                "','" + this.correo + "','" + this.direccion + "');";

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

    public List<Clientes> listaclientes() {
        List<Clientes> listaclientes = new ArrayList<>();

        this.conexionBD = new ConexionBD();

        String sql = "SELECT * FROM Clientes;";
        try {
            ResultSet rs = this.conexionBD.consultarBD(sql);
            Clientes clientes;
            while (rs.next()) {
                clientes = new Clientes();
                clientes.setId(rs.getInt("id"));
                clientes.setNombre(rs.getString("nombre"));
                clientes.setTelefono(rs.getString("telefono"));
                clientes.setCorreo(rs.getString("correo"));
                clientes.setDireccion(rs.getString("direccion"));

                listaclientes.add(clientes);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "\nError en clientes : " + e);
        } finally {
            this.conexionBD.cerrarConexion();
        }
        return listaclientes;

    }
    public boolean actualizarClientes() {
        boolean conf;
        this.conexionBD = new ConexionBD();

        String sql = "UPDATE Clientes SET " + "nombre = '" + this.nombre + "'," + "telefono = '" + this.telefono + "'," +
                "correo = '" + this.correo + "'," + "direccion = '" + this.direccion + "' WHERE id = " + this.id + ";";


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
    public boolean eliminarClientes(){
        boolean conf;

        this.conexionBD = new ConexionBD();

        String sql = "DELETE FROM Clientes " + "WHERE id = " + this.id + ";";

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
