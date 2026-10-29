package com.medinagarces.reporteservice.dto;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
public record CrearReporte(
 @NotBlank @Pattern(regexp="[0-9]{8}",message="El DNI debe tener 8 dígitos") String dni,
 LocalDateTime fecha,
 @NotBlank @Size(max=30) String tipo,
 @NotBlank @Size(max=200) String direccion,
 @Size(max=255) String referencia) {}
