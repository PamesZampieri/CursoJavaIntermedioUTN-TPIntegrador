package entidades;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Incidente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaEstimadaResolucion;

    private LocalDateTime fechaResolucion;

    @ManyToOne
    @JoinColumn(name = "estado_id", referencedColumnName = "id")
    private Estado estado;

    @OneToMany(mappedBy = "incidente")
    private List<Problema> problemas;

    @ManyToOne
    @JoinColumn(name = "usuarioCreo_id", referencedColumnName = "id")
    private Usuario usuarioCreo;

    @ManyToOne
    @JoinColumn(name = "servicio_id", referencedColumnName = "id")
    private Servicio servicio;

    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "tecnico_id", referencedColumnName = "id")
    private Tecnico tecnico;

    public Incidente(int horasEstimadasResolucion, Usuario usuarioCreo, List<Problema> problemas, Cliente cliente,
                     Tecnico tecnico, Servicio servicio) {
        this.fechaCreacion = LocalDateTime.now();
        this.fechaEstimadaResolucion = this.fechaCreacion.plusHours(horasEstimadasResolucion);
        this.usuarioCreo = usuarioCreo;
        this.estado = new Creado();
        this.problemas = problemas;
        this.cliente = cliente;
        this.tecnico = tecnico;
        this.servicio = servicio;
    }

    public Incidente() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaEstimadaResolucion() {
        return fechaEstimadaResolucion;
    }

    public void setFechaEstimadaResolucion(LocalDateTime fechaEstimadaResolucion) {
        this.fechaEstimadaResolucion = fechaEstimadaResolucion;
    }

    public LocalDateTime getFechaResolucion() {
        return fechaResolucion;
    }

    public void setFechaResolucion(LocalDateTime fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public List<Problema> getProblemas() {
        return problemas;
    }

    public void setProblemas(List<Problema> problemas) {
        this.problemas = problemas;
    }

    public Usuario getUsuarioCreo() {
        return usuarioCreo;
    }

    public void setUsuarioCreo(Usuario usuarioCreo) {
        this.usuarioCreo = usuarioCreo;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Tecnico getTecnico() {
        return tecnico;
    }

    public void setTecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
    }

    public boolean estaEstadoCreado() {
        if (estado == null) {
            return false;
        }

        return estado.esCreado();
    }

    public boolean estaEstadoResuelto() {
        if (estado == null) {
            return false;
        }

        return estado.esResuelto();
    }

    public void resolver(LocalDateTime fechaHoraResolucion) {
        estado.resolver(fechaHoraResolucion, this);
    }

    public boolean estaResueltoDesde(LocalDateTime fechaHoraDesde) {
        return fechaResolucion.isAfter(fechaHoraDesde);
    }

    public long getTiempoResolucion() {
        if (fechaCreacion == null || fechaResolucion == null) {
            return 0;
        }

        // Calcula la diferencia en minutos entre la fecha de inicio y la fecha de resolución
        return java.time.Duration.between(fechaCreacion, fechaResolucion).toMinutes();
    }
}