package med.voll.api.domain.medico;

import jakarta.persistence.EntityManager;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.paciente.DatosRegistroPaciente;
import med.voll.api.domain.paciente.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("Deberia devolver null cuando el medico buscado existe pero no esta disponible en esa fecha")
    void elegirMedicoAleatorioDisponibleEscenario1() {
        //given o arrange
        var lunesSiguienteAlas10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10,0);
        var medico = registrarMedico("Medico1","medico@gmail.com","79790998", Especialidad.CARDIOLOGIA);
        var paciente = registrarPaciente("Paciente2","Paciente@gmail.com","52182562");
        registrarConsulta(medico,paciente,lunesSiguienteAlas10);
        //when o act
        var medicoLibre = medicoRepository.elegirMedicoAleatorioDisponible(Especialidad.CARDIOLOGIA,lunesSiguienteAlas10);
        //then o assert
        assertThat(medicoLibre).isNull();
    }
    @Test
    @DisplayName("Deberia devolver medico cuando el medico buscado existe esta disponible en esa fecha")
    void elegirMedicoAleatorioDisponibleEscenario2() {
        //given o arrange
        var lunesSiguienteAlas10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10,0);

        var medico = registrarMedico("Medico1","medico@gmail.com","79790998", Especialidad.CARDIOLOGIA);
        //var paciente = registrarPaciente("Paciente2","Paciente@gmail.com","52182562");
        //registrarConsulta(medico,paciente,lunesSiguienteAlas10);
        //when o act
        var medicoLibre = medicoRepository.elegirMedicoAleatorioDisponible(Especialidad.CARDIOLOGIA,lunesSiguienteAlas10);
        //then o assert
        assertThat(medicoLibre).isEqualTo(medico);
    }

    private void registrarConsulta(Medico medico, Paciente paciente, LocalDateTime fecha) {
        em.persist(new Consulta(null, medico, paciente, fecha));
    }


    private Medico registrarMedico(String nombre, String email, String documento, Especialidad especialidad) {
        var medico = new Medico(datosMedico(nombre, email, documento, especialidad));
        em.persist(medico);
        return medico;
    }

    private Paciente registrarPaciente(String nombre, String email, String documento) {
        var paciente = new Paciente(datosPaciente(nombre, email, documento));
        em.persist(paciente);
        return paciente;
    }

    private DatosRegistroMedico datosMedico(String nombre, String email, String documento, Especialidad especialidad) {
        return new DatosRegistroMedico(
                nombre,
                email,
                "6145489789",
                documento,
                especialidad,
                datosDireccion()
        );
    }

    private DatosRegistroPaciente datosPaciente(String nombre, String email, String documento) {
        return new DatosRegistroPaciente(
                nombre,
                email,
                "1234564",
                documento,
                datosDireccion()
        );
    }
    private DatosDireccion datosDireccion() {
        return new DatosDireccion(
                "calle x",
                "36",
                "26",
                "Benjanin ",
                "11001000",
                "Bogotá",
                "Dc"
        );
    }

}