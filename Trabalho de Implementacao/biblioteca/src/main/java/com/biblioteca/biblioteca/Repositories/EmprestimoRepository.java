package com.biblioteca.biblioteca.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.biblioteca.Models.Emprestimo;
import com.biblioteca.biblioteca.Models.Livro;
import com.biblioteca.biblioteca.Models.Usuario;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
    Optional<List<Emprestimo>> findAllByLivro (Livro livro);
    Optional<List<Emprestimo>> findAllByUsuario (Usuario usuario);
    Optional<Emprestimo> findByLivro (Livro livro);
}
