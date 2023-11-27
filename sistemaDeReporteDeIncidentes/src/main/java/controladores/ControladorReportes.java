package controladores;

import entidades.Sesion;
import entidades.Tecnico;
import jakarta.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

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
        }

        List<Tecnico> tecnicos = em.createQuery("SELECT t FROM Tecnico t", Tecnico.class).getResultList();

        tecnicos.forEach(tecnico -> System.out.printf("%s\t%s\t%s\t%s\t%n%s\t%s\t\t\t\t%d\t\t\t\t%d%n%n", "Nombre",
                "Apellido", "Incidentes creados", "Incidentes resueltos",
                tecnico.getNombre(), tecnico.getApellido(), tecnico.getCantidadIncidentesCreados(),
                tecnico.getCantidadIncidentesResueltos()));
    }

    public void crearReporteTecnicosConMasIncidentesResueltos(int dias) {
        if (!sesion.esUsuarioRRHH()) {
            System.out.println("Error, usted no dispone el permiso para realizar esta tarea.");
        }

        List<Tecnico> tecnicos = em.createQuery("SELECT t FROM Tecnico t", Tecnico.class).getResultList();

        if (tecnicos.size() == 0) {
            return;
        }

        LocalDateTime fechaHoraDesde = LocalDateTime.now().minusDays(dias);

        int maxIncidentesResueltos = tecnicos.get(0).getCantidadIncidentesResueltosDesde(fechaHoraDesde);
        Tecnico tecnicoConMasIncidentesResueltos = tecnicos.get(0);

        for (int i = 1; i < tecnicos.size(); i++) {
            Tecnico tecnico = tecnicos.get(i);
            int incidentesResueltosTecnico = tecnico.getCantidadIncidentesResueltosDesde(fechaHoraDesde);
            if (incidentesResueltosTecnico > maxIncidentesResueltos) {
                maxIncidentesResueltos = incidentesResueltosTecnico;
                tecnicoConMasIncidentesResueltos = tecnico;
            }
        }

        System.out.printf("El técnico con más incidentes resueltos desde la fecha ingresada es: %s %s con %d incidentes " +
                        "resueltos.",
                tecnicoConMasIncidentesResueltos.getNombre(),
                tecnicoConMasIncidentesResueltos.getApellido(),
                maxIncidentesResueltos);
    }
}