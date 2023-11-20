package entidades;

public class DetalleIncidente {
    private String descripcion;
    private TipoProblema tipoProblema;

    public DetalleIncidente(String descripcion, TipoProblema tipoProblema) {
        this.descripcion = descripcion;
        this.tipoProblema = tipoProblema;
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
