package staticc;

import Model.Empleado;
import Model.Empresa;

public class SesionEmpleado {
    private static Empleado.InnerEmpleado empleadoActual = null;
    private static Empresa.InnerEmpresa   empresaDelEmpleado = null;

    public static void setEmpleadoActual(Empleado.InnerEmpleado emp, Empresa.InnerEmpresa empresa) {
        empleadoActual     = emp;
        empresaDelEmpleado = empresa;
    }

    public static Empleado.InnerEmpleado getEmpleadoActual() {
        return empleadoActual;
    }

    public static Empresa.InnerEmpresa getEmpresaDelEmpleado() {
        return empresaDelEmpleado;
    }

    public static void cerrarSesion() {
        empleadoActual     = null;
        empresaDelEmpleado = null;
    }
}