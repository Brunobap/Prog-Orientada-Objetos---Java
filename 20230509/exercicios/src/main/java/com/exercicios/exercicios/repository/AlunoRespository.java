package com.exercicios.exercicios.repository;

import com.exercicios.exercicios.models.Aluno;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRespository extends JpaRepository<Aluno,Long> {
    List<Aluno> findByMatricula(String matricula);
    
    List<Aluno> findByNome(String nome);
}
