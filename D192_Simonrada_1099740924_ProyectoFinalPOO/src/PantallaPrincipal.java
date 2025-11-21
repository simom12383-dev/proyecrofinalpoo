import ConexionBaseDeDatos.ConexionBD;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PantallaPrincipal extends JFrame {

    private String usuarioLogueado;
    private String rolLogueado;
    private ConexionBD conexion;

    public PantallaPrincipal(String usuario, String rol, ConexionBD conexion) {
        this.usuarioLogueado = usuario;
        this.rolLogueado = rol;
        this.conexion = conexion;

        setTitle("Sistema de Tienda de Ropa - Pantalla Principal");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

        JLabel lblTitulo = new JLabel("Bienvenido: " + usuarioLogueado + " (" + rolLogueado + ")", SwingConstants.CENTER);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        panelEncabezado.add(lblTitulo, BorderLayout.CENTER);

        add(panelEncabezado, BorderLayout.NORTH);

        // ------------------------------
        // Panel central con botones
        // ------------------------------
        JPanel panelCentral = new JPanel();
        panelCentral.setBackground(Color.WHITE);
        panelCentral.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.BOTH;

        int fila = 0;

        // Botones para admin
        if (rolLogueado.equalsIgnoreCase("admin")) {
            JButton btnGestionarClientes = crearBoton("Gestionar Clientes");
            JButton btnInventario = crearBoton("Inventario");

            gbc.gridx = 0;
            gbc.gridy = fila++;
            panelCentral.add(btnGestionarClientes, gbc);

            gbc.gridx = 0;
            gbc.gridy = fila++;
            panelCentral.add(btnInventario, gbc);

            btnGestionarClientes.addActionListener(this::abrirGestionarClientes);
            btnInventario.addActionListener(this::abrirInventario);
        }

        // Botones para todos los roles
        JButton btnVerClientes = crearBoton("Ver Lista de Clientes");
        JButton btnCerrarSesion = crearBoton("Cerrar Sesión");

        gbc.gridx = 0;
        gbc.gridy = fila++;
        panelCentral.add(btnVerClientes, gbc);

        gbc.gridx = 0;
        gbc.gridy = fila++;
        panelCentral.add(btnCerrarSesion, gbc);

        btnVerClientes.addActionListener(this::verClientes);
        btnCerrarSesion.addActionListener(e -> {
            dispose();
            new Login(conexion).setVisible(true);
        });

        add(panelCentral, BorderLayout.CENTER);
    }

    // ------------------------------
    // Método para crear botones profesionales
    // ------------------------------
    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        boton.setBackground(new Color(58, 123, 213));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto hover
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
    // Métodos de acción
    // ------------------------------
    private void abrirGestionarClientes(ActionEvent e) {
        new GestionarClientes(conexion).setVisible(true);
    }

    private void abrirInventario(ActionEvent e) {
        JOptionPane.showMessageDialog(this, "Aquí irá el módulo de inventario");
    }

    private void verClientes(ActionEvent e) {
        JOptionPane.showMessageDialog(this, "Aquí se mostrará la lista de clientes");
    }

    // ------------------------------
    // Método main para pruebas
    // ------------------------------
    public static void main(String[] args) {
        ConexionBD conexion = new ConexionBD();
        SwingUtilities.invokeLater(() -> new PantallaPrincipal("AdminEjemplo", "admin", conexion).setVisible(true));
    }
}
