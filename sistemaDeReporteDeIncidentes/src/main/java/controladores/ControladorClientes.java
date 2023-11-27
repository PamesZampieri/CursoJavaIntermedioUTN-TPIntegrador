package controladores;

import entidades.Cliente;
import entidades.Servicio;
import entidades.Sesion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ControladorClientes {
    private EntityManager em;
    private Sesion sesion;

    Scanner entrada = new Scanner(System.in);

    public ControladorClientes(EntityManager em, Sesion sesion) {
        this.em = em;
        this.sesion = sesion;
    }

    public ControladorClientes() {
    }

    public void altaCliente() {
        if (!sesion.esUsuarioAreaComercial()) {
            System.out.println("Error, usted no dispone el permiso para realizar esta tarea.");
        }

        System.out.print("Ingrese la razón social del cliente: ");
        String razonSocial = entrada.nextLine();

        System.out.print("Ingrese el cuit del cliente: ");
        String cuit = entrada.nextLine();

        System.out.print("Ingrese el email del cliente: ");
        String email = entrada.nextLine();

        List<Servicio> servicios = em.createQuery("SELECT s FROM Servicio s", Servicio.class).getResultList();

        List<Servicio> serviciosCliente = new ArrayList<>();

        System.out.println("Lista de Servicios:");

        for (Servicio servicio : servicios) {
            System.out.printf("Nombre: %s%n", servicio.getNombre());
            System.out.print("¿Desea agregar el servicio, seleccione 1 para continuar o 2 si no?");
            int opcion = entrada.nextInt();

            if (opcion != 1 && opcion != 2) {
                throw new InputMismatchException("La opción ingresada es incorrecta, finalizando el proceso");
            }

            if (opcion == 1) {
                serviciosCliente.add(servicio);
            }
        }

        System.out.println("Está por crear un Cliente con los siguientes datos:");
        System.out.println("Razón social: " + razonSocial);
        System.out.println("Cuit: " + cuit);
        System.out.println("Email: " + email);
        System.out.println("Servicios: ");
        serviciosCliente.forEach(System.out::println);
        System.out.print("¿Desea confirmar la operación, coloque 1 para continuar o 2 para cancelar?");
        int opcion = entrada.nextInt();

        if (opcion != 1 && opcion != 2) {
            throw new InputMismatchException("La opción ingresada es incorrecta, finalizando el proceso");
        }

        if (opcion == 1) {
            EntityTransaction tx = em.getTransaction();
            tx.begin();

            Cliente cliente = new Cliente(razonSocial, cuit, email, serviciosCliente);
            em.persist(cliente);

            tx.commit();

            System.out.println("Se ha creado el cliente con éxito!");

        } else {
            System.out.println("No se ha creado el cliente.");
        }
    }

    public void bajaCliente() {
        if (!sesion.esUsuarioAreaComercial()) {
            System.out.println("Error, usted no dispone el permiso para realizar esta tarea.");
        }

        System.out.println("Listado de clientes:");

        List<Cliente> clientes = em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();

        for (Cliente cliente : clientes) {
            System.out.printf("ID : %s%nRazón social: %s%nCuit: %s%n%n", cliente.getId(), cliente.getRazonSocial(), cliente.getCuit());
        }

        System.out.println("Escriba el id del cliente que desea dar de baja: ");
        int idClienteADarDeBaja = entrada.nextInt();

        // Buscar el cliente por ID
        Cliente clienteADarDeBaja = em.find(Cliente.class, idClienteADarDeBaja);

        if (clienteADarDeBaja != null) {
            EntityTransaction tx = em.getTransaction();
            tx.begin();

            em.remove(clienteADarDeBaja);

            tx.commit();

            System.out.println("Se ha eliminado el cliente con éxito!");
        }
    }

    public void editarCliente() {
        if (!sesion.esUsuarioAreaComercial()) {
            System.out.println("Error, usted no dispone el permiso para realizar esta tarea.");
        }

        System.out.println("Listado de clientes:");

        List<Cliente> clientes = em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();

        for (Cliente cliente : clientes) {
            System.out.printf("ID : %s%nRazón social: %s%nCuit: %s%n%n", cliente.getId(), cliente.getRazonSocial(),
                    cliente.getCuit());
        }
        System.out.println("Escriba el id del cliente que desea modificar: ");
        int IdClienteAModificar = entrada.nextInt();

        Cliente clienteAModificar = null;

        for (Cliente cliente : clientes) {
            if (cliente.getId() == IdClienteAModificar) {
                clienteAModificar = cliente;
            }
        }

        assert clienteAModificar != null;
        System.out.printf("La razón social del cliente es %s. ¿Desea modificarla? Seleccione opción 1 sino 2%n", clienteAModificar.getRazonSocial());
        int opcion = entrada.nextInt();

        if (opcion != 1 && opcion != 2) {
            throw new InputMismatchException("La opción ingresada es incorrecta, finalizando el proceso");
        }

        if (opcion == 1) {
            System.out.println("Escriba una razón social: ");
            entrada.nextLine();
            clienteAModificar.setRazonSocial(entrada.nextLine());

        }

        System.out.printf("El cuit del cliente es %s. ¿Desea modificarlo? Seleccione opción 1 sino 2%n", clienteAModificar.getCuit());
        opcion = entrada.nextInt();

        if (opcion != 1 && opcion != 2) {
            throw new InputMismatchException("La opción ingresada es incorrecta, finalizando el proceso");
        }

        if (opcion == 1) {
            System.out.println("Escriba un cuit: ");
            entrada.nextLine();
            clienteAModificar.setCuit(entrada.nextLine());
        }

        System.out.printf("El email del cliente es %s. ¿Desea modificarlo? Seleccione opción 1 sino 2%n", clienteAModificar.getEmail());
        opcion = entrada.nextInt();

        if (opcion != 1 && opcion != 2) {
            throw new InputMismatchException("La opción ingresada es incorrecta, finalizando el proceso");
        }

        if (opcion == 1) {
            System.out.println("Escriba un email: ");
            entrada.nextLine();
            clienteAModificar.setEmail(entrada.nextLine());
        }

        System.out.printf("La lista de servicios del cliente son: %s", clienteAModificar.getServicios());
        System.out.println("¿Desea modificarlo? Seleccione opción 1 sino 2");
        opcion = entrada.nextInt();

        if (opcion != 1 && opcion != 2) {
            throw new InputMismatchException("La opción ingresada es incorrecta, finalizando el proceso");
        }

        if (opcion == 1) {
            System.out.println("Lista de Servicios:");

            List<Servicio> servicios = em.createQuery("SELECT s FROM Servicio s", Servicio.class).getResultList();

            List<Servicio> serviciosCliente = new ArrayList<>();

            for (Servicio servicio : servicios) {
                System.out.printf("Nombre: %s%n", servicio.getNombre());
                System.out.print("¿Desea agregar el servicio, seleccione 1 para continuar o 2 si no?");
                opcion = entrada.nextInt();

                if (opcion != 1 && opcion != 2) {
                    throw new InputMismatchException("La opción ingresada es incorrecta, finalizando el proceso");
                }

                if (opcion == 1) {
                    serviciosCliente.add(servicio);
                }
            }
        }

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        em.merge(clienteAModificar);

        tx.commit();

        System.out.println("Se ha modificado el cliente con éxito!");

    }
}