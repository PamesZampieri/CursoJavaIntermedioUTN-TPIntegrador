package entidades;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    private String razonSocial;
    private String cuil;
    private String email;

    @ManyToMany
    private List<Servicio> servicios;

    @ManyToOne
    @JoinColumn(name = "incidente_id",referencedColumnName = "id")
    private Incidente incidente;

    public Cliente(String razonSocial, String cuil, String email, List<Servicio> servicios) {
        this.razonSocial = razonSocial;
        this.cuil = cuil;
        this.email = email;
        this.servicios = servicios;
    }

    public Cliente() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getCuil() {
        return cuil;
    }

    public void setCuil(String cuil) {
        this.cuil = cuil;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Servicio> getServicios() {
        return servicios;
    }

    public void setServicios(List<Servicio> servicios) {
        this.servicios = servicios;
    }

    public Incidente getIncidente() {
        return incidente;
    }

    public void setIncidente(Incidente incidente) {
        this.incidente = incidente;
    }
}