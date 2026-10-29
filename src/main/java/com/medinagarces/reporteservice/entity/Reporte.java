package com.medinagarces.reporteservice.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity @Table(name="reportes", indexes=@Index(name="idx_reporte_dni",columnList="dni"))
public class Reporte {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Integer id;
 @Column(nullable=false,length=8) private String dni;
 @Column(nullable=false) private LocalDateTime fecha;
 @Column(nullable=false,length=30) private String tipo;
 @Column(nullable=false,length=200) private String direccion;
 @Column(length=255) private String referencia;
 @Column(nullable=false,columnDefinition="integer default 1") private Integer estado=1;
 public Integer getId(){return id;} public void setId(Integer v){id=v;}
 public String getDni(){return dni;} public void setDni(String v){dni=v;}
 public LocalDateTime getFecha(){return fecha;} public void setFecha(LocalDateTime v){fecha=v;}
 public String getTipo(){return tipo;} public void setTipo(String v){tipo=v;}
 public String getDireccion(){return direccion;} public void setDireccion(String v){direccion=v;}
 public String getReferencia(){return referencia;} public void setReferencia(String v){referencia=v;}
 public Integer getEstado(){return estado;} public void setEstado(Integer v){estado=v;}
}
