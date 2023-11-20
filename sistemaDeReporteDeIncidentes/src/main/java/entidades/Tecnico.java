package entidades;

import java.util.ArrayList;
import java.util.List;

public class Tecnico {
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;

    private List<Especialidad> especialidades;

    private List<Incidente> incidentes;

    private TipoNotificacion tipoNotificacion;

    public Tecnico(String nombre, String apellido, String email, String telefono, List<Especialidad> especialidades,
                   List<Incidente> incidentes, TipoNotificacion tipoNotificacion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.especialidades = especialidades;
        this.incidentes = incidentes;
        this.tipoNotificacion = tipoNotificacion;
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

    //TODO:
    public boolean estaDisponible() {
        return true;
    }

    //TODO:
    public List<Incidente> listarIncidentePorEstado() {
        return null;
    }
}