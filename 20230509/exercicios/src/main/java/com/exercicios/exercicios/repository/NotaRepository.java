package com.exercicios.exercicios.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exercicios.exercicios.models.Aluno;
import com.exercicios.exercicios.models.Nota;

public interface NotaRepository extends JpaRepository<Nota,Long> {
    List<Nota> findByAluno (Aluno aluno);
}
