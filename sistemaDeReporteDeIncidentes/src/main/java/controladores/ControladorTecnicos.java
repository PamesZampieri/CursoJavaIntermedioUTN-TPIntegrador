package controladores;

import entidades.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ControladorTecnicos {
    private EntityManager em;
    private Sesion sesion;

    Scanner entrada = new Scanner(System.in);

    public ControladorTecnicos(EntityManager em, Sesion sesion) {
        this.em = em;
        this.sesion = sesion;
    }

    public ControladorTecnicos() {
    }

    public void altaTecnico() {
        if (!sesion.esUsuarioRRHH()) {
            System.out.println("Error, usted no dispone el permiso para realizar esta tarea.");
            System.out.println();

        } else if (sesion.esUsuarioRRHH()) {

            System.out.print("Ingrese el nombre del técnico: ");
            String nombre = entrada.nextLine();

            System.out.print("Ingrese el apellido del técnico: ");
            String apellido = entrada.nextLine();

            System.out.print("Ingrese el email del técnico: ");
            String email = entrada.nextLine();

            System.out.print("Ingrese el teléfono del técnico: ");
            String telefono = entrada.nextLine();

            List<Especialidad> especialidades = em.createQuery("SELECT e FROM Especialidad e", Especialidad.class).getResultList();

            List<Especialidad> especialidadesTecnico = new ArrayList<>();

            System.out.println("Lista de Especialidades:");

            for (Especialidad especialidad : especialidades) {
                System.out.printf("Nombre: %s%n", especialidad.getNombre());
                System.out.print("¿Desea agregar la especialidad, seleccione 1 para continuar o 2 si no?");
                int opcion = entrada.nextInt();

                if (opcion != 1 && opcion != 2) {
                    throw new InputMismatchException("La opción ingresada es incorrecta, finalizando el proceso");
                }

                if (opcion == 1) {
                    especialidadesTecnico.add(especialidad);
                }
            }

            List<TipoNotificacion> tiposNotificaciones = em.createQuery("SELECT t FROM TipoNotificacion t", TipoNotificacion.class).getResultList();

            System.out.println();
            System.out.println("Escriba la opción de Tipo de notificación del técnico:");

            for (int i = 0; i < tiposNotificaciones.size(); i++) {
                System.out.printf("%d) %s%n", i + 1, tiposNotificaciones.get(i).getNombre());
            }

            int opcion = entrada.nextInt();

            TipoNotificacion tipoNotificacion = tiposNotificaciones.get(opcion - 1);

            System.out.println("Está por crear un Técnico con los siguientes datos:");
            System.out.println("Nombre: " + nombre);
            System.out.println("Apellido: " + apellido);
            System.out.println("Email: " + email);
            System.out.println("Teléfono: " + telefono);
            System.out.println("Especialidades: ");
            especialidadesTecnico.forEach(System.out::println);
            System.out.println("Tipo de notificación: " + tipoNotificacion);
            System.out.print("¿Desea confirmar la operación, coloque 1 para continuar o 2 para cancelar?");
            opcion = entrada.nextInt();

            if (opcion != 1 && opcion != 2) {
                throw new InputMismatchException("La opción ingresada es incorrecta, finalizando el proceso");
            }

            if (opcion == 1) {
                EntityTransaction tx = em.getTransaction();
                tx.begin();

                Tecnico tecnico = new Tecnico(nombre, apellido, email, telefono, especialidades, tipoNotificacion);
                em.persist(tecnico);

                tx.commit();

                System.out.println("Se ha creado el técnico con éxito!");

            } else {
                System.out.println("No se ha creado el técnico.");
            }
        }
    }

    public void bajaTecnico() {
        if (!sesion.esUsuarioRRHH()) {
            System.out.println("Error, usted no dispone el permiso para realizar esta tarea.");
            System.out.println();

        } else if (sesion.esUsuarioRRHH()) {

            System.out.println("Listado de técnicos:");

            List<Tecnico> tecnicos = em.createQuery("SELECT e FROM Tecnico e", Tecnico.class).getResultList();

            for (Tecnico tecnico : tecnicos) {
                System.out.printf("ID : %s%nNombre: %s%nApellido: %s%n%n", tecnico.getId(), tecnico.getNombre(), tecnico.getApellido());
            }

            System.out.println("Escriba el id del técnico que desea dar de baja: ");
            int idTecnicoADarDeBaja = entrada.nextInt();

            // Buscar el técnico por ID
            Tecnico tecnicoADarDeBaja = em.find(Tecnico.class, idTecnicoADarDeBaja);

            if (tecnicoADarDeBaja != null) {
                EntityTransaction tx = em.getTransaction();
                tx.begin();

                // Lista de incidentes asociados.
                List<Incidente> incidentesAsociados = em.createQuery(
                                "SELECT i FROM Incidente i WHERE i.tecnico.id = :idTecnico", Incidente.class)
                        .setParameter("idTecnico", tecnicoADarDeBaja.getId())
                        .getResultList();

                // En estado creado.
                boolean hayIncidentesCreados = incidentesAsociados.stream()
                        .anyMatch(Incidente::estaEstadoCreado);

                if (hayIncidentesCreados) {
                    throw new IllegalStateException("No se puede dar de baja al técnico. Tiene incidentes en estado creado.");
                }

                // Eliminamos los incidentes asociados.
                for (Incidente incidente : incidentesAsociados) {
                    em.remove(incidente);
                }

                em.remove(tecnicoADarDeBaja);

                tx.commit();

                System.out.println("Se ha eliminado el técnico con éxito!");
                System.out.println();
            }
        }
    }

    public void editarTecnico() {
        if (!sesion.esUsuarioRRHH()) {
            System.out.println("Error, usted no dispone el permiso para realizar esta tarea.");
            System.out.println();

        } else if (sesion.esUsuarioRRHH()) {

            System.out.println("Listado de técnicos:");

            List<Tecnico> tecnicos = em.createQuery("SELECT e FROM Tecnico e", Tecnico.class).getResultList();

            for (Tecnico tecnico : tecnicos) {
                System.out.printf("ID : %s%nNombre: %s%nApellido: %s%n%n", tecnico.getId(), tecnico.getNombre(),
                        tecnico.getApellido());
            }

            System.out.println("Escriba el id del técnico que desea modificar: ");
            int IdTecnicoAModificar = entrada.nextInt();

            Tecnico tecnicoAModificar = null;

            for (Tecnico tecnico : tecnicos) {
                if (tecnico.getId() == IdTecnicoAModificar) {
                    tecnicoAModificar = tecnico;
                }
            }

            System.out.printf("El nombre del técnico es %s. ¿Desea modificarlo? Seleccione opción 1 sino 2%n", tecnicoAModificar.getNombre());
            int opcion = entrada.nextInt();

            if (opcion != 1 && opcion != 2) {
                throw new InputMismatchException("La opción ingresada es incorrecta, finalizando el proceso");
            }

            if (opcion == 1) {
                System.out.println("Escriba un nombre: ");
                entrada.nextLine();
                tecnicoAModificar.setNombre(entrada.nextLine());

            }

            System.out.printf("El apellido del técnico es %s. ¿Desea modificarlo? Seleccione opción 1 sino 2%n", tecnicoAModificar.getApellido());
            opcion = entrada.nextInt();

            if (opcion != 1 && opcion != 2) {
                throw new InputMismatchException("La opción ingresada es incorrecta, finalizando el proceso");
            }

            if (opcion == 1) {
                System.out.println("Escriba un apellido: ");
                entrada.nextLine();
                tecnicoAModificar.setApellido(entrada.nextLine());
            }

            System.out.printf("El email del técnico es %s. ¿Desea modificarlo? Seleccione opción 1 sino 2%n", tecnicoAModificar.getEmail());
            opcion = entrada.nextInt();

            if (opcion != 1 && opcion != 2) {
                throw new InputMismatchException("La opción ingresada es incorrecta, finalizando el proceso");
            }

            if (opcion == 1) {
                System.out.println("Escriba un email: ");
                entrada.nextLine();
                tecnicoAModificar.setEmail(entrada.nextLine());
            }

            System.out.printf("El teléfono del técnico es %s. ¿Desea modificarlo? Seleccione opción 1 sino 2%n", tecnicoAModificar.getTelefono());
            opcion = entrada.nextInt();

            if (opcion != 1 && opcion != 2) {
                throw new InputMismatchException("La opción ingresada es incorrecta, finalizando el proceso");
            }

            if (opcion == 1) {
                System.out.println("Escriba un teléfono: ");
                entrada.nextLine();
                tecnicoAModificar.setTelefono(entrada.nextLine());
            }

            System.out.printf("La lista de especialidades del técnico son: %s", tecnicoAModificar.getEspecialidades());
            System.out.println("¿Desea modificarlo? Seleccione opción 1 sino 2");
            opcion = entrada.nextInt();

            if (opcion != 1 && opcion != 2) {
                throw new InputMismatchException("La opción ingresada es incorrecta, finalizando el proceso");
            }

            if (opcion == 1) {
                System.out.println("Lista de Especialidades:");

                List<Especialidad> especialidades = em.createQuery("SELECT e FROM Especialidad e", Especialidad.class).getResultList();

                List<Especialidad> especialidadesTecnico = new ArrayList<>();

                for (Especialidad especialidad : especialidades) {
                    System.out.printf("Nombre: %s%n", especialidad.getNombre());
                    System.out.print("¿Desea agregar la especialidad, seleccione 1 para continuar o 2 si no?");
                    opcion = entrada.nextInt();

                    if (opcion != 1 && opcion != 2) {
                        throw new InputMismatchException("La opción ingresada es incorrecta, finalizando el proceso");
                    }

                    if (opcion == 1) {
                        especialidadesTecnico.add(especialidad);
                    }
                }

            }

            System.out.printf("El tipo de notificación del técnico es: %s", tecnicoAModificar.getTipoNotificacion());
            System.out.println("¿Desea modificarla? Seleccione opción 1 sino 2");
            opcion = entrada.nextInt();

            if (opcion != 1 && opcion != 2) {
                throw new InputMismatchException("La opción ingresada es incorrecta, finalizando el proceso");
            }

            if (opcion == 1) {
                List<TipoNotificacion> tiposNotificaciones = em.createQuery("SELECT t FROM TipoNotificacion t", TipoNotificacion.class).getResultList();

                System.out.println();
                System.out.println("Escriba la opción de Tipo de notificación del técnico:");

                for (int i = 0; i < tiposNotificaciones.size(); i++) {
                    System.out.printf("%d) %s%n", i + 1, tiposNotificaciones.get(i).getNombre());
                }

                opcion = entrada.nextInt();

                TipoNotificacion tipoNotificacion = tiposNotificaciones.get(opcion - 1);
            }

            EntityTransaction tx = em.getTransaction();
            tx.begin();

            em.merge(tecnicoAModificar);

            tx.commit();

            System.out.println("Se ha modificado el técnico con éxito!");
            System.out.println();
        }
    }
}