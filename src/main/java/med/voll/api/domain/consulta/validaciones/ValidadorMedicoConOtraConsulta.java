package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosReservaConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoConOtraConsulta implements ValidadorDeConsultas{

    @Autowired
    private ConsultaRepository repository;
    public void validar(DatosReservaConsulta datos){
        var medicoTieneOtraConsultaEnElMIsmoHorario = repository.existsByMedicoIdAndFechaAndMotivoCancelamientoIsNull(datos.idMedico(),datos.fecha());
        if(medicoTieneOtraConsultaEnElMIsmoHorario){
            System.out.println("medico.. "+datos.idMedico()+" fecha  que valido "+datos.fecha() );
            throw new ValidacionException("Medico ya tiene otra consulta asignada en ese horario y fecha");
        }
    }

}
