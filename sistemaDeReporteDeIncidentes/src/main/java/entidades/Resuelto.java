package entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Resuelto extends Estado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public Resuelto() {
        this.nombre = "Resuelto";
    }
}