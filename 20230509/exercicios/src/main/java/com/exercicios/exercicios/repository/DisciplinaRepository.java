package com.exercicios.exercicios.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exercicios.exercicios.models.Disciplina;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
    Disciplina findById (long id);
}
