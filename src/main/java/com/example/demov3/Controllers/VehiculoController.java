package com.example.demov3.Controllers;

import com.example.demov3.Entities.Vehiculo;
import com.example.demov3.Repositories.VehiculoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    private final VehiculoRepository repository;

    public VehiculoController(VehiculoRepository repository) {
        this.repository = repository;
    }
    @PostMapping
    public Vehiculo crear(@RequestBody Vehiculo vehiculo) {
        vehiculo.setId(null);
        return repository.save(vehiculo);
    }
    @GetMapping
    public List<Vehiculo> listar() {
        return repository.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Vehiculo> obtenerPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<Vehiculo> actualizar(@PathVariable Long id, @RequestBody Vehiculo datos) {
        return repository.findById(id).map(v -> {
            v.setMarca(datos.getMarca());
            v.setModelo(datos.getModelo());
            v.setAnio(datos.getAnio());
            v.setPlaca(datos.getPlaca());
            return ResponseEntity.ok(repository.save(v));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}