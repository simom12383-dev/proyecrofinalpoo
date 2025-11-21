import ConexionBaseDeDatos.ConexionBD;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JFrame {

    private JTextField campoUsuario;
    private JPasswordField campoPassword;
    private JButton botonLogin;
    private JCheckBox mostrarPassword;
    private ConexionBD conexion;

    public Login(ConexionBD conexion) {
        this.conexion = conexion;

        setTitle("Login - Tienda de Ropa");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Panel principal con gradiente
        JPanel panelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                Color color1 = new Color(58, 123, 213);
                Color color2 = new Color(0, 210, 255);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panelFondo.setLayout(new GridBagLayout());
        add(panelFondo);

        // Panel central tipo "tarjeta"
        JPanel panelLogin = new JPanel();
        panelLogin.setPreferredSize(new Dimension(350, 250));
        panelLogin.setBackground(Color.WHITE);
        panelLogin.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelLogin.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel titulo = new JLabel("Iniciar Sesión");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panelLogin.add(titulo, gbc);

        // Usuario
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panelLogin.add(lblUsuario, gbc);

        campoUsuario = new JTextField();
        campoUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        campoUsuario.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
        gbc.gridx = 1;
        panelLogin.add(campoUsuario, gbc);

        // Contraseña
        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelLogin.add(lblPassword, gbc);

        campoPassword = new JPasswordField();
        campoPassword.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        campoPassword.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
        gbc.gridx = 1;
        panelLogin.add(campoPassword, gbc);

        // Mostrar/Ocultar contraseña
        mostrarPassword = new JCheckBox("Mostrar contraseña");
        mostrarPassword.setBackground(Color.WHITE);
        mostrarPassword.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        mostrarPassword.addActionListener(e -> togglePassword());
        gbc.gridx = 1;
        gbc.gridy = 3;
        panelLogin.add(mostrarPassword, gbc);

        // Botón login con efecto hover
        botonLogin = new JButton("Iniciar Sesión");
        botonLogin.setFont(new Font("Segoe UI", Font.BOLD, 16));
        botonLogin.setBackground(new Color(58, 123, 213));
        botonLogin.setForeground(Color.WHITE);
        botonLogin.setFocusPainted(false);
        botonLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botonLogin.setBackground(new Color(0, 210, 255));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                botonLogin.setBackground(new Color(58, 123, 213));
            }
        });
        botonLogin.addActionListener(this::iniciarSesion);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panelLogin.add(botonLogin, gbc);

        // Añadir panelLogin al panelFondo
        panelFondo.add(panelLogin);
    }

    private void togglePassword() {
        if (mostrarPassword.isSelected()) {
            campoPassword.setEchoChar((char) 0);
        } else {
            campoPassword.setEchoChar('•');
        }
    }

    private void iniciarSesion(ActionEvent e) {
        String usuario = campoUsuario.getText();
        String password = String.valueOf(campoPassword.getPassword());

        if (usuario.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese usuario y contraseña", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Connection con = conexion.getConnection();
            String sql = "SELECT rol FROM Clientes WHERE usuario=? AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String rol = rs.getString("rol");
                JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso", "Bienvenido", JOptionPane.INFORMATION_MESSAGE);
                new PantallaPrincipal(usuario, rol, conexion).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error en la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Para probar directamente el Login
    public static void main(String[] args) {
        ConexionBD conexion = new ConexionBD();
        SwingUtilities.invokeLater(() -> new Login(conexion).setVisible(true));
    }
}
