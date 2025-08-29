package org.example;

import org.example.config.H2DatabaseInitializar;
import org.example.config.MySQLDatabaseInitializer;
import org.example.controller.ClienteController;
import org.example.controller.CompraController;
import org.example.controller.ProductoController;
import org.example.controller.UsuarioController;
import org.example.factory.ControllerFactory;
import org.example.presentation.console.ConsoleUI;
import org.example.presentation.desktop.DesktopUI;

import javax.swing.*;
import java.util.Scanner;

/**
 * Clase principal que permite seleccionar entre interfaz de consola o escritorio
 * Implementa principio Single Responsibility
 */
public class Main {
    public static void main(String[] args) {


        MySQLDatabaseInitializer.inicializar();
        H2DatabaseInitializar.inicializar();
        // Uso la fábrica para crear controladores
        UsuarioController usuarioController = ControllerFactory.crearUsuarioController();
        CompraController compraController= ControllerFactory.crearCompraController();
        ProductoController productoController = ControllerFactory.crearProductoController();
        ClienteController clienteController= ControllerFactory.crearClienteController();

        System.out.println("=== SISTEMA MONOLÍTICO CON CAPAS LÓGICAS ===");
        System.out.println("Seleccione la interfaz de usuario:");
        System.out.println("1. Consola");
        System.out.println("2. Escritorio (Swing)");
        System.out.print("Opción: ");
        
        Scanner scanner = new Scanner(System.in);
        try {
            int opcion = Integer.parseInt(scanner.nextLine());
            
            switch (opcion) {
                case 1 -> {
                    ConsoleUI consoleUI = new ConsoleUI(usuarioController,productoController,compraController,
                            clienteController);
                    consoleUI.iniciar();
                }
                case 2 -> {
                    SwingUtilities.invokeLater(() -> {
                        try {
                            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                        } catch (Exception e) {
                            // Usar look and feel por defecto
                        }
                        
                        DesktopUI desktopUI = new DesktopUI();
                        desktopUI.mostrar();
                    });
                }
                default -> System.out.println("Opción inválida");
            }
        } catch (NumberFormatException e) {
            System.out.println("Opción inválida");
        } finally {
            scanner.close();
        }
    }
}
