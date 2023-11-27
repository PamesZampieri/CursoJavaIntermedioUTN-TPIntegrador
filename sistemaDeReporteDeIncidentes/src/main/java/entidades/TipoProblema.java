package entidades;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tipo_problema")
public class TipoProblema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Basic
    private String nombre;

    private int tiempoMaximoEstimadoResolucion;
    private boolean complejo;

    @OneToMany(mappedBy = "tipoProblema")
    private List<Problema> problemas;

    @ManyToMany(mappedBy = "tipoProblema")
    private List<Especialidad> especialidades;

    public TipoProblema(String nombre, boolean complejo, List<Problema> problemas) {
        this.nombre = nombre;
        this.complejo = complejo;
        this.problemas = problemas;
    }

    public TipoProblema() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTiempoMaximoEstimadoResolucion() {
        return tiempoMaximoEstimadoResolucion;
    }

    public void setTiempoMaximoEstimadoResolucion(int tiempoMaximoEstimadoResolucion) {
        this.tiempoMaximoEstimadoResolucion = tiempoMaximoEstimadoResolucion;
    }

    public boolean isComplejo() {
        return complejo;
    }

    public void setComplejo(boolean complejo) {
        this.complejo = complejo;
    }

    public List<Problema> getProblemas() {
        return problemas;
    }

    public void setProblemas(List<Problema> problemas) {
        this.problemas = problemas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Especialidad> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(List<Especialidad> especialidades) {
        this.especialidades = especialidades;
    }

}