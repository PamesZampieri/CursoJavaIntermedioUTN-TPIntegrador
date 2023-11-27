package entidades;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public abstract class Estado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "estado")
    private List<Incidente> incidentes;

    @Basic
    protected String nombre;

    public Estado(String nombre) {
        this.nombre = nombre;
    }

    public Estado() {
    }

    public boolean esCreado() {
        return "Creado".equals(this.nombre);
    }

    public boolean esResuelto() {
        return "Resuelto".equals(this.nombre);
    }

    public void resolver(LocalDateTime fechaHoraResolucion, Incidente incidente) {
        throw new UnsupportedOperationException("No se puede resolver este incidente");
    }
}