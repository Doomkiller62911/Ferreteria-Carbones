package com.ferreteria_carbones_final_l.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "producto")
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Integer idProducto;
    //private Integer idCategoria;

    @Column(unique = true, nullable = false, length = 50)
    @NotNull
    @Size(max = 50)
    private String descripcion;

    @Column(columnDefinition = "TEXT")
    private String detalle;

    //Estas son restricciones a la base de datos, para validar.
    @Column(precision = 12, scale = 2)
    @NotNull(message = "El precio no puede ser nulo...")
    @DecimalMin(value = "0.00", inclusive = true, message = "El precio debe ser mayor o igual a 0...")
    private BigDecimal precio;

    //Estas son restricciones a la base de datos, para validar.
    @NotNull(message = "Las existencias no pueden ser nulas...")
    @Min(value = 0, message = "Las existencias deben ser mayor o igual a 0")
    private int existencias;

    @Column(length = 1024)
    @Size(max = 1024)
    private String rutaImagen;
    private boolean activo;

    @ManyToOne
    @JoinColumn(name = "id_categoria") //Define la llave primaria y su relación
    private Categoria categoria; //Esto nos permite recolectar la información del producto.

}

/*
-- Tabla de categorías
create table producto (
  id_producto INT NOT NULL AUTO_INCREMENT,
  descripcion VARCHAR(50) NOT NULL,
  ruta_imagen varchar(1024),
  activo boolean,
  fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id_producto),
  unique (descripcion),
  index ndx_descripcion (descripcion))
  ENGINE = InnoDB;
 */
