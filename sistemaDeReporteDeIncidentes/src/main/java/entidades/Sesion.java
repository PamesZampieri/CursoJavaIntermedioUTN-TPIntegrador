package entidades;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Sesion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    private LocalDateTime fechaHoraInicio;

    private LocalDateTime fechaHoraFin;

    @OneToOne(mappedBy = "sesion")
    private Usuario usuario;

    public Sesion(LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin, Usuario usuario) {
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        this.usuario = usuario;
    }

    public Sesion() {

    }

    public LocalDateTime getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public LocalDateTime getFechaHoraFin() {
        return fechaHoraFin;
    }

    public void setFechaHoraFin(LocalDateTime fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean esUsuarioRRHH() {
        return usuario.esRolRRHH();
    }

    public boolean esUsuarioMesaAyuda() {
        return usuario.esRolMesaAyuda();
    }

    public boolean esUsuarioAreaComercial() {
        return usuario.esRolAreaComercial();
    }

    public boolean esUsuarioTecnico() {
        return usuario.esRolTecnico();
    }
}