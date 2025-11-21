package ConexionBaseDeDatos;

import javax.swing.*;
import java.sql.*;

public class ConexionBD {
    //atributos propiedades

    private String url;
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    //metodos funciones

    public ConexionBD() {
        String host, nombreBD, userBD, pswBD, puerto;
        host = "b8rgnisdrgdi2aw2xeov-mysql.services.clever-cloud.com";
        nombreBD = "b8rgnisdrgdi2aw2xeov";
        userBD = "uzicauiucd5ywrij";
        pswBD = "ZPNqoF96kYVcDebf8Zd";
        puerto = "20480";
        this.url = this.url = "jdbc:mysql://" + host + ":" + puerto + "/" + nombreBD
                + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

        try {
            this.connection = DriverManager.getConnection(url, userBD, pswBD);
            if (this.connection != null) {
                DatabaseMetaData metaData = this.connection.getMetaData();
                System.out.println("conecion exitosa a la base de datos...");
                System.out.println("Meta Datos : " + metaData.getDriverName());
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "\nError de conexion a la base de datos..." + "\nTipo de Error : " + e.getMessage());
            System.out.println("\nError en la Conexion a la base de Datos..." + "\nTipo de Error : " + e.getMessage());
        }


    }

    public Connection getConnection() {
        return connection;
    }

    //CRUD insertar producto a la base de datos
    public boolean insertarBD(String sentenciaSQL) {
        boolean conf;
        try {
            this.statement = this.connection.createStatement();
            this.statement.execute(sentenciaSQL);
            conf = true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "\nError al Insertar Registros a la base De Datos..." + "\ntipo de error" + e.getMessage());
            System.out.println("\nError al Insertar Registros a la Base de Datos..." + "\nTipo de Error : " + e.getMessage());
            conf = false;
        }
        return conf;
    }

    // CRUD leer o consultar los registros de la base de datos
    public ResultSet consultarBD(String sentenciaSQL) {
        try {
            this.statement = this.connection.createStatement();
            this.resultSet = this.statement.executeQuery(sentenciaSQL);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "\nError al consultar registros en la base de datos..." + "\nTipo de eror : " + e.getMessage());
            System.out.println("\nEror al consultar registros a la base de datos..." + "\nTipo de Error :" + e.getMessage());
        }
        return resultSet;
    }

    //CRUD actualizar registros en la base de datos
    public boolean actualizarBD(String sentenciaSQL) {
        boolean conf;
        try {
            this.statement = this.connection.createStatement();
            this.statement.execute(sentenciaSQL);
            conf = true;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "\nError al actualizar registros en la base de datos..." + "\nTipo de eror : " + e.getMessage());
            System.out.println("\nEror al actualizar registros a la base de datos..." + "\nTipo de Error :" + e.getMessage());
            conf = false;
        }
        return conf;

    }

    public boolean borrarBD(String sentenciaSQL) {
        boolean conf;
        try {
            this.statement = this.connection.createStatement();
            this.statement.execute(sentenciaSQL);
            conf = true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "\nError al eliminar registros de la base de ddatos..." + "\nTipo de error : " + e.getMessage());
            System.out.println("\nError al eliminar Registros a la base de datos..." + "\nTipo de error : " + e.getMessage());
            conf = false;
        }
        return conf;
    }

    //funcion para confiar o negar el autoguardado en la base de datos
    public boolean setAutoCommitBD(boolean bandera) {
        boolean conf;
        try {
            this.connection.setAutoCommit(bandera);
            conf = true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "\nError al autocommit registros de la base de datos..." + "\nTipo de error : " + e.getMessage());
            System.out.println("\nError al autocommit Registros a la base de datos..." + "\nTipo de error : " + e.getMessage());
            conf = false;
        }
        return conf;
    }

    //confirmar el guardado de la sentencia ejecutada

    public boolean commitBD() {
        boolean conf;
        try {
            this.connection.commit();
            conf = true;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "\nError al commit registros de la base de datos..." + "\nTipo de error : " + e.getMessage());
            System.out.println("\nError al commit Registros a la base de datos..." + "\nTipo de error : " + e.getMessage());
            conf = false;
        }
        return conf;
    }

    //cerrar la conexion a la base de datos dedes un objeto conexion

    public void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("conexion cerrada...");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "\nError al cerrar la conexion de la base de datos..." + "\nTipo de error : " + e.getMessage());
                System.out.println("\nError al cerrar la conexion a la base de datos..." + "\nTipo de error : " + e.getMessage());

            }
        }
    }

    public void cerrarConexion() {
        this.closeConnection(this.connection);
    }

    //cancelar la ejecucion de una sentencia
    public boolean rollbackBD() {
        boolean conf;
        try {
            this.connection.rollback();
            conf = true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "\nError al rollback de la base de ddatos..." + "\nTipo de error : " + e.getMessage());
            System.out.println("\nError al rollback a la base de datos..." + "\nTipo de error : " + e.getMessage());
            conf = false;
        }
        return conf;
    }
}