package com.biblioteca.biblioteca.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.biblioteca.Models.Livro;
import com.biblioteca.biblioteca.Models.Reserva;
import com.biblioteca.biblioteca.Models.Usuario;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    Optional<List<Reserva>> findAllByLivro (Livro livro);
    Optional<List<Reserva>> findAllByUsuario (Usuario usuario);
}
