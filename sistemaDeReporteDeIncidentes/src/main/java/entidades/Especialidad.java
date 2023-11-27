package entidades;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Especialidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    private String nombre;

    @ManyToMany
    private List<TipoProblema> tipoProblema;

    @ManyToMany(mappedBy = "especialidades")
    private List<Tecnico> tecnicos;

    public Especialidad(int id, String nombre, List<TipoProblema> tipoProblema, List<Tecnico> tecnicos) {
        this.id = id;
        this.nombre = nombre;
        this.tipoProblema = tipoProblema;
        this.tecnicos = tecnicos;
    }

    public Especialidad() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<TipoProblema> getTipoProblema() {
        return tipoProblema;
    }

    public void setTipoProblema(List<TipoProblema> tipoProblema) {
        this.tipoProblema = tipoProblema;
    }

    public List<Tecnico> getTecnicos() {
        return tecnicos;
    }

    public void setTecnicos(List<Tecnico> tecnicos) {
        this.tecnicos = tecnicos;
    }

    public String toString() {
        return nombre;
    }
}