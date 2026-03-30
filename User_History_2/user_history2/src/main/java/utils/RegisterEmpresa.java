package utils;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import utils.RegisterEmpleado;
import schema.Empresa;  
import schema.Empleado;

public class RegisterEmpresa {
    private Empresa empresa;
    private List<Empleado> listaEmpleados = new ArrayList<>();
    
    public RegisterEmpresa() {
    }
    public List<Empleado> getEmpleados() {
        return listaEmpleados;
    }
    public RegisterEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public void registrarEmpresa() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nombre de la empresa: ");
        String nombre = scanner.nextLine();

        System.out.print("Dirección de la empresa: ");
        String direccion = scanner.nextLine();


        empresa = new Empresa(nombre, direccion);

        System.out.println("Empresa registrada exitosamente.");
    }


    public Empresa getEmpresaCreada() {
        return this.empresa;
    }
}


