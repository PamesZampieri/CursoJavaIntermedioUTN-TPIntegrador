package entidades;

import java.time.LocalDate;
import java.util.List;

public class Incidente {
    private LocalDate fechaCreacion;
    private LocalDate fechaEstimadaDeResolucion;
    private LocalDate fechaResolucion;

    private Estado estado;

    private List<DetalleIncidente> detalleIncidente;

    private Usuario usuarioCreo;

    private Especialidad especialidad;

    private Servicio servicio;

    public Incidente(LocalDate fechaCreacion, LocalDate fechaEstimadaDeResolucion, LocalDate fechaResolucion,
                     Estado estado, List<DetalleIncidente> detalleIncidente, Usuario usuarioCreo,
                     Especialidad especialidad, Servicio servicio) {
        this.fechaCreacion = fechaCreacion;
        this.fechaEstimadaDeResolucion = fechaEstimadaDeResolucion;
        this.fechaResolucion = fechaResolucion;
        this.estado = estado;
        this.detalleIncidente = detalleIncidente;
        this.usuarioCreo = usuarioCreo;
        this.especialidad = especialidad;
        this.servicio = servicio;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDate getFechaResolucion() {
        return fechaResolucion;
    }

    public void setFechaResolucion(LocalDate fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
    }

    public LocalDate getFechaEstimadaDeResolucion() {
        return fechaEstimadaDeResolucion;
    }

    public void setFechaEstimadaDeResolucion(LocalDate fechaEstimadaDeResolucion) {
        this.fechaEstimadaDeResolucion = fechaEstimadaDeResolucion;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public List<DetalleIncidente> getDetalleIncidente() {
        return detalleIncidente;
    }

    public void setDetalleIncidente(List<DetalleIncidente> detalleIncidente) {
        this.detalleIncidente = detalleIncidente;
    }

    public Usuario getUsuarioCreo() {
        return usuarioCreo;
    }

    public void setUsuarioCreo(Usuario usuarioCreo) {
        this.usuarioCreo = usuarioCreo;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }
}