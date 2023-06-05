package com.exercicios.exercicios.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Aluno")
public class Aluno {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "matricula")
    private String matricula;

    @Column(name = "curso")
    private String curso;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy="aluno", cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private List<Nota> notas;

    @ManyToMany(mappedBy = "aluno", cascade = CascadeType.PERSIST)
    @JsonBackReference
    private List<Disciplina> disciplinas;


    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setNotas(List<Nota> notas) {
        this.notas = notas;
    }


    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getCurso() {
        return curso;
    }

    public String getStatus() {
        return status;
    }

    public List<Nota> getNotas() {
        return notas;
    }

    public Aluno() {
        // construtor criado só pro SpringBoot parar de reclamar
    }

    public Aluno(long id, String curso, String matricula, String nome, List<Nota> notas) {
        this.id = id;
        this.curso = curso;
        this.matricula = matricula;
        this.nome = nome;
        this.notas = notas;
    }
}
