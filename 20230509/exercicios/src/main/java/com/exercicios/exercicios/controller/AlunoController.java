package com.exercicios.exercicios.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exercicios.exercicios.models.Aluno;
import com.exercicios.exercicios.models.Nota;
import com.exercicios.exercicios.repository.AlunoRespository;
import com.exercicios.exercicios.repository.NotaRepository;

@CrossOrigin(origins = "localhost:8080")
@RestController
@RequestMapping("/api")
public class AlunoController {

    @Autowired
    AlunoRespository alunoRepository;

    @Autowired
    NotaRepository notaRepository;

    // Post para criar um aluno informando nome, matrícula, curso e notas;
    @PostMapping("/aluno")
    public ResponseEntity<Aluno> createAluno(@RequestBody Aluno aluno) {
        try {
            Aluno add = new Aluno(aluno.getId(), aluno.getCurso(), aluno.getMatricula(), aluno.getNome(), aluno.getNotas());
            Aluno _aluno = alunoRepository.save(add);
            return new ResponseEntity<>(_aluno, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.I_AM_A_TEAPOT);
        }        
    }

    // Get para recuperar todos os alunos cadastrados;
    @GetMapping("/todosAlunos")
    public ResponseEntity<List<Aluno>> getTodosAlunos() {
        try {
            List<Aluno> get = alunoRepository.findAll();
            return new ResponseEntity<List<Aluno>>(get, HttpStatus.FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.I_AM_A_TEAPOT);
        }
    }

    // Put para editar o aluno informado;
    @PutMapping("/editarAluno/{id}")
    public ResponseEntity<Aluno> putEditarAluno (@RequestBody Aluno novo, @PathVariable Long id) {
        try {
            Aluno aluno = alunoRepository.findById((long) id);

            if (aluno != null) {
                List<Nota> notas = notaRepository.findByAluno(aluno);
                if (novo.getCurso() != null) aluno.setCurso(novo.getCurso());               
                if (novo.getMatricula() != null) aluno.setMatricula(novo.getMatricula());
                if (novo.getNome() != null) aluno.setNome(novo.getNome());
                for (int i=0; i<notas.size(); i++){
                    notas.get(i).setValor(novo.getNotas().get(i).getValor());
                }
                Aluno _aluno = alunoRepository.save(aluno);
                return new ResponseEntity<>(_aluno, HttpStatus.FOUND);
            }

            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.I_AM_A_TEAPOT);
        }
    }

    // Get para recuperar a média do aluno.
    @GetMapping("/mediaDoAluno/{id}")
    public ResponseEntity<Float> getMediaDoAluno (@PathVariable Long id) {
        try {
            Optional<Aluno> optAluno = alunoRepository.findById(id);

            if (optAluno.isPresent()) {
                Aluno aluno = optAluno.get();
                float media = 0;
                for (Nota n : aluno.getNotas()) {
                    media += n.getValor();
                }
                media /= aluno.getNotas().size();
                return new ResponseEntity<Float>(media, HttpStatus.FOUND);
            } 
            return new ResponseEntity<Float>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.I_AM_A_TEAPOT);
        }
    }
}