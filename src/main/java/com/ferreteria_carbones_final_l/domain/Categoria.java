package com.tienda_l.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.Data;
import jakarta.validation.constraints.*;
import java.util.List;



@Data
@Entity
@Table(name="categoria")
public class Categoria implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_categoria")
    private Integer idCategoria;
    
    @Column(unique=true, nullable=false, length=50)
    @NotNull
    @Size(max=50)
    private String descripcion;
    
    @Column (length=1024)
    @Size(max=1024)
    private String rutaImagen;
    private boolean activo;
    
    @OneToMany(mappedBy = "categoria")
    private List<Producto> productos; 
    /*Trae los productos de un ArrayList en particular*/
    
    
    
}

/*
-- Tabla de categorías
create table categoria (
  id_categoria INT NOT NULL AUTO_INCREMENT,
  descripcion VARCHAR(50) NOT NULL,
  ruta_imagen varchar(1024),
  activo boolean,
  fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id_categoria),
  unique (descripcion),
  index ndx_descripcion (descripcion))
  ENGINE = InnoDB;
*/
