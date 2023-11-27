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
    private String cuit;
    private String email;

    @ManyToMany
    private List<Servicio> servicios;

    @OneToMany(mappedBy = "cliente")
    private List<Incidente> incidentes;

    public Cliente(String razonSocial, String cuit, String email, List<Servicio> servicios) {
        this.razonSocial = razonSocial;
        this.cuit = cuit;
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

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
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

    public List<Incidente> getIncidentes() {
        return incidentes;
    }

    public void setIncidentes(List<Incidente> incidentes) {
        this.incidentes = incidentes;
    }

    public void agregarIncidente(Incidente incidente) {
        incidentes.add(incidente);
    }
}