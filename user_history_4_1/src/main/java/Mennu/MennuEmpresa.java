package Mennu;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import Model.Empresa;
import Model.Empresa.InnerEmpresa;
import Model.Empleado;
import staticc.listEmpresa;
import utils.logearEmpresa;
import utils.EliminarEmpleado;
import utils.RegistarEmpleado;
import hu_4.App;
import utils.RegistarEmpresas;
import utils.ReporteTrimestral;

public class MennuEmpresa {
    public static void menuEmpresa() {
            

            while (true) {
                System.out.println("");
                System.out.println("----- Menú Empresa -----");
                Scanner sc = new Scanner(System.in);
                System.out.println("1. Agregar Empleado a Empresa");
                System.out.println("2. Listar Empleados de Empresa");
                System.out.println("3. Eliminar Empleado de Empresa");
                System.out.println("4. Reporte de empleado");
                System.out.println("5. Salir");
                System.out.print("Seleccione una opción: ");
                int opcion = sc.nextInt();

                switch (opcion) {
                    case 1:
                        RegistarEmpleado.registrarEmpleado( );;
                        break;
                    case 2:
                        // Lógica para listar empleados de la empresa
                        System.out.println("Empleados registrados en la empresa:");
                        for (Empresa.InnerEmpresa empresa : listEmpresa.getEmpresas()) {
                            System.out.println("Empresa: " + empresa.nombre());
                            
                            // Verificamos si la lista de empleados de ESTA empresa está vacía
                            if (empresa.empleados().isEmpty()) {
                                System.out.println("   No hay empleados vinculados.");
                            } else {
                                // CORRECCIÓN: Usamos la instancia 'empresa' y el tipo 'InnerEmpleado'
                                for (Empleado.InnerEmpleado empleado : empresa.empleados()) {
                                    System.out.println("   - ID: " + empleado.id() + " | Nombre: " + empleado.nombre());
                                }
                            }
                        }
                        break;
                    case 3:
                        EliminarEmpleado.eliminarEmpleado();
                        break;
                    case 4:
                        System.out.println("Reporte trimestral de empleados.");
                        ReporteTrimestral.generarReporte();
                        break;

                    case 5 :
                        System.out.println("Saliendo del menú de empresa.");
                        return;
                    default:
                        System.out.println("Opción no válida. Intente nuevamente.");
                }
            }
    
    
    }
}
