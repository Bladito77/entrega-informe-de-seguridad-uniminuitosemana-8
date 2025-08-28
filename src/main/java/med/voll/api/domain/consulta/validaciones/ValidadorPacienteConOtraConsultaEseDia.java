package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosReservaConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ValidadorPacienteConOtraConsultaEseDia implements ValidadorDeConsultas{

    @Autowired
    private ConsultaRepository repository;

    public void validar(DatosReservaConsulta datos) {
        var primerHorario = datos.fecha().withHour(7);
        var ultimoHorario = datos.fecha().withHour(18);
        //var primerHorario = datos.fecha().toLocalDate().atTime(7, 0);
        //var ultimoHorario = datos.fecha().toLocalDate().atTime(18, 0);

        //boolean pacienteTieneConsultaEnElDia = repository.existsByPacienteIdAndFechaBetween(datos.idPaciente(), primerHorario, ultimoHorario);

        //System.out.println(">> DEBUG: idPaciente=" + datos.idPaciente()
          //      + " rango=" + primerHorario + " a " + ultimoHorario
          //      + " resultado=" + pacienteTieneConsultaEnElDia);

        var pacienteTieneConsultaEnElDia =repository.existsByPacienteIdAndFechaBetween(datos.idPaciente(), primerHorario, ultimoHorario);
        //System.out.println("Rango buscado: " + primerHorario + " hasta " + ultimoHorario);

        if(pacienteTieneConsultaEnElDia){
        //    System.out.println(">> DEBUG idPaciente=" + datos.idPaciente() + " fecha=" + datos.fecha());

        //    System.out.println("Médico  "+datos.idMedico()+" Paciente "+datos.idPaciente()+" Fecha Cita "+datos.fecha());
            throw new ValidacionException("Paciente ya tiene una consulta ese dia reservada");
        }
    }
}
