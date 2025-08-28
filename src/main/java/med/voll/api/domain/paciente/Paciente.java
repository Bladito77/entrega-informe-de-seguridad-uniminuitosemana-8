package med.voll.api.domain.paciente;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.direccion.Direccion;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
@Table(name="pacientes")
@Entity(name="Paciente")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean activo;
    private String nombre;
    private String email;
    private  String documento_identidad;

    private String telefono;
    @Embedded
    private Direccion direccion;

    public Paciente(DatosRegistroPaciente datos){
        this.id = null;
        this.activo = true;
        this.nombre = datos.nombre();
        this.email = datos.email();
        this.documento_identidad = datos.documento_identidad();
        this.telefono = datos.telefono();
        this.direccion =new Direccion(datos.direccion());
    }

    public void actualizarInformacionesP(@Valid DatosActualizacionPaciente datos) {
        if(datos.nombre() != null){
            this.nombre = datos.nombre();
        }
        if(datos.telefono() != null){
            this.telefono = datos.telefono();
        }
        if(datos.direccion() != null){
            this.direccion.actualizarDireccion(datos.direccion());
        }
    }

    public void inactivarp() {
        this.activo =false;
    }
}
