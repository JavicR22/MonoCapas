package org.example.presentation.desktop;

import org.example.controller.UsuarioController;
import org.example.model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Interfaz de usuario de escritorio usando Swing
 * Implementa principio Single Responsibility
 */
public class DesktopUI extends JFrame {
    private UsuarioController usuarioController;
    private JTable tablaUsuarios;
    private DefaultTableModel modeloTabla;
    private JTextField txtNombre, txtEmail, txtTelefono;
    private JLabel lblBaseDatos;
    
    public DesktopUI() {
        inicializarComponentes();
        configurarVentana();
        cargarUsuarios();
    }
    
    private void inicializarComponentes() {
        setLayout(new BorderLayout());
        
        // Panel superior con información de BD
        JPanel panelSuperior = new JPanel(new FlowLayout());

        JButton btnCambiarDB = new JButton("Cambiar BD");

        
        panelSuperior.add(lblBaseDatos);
        panelSuperior.add(btnCambiarDB);
        add(panelSuperior, BorderLayout.NORTH);
        
        // Panel central con tabla
        String[] columnas = {"ID", "Nombre", "Email", "Teléfono"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaUsuarios = new JTable(modeloTabla);
        tablaUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaUsuarios.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarUsuarioSeleccionado();
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tablaUsuarios);
        add(scrollPane, BorderLayout.CENTER);
        
        // Panel inferior con formulario
        JPanel panelFormulario = crearPanelFormulario();
        add(panelFormulario, BorderLayout.SOUTH);
    }
    
    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Gestión de Usuarios"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Campos de texto
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        txtNombre = new JTextField(20);
        panel.add(txtNombre, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        txtEmail = new JTextField(20);
        panel.add(txtEmail, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 1;
        txtTelefono = new JTextField(20);
        panel.add(txtTelefono, gbc);
        
        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        
        JButton btnCrear = new JButton("Crear");
        btnCrear.addActionListener(e -> crearUsuario());
        panelBotones.add(btnCrear);
        
        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> actualizarUsuario());
        panelBotones.add(btnActualizar);
        
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(e -> eliminarUsuario());
        panelBotones.add(btnEliminar);
        
        JButton btnLimpiar = new JButton("Limpiar");
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        panelBotones.add(btnLimpiar);
        
        JButton btnRefrescar = new JButton("Refrescar");
        btnRefrescar.addActionListener(e -> cargarUsuarios());
        panelBotones.add(btnRefrescar);
        
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(panelBotones, gbc);
        
        return panel;
    }
    
    private void configurarVentana() {
        setTitle("Sistema de Gestión de Usuarios - Escritorio");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
    }
    
    private void cargarUsuarios() {
        try {
            modeloTabla.setRowCount(0);
            List<Usuario> usuarios = usuarioController.listarUsuarios();
            
            for (Usuario usuario : usuarios) {
                Object[] fila = {
                    usuario.getId(),
                    usuario.getNombre(),
                    usuario.getEmail(),
                    usuario.getTelefono()
                };
                modeloTabla.addRow(fila);
            }
        } catch (Exception e) {
            mostrarError("Error al cargar usuarios: " + e.getMessage());
        }
    }
    
    private void cargarUsuarioSeleccionado() {
        int filaSeleccionada = tablaUsuarios.getSelectedRow();
        if (filaSeleccionada >= 0) {
            txtNombre.setText((String) modeloTabla.getValueAt(filaSeleccionada, 1));
            txtEmail.setText((String) modeloTabla.getValueAt(filaSeleccionada, 2));
            txtTelefono.setText((String) modeloTabla.getValueAt(filaSeleccionada, 3));
        }
    }
    
    private void crearUsuario() {
        try {

            String nombre = txtNombre.getText().trim();
            String email = txtEmail.getText().trim();
            String telefono = txtTelefono.getText().trim();

            usuarioController.crearUsuario(nombre, email, telefono);
            mostrarMensaje("Usuario creado exitosamente");
            limpiarFormulario();
            cargarUsuarios();
        } catch (Exception e) {
            mostrarError("Error al crear usuario: " + e.getMessage());
        }
    }
    
    private void actualizarUsuario() {
        int filaSeleccionada = tablaUsuarios.getSelectedRow();
        if (filaSeleccionada < 0) {
            mostrarError("Seleccione un usuario para actualizar");
            return;
        }
        
        try {
            Long id = (Long) modeloTabla.getValueAt(filaSeleccionada, 0);
            String nombre = txtNombre.getText().trim();
            String email = txtEmail.getText().trim();
            String telefono = txtTelefono.getText().trim();


            usuarioController.actualizarUsuario(id, nombre,email, telefono);
            mostrarMensaje("Usuario actualizado exitosamente");
            limpiarFormulario();
            cargarUsuarios();
        } catch (Exception e) {
            mostrarError("Error al actualizar usuario: " + e.getMessage());
        }
    }
    
    private void eliminarUsuario() {
        int filaSeleccionada = tablaUsuarios.getSelectedRow();
        if (filaSeleccionada < 0) {
            mostrarError("Seleccione un usuario para eliminar");
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro de eliminar este usuario?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                Long id = (Long) modeloTabla.getValueAt(filaSeleccionada, 0);
                usuarioController.eliminarUsuario(id);
                mostrarMensaje("Usuario eliminado exitosamente");
                limpiarFormulario();
                cargarUsuarios();
            } catch (Exception e) {
                mostrarError("Error al eliminar usuario: " + e.getMessage());
            }
        }
    }
    


    
    private void limpiarFormulario() {
        txtNombre.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
        tablaUsuarios.clearSelection();
    }
    
    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    public void mostrar() {
        SwingUtilities.invokeLater(() -> setVisible(true));
    }
}
