package com.medinagarces.reporteservice.repository;
import com.medinagarces.reporteservice.entity.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ReporteRepository extends JpaRepository<Reporte,Integer> {
 List<Reporte> findByDniOrderByIdDesc(String dni);
}
