package com.exercicios.exercicios.repository;

import com.exercicios.exercicios.models.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRespository extends JpaRepository<Aluno,Long> {
    Aluno findByMatricula(String matricula);
    Aluno findById(long id);    
    Aluno findByNome(String nome);
}
