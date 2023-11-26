package entidades;

import entidades.Tecnico;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tipo_notificacion")
public class TipoNotificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    private String nombre;

    @OneToMany(mappedBy = "tipoNotificacion")
    private List<Tecnico> tecnicos;

    public TipoNotificacion(String nombre, List<Tecnico> tecnicos) {
        this.nombre = nombre;
        this.tecnicos = tecnicos;
    }

    public TipoNotificacion() {

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
