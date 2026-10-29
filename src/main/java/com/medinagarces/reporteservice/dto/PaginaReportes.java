package com.medinagarces.reporteservice.dto;
import java.util.List;
import com.medinagarces.reporteservice.entity.Reporte;
public record PaginaReportes(List<Reporte> content,long totalElements,int offset,int limit,boolean last) {}
