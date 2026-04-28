package schema;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
public class Empresa {
    private String nombre;
    private String direccion;
    private List<Empleado> listaEmpleados;

        private static final List<String> TECNOLOGIAS_ESTANDAR = List.of(
        "Java 11", 
        "Spring Boot", 
        "PostgreSQL", 
        "Docker"
    );

    private static final Map<String, String> SEDES_GLOBALES = Map.of(
        "COL", "Sede Central Colombia",
        "MEX", "Sede Norte México",
        "ESP", "Sede Europa España"
    );

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

        public List<String> getTecnologiasEstandar() {
        return TECNOLOGIAS_ESTANDAR;
    }

    public Map<String, String> getSedesGlobales() {
        return SEDES_GLOBALES;
    }


}
