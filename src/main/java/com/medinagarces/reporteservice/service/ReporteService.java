package com.medinagarces.reporteservice.service;
import com.medinagarces.reporteservice.dto.*;
import com.medinagarces.reporteservice.entity.Reporte;
import java.util.List;
public interface ReporteService {
 Reporte registrar(CrearReporte request);
 PaginaReportes listar(int offset,int limit);
 Reporte obtener(Integer id);
 List<Reporte> porCiudadano(String dni);
 Reporte atender(Integer id);
}
