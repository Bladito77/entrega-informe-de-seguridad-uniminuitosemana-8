package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RequestMapping("/medicos")
@RestController
@SecurityRequirement(name = "bearer-key")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @Transactional
    @PostMapping
    //public void registrar(@RequestBody @Valid DatosRegistroMedico datos){
    // cambio del responseEntity se cambia al void
    public ResponseEntity registrar(@RequestBody @Valid DatosRegistroMedico datos, UriComponentsBuilder uriComponentsBuilder){
        var medico = new Medico(datos);
        repository.save(new Medico(datos));
        var uri = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DatosDetalleMedico(medico));
    }
    // OJO aca usamos el Pageable de libreria data no de Aw para paginar los datos
    //http://localhost:8080/medicos?size=1&page=1  link de paginacion
    //http://localhost:8080/medicos?sort=nombre,desc ordenacion
    // y como Page<> ya reconoce lista toca quitar el .stream(), el .map() y el .toList()
    //por que ya no habra que convertir objetos a lista
    @GetMapping
    public ResponseEntity <Page<DatosListaMedico>> listar(@PageableDefault(size = 10,sort = {"nombre"}) Pageable paginacion){
        //public Page<DatosListaMedico> listar(@PageableDefault(size = 10,sort = {"nombre"}) Pageable paginacion){
        // cambio envolviendo el metodo listar que devuelve un page ahora en un responseEntity
        //return repository.findAllByActivoTrue(paginacion).map(DatosListaMedico::new);
        // aca como cambio por el entity ya no devuelve un page se debe retornar una var
            var page= repository.findAllByActivoTrue(paginacion).map(DatosListaMedico::new);
            return ResponseEntity.ok(page);
        // devuelve el page entre el response entity .ok
                //.stream().map(DatosListaMedico::new);
                //.toList();
    }
    @Transactional
    @PutMapping
    //public void actualizar(@RequestBody @Valid DatosActualizacionMedico datos){
    // cambio del void por entity
    public ResponseEntity actualizar(@RequestBody @Valid DatosActualizacionMedico datos){
        var medico = repository.getReferenceById(datos.id());
        medico.actualizarInformaciones(datos);
        return ResponseEntity.ok(new DatosDetalleMedico(medico));
        // aca va el retur del responseEntity del medico en un nuevo metodo
        // por el transaccional no hay que llamar repository para grabar lo detecta Autom...
    }
    @Transactional
    @DeleteMapping("/{id}")
    //public void eliminar(@PathVariable Long id){
    public ResponseEntity eliminar(@PathVariable Long id){
        //repository.deleteById(id);
        var medico =repository.getReferenceById(id);
        medico.inactivar();
        // linea nueva para reconoder códigos 204
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity detallar(@PathVariable Long id){
        var medico =repository.getReferenceById(id);
        return ResponseEntity.ok(new DatosDetalleMedico(medico));
    }
}
