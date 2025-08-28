package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consulta.DatosReservaConsulta;
import med.voll.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidadorMedicoActivo implements ValidadorDeConsultas {

    @Autowired
    private MedicoRepository repository;
    public void validar(DatosReservaConsulta datos){
        //eleccion del medico opcional
        if(datos.idMedico()==null){
            return;
        }
        var medicoEstaActivo = repository.findActivoByID(datos.idMedico());
        if(!medicoEstaActivo){
            throw new ValidacionException("Consulta No puede Ser Reservada con medico inactivo");
        }
    }
}
