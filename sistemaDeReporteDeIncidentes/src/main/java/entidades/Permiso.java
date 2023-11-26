package entidades;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Permiso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    private String nombre;
    private String descripcion;

    @ManyToMany(mappedBy = "permisos")
    private List<Rol> roles;

    public Permiso(int id, String nombre, String descripcion, List<Rol> roles) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.roles = roles;
    }

    public Permiso() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
