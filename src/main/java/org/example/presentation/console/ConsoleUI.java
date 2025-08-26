package org.example.presentation.console;

import org.example.container.DIContainer;
import org.example.model.Usuario;
import org.example.service.interfaces.IUsuarioService;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Interfaz de usuario por consola
 * Implementa principio Single Responsibility
 */
public class ConsoleUI {
    private final IUsuarioService usuarioService;
    private final Scanner scanner;
    
    public ConsoleUI() {
        this.usuarioService = DIContainer.getInstance().getService(IUsuarioService.class);
        this.scanner = new Scanner(System.in);
    }
    
    public void iniciar() {
        System.out.println("=== SISTEMA DE GESTIÓN DE USUARIOS - CONSOLA ===");
        System.out.println("Base de datos actual: " + DIContainer.getInstance().getDatabaseType().toUpperCase());
        
        boolean continuar = true;
        while (continuar) {
            mostrarMenu();
            int opcion = leerOpcion();
            
            switch (opcion) {
                case 1 -> crearUsuario();
                case 2 -> listarUsuarios();
                case 3 -> buscarUsuario();
                case 4 -> actualizarUsuario();
                case 5 -> eliminarUsuario();
                case 6 -> cambiarBaseDatos();
                case 0 -> {
                    continuar = false;
                    System.out.println("¡Hasta luego!");
                }
                default -> System.out.println("Opción inválida");
            }
        }
    }
    
    private void mostrarMenu() {
        System.out.println("\n--- MENÚ PRINCIPAL ---");
        System.out.println("1. Crear usuario");
        System.out.println("2. Listar usuarios");
        System.out.println("3. Buscar usuario");
        System.out.println("4. Actualizar usuario");
        System.out.println("5. Eliminar usuario");
        System.out.println("6. Cambiar base de datos");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }
    
    private int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private void crearUsuario() {
        System.out.println("\n--- CREAR USUARIO ---");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();
        
        try {
            Usuario usuario = new Usuario.Builder()
                .setNombre(nombre)
                .setEmail(email)
                .setTelefono(telefono)
                .build();
            
            usuarioService.crearUsuario(usuario);
            System.out.println("Usuario creado exitosamente");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private void listarUsuarios() {
        System.out.println("\n--- LISTA DE USUARIOS ---");
        try {
            List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();
            if (usuarios.isEmpty()) {
                System.out.println("No hay usuarios registrados");
            } else {
                usuarios.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private void buscarUsuario() {
        System.out.println("\n--- BUSCAR USUARIO ---");
        System.out.print("ID del usuario: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            Optional<Usuario> usuario = usuarioService.obtenerUsuarioPorId(id);
            
            if (usuario.isPresent()) {
                System.out.println("Usuario encontrado: " + usuario.get());
            } else {
                System.out.println("Usuario no encontrado");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private void actualizarUsuario() {
        System.out.println("\n--- ACTUALIZAR USUARIO ---");
        System.out.print("ID del usuario: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            Optional<Usuario> usuarioExistente = usuarioService.obtenerUsuarioPorId(id);
            
            if (usuarioExistente.isEmpty()) {
                System.out.println("Usuario no encontrado");
                return;
            }
            
            System.out.println("Usuario actual: " + usuarioExistente.get());
            System.out.print("Nuevo nombre: ");
            String nombre = scanner.nextLine();
            
            System.out.print("Nuevo email: ");
            String email = scanner.nextLine();
            
            System.out.print("Nuevo teléfono: ");
            String telefono = scanner.nextLine();
            
            Usuario usuarioActualizado = new Usuario.Builder()
                .setId(id)
                .setNombre(nombre)
                .setEmail(email)
                .setTelefono(telefono)
                .build();
            
            usuarioService.actualizarUsuario(usuarioActualizado);
            System.out.println("Usuario actualizado exitosamente");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private void eliminarUsuario() {
        System.out.println("\n--- ELIMINAR USUARIO ---");
        System.out.print("ID del usuario: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            usuarioService.eliminarUsuario(id);
            System.out.println("Usuario eliminado exitosamente");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private void cambiarBaseDatos() {
        System.out.println("\n--- CAMBIAR BASE DE DATOS ---");
        System.out.println("1. H2 (en memoria)");
        System.out.println("2. MySQL");
        System.out.print("Seleccione: ");
        
        int opcion = leerOpcion();
        String nuevaDB = switch (opcion) {
            case 1 -> "h2";
            case 2 -> "mysql";
            default -> null;
        };
        
        if (nuevaDB != null) {
            try {
                DIContainer.getInstance().setDatabaseType(nuevaDB);
                System.out.println("Base de datos cambiada a: " + nuevaDB.toUpperCase());
            } catch (Exception e) {
                System.out.println("Error al cambiar base de datos: " + e.getMessage());
            }
        } else {
            System.out.println("Opción inválida");
        }
    }
}
