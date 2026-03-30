package schema;
import java.util.List;
import java.util.ArrayList;
public class Empresa {
    private String nombre;
    private String direccion;
    private List<Empleado> listaEmpleados;

    public Empresa(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.listaEmpleados = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public List<Empleado> getEmpleados() {
        return listaEmpleados;
    }

    public void agregarEmpleado(Empleado empleado) {
        listaEmpleados.add(empleado);
    }

    public void eliminarEmpleado(Empleado empleado) {
        listaEmpleados.remove(empleado);
    }
}
