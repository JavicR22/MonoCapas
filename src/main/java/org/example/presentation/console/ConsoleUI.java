package org.example.presentation.console;

import org.example.controller.ClienteController;
import org.example.controller.CompraController;
import org.example.controller.ProductoController;
import org.example.controller.UsuarioController;
import org.example.model.Usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Interfaz de usuario por consola
 * Implementa principio Single Responsibility
 */
public class ConsoleUI {
    private final UsuarioController usuarioController;
    private final ProductoController productoController;
    private final CompraController compraController;
    private final ClienteController clienteController;

    private final Scanner scanner;

    public ConsoleUI(UsuarioController usuarioController, ProductoController productoController,
                     CompraController compraController, ClienteController clienteController) {
        this.usuarioController=usuarioController;
        this.clienteController=clienteController;
        this.compraController=compraController;
        this.productoController=productoController;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        System.out.println("=== SISTEMA DE GESTIÓN DE USUARIOS - CONSOLA ===");
        boolean continuar = true;
        while (continuar) {
            mostrarMenuInicial();
            int opcion = leerOpcion();

            switch (opcion) {
                case 1 -> {

                    mostrarMenuGestionarClientes();
                }
                case 2 ->
                {


                    mostrarMenuGestionarCompras();
                }
                case 3 -> mostrarMenuGestionarDocumentos();
                case 4 -> { // ahora coincide con el texto "4. Salir"
                    continuar = false;
                    System.out.println("¡Hasta luego!");
                }
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private void mostrarMenuGestionarDocumentos() {
        System.out.println("\n--- MENÚ DE GESTIÓN DE DOCUMENTOS ---");
        // Aquí pondrás las opciones
    }

    private void mostrarMenuGestionarCompras() {
        boolean continuar = true;

        while (continuar) {
        System.out.println("\n--- MENÚ DE GESTIONAR COMPRAS ---");
        System.out.println("1. Gestionar productos");
        System.out.println("2. Gestionar compras");
        System.out.println("3. Gestionar clientes");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");

        // Aquí pondrás las opciones
        int opcion = leerOpcion();

        switch (opcion) {
            case 1 -> mostrarMenuProductos();
            case 2 -> mostrarMenuCompras();
            case 3 -> mostrarMenuClientes();
            case 0 -> continuar = false; // vuelve al menú principal
            default -> System.out.println("Opción inválida");
        }
        }
    }

    private void mostrarMenuGestionarClientes() {
        boolean continuar = true;

        while (continuar) {

            System.out.println("Base de datos actual: ");
            System.out.println("\n--- MENÚ DE GESTIÓN DE CLIENTES ---");
            System.out.println("1. Crear usuario");
            System.out.println("2. Listar usuarios");
            System.out.println("3. Buscar usuario");
            System.out.println("4. Actualizar usuario");
            System.out.println("5. Eliminar usuario");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            int opcion = leerOpcion();

            switch (opcion) {
                case 1 -> crearUsuario();
                case 2 -> listarUsuarios();
                case 3 -> buscarUsuario();
                case 4 -> actualizarUsuario();
                case 5 -> eliminarUsuario();
                case 0 -> continuar = false; // vuelve al menú principal
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private void mostrarMenuInicial() {
        System.out.println("\n--- MENÚ PRINCIPAL ---");
        System.out.println("1. Gestionar clientes");
        System.out.println("2. Gestionar compras");
        System.out.println("3. Gestionar documentos");
        System.out.println("4. Salir");
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

        System.out.println(usuarioController.crearUsuario(nombre, email, telefono));
    }

    private void listarUsuarios() {
        System.out.println("\n--- LISTA DE USUARIOS ---");
        List<Usuario> usuarios = usuarioController.listarUsuarios();
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados");
        } else {
            usuarios.forEach(System.out::println);
        }
    }

    private void buscarUsuario() {
        System.out.println("\n--- BUSCAR USUARIO ---");
        System.out.print("ID del usuario: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            Optional<Usuario> usuario = usuarioController.buscarUsuario(id);
            usuario.ifPresentOrElse(
                    u -> System.out.println("Usuario encontrado: " + u),
                    () -> System.out.println("Usuario no encontrado")
            );
        } catch (NumberFormatException e) {
            System.out.println("ID inválido");
        }
    }

    private void actualizarUsuario() {
        System.out.println("\n--- ACTUALIZAR USUARIO ---");
        System.out.print("ID del usuario: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            System.out.print("Nuevo nombre: ");
            String nombre = scanner.nextLine();
            System.out.print("Nuevo email: ");
            String email = scanner.nextLine();
            System.out.print("Nuevo teléfono: ");
            String telefono = scanner.nextLine();

            System.out.println(usuarioController.actualizarUsuario(id, nombre, email, telefono));
        } catch (NumberFormatException e) {
            System.out.println("ID inválido");
        }
    }

    private void eliminarUsuario() {
        System.out.println("\n--- ELIMINAR USUARIO ---");
        System.out.print("ID del usuario: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            System.out.println(usuarioController.eliminarUsuario(id));
        } catch (NumberFormatException e) {
            System.out.println("ID inválido");
        }
    }


    /* ======================Clientes============================*/

    private void mostrarMenuClientes() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n--- MENÚ CLIENTES ---");
            System.out.println("1. Crear Cliente");
            System.out.println("2. Listar Clientes");
            System.out.println("3. Buscar Cliente");
            System.out.println("4. Actualizar Cliente");
            System.out.println("5. Eliminar Cliente");
            System.out.println("0. Volver");
            int opcion = leerOpcion();

            switch (opcion) {
                case 1 -> {
                    System.out.print("Nombre: ");
                    String n = scanner.nextLine();
                    System.out.print("Email: ");
                    String e = scanner.nextLine();
                    System.out.println(clienteController.crearCliente(n, e));
                }
                case 2 -> clienteController.listarClientes().forEach(System.out::println);
                case 3 -> {
                    System.out.print("ID: ");
                    Long id = Long.parseLong(scanner.nextLine());
                    System.out.println(clienteController.buscarClientes(id).orElse(null));
                }
                case 4 -> {
                    System.out.print("ID: ");
                    Long id = Long.parseLong(scanner.nextLine());
                    System.out.print("Nuevo nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Nuevo email: ");
                    String email = scanner.nextLine();
                    System.out.println(clienteController.actualizarClientes(id, nombre, email));
                }
                case 5 -> {
                    System.out.print("ID: ");
                    Long id = Long.parseLong(scanner.nextLine());
                    System.out.println(clienteController.eliminarCliente(id));
                }
                case 0 -> continuar = false;
            }
        }
    }
    /* ======================Producto============================*/

    private void mostrarMenuProductos() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n--- MENÚ PRODUCTOS ---");
            System.out.println("1. Crear Producto");
            System.out.println("2. Listar Productos");
            System.out.println("3. Buscar Producto");
            System.out.println("4. Actualizar Producto");
            System.out.println("5. Eliminar Producto");
            System.out.println("0. Volver");
            int opcion = leerOpcion();

            switch (opcion) {
                case 1 -> {
                    System.out.print("Nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Precio: ");
                    double precio = Double.parseDouble(scanner.nextLine());
                    System.out.println(productoController.crearProducto(nombre, precio));
                }
                case 2 -> productoController.listarProductos().forEach(System.out::println);
                case 3 -> {
                    System.out.print("ID: ");
                    Long id = Long.parseLong(scanner.nextLine());
                    System.out.println(productoController.buscarProductoPorId(id).orElse(null));
                }
                case 4 -> {
                    System.out.print("ID: ");
                    Long id = Long.parseLong(scanner.nextLine());
                    System.out.print("Nuevo nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Nuevo precio: ");
                    double precio = Double.parseDouble(scanner.nextLine());
                    System.out.println(productoController.actualizarProducto(id, nombre, precio));
                }
                case 5 -> {
                    System.out.print("ID: ");
                    Long id = Long.parseLong(scanner.nextLine());
                    System.out.println(productoController.eliminarProducto(id));
                }
                case 0 -> continuar = false;
            }
        }
    }
    /* ======================Compra============================*/
    private void mostrarMenuCompras() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n--- MENÚ COMPRAS ---");
            System.out.println("1. Crear Compra");
            System.out.println("2. Listar Compras");
            System.out.println("3. Buscar Compra");
            System.out.println("4. Eliminar Compra");
            System.out.println("0. Volver");
            int opcion = leerOpcion();

            switch (opcion) {
                case 1 -> {
                    System.out.print("ID Cliente: ");
                    Long clienteId = Long.parseLong(scanner.nextLine());

                    List<Long> productoIds = new ArrayList<>();
                    while (true) {
                        System.out.print("ID Producto (0 para terminar): ");
                        Long productoId = Long.parseLong(scanner.nextLine());
                        if (productoId == 0) break;
                        productoIds.add(productoId);
                    }

                    String resultado = compraController.crearCompra(clienteId, productoIds, LocalDate.now());
                    System.out.println(resultado);
                }
                case 2 -> compraController.listarCompras().forEach(System.out::println);
                case 3 -> {
                    System.out.print("ID: ");
                    Long id = Long.parseLong(scanner.nextLine());
                    System.out.println(compraController.buscarCompraPorId(id).orElse(null));
                }
                case 4 -> {
                    System.out.print("ID: ");
                    Long id = Long.parseLong(scanner.nextLine());
                    System.out.println(compraController.eliminarCompra(id));
                }
                case 0 -> continuar = false;
            }
        }
    }
}




