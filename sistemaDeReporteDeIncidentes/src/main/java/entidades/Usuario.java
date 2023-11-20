package entidades;

public class Usuario {
    private String nombre;
    private String contraseña;
    private Rol rol;

    public Usuario(String nombre, String contraseña, Rol rol) {
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    //TODO
    //public void editarTecnico(Tecnico tecnico) {
    //public void alta()
    //public void baja(Tecnico tecnico)


}
