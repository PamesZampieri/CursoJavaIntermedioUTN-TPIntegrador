package main;

import controladores.ControladorClientes;
import controladores.ControladorIncidente;
import controladores.ControladorReportes;
import controladores.ControladorTecnicos;
import entidades.Rol;
import entidades.Sesion;
import entidades.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPA_PU");
        return factory.createEntityManager();
    }

    public static void main(String[] args) throws SQLException {
        Scanner entrada = new Scanner(System.in);

        EntityManager em = getEntityManager();

        int opcion;

        do {
            System.out.println("Bienvenido al software de gestión de incidentes.");
            System.out.println("Ingrese la opción deseada:");
            System.out.println("1) Alta Técnico");
            System.out.println("2) Baja Técnico");
            System.out.println("3) Editar Técnico");
            System.out.println("4) Alta Cliente");
            System.out.println("5) Baja Cliente");
            System.out.println("6) Editar Cliente");
            System.out.println("7) Registrar Incidente");
            System.out.println("8) Resolver Incidente");
            System.out.println("9) Crear Reporte Diario");
            System.out.println("10) Crear Reporte Técnicos con más Incidentes Resueltos");
            System.out.println("11) Salir");
            opcion = entrada.nextInt();

            //Rol RRHH
            Rol rolRRHH = new Rol("RRHH", null);
            Usuario usuarioRRHH = new Usuario("lpavan", "123", rolRRHH);
            Sesion sesionRRHH = new Sesion(null, null, usuarioRRHH);
            ControladorTecnicos controladorTecnicos = new ControladorTecnicos(em, sesionRRHH);

            //Rol Área Comercial
            Rol rolAreaComercial = new Rol("Area comercial", null);
            Usuario usuarioAreaComercial = new Usuario("lpavan", "123", rolAreaComercial);
            Sesion sesionAreaComercial = new Sesion(null, null, usuarioAreaComercial);
            ControladorClientes controladorClientes = new ControladorClientes(em, sesionAreaComercial);

            //Rol Mesa Ayuda
            Rol rolMesaAyuda = new Rol("Mesa de ayuda", null);
            Usuario usuarioMesaSAyuda = new Usuario("lpavan", "123", rolMesaAyuda);
            Sesion sesionMesaAyuda = new Sesion(null, null, usuarioMesaSAyuda);
            ControladorIncidente controladorIncidente = new ControladorIncidente(em, sesionMesaAyuda);

            //Controlador Reportes
            ControladorReportes controladorReportes = new ControladorReportes(em, sesionRRHH);

            switch (opcion) {
                case 1:
                    try {
                        controladorTecnicos.altaTecnico();
                    } catch (InputMismatchException e) {
                        e.printStackTrace();
                    }

                    break;
                case 2:
                    controladorTecnicos.bajaTecnico();
                    break;
                case 3:
                    try {
                        controladorTecnicos.editarTecnico();
                    } catch (InputMismatchException e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    try {
                        controladorClientes.altaCliente();
                    } catch (InputMismatchException e) {
                        e.printStackTrace();
                    }
                    break;
                case 5:
                    controladorClientes.bajaCliente();
                    break;
                case 6:
                    try {
                        controladorClientes.editarCliente();
                    } catch (InputMismatchException e) {
                        e.printStackTrace();
                    }
                    break;
                case 7:
                    controladorIncidente.registrarIncidente();
                    break;
                case 8:
                    controladorIncidente.resolverIncidente();
                    break;
                case 9:
                    controladorReportes.crearReporteDiario();
                    break;
                case 10:
                    controladorReportes.crearReporteTecnicosConMasIncidentesResueltos(2);
                    break;
                default:
                    System.out.println("Opción incorrecta!");
                    System.out.println();
            }
        } while (opcion != 11);
    }
}