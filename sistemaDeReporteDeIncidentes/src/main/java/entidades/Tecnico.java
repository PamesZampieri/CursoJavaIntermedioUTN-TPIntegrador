package entidades;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Tecnico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;

    @ManyToMany
    private List<Especialidad> especialidades;

    @OneToMany(mappedBy = "tecnico")
    private List<Incidente> incidentes;

    @ManyToOne
    @JoinColumn(name = "tipo_notificacion_id", referencedColumnName = "id")
    private TipoNotificacion tipoNotificacion;

    public Tecnico(String nombre, String apellido, String email, String telefono,
                   List<Especialidad> especialidades, TipoNotificacion tipoNotificacion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.especialidades = especialidades;
        this.tipoNotificacion = tipoNotificacion;
    }

    public Tecnico() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<Especialidad> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(List<Especialidad> especialidades) {
        this.especialidades = especialidades;
    }

    public List<Incidente> getIncidentes() {
        return incidentes;
    }

    public void setIncidentes(List<Incidente> incidentes) {
        this.incidentes = incidentes;
    }

    public TipoNotificacion getTipoNotificacion() {
        return tipoNotificacion;
    }

    public void setTipoNotificacion(TipoNotificacion tipoNotificacion) {
        this.tipoNotificacion = tipoNotificacion;
    }

    public boolean estaDisponible() {
        if (incidentes == null) {
            return true;
        }

        for (Incidente incidente : incidentes) {
            if (incidente.estaEstadoCreado()) {
                return false;
            }
        }

        return true;
    }

    public boolean puedeResolver(Set<TipoProblema> tiposProblemasAResolver) {
        Set<TipoProblema> tiposProblemasQueResuelve = new HashSet<>();

        for (Especialidad especialidad : especialidades) {
            tiposProblemasQueResuelve.addAll(especialidad.getTipoProblema());
        }

        return tiposProblemasQueResuelve.containsAll(tiposProblemasAResolver);
    }

    public void asignarIncidente(Incidente incidente) {
        incidentes.add(incidente);
    }

    public boolean esNotificacionEmail() {
        return tipoNotificacion.esEmail();
    }

    public Incidente obtenerIncidenteCreado() {
        for (Incidente incidente : incidentes) {
            if (incidente.estaEstadoCreado()) {
                return incidente;
            }
        }

        return null;
    }

    public int getCantidadIncidentesResueltos() {
        int resultado = 0;

        for (Incidente incidente : incidentes) {
            if (incidente.estaEstadoResuelto()) {
                resultado++;
            }
        }

        return resultado;
    }

    public int getCantidadIncidentesCreados() {
        int resultado = 0;

        for (Incidente incidente : incidentes) {
            if (incidente.estaEstadoCreado()) {
                resultado++;
            }
        }

        return resultado;
    }

    public int getCantidadIncidentesResueltosDesde(LocalDateTime fechaHoraDesde) {
        int resultado = 0;

        for (Incidente incidente : incidentes) {
            if (incidente.estaEstadoResuelto() && incidente.estaResueltoDesde(fechaHoraDesde)) {
                resultado++;
            }
        }

        return resultado;
    }

    public double getTiempoPromedioResolucion() {
        if (incidentes == null || incidentes.isEmpty()) {
            return 0;
        }

        double tiempoTotal = 0;

        for (Incidente incidente : incidentes) {
            tiempoTotal += incidente.getTiempoResolucion();
        }

        return tiempoTotal / incidentes.size();
    }
}