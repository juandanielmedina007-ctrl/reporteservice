package com.medinagarces.reporteservice.controller;
import com.medinagarces.reporteservice.dto.*;
import com.medinagarces.reporteservice.entity.Reporte;
import com.medinagarces.reporteservice.service.ReporteService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.net.URI;
import java.util.List;
@RestController @RequestMapping("/reportes") @Validated
public class ReporteController {
 private final ReporteService service;
 public ReporteController(ReporteService service){this.service=service;}
 @PostMapping({"","/"}) public ResponseEntity<Reporte> registrar(@Valid @RequestBody CrearReporte q){
  Reporte r=service.registrar(q);return ResponseEntity.created(URI.create("/reportes/"+r.getId())).body(r);
 }
 @GetMapping({"","/"}) public PaginaReportes listar(@RequestParam(defaultValue="0") @Min(0) int offset,@RequestParam(defaultValue="5") @Min(1) @Max(100) int limit){return service.listar(offset,limit);}
 @GetMapping("/{id}") public Reporte obtener(@PathVariable @Positive Integer id){return service.obtener(id);}
 @GetMapping("/ciudadano/{dni}") public List<Reporte> ciudadano(@PathVariable @Pattern(regexp="[0-9]{8}",message="El DNI debe tener 8 dígitos") String dni){return service.porCiudadano(dni);}
 @PutMapping("/{id}") public Reporte atender(@PathVariable @Positive Integer id){return service.atender(id);}
}
