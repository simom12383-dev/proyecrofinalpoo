import ConexionBaseDeDatos.ConexionBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class GestionarClientes extends JFrame {

    private ConexionBD conexion;
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;

    public GestionarClientes(ConexionBD conexion) {
        this.conexion = conexion;
        setTitle("Gestión de Clientes");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        // ------------------------------
        // Encabezado con gradiente
        // ------------------------------
        JPanel panelEncabezado = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                Color color1 = new Color(58, 123, 213);
                Color color2 = new Color(0, 210, 255);
                GradientPaint gp = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panelEncabezado.setPreferredSize(new Dimension(getWidth(), 80));
        panelEncabezado.setLayout(new BorderLayout());

        JLabel lblTitulo = new JLabel("Gestión de Clientes", SwingConstants.CENTER);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        panelEncabezado.add(lblTitulo, BorderLayout.CENTER);
        add(panelEncabezado, BorderLayout.NORTH);

        // ------------------------------
        // Tabla de clientes
        // ------------------------------
        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Nombre", "Teléfono", "Correo", "Dirección"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Evitar edición directa
            }
        };
        tablaClientes = new JTable(modeloTabla);
        tablaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tablaClientes);
        add(scrollPane, BorderLayout.CENTER);

        // ------------------------------
        // Panel de botones
        // ------------------------------
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnAgregar = crearBoton("Agregar Cliente");
        JButton btnModificar = crearBoton("Modificar Cliente");
        JButton btnEliminar = crearBoton("Eliminar Cliente");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);

        add(panelBotones, BorderLayout.SOUTH);

        // ------------------------------
        // Acciones de botones
        // ------------------------------
        btnAgregar.addActionListener(e -> agregarCliente());
        btnModificar.addActionListener(e -> modificarCliente());
        btnEliminar.addActionListener(e -> eliminarCliente());

        cargarClientes();
    }

    // ------------------------------
    // Crear botones estilizados
    // ------------------------------
    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        boton.setBackground(new Color(58, 123, 213));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(0, 210, 255));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(58, 123, 213));
            }
        });
        return boton;
    }

    // ------------------------------
    // Cargar clientes desde DB
    // ------------------------------
    private void cargarClientes() {
        modeloTabla.setRowCount(0); // Limpiar tabla
        try {
            String sql = "SELECT * FROM Clientes";
            PreparedStatement ps = conexion.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                modeloTabla.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("telefono"),
                        rs.getString("correo"),
                        rs.getString("direccion")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar clientes: " + e.getMessage());
        }
    }

    // ------------------------------
    // Agregar cliente
    // ------------------------------
    // ------------------------------
// Agregar cliente (con usuario y contraseña)
// ------------------------------
    private void agregarCliente() {
        JTextField nombreField = new JTextField();
        JTextField telefonoField = new JTextField();
        JTextField correoField = new JTextField();
        JTextField direccionField = new JTextField();
        JTextField usuarioField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        Object[] campos = {
                "Nombre:", nombreField,
                "Teléfono:", telefonoField,
                "Correo:", correoField,
                "Dirección:", direccionField,
                "Usuario:", usuarioField,
                "Contraseña:", passwordField
        };

        int opcion = JOptionPane.showConfirmDialog(this, campos, "Agregar Cliente", JOptionPane.OK_CANCEL_OPTION);
        if (opcion == JOptionPane.OK_OPTION) {
            try {
                String sql = "INSERT INTO Clientes(nombre, telefono, correo, direccion, usuario, password) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = conexion.getConnection().prepareStatement(sql);
                ps.setString(1, nombreField.getText());
                ps.setString(2, telefonoField.getText());
                ps.setString(3, correoField.getText());
                ps.setString(4, direccionField.getText());
                ps.setString(5, usuarioField.getText());
                ps.setString(6, String.valueOf(passwordField.getPassword()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Cliente agregado correctamente");
                cargarClientes();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al agregar cliente: " + e.getMessage());
            }
        }
    }

    // ------------------------------
// Modificar cliente (con usuario y contraseña)
// ------------------------------
    private void modificarCliente() {
        int fila = tablaClientes.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un cliente para modificar");
            return;
        }

        int idCliente = (int) modeloTabla.getValueAt(fila, 0);
        JTextField nombreField = new JTextField((String) modeloTabla.getValueAt(fila, 1));
        JTextField telefonoField = new JTextField((String) modeloTabla.getValueAt(fila, 2));
        JTextField correoField = new JTextField((String) modeloTabla.getValueAt(fila, 3));
        JTextField direccionField = new JTextField((String) modeloTabla.getValueAt(fila, 4));
        JTextField usuarioField = new JTextField(); // Debes cargar el valor desde DB si lo tienes
        JPasswordField passwordField = new JPasswordField();

        Object[] campos = {
                "Nombre:", nombreField,
                "Teléfono:", telefonoField,
                "Correo:", correoField,
                "Dirección:", direccionField,
                "Usuario:", usuarioField,
                "Contraseña:", passwordField
        };

        int opcion = JOptionPane.showConfirmDialog(this, campos, "Modificar Cliente", JOptionPane.OK_CANCEL_OPTION);
        if (opcion == JOptionPane.OK_OPTION) {
            try {
                String sql = "UPDATE Clientes SET nombre=?, telefono=?, correo=?, direccion=?, usuario=?, password=? WHERE id=?";
                PreparedStatement ps = conexion.getConnection().prepareStatement(sql);
                ps.setString(1, nombreField.getText());
                ps.setString(2, telefonoField.getText());
                ps.setString(3, correoField.getText());
                ps.setString(4, direccionField.getText());
                ps.setString(5, usuarioField.getText());
                ps.setString(6, String.valueOf(passwordField.getPassword()));
                ps.setInt(7, idCliente);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Cliente modificado correctamente");
                cargarClientes();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al modificar cliente: " + e.getMessage());
            }
        }
    }

    // ------------------------------
    // Eliminar cliente con advertencia
    // ------------------------------
    private void eliminarCliente() {
        int fila = tablaClientes.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un cliente para eliminar");
            return;
        }

        int idCliente = (int) modeloTabla.getValueAt(fila, 0);
        String nombre = (String) modeloTabla.getValueAt(fila, 1);

        try {
            // Verificar ventas
            String sqlVentas = "SELECT COUNT(*) FROM Ventas WHERE cliente_id=?";
            PreparedStatement psVentas = conexion.getConnection().prepareStatement(sqlVentas);
            psVentas.setInt(1, idCliente);
            ResultSet rs = psVentas.executeQuery();
            rs.next();
            int cantidadVentas = rs.getInt(1);

            int confirm;
            if (cantidadVentas > 0) {
                confirm = JOptionPane.showConfirmDialog(
                        this,
                        "El cliente " + nombre + " tiene " + cantidadVentas + " venta(s) registradas. ¿Deseas eliminarlo de todas formas?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION
                );
            } else {
                confirm = JOptionPane.showConfirmDialog(
                        this,
                        "¿Deseas eliminar al cliente " + nombre + "?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION
                );
            }

            if (confirm == JOptionPane.YES_OPTION) {
                // Eliminar ventas primero
                String eliminarVentas = "DELETE FROM Ventas WHERE cliente_id=?";
                PreparedStatement psEliminarVentas = conexion.getConnection().prepareStatement(eliminarVentas);
                psEliminarVentas.setInt(1, idCliente);
                psEliminarVentas.executeUpdate();

                // Eliminar cliente
                String eliminarCliente = "DELETE FROM Clientes WHERE id=?";
                PreparedStatement psEliminarCliente = conexion.getConnection().prepareStatement(eliminarCliente);
                psEliminarCliente.setInt(1, idCliente);
                psEliminarCliente.executeUpdate();

                JOptionPane.showMessageDialog(this, "Cliente eliminado correctamente");
                cargarClientes();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al eliminar cliente: " + e.getMessage());
        }
    }

    // ------------------------------
    // Para probar la ventana directamente
    // ------------------------------
    public static void main(String[] args) {
        ConexionBD conexion = new ConexionBD();
        SwingUtilities.invokeLater(() -> new GestionarClientes(conexion).setVisible(true));
    }
}
