package controladores;

import entidades.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDateTime;
import java.util.*;

public class ControladorIncidente {
    private EntityManager em;
    private Sesion sesion;

    Scanner entrada = new Scanner(System.in);

    public ControladorIncidente(EntityManager em, Sesion sesion) {
        this.em = em;
        this.sesion = sesion;
    }

    public ControladorIncidente() {
    }

    public void registrarIncidente() {
        if (!sesion.esUsuarioMesaAyuda()) {
            System.out.println("Error, usted no dispone el permiso para realizar esta tarea.");
        }

        System.out.println("Ingrese la razón social del cliente: ");
        String razonSocial = entrada.nextLine();

        System.out.println("Ingrese el cuit del cliente: ");
        String cuit = entrada.nextLine();

        List<Cliente> clientes = em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();

        Cliente clienteIncidente = null;
        for (Cliente cliente : clientes) {
            if (cliente.getRazonSocial().equals(razonSocial) && cliente.getCuit().equals(cuit)) {
                clienteIncidente = cliente;
            }
        }

        System.out.println("Servicios del cliente");
        for (int i = 0; i < clienteIncidente.getServicios().size(); i++) {
            System.out.printf("%d) %s%n", i + 1, clienteIncidente.getServicios().get(i).getNombre());
        }

        System.out.println("Seleccione el id del servicio que desea reportar: ");
        int idServicioAReportar = entrada.nextInt();

        Servicio servicio = clienteIncidente.getServicios().get(idServicioAReportar - 1);

        List<Problema> problemas = new ArrayList<>();
        int opcion;

        do {
            System.out.println("Indique el tipo del problema: ");

            List<TipoProblema> tipoProblemas = em.createQuery("SELECT t FROM TipoProblema t", TipoProblema.class).getResultList();

            for (TipoProblema tipoProblema : tipoProblemas) {
                System.out.printf("%d) %s%n", tipoProblema.getId(), tipoProblema.getNombre());
            }

            System.out.println("Seleccione el id del tipo de problema que desea reportar: ");
            int idTipoProblemaAReportar = entrada.nextInt();

            TipoProblema tipoProblema = tipoProblemas.get(idTipoProblemaAReportar - 1);

            System.out.println("Indique una descripción del problema: ");
            entrada.nextLine();
            String descripcionProblema = entrada.nextLine();

            Problema problema = new Problema(descripcionProblema, tipoProblema);
            problemas.add(problema);

            System.out.println("¿Desea agregar un problema, seleccione 1 para continuar o 2 si no?");
            opcion = entrada.nextInt();
        } while (opcion == 1);

        Set<TipoProblema> tipoProblemasDelIncidente = new HashSet<>();

        for (Problema problema : problemas) {
            tipoProblemasDelIncidente.add(problema.getTipoProblema());
        }

        List<Tecnico> tecnicos = em.createQuery("SELECT t FROM Tecnico t", Tecnico.class).getResultList();

        List<Tecnico> tecnicosDisponibles = new ArrayList<>();
        for (Tecnico tecnico : tecnicos) {
            if (tecnico.estaDisponible() && tecnico.puedeResolver(tipoProblemasDelIncidente)) {
                tecnicosDisponibles.add(tecnico);
            }
        }

        System.out.println("Escriba el id del técnico que desea elegir: ");

        for (Tecnico tecnico : tecnicosDisponibles) {
            System.out.printf("ID: %s%nNombre: %s%nApellido: %s%n", tecnico.getId(), tecnico.getNombre(), tecnico.getApellido());
        }

        int IdTecnicoAElegir = entrada.nextInt();

        boolean esComplejo = false;

        for (Problema problema : problemas) {
            if (problema.esComplejo()) {
                esComplejo = true;
                break;
            }
        }

        int tiempoMaximo = Integer.MIN_VALUE;
        int tiempoEstimadoDeResolucion;

        if (esComplejo) {
            System.out.println("El Incidente tiene problemas complejos. Ingrese el tiempo estimado de resolución: ");
            tiempoEstimadoDeResolucion = entrada.nextInt();
        } else {
            for (Problema problema : problemas) {
                if (problema.tiempoMaximoResolucion() > tiempoMaximo)
                    tiempoMaximo = problema.tiempoMaximoResolucion();
            }

            System.out.printf("Ingrese el tiempo estimado de resolución, debe ser menor que %d horas: ", tiempoMaximo);
            tiempoEstimadoDeResolucion = entrada.nextInt();
        }

        System.out.println("Está por reportar un Incidente:");
        System.out.print("¿Desea confirmar la operación, coloque 1 para continuar o 2 para cancelar?");
        opcion = entrada.nextInt();


        if (opcion == 1) {
            Estado estado = new Creado();

            Rol rol = sesion.getUsuario().getRol();

            Usuario usuario = sesion.getUsuario();

            EntityTransaction tx = em.getTransaction();
            tx.begin();

            Tecnico tecnico = em.find(Tecnico.class, IdTecnicoAElegir);

            em.persist(estado);

            em.persist(rol);

            em.persist(usuario);

            Incidente incidente = new Incidente(tiempoEstimadoDeResolucion, usuario, problemas, clienteIncidente, tecnico,
                    servicio);
            incidente.setEstado(estado);
            incidente.setFechaEstimadaResolucion(incidente.getFechaCreacion().plusHours(tiempoEstimadoDeResolucion));

            tecnico.asignarIncidente(incidente);
            em.merge(tecnico);

            clienteIncidente.agregarIncidente(incidente);
            em.merge(clienteIncidente);

            em.persist(incidente);

            tx.commit();

            System.out.printf("Se ha creado el Incidente con éxito!%nFecha posible de resolución: %s%n%n",
                    incidente.getFechaEstimadaResolucion());

            if (tecnico.esNotificacionEmail()) {
                enviarEmailAsignacionIncidente(tecnico.getEmail());
            } else {
                enviarWhatsAppAsignacionIncidente(tecnico.getTelefono());
            }

        } else {
            System.out.println("No se ha creado el incidente.");
        }
    }

    private void enviarEmailAsignacionIncidente(String email) {
        System.out.printf("Enviando email a %s. Tiene asignado un incidente.%n", email);
    }

    private void enviarWhatsAppAsignacionIncidente(String telefono) {
        System.out.printf("Enviando whatsapp a %s. Tiene asignado un incidente.%n", telefono);
    }

    public void resolverIncidente() {
        System.out.println("Listado de técnicos:");

        List<Tecnico> tecnicos = em.createQuery("SELECT e FROM Tecnico e", Tecnico.class).getResultList();

        for (Tecnico tecnico : tecnicos) {
            System.out.printf("ID : %s%nNombre: %s%nApellido: %s%n%n", tecnico.getId(), tecnico.getNombre(), tecnico.getApellido());
        }

        System.out.println("Seleccione el id del técnico del cual quiere resolver el incidente: ");
        int idTecnico = entrada.nextInt();

        // Buscar el técnico por ID
        Tecnico tecnico = em.find(Tecnico.class, idTecnico);

        Incidente incidente = tecnico.obtenerIncidenteCreado();

        System.out.print("¿Desea cambiar el estado del Incidente a resuelto?, coloque 1 para continuar o 2 para cancelar:");
        int opcion = entrada.nextInt();

        if (opcion == 1) {
            incidente.resolver(LocalDateTime.now());

            EntityTransaction tx = em.getTransaction();
            tx.begin();

            Estado estado = new Resuelto();
            em.persist(estado);

            incidente.setEstado(estado);

            em.persist(incidente);

            tx.commit();

            System.out.println("Se ha resuelto el incidente.");
        }
    }
}