package entidades;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    private String nombre;

    @ManyToMany
    private List<Permiso> permisos;

    @OneToMany(mappedBy = "rol")
    private List<Usuario> usuarios;

    public Rol(String nombre, List<Permiso> permisos) {
        this.nombre = nombre;
        this.permisos = permisos;
    }

    public Rol() {

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Permiso> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<Permiso> permisos) {
        this.permisos = permisos;
    }

    public boolean esRRHH() {
        return nombre.equals("RRHH");
    }

    public boolean esMesaAyuda() {
        return nombre.equals("Mesa de ayuda");
    }

    public boolean esAreaComercial() {
        return nombre.equals("Area comercial");
    }
}
