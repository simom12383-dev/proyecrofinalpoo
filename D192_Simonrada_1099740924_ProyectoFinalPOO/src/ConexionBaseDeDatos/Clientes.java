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
    private String usuario;
    private String password;
    private String rol;   // ADMIN o CLIENTE

    private ConexionBD conexionBD;

    public Clientes() {}

    public Clientes(int id, String nombre, String telefono, String correo, String direccion,
                    String usuario, String password, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
        this.usuario = usuario;
        this.password = password;
        this.rol = rol;
    }

    // ------------------ GETTERS Y SETTERS ------------------

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    // ------------------ GUARDAR CLIENTE ------------------

    public boolean guardarClientes() {
        boolean conf;
        this.conexionBD = new ConexionBD();
        String sql = "INSERT INTO Clientes(nombre, telefono, correo, direccion, usuario, password, rol) VALUES('" +
                this.nombre + "','" + this.telefono + "','" + this.correo + "','" + this.direccion + "','" +
                this.usuario + "','" + this.password + "','" + this.rol + "');";

        if (this.conexionBD.setAutoCommitBD(false)) {
            if (this.conexionBD.insertarBD(sql)) {
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

    // ------------------ LISTAR CLIENTES ------------------

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
                clientes.setUsuario(rs.getString("usuario"));
                clientes.setPassword(rs.getString("password"));
                clientes.setRol(rs.getString("rol"));

                listaclientes.add(clientes);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en lista de clientes: " + e);
        } finally {
            this.conexionBD.cerrarConexion();
        }

        return listaclientes;
    }

    // ------------------ ACTUALIZAR CLIENTE ------------------

    public boolean actualizarClientes() {
        boolean conf;
        this.conexionBD = new ConexionBD();

        String sql = "UPDATE Clientes SET nombre='" + this.nombre +
                "', telefono='" + this.telefono + "', correo='" + this.correo +
                "', direccion='" + this.direccion + "', usuario='" + this.usuario +
                "', password='" + this.password + "', rol='" + this.rol +
                "' WHERE id=" + this.id + ";";

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

    // ------------------ ELIMINAR CLIENTE ------------------

    public boolean eliminarClientes() {
        boolean conf;
        this.conexionBD = new ConexionBD();

        String sql = "DELETE FROM Clientes WHERE id=" + this.id + ";";

        if (this.conexionBD.setAutoCommitBD(false)) {
            if (this.conexionBD.borrarBD(sql)) {
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

    // ------------------ INICIAR SESIÓN (RETORNA ROL) ------------------

    public String iniciarSesion(String usuario, String password) {
        String rol = null;
        this.conexionBD = new ConexionBD();

        String sql = "SELECT rol FROM Clientes WHERE usuario='" + usuario +
                "' AND password='" + password + "';";

        try {
            ResultSet rs = this.conexionBD.consultarBD(sql);
            if (rs.next()) {
                rol = rs.getString("rol"); // admin o cliente
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al iniciar sesión: " + e);
        } finally {
            this.conexionBD.cerrarConexion();
        }

        return rol;
    }
}
