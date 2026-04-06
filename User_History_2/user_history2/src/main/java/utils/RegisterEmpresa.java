package utils;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import schema.Empresa;  
import schema.Empleado;

public class RegisterEmpresa {
    private Empresa empresa;
    private List<Empleado> listaEmpleados = new ArrayList<>();
    private List<Empresa> listaEmpresas = new ArrayList<>();

    public RegisterEmpresa(List<Empresa> listaEmpresasGlobal) {
        this.listaEmpresas = listaEmpresasGlobal;
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

        if (nombre.trim().isEmpty()) {
            System.out.println("El nombre de la empresa no puede estar vacío.");
            return;
        }
        boolean existe = listaEmpresas.stream()
            .anyMatch(e -> e.getNombre().equalsIgnoreCase(nombre));

        if (existe) {
            System.out.println(" La empresa '" + nombre + "' ya existe en el sistema.");
            return; 
        }

        System.out.print("Dirección de la empresa: ");
        String direccion = scanner.nextLine();

        if (direccion.trim().isEmpty()) {
            System.out.println("La dirección de la empresa no puede estar vacía.");
            return;
        }

        empresa = new Empresa(nombre, direccion);
        listaEmpresas.add(empresa);
        listaEmpleados.addAll(empresa.getEmpleados());

        System.out.println("Empresa registrada exitosamente.");
    }


    public Empresa getEmpresaCreada() {
        return this.empresa;
    }

    public int empleadoeliminar(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el ID del empleado a eliminar: ");
        int idEliminar = scanner.nextInt();
        return idEliminar;
    }

    public void eliminarEmpleado(Empleado empleado) {

        if (empleado == null) {
            System.out.println("Empleado no encontrado. No se puede eliminar.");
            return;
        }
        if (!listaEmpleados.contains(empleado)) {
            System.out.println("Empleado no pertenece a esta empresa. No se puede eliminar.");
            return;
        }
        if (empleado.getId() == empleadoeliminar()) {
            empresa.eliminarEmpleado(empleado);
            listaEmpleados.remove(empleado);
        }
        
    }
}


