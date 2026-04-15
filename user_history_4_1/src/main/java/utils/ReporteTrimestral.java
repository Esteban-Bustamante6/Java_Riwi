package utils;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import Model.Empleado;
import Model.Empresa;
import staticc.SesionEmpresa;


public class ReporteTrimestral {

    /**
     * Solicita las calificaciones trimestrales de cada empleado de la empresa
     * logueada y genera un reporte con:
     *  - Promedio por empleado (double)
     *  - Puntaje simplificado con casting explícito double → int
     *  - Estado de promoción con operador ternario
     *
     * ANÁLISIS LTS — Mensajes de error en excepciones:
     *  Java 8  : Al ingresar un tipo incorrecto en Scanner, el mensaje era genérico:
     *            "java.util.InputMismatchException" sin indicar línea exacta del fallo.
     *  Java 17 : Introdujo "Helpful NullPointerExceptions" (JEP 358), con mensajes
     *            detallados que indican exactamente qué variable fue null y en qué
     *            expresión. Esto se extendió a otros errores de tipo.
     *  Java 21 : Refinó aún más los mensajes con contexto del valor que causó el
     *            mismatch, facilitando el diagnóstico sin necesidad de debugger.
     */
    public static void generarReporte() {
        Empresa.InnerEmpresa empresa = SesionEmpresa.getEmpresaActual();
        if (empresa == null) {
            System.out.println("Error: No hay empresa logueada.");
            return;
        }

        List<Empleado.InnerEmpleado> empleados = empresa.empleados();
        if (empleados.isEmpty()) {
            System.out.println("La empresa no tiene empleados registrados.");
            return;
        }

        int totalEmpleados = empleados.size();

        // Matriz: filas = empleados, columnas = 3 trimestres
        double[][] calificaciones = new double[totalEmpleados][3];

        Scanner sc = new Scanner(System.in);

        System.out.println("\n====== Reporte Trimestral: " + empresa.nombre() + " ======");
        System.out.println("Ingrese las calificaciones (0.0 - 100.0) por trimestre:\n");

        // ── Captura de datos con try-catch (InputMismatchException) ──
        for (int i = 0; i < totalEmpleados; i++) {
            Empleado.InnerEmpleado emp = empleados.get(i);
            System.out.println("Empleado: " + emp.nombre());

            for (int j = 0; j < 3; j++) {
                boolean entradaValida = false;
                while (!entradaValida) {
                    try {
                        System.out.print("  Trimestre " + (j + 1) + ": ");
                        calificaciones[i][j] = sc.nextDouble();
                        entradaValida = true;

                    } catch (InputMismatchException e) {
                        /*
                         * Java 8  → mensaje genérico sin contexto del valor inválido.
                         * Java 17 → JEP 358: indica qué expresión falló y por qué.
                         * Java 21 → muestra el token que causó el mismatch directamente.
                         * Esto acelera el diagnóstico sin necesidad de abrir un debugger.
                         */
                        System.out.println("  ⚠ Valor inválido. Ingrese un número decimal (ej: 87.5)");
                        sc.nextLine(); // limpiar buffer
                    }
                }
            }
        }

        // ── Recorrer matriz con for anidados y calcular promedios ──
        System.out.println("\n────── Resultados ──────");

        for (int i = 0; i < totalEmpleados; i++) {
            double suma = 0;

            // Sumar los 3 trimestres del empleado i
            for (int j = 0; j < 3; j++) {
                suma += calificaciones[i][j];
            }

            double promedio = suma / 3.0;

            /*
             * Casting explícito double → int
             * Se pierde la parte decimal intencionalmente para el "Puntaje Simplificado".
             * Ejemplo: promedio = 87.6 → (int) 87.6 = 87  (se trunca, NO se redondea)
             * Esta pérdida de precisión es documentada y esperada en el reporte.
             */
            int puntajeSimplificado = (int) promedio;

            /*
             * Operador ternario para estado de promoción:
             * Si el promedio es >= 70 → "PROMOVIDO", de lo contrario → "EN OBSERVACIÓN"
             */
            String estadoPromocion = (promedio >= 70.0) ? "PROMOVIDO" : "EN OBSERVACIÓN";

            Empleado.InnerEmpleado emp = empleados.get(i);
            System.out.println("\nEmpleado        : " + emp.nombre());
            System.out.printf( "Promedio        : %.2f%n", promedio);
            System.out.println("Puntaje Simple  : " + puntajeSimplificado + "  ← casting double→int (se trunca decimal)");
            System.out.println("Estado          : " + estadoPromocion);
        }

        System.out.println("\n────── Fin del reporte ──────");
    }
}