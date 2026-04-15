package staticc;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import Model.Empleado;
import Model.Empresa;
import staticc.listEmpleado;
import Mennu.MennuEmpleado;

public class listEmpresa {
    private static List<Empresa.InnerEmpresa> empresas = new ArrayList<>();

    public static void agregarEmpresa(Empresa.InnerEmpresa empresa) {
        empresas.add(empresa);
    }

    public static List<Empresa.InnerEmpresa> getEmpresas() {
        return empresas;
    }

    /**
     * List.of() y Map.of() son factory methods introducidos en Java 9.
     *
     * ✅ Más seguros que ArrayList porque:
     *   - Son inmutables: no permiten .add(), .remove() ni .set()
     *   - No permiten elementos null (lanza NullPointerException al construirse)
     *   - Son thread-safe de lectura sin sincronización
     *
     * ⚠ Limitación importante: si intentas hacer .add() obtienes
     *   UnsupportedOperationException en tiempo de ejecución.
     *   Úsalos solo para datos de referencia que no cambiarán.
     */
    public static final List<String> SEDES_DISPONIBLES = List.of(
        "Medellin", "Bogota", "Cali", "Barranquilla", "Cartagena"
    );

    public static final List<String> TECNOLOGIAS = List.of(
        "Java", "Python", "JavaScript", "React", "Spring Boot"
    );

    // Map.of() para asociar sede → código de región (Java 9+)
    public static final Map<String, Integer> CODIGO_SEDES = Map.of(
        "Medellin",    1,
        "Bogota",      2,
        "Cali",        3,
        "Barranquilla", 4,
        "Cartagena",   5
    );
    

    static {
        // 1. Solo necesitamos la lista de empleados para la empresa
        List<Empleado.InnerEmpleado> empleadosDeRiwi = new ArrayList<>();

        // 2. Creamos al empleado (sin pasarle lista de empresas, asumiendo que el record ya no la pide)
        Empleado.InnerEmpleado juanPerez = new Empleado.InnerEmpleado(
            (byte) 1, 
            (short) 101, 
            30, 
            123456789L, 
            2000.0f, 
            500.0, 
            'M', 
            "Juan Perez", 
            2500.0, 
            true, 
            85
        );

        empleadosDeRiwi.add(juanPerez);

        Empresa.InnerEmpresa riwi = new Empresa.InnerEmpresa(
            (byte) 1, 
            "Riwi", 
            "cl73 #54 guayabal", 
            "Medellin", 
            empleadosDeRiwi
        );

        empresas.add(riwi);

        listEmpleado.agregarEmpleado(juanPerez);
    }
}
