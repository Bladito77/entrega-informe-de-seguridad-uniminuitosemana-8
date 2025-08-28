package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consulta.DatosReservaConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidacionFueraDeHorarioConsulta implements ValidadorDeConsultas {
    public void validar(DatosReservaConsulta datos){
        var fechaConsulta = datos.fecha();
        var domingo = fechaConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var horariAntesDeAperturaClinica = fechaConsulta.getHour() < 7;
        var horarioDespuesDeCierre = fechaConsulta.getHour() >18;
        if(domingo || horariAntesDeAperturaClinica|| horarioDespuesDeCierre){
            throw new ValidacionException("Horario seleccionado fuera de horario laboral...");
        }
    }
}
