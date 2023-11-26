package entidades;

import entidades.Incidente;
import entidades.Rol;
import entidades.Sesion;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    private String nombre;
    private String contrasenia;

    @OneToMany(mappedBy = "usuarioCreo")
    private List<Incidente> incidentes;

    @OneToOne
    @JoinColumn(name = "usuario_id",  referencedColumnName="id")
    private Sesion sesion;

    @ManyToOne
    @JoinColumn(name = "rol_id",referencedColumnName = "id")
    private Rol rol;

    public Usuario(String nombre, String contrasenia, Rol rol) {
        this.nombre = nombre;
        this.contrasenia = contrasenia;
        this.rol = rol;
    }

    public Usuario() {

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public boolean esRolRRHH() {
        return rol.esRRHH();
    }

    public boolean esRolMesaAyuda() {
        return rol.esMesaAyuda();
    }

    public boolean esRolAreaComercial() {
        return rol.esAreaComercial();
    }

    //TODO
    //public void editarTecnico(Tecnico tecnico) RRHH
    //public void altaTecnico() RRHH
    //public void bajaTecnico(Tecnico tecnico) RRHH
    //public void crearReporteDiario(Incidente incidente, Tecnico tecnico, Estado estado)RRHH

    //TODO
    //public void editarCliente(Cliente cliente) areaComercial
    //public void altaCliente() areaComercial
    //public void bajaCliente(Cliente cliente) areaComercial

    //TODO
    //public void atenderLlamada() mesaAyuda
    //public void crearIncidente(List<Servicio> Cliente, String descripcionProblema, TipoProblema problema) return List<Tecnico> y fechaEstimadaResolucion una vez seleccionado uno, mesaAyuda
    //public void horasEstimadasDeResolucion(TipoProblema problema) segun complejidad- mesaAyuda
}