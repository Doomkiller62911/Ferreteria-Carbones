package com.ferreteria_carbones_final_l.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ferreteria_carbones_final_l.domain.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    public List<Categoria> findByActivoTrue();
}
