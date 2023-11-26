package entidades;

import jakarta.persistence.*;

@Entity
public class Problema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "incidente_id",referencedColumnName = "id")
    private Incidente incidente;

    @ManyToOne
    @JoinColumn(name = "tipo_problema_id",referencedColumnName = "id")
    private TipoProblema tipoProblema;

    public Problema() {

    }

    public Problema(int id, String descripcion, Incidente incidente, TipoProblema tipoProblema) {
        this.id = id;
        this.descripcion = descripcion;
        this.incidente = incidente;
        this.tipoProblema = tipoProblema;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoProblema getTipoProblema() {
        return tipoProblema;
    }

    public void setTipoProblema(TipoProblema tipoProblema) {
        this.tipoProblema = tipoProblema;
    }
}
