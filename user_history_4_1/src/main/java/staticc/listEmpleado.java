package staticc;
import java.util.List;
import java.util.ArrayList;
import Model.Empleado;
import Model.Empresa;
import staticc.listEmpresa;

public class listEmpleado {
    private static List<Empleado> empleados = new ArrayList<>();

    public static void agregarEmpleado(Empleado empleado) {
        empleados.add(empleado);
    }

    public static List<Empleado> getEmpleados() {
        return empleados;
    }

    

}
