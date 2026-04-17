package utils;

import java.util.InputMismatchException;
import java.util.Scanner;
import Model.Empleado;


public class ProcesoEmpleado {
    /**
     * Calcula el salario final de un empleado.
     *
     * Expresión: (salarioBase + (bonoMensual * 1.10)) - (salarioBase * 0.05)
     *
     * Orden de ejecución (precedencia de operadores):
     *  1. Paréntesis internos primero:
     *       a) (bonoMensual * 1.10)  → multiplicación del bono
     *       b) (salarioBase * 0.05)  → multiplicación para la deducción
     *  2. Paréntesis externos:
     *       a) (salarioBase + resultado_a) → suma del salario + bono aumentado
     *  3. Resta final:
     *       resultado_externo - resultado_b → se descuenta la deducción
     */
    public static double calcularSalarioFinal(double salarioBase, double bonoMensual) {
        return (salarioBase + (bonoMensual * 1.10)) - (salarioBase * 0.05);
    }

    /**
     * Determina si el empleado recibe un bono extra.
     * Un ID par (idEmpleado % 2 == 0) otorga bono extra de $500.
     */
    public static double calcularBonoExtra(byte idEmpleado) {
        // El operador % retorna el residuo de la división
        // Si el residuo entre 2 es 0, el ID es par → bono extra
        return (idEmpleado % 2 == 0) ? 500.0 : 0.0;
    }


    /**
     * Valida si un empleado es elegible para un beneficio.
     *
     * Expresión: (puntajeTest > 85 && edad < 30) || (idSede == 1 && !esActivo)
     *
     * Precedencia evaluada de mayor a menor:
     *  1. ! (NOT)       → !esActivo se evalúa primero
     *  2. && (AND)      → se evalúan los dos grupos con &&
     *       Grupo A: (puntajeTest > 85 && edad < 30)
     *       Grupo B: (idSede == 1 && !esActivo)
     *  3. || (OR)       → si cualquiera de los dos grupos es true, retorna true
     *
     * Casos donde es elegible:
     *  - Empleado joven (< 30) con buen puntaje (> 85), SIN IMPORTAR la sede
     *  - O empleado en sede 1 que actualmente NO está activo
     */
    public static boolean validarElegibilidad(int puntajeTest, int edad, short idSede, boolean esActivo) {
        return (puntajeTest > 85 && edad < 30) || (idSede == 1 && !esActivo);
    }
    /**
     * Aumenta el bono mensual de un empleado usando asignación compuesta +=
     * Equivale a: bonoMensual = bonoMensual + incremento
     */
    public static double actualizarBono(double bonoMensual, double incremento) {
        bonoMensual += incremento; // asignación compuesta
        return bonoMensual;
    }


    public static void procesarEmpleado(Empleado emp) {
        System.out.println("\n====== Proceso de Empleado: " + emp.getNombre() + " ======");

        double salarioFinal = calcularSalarioFinal(emp.getSueldoBasicoF(), emp.getBonoMensual());
        System.out.printf("Salario Final calculado : $%.2f%n", salarioFinal);

        double bonoExtra = calcularBonoExtra(emp.getId());
        System.out.println("Bono extra (ID " + (emp.getId() % 2 == 0 ? "par" : "impar") + ")  : $" + bonoExtra);

        boolean elegible = validarElegibilidad(emp.getPuntaje(), emp.getEdad(), emp.getCodigoOficina(), emp.isActivo());
        System.out.println("¿Es elegible?           : " + (elegible ? "Sí" : "No"));

        double bonoActualizado = actualizarBono(emp.getBonoMensual(), 200.0);
        System.out.printf("Bono mensual actualizado: $%.2f (se agregó $200 con +=)%n", bonoActualizado);
    }
}