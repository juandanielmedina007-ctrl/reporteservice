package com.medinagarces.reporteservice.service;
import com.medinagarces.reporteservice.dto.*;
import com.medinagarces.reporteservice.entity.Reporte;
import com.medinagarces.reporteservice.repository.ReporteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import jakarta.persistence.EntityManager;
import java.time.*;
import java.util.List;
@Service @Transactional(readOnly=true)
public class ReporteServiceImpl implements ReporteService {
 private final ReporteRepository repository; private final EntityManager em;
 public ReporteServiceImpl(ReporteRepository repository,EntityManager em){this.repository=repository;this.em=em;}
 @Transactional public Reporte registrar(CrearReporte q){
  Reporte r=new Reporte();r.setDni(q.dni());
  r.setFecha(q.fecha()==null?LocalDateTime.now(ZoneId.of("America/Lima")):q.fecha());
  r.setTipo(q.tipo().trim().toUpperCase(java.util.Locale.ROOT));r.setDireccion(q.direccion().trim());
  r.setReferencia(q.referencia()==null?null:q.referencia().trim());r.setEstado(1);
  return repository.save(r);
 }
 public PaginaReportes listar(int offset,int limit){
  // offset es cantidad de registros omitidos: 0, 5, 10...; orden estable por ID.
  var rows=em.createQuery("select r from Reporte r order by r.id desc",Reporte.class)
    .setFirstResult(offset).setMaxResults(limit).getResultList();
  long total=repository.count();return new PaginaReportes(rows,total,offset,limit,(long)offset+rows.size()>=total);
 }
 public Reporte obtener(Integer id){return repository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Reporte no encontrado"));}
 public List<Reporte> porCiudadano(String dni){return repository.findByDniOrderByIdDesc(dni);}
 @Transactional public Reporte atender(Integer id){Reporte r=obtener(id);r.setEstado(0);return repository.save(r);}
}
