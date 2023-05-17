package com.exercicios.exercicios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exercicios.exercicios.models.Aluno;
import com.exercicios.exercicios.repository.AlunoRespository;

@CrossOrigin(origins = "localhost:8080")
@RestController
@RequestMapping("/api")
public class AlunoController {

    @Autowired
    AlunoRespository alunoRepository;

    // Post para criar um aluno informando nome, matrícula, curso e notas;
    @PostMapping("/aluno")
    public ResponseEntity<Aluno> createAluno(@RequestBody Aluno aluno) {
        try {
            Aluno add = new Aluno(aluno.getId(), aluno.getCurso(), aluno.getMatricula(), aluno.getNome(), aluno.getNotas());
            Aluno _aluno = alunoRepository.save(add);
            return new ResponseEntity<>(_aluno, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }        
    }

    // Get para recuperar todos os alunos cadastrados;


    // Put para editar o aluno informado;


    // Get para recuperar a média do aluno.

}