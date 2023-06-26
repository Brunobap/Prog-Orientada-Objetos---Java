package com.biblioteca.biblioteca.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.biblioteca.Models.Biblioteca;

public interface BibliotecaRepository extends JpaRepository<Biblioteca, Long> {
    Optional<Biblioteca> findByNome (String nome);
}
