package med.voll.api.domain.paciente;

public record DatosListaPaciente(
    String nombre,
    String email,
    String documento_identidad,
    String telefono) {
    public DatosListaPaciente(Paciente paciente) {
        this(
                paciente.getNombre(),
                paciente.getEmail(),
                paciente.getDocumento_identidad(),
                paciente.getTelefono()
        );
    }
}
