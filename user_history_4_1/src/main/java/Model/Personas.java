package Model;

public abstract sealed class Personas permits Empleado, ConsultorExterno {
    protected String nombre;
    protected int edad;
    protected int cedula;

    public Personas(String nombre, int edad, int cedula) {
        this.nombre = nombre;
        this.edad = edad;
        this.cedula = cedula;
    }

    public String getNombre() { return nombre; }
    public int getEdad() { return edad; }
    public int getCedula() { return cedula; }
}
