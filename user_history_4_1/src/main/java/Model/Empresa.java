
package Model;

import java.util.ArrayList;
import java.util.List;

public class Empresa {
    public static class InnerEmpresa {
        private byte id;
        private String nombre;
        private String direccion;
        private String sede;
        private List<Empleado> empleados;

        public InnerEmpresa(byte id, String nombre, String direccion, String sede, List<Empleado> empleados) {
            this.id = id;
            this.nombre = nombre;
            this.direccion = direccion;
            this.sede = sede;
            this.empleados = empleados != null ? empleados : new ArrayList<>();
        }

        public byte id()           { return id; }
        public String nombre()     { return nombre; }
        public String direccion()  { return direccion; }
        public String sede()       { return sede; }
        public List<Empleado> empleados() { return empleados; }

        public void agregarEmpleado(Empleado empleado) {
            this.empleados.add(empleado);
        }
    }
}