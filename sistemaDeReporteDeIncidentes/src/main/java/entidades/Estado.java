package entidades;

import jakarta.persistence.*;

import java.util.List;

@Entity
public abstract class Estado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "estado")
    private List<Incidente> incidentes;

    @Basic
    private String nombre;
}