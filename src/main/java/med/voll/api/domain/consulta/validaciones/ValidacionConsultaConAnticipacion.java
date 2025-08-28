package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consulta.DatosReservaConsulta;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidacionConsultaConAnticipacion implements ValidadorDeConsultas {
    public void validar(DatosReservaConsulta datos){
        var fechaConsulta= datos.fecha();
        var ahora = LocalDateTime.now();
        var difereciaEnMInutos = Duration.between(ahora, fechaConsulta).toMinutes();
        if(difereciaEnMInutos<30){
            throw new ValidacionException("Debe buscar cita con menos de 30 minutos de anticipación");
        }
    }
}
