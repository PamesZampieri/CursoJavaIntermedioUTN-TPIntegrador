package controladores;

import entidades.Incidente;
import entidades.Sesion;
import entidades.Tecnico;
import jakarta.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class ControladorReportes {
    private EntityManager em;
    private Sesion sesion;
    Scanner entrada = new Scanner(System.in);

    public ControladorReportes(EntityManager em, Sesion sesion) {
        this.em = em;
        this.sesion = sesion;
    }

    public ControladorReportes() {

    }

    public void crearReporteDiario() {
        if (!sesion.esUsuarioRRHH()) {
            System.out.println("Error, usted no dispone el permiso para realizar esta tarea.");
            System.out.println();
        } else if (sesion.esUsuarioRRHH()) {
            List<Tecnico> tecnicos = em.createQuery("SELECT t FROM Tecnico t", Tecnico.class).getResultList();

            tecnicos.forEach(tecnico -> System.out.printf("%s\t%s\t%s\t%s\t%n%s\t%s\t\t\t\t%d\t\t\t\t%d%n%n", "Nombre",
                    "Apellido", "Incidentes creados", "Incidentes resueltos",
                    tecnico.getNombre(), tecnico.getApellido(), tecnico.getCantidadIncidentesCreados(),
                    tecnico.getCantidadIncidentesResueltos()));
        }
    }

    public void crearReporteTecnicosConMasIncidentesResueltos(int dias) {
        if (!sesion.esUsuarioRRHH()) {
            System.out.println("Error, usted no dispone el permiso para realizar esta tarea.");
            System.out.println();

        } else if (sesion.esUsuarioRRHH()) {
            List<Tecnico> tecnicos = em.createQuery("SELECT t FROM Tecnico t", Tecnico.class).getResultList();

            if (tecnicos.size() == 0) {
                return;
            }

            LocalDateTime fechaHoraDesde = LocalDateTime.now().minusDays(dias);

            Tecnico tecnicoConMasIncidentesResueltos = tecnicos.stream()
                    .skip(1)
                    .reduce(tecnicos.get(0), (tecnico1, tecnico2) -> {
                        int incidentesResueltosTecnico1 = tecnico1.getCantidadIncidentesResueltosDesde(fechaHoraDesde);
                        int incidentesResueltosTecnico2 = tecnico2.getCantidadIncidentesResueltosDesde(fechaHoraDesde);

                        return incidentesResueltosTecnico1 > incidentesResueltosTecnico2 ? tecnico1 : tecnico2;
                    });

            int maxIncidentesResueltos = tecnicoConMasIncidentesResueltos.getCantidadIncidentesResueltosDesde(fechaHoraDesde);

            System.out.printf("El técnico con más incidentes resueltos desde la fecha ingresada es: %s %s con %d incidentes " +
                            "resueltos.%n",
                    tecnicoConMasIncidentesResueltos.getNombre(),
                    tecnicoConMasIncidentesResueltos.getApellido(),
                    maxIncidentesResueltos);
        }
    }

    public void crearReporteTecnicosConMasIncidentesResueltosEnEspecialidad(String especialidad, int dias,
                                                                            ControladorReportes controlador) {
        if (!sesion.esUsuarioRRHH()) {
            System.out.println("Error, usted no dispone el permiso para realizar esta tarea.");
            System.out.println();

        } else if (sesion.esUsuarioRRHH()) {
            Tecnico tecnicoConMasIncidentes = controlador.obtenerTecnicoConMasIncidentesResueltosEnEspecialidad(especialidad, dias);

            System.out.println("El técnico con más incidentes resueltos en la especialidad " +
                    especialidad + " en los últimos " + dias + " días es: " + tecnicoConMasIncidentes.getNombre() + " "
                    + tecnicoConMasIncidentes.getApellido());
            System.out.println();
        }
    }

    public Tecnico obtenerTecnicoConMasIncidentesResueltosEnEspecialidad(String especialidad, int dias) {
        List<Tecnico> tecnicos = em.createQuery("SELECT t FROM Tecnico t", Tecnico.class).getResultList();

        LocalDateTime fechaHoraDesde = LocalDateTime.now().minusDays(dias);

        return tecnicos.stream()
                .filter(tecnico -> tecnico.getEspecialidades().equals(especialidad))
                .skip(1)
                .reduce(tecnicos.get(0), (tecnico1, tecnico2) -> {
                    int incidentesResueltosTecnico1 = tecnico1.getCantidadIncidentesResueltosDesde(fechaHoraDesde);
                    int incidentesResueltosTecnico2 = tecnico2.getCantidadIncidentesResueltosDesde(fechaHoraDesde);

                    return incidentesResueltosTecnico1 > incidentesResueltosTecnico2 ? tecnico1 : tecnico2;
                });
    }

    public void crearReporteTecnicoQueMasRapidoResolvioIncidentes(int dias, ControladorReportes controlador) {
        if (!sesion.esUsuarioRRHH()) {
            System.out.println("Error, usted no dispone el permiso para realizar esta tarea.");
            System.out.println();

        } else if (sesion.esUsuarioRRHH()) {
            List<Incidente> incidentes = em.createQuery("SELECT i FROM Incidente i", Incidente.class).getResultList();

            Tecnico tecnicoMasRapido = controlador.obtenerTecnicoMasRapido(incidentes);

            if (tecnicoMasRapido != null) {
                System.out.println("El técnico que más rápido resolvió los incidentes es: " + tecnicoMasRapido.getNombre()
                        + " " + tecnicoMasRapido.getApellido());
                System.out.println();

            } else {
                System.out.println("No hay información suficiente para determinar el técnico más rápido.");
                System.out.println();
            }
        }
    }

    public Tecnico obtenerTecnicoMasRapido(List<Incidente> incidentes) {
        if (incidentes == null || incidentes.isEmpty()) {
            return null;
        }

        return incidentes.stream()
                .filter(Incidente::estaEstadoResuelto)  // Filtra incidentes resueltos
                .collect(Collectors.groupingBy(Incidente::getTecnico, Collectors.toList()))
                .entrySet().stream()
                .filter(entry -> !entry.getValue().isEmpty())  // Filtra técnicos con incidentes resueltos
                .max(Comparator.comparingInt(entry -> entry.getValue().size()))  // Encuentra al técnico con más incidentes resueltos
                .map(Map.Entry::getKey)
                .orElse(null);
    }
}