package Model;

public final class ConsultorExterno extends Personas {
    private String empresaConsultora;

    public ConsultorExterno(String nombre, int edad, int cedula, String empresaConsultora) {
        super(nombre, edad, cedula);
        this.empresaConsultora = empresaConsultora;
    }

    public String getEmpresaConsultora() { return empresaConsultora; }
}
