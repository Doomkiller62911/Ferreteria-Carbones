package com.tienda_l.repository;

import com.tienda_l.domain.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto,Integer> {
    public List<Producto> findByActivoTrue();
}
