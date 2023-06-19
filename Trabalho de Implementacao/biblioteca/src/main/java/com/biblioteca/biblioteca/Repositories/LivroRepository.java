package com.biblioteca.biblioteca.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.biblioteca.Models.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    Optional<Livro> findByTitulo (String titulo);
}
