package entidades;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Incidente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    private LocalDate fechaCreacion;

    private LocalDate fechaEstimadaDeResolucion;

    private LocalDate fechaResolucion;

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

    @OneToMany(mappedBy = "incidente")
    private List<Cliente> clientes;

    @ManyToOne
    @JoinColumn(name = "tecnico_id", referencedColumnName = "id")
    private Tecnico tecnico;

    public Incidente(LocalDate fechaCreacion, LocalDate fechaEstimadaDeResolucion, List<Problema> problemas, Servicio servicio) {
        this.fechaCreacion = fechaCreacion;
        this.fechaEstimadaDeResolucion = fechaEstimadaDeResolucion;
        this.estado = new Creado();
        this.problemas = problemas;
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

    public List<Problema> getDetalleIncidente() {
        return problemas;
    }

    public void setDetalleIncidente(List<Problema> problema) {
        this.problemas = problema;
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
}