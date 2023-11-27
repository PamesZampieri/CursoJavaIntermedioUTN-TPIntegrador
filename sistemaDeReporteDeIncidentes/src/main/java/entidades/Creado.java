package entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Creado extends Estado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public Creado() {
        this.nombre = "Creado";
    }

    @Override
    public void resolver(LocalDateTime fechaHoraResolucion, Incidente incidente) {
        incidente.setFechaResolucion(fechaHoraResolucion);
        incidente.setEstado(new Resuelto());
    }
}
