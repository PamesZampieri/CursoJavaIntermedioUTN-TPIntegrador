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
    private int tiempoMaximoEstimadoDeResolucion;

    @OneToMany(mappedBy = "tipoProblema")
    private List<Problema> problemas;

    @ManyToMany(mappedBy = "tipoProblema")
    private List<Especialidad> especialidades;

    public TipoProblema(String nombre, int tiempoMaximoEstimadoDeResolucion, List<Especialidad> especialidades) {
        this.nombre = nombre;
        this.tiempoMaximoEstimadoDeResolucion = tiempoMaximoEstimadoDeResolucion;
        this.especialidades = especialidades;
    }

    public TipoProblema() {

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTiempoMaximoEstimadoDeResolucion() {
        return tiempoMaximoEstimadoDeResolucion;
    }

    public void setTiempoMaximoEstimadoDeResolucion(int tiempoMaximoEstimadoDeResolucion) {
        this.tiempoMaximoEstimadoDeResolucion = tiempoMaximoEstimadoDeResolucion;
    }

    public List<Especialidad> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(List<Especialidad> especialidades) {
        this.especialidades = especialidades;
    }
}