package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consulta.DatosReservaConsulta;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteActivo implements ValidadorDeConsultas{

    @Autowired
    private PacienteRepository repository;

    public void validar(DatosReservaConsulta datos){
        var pacientEstaActivo = repository.findActivoById(datos.idPaciente());
        if(!pacientEstaActivo){
            throw new ValidacionException("Consulta no puede ser reservada por que el paciente esta inactivo ");
        }
    }
}
