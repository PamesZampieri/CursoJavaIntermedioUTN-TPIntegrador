package entidades;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    private String nombre;

    @ManyToMany(mappedBy = "servicios")
    private List<Cliente> clientes;

    @OneToMany(mappedBy = "servicio")
    private List<Incidente> incidentes;

    public Servicio(int id, String nombre, List<Cliente> clientes) {
        this.id = id;
        this.nombre = nombre;
        this.clientes = clientes;
    }

    public Servicio() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String toString() {
        return nombre;
    }
}