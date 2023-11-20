package entidades;

import java.util.List;

public class TipoProblema {
    private String nombre;
    private int tiempoMaximoEstimadoDeResolucion;
    private List<Especialidad> especialidades;

    public TipoProblema(String nombre, int tiempoMaximoEstimadoDeResolucion, List<Especialidad> especialidades) {
        this.nombre = nombre;
        this.tiempoMaximoEstimadoDeResolucion = tiempoMaximoEstimadoDeResolucion;
        this.especialidades = especialidades;
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