package com.example.demo.Repositories;

import com.example.demo.Models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Lista ordenada por nombre — para checkboxes en formularios
    List<Category> findAllByOrderByNameAsc();
}