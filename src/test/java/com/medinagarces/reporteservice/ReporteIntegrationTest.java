package com.medinagarces.reporteservice;
import com.medinagarces.reporteservice.dto.*;
import com.medinagarces.reporteservice.service.ReporteService;
import com.medinagarces.reporteservice.repository.ReporteRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ReporteIntegrationTest {
 @Autowired ReporteService service; @Autowired ReporteRepository repository;
 @BeforeEach void clean(){repository.deleteAll();}
 @Test void cicloCompletoYPaginacion(){
  for(int i=0;i<12;i++)service.registrar(new CrearReporte(i%2==0?"12345678":"87654321",null,"DOMICILIARIA","Calle "+i,null));
  var primera=service.listar(0,5);var segunda=service.listar(5,5);var ultima=service.listar(10,5);
  assertEquals(12,primera.totalElements());assertEquals(5,primera.content().size());assertFalse(primera.last());
  assertEquals(5,segunda.content().size());assertEquals(2,ultima.content().size());assertTrue(ultima.last());
  assertTrue(primera.content().stream().noneMatch(a->segunda.content().stream().anyMatch(b->a.getId().equals(b.getId()))));
  var r=primera.content().getFirst();assertEquals(1,r.getEstado());assertNotNull(r.getFecha());
  assertEquals(0,service.atender(r.getId()).getEstado());assertEquals(0,service.obtener(r.getId()).getEstado());
  assertEquals(0,service.atender(r.getId()).getEstado());assertEquals(6,service.porCiudadano("12345678").size());
  assertTrue(service.porCiudadano("00000000").isEmpty());
 }
 @Test void inexistente(){assertThrows(ResponseStatusException.class,()->service.obtener(999999));}
 @Test void paginaVacia(){assertTrue(service.listar(0,5).last());assertTrue(service.listar(99,5).content().isEmpty());}
}
