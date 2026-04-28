package Mennu;

import java.util.Scanner;

import Model.Empleado;
import Model.Empresa;
import staticc.SesionEmpleado;
import utils.ProcesoEmpleado;

public class MennuEmpleado {
    public static void menuEmpleado() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n----- Menú Empleado -----");
            System.out.println("1. Ver mis datos");
            System.out.println("2. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    verDatos();
                    break;
                case 2:
                    System.out.println("Cerrando sesión.");
                    SesionEmpleado.cerrarSesion();
                    return;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private static void verDatos() {
        Empleado  emp     = SesionEmpleado.getEmpleadoActual();
        Empresa.InnerEmpresa empresa   = SesionEmpleado.getEmpresaDelEmpleado();

        if (emp == null) {
            System.out.println("Error: no hay sesión activa.");
            return;
        }

        // ── Datos personales ──
        System.out.println("\n");
        System.out.println("       DATOS DEL EMPLEADO");
        System.out.println("");
        System.out.println("Nombre          : " + emp.genombre());
        System.out.println("Edad            : " + emp.getEdad());
        System.out.println("Cédula          : " + emp.getCedula());
        System.out.println("Empresa         : " + (empresa != null ? empresa.nombre() : "N/A"));
        System.out.println("Sede            : " + (empresa != null ? empresa.sede()   : "N/A"));
        System.out.println("Código oficina  : " + emp.getCodigoOficina());
        System.out.println("Estado          : " + (emp.isActivo() ? "Activo" : "Inactivo"));

        // ── Información salarial ──
        System.out.println("\n── Información Salarial ──");
        System.out.printf("Salario base    : $%.2f%n", (double) emp.getSueldoBasicoF());
        System.out.printf("Bono mensual    : $%.2f%n", emp.getBonoMensual());

        // Calcula el salario final usando procesoEmpleado (con la fórmula real)
        double salarioCalculado = ProcesoEmpleado.calcularSalarioFinal(emp.getSueldoBasicoF(), emp.getBonoMensual());
        System.out.printf("Sueldo final    : $%.2f%n", salarioCalculado);

        // Bono extra si ID es par
        double bonoExtra = ProcesoEmpleado.calcularBonoExtra(emp.getId());
        System.out.printf("Bono extra      : $%.2f %s%n", bonoExtra,
                bonoExtra > 0 ? "(ID par ✓)" : "(ID impar)");

        // Elegibilidad
        boolean elegible = ProcesoEmpleado.validarElegibilidad(
                emp.getPuntaje(), emp.getEdad(), emp.getCodigoOficina(), emp.isActivo());
        System.out.println("Elegibilidad    : " + (elegible ? "Sí aplica" : "No aplica"));

        // ── Puntaje y reportes ──
        System.out.println("\n── Puntaje y Reportes ──");
        System.out.println("Puntaje         : " + emp.getPuntaje());
        System.out.println("(Los reportes trimestrales los genera la empresa desde su menú)");
    }
}