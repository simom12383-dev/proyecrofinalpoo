import ConexionBaseDeDatos.ConexionBD;

public class Main {
    public static void main(String[] args) {
        // 1️⃣ Crear la conexión a la base de datos
        ConexionBD conexion = new ConexionBD();

        // 2️⃣ Abrir la ventana de login pasando la conexión
        Login login = new Login(conexion);
        login.setVisible(true);
    }
}
