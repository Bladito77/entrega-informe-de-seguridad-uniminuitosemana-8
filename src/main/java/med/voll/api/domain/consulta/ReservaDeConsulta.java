package med.voll.api.domain.consulta;

import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consulta.cancelamiento.ValidadorCancelamientoDeConsulta;
import med.voll.api.domain.consulta.validaciones.ValidadorDeConsultas;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaDeConsulta {
    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private List<ValidadorDeConsultas> validadores;

    @Autowired
    private List<ValidadorCancelamientoDeConsulta> validadoresCancelamiento;

    //private ValidadorContaConAnticipacion  *** tocaria una a una pero es mejor... codigo 25

    public DatosDetalleConsulta reservar(DatosReservaConsulta datos){
        if(!pacienteRepository.existsById(datos.idPaciente())){
            throw new ValidacionException("no existe un paciente con este Id ");
        }
        if(datos.idMedico() != null && !medicoRepository.existsById(datos.idMedico())){
            throw new ValidacionException("no existe un medico con este Id ");
        }
        //validaciones
        validadores.forEach(v->v.validar(datos));

        var medico = elegirMedico(datos);
        if(medico == null){
            throw new ValidacionException("No existe un medico disponible en este horario ");
        }
        var paciente = pacienteRepository.findById(datos.idPaciente()).get();
        var consulta = new Consulta(null, medico, paciente, datos.fecha());
        consultaRepository.save(consulta);
        return new DatosDetalleConsulta(consulta);

    }


    private Medico elegirMedico(DatosReservaConsulta datos) {
        if(datos.idMedico()!=null) {
            return medicoRepository.getReferenceById(datos.idMedico());
        }
        if(datos.especialidad()== null){
            throw new ValidacionException("Es necesario elegir una especialidad cuando no se elige un medico");
        }
        return medicoRepository.elegirMedicoAleatorioDisponible(datos.especialidad(), datos.fecha());
    }

    public void cancelar(DatosCancelamientoConsulta datos) {
        System.out.println(">>> DEBUG cancelar: idConsulta=" + datos.idConsulta() + " motivo=" + datos.motivo());

        for (MotivoCancelamiento m : MotivoCancelamiento.values()) {
            System.out.println("Enum disponible: -" + m.name() + "-");
        }

        if (!consultaRepository.existsById(datos.idConsulta())) {
            throw new ValidacionException("Id de la consulta informado no existe!");
        }

        validadoresCancelamiento.forEach(v -> v.validar(datos));

        var consulta = consultaRepository.getReferenceById(datos.idConsulta());
        consulta.cancelar(datos.motivo());

//        var motivoEnum = MotivoCancelamiento.valueOf(datos.motivo().trim());
//        consulta.cancelar(motivoEnum);

    }

}
